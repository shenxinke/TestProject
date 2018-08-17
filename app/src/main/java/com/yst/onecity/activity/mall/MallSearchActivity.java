package com.yst.onecity.activity.mall;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.KeyBoardUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.view.ContainsEmojiEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商城搜索
 *
 * @author wuxiaofang
 * @version 4.0.0
 * @date 2018/3/26
 */

public class MallSearchActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.et_input_content)
    ContainsEmojiEditText etInputContent;
    @BindView(R.id.searchBarLayout)
    LinearLayout searchBarLayout;
    @BindView(R.id.searchTV)
    TextView searchTV;
    @BindView(R.id.searchRL)
    RelativeLayout searchRL;

    @Override
    public int bindLayout() {
        return R.layout.activity_mall_search;
    }

    @Override
    public void initData() {
        KeyBoardUtils.showSoftInputFromWindow(this, etInputContent);
    }

    /**
     * 搜索的监听
     */
    @OnClick(R.id.searchTV)
    public void search() {
        String s = etInputContent.getText().toString();
        if (TextUtils.isEmpty(s)) {
            ToastUtils.show("请输入搜索内容");
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("type", "search");
            bundle.putString("content", s);
            JumpIntent.jump(MallSearchActivity.this, GoodsListActivity.class, bundle);
            KeyBoardUtils.closeKeybord(etInputContent, this);
            finish();
        }
    }

    /**
     * 返回的监听
     */
    @OnClick(R.id.back)
    public void back() {
        finish();
    }
}
