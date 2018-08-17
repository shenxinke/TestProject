package com.yst.onecity.activity.commissioner;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.ProductDetailActivity;
import com.yst.onecity.adapter.ShareDetailsAdapter;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.DetailsBean;
import com.yst.onecity.bean.product.ProductSortStandardBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.fragment.popfragment.AddCartPopFragment;
import com.yst.onecity.interfaces.BaseAdapterListener;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 分享详情
 *
 * @author wuxiaofang
 * @version 4.2.0
 * @date 2018/6/15
 */

public class ShareDetailsActivity extends BaseActivity implements AdapterView.OnItemClickListener, BaseAdapterListener {
    @BindView(R.id.lv_detals)
    ListView lvDetals;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.view2)
    View view2;
    private String id;
    private TextView title;
    private TextView time;
    private TextView lookNum;
    private ShareDetailsAdapter adapter;
    private List<DetailsBean.ContentBean.ConsultationBean.InfoBean> info = new ArrayList<>();
    private int standardId;
    private int classId;
    private String hid;
    private String authorId;
    private int userId;

    @Override
    public int bindLayout() {
        return R.layout.layout_share_details;
    }

    @Override
    public void initData() {
        initTitleBar("分享详情");
        View inflate = LayoutInflater.from(this).inflate(R.layout.layout_share_head, null);
        title = (TextView) inflate.findViewById(R.id.tv_title);
        time = (TextView) inflate.findViewById(R.id.tv_time);
        lookNum = (TextView) inflate.findViewById(R.id.tv_look_num);
        lvDetals.addHeaderView(inflate);
        lvDetals.setOnItemClickListener(this);
        if (null != getIntent() && null != getIntent().getExtras()) {
            Bundle bundle = getIntent().getExtras();
            id = bundle.getString(Const.STR_ID);
            hid = bundle.getString(Const.CONS_STR_HUNTERID);
            authorId = bundle.getString(Const.CONS_STR_AUTHORID);
        }
        adapter = new ShareDetailsAdapter(this, this);
        lvDetals.setAdapter(adapter);
        getDetailsNet();
    }

    /**
     * 获取详情
     */
    private void getDetailsNet() {
        if (TextUtils.isEmpty(id)) {
            ToastUtils.show("id为空");
            return;
        }
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("consultationId", id, "merberId", TianyiApplication.appCommonBean.getId() == null ? "" : TianyiApplication.appCommonBean.getId(), "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.CONSULT_DETAIL)
                .addParams("consultationId", id)
                .addParams("merberId", TianyiApplication.appCommonBean.getId() == null ? "" : TianyiApplication.appCommonBean.getId())
                .addParams("timestamp", timestamp)
                .addParams("sign", sign)
                .addParams("client_type", "A")
                .build().execute(new StringCallback() {

                                     @Override
                                     public void onError(Call call, Exception e, int id) {
                                         ToastUtils.show(getString(R.string.str_net_error_message));
                                         MyLog.e("sss", "--e: " + e.getMessage());
                                     }

                                     @Override
                                     public void onResponse(String response, int id) {
                                         MyLog.e("sss", "00:" + response);
                                         if (null != response) {
                                             DetailsBean bean = new Gson().fromJson(response, DetailsBean.class);
                                             if (null != bean) {
                                                 if (bean.getCode() == Const.INTEGER_1 && null != bean.getContent() && null != bean.getContent().getConsultation()) {
                                                     DetailsBean.ContentBean.ConsultationBean consultation = bean.getContent().getConsultation();
                                                     userId = consultation.getUser_id();
                                                     MyLog.e("sss", "-hid: " + hid + "-ddd: " + userId);
                                                     title.setText(consultation.getTitle());
                                                     String dateToString = ConstUtils.getDateToString(consultation.getCreated_time());
                                                     time.setText(dateToString);
                                                     lookNum.setText(String.valueOf(consultation.getRead_num()) + "人看过");
                                                     info.clear();
                                                     for (int i = 0; i < consultation.getInfo().size(); i++) {
                                                         int cover = consultation.getInfo().get(i).getCover();
                                                         if (cover != Const.INTEGER_1) {
                                                             info.add(consultation.getInfo().get(i));
                                                         }
                                                     }
                                                     adapter.addData(info);
                                                 } else {
                                                     ToastUtils.show(bean.getMsg());
                                                 }
                                             }
                                         }
                                     }
                                 }
        );
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!Utils.isClickable()) {
            return;
        }
        if (info.get(position - 1).getType() == Const.INTEGER_2) {
            Bundle bundle = new Bundle();
            MyLog.e("sss", "--pid: " + (position - 1) + String.valueOf(info.get(position - 1).getProductId()));
            bundle.putString("productId", String.valueOf(info.get(position - 1).getProductId()));
            bundle.putString(Const.CONS_STR_HUNTERID, authorId);
            bundle.putString(Const.CONS_STR_AUTHORID, authorId);
            JumpIntent.jump(this, ProductDetailActivity.class, bundle);
        }
    }

    @Override
    public void onItemClick(int position) {
        getGuige(info.get(position).getProductId(), position);
    }

    /**
     * 获取商品规格
     */
    private void getGuige(final int pId, final int position) {
        final String productId = String.valueOf(pId);
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("timestamp", timestamp,
                "pId", productId, "merchantid", Const.STR1);
        OkHttpUtils.post().url(Constants.GET_PRODUCT_SORT_STANDARD)
                .addParams("timestamp", timestamp)
                .addParams("pId", productId)
                .addParams("merchantid", Const.STR1)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("sss", "-规格：" + response);
                if (response != null) {
                    ProductSortStandardBean standardBean = new Gson().fromJson(response, ProductSortStandardBean.class);
                    if (standardBean.getContent().getStandard().size() > 1) {
                        showAddCartDialog(productId, Const.STR1);
                    } else {
                        standardId = standardBean.getContent().getStandard().get(0).getStandardId();
                        classId = standardBean.getContent().getClassify().get(0).getClassId();
                        addCart(Const.STR1, String.valueOf(standardId), String.valueOf(classId));
                    }
                } else {
                    ToastUtils.show(getString(R.string.tv_check_service));
                }
            }
        });
    }

    /**
     * 显示弹框
     *
     * @param productId
     * @param merchantId
     */
    private void showAddCartDialog(String productId, String merchantId) {
        AddCartPopFragment fragment = AddCartPopFragment.newInstance(productId, merchantId, String.valueOf(userId), String.valueOf(userId), "", "", "", "", "");
        fragment.show(getSupportFragmentManager(), "goodsLIst");
    }

    /**
     * 添加购物车
     */
    private void addCart(String num, String standardId, String classId) {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("timestamp", timestamp,
                "phone", TianyiApplication.appCommonBean.getPhone()
                , "uuid", TianyiApplication.appCommonBean.getUuid(),
                "spid", standardId,
                "stid", classId,
                "authorid", String.valueOf(userId),
                "num", num,
                "hunterid", String.valueOf(userId),
                "timestamp", timestamp
        );
        OkHttpUtils.post().url(Constants.ADD_SHOP_CART)
                .addParams("timestamp", timestamp)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("spid", standardId)
                .addParams("stid", classId)
                .addParams("authorid", String.valueOf(userId))
                .addParams("num", num)
                .addParams("hunterid", String.valueOf(userId))
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.str_net_error_message));
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject obj = new JSONObject(response);
                    int code = obj.getInt("code");
                    if (code == 1) {
                        ToastUtils.show("成功加入购物车");
                    } else {
                        ToastUtils.show(obj.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}