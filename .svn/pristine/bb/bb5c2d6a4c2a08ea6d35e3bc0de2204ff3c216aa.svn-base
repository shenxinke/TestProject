package com.yst.onecity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.view.MyListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 线上订单适配器
 *
 * @author chenxiaowei
 * @version 3.2.1
 * @date 2017/0/19
 */
public class OnLineOrderLvAdapter extends BaseAdapter {

    private Context context;
    private List<Integer> data;

    public OnLineOrderLvAdapter(Context context, List<Integer> data){
        this.context = context;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_on_line_listview, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.shopName.setText(data.get(i).toString());
        OnLineOrderItemMyLvAdapter lvAdapter = new OnLineOrderItemMyLvAdapter(context, data);
        holder.myListView.setAdapter(lvAdapter);
        return view;
    }

    class ViewHolder{

        @BindView(R.id.item_on_line_img)
        ImageView image;
        @BindView(R.id.item_on_line_shop_name)
        TextView shopName;
        @BindView(R.id.item_on_line_state)
        TextView state;
        @BindView(R.id.item_line_order_mylv)
        MyListView myListView;
        @BindView(R.id.item_on_line_num)
        TextView number;
        @BindView(R.id.item_on_line_price)
        TextView price;
        @BindView(R.id.item_on_line_reward)
        TextView reward;
        @BindView(R.id.item_on_line_pay)
        Button pay;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
