package com.yst.onecity.activity.member;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.config.Const;
import com.yst.onecity.fragment.OnLineFragment;
import com.yst.onecity.fragment.ServiceOffLineFragment;
import com.yst.onecity.fragment.ServiceOnLineFragment;
import com.yst.onecity.fragment.UnLineFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的订单
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/12/18
 */
public class MyOrderActivity extends BaseActivity {

    @BindView(R.id.order_act_line)
    TextView line;
    @BindView(R.id.order_act_on_line)
    TextView onLine;
    Fragment currentFragment;
    /**
     * 0会员，1服务专员
     */
    private int flag = 0;

    @Override
    public int bindLayout() {
        return R.layout.activity_my_order;
    }

    @Override
    public void initData() {
        flag = getIntent().getExtras().getInt("flag");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = null;
        if (flag == 0) {
            if (getIntent().getExtras().getString(Const.CONS_STR_PAY) != null || getIntent().getExtras().getString(Const.CONS_STR_JUMPONLINEFRAGMENT) != null) {
                line.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_gray_6_left));
                onLine.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_yellow_6_right));
                line.setTextColor(ContextCompat.getColor(this, R.color.color_8F8F8F));
                onLine.setTextColor(ContextCompat.getColor(this, R.color.white));
                fragment = new OnLineFragment();
            } else {
                fragment = new UnLineFragment();
            }
        } else {
            fragment = new ServiceOffLineFragment();
        }
        transaction.replace(R.id.order_act_frame_layout, fragment).commit();
        currentFragment = fragment;
    }

    @OnClick(R.id.iv_back)
    public void closeClicked() {
        MyOrderActivity.this.finish();
    }

    @OnClick({R.id.order_act_line, R.id.order_act_on_line})
    public void onViewClicked(View view) {
        Fragment fragment = null;
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.order_act_line:
                line.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_yellow_6));
                onLine.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_gray_6));
                line.setTextColor(ContextCompat.getColor(this, R.color.white));
                onLine.setTextColor(ContextCompat.getColor(this, R.color.color_8F8F8F));
                if (flag == 0) {
                    fragment = new UnLineFragment();
                } else {
                    fragment = new ServiceOffLineFragment();
                }
                transaction.add(R.id.order_act_frame_layout, fragment);
                break;
            case R.id.order_act_on_line:
                line.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_gray_6_left));
                onLine.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_yellow_6_right));
                line.setTextColor(ContextCompat.getColor(this, R.color.color_8F8F8F));
                onLine.setTextColor(ContextCompat.getColor(this, R.color.white));
                if (flag == 0) {
                    fragment = new OnLineFragment();
                } else {
                    fragment = new ServiceOnLineFragment();
                }
                transaction.add(R.id.order_act_frame_layout, fragment);
                break;
            default:
                break;
        }
        transaction.hide(currentFragment).show(fragment).commit();
        currentFragment = fragment;
    }
}
