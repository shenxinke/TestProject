package com.yst.onecity.activity.servermember;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.bankcard.UserBankCardActivity;
import com.yst.onecity.activity.member.AuthAccountSafeActivity;
import com.yst.onecity.activity.member.MyOrderActivity;
import com.yst.onecity.activity.member.MyPublishActivity;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.ServerMemberInfoBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.roundedimageview.RoundedImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.yst.onecity.R.id.rl_my_publish;

/**
 * 服务专员个人中心页面
 *
 * @author Chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class ServerMemberActivity extends BaseActivity {

    @BindView(R.id.tv_go_promotion_center)
    LinearLayout tvGoPromotionCenter;
    @BindView(R.id.iv_headView)
    RoundedImageView ivHeadView;
    @BindView(R.id.tv_identification_status)
    TextView tvIdentificationStatus;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_my_orders)
    TextView tvMyOrders;
    @BindView(R.id.tv_my_account)
    TextView tvMyAccount;
    @BindView(rl_my_publish)
    RelativeLayout rlMyPublish;
    @BindView(R.id.rl_my_bank_card)
    RelativeLayout rlMyBankCard;
    @BindView(R.id.rl_account_safe)
    RelativeLayout rlAccountSafe;
    @BindView(R.id.server_member_info)
    RelativeLayout serVerMemberInfo;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.my_hiuyuan_list)
    RelativeLayout myHiuyuanList;
    @BindView(R.id.tv_yaoqing)
    TextView tvYaoqing;
    @BindView(R.id.messageTagTV)
    TextView messageTagTV;

    @Override
    public int bindLayout() {
        return R.layout.activity_server_member;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getServerMemberInfo();
        getUnReadMessageNum();
    }


    @OnClick({R.id.tv_go_promotion_center, R.id.tv_my_orders, R.id.tv_my_account, R.id.rl_my_publish,
            R.id.my_hiuyuan_list, R.id.rl_my_bank_card, R.id.rl_account_safe, R.id.server_member_info, R.id.my_news, R.id.ll_invite})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_my_orders:
                Constants.ORDER_ISMEMBER = 1;
                Bundle bundle = new Bundle();
                bundle.putInt("flag", 1);
                JumpIntent.jump(this, MyOrderActivity.class, bundle);
                break;
            case R.id.rl_my_bank_card:
                Bundle bankCardBundle = new Bundle();
                bankCardBundle.putInt("type", 1);
                JumpIntent.jump(this, UserBankCardActivity.class, bankCardBundle);
                break;
            case R.id.rl_my_publish:
                Constants.ISSERVER = true;
                JumpIntent.jump(this, MyPublishActivity.class);
                break;
            case R.id.my_hiuyuan_list:
                //会员列表
                JumpIntent.jump(this, ServerMemberListActivity.class);
                break;
            case R.id.server_member_info:
                //基本信息
                JumpIntent.jump(this, EssentialInformationActivity.class);
                break;
            case R.id.ll_invite:
                // /邀请下载app
                JumpIntent.jump(ServerMemberActivity.this, InvatationActivity.class);
                break;
            case R.id.rl_account_safe:
                //账号安全
                JumpIntent.jump(this, AuthAccountSafeActivity.class);
                break;
            case R.id.tv_my_account:
                //我得账户
                JumpIntent.jump(this, MyAccountActivity.class);
                break;
            case R.id.tv_go_promotion_center:
                //返回
                finish();
                break;
            case R.id.my_news:
                //消息lieb
                JumpIntent.jump(this, NewListActivity.class);
                break;
            default:
                break;
        }
    }

    /**
     * 获取未读消息数量
     */
    private void getUnReadMessageNum(){
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "timestamp", timestamp,
                "type","1"
        );
        OkHttpUtils.post().url(Constants.GET_UNREADMESSAGE)
                .addParams("type","1")
                .addParams("timestamp",timestamp)
                .addParams("client_type","A")
                .addParams("sign",sign)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt(Const.CONS_STR_CODE) == 1){
                        if (jsonObject.getInt(Const.CONS_STR_CONTENT)>0){
                            messageTagTV.setVisibility(View.VISIBLE);
                            messageTagTV.setText(String.valueOf(jsonObject.getInt("content")));
                        }else{
                            messageTagTV.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取服务专员基本信息
     */
    private void getServerMemberInfo() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.SERVERMEMBER_INFO)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("timestamp", timestamp)
                .addParams("sign", sign)
                .addParams("client_type", "A")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.app_on_request_error_msg));
            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("ServerMemberInfo", "getServerMemberInfo____onResponse___" + response);
                Gson gson = new Gson();
                ServerMemberInfoBean serverMemberInfoBean = gson.fromJson(response, ServerMemberInfoBean.class);
                if (serverMemberInfoBean != null && Const.STR1.equals(serverMemberInfoBean.getCode()) && serverMemberInfoBean.getContent() != null) {
                    ServerMemberInfoBean.ContentBean content = serverMemberInfoBean.getContent();
                    setServerMemberInfo(content);
                }
            }
        });
    }

    /**
     * 展示服务专员基本信息
     *
     * @param content
     */
    private void setServerMemberInfo(ServerMemberInfoBean.ContentBean content) {
        if (content.getLogoImg() != null) {
            Glide.with(this).load(Constants.URL_IMAGE_HEAD + content.getLogoImg()).into(ivHeadView);
            TianyiApplication.appCommonBean.setAddress(Constants.URL_IMAGE_HEAD + content.getLogoImg());
        }
        if (content.getNickname() != null) {
            tvName.setText(content.getNickname());
            TianyiApplication.appCommonBean.setNickname(content.getNickname());
        } else {
            tvName.setText("昵称");
        }
        if (content.getLevel()!=null){
            tvIdentificationStatus.setText(content.getLevel());
        }
        ConstUtils.setTextString(TianyiApplication.appCommonBean.getPhone(), tvPhone);
    }
}
