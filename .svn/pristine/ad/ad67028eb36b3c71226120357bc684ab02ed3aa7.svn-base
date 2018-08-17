package com.yst.onecity.activity.member;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.adapter.OrderEvaluteAdapter;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.CodeMsgBean;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.OrderEvaluteBean;
import com.yst.onecity.bean.order.EvaluteListBean;
import com.yst.onecity.callbacks.AbstractCodeMsgCallback;
import com.yst.onecity.callbacks.AbstractOrderEvaluteCallback;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.MyListView;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 评价
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/12/18
 */
public class OrderEvaluteActivity extends BaseActivity implements OrderEvaluteAdapter.EvalueLitener {

    @BindView(R.id.listView)
    MyListView listView;
    @BindView(R.id.tv_right)
    TextView tvRight;
    private List<OrderEvaluteBean> data = new ArrayList<>();
    private OrderEvaluteAdapter adapter;
    private List<OrderEvaluteBean> publishList = new ArrayList<>();
    private String merchantId;
    private String orderNo;
    /**
     * 0未发布评论，1已评论，查看评价
     */
    private int flag;

    @Override
    public int bindLayout() {
        return R.layout.activity_order_evalute;
    }

    @Override
    public void initData() {
        initTitleBar("评价");
        setRightTextVisibility(View.VISIBLE);
        setRightText("发布");
        orderNo = getIntent().getExtras().getString("orderNo");
        merchantId = getIntent().getExtras().getString("merchantId");
        flag = getIntent().getExtras().getInt("flag");
        if (flag == 1) {
            tvRight.setVisibility(View.GONE);
        }
        requestEvaluteListNetWork();
    }

    private void requestEvaluteListNetWork() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "orderNo", orderNo,
                "merchantId", merchantId,
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "timestamp", timestamp);
        //请求参数：订单号、会员手机号、uuid
        OkHttpUtils.post().url(flag == 0 ?  Constants.COMMENT_LIST :Constants.COMMENT_DETAIL)
                .addParams("orderNo", orderNo)
                .addParams("merchantId", merchantId)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractOrderEvaluteCallback() {
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
            public void onResponse(EvaluteListBean response, int id) {
                if (response.getCode() == 1 && response.getContent() != null) {
                    if(response.getContent().getpList() != null){
                        data.addAll(response.getContent().getpList());
                    }

                    if(response.getContent().gethList() != null){
                        data.addAll(response.getContent().gethList());
                    }

                    initControll(data);
                } else {
                    ToastUtils.show(response.getMsg());
                }
            }
        });
    }

    /**
     * 初始化适配器
     * @param data
     */
    public void initControll(List<OrderEvaluteBean> data) {
        adapter = new OrderEvaluteAdapter(context, data, flag, this);
        listView.setAdapter(adapter);
    }

    @Override
    public void getEvaluteData(List<OrderEvaluteBean> list) {
        publishList = list;
    }

    @OnClick(R.id.tv_right)
    public void onViewClicked() { //发布评价
        if(Utils.isClickable()){
            requestEvaluteNetWork();
        }
    }

    /**
     * 发布评价
     */
    private void requestEvaluteNetWork() {
        for (int i = 0; i < publishList.size(); i++) {
            if (TextUtils.isEmpty(publishList.get(i).getComment()) || publishList.get(i).getStart() == 0) {
                ToastUtils.show("请完善评论内容");
                return;
            }
        }
        String json = new Gson().toJson(publishList);
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "params", json,
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "timestamp", timestamp);
        //请求参数：评价内容json、会员手机号、uuid
        OkHttpUtils.post().url(Constants.ORDER_COMMEND)
                //commentType=1评论商品，hid可不传、2评论服务专员，oid可不传
                .addParams("params", json)
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
                if (response.getCode() == 1) {
                    OrderEvaluteActivity.this.finish();
                    EventBean bean = new EventBean("1", "", "", "", "");
                    EventBus.getDefault().post(bean);
                }
                ToastUtils.show(response.getMsg());
            }
        });
    }
}
