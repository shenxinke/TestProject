package com.yst.onecity.fragment;

import android.os.Bundle;
import android.widget.ListView;

import com.yst.onecity.R;
import com.yst.onecity.adapter.AbstractCommonAdapter;
import com.yst.onecity.adapter.CommonViewHolder;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.config.Const;
import com.yst.onecity.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 订单列表页面
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class OrderListFragment extends BaseFragment {

    @BindView(R.id.listView)
    ListView listView;
    private List<String> productData = new ArrayList<>();
    private List<String> data = new ArrayList<>();
    private AbstractCommonAdapter<String> adapter;
    private AbstractCommonAdapter<String> productAdapter;

    public static OrderListFragment getInstance(int position) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_order;
    }

    @Override
    public void initData() {
        for (int i = 0; i < Const.INTEGER_5; i++) {
            data.add("-");
        }
        for (int i = 0; i < Const.INTEGER_2; i++) {
            productData.add("0");
        }
    }

    @Override
    public void initControll() {
        adapter = new AbstractCommonAdapter<String>(context, data, R.layout.item_order_list) {
            @Override
            public void convert(CommonViewHolder holder, int position, String item) {
                MyListView listView = holder.getView(R.id.listView);
                initCtrl(listView);
            }
        };
        listView.setAdapter(adapter);
    }

    private void initCtrl(MyListView listView) {
        productAdapter = new AbstractCommonAdapter<String>(context, productData, R.layout.item_add_product) {
            @Override
            public void convert(CommonViewHolder holder, int position, String item) {

            }
        };
        listView.setAdapter(productAdapter);
    }
}
