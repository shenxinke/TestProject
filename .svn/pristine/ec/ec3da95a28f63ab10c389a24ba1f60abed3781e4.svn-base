package com.yst.onecity.activity.mall;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.AddCartPopuwindow;
import com.yst.onecity.activity.ProductDetailActivity;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.adapter.malladapter.GoodsListAdapter;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.mall.GoodsListBean;
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
import butterknife.OnClick;
import gorden.rxbus2.RxBus;
import gorden.rxbus2.Subscribe;
import gorden.rxbus2.ThreadMode;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 商城的商品列表
 *
 * @author wuxiaofang
 * @version 4.0.0
 * @date 2018/3/21
 */

public class GoodsListActivity extends BaseActivity implements AdapterView.OnItemClickListener {


    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.tv_sale_count)
    TextView tvSaleCount;
    @BindView(R.id.iv_count)
    ImageView ivCount;
    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @BindView(R.id.iv_price)
    ImageView ivPrice;
    @BindView(R.id.tv_goods_update)
    TextView tvGoodsUpdate;
    @BindView(R.id.iv_update)
    ImageView ivUpdate;
    @BindView(R.id.tv_goods_pinkage)
    TextView tvGoodsPinkage;
    @BindView(R.id.ll_include)
    LinearLayout llInclude;
    @BindView(R.id.gv_goodslist)
    GridView gvGoodslist;
    @BindView(R.id.refresh_goodslist)
    SmartRefreshLayout refreshGoodslist;
    @BindView(R.id.parent)
    RelativeLayout parent;
    @BindView(R.id.ll_sale_count)
    LinearLayout llSaleCount;
    @BindView(R.id.ll_goods_price)
    LinearLayout llGoodsPrice;
    @BindView(R.id.ll_goods_update)
    LinearLayout llGoodsUpdate;
    @BindView(R.id.img_empty)
    ImageView imgEmpty;

    private boolean isCountClick = true;
    private boolean isPriceClick = true;
    private boolean isUpdateClick = true;
    private boolean isPinkClick = true;
    private GoodsListAdapter adapter;
    private ArrayList<GoodsListBean> listBeen;
    private ArrayList<GoodsListBean> emptyList = new ArrayList<>();
    private int id;
    private String string = "";
    private int page = 1;
    private String type;
    private int chooseType;
    private int pageSize = 10;
    private List<GoodsListBean.ContentBean.ProductBean> productList;
    private List<GoodsListBean.ContentBean.ProductBean> mList = new ArrayList<>();
    private static final String CLASSIFITY = "classifity";
    private static final String BRAND = "brand";

    private int standardId;
    private int classId;

    /**
     * 0 搜索 info必传
     * 1 id 为分类
     * 2 id 为品牌
     */
    private int flag;
    /**
     * 0:销量降序 1:销量升序
     * 2:价格降序 3:价格升序
     * 4:上新降序 5:上新升序
     * 6:包邮
     */
    private int orderType;
    private String content;
    private AddCartPopuwindow addCartPopuwindow;
    private AddCartPopFragment fragment;


    @Override
    public int bindLayout() {
        return R.layout.activity_goodslist_mall;
    }


    @Override
    public void initData() {
        RxBus.get().register(this);
        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("type");
        id = bundle.getInt("id");
        String name = bundle.getString("name");
        initTitleBar(name);
        if (CLASSIFITY.equals(type)) {
            flag = 1;
            orderType = 0;
            requestGoodsListByClassfity(flag, id, page, orderType);
        } else if (BRAND.equals(type)) {
            flag = 2;
            orderType = 0;
            requestGoodsListByBrand(flag, id, page, orderType);
        } else {
            content = bundle.getString("content");
            flag = 0;
            orderType = 0;
            initTitleBar("商品列表");
            requestDataBySearch(flag, page, orderType, pageSize, content);
        }
        adapter = new GoodsListAdapter(this);
        gvGoodslist.setAdapter(adapter);
        gvGoodslist.setOnItemClickListener(this);
        adapter.initInterface(new GoodsListAdapter.ShowDialogInterface() {
            @Override
            public void showDialog(int position) {
                MyLog.e("sss", "-position: " + position);
                getGuige(mList.get(position).getId(), position);
            }
        });
        refreshGoodslist.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                if (CLASSIFITY.equals(type)) {
                    requestGoodsListByClassfity(flag, id, page, orderType);
                } else if (BRAND.equals(type)) {
                    requestGoodsListByBrand(flag, id, page, orderType);
                } else {
                    requestDataBySearch(flag, page, orderType, pageSize, content);
                }
                refreshGoodslist.finishLoadmore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                if (CLASSIFITY.equals(type)) {
                    requestGoodsListByClassfity(flag, id, page, orderType);
                } else if (BRAND.equals(type)) {
                    requestGoodsListByBrand(flag, id, page, orderType);
                } else {
                    requestDataBySearch(flag, page, orderType, pageSize, content);
                }
                refreshGoodslist.finishRefresh();
            }
        });
    }

    /**
     * 获取商品规格
     */
    private void getGuige(final int pId, final int position) {
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
                    if (standardBean.getContent().getStandard().size() > 1) {
                        showAddCartDialog(productId, Const.STR1);
                    } else {
                        standardId = standardBean.getContent().getStandard().get(0).getStandardId();
                        classId = standardBean.getContent().getClassify().get(0).getClassId();
                        if (TianyiApplication.isLogin) {
                            addCart(Const.STR1, String.valueOf(standardId), String.valueOf(classId));
                        } else {
                            Bundle bundle = new Bundle();
                            bundle.putString("addcart", "addcart");
                            JumpIntent.jump(GoodsListActivity.this, LoginActivity.class, bundle);
                        }
                    }
                } else {
                    ToastUtils.show(getString(R.string.tv_check_service));
                }
            }
        });
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

    /**
     * 搜索
     */
    private void requestDataBySearch(int flag, final int page, int type, int pageSize, String info) {
        String flagString = String.valueOf(flag);
        String pageString = String.valueOf(page);
        String typeString = String.valueOf(type);
        String pageSizeString = String.valueOf(pageSize);

        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "page", pageString,
                "type", typeString,
                "pageSize", pageSizeString,
                "flag", flagString,
                "info", info,
                "timestamp", timestamp);
        OkHttpUtils
                .post()
                .url(Constants.GET_GOODSLIST_BYCLASSFITY)
                .addParams("type", typeString)
                .addParams("page", pageString)
                .addParams("info", info)
                .addParams("flag", flagString)
                .addParams("pageSize", pageSizeString)
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
                MyLog.e("sss", "-搜索： " + response);
                if (response != null) {
                    GoodsListBean brandBean = new Gson().fromJson(response, GoodsListBean.class);
                    if (brandBean.getCode() == 1) {
                        productList = brandBean.getContent().getProduct();
                        if (page == 1) {
                            mList.clear();
                        }
                        mList.addAll(productList);
                        adapter.addData(mList);
                        if (mList.size() == 0) {
                            imgEmpty.setVisibility(View.VISIBLE);
                        } else {
                            imgEmpty.setVisibility(View.GONE);
                        }
                    } else {
                        ToastUtils.show(brandBean.getMsg());
                    }
                } else {
                    ToastUtils.show(getString(R.string.tv_check_service));
                }
            }
        });
    }

    /**
     * 通过品牌获取商品列表
     *
     * @param id   id
     * @param page 当前页码
     * @param type 类型
     */
    private void requestGoodsListByBrand(int flag, int id, final int page, int type) {
        switch (type) {
            case 0:
                string = "按照销量降序";
                break;
            case 1:
                string = "销量升序";
                break;
            case 2:
                string = "价格降序";
                break;
            case 3:
                string = "价格升序";
                break;
            case 4:
                string = "上新降序";
                break;
            case 5:
                string = "上新升序";
                break;
            case 6:
                string = "包邮";
                break;
            default:
                break;
        }
        String flagString = String.valueOf(flag);
        String brandId = String.valueOf(id);
        String brandPage = String.valueOf(page);
        String brandType = String.valueOf(type);
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "id", brandId,
                "flag", flagString,
                "page", brandPage,
                "type", brandType,
                "timestamp", timestamp);
        OkHttpUtils
                .post()
                .url(Constants.GET_GOODSLIST_BYCLASSFITY)
                .addParams("id", brandId)
                .addParams("flag", flagString)
                .addParams("type", brandType)
                .addParams("page", brandPage)
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
                MyLog.e("sss", "-品牌商品列表 " + string + " :" + response);
                if (response != null) {
                    GoodsListBean brandBean = new Gson().fromJson(response, GoodsListBean.class);
                    if (brandBean.getCode() == 1) {
                        productList = brandBean.getContent().getProduct();
                        if (page == 1) {
                            mList.clear();
                        }
                        mList.addAll(productList);
                        adapter.addData(mList);
                        if (mList.size() > 0) {
                            imgEmpty.setVisibility(View.GONE);
                        } else {
                            imgEmpty.setVisibility(View.VISIBLE);
                        }
                    } else {
                        ToastUtils.show(brandBean.getMsg());
                    }
                } else {
                    ToastUtils.show(getString(R.string.tv_check_service));
                }
            }
        });
    }

    @Subscribe(code = RxCode.ADD_CART, threadMode = ThreadMode.MAIN)
    public void addcart() {
        if (fragment != null) {
            MyLog.e("sss", "addcart11111");
            fragment.addShopCart();
        } else {
            MyLog.e("sss", "addcart22222");
            addCart(Const.STR1, String.valueOf(standardId), String.valueOf(classId));
        }
    }

    private void showAddCartDialog(String productId, String merchantId) {
        fragment = AddCartPopFragment.newInstance(productId, merchantId, "","","", "", "", "", "");
        fragment.show(getSupportFragmentManager(), "goodsLIst");
    }


    /**
     * 根据分类id请求数据
     *
     * @param id   id
     * @param page 当前页码
     * @param type 类型
     */
    private void requestGoodsListByClassfity(int flag, int id, final int page, int type) {
        switch (type) {
            case 0:
                string = "按照销量降序";
                break;
            case 1:
                string = "销量升序";
                break;
            case 2:
                string = "价格降序";
                break;
            case 3:
                string = "价格升序";
                break;
            case 4:
                string = "上新降序";
                break;
            case 5:
                string = "上新升序";
                break;
            case 6:
                string = "包邮";
                break;
            default:
                break;
        }
        String flagString = String.valueOf(flag);
        String classfityId = String.valueOf(id);
        String goodslistPage = String.valueOf(page);
        String goodslistType = String.valueOf(type);
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "id", classfityId,
                "flag", flagString,
                "page", goodslistPage,
                "type", goodslistType,
                "timestamp", timestamp);
        OkHttpUtils
                .post()
                .url(Constants.GET_GOODSLIST_BYCLASSFITY)
                .addParams("id", classfityId)
                .addParams("flag", flagString)
                .addParams("type", goodslistType)
                .addParams("page", goodslistPage)
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
                MyLog.e("sss", "-分类商品列表 " + string + " :" + response);
                if (response != null) {
                    GoodsListBean brandBean = new Gson().fromJson(response, GoodsListBean.class);
                    if (brandBean.getCode() == 1) {
                        productList = brandBean.getContent().getProduct();
                        if (page == 1) {
                            mList.clear();
                        }
                        mList.addAll(productList);
                        adapter.addData(mList);
                        if (mList.size() > 0) {
                            imgEmpty.setVisibility(View.GONE);
                        } else {
                            imgEmpty.setVisibility(View.VISIBLE);
                        }
                    } else {
                        ToastUtils.show(brandBean.getMsg());
                    }
                } else {
                    ToastUtils.show(getString(R.string.tv_check_service));
                }
            }
        });
    }

    /**
     * 商品列表的监听事件
     */
    @TargetApi(Build.VERSION_CODES.M)
    @OnClick({R.id.ll_sale_count, R.id.ll_goods_price, R.id.ll_goods_update, R.id.tv_goods_pinkage})
    public void sort(View view) {
        gvGoodslist.setSelection(0);
        switch (view.getId()) {
            case R.id.ll_sale_count:
                MyLog.e("sss", "isCountClick: " + isCountClick);
                ivPrice.setImageResource(R.mipmap.paixuall);
                ivUpdate.setImageResource(R.mipmap.paixuall);
                tvSaleCount.setTextColor(ContextCompat.getColor(this, R.color.color_goodslist_selected));
                tvGoodsPrice.setTextColor(ContextCompat.getColor(this, R.color.black));
                tvGoodsUpdate.setTextColor(ContextCompat.getColor(this, R.color.black));
                tvGoodsPinkage.setTextColor(ContextCompat.getColor(this, R.color.black));
                /**
                 * 销量升序
                 */
                if (isCountClick) {
                    orderType = 1;
                    ivCount.setImageResource(R.mipmap.jiantoudown);
                    if (BRAND.equals(type)) {
                        requestGoodsListByBrand(flag, id, 1, orderType);
                    } else if (CLASSIFITY.equals(type)) {
                        requestGoodsListByClassfity(flag, id, page, orderType);
                    } else {
                        requestDataBySearch(flag, 1, orderType, pageSize, content);
                    }
                }
                //销量降序
                else {
                    orderType = 0;
                    ivCount.setImageResource(R.mipmap.jiantouup);
                    if (BRAND.equals(type)) {
                        requestGoodsListByBrand(flag, id, 1, orderType);
                    } else if (CLASSIFITY.equals(type)) {
                        requestGoodsListByClassfity(flag, id, 1, orderType);
                    } else {
                        requestDataBySearch(flag, 1, orderType, pageSize, content);
                    }
                }
                isCountClick = !isCountClick;
                break;
            case R.id.ll_goods_price:
                ivCount.setImageResource(R.mipmap.paixuall);
                ivUpdate.setImageResource(R.mipmap.paixuall);
                tvGoodsPrice.setTextColor(ContextCompat.getColor(this, R.color.color_goodslist_selected));
                tvSaleCount.setTextColor(ContextCompat.getColor(this, R.color.black));
                tvGoodsUpdate.setTextColor(ContextCompat.getColor(this, R.color.black));
                tvGoodsPinkage.setTextColor(ContextCompat.getColor(this, R.color.black));
                //价格升序
                if (isPriceClick) {
                    orderType = 3;
                    ivPrice.setImageResource(R.mipmap.jiantouup);
                    if (BRAND.equals(type)) {
                        requestGoodsListByBrand(flag, id, 1, orderType);
                    } else if (CLASSIFITY.equals(type)) {
                        requestGoodsListByClassfity(flag, id, 1, orderType);
                    } else {
                        requestDataBySearch(flag, 1, orderType, pageSize, content);
                    }
                }
                //价格降序
                else {
                    orderType = 2;
                    ivPrice.setImageResource(R.mipmap.jiantoudown);
                    if (BRAND.equals(type)) {
                        requestGoodsListByBrand(flag, id, 1, orderType);
                    } else if (CLASSIFITY.equals(type)) {
                        requestGoodsListByClassfity(flag, id, 1, orderType);
                    } else {
                        requestDataBySearch(flag, 1, orderType, pageSize, content);
                    }
                }
                isPriceClick = !isPriceClick;
                break;
            case R.id.ll_goods_update:
                ivCount.setImageResource(R.mipmap.paixuall);
                ivPrice.setImageResource(R.mipmap.paixuall);
                tvGoodsUpdate.setTextColor(ContextCompat.getColor(this, R.color.color_goodslist_selected));
                tvSaleCount.setTextColor(ContextCompat.getColor(this, R.color.black));
                tvGoodsPrice.setTextColor(ContextCompat.getColor(this, R.color.black));
                tvGoodsPinkage.setTextColor(ContextCompat.getColor(this, R.color.black));
                //上新升序
                if (isUpdateClick) {
                    orderType = 4;
                    ivUpdate.setImageResource(R.mipmap.jiantouup);
                    if (BRAND.equals(type)) {
                        requestGoodsListByBrand(flag, id, 1, orderType);
                    } else if (CLASSIFITY.equals(type)) {
                        requestGoodsListByClassfity(flag, id, 1, orderType);
                    } else {
                        requestDataBySearch(flag, 1, orderType, pageSize, content);
                    }
                }
                //上新降序
                else {
                    orderType = 5;
                    ivUpdate.setImageResource(R.mipmap.jiantoudown);
                    if (BRAND.equals(type)) {
                        requestGoodsListByBrand(flag, id, 1, orderType);
                    } else if (CLASSIFITY.equals(type)) {
                        requestGoodsListByClassfity(flag, id, 1, orderType);
                    } else {
                        requestDataBySearch(flag, 1, orderType, pageSize, content);
                    }
                }
                isUpdateClick = !isUpdateClick;
                break;
            case R.id.tv_goods_pinkage:
                isPriceClick = false;
                isCountClick = false;
                isUpdateClick = false;
                ivCount.setImageResource(R.mipmap.paixuall);
                ivUpdate.setImageResource(R.mipmap.paixuall);
                ivPrice.setImageResource(R.mipmap.paixuall);
                tvSaleCount.setTextColor(ContextCompat.getColor(this, R.color.black));
                tvGoodsPrice.setTextColor(ContextCompat.getColor(this, R.color.black));
                tvGoodsUpdate.setTextColor(ContextCompat.getColor(this, R.color.black));
                if (isPinkClick) {
                    tvGoodsPinkage.setTextColor(ContextCompat.getColor(this, R.color.color_goodslist_selected));
                    orderType = 6;
                    if (BRAND.equals(type)) {
                        requestGoodsListByBrand(flag, id, 1, orderType);
                    } else if (CLASSIFITY.equals(type)) {
                        requestGoodsListByClassfity(flag, id, 1, orderType);
                    } else {
                        requestDataBySearch(flag, 1, orderType, pageSize, content);
                    }
                } else {
                    tvGoodsPinkage.setTextColor(ContextCompat.getColor(this, R.color.black));
                    orderType = 0;
                    if (BRAND.equals(type)) {
                        requestGoodsListByBrand(flag, id, 1, orderType);
                    } else if (CLASSIFITY.equals(type)) {
                        requestGoodsListByClassfity(flag, id, 1, orderType);
                    } else {
                        requestDataBySearch(0, 1, orderType, pageSize, content);
                    }
                }
                isPinkClick = !isPinkClick;
                break;
            default:
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (Utils.isClickable()) {
            Bundle bundle = new Bundle();
            bundle.putString("productId", String.valueOf(mList.get(position).getId()));
            bundle.putString("merchantId", "1");
            JumpIntent.jump(this, ProductDetailActivity.class, bundle);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        MyLog.e("sss", "-onKeyDown");
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (fragment != null) {
                if (fragment.isVisible()) {
                    fragment.dismiss();
                } else {
                    finish();
                }
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
    }
}
