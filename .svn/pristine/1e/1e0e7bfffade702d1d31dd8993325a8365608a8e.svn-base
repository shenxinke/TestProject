package com.yst.onecity.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.servermember.ServiceOffLineOrderDetailActivity;
import com.yst.onecity.adapter.chatadapter.ServiceOffLineOrderAdapter;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.bean.order.MemberOffLineBean;
import com.yst.onecity.bean.order.MemberOrderBean;
import com.yst.onecity.callbacks.AbstractMemberOffLineCallBack;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 线下订单(服务专员)
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class ServiceOffLineFragment extends BaseFragment implements OnLoadmoreListener, OnRefreshListener {

    @BindView(R.id.fragment_line_lv)
    ListView listView;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.tv_no_order)
    TextView tvNoOrder;
    private List<MemberOrderBean> data = new ArrayList<>();
    private int pageSize = 10, pageNum = 1;
    private ServiceOffLineOrderAdapter adapter;

    @Override
    public int bindLayout() {
        return R.layout.fragment_line;
    }

    @Override
    public void initData() {
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadmoreListener(this);
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableLoadmore(true);
        smartRefreshLayout.autoRefresh();
        requestOffLineNetWork(pageNum);
    }

    /**
     * 获取线下订单列表
     */
    private void requestOffLineNetWork(final int page) {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "page", String.valueOf(page),
                "rows", String.valueOf(pageSize),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.SERVER_UNLINE_ORDER_LIST)
                .addParams("page", String.valueOf(page))
                .addParams("rows", String.valueOf(pageSize))
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractMemberOffLineCallBack() {
            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                onLoad();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show("网络访问失败，请重试!");
            }

            @Override
            public void onResponse(MemberOffLineBean response, int id) {
                if (response.getCode() == 1) {
                    if (page == 1) {
                        data = response.getContent();
                        if (data == null || data.size() == 0) {
                            tvNoOrder.setVisibility(View.VISIBLE);
                        } else {
                            tvNoOrder.setVisibility(View.GONE);
                        }
                        adapter.onRefresh(response.getContent());
                    } else {
                        data.addAll(response.getContent());
                        adapter.addData(response.getContent());
                    }
                } else {
                    ToastUtils.show(response.getMsg());
                    tvNoOrder.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void initControll() {
        adapter = new ServiceOffLineOrderAdapter(getActivity(), data);
        listView.setAdapter(adapter);
    }

    @Override
    public void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == -1) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("oid", data.get(i).getId());
                JumpIntent.jump(getActivity(), ServiceOffLineOrderDetailActivity.class, bundle);
            }
        });
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNum = 1;
        requestOffLineNetWork(pageNum);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        pageNum++;
        requestOffLineNetWork(pageNum);
    }

    /**
     * 用来停止列表刷新的
     **/
    private void onLoad() {
        smartRefreshLayout.finishRefresh(500);
        smartRefreshLayout.finishLoadmore(500);
    }
}
