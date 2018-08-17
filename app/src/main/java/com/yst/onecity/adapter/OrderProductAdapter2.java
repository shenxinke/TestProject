package com.yst.onecity.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import butterknife.ButterKnife;

/**
 * 订单商品适配器
 *
 * @author chenxiaowei
 * @version 3.2.1
 * @date 2017/0/19
 */
public class OrderProductAdapter2 extends BaseExpandableListAdapter {

    @Override
    public int getGroupCount() {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int childPosition, boolean isExpanded, View convertView, ViewGroup viewGroup) {
        if (convertView==null){
///            convertView= LayoutInflater.from(R.layout_item.item_group_order);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup viewGroup) {
        if (convertView==null){
///            convertView= LayoutInflater.from(R.layout_item.item_product);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class GroupViewHolder {

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ChildViewHolder {
        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
