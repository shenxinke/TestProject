package com.yst.onecity.activity.bankcard;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.CodeMsgBean;
import com.yst.onecity.callbacks.AbstractCodeMsgCallback;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

import static com.yst.onecity.activity.bankcard.UserBindCardNumActivity.isNew;

/**
 * 用户绑定银行卡手机号认证页面
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class UserBindCardPhoneActivity extends BaseActivity {

    @BindView(R.id.activity_back_iv1)
    ImageView activityBackIv1;
    @BindView(R.id.activity_back_iv)
    ImageView activityBackIv;
    @BindView(R.id.activity_title_tv)
    TextView activityTitleTv;
    @BindView(R.id.activity_title_right_img)
    ImageView activityTitleRightImg;
    @BindView(R.id.activity_title_right_chat_say_img)
    ImageView activityTitleRightChatSayImg;
    @BindView(R.id.activity_complete_tv)
    TextView activityCompleteTv;
    @BindView(R.id.edit_bind_card_phone)
    EditText editBindCardPhone;
    @BindView(R.id.txt_send_code)
    TextView txtSendCode;
    @BindView(R.id.edit_bind_card_code)
    EditText editBindCardCode;
    @BindView(R.id.txt_bank_next)
    TextView txtBankNext;
    @BindView(R.id.activity_user_bind_card_phone)
    LinearLayout activityUserBindCardPhone;
    private TimeCount countDownTimer;
    private int type = 0;
    private String num = "";
    private boolean flag = false;

    @Override
    public int bindLayout() {
        return R.layout.activity_user_bind_card_phone;
    }

    @Override
    public void initData() {
        type = getIntent().getExtras().getInt("type", 0);
        num = getIntent().getStringExtra("number");
        activityTitleTv.setText(getString(R.string.str_bind_card_title));
        countDownTimer = new TimeCount(60000, 1000);
        editBindCardPhone.setText(TianyiApplication.appCommonBean.getPhone());
    }

    @OnClick({R.id.activity_back_iv, R.id.txt_send_code, R.id.txt_bank_next})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.activity_back_iv:
                finish();
                break;
            case R.id.txt_send_code:
                if (Utils.isClickable()) {
                    sendCode();
                    flag = true;
                }
                break;
            case R.id.txt_bank_next:
                if (Utils.isClickable()) {
                    verifyParams();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 发送验证码
     */
    private void sendCode() {
        String phone = editBindCardPhone.getText().toString().trim();
        if (null == phone || phone.length() != Const.INTEGER_11) {
            ToastUtils.show("请输入正确的手机号");
            return;
        }
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "timestamp", timestamp,
                "id", TianyiApplication.appCommonBean.getId());
        OkHttpUtils.post().url(Constants.URL_SEND_MOBILEMSG)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("timestamp", timestamp)
                .addParams("id", TianyiApplication.appCommonBean.getId())
                .addParams("sign", sign)
                .addParams("client_type", "A")
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
                ToastUtils.show(getString(R.string.str_net_error_message));
            }

            @Override
            public void onResponse(CodeMsgBean response, int id) {
                if (response.getCode() == 1) {
                    countDownTimer.start();
                }
                ToastUtils.show(response.getMsg());
            }
        });
    }

    /**
     * 验证界面参数是否有效
     */
    private void verifyParams() {
        String timestamp = SignUtils.getTimeStamp();
        String phone = editBindCardPhone.getText().toString().trim();
        String code = editBindCardCode.getText().toString().trim();

        if (null == phone || phone.length() != Const.INTEGER_11) {
            ToastUtils.show("请输入正确的手机号");
            return;
        }
        if (code == null || code.length() != Const.INTEGER_6) {
            ToastUtils.show("请输入6位验证码");
            return;
        }
        String sign = Utils.getSign(
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "timestamp", timestamp,
                "code", code);
        OkHttpUtils
                .post()
                .url(Constants.URL_MOBILE_VERIFICATION)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("code", code)
                .addParams("timestamp", timestamp)
                .addParams("sign", sign)
                .addParams("client_type", "A")
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
                ToastUtils.show(getString(R.string.str_net_error_message));
            }

            @Override
            public void onResponse(CodeMsgBean response, int id) {
                if (response.getCode() == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(isNew, true);
                    bundle.putInt("type", type);
                    bundle.putString("num", num);
                    JumpIntent.jump(UserBindCardPhoneActivity.this, UserBindCardNumActivity.class, bundle);
                    UserBindCardPhoneActivity.this.finish();
                } else {
                    if (flag) {
                        ToastUtils.show(response.getMsg());
                    } else {
                        ToastUtils.show("验证码错误");

                    }
                }
            }
        });
    }

    /**
     * 倒计时控制器
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (txtSendCode != null) {
                txtSendCode.setEnabled(false);
                txtSendCode.setText("重新获取 " + millisUntilFinished / 1000 + "秒");
            }
        }

        @Override
        public void onFinish() {
            if (txtSendCode != null) {
                txtSendCode.setText(getString(R.string.get_verification_code));
                txtSendCode.setEnabled(true);
            }
        }
    }
}
