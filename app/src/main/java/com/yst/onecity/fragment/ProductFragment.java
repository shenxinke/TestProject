package com.yst.onecity.fragment;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tencent.TIMConversationType;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.MainActivity;
import com.yst.onecity.activity.addorder.AddOrderActivity;
import com.yst.onecity.activity.chat.ChatActivity;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.activity.member.TrademarkActivity;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.bean.addorder.OrderBean;
import com.yst.onecity.bean.product.ProductDetailBean;
import com.yst.onecity.bean.product.ProductPicListBean;
import com.yst.onecity.bean.product.ProductSortStandardBean;
import com.yst.onecity.callbacks.AbstractProductDetailCallback;
import com.yst.onecity.callbacks.AbstractProductPicCallBack;
import com.yst.onecity.callbacks.AbstractProductSortStandardCallBack;
import com.yst.onecity.callbacks.AbstractSureOderCallBack;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.eventbus.UpdateEvent;
import com.yst.onecity.eventbus.UpdateSpEvent;
import com.yst.onecity.fragment.popfragment.AddCartPopFragment;
import com.yst.onecity.utils.CircleTransFrom;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.RxCode;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.utils.WindowUtils;
import com.yst.onecity.view.FlowLayout;
import com.yst.onecity.view.NetworkImageHolderView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import gorden.rxbus2.RxBus;
import gorden.rxbus2.ThreadMode;
import okhttp3.Call;

