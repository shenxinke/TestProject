package com.yst.onecity.activity.order;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.adapter.AbstractCommonAdapter;
import com.yst.onecity.adapter.CommonViewHolder;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.order.OrderComment;
import com.yst.onecity.callbacks.AbstractOrderCommentCallback;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.ContainsEmojiEditText;
import com.yst.onecity.view.RatingBar;
import com.yst.onecity.view.SoftHideKeyBoardUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 订单评价页面
 *
 * @author WangJingWei
 * @version 4.2.0
 * @date 2018/5/30.
 */
public class OrderCommentActivity extends BaseActivity {
    @BindView(R.id.activity_title_tv)
    TextView activityTitleTv;
    @BindView(R.id.activity_complete_tv)
    TextView activityCompleteTv;
    @BindView(R.id.ll_comment)
    LinearLayout llComment;
    @BindView(R.id.scrollview)
    ScrollView scrollview;

    private AbstractCommonAdapter<OrderComment.CommentBean> adapter;
    private List<OrderComment.CommentBean> mData = new ArrayList<>();
    private String jsonContent;

    private String orderNo;
    private String merchantId;
    /**
     * 0发布评论，1查看评论
     */
    private int flag;


    /**
     * 跳转订单评论页面
     */
    public static void openActivity(Context context, String orderNo,String merchantId,int flag) {
        Bundle bundle = new Bundle();
        bundle.putString("orderNo", orderNo);
        bundle.putString("merchantId",merchantId);
        bundle.putInt("flag",flag);
        JumpIntent.jump((Activity) context, OrderCommentActivity.class, bundle);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_order_comment;
    }

    @Override
    public void initData() {
        initTitle();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            orderNo = bundle.getString("orderNo");
            merchantId = bundle.getString("merchantId");
            flag = bundle.getInt("flag");
        }
        if (flag == 1) {
            activityCompleteTv.setVisibility(View.GONE);
        }

        requestOrderContent();
    }

    @OnClick(R.id.activity_complete_tv)
    public void toPublish() {

        getLocalPublishContent();
        if(mData.size() == 0){
            return;
        }

        jsonContent = toPackageContentJson();

        if(!TextUtils.isEmpty(jsonContent)){
            requestOrderContent();
        }

    }

    private void initTitle() {
        activityTitleTv.setText(getResources().getString(R.string.comment));
        activityCompleteTv.setVisibility(View.VISIBLE);
        activityCompleteTv.setTextSize(Const.FLOAT_13F);
        activityCompleteTv.setText(getResources().getString(R.string.publish));
        SoftHideKeyBoardUtil.assistActivity(this);
    }

    /**
     * 动态显示评论列表数据
     */
    private void showOrderCommentList() {
        adapter = new AbstractCommonAdapter<OrderComment.CommentBean>(this, mData, R.layout.item_order_comment) {
            @Override
            public void convert(CommonViewHolder holder, int position, OrderComment.CommentBean item) {
            }
        };
        llComment.removeAllViews();
        for (int i = 0; i < adapter.getCount(); i++) {
            View view = adapter.getView(i, null, null);
            llComment.addView(view);
        }
    }


    /**
     * 提交发布前，获取数据源
     */
    private void getLocalPublishContent() {
        if (llComment.getChildCount() == 0) {
            return;
        }
        mData.clear();

        for (int i = 0; i < llComment.getChildCount(); i++) {
            OrderComment.CommentBean commentBean = new OrderComment.CommentBean();
            ConstraintLayout mConstraintLayout = (ConstraintLayout) llComment.getChildAt(i);
            /**
             * 商品评价EditText
             */
            ContainsEmojiEditText storeEdittext = (ContainsEmojiEditText) mConstraintLayout.getChildAt(0);
            if (storeEdittext != null) {
                if(!TextUtils.isEmpty(storeEdittext.getText().toString().trim())){
                    commentBean.setStoreDesc(storeEdittext.getText().toString());
                }else{
                    ToastUtils.show("请填写商品评价");
                    return;
                }

            }
            /**
             * 专员评价EditText
             */
            ContainsEmojiEditText userEdittext = (ContainsEmojiEditText) mConstraintLayout.getChildAt(1);
            if (userEdittext != null) {
                if(!TextUtils.isEmpty(userEdittext.getText().toString().trim())){
                    commentBean.setUserDesc(userEdittext.getText().toString());
                }else{
                    ToastUtils.show("请填写专员评价");
                    return;
                }
            }
            /**
             * 商品评价RatingBar
             */
            RatingBar storeRatingBar = (RatingBar) mConstraintLayout.getChildAt(2);
            if (storeRatingBar != null) {
                commentBean.setStoreRating(String.valueOf(storeRatingBar.getCurrentStarNum()));
            }
            /**
             * 专员评价RatingBar
             */
            RatingBar userRatingBar = (RatingBar) mConstraintLayout.getChildAt(3);
            if (userRatingBar != null) {
                commentBean.setUserRating(String.valueOf(userRatingBar.getCurrentStarNum()));
            }

            mData.add(commentBean);
        }

        MyLog.e("sss", "---" + mData.toString());
    }

    /**
     * 拼接发布订单评价参数
     */
    private String toPackageContentJson() {
        JSONObject mJSONObject = null;
        JSONArray contentJSONArray = null;
        try {
            mJSONObject = new JSONObject();

            contentJSONArray = new JSONArray();
            for (int i = 0; i < mData.size(); i++) {
                JSONObject mJSONContent = new JSONObject();
                mJSONContent.put("storeDesc", mData.get(i).getStoreDesc());
                mJSONContent.put("userDesc", mData.get(i).getUserDesc());
                mJSONContent.put("storeRating", mData.get(i).getStoreRating());
                mJSONContent.put("userRating", mData.get(i).getUserRating());
                contentJSONArray.put(mJSONContent);
            }

            mJSONObject.put("content", contentJSONArray);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mJSONObject.toString();
    }

    /**
     * 获取订单评价内容接口
     */
    private void requestOrderContent() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "orderNo", orderNo,
                "merchantId", merchantId,
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "timestamp", timestamp);
        if(TextUtils.isEmpty(sign)){
            return;
        }


        OkHttpUtils.post().url(flag == 1 ? Constants.COMMENT_DETAIL : Constants.COMMENT_LIST)
                .addParams("orderNo", orderNo)
                .addParams("merchantId", merchantId)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractOrderCommentCallback() {

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
                ToastUtils.show(TianyiApplication.getInstance().getString(R.string.str_net_error_message));
            }

            @Override
            public void onResponse(OrderComment response, int id) {

                if (response.getCode() == 1) {
                    if(response.getContent() != null){
                        mData = response.getContent();
                        if(mData.size() > 0){
                            showOrderCommentList();
                        }
                    }
                }else{
                    ToastUtils.show(response.getMsg());
                }
            }
        });
    }

    /**
     * 发布订单评价接口
     */
    private void publishOrderContent() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "json", jsonContent,
                "timestamp", timestamp);

        if (TextUtils.isEmpty(sign)) {
            return;
        }

        OkHttpUtils.post()
                .url(Constants.VIDEO_COMMENT_LIST)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("json", jsonContent)
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build()
                .execute(new StringCallback() {

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
                        ToastUtils.show(TianyiApplication.getInstance().getString(R.string.str_net_error_message));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt(Const.CONS_STR_CODE) == 1) {
                            }else{
                                ToastUtils.show(jsonObject.getString("msg"), Toast.LENGTH_SHORT);
                            }
                        } catch (JSONException e) {
                            ToastUtils.show(getString(R.string.app_on_request_error_msg));
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
