package com.yst.onecity.activity.member;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.bankcard.UserBankCardActivity;
import com.yst.onecity.activity.bankcard.UserBindCardPhoneActivity;
import com.yst.onecity.activity.servermember.CashStateActivity;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.CashBean;
import com.yst.onecity.bean.CodeMsgBean;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.IsPayPwdBean;
import com.yst.onecity.callbacks.AbstractCodeMsgCallback;
import com.yst.onecity.callbacks.AbstractIsPayPwdCallback;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.dialog.AbstractEditPwdDialog;
import com.yst.onecity.dialog.AbstractSaveDialog;
import com.yst.onecity.dialog.AbstractSetPasswordSuccessDialog;
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

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

import static com.yst.onecity.R.id.et_cash_money;

/**
 * 会员提现页面
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class CashMemberActivity extends BaseActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_bank)
    ImageView ivBank;
    @BindView(R.id.tv_bank)
    TextView tvBank;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_yue)
    TextView tvYue;
    @BindView(R.id.tv_shouxufei)
    TextView tvShouxufei;
    @BindView(R.id.tv_txnum)
    TextView tvTxNum;
    @BindView(R.id.tv_cash)
    TextView tvCash;
    @BindView(et_cash_money)
    EditText etCashMoney;
    private String bankName = "", bankNum = "", backId = "", bankUserName = "", bindbacktime = "";
    private AbstractSaveDialog mVerifyDialog = null;
    private AbstractSaveDialog bindCard = null;
    private int type = 0;
    private String jump = "jump";
    private int num;
    private int flag = 0;
    private String pwd;
    private Double cashBalance;

    @Override
    public int bindLayout() {
        return R.layout.activity_cash_member;
    }

    @Override
    public void initData() {
        initTitleBar("提现");
        EventBus.getDefault().register(this);
        if (getIntent() != null) {
            Intent intent = getIntent();
            cashBalance = intent.getDoubleExtra("money",0);
            //格式化设置
            num = intent.getIntExtra("num", 0);
            DecimalFormat decimalFormat = new DecimalFormat("###0.00");
            tvYue.setText(decimalFormat.format(cashBalance));
        }
        showDialog();
        initListener();
    }

    private void initListener() {
        //允许输入数字并且可以为小数
        etCashMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable edt) {
                String temp = edt.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) {
                    return;
                }
                if (temp.length() - posDot - 1 > Const.INTEGER_2) {
                    edt.delete(posDot + 3, posDot + 4);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBandCardStatus();
    }

    @OnClick({R.id.tv_cash})
    public void onViewClicked(View view) {
        if (!ConstUtils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            //确认提现
            case R.id.tv_cash:
                requestIsPwd();
                break;
            default:
                break;
        }
    }

    /**
     * 判断是否设置过支付密码
     */
    private void requestIsPwd() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "timestamp", timestamp);
        if (TextUtils.isEmpty(sign)) {
            return;
        }
        OkHttpUtils.post().url(Constants.ISSETPASSWORD)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("timestamp", timestamp)
                .addParams("sign", sign)
                .addParams("client_type", "A")
                .build().execute(new AbstractIsPayPwdCallback() {
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
            public void onResponse(IsPayPwdBean response, int id) {
                if (response.getCode() == Const.INTEGER_1) {
                    String cashMoney = etCashMoney.getText().toString().trim();
                    Double iCashMoney = Double.parseDouble(cashMoney);
                    if( iCashMoney > cashBalance){
                        ToastUtils.show("您的可提余额为" + cashBalance);
                        return;
                    }
                    if (response.getContent() == Const.INTEGER_1) {
                        //设置支付密码
                        showSetPwdDialog();
                    } else {
                        //输入支付密码
                        showEditPayPwdDialog();
                    }
                }
            }
        });
    }

    /**
     * 输入支付密码弹框
     */
    private void showEditPayPwdDialog() {
        final AbstractEditPwdDialog dialog = new AbstractEditPwdDialog(CashMemberActivity.this) {
            @Override
            public void forgetClick() {
                JumpIntent.jump(CashMemberActivity.this, SetPaymentPasswordActivity.class);
            }

            @Override
            public void closeClick() {

            }
        };
        dialog.setText("请输入支付密码");
        dialog.showDialog();
        dialog.setOnFinishInput(new AbstractEditPwdDialog.OnPasswordInputFinish() {
            @Override
            public void inputFinish(String password) {
                goCash(password);
            }
        });
    }

    /**
     * 如果没有设置过支付密码就弹此框
     */
    private void showSetPwdDialog() {
        final AbstractEditPwdDialog dialog = new AbstractEditPwdDialog(this) {
            @Override
            public void forgetClick() {

            }

            @Override
            public void closeClick() {

            }
        };
        if (Const.CONS_STR_FORGET_PASSWARD.equals(dialog.getTextView().getText().toString())) {
            dialog.getTextView().setVisibility(View.GONE);
        }
        dialog.showDialog();
        dialog.setOnFinishInput(new AbstractEditPwdDialog.OnPasswordInputFinish() {
            @Override
            public void inputFinish(String password) {
                if (flag == Const.INTEGER_1) {
                    if (password.equals(pwd)) {
                        dialog.cleanPassword();
                        dialog.setText("请输入短信验证码");
                        getSmsCode();
                        flag = Const.INTEGER_2;
                    } else {
                        dialog.cleanPassword();
                        ToastUtils.show("密码不一致，请重新输入");
                    }
                } else if (flag == Const.INTEGER_0) {
                    //保存第一次输入的密码
                    pwd = password;
                    flag = Const.INTEGER_1;
                    dialog.cleanPassword();
                    dialog.setText("请再输入一次");
                } else {
                    //设置支付密码接口
                    requestSetPayPassword(password, pwd, dialog);
                }
            }
        });
    }

    /**
     * 获取短信验证码
     */
    private void getSmsCode() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.GETSMSCODE)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("timestamp", timestamp)
                .addParams("sign", sign)
                .addParams("client_type", "A")
                .build().execute(new AbstractCodeMsgCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(R.string.app_on_request_error_msg, Toast.LENGTH_SHORT);
            }

            @Override
            public void onResponse(CodeMsgBean response, int id) {
                if (response.getCode() == Const.INTEGER_1) {
                    ToastUtils.show(response.getMsg());
                } else {
                    ToastUtils.show(response.getMsg());
                }
            }
        });
    }


    /**
     * 设置支付密码接口
     *
     * @param password 短信码
     * @param pwd      第一次输入的支付密码
     */
    private void requestSetPayPassword(String password, final String pwd, final AbstractEditPwdDialog editDialog) {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "smsCode", password,
                "newPassword", pwd,
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.SETPASSWORD)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("smsCode", password)
                .addParams("newPassword", pwd)
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
                dismissInfoProgressDialog();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(R.string.app_on_request_error_msg, Toast.LENGTH_SHORT);
            }

            @Override
            public void onResponse(CodeMsgBean response, int id) {
                if (response.getCode() == Const.INTEGER_1) {
                    editDialog.dismissDialog();
                    //支付密码设置成功弹框
                    AbstractSetPasswordSuccessDialog dialog = new AbstractSetPasswordSuccessDialog(CashMemberActivity.this) {
                        @Override
                        public void onSureClick() {
                            goCash(pwd);
                        }
                    };
                    dialog.showDialog();
                } else {
                    ToastUtils.show(response.getMsg());
                    editDialog.cleanPassword();
                }
            }
        });
    }


    /**
     * 初始化弹框
     */
    private void showDialog() {
        mVerifyDialog = new AbstractSaveDialog(this) {
            @Override
            public void onSureClick() {
                mVerifyDialog.dismissDialog();
                JumpIntent.jump(CashMemberActivity.this, RealNameAuthenticationActivity.class);
                finish();
            }

            @Override
            public void onCancleClick() {
                CashMemberActivity.this.finish();
            }
        };
        mVerifyDialog.setText(getString(R.string.str_bank_card_dialog_message));
        mVerifyDialog.setCancleStyle(R.drawable.shape_white_10, R.color.black, getString(R.string.cancel));
        mVerifyDialog.setSureStyle(R.drawable.shape_gray_180dp_bg, R.color.black, getString(R.string.str_bank_card_real_name_message));
        bindCard = new AbstractSaveDialog(this) {
            @Override
            public void onSureClick() {
                bindCard.dismissDialog();
                Bundle bankCardBundle = new Bundle();
                bankCardBundle.putInt("type", 0);
                JumpIntent.jump(CashMemberActivity.this, UserBankCardActivity.class, bankCardBundle);
                finish();
            }

            @Override
            public void onCancleClick() {
                CashMemberActivity.this.finish();
            }
        };
        bindCard.setText(getString(R.string.str_bank_card_dialog_cash_message));
        bindCard.setCancleStyle(R.drawable.shape_white_10, R.color.black, getString(R.string.cancel));
        bindCard.setSureStyle(R.drawable.shape_gray_180dp_bg, R.color.black, getString(R.string.str_bind_card_title));
    }

    /**
     * 获取用户的绑卡信息
     * 未实名
     * 未绑卡
     * 已绑卡
     */
    private void getBandCardStatus() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "usetType", "0",
                "timestamp", timestamp);
        OkHttpUtils
                .post()
                .url(Constants.URL_TO_BIND_BANKCARD)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("usetType", "0")
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
                super.onAfter(id);
                dismissInfoProgressDialog();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.str_net_error_message));
            }

            @Override
            public void onResponse(String response, int id) {
                bankName = "";
                bankNum = "";
                backId = "";
                bankUserName = "";
                bindbacktime = "";
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code;
                    if (jsonObject.getInt(Const.CONS_STR_CODE) == 1) {
                        JSONObject jsonContent = jsonObject.getJSONObject(Const.CONS_STR_CONTENT);
                        if (jsonContent != null) {
                            if (jsonContent.has(Const.CONS_STR_CODES)) {
                                code = jsonContent.getString("codes");
                            } else {
                                code = jsonContent.getString("code");
                            }
                            if (Const.STR1.equals(code)) {
                                //未实名认证
                                mVerifyDialog.showDialog();
                            } else if (Const.STR2.equals(code)) {
                                //绑卡成功信息回显
                                bankUserName = jsonContent.getString("name");
                                bankName = jsonContent.getString("bank");
                                bankNum = jsonContent.getString("bankNo");
                                backId = jsonContent.getString("id");
                                bindbacktime = jsonContent.getString("num");
                                showBankCardInfo();
                            } else if (Const.STR3.equals(code)) {
                                //尚未绑卡显示页面
                                bindbacktime = jsonContent.getString("num");
                                bindCard.showDialog();
                            }
                        }
                    } else {
                        ToastUtils.show(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    MyLog.e("CashMember", "exception ==============" + e.getMessage());
                }
            }
        });
    }

    /**
     * 显示绑卡的信息回显
     */
    private void showBankCardInfo() {
        String end = bankNum.substring(bankNum.length() - 4);
        tvBank.setText(bankName);
        tvBankName.setText("尾号" + end + "储存卡");
        tvTxNum.setText("还剩" + num + "次提现机会");
    }

    /**
     * 提现
     */
    private void goCash(String passWord) {
        String cashMoney = etCashMoney.getText().toString().trim();
        if (TextUtils.isEmpty(cashMoney)) {
            ToastUtils.show("请输入您要提现得金额");
            return;
        }

        String usedMoney = tvYue.getText().toString().trim();
        Double iCashMoney = Double.parseDouble(cashMoney);
        Double iUsedMoney = Double.parseDouble(usedMoney);
        if (iCashMoney < Const.INTEGER_100) {
            ToastUtils.show("最低提现金额为100元");
            return;
        }
        if (iCashMoney > iUsedMoney) {
            ToastUtils.show("您的可提余额为" + usedMoney);
            return;
        }

        cashMoney = String.valueOf(new Double(Double.parseDouble(cashMoney) * 10000).longValue());


        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "payPassword", passWord,
                "cardId", backId,
                "ipaddress", ConstUtils.getIPAddress(context),
                "money", cashMoney,
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.MEMBER_CASH)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("payPassword", passWord)
                .addParams("cardId", backId)
                .addParams("ipaddress", ConstUtils.getIPAddress(context))
                .addParams("money", cashMoney)
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
                ToastUtils.show(getString(R.string.app_on_request_error_msg));
                MyLog.e("CashMember", "Member_goCash____onerror___" + e);
            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("CashMember", "Member_goCash____onResponse___" + response);
                Gson gson = new Gson();
                CashBean cashBean = gson.fromJson(response, CashBean.class);
                if (cashBean != null && cashBean.getCode() == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isSuccess", true);
                    bundle.putString("bankno", bankNum);
                    bundle.putString("cashmoney", etCashMoney.getText().toString().trim());
                    JumpIntent.jump(CashMemberActivity.this, CashStateActivity.class, bundle);
                    finish();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isSuccess", false);
                    bundle.putString("msg", cashBean.getMsg());
                    JumpIntent.jump(CashMemberActivity.this, CashStateActivity.class, bundle);
                    finish();
                }
            }

        });
    }

    @Subscribe
    public void onEventMainThread(EventBean event) {
        if (jump.equals(event.getMsg())) {
            finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
