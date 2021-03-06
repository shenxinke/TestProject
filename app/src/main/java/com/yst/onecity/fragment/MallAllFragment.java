package com.yst.onecity.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.ProductDetailActivity;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.adapter.ShoppingMallAdapter;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.bean.ShoppingMallBean;
import com.yst.onecity.bean.product.ProductSortStandardBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.fragment.popfragment.AddCartPopFragment;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.RxCode;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import gorden.rxbus2.RxBus;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 商城页面
 *
 * @author Shenxinke
 * @version 4.2.0
 * @data 2018/5/31
 */

public class MallAllFragment extends BaseFragment {
    private ShoppingMallAdapter shoppingMallAdapter;
    private List<ShoppingMallBean.ContentBean> contentList = new ArrayList<>();
    private String id;
    private int pageNum = 1;
    private int rows = 10;

    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView rvInformation;
    @BindView(R.id.tv_no_shopping)
    TextView tvNoShopping;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.get("id").toString();
        }
        MyLog.e("aaa", "id____" + id);
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_shopping_mall_item;
    }

    @Override
    public void initData() {
        getShoppingMallList();

        rvInformation.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        shoppingMallAdapter = new ShoppingMallAdapter();
        rvInformation.setAdapter(shoppingMallAdapter);
        shoppingMallAdapter.setShoppingMallListener(new ShoppingMallAdapter.ShoppingMallListener() {
            @Override
            public void btShoppingCart(int position, String status) {
                if (!TianyiApplication.isLogin) {
                    JumpIntent.jump((Activity) context, LoginActivity.class);
                    return;
                }
                if (Utils.isClickable()) {
                    getGuige(status, position);
                }
            }

            @Override
            public void btShoppingStory(int position, String status) {
                Bundle bundle = new Bundle();
                bundle.putString("productId", String.valueOf(contentList.get(position).getId()));
                bundle.putInt(Const.STR_STORY_INTENT, Const.INTEGER_3);
                JumpIntent.jump(getActivity(), ProductDetailActivity.class, bundle);
            }

            @Override
            public void onItemClick(int position) {
                if (Utils.isClickable()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("productId", String.valueOf(contentList.get(position).getId()));
                    JumpIntent.jump(getActivity(),ProductDetailActivity.class,bundle);
                }
            }
        });

        initListener();
    }

    /**
     * 创建一个frament的实例，类型id
     *
     * @param id 类别id、
     * @return 具体fragment
     */
    public static MallAllFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("id", id);
        MallAllFragment fragment = new MallAllFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 获取列表数据
     */
    public void getShoppingMallList() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "productTypeId", id,
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "type", Const.STR2,
                "page", String.valueOf(pageNum),
                "rows", String.valueOf(rows),
                "timestamp", timestamp);
        if (TextUtils.isEmpty(sign)) {
            return;
        }
        OkHttpUtils.post()
                .url(Constants.COMMISSIONER_PRODUCT_LIST)
                .addParams("productTypeId", id)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("rows", String.valueOf(rows))
                .addParams("page", String.valueOf(pageNum))
                .addParams("type", Const.STR2)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .addParams("timestamp", timestamp)
                .build().execute(new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.tv_net_error));
            }

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                showInfoProgressDialog();
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                dismissInfoProgressDialog();
            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("sss", "-商城分类列表数据: " + response);
                if (response != null) {
                    ShoppingMallBean shoppingMallBean = new Gson().fromJson(response, ShoppingMallBean.class);
                    if (shoppingMallBean.getCode() == Const.INTEGER_1) {
                        List<ShoppingMallBean.ContentBean> content = shoppingMallBean.getContent();
                        contentList.addAll(content);
                        shoppingMallAdapter.addList(contentList, Const.INTEGER_0);
                        if (content.size() > Const.INTEGER_0 || contentList.size() > Const.INTEGER_0) {
                            tvNoShopping.setVisibility(View.GONE);
                        } else {
                            tvNoShopping.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    ToastUtils.show(getString(R.string.tv_check_service));
                }
            }
        });
    }

    /**
     * 上拉加载下拉刷新
     */
    private void initListener() {
        smartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageNum++;
                getShoppingMallList();
                smartRefreshLayout.finishLoadmore(1000);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNum = 1;
                if (contentList != null) {
                    contentList.clear();
                }
                getShoppingMallList();
                smartRefreshLayout.finishRefresh(500);
            }
        });
    }

    /**
     * 获取商品规格
     */
    private void getGuige(final String pId, final int position) {
        final String productId = String.valueOf(pId);
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("timestamp", timestamp,
                "pId", productId, "merchantid", Const.STR1);
        OkHttpUtils.post().url(Constants.GET_PRODUCT_SORT_STANDARD)
                .addParams("timestamp", timestamp)
                .addParams("pId", productId)
                .addParams("merchantid", Const.STR1)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("sss", "-规格：" + response);
                if (response != null) {
                    ProductSortStandardBean standardBean = new Gson().fromJson(response, ProductSortStandardBean.class);
                    if (null != standardBean.getContent() && standardBean.getCode() == Const.INTEGER_1) {
                        if (standardBean.getContent().getStandard().size() > 1) {
                            showAddCartDialog(productId, Const.STR1);
                        } else {
                            int standardId = standardBean.getContent().getStandard().get(0).getStandardId();
                            int classId = standardBean.getContent().getClassify().get(0).getClassId();
                            int kuNum = standardBean.getContent().getStandard().get(0).getKuNum();
                            addCart(Const.STR1, String.valueOf(standardId), String.valueOf(classId));
                        }
                    }
                } else {
                    ToastUtils.show(getString(R.string.tv_check_service));
                }
            }
        });
    }

    /**
     * 显示购物车弹框
     *
     * @param productId  商品id
     * @param merchantId merchantId
     */
    private void showAddCartDialog(String productId, String merchantId) {
        AddCartPopFragment popFragment = AddCartPopFragment.newInstance(productId, merchantId, "", "", "", "", "", "", "");
        popFragment.show(getFragmentManager(), "goodsFragment");
    }

    /**
     * 添加购物车
     */
    private void addCart(String num, String standardId, String classId) {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("timestamp", timestamp,
                "phone", TianyiApplication.appCommonBean.getPhone()
                , "uuid", TianyiApplication.appCommonBean.getUuid(),
                "spid", standardId,
                "stid", classId,
                "num", num,
                "hunterid", "");
        OkHttpUtils.post().url(Constants.ADD_SHOP_CART)
                .addParams("timestamp", timestamp)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("spid", standardId)
                .addParams("stid", classId)
                .addParams("num", num)
                .addParams("hunterid", "")
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject obj = new JSONObject(response);
                    int code = obj.getInt("code");
                    if (code == 1) {
                        RxBus.get().send(RxCode.CODE_PUBLISH_ADD_SHOPPING_CARD);
                        ToastUtils.show("成功加入购物车");
                    } else {
                        ToastUtils.show(obj.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
