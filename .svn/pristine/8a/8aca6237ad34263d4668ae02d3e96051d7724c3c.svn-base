package com.yst.onecity.fragment;

import android.widget.ListView;

import com.yst.onecity.R;
import com.yst.onecity.adapter.AbstractCommonAdapter;
import com.yst.onecity.adapter.CommonViewHolder;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.config.Const;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页资讯
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class SearchProductFragment extends BaseFragment {
    @BindView(R.id.listView)
    ListView listView;

    AbstractCommonAdapter<String> adapter;
    List<String> datas = new ArrayList<>();

    @Override
    public int bindLayout() {
        return R.layout.fragment_search_product;
    }

    @Override
    public void initData() {
        for (int i = 0; i < Const.INTEGER_8 ; i++) {
            datas.add(""+i);
        }

        adapter = new AbstractCommonAdapter<String>(getContext(),datas,R.layout.item_serach_product) {
            @Override
            public void convert(CommonViewHolder holder, int position, String item) {

            }
        };

        listView.setAdapter(adapter);
    }

}
