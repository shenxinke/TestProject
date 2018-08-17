package com.yst.onecity.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.WindowUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 网格适配器
 *
 * @author wuxiaofang
 * @version 4.2.0
 * @date 2018/06/02
 */

public class GridAdapter extends BaseAdapter {
    private String[] mList;
    private Context mContext;

    public GridAdapter(String[] list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        MyLog.e("aaa","--"+mList.length);
        return mList.length;
    }

    @Override
    public Object getItem(int position) {
        return mList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (null == convertView) {
            convertView = View.inflate(mContext, R.layout.item_grid, null);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        ViewGroup.LayoutParams layoutParams = mViewHolder.ivShare.getLayoutParams();
        int width = (WindowUtils.getScreenWidth((Activity) mContext) - WindowUtils.dip2px(mContext, 45)) / 3;
        layoutParams.width = width;
        layoutParams.height = width;
        mViewHolder.ivShare.setLayoutParams(layoutParams);
        for (int i = 0; i <mList.length ; i++) {
            MyLog.e("sss","==: "+mList[position]);
        }
        Glide.with(mContext).load(mList[position]).error(R.mipmap.img_default_p).into(mViewHolder.ivShare);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_share)
        ImageView ivShare;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
