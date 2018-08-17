package com.yst.onecity.activity.member;

import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.servermember.NameAuthenticationInfoActivity;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

import static com.yst.onecity.utils.ConstUtils.showIdNumber;

/**
 * 会员实名认证结果页面
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class AuthenticationStateActivity extends BaseActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.iv_state)
    ImageView ivState;
    @BindView(R.id.tv_real_name)
    TextView tvRealName;
    @BindView(R.id.tv_card_type)
    TextView tvCardType;
    @BindView(R.id.tv_card_num)
    TextView tvCardNum;
    private String name = "";
    private String card = "";
    private boolean isSuccess;
    String authentication = "authentication";

    @Override
    public int bindLayout() {
        return R.layout.activity_authentication_state;
    }

    @Override
    public void initData() {
        setTitleBarTitle("认证状态");
        EventBus.getDefault().register(this);
        if (getIntent().getExtras() != null) {
            isSuccess = getIntent().getExtras().getBoolean("isSuccess");
            MyLog.e("realnameauth","实名认证结果\n"+isSuccess);
            if (isSuccess) {
                //认证成功
                tvState.setText("认证成功");
                ivState.setImageResource(R.mipmap.realname_img_success);
                getAuthencitationInfo();
            } else {
                //认证失败
                tvState.setText("认证失败");
                ivState.setImageResource(R.mipmap.realname_img_failure);
                String relaname = getIntent().getExtras().getString("name");
                String idcard = getIntent().getExtras().getString("card");
                tvRealName.setText(setName(relaname));
                tvCardNum.setText(showIdNumber(idcard));
                tvCardType.setText("身份证");
            }
        }
    }

    @OnClick({R.id.ll_back})
    public void onViewClicked(View view) {
        if (!ConstUtils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            //返回
            case R.id.ll_back:
                if (isSuccess) {
                    //认证成功
                    JumpIntent.jump(this, NameAuthenticationInfoActivity.class);
                } else {
                    finish();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获取个人认证信息
     */
    private void getAuthencitationInfo() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "timestamp", timestamp,
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone()
        );
        OkHttpUtils.post().url(Constants.AUTHCATIONINFO)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("sign", sign)
                .addParams("timestamp", timestamp)
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
            public void onResponse(String s, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.has(Const.CONS_STR_CODE) && jsonObject.getInt(Const.CONS_STR_CODE) == 1) {
                        JSONObject jsonContent = jsonObject.getJSONObject("content");
                        if (jsonContent.has(Const.CONS_STR_NAME)) {
                            name = jsonContent.getString("name");
                        }
                        if (jsonContent.has(Const.CONS_STR_CARD)) {
                            card = jsonContent.getString("card");
                        }
                        tvRealName.setText(setName(name));
                        tvCardNum.setText(showIdNumber(card));
                        tvCardType.setText("身份证");
                    } else {
                        ToastUtils.show(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    ToastUtils.show(R.string.app_on_request_exception_msg, Toast.LENGTH_SHORT);
                }
            }
        });
    }

    public static String setName(String name) {
        if (name == null) {
            return "";
        }
        String top = name.substring(0, 1);
        StringBuffer sb = new StringBuffer();
        if (name.length() > 1) {
            for (int i = 0; i < name.length() - 1; i++) {
                sb = sb.append("*");
            }
        }
        return top + sb.toString();
    }

    @Subscribe
    public void onEventMainThread(EventBean event) {
        if (authentication.equals(event.getMsg())) {
            finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            //认证成功
            if (isSuccess) {
                JumpIntent.jump(this, NameAuthenticationInfoActivity.class);
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
