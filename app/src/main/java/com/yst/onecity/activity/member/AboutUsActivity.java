package com.yst.onecity.activity.member;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.yst.onecity.R;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.config.Constants;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 关于我们页面
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.mWebViewContainer)
    LinearLayout mWebViewContainer;
    @BindView(R.id.webView)
    WebView webView;

    @Override
    public int bindLayout() {
        return R.layout.activity_about_us;
    }

    @Override
    public void initData() {
        initTitleBar("关于我们");
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setSupportZoom(false);
        webSetting.setUseWideViewPort(false);
        webSetting.setTextZoom(100);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSetting.setDefaultTextEncodingName("utf-8");
        webSetting.setBlockNetworkImage(false);
        webView.loadUrl(Constants.URL_ROOT + "mobile/view/webview/aboutUsPage");
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebViewContainer.removeView(webView);
        webView.stopLoading();
        webView.getSettings().setJavaScriptEnabled(false);
        webView.clearHistory();
        webView.removeAllViews();
        webView.destroy();
    }
}
