package com.yst.onecity.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.yst.onecity.R;
import com.yst.onecity.activity.LiveRoomActivity;
import com.yst.onecity.activity.VideoPlayerActivity;
import com.yst.onecity.adapter.HomeLiveListAdapter;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.bean.livevideo.LiveVideoBean;
import com.yst.onecity.callbacks.AbstractLiveListCallback;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 首页直播类标题栏Fragment
 *
 * @author WangJingWei
 * @version 4.1.0
 * @date 2018/5/14.
 */
public class HomeLiveTabFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.img_empty)
    ImageView imgEmpty;

    private int page = 1;
    private int size = 5;
    private HomeLiveListAdapter mHomeLiveListAdapter;
    private List<LiveVideoBean.ContentBean> mData = new ArrayList<>();

    @Override
    public int bindLayout() {
        return R.layout.fragment_home_live_tab;
    }

    /**
     * 创建一个资讯frament的实例，传入资讯类型id
     *
     * @return 具体fragment
     */
    public static HomeLiveTabFragment newInstance() {
        HomeLiveTabFragment fragment = new HomeLiveTabFragment();
        return fragment;
    }

    @Override
    public void initData() {
        smartRefreshLayout.autoRefresh(100);
        mHomeLiveListAdapter = new HomeLiveListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mHomeLiveListAdapter);
        initListener();
    }

    private void initListener() {
        mHomeLiveListAdapter.setBaseAdapterListener(position -> {
            String type = Utils.changeEmptyStringToZero(mData.get(position).getType());
            switch (type) {
                case Const.TYPE_LIVE:
                    LiveRoomActivity.openActivity(getActivity(), mData.get(position).getGroupId(), mData.get(position).getFlvPlayAddress(), mData.get(position).getRoomId(), mData.get(position).getImgUrl(), mData.get(position).getHlsPlayAddress(), mData.get(position).getDesc());
                    break;
                case Const.TYPE_VIDEO:
                    VideoPlayerActivity.openActivity(getActivity(), mData.get(position).getId(), mData.get(position).getImgUrl());
                    break;
                default:
                    break;
            }
        });

        smartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                requestLiveData();
                smartRefreshLayout.finishLoadmore(1000);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                if (mData != null) {
                    mData.clear();
                }
                requestLiveData();
                smartRefreshLayout.finishRefresh(500);
            }
        });
    }

    /**
     * 请求直播列表页面数据
     */
    private void requestLiveData() {

        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "page", String.valueOf(page),
                "size", String.valueOf(size),
                "timestamp", timestamp);

        OkHttpUtils.post().url(Constants.HOME_LIVEVIDEO_LIST)
                .addParams("page", String.valueOf(page))
                .addParams("size", String.valueOf(size))
                .addParams("timestamp", timestamp)
                .addParams("sign", sign)
                .addParams("client_type", "A")
                .build().execute(new AbstractLiveListCallback() {

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                imgEmpty.setVisibility(View.VISIBLE);
            }

            @Override
            public void onResponse(LiveVideoBean response, int id) {
                if (response.getContent() != null) {
                    List<LiveVideoBean.ContentBean> content = response.getContent();
                    mData.addAll(content);
                    if (mData.size() == Const.INTEGER_0) {
                        imgEmpty.setVisibility(View.VISIBLE);
                    } else {
                        imgEmpty.setVisibility(View.GONE);
                    }
                    mHomeLiveListAdapter.addList(mData);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
