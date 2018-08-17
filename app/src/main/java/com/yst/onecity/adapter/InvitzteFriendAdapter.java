package com.yst.onecity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.bean.FriendBean;
import com.yst.onecity.view.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 邀请注册的好友列表适配器
 *
 * @author wuxiaofang
 * @version 4.2.0
 * @date 2018/6/14
 */

public class InvitzteFriendAdapter extends BaseAdapter {
    private Context context;
    private List<FriendBean.ContentBean> friendList = new ArrayList<>();

    public InvitzteFriendAdapter(Context context) {
        this.context = context;
    }

    /**
     * 为集合添加数据
     */
    public void addData(List<FriendBean.ContentBean> friendList) {
        if (null != friendList) {
            this.friendList = friendList;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView) {
            convertView = View.inflate(context, R.layout.item_invitate_friiend, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(friendList.get(position).getAddress()).error(R.mipmap.head_2).into(viewHolder.ivInvitateHead);
        viewHolder.tvInvitateName.setText(friendList.get(position).getNickname());
        viewHolder.tvInvitateTime.setText(friendList.get(position).getCreated_time());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_invitate_head)
        RoundedImageView ivInvitateHead;
        @BindView(R.id.tv_invitate_name)
        TextView tvInvitateName;
        @BindView(R.id.tv_invitate_time)
        TextView tvInvitateTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
