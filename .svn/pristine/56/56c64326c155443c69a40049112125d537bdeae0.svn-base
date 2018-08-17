package com.yst.onecity.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.activity.member.AddAddressActivity;
import com.yst.onecity.bean.AddressEntity;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.JumpIntent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 地址管理adapter
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/3/14.
 */
public class AddressManageAdapter extends BaseAdapter {
    private List<AddressEntity> dataBean = new ArrayList<>();
    private int defaultCount = 0;
    private Activity mContext;

    public AddressManageAdapter(List<AddressEntity> datas, Activity mContext) {
        if (dataBean != null) {
            this.dataBean = datas;
        } else {
            this.dataBean = new ArrayList<>();
        }
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        if (dataBean.size() != 0) {
            return dataBean.size();
        }
        return defaultCount;
    }

    @Override
    public Object getItem(int position) {
        return dataBean.get(position);
    }

    public void setData(List<AddressEntity> data) {
        if (data != null) {
            this.dataBean = data;
            this.notifyDataSetChanged();

        } else {
            this.dataBean = new ArrayList<>();
            this.notifyDataSetChanged();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder mGridItemHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_address_manage_list, null);
            mGridItemHolder = new ViewHolder(convertView);
            convertView.setTag(mGridItemHolder);
        }
        mGridItemHolder = (ViewHolder) convertView.getTag();
        mGridItemHolder.ivEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("edit", "edit");
                bundle.putString("name", dataBean.get(position).getUserName());
                bundle.putString("phone", dataBean.get(position).getMobile());
                bundle.putString("detailAddress", dataBean.get(position).getDetailAddress());
                bundle.putString("isdefault", dataBean.get(position).getIsDefault() + "");
                bundle.putString("provinceid", dataBean.get(position).getPid() + "");
                bundle.putString("cityid", dataBean.get(position).getCid() + "");
                bundle.putString("areaid", dataBean.get(position).getDid() + "");
                bundle.putString("pname", dataBean.get(position).getPname());
                bundle.putString("cname", dataBean.get(position).getCname());
                bundle.putString("dname", dataBean.get(position).getDname());
                bundle.putString("position", dataBean.get(position).getPosition());
                bundle.putString("addressId", dataBean.get(position).getId() + "");
                bundle.putString("longitute", dataBean.get(position).getLongitude());
                bundle.putString("lantitute", dataBean.get(position).getLatitude());

                JumpIntent.jump(mContext, AddAddressActivity.class, bundle);
            }
        });
        ConstUtils.setTextString(dataBean.get(position).getUserName(), mGridItemHolder.tvName);
        ConstUtils.setTextString(dataBean.get(position).getMobile(), mGridItemHolder.tvPhone);
        ConstUtils.setTextString(dataBean.get(position).getDetailAddress(), mGridItemHolder.tvAddress);
        if (dataBean.get(position).getIsDefault() == 1) {
            //默认
            mGridItemHolder.tvMoren.setVisibility(View.VISIBLE);
        } else {
            mGridItemHolder.tvMoren.setVisibility(View.GONE);
        }
        mGridItemHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iGetScrollPosition != null) {
                    iGetScrollPosition.delete(position);
                }
            }
        });
        return convertView;
    }

    private IGetScrollPosition iGetScrollPosition;

    public void setIScrollPositon(IGetScrollPosition iGetScrollPosition) {
        this.iGetScrollPosition = iGetScrollPosition;
    }

    public interface IGetScrollPosition {
        /**
         * 点击加号回掉
         * @param position
         */
        void delete(int position);

    }

    public class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.iv_moren)
        ImageView tvMoren;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_delete)
        TextView tvDelete;
        @BindView(R.id.iv_edit_address)
        ImageView ivEditAddress;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
