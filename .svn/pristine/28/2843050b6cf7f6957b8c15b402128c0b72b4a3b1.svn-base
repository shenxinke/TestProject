package com.yst.onecity.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.onecity.R;
import com.yst.onecity.adapter.AddProductAdapter;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.bean.ShoppingMallBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Request;

import static com.yst.onecity.activity.publish.EditShareActivity.mAllData;

/**
 * 添加商品页面全部fragment
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/6/9
 */

public class AllProductFragment extends BaseFragment implements OnLoadmoreListener, OnRefreshListener {
    @BindView(R.id.lv_product)
    ListView mLvProduct;
    @BindView(R.id.refresh_product)
    SmartRefreshLayout mRefreshProduct;
    @BindView(R.id.iv_empty)
    ImageView mIvEmpty;
    private AddProductAdapter mAdapter;
    private List<ShoppingMallBean.ContentBean> mLists = new ArrayList<>();
    private int page = 1;

    @Override
    public int bindLayout() {
        return R.layout.fragment_add_product;
    }

    @Override
    public void initData() {
        mRefreshProduct.setOnRefreshListener(this);
        mRefreshProduct.setOnLoadmoreListener(this);
        requestProductList();
        mAdapter = new AddProductAdapter(mLists, context, 0);
        mLvProduct.setAdapter(mAdapter);
    }
    /**
     * 商品列表
     */
    private void requestProductList() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "type", "0",
                "page", String.valueOf(page),
                "pageSize", "10",
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.COMMISSIONER_PRODUCT_LIST)
                .addParams("type", "0")
                .addParams("page", String.valueOf(page))
                .addParams("pageSize", "10")
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                showInfoProgressDialog();
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                dismissInfoProgressDialog();
                mRefreshProduct.finishLoadmore(500);
                mRefreshProduct.finishRefresh(500);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                mIvEmpty.setVisibility(View.VISIBLE);
                mRefreshProduct.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("response", "商品列表: " + response);
                if (response != null) {
                    ShoppingMallBean bean = new Gson().fromJson(response, ShoppingMallBean.class);
                    if (bean.getCode() == Const.INTEGER_1) {
                        if (bean.getContent().size() > 0) {
                            mIvEmpty.setVisibility(View.GONE);
                            mRefreshProduct.setVisibility(View.VISIBLE);
                            for (int i = 0; i < bean.getContent().size(); i++) {
                                for (int j = 0; j < mAllData.size() ; j++) {
                                    if (bean.getContent().get(i).getId() == mAllData.get(j).getId()){
                                        bean.getContent().get(i).setClick(true);
                                    }
                                }
                            }
                            mLists.addAll(bean.getContent());
                            mAdapter.notifyDataSetChanged();
                        } else {
                            if (page == Const.INTEGER_1) {
                                mIvEmpty.setVisibility(View.VISIBLE);
                                mRefreshProduct.setVisibility(View.GONE);
                            } else {
                                mIvEmpty.setVisibility(View.GONE);
                                mRefreshProduct.setVisibility(View.VISIBLE);
                                ToastUtils.show("已加载全部数据");
                            }
                        }
                    } else {
                        ToastUtils.show(bean.getMsg());
                    }
                }
            }
        });
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        mLists.clear();
        requestProductList();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        requestProductList();
    }

    public static AllProductFragment newInstance() {
        return new AllProductFragment();
    }
}
