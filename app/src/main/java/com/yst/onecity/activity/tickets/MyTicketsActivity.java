package com.yst.onecity.activity.tickets;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.fragment.TkHasLotteryFragment;
import com.yst.onecity.fragment.TkNoLotteryFragment;
import com.yst.onecity.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnPageChange;

/**
 * 我的奖券页面
 *
 * @author WangJingWei
 * @version 4.0.0
 * @date 2018/3/20.
 */
public class MyTicketsActivity extends BaseActivity {

    @BindView(R.id.activity_title_tv)
    TextView activityTitleTv;
    @BindView(R.id.activity_back_iv)
    ImageView imgBack;
    @BindView(R.id.tab_main)
    TabLayout mTabLayout;
    @BindView(R.id.pager)
    ViewPager mPager;
    @BindArray(R.array.my_tickets)
    String[] tabNameStrArr;

    private FragmentPagerAdapter myFragmentPagerAdapter;
    private List<Fragment> listFragments = new ArrayList<>();
    private TkHasLotteryFragment mTicketsHasLFragment;
    private TkNoLotteryFragment mTicketsNoLFragment;

    @Override
    public int bindLayout() {
        return R.layout.activity_my_tickets;
    }

    @Override
    public void initData() {
        activityTitleTv.setText(getString(R.string.my_tickets));
        myFragmentPagerAdapter = new MyFragmentAdapter(getSupportFragmentManager());
        mPager.setAdapter(myFragmentPagerAdapter);
        initFragment();
        initTab();
    }

    @OnClick(R.id.activity_back_iv)
    public void toBack(){
        finish();
    }

    /**
     * 告诉未开奖广播消息是否继续轮播
     * @param position
     */
    @OnPageChange(R.id.pager)
    public void onPageSelected(int position){
        mTicketsNoLFragment.onSelectPosition(position);
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        mTicketsNoLFragment = new TkNoLotteryFragment();
        mTicketsHasLFragment = new TkHasLotteryFragment();
        listFragments.add(mTicketsNoLFragment);
        listFragments.add(mTicketsHasLFragment);
    }

    /**
     * 初始化TAB标签
     */
    private void initTab() {
        mTabLayout.setupWithViewPager(mPager);
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            mTabLayout.getTabAt(i).setCustomView(getTabView(i));
        }
        Utils.setIndicator(mTabLayout,50,50);
    }

    /**
     * 获得每一个Tab的View
     *
     * @param i
     * @return
     */
    private View getTabView(int i) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_tickets_type, null);
        TextView mTextView = (TextView) view.findViewById(R.id.tv_tab);
        mTextView.setText(tabNameStrArr[i]);
        return view;
    }

    private class MyFragmentAdapter extends FragmentPagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return listFragments.get(position);
        }

        @Override
        public int getCount() {
            return tabNameStrArr.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabNameStrArr[position];
        }
    }
}
