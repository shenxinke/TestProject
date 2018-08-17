package com.yst.onecity.activity.servermember;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.TIMConversationType;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.ShareActivity;
import com.yst.onecity.activity.chat.ChatActivity;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.servermember.ServerMemberDetailBean;
import com.yst.onecity.callbacks.AbstractServerMemberDetailCallback;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.fragment.ServerMemberInformationFragment;
import com.yst.onecity.fragment.ServerMemberProductListFragment;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.FlowLayout;
import com.yst.onecity.view.roundedimageview.RoundedImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.yst.onecity.activity.ShareActivity.SHARE_SERVER_MEMBER;

/**
 * 服务专员详情
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/12/18
 */
public class ServerMemberProductDetailActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.serverMemberHeadView)
    RoundedImageView serverMemberHeadView;
    @BindView(R.id.iv_attention)
    ImageView ivAttention;
    @BindView(R.id.serverMemberName)
    TextView serverMemberName;
    @BindView(R.id.contact_comment_num)
    TextView contactCommentNum;
    @BindView(R.id.serverMemberIntroduce)
    TextView serverMemberIntroduce;
    @BindView(R.id.fl_view)
    FrameLayout flView;
    @BindView(R.id.consult_list)
    TextView consultList;
    @BindView(R.id.product_list)
    TextView productList;
    @BindView(R.id.background)
    ImageView background;
    @BindView(R.id.now_contact_ta1)
    TextView nowContactTa1;
    @BindView(R.id.now_contact_ta)
    TextView nowContactTa;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.appbarlayou)
    AppBarLayout appbarlayout;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.ll_header)
    LinearLayout llHeader;
    @BindView(R.id.flow_label)
    FlowLayout flowLabel;
    @BindView(R.id.store_icon)
    RoundedImageView storeIcon;
    @BindView(R.id.tv_shop_name)
    TextView shopName;
    @BindView(R.id.tv_shop_address)
    TextView shopAddress;

    private ServerMemberInformationFragment serverMemberInformationFragment;
    private ServerMemberProductListFragment serverMemberProductListFragment;
    private boolean isAttention = false;
    private int hunterId;
    private int storeId;
    private String mImId;

    @Override
    public int bindLayout() {
        return R.layout.activity_server_member_product_detail;
    }

    @Override
    public void initData() {
        hunterId = getIntent().getExtras().getInt("hunterId", -1);
        setTitleToCollapsingToolbarLayout("");
        if (hunterId != -1) {
            getData(hunterId);
        }
    }

    /**
     * 获取服务专员信息
     * @param hunterId
     */
    private void getData(int hunterId) {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("hunterId", String.valueOf(hunterId), "memberId", TianyiApplication.appCommonBean.getId(), "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.SERVER_MEMBER_DETAIL)
                .addParams("hunterId", String.valueOf(hunterId))
                .addParams("memberId", TianyiApplication.appCommonBean.getId())
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractServerMemberDetailCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.str_net_error_message));
            }

            @Override
            public void onResponse(ServerMemberDetailBean response, int id) {
                if (response.getCode() == 1) {
                    ServerMemberDetailBean.ContentBean contentBean = response.getContent();
                    Glide.with(getApplicationContext()).load(contentBean.getBackImg()).placeholder(R.mipmap.bg_list_default).error(R.mipmap.bg_list_default).into(background);
                    Glide.with(getApplicationContext()).load(Constants.URL_IMAGE_HEAD + contentBean.getLogoImg()).error(R.mipmap.head_2).into(serverMemberHeadView);
                    if (contentBean.getNickname()==null){
                        serverMemberName.setText("暂无昵称");
                    }else{
                        serverMemberName.setText(contentBean.getNickname());
                    }
                    mImId = contentBean.getImid();
                    contactCommentNum.setText(String.format(getString(R.string.find_ta_and_comment), contentBean.getNum(), contentBean.getCommentNum()));
                    if (response.getContent().getIsCollection() == 1) {
                        isAttention = true;
                        ivAttention.setSelected(true);
                    } else {
                        isAttention = false;
                        ivAttention.setSelected(false);
                    }
                    if (contentBean.getHunterLable() != null){

                        for (int i = 0; i < contentBean.getHunterLable().size(); i++) {
                            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_lable, null);
                            final TextView columnTextView = (TextView) view.findViewById(R.id.txt_lable);
                            columnTextView.setTextSize(10);
                            columnTextView.setPadding(5,3,5,3);
                            columnTextView.setText(contentBean.getHunterLable().get(i).getName());
                            flowLabel.addView(view);
                        }
                    }
                    if (Const.CONS_STR_NULL.equals(String.valueOf(contentBean.getServiceDigest()))) {
                        serverMemberIntroduce.setText("暂无简介");
                    } else {
                        serverMemberIntroduce.setText(String.valueOf(contentBean.getServiceDigest()));
                    }

                    storeId = contentBean.getMerchantId();
                    shopName.setText(contentBean.getMerchantName());
                    shopAddress.setText(contentBean.getMerchantAdress());

                    initFragment1();
                }
            }
        });
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，
     * 设置到Toolbar上则不会显示
     */
    private void setTitleToCollapsingToolbarLayout(final String string) {
        appbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int titleHeight = Utils.dip2px(context, 50);
                if (verticalOffset <= -(llHeader.getHeight() - titleHeight)) {
                    ///使用下面两个CollapsingToolbarLayout的方法设置展开透明->折叠时你想要的颜色
                    //collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
                    //collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorAccent));
                    collapsingToolbarLayout.setTitle(serverMemberName.getText().toString().trim());
                    nowContactTa.setVisibility(View.VISIBLE);
                    collapsingToolbarLayout.setCollapsedTitleGravity(Gravity.LEFT);
                } else {
                    collapsingToolbarLayout.setTitle("");
                    nowContactTa.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    /**
     * 显示第一个fragment
     *
     */
    private void initFragment1() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
        if (serverMemberInformationFragment == null) {
            Bundle args = new Bundle();
            args.putInt("id", hunterId);
            args.putInt("type", 1);
            serverMemberInformationFragment = new ServerMemberInformationFragment();
            serverMemberInformationFragment.setArguments(args);
            transaction.add(R.id.fl_view, serverMemberInformationFragment);
        }
        //隐藏所有fragment
        hideFragment(transaction);
        //显示需要显示的fragment
        transaction.show(serverMemberInformationFragment);

        //提交事务
        transaction.commit();
    }

    /**
     * 显示第二个fragment
     *
     */
    private void initFragment2() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (serverMemberProductListFragment == null) {
            Bundle args = new Bundle();
            args.putInt("serverMemberId", hunterId);
            serverMemberProductListFragment = new ServerMemberProductListFragment();
            serverMemberProductListFragment.setArguments(args);
            transaction.add(R.id.fl_view, serverMemberProductListFragment);
        }
        hideFragment(transaction);
        transaction.show(serverMemberProductListFragment);

        transaction.commit();
    }

    /**
     * 隐藏所有的fragment
     * @param transaction 事物
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (serverMemberInformationFragment != null) {
            transaction.hide(serverMemberInformationFragment);
        }
        if (serverMemberProductListFragment != null) {
            transaction.hide(serverMemberProductListFragment);
        }
    }

    @OnClick({R.id.ll_store_bottom,R.id.back, R.id.now_contact_ta, R.id.share, R.id.iv_attention, R.id.now_contact_ta1, R.id.consult_list, R.id.product_list})
    public void onViewClicked(View view) {
        if (!Utils.isClickable()){return;}
        switch (view.getId()) {
            case R.id.ll_store_bottom:
                Bundle b = new Bundle();
                b.putString("merchantId",String.valueOf(storeId));
                JumpIntent.jump(this,StoreDetailActivity.class,b);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.share:
                if (!TianyiApplication.isLogin) {
                    JumpIntent.jump(this, LoginActivity.class);
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putInt("type",SHARE_SERVER_MEMBER);
                bundle.putString("hunterId",String.valueOf(hunterId));
                JumpIntent.jump(this, ShareActivity.class,bundle);
                break;
            case R.id.iv_attention:
                if (!TianyiApplication.isLogin){
                    JumpIntent.jump(this, LoginActivity.class);
                    return;
                }
                if (!isAttention) {
                    attentionServerMember(0);
                } else {
                    attentionServerMember(1);
                }
                break;
            case R.id.now_contact_ta:
            case R.id.now_contact_ta1:
                if (!TianyiApplication.isLogin){
                    JumpIntent.jump(this, LoginActivity.class);
                    return;
                }
                ChatActivity.navToChat(context, mImId, String.valueOf(hunterId) ,TIMConversationType.C2C);
                break;
            case R.id.consult_list:
                consultList.setBackgroundColor(ContextCompat.getColor(context, R.color.black));
                consultList.setTextColor(ContextCompat.getColor(context, R.color.white));
                productList.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                productList.setTextColor(ContextCompat.getColor(context, R.color.black));
                initFragment1();
                break;
            case R.id.product_list:
                consultList.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                consultList.setTextColor(ContextCompat.getColor(context, R.color.black));
                productList.setBackgroundColor(ContextCompat.getColor(context, R.color.black));
                productList.setTextColor(ContextCompat.getColor(context, R.color.white));
                initFragment2();
                break;
            default:
                break;
        }
    }

    /**
     * 关注推广师
     * @param type 类型
     */
    private void attentionServerMember(final int type) {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("timestamp", timestamp, "type", String.valueOf(type), "hunterId", String.valueOf(hunterId)
                , "uuid", TianyiApplication.appCommonBean.getUuid(), "phone", TianyiApplication.appCommonBean.getPhone());

        OkHttpUtils.post().url(Constants.ATTENTION_HUNTER)
                .addParams("timestamp", timestamp)
                .addParams("type", String.valueOf(type))
                .addParams("hunterId", String.valueOf(hunterId))
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
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
                    if (code == 1 && type == 0) {
                        ToastUtils.show("关注成功");
                        isAttention = true;
                        ivAttention.setSelected(true);
                    } else if (code == 1 && type == 1) {
                        ToastUtils.show("取消成功");
                        isAttention = false;
                        ivAttention.setSelected(false);
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
