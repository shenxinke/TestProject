package com.yst.onecity.fragment;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.NewDetailActivity;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.activity.publish.EditShareActivity;
import com.yst.onecity.adapter.HomeShareAdapter;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.bean.consult.ConsultListBean;
import com.yst.onecity.callbacks.AbstractConsultListCallback;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 首页分享fragment
 *
 * @author jiaofan
 * @version 4.2.0
 * @date 2018/5/30
 */

public class HomeShareFragment extends BaseFragment implements OnRefreshListener, OnLoadmoreListener, AdapterView.OnItemClickListener, View.OnTouchListener {
    @BindView(R.id.lv_share)
    ListView mLvShare;
    @BindView(R.id.smart_share)
    SmartRefreshLayout mSmartShare;
    @BindView(R.id.iv_empty)
    ImageView mIvEmpty;
    @BindView(R.id.ib_publish)
    ImageView mPublish;

    private List<ConsultListBean.ContentBean.ContentListBean> mList = new ArrayList<>();
    private HomeShareAdapter mAdapter;
    private int page = 1;

    @Override
    public int bindLayout() {
        return R.layout.fragment_home_share;
    }

    /**
     * 创建一个分享fragment实例
     *
     * @return fragment
     */
    public static HomeShareFragment newInstance() {
        return new HomeShareFragment();
    }

    @Override
    public void initData() {
        mSmartShare.autoRefresh(100);
        mSmartShare.setOnRefreshListener(this);
        mSmartShare.setOnLoadmoreListener(this);

        mAdapter = new HomeShareAdapter(context, mList);
        mLvShare.setAdapter(mAdapter);
        mLvShare.setOnTouchListener(this);
        mLvShare.setOnItemClickListener(this);
    }

    /**
     * 获取列表数据
     */
    private void getShareData() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "page", String.valueOf(page),
                "rows", String.valueOf(10),
                "flag","0",
                "timestamp", timestamp);

        OkHttpUtils.post().url(Constants.GET_HOME_CONSULT_LIST)
                .addParams("page", String.valueOf(page))
                .addParams("rows", String.valueOf(10))
                .addParams("flag", "0")
                .addParams("timestamp", timestamp)
                .addParams("sign", sign)
                .addParams("client_type", "A")
                .build().execute(new AbstractConsultListCallback() {

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                showInfoProgressDialog();
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                dismissInfoProgressDialog();
                onLoadFinish();
                if (mList.size() == Const.INTEGER_0) {
                    mIvEmpty.setVisibility(View.VISIBLE);
                } else {
                    mIvEmpty.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.str_net_error_message));
                MyLog.e("zzz", e.toString());
            }

            @Override
            public void onResponse(ConsultListBean bean, int id) {
                if (bean.getCode() == Const.INTEGER_1) {
                    if (bean.getContent().getConsultationInfoList().size() > Const.INTEGER_0) {
                        mIvEmpty.setVisibility(View.GONE);
                        mAdapter.setData(bean.getContent().getConsultationInfoList());
                    } else {
                        if (mList.size() > Const.INTEGER_0 && page > Const.INTEGER_1) {
                            ToastUtils.show("已加载全部数据");
                        }
                    }
                } else {
                    ToastUtils.show(bean.getMsg());
                }
            }
        });
    }

    @OnClick(R.id.ib_publish)
    public void onViewClicked() {
        if (!ConstUtils.isClickable()) {
            return;
        }
        ConstUtils.setFilter(mPublish, Const.INTEGER_2000);
        if (!TianyiApplication.isLogin) {
            JumpIntent.jump(getActivity(), LoginActivity.class);
        } else {
            JumpIntent.jump(getActivity(), EditShareActivity.class);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", String.valueOf(mList.get(position).getId()));
        JumpIntent.jump(getActivity(), NewDetailActivity.class, bundle);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        mList.clear();
        getShareData();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        getShareData();
    }

    public void setPublishVisibility() {
        if (mPublish != null) {
            mPublish.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 停止刷新和加载
     */
    private void onLoadFinish() {
        mSmartShare.finishRefresh(500);
        mSmartShare.finishLoadmore(500);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                mPublish.setVisibility(View.GONE);
                break;
            case MotionEvent.ACTION_UP:
                if (mSmartShare.isRefreshing() || mSmartShare.isLoading()) {
                    mPublish.setVisibility(View.VISIBLE);
                }
                mPublish.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        return false;
    }
}