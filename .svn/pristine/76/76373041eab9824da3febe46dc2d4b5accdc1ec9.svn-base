package com.yst.onecity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.bean.addressmanager.CitylistBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 二集列表的适配器
 *
 * @author wuxiaofang
 * @version 4.0.0
 * @date 2018/3/27
 */

public class MapCityAdater extends BaseExpandableListAdapter {
    @BindView(R.id.item_city_group)
    TextView itemCityGroup;
    private Context context;
    private List<CitylistBean> cityList = new ArrayList<>();

    public MapCityAdater(Context context) {
        this.context = context;
    }

    public void addData(List<CitylistBean> mcityList) {
        if (mcityList != null) {
            cityList.addAll(mcityList);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getGroupCount() {
        return cityList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return cityList.get(groupPosition).getCityList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return cityList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return cityList.get(groupPosition).getCityList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder = null;
        if (convertView == null) {
            groupViewHolder = new GroupViewHolder();
            convertView = View.inflate(context, R.layout.item_city_group, null);
            groupViewHolder.itemCityGroup = (TextView) convertView.findViewById(R.id.item_city_group);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.itemCityGroup.setText(cityList.get(groupPosition).getFirstName());
        return convertView;


    }

    private static class GroupViewHolder {

        TextView itemCityGroup;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder = null;
        if (convertView == null) {
            childViewHolder = new ChildViewHolder();
            convertView = View.inflate(context, R.layout.item_citychild, null);
            childViewHolder.itemCityChild = (TextView) convertView.findViewById(R.id.item_city_child);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.itemCityChild.setText(cityList.get(groupPosition).getCityList().get(childPosition));
        return convertView;

    }

    private static class ChildViewHolder {
        TextView itemCityChild;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
