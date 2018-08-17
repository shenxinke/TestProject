package com.yst.onecity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.bean.AdressBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 服务范围数据的适配器
 *
 * @author wuxiaofang
 * @version 4.2.0
 * @date 2018/6/4
 */

public class ServiceAdapter extends BaseAdapter {
    private Context context;
    private List<AdressBean> addressList = new ArrayList<>();

    public ServiceAdapter(Context context) {
        this.context = context;
    }

    /**
     * 为集合添加数据
     *
     * @param addressList 有数据的集合
     */
    public void addData(List<AdressBean> addressList) {
        if (null != addressList) {
            this.addressList.clear();
            this.addressList.addAll(addressList);
            notifyDataSetChanged();
        }

    }

    @Override
    public int getCount() {
        return addressList.size();
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
            convertView = View.inflate(context, R.layout.item_address, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvProCityDis.setText(addressList.get(position).getProvince());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_pro_city_dis)
        TextView tvProCityDis;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
