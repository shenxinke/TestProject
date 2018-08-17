package com.yst.onecity.activity.login;

import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.base.BaseActivity;

import butterknife.BindView;

/**
 * 用户协议页面
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class UserAgreementActivity extends BaseActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.activity_user_agreement)
    LinearLayout activityUserAgreement;

    @Override
    public int bindLayout() {
        return R.layout.activity_user_agreement;
    }

    @Override
    public void initData() {
        initTitleBar("用户协议");
        webview.loadUrl("file:///android_asset/useraggerment.html");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityUserAgreement.removeView(webview);
        webview.stopLoading();
        webview.clearHistory();
        webview.removeAllViews();
        webview.destroy();
    }
}
