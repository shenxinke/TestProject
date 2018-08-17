package com.yst.onecity.activity.servermember;

import android.content.ClipboardManager;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.adapter.AbstractCommonAdapter;
import com.yst.onecity.adapter.CommonViewHolder;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.order.MemberOffLineDetailsBean;
import com.yst.onecity.bean.order.UnLineOrderBean;
import com.yst.onecity.callbacks.AbstractMemberOffLineDetailsCallBack;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.MyListView;
import com.yst.onecity.view.roundedimageview.RoundedImageView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 服务专员线下订单详情
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/12/18
 */
public class ServiceOffLineOrderDetailActivity extends BaseActivity {

    @BindView(R.id.iv_head)
    RoundedImageView ivHead;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.listView)
    MyListView listView;
    @BindView(R.id.tv_order_creat)
    TextView tvOrderCreat;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_order_price)
    TextView tvOrderPrice;
    @BindView(R.id.tv_order_reward)
    TextView tvOrderReward;
    private List<UnLineOrderBean> data = new ArrayList<>();
    private AbstractCommonAdapter<UnLineOrderBean> adapter;

    @Override
    public int bindLayout() {
        return R.layout.activity_offline_order_detail;
    }

    @Override
    public void initData() {
        initTitleBar("订单详情");
        requestOrderDetailsNetWork(getIntent().getExtras().getString("oid"));
    }

    /**
     * 获取服务专员线下订单详情
     */
    private void requestOrderDetailsNetWork(String oid) {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "oid", oid,
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "timestamp", timestamp);
        //请求参数：主订单id、会员手机号、uuid
        OkHttpUtils.post().url(Constants.MEMBER_UNLINE_ORDER_DETAIL)
                .addParams("oid", oid)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractMemberOffLineDetailsCallBack() {
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

            }

            @Override
            public void onResponse(MemberOffLineDetailsBean response, int id) {
                if (response.getCode() == 1) {
                    MemberOffLineDetailsBean.MemberOffLineDetailBean content = response.getContent();
                    tvShopName.setText(ConstUtils.getStringNoEmpty(content.getPhone()));
                    tvOrderCreat.setText(ConstUtils.getStringNoEmpty(content.getCreated_time()));
                    tvOrderNumber.setText(ConstUtils.getStringNoEmpty(content.getOrder_no()));
                    tvOrderPrice.setText("¥" + content.getTotalMoney());
                    Glide.with(ServiceOffLineOrderDetailActivity.this).load(Constants.URL_IMAGE_HEAD + content.getAddress())
                            .placeholder(R.mipmap.head_2).error(R.mipmap.head_2).into(ivHead);
                    tvOrderReward.setText("¥" + content.getTotalScore());
                    if (content.getSon() != null) {
                        data = content.getSon();
                        initCtrl();
                    }
                } else {
                    ToastUtils.show(response.getMsg());
                }
            }
        });
    }

    /**
     * 刷新适配器
     */
    public void initCtrl() {
        adapter = new AbstractCommonAdapter<UnLineOrderBean>(context, data, R.layout.item_offline_order_details) {
            @Override
            public void convert(CommonViewHolder holder, int position, UnLineOrderBean item) {
                holder.setText(R.id.tv_goods_name, ConstUtils.getStringNoEmpty(item.getName()));
                holder.setText(R.id.tv_goods_money, "¥" + item.getPrice());
                TextView tvGoodsNumber = holder.getView(R.id.tv_goods_number);
                tvGoodsNumber.setGravity(Gravity.LEFT);
                tvGoodsNumber.setText(ConstUtils.getStringNoEmpty(item.getContent()));
                holder.setText(R.id.tv_goods_color, "x" + item.getNum());
                TextView tvIntegral = holder.getView(R.id.tv_integral);
                tvIntegral.setTextColor(0xFFFFCE1C);
                tvIntegral.setText("分润金额：" + item.getScorePrice());
            }
        };
        listView.setAdapter(adapter);
    }

    @OnClick({R.id.tv_ordernumber_copy})
    public void onViewClicked() {//复制订单号
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(tvOrderNumber.getText());
        ToastUtils.show("复制成功");
    }
}
