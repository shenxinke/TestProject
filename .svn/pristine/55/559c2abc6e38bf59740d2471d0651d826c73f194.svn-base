package com.yst.onecity.activity.member;

import android.graphics.Bitmap;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.WriterException;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.zxing.QrCodeUtil;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.PayCodeBean;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.utils.WindowUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 服务专员中心付款码
 *
 * @author Shenxinke
 * @version 4.0.0
 * @data 2018/3/20
 */
public class PaymentCodeActivity extends BaseActivity {
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.one_dimensional_code)
    ImageView oneDimensionalCode;
    @BindView(R.id.two_dimensional_code)
    ImageView twoDimensionalCode;

    private int oneWidth = 0;
    private int oneHeight = 0;
    private int twoWidthAndHeight = 0;
    private int spTime =  1000 * 60 * 10;

    @Override
    public int bindLayout() {
        return R.layout.activity_payment_code;
    }

    @Override
    public void initData() {
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /**
         * 设置显示条形码图片的大小
         * */
        ViewGroup.LayoutParams oneLayoutParams = oneDimensionalCode.getLayoutParams();
        oneLayoutParams.width = WindowUtils.getScreenWidth(PaymentCodeActivity.this) * 9 / 10;
        oneLayoutParams.height = WindowUtils.getScreenHeight(PaymentCodeActivity.this) * 1 / 8;
        oneDimensionalCode.setLayoutParams(oneLayoutParams);
        oneWidth = oneLayoutParams.width;
        oneHeight = oneLayoutParams.height;

        /**
         * 设置显示二维码图片的大小
         */
        ViewGroup.LayoutParams layoutParams = twoDimensionalCode.getLayoutParams();
        layoutParams.width = WindowUtils.getScreenWidth(PaymentCodeActivity.this) / 2;
        layoutParams.height = layoutParams.width;
        twoDimensionalCode.setLayoutParams(layoutParams);
        twoWidthAndHeight = layoutParams.width;

        handler.postDelayed(runnable, 0);

    }

    private void initialLayout(String contentQRCode) {
        try {
            // 根据字符串生成二维码图片并显示在界面上
            Bitmap bitmapQRCode = QrCodeUtil.createQRCode(contentQRCode,
                    twoWidthAndHeight);
            twoDimensionalCode.setImageBitmap(bitmapQRCode);
            oneDimensionalCode.setImageBitmap(QrCodeUtil.creatBarcode(
                    getApplicationContext(), contentQRCode, oneWidth, oneHeight, false));
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取付款码
     */
    private void getPayCode() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.PAY_CODE)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
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
                ToastUtils.show(R.string.app_on_request_error_msg, Toast.LENGTH_SHORT);
            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("register", "getcode__onResponse___" + response);
                Gson gson = new Gson();
                PayCodeBean payCodeBean = gson.fromJson(response, PayCodeBean.class);
                int code = payCodeBean.getCode();
                if (code == 1) {
                    String payCode = payCodeBean.getContent().getCode();
                    initialLayout(payCode);
                } else {
                    ToastUtils.show(R.string.app_on_request_error_msg, Toast.LENGTH_SHORT);
                }
            }
        });
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                handler.postDelayed(this, spTime);
                /**
                 * 获取付款码
                 */
                getPayCode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
