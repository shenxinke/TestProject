package com.yst.onecity.activity.bankcard;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.servermember.NameAuthenticationInfoActivity;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.CodeMsgBean;
import com.yst.onecity.callbacks.AbstractCodeMsgCallback;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.dialog.AbstractBankCardNumHintDialog;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

import static com.yst.onecity.utils.ConstUtils.replaceSubString;

/**
 * 用户绑定银行卡
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class UserBindCardNumActivity extends BaseActivity {

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
    @BindView(R.id.et_bankno)
    EditText etBankno;
    @BindView(R.id.text_bankcard_name)
    TextView textBankcardName;
    @BindView(R.id.fragment_edit_bankcard_name_rel)
    RelativeLayout fragmentEditBankcardNameRel;
    @BindView(R.id.text_user_name)
    TextView textUserName;
    @BindView(R.id.text_user_card)
    TextView textUserCard;
    @BindView(R.id.bank_edit_tv)
    TextView bankEditTv;
    @BindView(R.id.activity_user_bind_card)
    LinearLayout activityUserBindCard;
    @BindView(R.id.layout_bind_card_main)
    LinearLayout llayoutBindCardMain;
    private ListView mCardListView;
    private String bankNo;
    private String bankId;
    private String bankcardname;
    private boolean isNewFlag;
    private List<Map<String, String>> bankNameMapList = new ArrayList<>();
    /**
     * 判断标识
     */
    public static String isNew = "isnew";
    private int type = 0;
    private int num = 0;

    @Override
    public int bindLayout() {
        return R.layout.activity_user_bind_card;
    }

    @Override
    public void initData() {
        type = getIntent().getExtras().getInt("type", 0);
        num = Integer.parseInt(getIntent().getStringExtra("num"));
        activityTitleTv.setText(getString(R.string.str_bind_card_title));
        isNewFlag = getIntent().getExtras().getBoolean(isNew);
        if (isNewFlag) {
            etBankno.addTextChangedListener(new EditChangedListener());
        } else {
            bankId = getIntent().getExtras().getString("id");
            bankcardname = getIntent().getExtras().getString("bankName");
            bankNo = getIntent().getExtras().getString("bankNum");
            etBankno.setText(bankNo);
            etBankno.setSelection(bankNo.length());
            textBankcardName.setText(bankcardname);
            etBankno.addTextChangedListener(new EditChangedListener());
        }
        mCardListView = (ListView) View.inflate(this, R.layout.popwindow_list, null).findViewById(R.id.popwindow_listView);
        mCardListView.setDividerHeight(0);
        getBankName();
        mCardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bankId = bankNameMapList.get(position).get("id");
                bankcardname = bankNameMapList.get(position).get("text");
                textBankcardName.setText(bankNameMapList.get(position).get("text"));
                Utils.popupWindow.dismiss();
            }
        });

    }

    /**
     * 获取银行卡名称
     */
    private void getBankCardName(String card) {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "timestamp", timestamp,
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "cardnum", card
        );
        OkHttpUtils
                .post()
                .url(Constants.BANKCARD_NAME)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("cardnum", card)
                .addParams("sign", sign)
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.show(getString(R.string.str_net_error_message));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.has(Const.CONS_STR_CODE) && jsonObject.getInt(Const.CONS_STR_CODE) == 1) {
                                JSONObject content = jsonObject.getJSONObject("content");
                                String bankname = content.getString("bankname");
                                bankcardname = bankname;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    class EditChangedListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            getBankCardName(s.toString());
            textBankcardName.setText(bankcardname);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAuthencitationInfo();
    }

    @OnClick({R.id.activity_back_iv, R.id.bank_edit_tv})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.activity_back_iv:
                finish();
                break;
            case R.id.bank_edit_tv:
                if (Utils.isClickable()) {
                    MyLog.e("sss", "is new " + isNewFlag);
                    if (isNewFlag) {
                        bindCard();
                    } else {
                        if (num == 0) {
                            AbstractBankCardNumHintDialog dialog = new AbstractBankCardNumHintDialog(UserBindCardNumActivity.this) {
                                @Override
                                public void onSureClick() {
                                    updateCard();
                                }
                            };
                            dialog.setCancleStyle(R.drawable.shape_white_10, R.color.black, "取消");
                            dialog.setSureStyle(R.drawable.shape_gray_180dp_bg, R.color.black, "确认");
                            dialog.showDialog();
                        } else {
                            updateCard();
                        }

                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获取银行卡名称列表
     */
    private void getBankName() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "timestamp", timestamp
        );
        OkHttpUtils.post().url(Constants.URL_GETBANKCARDLIST)
                .addParams("sign", sign)
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .build().execute(new StringCallback() {
            @Override
            public void onResponse(String s, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.has(Const.CONS_STR_CODE) && jsonObject.getInt(Const.CONS_STR_CODE) == 1) {
                        JSONArray content = jsonObject.getJSONArray("content");
                        for (int i = 0; i < content.length(); i++) {
                            JSONObject bank = content.getJSONObject(i);
                            Map<String, String> bankNameMap = new HashMap<>(16);
                            if (bank.has("id")) {
                                bankNameMap.put("id", bank.getString("id"));
                            }
                            if (bank.has("text")) {
                                bankNameMap.put("text", bank.getString("text"));
                            }
                            bankNameMapList.add(bankNameMap);
                        }
                        mCardListView.setAdapter(new SimpleAdapter(UserBindCardNumActivity.this, bankNameMapList, R.layout.bank_name_list_item, new String[]{"text"}, new int[]{R.id.bank_name}));
                    }
                } catch (JSONException e) {

                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.str_net_error_message));
            }

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
        });
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
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(R.string.app_on_request_error_msg, Toast.LENGTH_SHORT);
            }

            @Override
            public void onResponse(String s, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String name = "";
                    String card = "";
                    if (jsonObject.has(Const.CONS_STR_CODE) && jsonObject.getInt(Const.CONS_STR_CODE) == 1) {
                        JSONObject jsonContent = jsonObject.getJSONObject("content");
                        if (jsonContent.has(Const.CONS_STR_NAME)) {
                            name = jsonContent.getString("name");
                        }
                        if (jsonContent.has(Const.CONS_STR_CARD)) {
                            card = jsonContent.getString("card");
                        }
                        textUserName.setText(NameAuthenticationInfoActivity.setName(name));
                        textUserCard.setText(replaceSubString(card));
                    } else {
                        ToastUtils.show(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    ToastUtils.show(R.string.app_on_request_exception_msg, Toast.LENGTH_SHORT);
                }
            }
        });
    }

    /**
     * 绑定新卡
     */
    private void bindCard() {
        String cardNum = etBankno.getText().toString().trim();

        if (cardNum.length() < Const.INTEGER_10) {
            ToastUtils.show("请输入正确的银行卡号");
            return;
        }

        if (textBankcardName.getText().length() < 1) {
            ToastUtils.show("请选择银行卡名称");
            return;
        }
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "usetid", TianyiApplication.appCommonBean.getId(),
                "usetType", String.valueOf(type),
                "cardnum", cardNum,
                "bankname", bankcardname,
                "timestamp", timestamp);
        OkHttpUtils
                .post()
                .url(Constants.URL_BIND_BANKCARD)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("cardnum", cardNum)
                .addParams("bankname", bankcardname)
                .addParams("usetid", TianyiApplication.appCommonBean.getId())
                .addParams("usetType", String.valueOf(type))
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
                ToastUtils.show(getString(R.string.str_net_error_message));
            }

            @Override
            public void onResponse(CodeMsgBean response, int id) {
                if (response.getCode() == 1) {
                    finish();
                    if (num == 0) {
                        ToastUtils.show(response.getMsg());
                    } else {
                        ToastUtils.show("剩余免费绑定次数:" + (num - 1) + "次");
                    }
                } else {
                    ToastUtils.show(response.getMsg());
                }
            }
        });
    }

    /**
     * 更新银行卡
     */
    private void updateCard() {
        String cardNum = etBankno.getText().toString().trim();
        if (cardNum.length() < Const.INTEGER_10) {
            ToastUtils.show("请输入正确的银行卡号");
            return;
        }
        if (bankcardname.length() < 1) {
            ToastUtils.show("请选择银行卡名称");
            return;
        }
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "bankname", bankcardname,
                "usetType", String.valueOf(type),
                "cardnum", cardNum,
                "timestamp", timestamp);
        OkHttpUtils
                .post()
                .url(Constants.URL_UPDATE_BANKCARD)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("cardnum", cardNum)
                .addParams("bankname", bankcardname)
                .addParams("usetType", String.valueOf(type))
                .addParams("client_type", "A")
                .addParams("timestamp", timestamp)
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
                ToastUtils.show(getString(R.string.str_net_error_message));
            }

            @Override
            public void onResponse(CodeMsgBean response, int id) {
                if (response.getCode() == 1) {
                    finish();
                }
                if (num == 0) {
                    ToastUtils.show(response.getMsg());
                } else {
                    ToastUtils.show("剩余免费绑定次数:" + (num - 1) + "次");
                }
            }
        });
    }

}
