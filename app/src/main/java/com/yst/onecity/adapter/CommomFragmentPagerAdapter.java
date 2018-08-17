package com.yst.onecity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yst.onecity.utils.MyLog;

import java.util.ArrayList;

/**
 * 通用viewpager Fragment适配器
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/19.
 */
public class CommomFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;
    private String[] titles;

    public CommomFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, ArrayList<String> titleArr) {
        super(fm);
        notifyDataSetChanged();
        this.fragments = fragments;
        titles = new String[titleArr.size()];
        for (int i = 0; i < titleArr.size(); i++) {
            titles[i] = titleArr.get(i);
        }

    }
    public CommomFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, String[] titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles == null){
            return "";
        }
        MyLog.e("sss","--title: "+titles[position]);
        return titles[position];
    }
}
