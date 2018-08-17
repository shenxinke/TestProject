package com.yst.onecity.fragment;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewDebug;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.adapter.AbstractCommonAdapter;
import com.yst.onecity.adapter.CommonViewHolder;
import com.yst.onecity.adapter.ShoppingEvaluateAdapter;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.bean.product.ProductCommentListBean;
import com.yst.onecity.callbacks.AbstractProductCommentCallBack;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.RatingBar;
import com.yst.onecity.view.roundedimageview.RoundedImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 订单列表页面
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class ProductCommentsFragment extends BaseFragment implements OnRefreshLoadmoreListener {
    @BindView(R.id.comment_all_tv)
    TextView commentAllTv;
    @BindView(R.id.comment_nice_tv)
    TextView commentNiceTv;
    @BindView(R.id.commentnum_tv)
    TextView commentnumTv;
    @BindView(R.id.commentbad_tv)
    TextView commentbadTv;
    @BindView(R.id.img_no_commont)
    ImageView imgNoCommont;
    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.fragment_user_appraise_xlistview)
    ListView fragmentUserAppraiseXlistview;
    @BindView(R.id.tv_allComment)
    TextView tvAllComment;
    @BindView(R.id.tv_feedback)
    TextView tvFeedback;
    Unbinder unbinder;

    private int page = 1;
    private String rows = "10";
    private String type = "-1";
    private String productId;
    private List<ProductCommentListBean.ContentBean.ContentListBean> comments = new ArrayList<>();
    private ShoppingEvaluateAdapter shoppingEvaluateAdapter;

    @Override
    public int bindLayout() {
        return R.layout.fragment_product_comments;
    }

    @Override
    public void initData() {

        if (getArguments() != null) {
            productId = getArguments().getString("productId");
        }

        mSmartRefreshLayout.setOnRefreshLoadmoreListener(this);

        initCommentTab();
    }


    /**
     * 初始化评论Tab
     */
    private void initCommentTab() {
        commentAllTv.setBackgroundResource(R.drawable.appraise_all_bg);
        commentAllTv.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        commentNiceTv.setBackgroundResource(R.drawable.appraise_bg);
        commentNiceTv.setTextColor(ContextCompat.getColor(context, R.color.app_default_color));
        commentnumTv.setBackgroundResource(R.drawable.appraise_bg);
        commentnumTv.setTextColor(ContextCompat.getColor(context, R.color.app_default_color));
        commentbadTv.setBackgroundResource(R.drawable.appraise_bg);
        commentbadTv.setTextColor(ContextCompat.getColor(context, R.color.app_default_color));
    }

    @Override
    public void onResume() {
        comments.clear();
        page = 1;
        getCommentList();
        super.onResume();
    }

    @OnClick({R.id.comment_all_tv, R.id.comment_nice_tv, R.id.commentnum_tv, R.id.commentbad_tv})
    public void getComment(TextView textView) {
        switch (textView.getId()) {
            case R.id.comment_all_tv:
                type = "-1";
                initCommentTab();
                break;
            case R.id.comment_nice_tv:
                type = "1";
                commentAllTv.setBackgroundResource(R.drawable.appraise_bg);
                commentAllTv.setTextColor(ContextCompat.getColor(context, R.color.app_default_color));
                commentNiceTv.setBackgroundResource(R.drawable.appraise_all_bg);
                commentNiceTv.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
                commentnumTv.setBackgroundResource(R.drawable.appraise_bg);
                commentnumTv.setTextColor(ContextCompat.getColor(context, R.color.app_default_color));
                commentbadTv.setBackgroundResource(R.drawable.appraise_bg);
                commentbadTv.setTextColor(ContextCompat.getColor(context, R.color.app_default_color));
                break;
            case R.id.commentnum_tv:
                type = "2";
                commentAllTv.setBackgroundResource(R.drawable.appraise_bg);
                commentAllTv.setTextColor(ContextCompat.getColor(context, R.color.app_default_color));
                commentNiceTv.setBackgroundResource(R.drawable.appraise_bg);
                commentNiceTv.setTextColor(ContextCompat.getColor(context, R.color.app_default_color));
                commentnumTv.setBackgroundResource(R.drawable.appraise_all_bg);
                commentnumTv.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
                commentbadTv.setBackgroundResource(R.drawable.appraise_bg);
                commentbadTv.setTextColor(ContextCompat.getColor(context, R.color.app_default_color));
                break;
            case R.id.commentbad_tv:
                type = "3";
                commentAllTv.setBackgroundResource(R.drawable.appraise_bg);
                commentAllTv.setTextColor(ContextCompat.getColor(context, R.color.app_default_color));
                commentNiceTv.setBackgroundResource(R.drawable.appraise_bg);
                commentNiceTv.setTextColor(ContextCompat.getColor(context, R.color.app_default_color));
                commentnumTv.setBackgroundResource(R.drawable.appraise_bg);
                commentnumTv.setTextColor(ContextCompat.getColor(context, R.color.app_default_color));
                commentbadTv.setBackgroundResource(R.drawable.appraise_all_bg);
                commentbadTv.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
                break;
            default:
                break;
        }
        page = 1;
        comments.clear();
        getCommentList();
    }

    /**
     * 获取评论列表
     */
    private void getCommentList() {

        String timeStamp = SignUtils.getTimeStamp();
        String[] params;
        String sign = null;
        PostFormBuilder post = OkHttpUtils.post();

        if (TianyiApplication.isLogin) {
            params = new String[]{"uuid", TianyiApplication.appCommonBean.getUuid(),
                    "phone", TianyiApplication.appCommonBean.getPhone(), "id", productId,
                    "page", String.valueOf(page), "rows", rows, "type", type, "timestamp", timeStamp};
            sign = Utils.getSign(params);
            if (TextUtils.isEmpty(sign)) {
                return;
            }
            post.url(Constants.PRODUCT_COMMENT_LOGIN);
            post.addParams("uuid", TianyiApplication.appCommonBean.getUuid());
            post.addParams("phone", TianyiApplication.appCommonBean.getPhone());

        } else {
            params = new String[]{"id", productId,
                    "page", String.valueOf(page), "rows", rows, "type", type, "timestamp", timeStamp};
            sign = Utils.getSign(params);
            if (TextUtils.isEmpty(sign)) {
                return;
            }
            post.url(Constants.PRODUCT_COMMENT_UNLOGIN);
        }

        post.addParams("id", productId);
        post.addParams("page", page + "");
        post.addParams("rows", rows);
        post.addParams("type", type);
        post.addParams("sign", sign);
        post.addParams("timestamp", timeStamp);
        post.addParams("client_type", "A");
        post.build().execute(new AbstractProductCommentCallBack() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(TianyiApplication.getInstance().getResources().getString(R.string.str_net_error_message));
            }

            @Override
            public void onResponse(ProductCommentListBean response, int id) {
                if (response.getCode() == 1) {
                    if (response.getContent() == null) {
                        return;
                    }
                    List<ProductCommentListBean.ContentBean.ContentListBean> contentList = response.getContent().getContentList();
                    commentNiceTv.setText("好评(" + response.getContent().getGoodnum() + ")");
                    commentnumTv.setText("中评(" + response.getContent().getCommentnum() + ")");
                    commentbadTv.setText("差评(" + response.getContent().getBadnum() + ")");
                    comments.addAll(contentList);
                    setAdapter();

                    int scaleStr;
                    if (response.getContent().getAllcommentnum() == Const.INTEGER_0) {
                        scaleStr = 0;
                    } else {
                        float goodnum = (float) response.getContent().getGoodnum();
                        float allcommentnum = (float) response.getContent().getAllcommentnum();
                        float scale = goodnum / allcommentnum * 100;
                        scaleStr = (int) scale;
                    }
                    tvFeedback.setText(scaleStr + "%好评");
                    tvAllComment.setText("商品评价(" + String.valueOf(response.getContent().getAllcommentnum()) + ")");
                    setAdapter();
                } else {
                    ToastUtils.show(response.getMsg());
                }

                if (comments.size() > 0) {
                    imgNoCommont.setVisibility(View.GONE);
                } else {
                    imgNoCommont.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setAdapter() {
        if (shoppingEvaluateAdapter == null) {
            shoppingEvaluateAdapter = new ShoppingEvaluateAdapter(getActivity(), comments);
            fragmentUserAppraiseXlistview.setAdapter(shoppingEvaluateAdapter);
        } else {
            shoppingEvaluateAdapter.setData(comments);
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        getCommentList();
        mSmartRefreshLayout.finishLoadmore(500);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        if (comments != null) {
            comments.clear();
        }
        getCommentList();
        mSmartRefreshLayout.finishRefresh(500);
    }
}
