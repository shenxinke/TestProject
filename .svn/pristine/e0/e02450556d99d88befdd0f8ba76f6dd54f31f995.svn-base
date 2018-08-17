package com.yst.onecity.activity.commissioner;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.config.Const;
import com.yst.onecity.utils.InputTextWatcher;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人简介
 *
 * @author wuxiaofang
 * @version 4.2.0
 * @date 2018/6/4
 */

public class SimpleIntroActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.ed_simple_intro)
    EditText edSimpleIntro;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.ll_ed)
    LinearLayout llEd;
    @BindView(R.id.tv_have_input)
    TextView tvHaveInput;
    @BindView(R.id.tv_num)
    TextView tvNum;

    @Override
    public int bindLayout() {
        return R.layout.activity_simple_intro;
    }

    @Override
    public void initData() {
        tvMainTitle.setText(getString(R.string.tv_simple_intr));
        tvLeft.setVisibility(View.VISIBLE);
        tvLeft.setText(getString(R.string.search_cancel));
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getString(R.string.done));
        tvRight.setOnClickListener(this);
        tvRight.setTextColor(ContextCompat.getColor(this, R.color.color_F1B36F));
        edSimpleIntro.addTextChangedListener(new InputTextWatcher(Const.INTEGER_200, edSimpleIntro, this, tvHaveInput));
        edSimpleIntro.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                MyLog.e("多行监听", actionId + "\t" + KeyEvent.KEYCODE_ENTER);
                return event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
        if (null != getIntent() && null != getIntent().getExtras()) {
            if (getIntent().getExtras().containsKey("simpleIntro")) {
                String simpleIntro = getIntent().getExtras().getString("simpleIntro");
                edSimpleIntro.setText(simpleIntro);
                if (!TextUtils.isEmpty(simpleIntro)){
                    edSimpleIntro.setSelection(simpleIntro.length());
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("intro", edSimpleIntro.getText().toString());
        setResult(Const.INTEGER_0, intent);
        finish();
    }

    @OnClick(R.id.tv_left)
    public void onViewCancle() {
        if (Utils.isClickable()) {
            finish();
        }
    }
}
