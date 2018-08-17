package com.yst.onecity.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.onecity.R;
import com.yst.onecity.activity.ProductDetailActivity;
import com.yst.onecity.adapter.RecycleStaggeredAdapter;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.bean.servermember.ProductListBean;
import com.yst.onecity.callbacks.AbstractProductListCallBack;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.CustomRecycleDecoration;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 商品列表 子fragment
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class ProductListFragment extends BaseFragment {

    @BindView(R.id.recycle)
    RecyclerView recyclerView;
    @BindView(R.id.swipe)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.empty)
    TextView empty;

    private RecycleStaggeredAdapter recycleAdapter;
    private List<ProductListBean.ContentBean> productList = new ArrayList<>();
    private CustomRecycleDecoration mCustomRecycleDecoration;
    /**分类id*/
    private String id;
    /**服务专员id*/
    private String hId;
    private boolean isRefresh = true;
    private int page = 1;
    private int rows = 10;

    @Override
    public int bindLayout() {
        return R.layout.fragment_product_list;
    }

    @Override
    public void initData() {

        id = getArguments().getString("id", "");
        hId = getArguments().getString("hId");
        initRequestData();
        initView();
    }

    private void initRequestData() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("timestamp", timestamp, "firsttypeid", id, "hId", hId,"page",String.valueOf(page),"rows",String.valueOf(rows));
        OkHttpUtils.post().url(Constants.SERVER_MEMBER_PRODUCT_LIST)
                .addParams("timestamp", timestamp)
                .addParams("firsttypeid", id)
                .addParams("hId", hId)
                .addParams("page",String.valueOf(page))
                .addParams("rows",String.valueOf(rows))
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractProductListCallBack() {

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (isRefresh) {
                    smartRefreshLayout.finishRefresh(500);
                } else {
                    smartRefreshLayout.finishLoadmore(1500);
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(ProductListBean response, int id) {

                if (response.getCode() == 1){
                    if (response.getContent()==null){
                        empty.setVisibility(View.VISIBLE);
                        return;
                    }
                    if (page == 1 && response.getContent().size()<1){
                        empty.setVisibility(View.VISIBLE);
                    }else {
                        empty.setVisibility(View.GONE);
                    }
                    productList.addAll(response.getContent());
                    recycleAdapter.notifyDataSetChanged();
                }else{
                    empty.setVisibility(View.VISIBLE);
//                    ToastUtils.show(response.getMsg());
                }
            }
        });
    }

    private void initView() {
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                isRefresh = true;
                productList.clear();
                recycleAdapter.notifyDataSetChanged();
                initRequestData();
            }
        });
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

                if (productList.size() < 1) {
                    page = 1;
                } else {
                    page++;
                }

                isRefresh = false;
                smartRefreshLayout.finishLoadmore();

            }
        });

        recycleAdapter = new RecycleStaggeredAdapter(productList, getActivity());
        recyclerView.setAdapter(recycleAdapter);

        recyclerView.setHasFixedSize(true);
        // 设置item动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        CustomRecycleDecoration decoration = new CustomRecycleDecoration(16, 2);
        recyclerView.addItemDecoration(decoration);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(CustomRecycleDecoration.Column, StaggeredGridLayoutManager.VERTICAL));


        recycleAdapter.setOnItemClickLitener(new RecycleStaggeredAdapter.OnItemClickLitener() {

            @Override
            public void onItemClick(View view, int position) {
                if (!Utils.isClickable()) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("productId",String.valueOf(productList.get(position).getId()));
                bundle.putString("hunterId",hId);
                bundle.putString("sortId",id);
                bundle.putString("spid",String.valueOf(productList.get(position).getSpid()));
                bundle.putString("merchantId",String.valueOf(productList.get(position).getMerchantId()));
                JumpIntent.jump(getActivity(), ProductDetailActivity.class,bundle);
            }
        });
    }
}