/**
 * 商品图片详情
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class ProductFragment extends BaseFragment {

    @BindView(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    @BindView(R.id.productName)
    TextView productName;
    @BindView(R.id.tv_select_sku)
    TextView tvSelectSku;
    @BindView(R.id.productPrice)
    TextView productPrice;
    @BindView(R.id.saleCount)
    TextView saleCount;
    @BindView(R.id.cbLike)
    ImageView cbLike;
    @BindView(R.id.parentView)
    RelativeLayout parentView;
    @BindView(R.id.iv_ppHead)
    ImageView ivPpHead;
    @BindView(R.id.tv_ppName)
    TextView tvPpName;
    @BindView(R.id.tv_ppDesc)
    TextView tvPpDesc;
    @BindView(R.id.tv_desc_empty)
    TextView tvDescEmpty;
    @BindView(R.id.tv_ppnum)
    TextView tvPpnum;
    @BindView(R.id.tv_scope)
    TextView tvScope;
    @BindView(R.id.tv_introduce)
    TextView tvIntroduce;
    @BindView(R.id.ll_detailRoot)
    LinearLayout llDetailRoot;
    @BindView(R.id.delivery_area)
    ConstraintLayout llDeliveryArea;
    @BindView(R.id.tv_merchant)
    TextView tvMerchant;
    @BindView(R.id.tv_point)
    TextView tvPoint;
    @BindView(R.id.fl_area)
    FlowLayout flArea;
    @BindView(R.id.ll_addition)
    LinearLayout llAddition;
    Unbinder unbinder;

    public static boolean isDetailsPop;
    private int mPhoneWidth;
    private int mPhoneHeight;
    private String productId = "";
    /**
     * //商品类型ID
     */
    private String stid = "";
    /**
     * 商品规格ID
     */
    private String num = "1";
    /**
     * 服务专员id
     */
    private String hunterid = "0";
    private String authorid = "0";
    private String hid = "0";
    private String merchantId = "";
    private String mImId = "";
    private boolean isCollect = false;
    /**
     * banner数据源
     */
    private List<ProductDetailBean.ContentBean.ImageUrlBean> productImageList = new ArrayList<>();
    private List<ProductSortStandardBean.ContentBean.SpecBean> specBeen = new ArrayList<>();
    private ProductDetailBean.ContentBean.Brand brand;
    private String eSpid = "";
    private String eStid = "";
    private String sortOneName = "";
    private String sortTwoName = "";
    private String selectOne = "";
    private String selectTwo = "";
    private AddCartPopFragment fragment;
    private List<ProductPicListBean.ContentBean> mProductDetailList = new ArrayList<>();

    @Override
    public int bindLayout() {
        return R.layout.fragment_product;
    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
        RxBus.get().register(this);
        showCartCount();
        if (getArguments() != null) {
            productId = getArguments().getString("productId", "-1");
            merchantId = getArguments().getString("merchantId", "-1");
            if (getArguments().containsKey(Const.CONS_STR_HUNTERID)) {
                hunterid = getArguments().getString(Const.CONS_STR_HUNTERID, "0");
            }
            if (getArguments().containsKey(Const.CONS_STR_AUTHORID)) {
                authorid = getArguments().getString(Const.CONS_STR_AUTHORID, "0");
            }
            if (getArguments().containsKey(Const.CONS_STR_HID)) {
                hid = getArguments().getString(Const.CONS_STR_HID, "0");
            }
        }


        Display d = getActivity().getWindowManager().getDefaultDisplay();
        Point outSize = new Point();
        d.getSize(outSize);
        mPhoneWidth = outSize.x;
        mPhoneHeight = mPhoneWidth * 3 / 4;
        convenientBanner.setLayoutParams(new LinearLayout.LayoutParams(mPhoneWidth, mPhoneHeight));
        if (TextUtils.isEmpty(merchantId)) {
            return;
        }

        getProductTextImageList();
        getProductData();
        getProductSortStandard(productId, merchantId);
    }

    /**
     * 动态显示商品介绍列表数据
     */
    private void showProductDetailList() {
        if (llDetailRoot == null) {
            return;
        }
        llDetailRoot.removeAllViews();
        for (int i = 0; i < mProductDetailList.size(); i++) {
            llDetailRoot.addView(getDetailItemView(mProductDetailList.get(i)));
        }
    }

    /**
     * 根据条目类型动态创建View
     *
     * @param content
     * @return
     */
    private View getDetailItemView(ProductPicListBean.ContentBean content) {
        View view = null;
        switch (content.getType()) {
            case Const.INTEGER_0:
                view = LayoutInflater.from(TianyiApplication.getInstance()).inflate(R.layout.item_publish_content, null);
                TextView tvContent = (TextView) view.findViewById(R.id.content);
                tvContent.setText(Utils.getStringNoEmpty(content.getContent()));
                break;
            case Const.INTEGER_1:
                view = LayoutInflater.from(TianyiApplication.getInstance()).inflate(R.layout.item_product_pic, null);
                ImageView ivProduct = (ImageView) view.findViewById(R.id.product_pic);


                ViewGroup.LayoutParams layoutParams = ivProduct.getLayoutParams();
                layoutParams.height = WindowUtils.getScreenWidth((Activity) context);

                ivProduct.setLayoutParams(layoutParams);

                Glide.with(TianyiApplication.getContext()).
                        load(ConstUtils.matchingImageUrl(content.getContent()))
                        .thumbnail(0.1f)
                        .error(R.mipmap.img_default_bg).diskCacheStrategy(DiskCacheStrategy.SOURCE).
                        crossFade().into(ivProduct);
                break;
            default:
                break;
        }

        return view;
    }

    /**
     * 获取商品 分类 和  规格
     */
    private void getProductSortStandard(String productId, String merchantId) {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("timestamp", timestamp, "pId", productId, "merchantid", merchantId);

        OkHttpUtils.post().url(Constants.GET_PRODUCT_SORT_STANDARD)
                .addParams("timestamp", timestamp)
                .addParams("pId", productId)
                .addParams("merchantid", merchantId)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractProductSortStandardCallBack() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(ProductSortStandardBean response, int id) {
                MyLog.e("sss", "-获取规格：" + response);
                if (response.getCode() == 1) {
                    if (response.getContent().getStandard().size() == 1) {
                        tvSelectSku.setText("已选 " + response.getContent().getStandard().get(0).getStandard());
                        eSpid = response.getContent().getStandard().get(0).getStandardId() + "";
                    } else {
                        if (response.getContent().getSpec().size() == 1) {
                            tvSelectSku.setText("请选择 " + response.getContent().getSpec().get(0).getName());
                        } else if (response.getContent().getSpec().size() > 1) {
                            tvSelectSku.setText("请选择 " + response.getContent().getSpec().get(0).getName() + "," + response.getContent().getSpec().get(1).getName());
                        }
                    }
                    specBeen = response.getContent().getSpec();
                    if (response.getContent().getClassify().size() != 0) {
                        eStid = String.valueOf(response.getContent().getClassify().get(0).getClassId());
                    }

                }
            }
        });
    }

    @gorden.rxbus2.Subscribe(code = RxCode.CODE_ADD_CART_SUCCESSFUL, threadMode = ThreadMode.MAIN)
    public void receive(UpdateEvent updateEvent) {
        String msg = updateEvent.getMsg();
        MyLog.e("sss", "msg: " + msg);
        tvSelectSku.setText(msg);
    }

    /**
     * 获取商品详情
     */
    private void getProductData() {
        productImageList.clear();
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("timestamp", timestamp,
                "productId", productId,
                "equipmentId", Utils.getDeviceId(TianyiApplication.getInstance()),
                "mId", TianyiApplication.appCommonBean.getId(),
                "provinceName", TextUtils.isEmpty(TianyiApplication.appCommonBean.getProvinceName()) ? "null" : TianyiApplication.appCommonBean.getProvinceName(),
                "cityName", TextUtils.isEmpty(TianyiApplication.appCommonBean.getCityName()) ? "null" : TianyiApplication.appCommonBean.getCityName(),
                "countyName", TextUtils.isEmpty(TianyiApplication.appCommonBean.getCountyName()) ? "null" : TianyiApplication.appCommonBean.getCountyName()
        );

        OkHttpUtils.post().url(Constants.PRODUCT_DETAIL)
                .addParams("productId", productId)
                .addParams("equipmentId", Utils.getDeviceId(TianyiApplication.getInstance()))
                .addParams("mId", TianyiApplication.appCommonBean.getId())
                .addParams("equipmentId", Utils.getDeviceId(TianyiApplication.getInstance()))
                .addParams("provinceName", TextUtils.isEmpty(TianyiApplication.appCommonBean.getProvinceName()) ? "null" : TianyiApplication.appCommonBean.getProvinceName())
                .addParams("cityName", TextUtils.isEmpty(TianyiApplication.appCommonBean.getCityName()) ? "null" : TianyiApplication.appCommonBean.getCityName())
                .addParams("countyName", TextUtils.isEmpty(TianyiApplication.appCommonBean.getCountyName()) ? "null" : TianyiApplication.appCommonBean.getCountyName())
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractProductDetailCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(TianyiApplication.getInstance().getResources().getString(R.string.str_net_error_message));
            }

            @Override
            public void onResponse(ProductDetailBean response, int id) {
                if (response.getCode() == 1 && response.getContent() != null) {
                    ProductDetailBean.ContentBean content = response.getContent();
                    MyLog.e("ss", "-count:" + content.getText().get(0).getSale_num());
                    productName.setText(content.getText().get(0).getName());
                    if (content.getText().get(0).getMaxPrice() == content.getText().get(0).getMinPrice()) {
                        productPrice.setText(String.format("¥%1$s", String.format("%.2f", content.getText().get(0).getMinPrice())));
                    } else {
                        productPrice.setText(String.format("¥%1$s-%2$s", String.format("%.2f", content.getText().get(0).getMinPrice()), String.format("%.2f", response.getContent().getText().get(0).getMaxPrice())));
                    }
                    if (content.getText().get(0).getSale_num() >= Const.INTEGER_10000) {
                        saleCount.setText(String.format("销量%1$f万", String.format("%.2f", content.getText().get(0).getSale_num() / 10000)));
                    } else {
                        saleCount.setText(String.format("已销%1$d笔", content.getText().get(0).getSale_num()));
                    }
                    if (content.getImageUrl() == null || content.getImageUrl().size() == 0) {
                        ProductDetailBean.ContentBean.ImageUrlBean bean = new ProductDetailBean.ContentBean.ImageUrlBean();
                        bean.setAddress(content.getText().get(0).getImageurl());
                        productImageList.add(bean);
                    } else {
                        productImageList.addAll(content.getImageUrl());
                    }
                    if (productImageList.size() < 1) {
                        convenientBanner.setBackgroundResource(R.mipmap.img_default_p);
                    }

                    brand = content.getBrand();
                    setBrandData();

                    convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                        @Override
                        public NetworkImageHolderView createHolder() {
                            return new NetworkImageHolderView();
                        }
                    }, productImageList).setPageIndicator(new int[]{R.mipmap.dot, R.mipmap.dot_selected}).setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL).startTurning(5000);
                    if (TianyiApplication.isLogin && content.getType() == 0) {
                        cbLike.setSelected(true);
                        isCollect = true;
                    } else {
                        cbLike.setSelected(false);
                        isCollect = false;
                    }

                    /**
                     * 是否超出配送范围
                     */
                    if (content.getIsScope() == Const.INTEGER_0) {
                        Const.isOutRange = Const.INTEGER_0;
                        tvScope.setVisibility(View.GONE);
                        llAddition.setVisibility(View.VISIBLE);
                    } else {
                        Const.isOutRange = content.getIsScope();
                        tvScope.setVisibility(View.VISIBLE);
                        llAddition.setVisibility(View.GONE);
                    }

                    if(Const.STR1.equals(content.getTypeScoure())){
                        llDeliveryArea.setVisibility(View.VISIBLE);
                        tvMerchant.setText(ConstUtils.getStringNoEmpty(content.getMerchantAddress()));
                        for (int i = 0; i < content.getProductScope().size(); i++) {
                            View view = LayoutInflater.from(TianyiApplication.getInstance()).inflate(R.layout.item_lable, null);
                            final TextView columnTextView = (TextView) view.findViewById(R.id.txt_lable);
                            columnTextView.setTextSize(13);
                            columnTextView.setTextColor(ContextCompat.getColor(TianyiApplication.getInstance(),R.color.color_A47A54));
                            columnTextView.setBackgroundResource(R.drawable.product_ps_area);
                            columnTextView.setText(ConstUtils.getStringNoEmpty(content.getProductScope().get(i)));
                            flArea.addView(view);
                        }

                    }else{
                        llDeliveryArea.setVisibility(View.GONE);
                    }

                } else {
                    ToastUtils.show(response.getMsg());
                }
            }
        });
    }

    /**
     * 获取商品图文详情接口
     */
    private void getProductTextImageList() {

        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("timestamp", timestamp, "productId", productId);
        if (TextUtils.isEmpty(sign)) {
            return;
        }

        OkHttpUtils.post().url(Constants.PRODUCT_DETAIL_PIC)
                .addParams("productId", productId)
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractProductPicCallBack() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show("网络请求失败，请重试");
            }

            @Override
            public void onResponse(ProductPicListBean response, int id) {
                if (response.getCode() == 1) {
                    if (response.getContent() == null) {
                        return;
                    }
                    mProductDetailList.addAll(response.getContent());
                    showProductDetailList();
                }
            }
        });
    }

    /**
     * 设置品牌详情数据
     */
    private void setBrandData() {
        if (brand != null && !TextUtils.isEmpty(brand.getBrandid())) {
            tvDescEmpty.setVisibility(View.GONE);
            tvPpnum.setVisibility(View.VISIBLE);
            tvPpDesc.setVisibility(View.VISIBLE);

            tvPpName.setText(ConstUtils.getStringNoEmpty(brand.getBarndName()));
            tvPpnum.setText(ConstUtils.changeEmptyStringToZero(brand.getProductCount()) + "件商品");
            tvPpDesc.setText(ConstUtils.getStringNoEmpty(brand.getBranddescription()));
            Glide.with(TianyiApplication.getInstance()).load(ConstUtils.matchingImageUrl(brand.getAddress()))
                    .asBitmap().transform(new CircleTransFrom(TianyiApplication.getInstance())).error(R.mipmap.img_default_p).into(ivPpHead);
        } else {
            tvDescEmpty.setVisibility(View.VISIBLE);
            tvPpnum.setVisibility(View.GONE);
            tvPpDesc.setVisibility(View.GONE);
            tvPpName.setVisibility(View.GONE);

            Glide.with(TianyiApplication.getInstance()).load(R.drawable.product_brand_default)
                    .asBitmap().transform(new CircleTransFrom(TianyiApplication.getInstance())).error(R.mipmap.img_default_p).into(ivPpHead);
        }
    }

    @OnClick({R.id.btnContactTa, R.id.rl_like, R.id.addCart, R.id.nowBuy,R.id.tv_select_sku, R.id.rl_cart, R.id.rl_pp})
    public void onViewClicked(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            case R.id.btnContactTa:
                if (!TianyiApplication.isLogin) {
                    JumpIntent.jump(getActivity(), LoginActivity.class);
                    return;
                }
                ChatActivity.navToChat(context, mImId, String.valueOf(hunterid), TIMConversationType.C2C);
                break;
            case R.id.tv_select_sku:
                if (!TianyiApplication.isLogin) {
                    JumpIntent.jump(getActivity(), LoginActivity.class);
                    return;
                }
                showDialog();
                break;
            case R.id.rl_like:
                if (!Utils.isClickable()) {
                    return;
                }
                if (!TianyiApplication.isLogin) {
                    JumpIntent.jump(getActivity(), LoginActivity.class);
                    return;
                }
                if (!isCollect) {
                    collectProduct(0);
                } else {
                    collectProduct(1);
                }
                break;
            case R.id.addCart:
                if (!TextUtils.isEmpty(eSpid) && !TextUtils.isEmpty(eStid)) {
                    if (TianyiApplication.isLogin) {
                        addShopCart();
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("addcartDetails", "addcartDetails");
                        JumpIntent.jump(getActivity(), LoginActivity.class, bundle);
                    }
                } else {
                    showDialog();
                }
                break;
            case R.id.nowBuy:
                if (!TianyiApplication.isLogin) {
                    JumpIntent.jump(getActivity(), LoginActivity.class);
                    return;
                }
                if (!TextUtils.isEmpty(eSpid)) {
                    nowBuy();
                } else {
                    showDialog();
                }
                break;
            case R.id.rl_pp:
                if(brand == null || TextUtils.isEmpty(brand.getBrandid())){
                    return;
                }
                TrademarkActivity.openActivity(getActivity(), ConstUtils.getStringNoEmpty(brand.getBrandid()));
                break;
            case R.id.rl_cart:

                if (!TianyiApplication.isLogin) {
                    JumpIntent.jump(getActivity(), LoginActivity.class);
                    return;
                }
                MainActivity.isCart = true;
                CartFragment.productId = productId;
                CartFragment.isShowBack = true;
                JumpIntent.jump(getActivity(), MainActivity.class);
                break;
            default:
                break;
        }
    }

    public void showDialog() {
        isDetailsPop = true;
        fragment = AddCartPopFragment.newInstance(productId, merchantId, hunterid, authorid, hid, selectOne, selectTwo, sortOneName, sortTwoName);
        fragment.show(getFragmentManager(), "details");
    }

    /**
     * 立即购买
     */
    private void nowBuy() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("timestamp", timestamp,
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "pid", productId,
                "sid", eSpid,
                "num", num,
                "authorid", authorid,
                "hid", hunterid,
                "longitude", "null",
                "latitude", "null",
                "provinceName", TextUtils.isEmpty(TianyiApplication.appCommonBean.getProvinceName()) ? "null" : TianyiApplication.appCommonBean.getProvinceName(),
                "cityName", TextUtils.isEmpty(TianyiApplication.appCommonBean.getCityName()) ? "null" : TianyiApplication.appCommonBean.getCityName(),
                "countyName", TextUtils.isEmpty(TianyiApplication.appCommonBean.getCountyName()) ? "null" : TianyiApplication.appCommonBean.getCountyName()
        );

        OkHttpUtils.post().url(Constants.NOW_BUY)
                .addParams("timestamp", timestamp)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("pid", productId)
                .addParams("authorid", authorid)
                .addParams("num", num)
                .addParams("hid", hunterid)
                .addParams("sid", eSpid)
                .addParams("longitude", "null")
                .addParams("latitude", "null")
                .addParams("provinceName", TextUtils.isEmpty(TianyiApplication.appCommonBean.getProvinceName()) ? "null" : TianyiApplication.appCommonBean.getProvinceName())
                .addParams("cityName", TextUtils.isEmpty(TianyiApplication.appCommonBean.getCityName()) ? "null" : TianyiApplication.appCommonBean.getCityName())
                .addParams("countyName", TextUtils.isEmpty(TianyiApplication.appCommonBean.getCountyName()) ? "null" : TianyiApplication.appCommonBean.getCountyName())
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractSureOderCallBack() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(TianyiApplication.getInstance().getResources().getString(R.string.str_net_error_message));
            }

            @Override
            public void onResponse(OrderBean response, int id) {

                if (response.getCode() == 1) {
                    if (response.getContent() != null && response.getContent().getAddress() != null) {
                        OrderBean.ContentBean.AddressBeanX.AddressBean address = response.getContent().getAddress().getAddress();

                        Bundle bundle = new Bundle();
                        bundle.putBoolean("nowBuy", true);
                        bundle.putString("productId", productId);
                        bundle.putString("hunterId", hunterid);
                        bundle.putString("authorid", authorid);
                        bundle.putString("sid", eSpid);
                        bundle.putString("latitude", address == null ? "null" : address.getLatitude());
                        bundle.putString("longitude", address == null ? "null" : address.getLongitude());
                        bundle.putString("num", num);
                        JumpIntent.jump(getActivity(), AddOrderActivity.class, bundle);
                    }
                } else {
                    ToastUtils.show(response.getMsg());
                }

            }
        });
    }

    /**
     * 商品收藏
     *
     * @param type
     */
    private void collectProduct(final int type) {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "timestamp", timestamp,
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "hunterId", hunterid,
                "type", String.valueOf(type),
                "id", TianyiApplication.appCommonBean.getId(),
                "productId", productId);

        if (TextUtils.isEmpty(sign)) {
            return;
        }

        OkHttpUtils.post().url(Constants.COLLECTION_PRODUCT)
                .addParams("timestamp", timestamp)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("type", String.valueOf(type))
                .addParams("hunterId", hunterid)
                .addParams("id", TianyiApplication.appCommonBean.getId())
                .addParams("productId", productId)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(TianyiApplication.getInstance().getResources().getString(R.string.str_net_error_message));
            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("@@", response);
                try {
                    JSONObject obj = new JSONObject(response);
                    int code = obj.getInt("code");
                    if (code == 1 && type == 0) {
                        isCollect = true;
                        cbLike.setSelected(true);
                        ToastUtils.show(obj.getString("msg"));
                    } else if (code == 1 && type == 1) {
                        isCollect = false;
                        cbLike.setSelected(false);
                        ToastUtils.show(obj.getString("msg"));
                    } else {
                        ToastUtils.show(obj.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getPop() {
        if (fragment != null) {
            fragment.addShopCart();
        }
    }

    /**
     * 加入购物车
     */
    public void addShopCart() {

        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("timestamp", timestamp, "phone", TianyiApplication.appCommonBean.getPhone()
                , "uuid", TianyiApplication.appCommonBean.getUuid(), "spid", eSpid, "stid", eStid, "num", num, "hunterid", hunterid, "authorid", authorid);

        OkHttpUtils.post().url(Constants.ADD_SHOP_CART)
                .addParams("timestamp", timestamp)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("spid", eSpid)
                .addParams("stid", eStid)
                .addParams("num", num)
                .addParams("hunterid", hunterid)
                .addParams("authorid", authorid)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(TianyiApplication.getInstance().getResources().getString(R.string.str_net_error_message));
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject obj = new JSONObject(response);
                    int code = obj.getInt("code");
                    if (code == 1) {
                        ToastUtils.show("成功加入购物车");
                        RxBus.get().send(RxCode.CODE_ADD_SHOPPING_CART);
                    } else {
                        ToastUtils.show(obj.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Subscribe
    public void onEventMainThread(UpdateSpEvent event) {
        MyLog.e("asdsada", event.isMoreSp() + "___name__" + event.getSpName());
        MyLog.e("asdsada", event.isMoreSp() + "___guige__" + event.getStandardOneName());
        String choosedContent = event.getChoosedContent();
        if (choosedContent.contains(Const.CONS_STR_CHOOSED)) {
            tvSelectSku.setText(choosedContent);
        } else {
            if (!event.isMoreSp()) {
                if (event.getSpName() == null || Const.CONS_STR_NULL.equals(event.getSpName()) || Const.CONS_STR_NULL_NULL.equals(event.getSpName()) || "".equals(event.getSpName()) || Const.CONS_STR_DOUHAO.equals(event.getSpName())) {
                    tvSelectSku.setText("请选择:" + event.getStandardOneName());
                } else {
                    tvSelectSku.setText("已选择:" + event.getSpName());
                }
            } else {
                if (event.getSortOneName() == null && event.getSortTwoName() != null) {
                    tvSelectSku.setText("请选择:" + event.getStandardOneName());
                } else if (event.getSortTwoName() == null && event.getSortOneName() != null) {
                    tvSelectSku.setText("请选择:" + event.getStandardTwoName());
                } else if (event.getSortOneName() == null && event.getSortTwoName() == null) {
                    tvSelectSku.setText("请选择:" + event.getStandardOneName() + "," + event.getStandardTwoName());
                } else if (event.getSpName() != null && !Const.CONS_STR_NULL.equals(event.getSpName()) && !Const.CONS_STR_NULL_NULL.equals(event.getSpName())) {
                    tvSelectSku.setText("已选择:" + event.getSpName());
                }
            }
        }

        if (!TextUtils.isEmpty(event.getStid())) {
            eStid = event.getStid();
        }
        if (!TextUtils.isEmpty(event.getSpid())) {
            eSpid = event.getSpid();

        }
        num = event.getNum();
        sortOneName = event.getSortOneName();
        sortTwoName = event.getSortTwoName();
        selectOne = event.getSelectOne();
        selectTwo = event.getSelectTwo();
        productPrice.setText(event.getPrice());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyLog.e("sss", "productFragmnt:finish()");
        EventBus.getDefault().unregister(getActivity());
        RxBus.get().unRegister(this);
    }


    /**
     * 更新显示购物车数量
     */
    public void showCartCount() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("timestamp", timestamp,
                "phone", TianyiApplication.appCommonBean.getPhone()
                , "uuid", TianyiApplication.appCommonBean.getUuid());
        if (TextUtils.isEmpty(sign)) {
            return;
        }
        OkHttpUtils.post().url(Constants.PRODUCT_DETAIL_CART_NUM)
                .addParams("timestamp", timestamp)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(TianyiApplication.getInstance().getResources().getString(R.string.str_net_error_message));
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject mJSONObject = new JSONObject(response);
                    if (mJSONObject != null) {
                        if (mJSONObject.getInt(Const.CONS_STR_CODE) == Const.INTEGER_1) {
                            if (mJSONObject.has(Const.CONS_STR_CONTENT)) {
                                JSONObject jsonData = mJSONObject.getJSONObject(Const.CONS_STR_CONTENT);
                                if (jsonData.has(Const.CON_STR_COUNT)) {
                                    int count = jsonData.getInt(Const.CON_STR_COUNT);
                                    tvPoint.setText(String.valueOf(count));
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
