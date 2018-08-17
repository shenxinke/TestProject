package com.yst.onecity.activity.servermember;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.fragment.ConsultReplyFragment;
import com.yst.onecity.fragment.ServiceSpecialistInteractionFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 新闻列表页面
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/12/18
 */
public class NewListActivity extends BaseActivity {

    @BindView(R.id.fl_message)
    FrameLayout flMessage;
    @BindView(R.id.tv_consult_news)
    TextView tvConsultNews;
    @BindView(R.id.tv_member_list)
    TextView tvMemberList;

    private ConsultReplyFragment consultReplyFragment;
    private ServiceSpecialistInteractionFragment memberListFragment;

    @Override
    public int bindLayout() {
        return R.layout.activity_new_list;
    }

    @Override
    public void initData() {
        initFragment1();
    }

    /**
     * 显示第一个fragment
     */
    private void initFragment1() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
        if (consultReplyFragment == null) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("isHunter",true);
            consultReplyFragment = new ConsultReplyFragment();
            consultReplyFragment.setArguments(bundle);
            transaction.add(R.id.fl_message, consultReplyFragment);
        }
        //隐藏所有fragment
        hideFragment(transaction);
        //显示需要显示的fragment
        transaction.show(consultReplyFragment);

        //提交事务
        transaction.commit();
    }

    private void initFragment2() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
        if (memberListFragment == null) {
            memberListFragment = new ServiceSpecialistInteractionFragment();
            transaction.add(R.id.fl_message, memberListFragment);
        }
        //隐藏所有fragment
        hideFragment(transaction);
        //显示需要显示的fragment
        transaction.show(memberListFragment);

        //提交事务
        transaction.commit();
    }

    /**
     * 隐藏所有的fragment
     * @param transaction 事物
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (consultReplyFragment != null) {
            transaction.hide(consultReplyFragment);
        }
        if (memberListFragment != null) {
            transaction.hide(memberListFragment);
        }
    }

    @OnClick({R.id.ll_back, R.id.tv_consult_news, R.id.tv_member_list})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_consult_news:
                tvConsultNews.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_yellow_6));
                tvMemberList.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_gray_6));
                tvConsultNews.setTextColor(0xffffffff);
                tvMemberList.setTextColor(0xffaaaaaa);
                initFragment1();
                break;
            case R.id.tv_member_list:
                tvConsultNews.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_gray_6_left));
                tvMemberList.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_yellow_6_right));
                tvConsultNews.setTextColor(0xffaaaaaa);
                tvMemberList.setTextColor(0xffffffff);
                initFragment2();
                break;
            default:
                break;
        }
    }
}
