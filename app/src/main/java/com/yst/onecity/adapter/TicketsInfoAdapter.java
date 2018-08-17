package com.yst.onecity.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.activity.tickets.TkPreviewDetailActivity;
import com.yst.onecity.bean.tickets.TableTicketsBean;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.JumpIntent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 奖项名单信息适配器
 *
 * @author WangJingWei
 * @version 4.0.0
 * @date 2018/3/22.
 */
public class TicketsInfoAdapter extends BaseAdapter {

    List<TableTicketsBean.ContentBean.RecordBean.NameListBean> mData;
    private Context mContext;
    private int mPosition;

    public TicketsInfoAdapter(Context context, List<TableTicketsBean.ContentBean.RecordBean.NameListBean> data,int position) {
        this.mData = data;
        this.mContext = context;
        this.mPosition = position;
    }

    @Override
    public int getCount() {
        return null == mData ? 0 : mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder mViewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_tickets_info, null);
            mViewHolder = new ViewHolder(view);
            view.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder) view.getTag();
        }

        TableTicketsBean.ContentBean.RecordBean.NameListBean ticketsListInfo = mData.get(position);

        mViewHolder.tvName.setText(ConstUtils.getStringNoEmpty(ticketsListInfo.getNickname()));
        mViewHolder.tvPhone.setText(ConstUtils.settingPhone(ConstUtils.getStringNoEmpty(ticketsListInfo.getPhone())));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConstUtils.changeAwardsContent(mPosition);
                Bundle bundle = new Bundle();
                bundle.putString("name", ConstUtils.getStringNoEmpty(ConstUtils.changeAwardsContent(mPosition)));
                bundle.putString("awards",String.valueOf(mPosition));
                JumpIntent.jump((Activity) mContext,TkPreviewDetailActivity.class,bundle);
            }
        });

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tvPhone;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
