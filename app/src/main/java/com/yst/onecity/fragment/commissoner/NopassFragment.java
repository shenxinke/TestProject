package com.yst.onecity.fragment.commissoner;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.VideoPlayerActivity;
import com.yst.onecity.activity.publish.EditShareActivity;
import com.yst.onecity.adapter.commissoner.PubLishAdapter;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.bean.AdressBean;
import com.yst.onecity.bean.commissioner.PublishBean;
import com.yst.onecity.bean.commissioner.PublishDataBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.interfaces.BaseAdapterListener;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.SpaceItemDecoration;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import gorden.rxbus2.RxBus;
import okhttp3.Call;

/**
 * 未通过
 *
 * @author wuxiaofang
 * @version 4.2.0
 * @date 2018/6/5
 */

public class NopassFragment extends BaseFragment implements OnRefreshLoadmoreListener, PubLishAdapter.OnItemClick, BaseAdapterListener, PubLishAdapter.OnPlayClick {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    Unbinder unbinder;
    private boolean isPrepared;
    private boolean isFirst = true;
    private boolean isVisiable;
    private int page = Const.INTEGER_1;
    private PubLishAdapter pubLishAdapter;
    private List<PublishDataBean> noPassList = new ArrayList<>();
    private int anInt;
    private int id;
    private int type;

    @Override
    public int bindLayout() {
        return R.layout.fragment_publish;
    }

    @Override
    public void initData() {
        isPrepared = true;
        init();
        lazyLoad();
    }

    /**
     * 初始化
     */
    private void init() {
        pubLishAdapter = new PubLishAdapter(getActivity(), Const.INTEGER_0);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycler.addItemDecoration(new SpaceItemDecoration(0, 20));
        recycler.setAdapter(pubLishAdapter);
        refresh.setOnRefreshLoadmoreListener(this);
        pubLishAdapter.setOnclick(this, this);
        pubLishAdapter.setOnPlayClick(this);
    }

    /**
     * 懒加载
     */
    public void lazyLoad() {
        MyLog.e("sss", "isFirst: " + isFirst + "-isRepared: " + isPrepared + "_isVisible: " + isVisiable);
        if (!isPrepared || !isVisiable || !isFirst) {
            return;
        }
        getNoPassList();
        isFirst = false;
    }

    /**
     * 获取未通过的数据
     */
    private void getNoPassList() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "status", String.valueOf(Const.INTEGER_3),
                "page", String.valueOf(page),
                "rows", String.valueOf(Const.INTEGER_10),
                "timestamp", timestamp);
        if (TextUtils.isEmpty(sign)) {
            return;
        }
        OkHttpUtils.post().url(Constants.GET_SHARE_LIST)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("status", String.valueOf(Const.INTEGER_3))
                .addParams("page", String.valueOf(page))
                .addParams("rows", String.valueOf(Const.INTEGER_10))
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.str_net_error_message));
                ivEmpty.setVisibility(View.VISIBLE);
            }

            @Override
            public void onResponse(String response, int id) {
                if (null != response) {
                    PublishBean bean = new Gson().fromJson(response, PublishBean.class);
                    if (null != bean) {
                        if (bean.getCode() == Const.INTEGER_1 && null != bean.getContent() && null != bean.getContent().getList()) {
                            if (page == Const.INTEGER_1) {
                                noPassList.clear();
                            } else {
                                if (bean.getContent().getList().isEmpty()) {
                                    ToastUtils.show(getString(R.string.xlist_add_all_msg));
                                }
                            }
                            for (int i = 0; i < bean.getContent().getList().size(); i++) {
                                PublishDataBean dataBean = new PublishDataBean();
                                dataBean.address.set(bean.getContent().getList().get(i).getAddress());
                                dataBean.readNum.set(String.valueOf(bean.getContent().getList().get(i).getReadNum()) + "人看过");
                                dataBean.title.set(bean.getContent().getList().get(i).getTitle());
                                dataBean.time.set(bean.getContent().getList().get(i).getTime());
                                dataBean.modelType.set(bean.getContent().getList().get(i).getModelType());
                                dataBean.type.set(bean.getContent().getList().get(i).getType());
                                dataBean.videoAddress.set(bean.getContent().getList().get(i).getVideoAddress());
                                dataBean.id.set(bean.getContent().getList().get(i).getId());
                                noPassList.add(dataBean);
                            }
                            pubLishAdapter.addData(noPassList);
                            setVisiableGone(noPassList);
                        } else {
                            ToastUtils.show(bean.getMsg());
                        }
                    }
                }
            }
        });
    }

    /**
     * 设置数据和图片的展示
     *
     * @param list 集合
     */
    public void setVisiableGone(List<PublishDataBean> list) {
        if (null != list) {
            if (list.isEmpty()) {
                ivEmpty.setVisibility(View.VISIBLE);
            } else {
                ivEmpty.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = Const.INTEGER_1;
        getNoPassList();
        refreshlayout.finishRefresh(500);
        RxBus.get().send(Const.INTEGER_1000,new AdressBean(Const.STR2));
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        getNoPassList();
        refreshlayout.finishLoadmore(500);
        RxBus.get().send(Const.INTEGER_1000,new AdressBean(Const.STR2));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisiable = true;
            lazyLoad();
        } else {
            isVisiable = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getNoPassList();
    }

    @Override
    public void onClick() {
        if (type == Const.INTEGER_1) {
            VideoPlayerActivity.openActivity(getActivity(), String.valueOf(id), noPassList.get(anInt).address.get());
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("id", String.valueOf(id));
            bundle.putString("from", "0");
            JumpIntent.jump(getActivity(), EditShareActivity.class, bundle);
        }
    }

    @Override
    public void onItemClick(int position) {
        anInt = position;
        id = noPassList.get(position).id.get();
        MyLog.e("sss", "---id: " + noPassList.get(position).id.get());
        type = noPassList.get(position).type.get();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MyLog.e("xxx", "onDestroyView-------未通过");
    }

    @Override
    public void onPlay() {
    }
}
