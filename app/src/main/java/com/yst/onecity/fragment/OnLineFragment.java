package com.yst.onecity.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.member.OrderDetailActivity;
import com.yst.onecity.activity.member.OrderEvaluteActivity;
import com.yst.onecity.activity.member.PayCenterActivity;
import com.yst.onecity.adapter.OnLineOrderAdapter;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.bean.CodeMsgBean;
import com.yst.onecity.bean.order.MemberOnLineOrderBean;
import com.yst.onecity.bean.order.OrderStatusNumBean;
import com.yst.onecity.bean.order.SonBean;
import com.yst.onecity.callbacks.AbstractCodeMsgCallback;
import com.yst.onecity.callbacks.AbstractOrderNumCallback;
import com.yst.onecity.config.Const;
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
import okhttp3.Request;

/**
 * 线上订单(会员端)
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class OnLineFragment extends BaseFragment implements OnRefreshListener, OnLoadmoreListener {

    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_all_count)
    TextView tvAllCount;
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
    @BindView(R.id.tv_order_accomplish)
    TextView tvOrderAccomplish;
    @BindView(R.id.tv_accomplish_count)
    TextView tvAccomplishCount;
    @BindView(R.id.tv_order_back)
    TextView tvOrderBack;
    @BindView(R.id.tv_back_count)
    TextView tvBackCount;
    @BindView(R.id.tv_no_order)
    TextView tvNoOrder;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private OnLineOrderAdapter adapter;
    private int state = -1;
    private int selectPosition = -2;
    private int pageSize = 5, pageNum = 1;
    private List<MemberOnLineOrderBean.OnLineBean> onLineList = new ArrayList<>();

    @Override
    public int bindLayout() {
        return R.layout.fragment_on_line;
    }

    @Override
    public void initData() {
        tvAll.setSelected(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        adapter = new OnLineOrderAdapter();
        adapter.addList(onLineList);
        recyclerView.setAdapter(adapter);

        initListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        pageNum = 1;
        requestOnLineNetWork(pageNum);
    }


    /**
     * 各个状态的数量
     */
    private void requestStatusNumNetWork() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "id", TianyiApplication.appCommonBean.getId(),
                //会员0，推广师1
                "flag", "0",
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.QUERY_ORDER_NUM)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("id", TianyiApplication.appCommonBean.getId())
                .addParams("flag", "0")
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
                    MyLog.e("@@@@@@@@", content.getAllOrder() + "==========");
                    //待付款
                    if (content.getDfk() > 0) {
                        tvPayCount.setVisibility(View.VISIBLE);
                        tvPayCount.setText(String.valueOf(content.getDfk()));
                    } else {
                        tvPayCount.setVisibility(View.GONE);
                    }
                    //待发货
                    if (content.getDfh() > 0) {
                        tvSendCount.setVisibility(View.VISIBLE);
                        tvSendCount.setText(String.valueOf(content.getDfh()));
                    } else {
                        tvSendCount.setVisibility(View.GONE);
                    }
                    //待收货
                    if (content.getDsh() > 0) {
                        tvRecieveCount.setVisibility(View.VISIBLE);
                        tvRecieveCount.setText(String.valueOf(content.getDsh()));
                    } else {
                        tvRecieveCount.setVisibility(View.GONE);
                    }
                    //已收货
                    if (content.getDpj() > 0) {
                        tvEvaluteCount.setVisibility(View.VISIBLE);
                        tvEvaluteCount.setText(String.valueOf(content.getDpj()));
                    } else {
                        tvEvaluteCount.setVisibility(View.GONE);
                    }
                    //已完成
                    if (content.getYwc() > 0) {
                        tvAccomplishCount.setVisibility(View.VISIBLE);
                        tvAccomplishCount.setText(String.valueOf(content.getYwc()));
                    } else {
                        tvAccomplishCount.setVisibility(View.GONE);
                    }
                    //已撤销
                    if (content.getYcx() > 0) {
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
        //0待付款 2待发货（已付款） 3待收货 4已评价  6已撤销 7已收货（待评价）8已完成 -1全部

        //订单状态、当前页码、页大小
        OkHttpUtils.post().url(Constants.MEMBER_ONLINE_ORDER)
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
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                showProgressDialog();
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);

            }

            @Override
            public void onError(Call call, Exception e, int id) {
                dismissProgressDialog();
                ToastUtils.show("网络访问失败，请重试!");
            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("@@@@@@@@@@@", response);
                Gson gson = new Gson();
                MemberOnLineOrderBean onLineOrderBean = gson.fromJson(response, MemberOnLineOrderBean.class);
                if (onLineOrderBean.getCode() == 1) {
                    if (page == 1) {
                        onLineList = onLineOrderBean.getContent();
                    } else {
                        onLineList.addAll(onLineOrderBean.getContent());
                    }
                    if (onLineList.size() > 0) {
                        tvNoOrder.setVisibility(View.GONE);
                    } else {
                        tvNoOrder.setVisibility(View.VISIBLE);
                    }
                    flushData(onLineList);
                } else {
                    ToastUtils.show(onLineOrderBean.getMsg());
                    tvNoOrder.setVisibility(View.VISIBLE);
                }
                requestStatusNumNetWork();
                dismissProgressDialog();
            }
        });
    }

    /**
     * 初始化监听事件
     */
    private void initListener() {
        smartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                smartRefreshLayout.finishLoadmore(500);
                pageNum++;
                requestOnLineNetWork(pageNum);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                smartRefreshLayout.finishRefresh(500);
                pageNum = 1;
                requestOnLineNetWork(pageNum);
            }
        });

        adapter.setOnLineAdapterListener(new OnLineOrderAdapter.OnLineAdapterListener() {
            @Override
            public void btStateClick(int position, String status) {
                MemberOnLineOrderBean.OnLineBean item = onLineList.get(position);
                Bundle bundle = new Bundle();
                switch (status) {
                    case Const.STR0:
                    case Const.STR3:
                        List<SonBean> sonData = item.getSon();
                        StringBuilder orderIds = new StringBuilder();
                        for (int i = 0; i < sonData.size(); i++) {
                            orderIds.append(sonData.get(i).getId());
                            if (i != sonData.size() - 1) {
                                orderIds.append(",");
                            }
                        }
                        if (Const.STR3.equals(status)) {
                            requestConfirmReceiveNetWork(orderIds.toString());
                        } else {
                            bundle.putString("orderIds", orderIds.toString());
                            bundle.putString("price", String.valueOf(item.getTotal_price()));
                            bundle.putString("score", String.valueOf(item.getTotalScore()));
                            JumpIntent.jump(getActivity(), PayCenterActivity.class, bundle);
                        }
                        break;
                    case Const.STR7:
                    case Const.STR8:
                        bundle.putString("orderNo", item.getOrderNo());
                        bundle.putString("merchantId", item.getMerchant_id());
                        bundle.putInt("flag", item.getCommentStatus());
                        JumpIntent.jump(getActivity(), OrderEvaluteActivity.class, bundle);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onItemClick(int position) {
                List<SonBean> sonData = onLineList.get(position).getSon();
                StringBuilder ids = new StringBuilder();
                for (int i = 0; i < sonData.size(); i++) {
                    ids.append(sonData.get(i).getId());
                    if (i != sonData.size() - 1) {
                        ids.append(",");
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putString("orderNo", onLineList.get(position).getOrderNo());
                bundle.putString("oid", ids.toString());
                bundle.putString("payType", onLineList.get(position).getPayType());
                bundle.putString("id", onLineList.get(position).getId());
                bundle.putString("merchantId", onLineList.get(position).getMerchant_id());
                bundle.putInt("state", onLineList.get(position).getSon().get(0).getStatus());
                bundle.putInt("commentStatus", onLineList.get(position).getCommentStatus());
                JumpIntent.jump(getActivity(), OrderDetailActivity.class, bundle);
            }
        });
    }

    /**
     * 适配数据
     *
     * @param data
     */
    private void flushData(List<MemberOnLineOrderBean.OnLineBean> data) {
        adapter.addList(data);
    }

    @OnClick({R.id.tv_all, R.id.tv_wait_pay, R.id.tv_wait_send, R.id.tv_wait_recieve,
            R.id.tv_order_evalute, R.id.tv_order_accomplish, R.id.tv_order_back})
    public void onViewClicked(View view) {
        tvNoOrder.setVisibility(View.GONE);
        switch (view.getId()) {
            //全部
            case R.id.tv_all:
                state = -1;
                changeViewSelectedState(state);
                break;
            //待付款
            case R.id.tv_wait_pay:
                state = 0;
                changeViewSelectedState(state);
                break;
            //待发货
            case R.id.tv_wait_send:
                state = 2;
                changeViewSelectedState(state);
                break;
            //待收货
            case R.id.tv_wait_recieve:
                state = 3;
                changeViewSelectedState(state);
                break;
            //已收货
            case R.id.tv_order_evalute:
                state = 7;
                changeViewSelectedState(state);
                break;
            //已完成
            case R.id.tv_order_accomplish:
                state = 8;
                changeViewSelectedState(state);
                break;

            case R.id.tv_order_back:
                state = 6;
                changeViewSelectedState(state);
                break;
            default:
                break;
        }
        pageNum = 1;
        if (selectPosition != state) {
            selectPosition = state;
            requestOnLineNetWork(pageNum);
        } else {
            if (onLineList.size() == 0) {
                tvNoOrder.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 点击时的状态码
     *
     * @param state 状态码
     */
    private void changeViewSelectedState(int state) {
        if (state == Const.INTEGER_DEFAULT) {
            tvAll.setSelected(true);
            tvWaitPay.setSelected(false);
            tvWaitSend.setSelected(false);
            tvWaitRecieve.setSelected(false);
            tvOrderEvalute.setSelected(false);
            tvOrderAccomplish.setSelected(false);
            tvOrderBack.setSelected(false);
        } else if (state == Const.INTEGER_0) {
            tvAll.setSelected(false);
            tvWaitPay.setSelected(true);
            tvWaitSend.setSelected(false);
            tvWaitRecieve.setSelected(false);
            tvOrderEvalute.setSelected(false);
            tvOrderAccomplish.setSelected(false);
            tvOrderBack.setSelected(false);
        } else if (state == Const.INTEGER_2) {
            tvAll.setSelected(false);
            tvWaitPay.setSelected(false);
            tvWaitSend.setSelected(true);
            tvWaitRecieve.setSelected(false);
            tvOrderEvalute.setSelected(false);
            tvOrderAccomplish.setSelected(false);
            tvOrderBack.setSelected(false);
        } else if (state == Const.INTEGER_3) {
            tvAll.setSelected(false);
            tvWaitPay.setSelected(false);
            tvWaitSend.setSelected(false);
            tvWaitRecieve.setSelected(true);
            tvOrderEvalute.setSelected(false);
            tvOrderAccomplish.setSelected(false);
            tvOrderBack.setSelected(false);
        } else if (state == Const.INTEGER_7) {
            tvAll.setSelected(false);
            tvWaitPay.setSelected(false);
            tvWaitSend.setSelected(false);
            tvWaitRecieve.setSelected(false);
            tvOrderEvalute.setSelected(true);
            tvOrderAccomplish.setSelected(false);
            tvOrderBack.setSelected(false);
        } else if (state == Const.INTEGER_8) {
            tvAll.setSelected(false);
            tvWaitPay.setSelected(false);
            tvWaitSend.setSelected(false);
            tvWaitRecieve.setSelected(false);
            tvOrderEvalute.setSelected(false);
            tvOrderAccomplish.setSelected(true);
            tvOrderBack.setSelected(false);
        } else if (state == Const.INTEGER_6) {
            tvAll.setSelected(false);
            tvWaitPay.setSelected(false);
            tvWaitSend.setSelected(false);
            tvWaitRecieve.setSelected(false);
            tvOrderEvalute.setSelected(false);
            tvOrderAccomplish.setSelected(false);
            tvOrderBack.setSelected(true);
        }
    }

    /**
     * 确认收货
     */
    private void requestConfirmReceiveNetWork(String oid) {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "oid", oid,
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "timestamp", timestamp);
        //请求参数：主订单id、会员手机号、uuid
        OkHttpUtils.post().url(Constants.CONFIRM_RECEIVE)
                .addParams("oid", oid)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractCodeMsgCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                showInfoProgressDialog();
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                dismissInfoProgressDialog();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(context.getString(R.string.app_on_request_error_msg));
            }

            @Override
            public void onResponse(CodeMsgBean response, int id) {
                if (response.getCode() == 1) {
                    pageNum = 1;
                    requestOnLineNetWork(pageNum);
                }
                ToastUtils.show(response.getMsg());
            }
        });
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNum = 1;
        requestOnLineNetWork(pageNum);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        pageNum++;
        requestOnLineNetWork(pageNum);
    }

}
