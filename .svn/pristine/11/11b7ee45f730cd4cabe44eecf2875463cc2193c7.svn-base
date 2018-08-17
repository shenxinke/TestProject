package com.yst.onecity.adapter.malladapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.yst.onecity.utils.MyLog;

import java.util.ArrayList;

/**
 * 商城页面的viewpager的适配器
 *
 * @author wuxiaofang
 * @version 3.1.0
 * @date 2018/3/20
 */

public class ViewpagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<View> gridviewList = new ArrayList<>();

    public ViewpagerAdapter(Context context) {
        this.context = context;
    }

    /**
     * 为集合添加数据
     *
     * @param mList 数据集合
     */
    public void addVpData(ArrayList<View> mList) {
        if (mList != null) {
            gridviewList = mList;
            notifyDataSetChanged();
        }
    }

    /**
     * 解决数据不刷新的问题
     *
     * @param object tiaomu
     * @return int 条目id
     */
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        MyLog.e("sss", "-gridviewList:" + gridviewList.size());
        return gridviewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(gridviewList.get(position));
        return gridviewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
