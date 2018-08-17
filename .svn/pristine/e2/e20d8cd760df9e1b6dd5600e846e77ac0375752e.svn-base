package com.yst.onecity.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.servermember.ServiceOnlineOrderDetailActivity;
import com.yst.onecity.adapter.ServiceOrderAdapter;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.bean.ServerOnLineBean;
import com.yst.onecity.bean.order.OrderStatusNumBean;
import com.yst.onecity.callbacks.AbstractOrderNumCallback;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 线下订单(服务专员)
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class ServiceOnLineFragment extends BaseFragment implements OnRefreshListener, OnLoadmoreListener {

    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_wait_pay)
    TextView tvWaitPay;
    @BindView(R.id.tv_pay_count)
    TextView tvPayCount;
    @BindView(R.id.tv_wait_send)
    TextView tvWaitSend;
    @BindView(R.id.tv_send_count)
    TextView tvSendCount;
    @BindView(R.id.tv_wait_recieve)
    TextView tvWaitRecieve;
    @BindView(R.id.tv_recieve_count)
    TextView tvRecieveCount;
    @BindView(R.id.tv_order_evalute)
    TextView tvOrderEvalute;
    @BindView(R.id.tv_evalute_count)
    TextView tvEvaluteCount;
    @BindView(R.id.tv_order_back)
    TextView tvOrderBack;
    @BindView(R.id.tv_back_count)
    TextView tvBackCount;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.tv_no_order)
    TextView tvNoOrder;
    private ServiceOrderAdapter adapter;
    private int state = 8;
    private int pageSize = 10, pageNum = 1;
    private List<ServerOnLineBean.ContentBean> onLineList = new ArrayList<>();

    @Override
    public int bindLayout() {
        return R.layout.fragment_on_line;
    }

    @Override
    public void initData() {
        tvAll.setSelected(true);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadmoreListener(this);
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableLoadmore(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        requestOnLineNetWork(pageNum);
    }

    private void requestStatusNumNetWork() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "id", TianyiApplication.appCommonBean.getHunter_id(),
                //会员0，推广师1
                "flag", "1",
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "timestamp", timestamp);
        //订单状态、当前页码、页大小
        OkHttpUtils.post().url(Constants.QUERY_ORDER_NUM)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("id", TianyiApplication.appCommonBean.getHunter_id())
                .addParams("flag", "1")
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractOrderNumCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show("网络访问失败，请重试!");
            }

            @Override
            public void onResponse(OrderStatusNumBean response, int id) {
                if (response.getCode() == 1) {
                    OrderStatusNumBean.OrderNumBean content = response.getContent();
                    if (content.getDfk() > 0) {
                        //待付款
                        tvPayCount.setVisibility(View.VISIBLE);
                        tvPayCount.setText(String.valueOf(content.getDfk()));
                    } else {
                        tvPayCount.setVisibility(View.GONE);
                    }
                    if (content.getDfh() > 0) {
                        //代发货
                        tvSendCount.setVisibility(View.VISIBLE);
                        tvSendCount.setText(String.valueOf(content.getDfh()));
                    } else {
                        tvSendCount.setVisibility(View.GONE);
                    }
                    if (content.getDsh() > 0) {
                        //待收货
                        tvRecieveCount.setVisibility(View.VISIBLE);
                        tvRecieveCount.setText(String.valueOf(content.getDsh()));
                    } else {
                        tvRecieveCount.setVisibility(View.GONE);
                    }
                    if (content.getDpj() > 0) {
                        //待评价
                        tvEvaluteCount.setVisibility(View.VISIBLE);
                        tvEvaluteCount.setText(String.valueOf(content.getDpj()));
                    } else {
                        tvEvaluteCount.setVisibility(View.GONE);
                    }
                    if (content.getYcx() > 0) {
                        //已撤销
                        tvBackCount.setVisibility(View.VISIBLE);
                        tvBackCount.setText(String.valueOf(content.getYcx()));
                    } else {
                        tvBackCount.setVisibility(View.GONE);
                    }
                } else {
                    ToastUtils.show(response.getMsg());
                }
            }
        });
    }


    /**
     * 获取线上订单列表
     *
     * @param page 页数
     */
    private void requestOnLineNetWork(final int page) {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "status", String.valueOf(state),
                "page", String.valueOf(page),
                "rows", String.valueOf(pageSize),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "timestamp", timestamp);
        // 	订单状态码0待付款 1已支付 2待发货 3待收货 4已评价 5待评价 6已撤销 7已收货 8全部
        OkHttpUtils.post().url(Constants.SERVER_ONLINE_ORDER_LIST)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("status", String.valueOf(state))
                .addParams("page", String.valueOf(page))
                .addParams("rows", String.valueOf(pageSize))
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new StringCallback() {
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
            public void onResponse(String response, int id) {
                MyLog.e("@@@@@@@@@@@", response);
                Gson gson = new Gson();
                ServerOnLineBean serverOnLineBean = gson.fromJson(response, ServerOnLineBean.class);
                if (serverOnLineBean != null && serverOnLineBean.getCode() == 1) {
                    if (page == 1) {
                        onLineList = serverOnLineBean.getContent();
                        if (onLineList == null || onLineList.size() == 0) {
                            tvNoOrder.setVisibility(View.VISIBLE);
                        } else {
                            tvNoOrder.setVisibility(View.GONE);
                        }
                        requestStatusNumNetWork();
                    } else {
                        onLineList.addAll(serverOnLineBean.getContent());
                    }
                } else {
                    ToastUtils.show(serverOnLineBean.getMsg());
                    tvNoOrder.setVisibility(View.VISIBLE);
                }
                flushOnLineListData();
            }
        });
    }

    /**
     * 刷新适配器
     */
    private void flushOnLineListData() {
        if (null == listView) {
            return;
        }
        if (adapter == null) {
            adapter = new ServiceOrderAdapter(getActivity(), onLineList);
            listView.setAdapter(adapter);
        } else {
            adapter.setData(onLineList);
        }
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
                bundle.putString("oid", onLineList.get(i).getId());
                bundle.putInt("status", onLineList.get(i).getStatus());
                bundle.putString("mImId", onLineList.get(i).getMImId());
                bundle.putString("orderNo", onLineList.get(i).getOrderNo());
                bundle.putString("merchantId", onLineList.get(i).getMerchant_id());
                JumpIntent.jump(getActivity(), ServiceOnlineOrderDetailActivity.class, bundle);
            }
        });
    }


    @OnClick({R.id.tv_all, R.id.tv_wait_pay, R.id.tv_wait_send, R.id.tv_wait_recieve,
            R.id.tv_order_evalute, R.id.tv_order_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_all:
                state = 8;
                tvAll.setSelected(true);
                tvWaitPay.setSelected(false);
                tvWaitSend.setSelected(false);
                tvWaitRecieve.setSelected(false);
                tvOrderEvalute.setSelected(false);
                tvOrderBack.setSelected(false);
                break;
            case R.id.tv_wait_pay:
                state = 0;
                tvAll.setSelected(false);
                tvWaitPay.setSelected(true);
                tvWaitSend.setSelected(false);
                tvWaitRecieve.setSelected(false);
                tvOrderEvalute.setSelected(false);
                tvOrderBack.setSelected(false);
                break;
            case R.id.tv_wait_send:
                state = 2;
                tvAll.setSelected(false);
                tvWaitPay.setSelected(false);
                tvWaitSend.setSelected(true);
                tvWaitRecieve.setSelected(false);
                tvOrderEvalute.setSelected(false);
                tvOrderBack.setSelected(false);
                break;
            case R.id.tv_wait_recieve:
                state = 3;
                tvAll.setSelected(false);
                tvWaitPay.setSelected(false);
                tvWaitSend.setSelected(false);
                tvWaitRecieve.setSelected(true);
                tvOrderEvalute.setSelected(false);
                tvOrderBack.setSelected(false);
                break;
            case R.id.tv_order_evalute:
                state = 7;
                tvAll.setSelected(false);
                tvWaitPay.setSelected(false);
                tvWaitSend.setSelected(false);
                tvWaitRecieve.setSelected(false);
                tvOrderEvalute.setSelected(true);
                tvOrderBack.setSelected(false);
                break;
            case R.id.tv_order_back:
                state = 6;
                tvAll.setSelected(false);
                tvWaitPay.setSelected(false);
                tvWaitSend.setSelected(false);
                tvWaitRecieve.setSelected(false);
                tvOrderEvalute.setSelected(false);
                tvOrderBack.setSelected(true);
                break;
            default:
                break;
        }
        smartRefreshLayout.autoRefresh();
        pageNum = 1;
        requestOnLineNetWork(pageNum);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNum = 1;
        requestOnLineNetWork(pageNum);
    }

    /**
     * 用来停止列表刷新的
     **/
    private void onLoad() {
        smartRefreshLayout.finishRefresh(500);
        smartRefreshLayout.finishLoadmore(500);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        pageNum++;
        requestOnLineNetWork(pageNum);
    }
}
