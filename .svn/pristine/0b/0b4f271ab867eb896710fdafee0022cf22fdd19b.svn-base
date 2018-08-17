package com.yst.onecity.activity.servermember;

import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

import static com.yst.onecity.utils.ConstUtils.showIdNumber;

/**
 * 服务专员身份认证信息页面
 *
 * @author Chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class NameAuthenticationInfoActivity extends BaseActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_real_name)
    TextView tvRealName;
    @BindView(R.id.tv_card_type)
    TextView tvCardType;
    @BindView(R.id.tv_card_num)
    TextView tvCardNum;
    private String name = "";
    private String card = "";

    @Override
    public int bindLayout() {
        return R.layout.activity_name_authentication_info;
    }

    @Override
    public void initData() {
        setTitleBarTitle("认证信息");
        getAuthencitationInfo();
    }

    @OnClick({R.id.ll_back})
    public void onViewClicked(View view) {
        if (!ConstUtils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            case R.id.ll_back:
                //返回
                EventBus.getDefault().post(new EventBean("authentication", "","","",""));
                finish();
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
                        JSONObject jsonContent = jsonObject.getJSONObject(Const.CONS_STR_CONTENT);
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

    /**
     * 设置用户名
     * @param name 用户名
     * @return 拼接后得用户名
     */
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            EventBus.getDefault().post(new EventBean("authentication", "","","",""));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
