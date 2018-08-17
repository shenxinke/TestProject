package com.yst.onecity.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.bean.UserFocusBean;
import com.yst.onecity.config.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的关注适配器
 *
 * @author lixiangchao
 * @version 3.2.1
 * @date 2017/0/19
 */
public class UserFocusAdapter extends BaseAdapter {

    private List<UserFocusBean.ContentBean> mdata;
    private Activity mContext;
    private LayoutInflater inflater;

    public UserFocusAdapter(Activity context, List<UserFocusBean.ContentBean> data) {
        this.mContext = context;
        this.mdata = data;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mdata == null ? 0 : mdata.size();
    }

    @Override
    public Object getItem(int position) {
        return mdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.item_focus, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext).load(mdata.get(position).getAddress() == null ? "http://www.baidu.com" : Constants.URL_IMAGE_HEAD+mdata.get(position).getAddress()).error(R.mipmap.head_2).into(holder.rimgFocusHead);

        holder.txtFocusHandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancleFocus.unFocusHunter(position);
            }
        });
        holder.txtFocusName.setText(mdata.get(position).getNickname() == null ? "暂无昵称" : mdata.get(position).getNickname());
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.rimg_focus_head)
        ImageView rimgFocusHead;
        @BindView(R.id.txt_focus_name)
        TextView txtFocusName;
        @BindView(R.id.txt_focus_handle)
        TextView txtFocusHandle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    public interface CancleFocus{
        /**
         * 取消关注
         */
        void onClickListener();

        /**
         * 猎头关注
         * @param hunterId
         */
        void unFocusHunter(int hunterId);
    }
    private CancleFocus cancleFocus;
    public void setOnClickListener(CancleFocus cancleFocus){
        this.cancleFocus=cancleFocus;
    }

}
