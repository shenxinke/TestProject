package com.yst.onecity.activity.member;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tencent.TIMConversationType;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.chat.ChatActivity;
import com.yst.onecity.adapter.OrderDetailAdapter;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.CodeMsgBean;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.InformationBean;
import com.yst.onecity.bean.order.OnLineOrderDetailBean;
import com.yst.onecity.bean.order.OrderDetailBean;
import com.yst.onecity.bean.order.OrderProduct;
import com.yst.onecity.bean.order.OrderRewardBean;
import com.yst.onecity.callbacks.AbstractCodeMsgCallback;
import com.yst.onecity.callbacks.AbstractOnLineDetailsCallBack;
import com.yst.onecity.callbacks.AbstractOrderRewardCallBack;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.MyExpandableListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 订单详情
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/12/18
 */
public class OrderDetailActivity extends BaseActivity {

    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_tel)
    TextView tvUserTel;
    @BindView(R.id.tv_user_address)
    TextView tvUserAddress;
    @BindView(R.id.el_order_list)
    MyExpandableListView elOrderList;
    @BindView(R.id.tv_payment_price)
    TextView tvPaymentPrice;
    @BindView(R.id.tv_total_number)
    TextView tvTotalNumber;
    @BindView(R.id.tv_reward)
    TextView tvReward;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_order_create)
    TextView tvOrderCreate;
    @BindView(R.id.tv_order_payment)
    TextView tvOrderPayment;
    @BindView(R.id.tv_order_send)
    TextView tvOrderSend;
    @BindView(R.id.tv_order_receipt)
    TextView tvOrderReceipt;
    @BindView(R.id.tv_bt_state)
    TextView tvBtState;
    @BindView(R.id.rl_pay_time)
    RelativeLayout rlPayTime;
    @BindView(R.id.rl_send_time)
    RelativeLayout rlSendTime;
    @BindView(R.id.rl_receive_time)
    RelativeLayout rlReceiveTime;
    @BindView(R.id.rl_complete_time)
    RelativeLayout rlCompleteTime;
    @BindView(R.id.tv_order_state_name)
    TextView tvOrderStateName;
    @BindView(R.id.tv_order_complete)
    TextView tvOrderComplete;
    @BindView(R.id.rl_bottom_status)
    RelativeLayout rlBottomStatus;
    @BindView(R.id.ll_reward)
    LinearLayout llReward;
    @BindView(R.id.tv_everyday_reward)
    TextView tvEveryDarReward;
    @BindView(R.id.tv_open_red_package)
    TextView tvOpenRedReward;
    @BindView(R.id.iv_reward)
    ImageView ivReward;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.information)
    RelativeLayout information;
    @BindView(R.id.rl_leavemessage)
    RelativeLayout rlLeaveMessage;
    @BindView(R.id.rl_order_cause)
    ConstraintLayout rlOrderCause;
    @BindView(R.id.immediate_click)
    TextView immediateClick;
    @BindView(R.id.tv_check_evalute)
    TextView tvCheckEvalute;
    @BindView(R.id.ll_contacts)
    LinearLayout llContacts;
    @BindView(R.id.invoice_id)
    TextView tvInvoiceId;
    @BindView(R.id.rl_contacts)
    RelativeLayout rlContacts;
    @BindView(R.id.tv_invoiceType)
    TextView tvInvoiceType;
    @BindView(R.id.tv_invoice_head)
    TextView tvInvoiceHead;
    @BindView(R.id.tv_invoice_price)
    TextView tvInvoicePrice;
    @BindView(R.id.tv_width)
    TextView tvWidth;
    @BindView(R.id.tv_invoice_money)
    TextView tvInvoiceMoney;
    @BindView(R.id.tv_user_location)
    TextView tvUserLocation;
    @BindView(R.id.ll_invoice_4)
    LinearLayout llInvoice;
    @BindView(R.id.tv_payment_score)
    TextView tvPaymentScore;
    @BindView(R.id.tv_invoice_score)
    TextView tvInvoiceScore;
    @BindView(R.id.txt_order_message)
    TextView txtOrderMessage;
    @BindView(R.id.tv_message)
    TextView txtMessage;
    @BindView(R.id.tv_order_cause)
    TextView txtOrderCause;
    @BindView(R.id.view_order)
    View viewOrder;

    private OrderDetailAdapter adapter;
    private String oid;
    private String merchantId;
    private String orderNo;
    private int status;
    private String id;
    private List<OrderProduct> groupList = new ArrayList<>();
    private String payType;
    private int commentStatus;

    @Override
    public int bindLayout() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initData() {
        initTitleBar("订单详情");
        EventBus.getDefault().register(this);
        ConstUtils.disableAutoScrollToBottom(scrollView);
        oid = getIntent().getExtras().getString("oid");
        id = getIntent().getExtras().getString("id");
        merchantId = getIntent().getExtras().getString("merchantId");
        orderNo = getIntent().getExtras().getString("orderNo");
        status = getIntent().getExtras().getInt("state");
        payType = getIntent().getExtras().getString("payType");
        commentStatus = getIntent().getExtras().getInt("commentStatus");

    }

    @Override
    protected void onResume() {
        super.onResume();
        requestOrderDetailsNetWork();
        /**
         * 条目不可折叠
         */
        elOrderList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });
    }

    /**
     * 获取订单详情
     */
    private void requestOrderDetailsNetWork() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "orderNo", orderNo,
                "merchantId", merchantId,
                "status", String.valueOf(status),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "timestamp", timestamp);
        //请求参数：主订单id、会员手机号、uuid
        OkHttpUtils.post().url(Constants.MEMBER_ONLINE_ORDER_DETAIL)
                .addParams("orderNo", orderNo)
                .addParams("merchantId", merchantId)
                .addParams("status", String.valueOf(status))
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractOnLineDetailsCallBack() {
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
                ToastUtils.show(getString(R.string.app_on_request_error_msg));
            }

            @Override
            public void onResponse(OnLineOrderDetailBean response, int id) {
                if (response.getCode() == 1) {
                    setOrderInfo(response.getContent());
                    groupList = response.getContent().getMerchantList();
                    int scoreStatus = response.getContent().getScoreStatus();
                    String payType = response.getContent().getPayType();
                    if (groupList != null) {
                        initCtrl(groupList, scoreStatus, payType);
                    }
                } else {
                    ToastUtils.show(response.getMsg());
                }
            }
        });
    }

    private void initCtrl(List<OrderProduct> groupList, int scoreStatus, String payType) {
        adapter = new OrderDetailAdapter(groupList, status, orderNo, this, merchantId, scoreStatus, payType, status);
        elOrderList.setAdapter(adapter);
        final OrderProduct groupBean = groupList.get(0);
        //立即找TA 跳转
        immediateClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatActivity.navToChat(OrderDetailActivity.this, groupBean.gethImId(),
                        groupBean.gethId(),
                        TIMConversationType.C2C);
            }
        });
        //查看评价 跳转
        tvCheckEvalute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("orderNo", orderNo);
                bundle.putString("merchantId", merchantId);
                //0 发布 1 查看
                bundle.putInt("flag", commentStatus);
                JumpIntent.jump(OrderDetailActivity.this, OrderEvaluteActivity.class, bundle);
            }
        });
        //当status=4是 显示评价按钮
        if (status == Const.INTEGER_4) {
            tvCheckEvalute.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < groupList.size(); i++) {
            elOrderList.expandGroup(i);
        }
    }

    private void setOrderInfo(final OrderDetailBean content) {
        //0天天奖
        if (Const.STR0.equals(content.getFenRunType())) {
            ivReward.setVisibility(View.GONE);
            ivReward.setImageResource(R.mipmap.tiantianjiang);
            llReward.setVisibility(View.GONE);
            tvEveryDarReward.setBackgroundColor(0xFFECECEC);
            tvEveryDarReward.setTextColor(0xFFCCCCCC);
            tvEveryDarReward.setEnabled(false);
            tvOpenRedReward.setBackgroundColor(0xFFECECEC);
            tvOpenRedReward.setTextColor(0xFFCCCCCC);
            tvOpenRedReward.setEnabled(false);
            viewLine.setVisibility(View.GONE);
            rlBottomStatus.setVisibility(View.GONE);
        } else if (Const.STR1.equals(content.getFenRunType())) {
            ivReward.setVisibility(View.GONE);
            ivReward.setImageResource(R.mipmap.red_package);
            llReward.setVisibility(View.GONE);
            tvEveryDarReward.setBackgroundColor(0xFFECECEC);
            tvEveryDarReward.setTextColor(0xFFCCCCCC);
            tvEveryDarReward.setEnabled(false);
            tvOpenRedReward.setBackgroundColor(0xFFECECEC);
            tvOpenRedReward.setTextColor(0xFFCCCCCC);
            tvOpenRedReward.setEnabled(false);
            viewLine.setVisibility(View.GONE);
            rlBottomStatus.setVisibility(View.GONE);
        } else { //是空说明没开过
            if (content.getFlag() == 1) {
                llReward.setVisibility(View.GONE);
                rlBottomStatus.setVisibility(View.GONE);
                tvEveryDarReward.setBackgroundResource(R.drawable.shape_gradient_btn);
                tvEveryDarReward.setTextColor(0xFFFFFFFF);
                tvEveryDarReward.setEnabled(true);
                tvOpenRedReward.setBackgroundColor(0xFFFF763F);
                tvOpenRedReward.setTextColor(0xFFFFFFFF);
                tvOpenRedReward.setEnabled(true);
                viewLine.setVisibility(View.GONE);
            } else {
                llReward.setVisibility(View.GONE);
            }
        }
        // 	订单状态码0待付款 2待发货 3待收货 8已完成 6已撤销 7待评价
        if (status == 0) {
            tvOrderStatus.setText("订单状态：待付款");
            rlBottomStatus.setVisibility(View.VISIBLE);
            tvBtState.setText("付\t\t款");
            // 0 是新业态  1不是新业态
            if (content.getIsXYT() == Const.INTEGER_1) {
                Const.ISNEWYETAI = Const.INTEGER_1;
            } else {
                //0 显示积分 1不显示积分
                if (content.getScoreStatus() == Const.INTEGER_0) {
                    Const.ISNEWYETAI = Const.INTEGER_0;
                } else {
                    Const.ISNEWYETAI = Const.INTEGER_1;
                }
            }
        } else if (status == Const.INTEGER_2) {
            tvOrderStatus.setText("订单状态：待发货");
            rlPayTime.setVisibility(View.VISIBLE);

        } else if (status == Const.INTEGER_3) {
            getLogisticsInformation(orderNo, content);
            tvOrderStatus.setText("订单状态：待收货");
            rlBottomStatus.setVisibility(View.VISIBLE);
            tvBtState.setText("确认收货");
            information.setVisibility(View.VISIBLE);
            tvBtState.setVisibility(View.VISIBLE);
            rlPayTime.setVisibility(View.VISIBLE);
            rlSendTime.setVisibility(View.VISIBLE);

        } else if (status == Const.INTEGER_8) {
            getLogisticsInformation(orderNo, content);
            tvOrderStatus.setText("订单状态：已完成");
            //0 发布 1 查看
            if (commentStatus == Const.INTEGER_0) {
                tvBtState.setText("评\t\t价");
            } else {
                tvBtState.setText("查看评价");
            }
            rlBottomStatus.setVisibility(View.VISIBLE);
            information.setVisibility(View.VISIBLE);
            rlPayTime.setVisibility(View.VISIBLE);
            rlSendTime.setVisibility(View.VISIBLE);
            rlReceiveTime.setVisibility(View.VISIBLE);
            rlCompleteTime.setVisibility(View.VISIBLE);
            tvOrderComplete.setText(ConstUtils.getStringNoEmpty(content.getFinishTime()));

        } else if (status == Const.INTEGER_6) {
            tvOrderStatus.setText("订单状态：已撤销");
            rlPayTime.setVisibility(View.VISIBLE);
            rlCompleteTime.setVisibility(View.VISIBLE);
            rlOrderCause.setVisibility(View.VISIBLE);
            tvOrderStateName.setText("订单撤销时间");
            tvOrderComplete.setText(ConstUtils.getStringNoEmpty(content.getCancelTime()));
            txtOrderCause.setText(ConstUtils.getStringNoEmpty(content.getRefundMsg()));
        } else if (status == Const.INTEGER_7) {
            getLogisticsInformation(orderNo, content);
            tvOrderStatus.setText("订单状态：已收货");
            information.setVisibility(View.VISIBLE);
            //0 发布 1 查看
            if (commentStatus == Const.INTEGER_0) {
                tvBtState.setText("评\t\t价");
            } else {
                tvBtState.setText("查看评价");
            }
            tvBtState.setVisibility(View.VISIBLE);
            rlBottomStatus.setVisibility(View.VISIBLE);
            rlPayTime.setVisibility(View.VISIBLE);
            rlSendTime.setVisibility(View.VISIBLE);
            rlReceiveTime.setVisibility(View.VISIBLE);

        }
        //判断发票类型 1公司 0个人
        String invoiceType = ConstUtils.getStringNoEmpty(content.getInvoiceType());
        if (invoiceType.equals(String.valueOf(Const.INTEGER_1))) {
            tvInvoiceType.setText("公司发票");
            tvInvoiceId.setText(content.getInvoiceMark());
        } else if (invoiceType.equals(String.valueOf(Const.INTEGER_0))) {
            tvInvoiceType.setText("个人发票");
            llInvoice.setVisibility(View.GONE);
        } else {
            rlContacts.setVisibility(View.GONE);
        }
        //判断留言是否为空
        if(TextUtils.isEmpty(content.getMessage())){
            rlLeaveMessage.setVisibility(View.GONE);
        } else {
            rlLeaveMessage.setVisibility(View.VISIBLE);
            txtMessage.setText(content.getMessage());
        }
        tvInvoiceMoney.setText("¥" + content.getFreightFee());
        tvWidth.setText(content.getTotalWeight() + "kg");
        tvInvoicePrice.setText("¥" + content.getTotalOrderPrice());
        tvInvoiceHead.setText(content.getInvoiceName() + "");
        tvUserName.setText("收货人:" + ConstUtils.getStringNoEmpty(content.getReceiveName()));
        tvUserTel.setText(ConstUtils.getStringNoEmpty(content.getReceivePhone()));
        tvUserAddress.setText("收货地址:" + ConstUtils.getStringNoEmpty(content.getReceiveAddress()));
        tvPaymentPrice.setText("¥" + content.getTotalPrice());
        tvOrderNumber.setText(ConstUtils.getStringNoEmpty(content.getOrderNo()));
        tvOrderCreate.setText(ConstUtils.getStringNoEmpty(content.getCreateTime()));
        tvOrderPayment.setText(ConstUtils.getStringNoEmpty(content.getPayTime()));
        tvOrderSend.setText(ConstUtils.getStringNoEmpty(content.getSendTime()));
        tvOrderReceipt.setText(ConstUtils.getStringNoEmpty(content.getReceiveTime()));

        //天天奖
        tvEveryDarReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRedPackage();
            }
        });
        //开红包
        tvOpenRedReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                JumpIntent.jump(OrderDetailActivity.this, RedPackageActivity.class, bundle);
            }
        });
        //待付款
        tvBtState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status == 0) {
                    Bundle bundle = new Bundle();
                    StringBuilder orderIds = new StringBuilder();
                    for (int i = 0; i < groupList.size(); i++) {
                        List<OrderProduct.ProductDetailBean> products = groupList.get(i).getpList();
                        if (groupList.get(i).getpList() != null) {
                            for (int j = 0; j < products.size(); j++) {
                                orderIds.append(products.get(j).getoId());
                                if (j != products.size() - 1) {
                                    orderIds.append(",");
                                }
                            }
                        }
                    }
                    bundle.putString("orderIds", orderIds.toString());
                    bundle.putString("price", String.valueOf(content.getTotalPrice()));
                    bundle.putString("score", String.valueOf(content.getSumScore()));
                    JumpIntent.jump(OrderDetailActivity.this, PayCenterActivity.class, bundle);
                    //待发货、待收货
                } else if (status == Const.INTEGER_3) {
                    requestConfirmReceiveNetWork();
                    //已撤销
                } else if (status == Const.INTEGER_6) {
                    tvBtState.setEnabled(false);
                    tvBtState.setBackgroundResource(R.drawable.shape_order_gray);
                    //已收货(评价)
                } else if (status == Const.INTEGER_7) {
                    Bundle bundle = new Bundle();
                    bundle.putString("orderNo", content.getOrderNo());
                    bundle.putString("merchantId", merchantId);
                    //0查看，1发布评论
                    bundle.putInt("flag", commentStatus);
                    JumpIntent.jump(OrderDetailActivity.this, OrderEvaluteActivity.class, bundle);
                    //完成(评价)
                } else if (status == Const.INTEGER_8) {
                    Bundle bundle = new Bundle();
                    bundle.putString("orderNo", content.getOrderNo());
                    bundle.putString("merchantId", merchantId);
                    //0查看，1发布评论
                    bundle.putInt("flag", commentStatus);
                    JumpIntent.jump(OrderDetailActivity.this, OrderEvaluteActivity.class, bundle);
                }
            }
        });
        /**
         * 判断是否是代付款状态
         * 判断是否是新业态用户
         * 判断后台积分按钮是否打开
         * 判断是否是积分支付
         *
         * 0 是新业态  1不是新业态
         * 0是显示积分  1是不显示积分
         */
        if (status == Const.INTEGER_0) {
            if (content.getIsXYT() == Const.INTEGER_1) {
                tvPaymentScore.setVisibility(View.GONE);
                tvInvoiceScore.setVisibility(View.GONE);
            } else {
                if (content.getScoreStatus() == Const.INTEGER_0) {
                    tvPaymentScore.setVisibility(View.VISIBLE);
                    tvInvoiceScore.setVisibility(View.VISIBLE);
                    tvPaymentScore.setText("或" + content.getSumScore() + "积分");
                    tvInvoiceScore.setText("或" + content.getTotalScorePrice() + "积分");
                } else {
                    tvPaymentScore.setVisibility(View.GONE);
                    tvInvoiceScore.setVisibility(View.GONE);
                }
            }
        } else {
            if (content.getIsXYT() == Const.INTEGER_1) {
                tvPaymentScore.setVisibility(View.GONE);
                tvInvoiceScore.setVisibility(View.GONE);
            } else {
                if (Const.CONS_STR_JIFENZHIFU.equals(payType)) {
                    tvPaymentScore.setVisibility(View.VISIBLE);
                    tvInvoiceScore.setVisibility(View.VISIBLE);
                    tvPaymentScore.setText(content.getSumScore() + "积分");
                    tvInvoiceScore.setText(content.getTotalScorePrice() + "积分");
                    tvInvoicePrice.setVisibility(View.GONE);
                    tvPaymentPrice.setVisibility(View.GONE);
                } else {
                    tvPaymentPrice.setVisibility(View.VISIBLE);
                    tvInvoicePrice.setVisibility(View.VISIBLE);
                    tvPaymentScore.setVisibility(View.GONE);
                    tvInvoiceScore.setVisibility(View.GONE);
                }
            }
        }
    }

    /**
     * 开红包
     */
    private void openRedPackage() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "id", id,
                "type", "0",
                "memberId", TianyiApplication.appCommonBean.getId(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.ORDER_REWARD)
                .addParams("id", id)
                //1开红包，0天天返
                .addParams("type", "0")
                .addParams("memberId", TianyiApplication.appCommonBean.getId())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractOrderRewardCallBack() {
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
                ToastUtils.show(getString(R.string.app_on_request_error_msg));
            }

            @Override
            public void onResponse(OrderRewardBean response, int id) {
                requestOrderDetailsNetWork();
                ToastUtils.show(response.getMsg());
            }
        });
    }

    /**
     * 确认收货
     */
    private void requestConfirmReceiveNetWork() {
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
                ToastUtils.show(getString(R.string.app_on_request_error_msg));
            }

            @Override
            public void onResponse(CodeMsgBean response, int id) {
                ToastUtils.show(response.getMsg());
                if (response.getCode() == 1) {
                    OrderDetailActivity.this.finish();
                }
            }
        });
    }

    @OnClick(R.id.tv_ordernumber_copy)
    public void onViewClicked(View view) {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(tvOrderNumber.getText());
        ToastUtils.show("复制成功");
    }

    @Subscribe
    public void onEventMainThread(EventBean bean) {
        if (Const.STR1.equals(bean.getMsg())) {
            OrderDetailActivity.this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 物流信息
     */
    private void getLogisticsInformation(String orderNo, final OrderDetailBean content) {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "orderNo", orderNo,
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.LOGISTICSINFORMATION)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("orderNo", orderNo)
                .addParams("timestamp", timestamp)
                .addParams("sign", sign)
                .addParams("client_type", "A")
                .build().execute(new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                showInfoProgressDialog();
            }

            @Override
            public void onAfter(int id) {
                dismissInfoProgressDialog();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(R.string.app_on_request_error_msg, Toast.LENGTH_SHORT);
            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("register", "getcode__onResponse___" + response);
                Gson gson = new Gson();
                InformationBean informationBean = gson.fromJson(response, InformationBean.class);
                int code = informationBean.getCode();
                if (code == 1) {
                    information.setVisibility(View.VISIBLE);
                    InformationBean.ContentBean contentBean = informationBean.getContent();
                    if (contentBean != null) {
                        if (Const.CONS_STR_ZANWUJILU.equals(contentBean.getStateValue())) {
                            tvUserLocation.setText(Utils.getStringNoEmpty(contentBean.getStateValue()));
                        } else {
                            if (contentBean.getTelPhone() == null && contentBean.getOrderNum() == null) {
                                tvUserLocation.setText( Const.CONS_STR_NULL);
                            } else {
                                tvUserLocation.setText(Utils.getStringNoEmpty(contentBean.getLogisticMessage().get(0).getContext()));
                                information.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent();
                                        intent.putExtra("phone", ConstUtils.getStringNoEmpty(content.getOrderNo()));
                                        intent.putExtra("image", content.getMerchantList().get(0).getpList().get(0).getpImg());
                                        intent.setClass(OrderDetailActivity.this, InformationAcitivity.class);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                    }

                } else {
                    information.setVisibility(View.VISIBLE);
                    tvUserLocation.setText("暂无物流信息");
                }
            }
        });
    }
}
