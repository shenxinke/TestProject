package com.yst.onecity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.adapter.CommomFragmentPagerAdapter;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.bean.servermember.ProductSortBean;
import com.yst.onecity.callbacks.AbstractProductSortCallBack;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.viewpagerindicator.ViewPagerIndicator;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 商品列表分类
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class ServerMemberProductListFragment extends BaseFragment {

    ArrayList<String> items = new ArrayList<>();
    ArrayList<Fragment> fragments = new ArrayList<>();

    @BindView(R.id.indicator)
    ViewPagerIndicator indicator;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.empty)
    TextView empty;

    private int serverMemberId;
    private CommomFragmentPagerAdapter adapter;

    @Override
    public int bindLayout() {
        return R.layout.fragment_server_member_information;
    }

    @Override
    public void initData() {
        serverMemberId = getArguments().getInt("serverMemberId",-1);
        getSort();
    }



    private void getSort() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("timestamp",timestamp,"hid",String.valueOf(serverMemberId));
        OkHttpUtils.post().url(Constants.SERVER_MEMBER_PRODUCT_SORT)
                .addParams("hid",String.valueOf(serverMemberId))
                .addParams("timestamp",timestamp)
                .addParams("sign",sign)
                .addParams("client_type","A")
                .build().execute(new AbstractProductSortCallBack() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getActivity().getResources().getString(R.string.str_net_error_message));
            }

            @Override
            public void onResponse(ProductSortBean response, int id) {
                if (response != null && response.getCode() ==1){
                    if (response.getContent().size() <= 0){
                        empty.setVisibility(View.VISIBLE);
                        empty.setText("暂无商品");
                        viewpager.setVisibility(View.GONE);
                    }else{
                        empty.setVisibility(View.GONE);
                        viewpager.setVisibility(View.VISIBLE);
                    }
                    for (int i = 0; i < response.getContent().size(); i++) {
                        items.add(response.getContent().get(i).getClassifyName());
                        Bundle b = new Bundle();
                        b.putString("id",String.valueOf(response.getContent().get(i).getId()));
                        b.putString("hId",String.valueOf(serverMemberId));
                        ProductListFragment fragment = new ProductListFragment();
                        fragment.setArguments(b);
                        fragments.add(fragment);
                    }
                    adapter = new CommomFragmentPagerAdapter(getChildFragmentManager(), fragments, items);
                    viewpager.setAdapter(adapter);
                    indicator.bindViewPager(viewpager, false);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
