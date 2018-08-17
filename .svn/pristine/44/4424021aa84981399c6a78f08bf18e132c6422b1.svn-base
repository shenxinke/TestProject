package com.yst.onecity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.umeng.socialize.UMShareAPI;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.PxVideoBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.eventbus.GoCommentEvent;
import com.yst.onecity.eventbus.GoProductDetailEvent;
import com.yst.onecity.fragment.CartFragment;
import com.yst.onecity.fragment.ProductCommentsFragment;
import com.yst.onecity.fragment.ProductFragment;
import com.yst.onecity.fragment.ProductShareZxFragment;
import com.yst.onecity.fragment.PxSlideVideoFragment;
import com.yst.onecity.fragment.popfragment.ShareCommonDialog;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.RxCode;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.view.viewpagerindicator.ViewPagerIndicator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnPageChange;
import gorden.rxbus2.RxBus;
import gorden.rxbus2.ThreadMode;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * 商品详情界面
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/12/18
 */
public class ProductDetailActivity extends BaseActivity {

    @BindView(R.id.indicator_product)
    ViewPagerIndicator indicatorProduct;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private ArrayList<String> items = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();
    public static String productId = "";
    private String hunterId = "";
    private String authorid = "";
    private String sortId = "";
    private String spid = "";
    private String merchantId = "";
    private String story = "";
    private ProductFragment productFragment;
    private PxSlideVideoFragment mPxSlideVideoFragment;
    private int mCurrentIndex;
    private ShareCommonDialog shareCommonDialog;

    @Override
    public int bindLayout() {
        return R.layout.activity_product_detail;
    }

    @OnPageChange(R.id.viewpager)
    public void onPageSelected(int position) {
        if (position != 0) {
            if (mPxSlideVideoFragment != null) {
                mPxSlideVideoFragment.setPause();
                mPxSlideVideoFragment.isResumePlay = false;
            }
        } else {
            if (mPxSlideVideoFragment != null) {
                mPxSlideVideoFragment.setResume();
                mPxSlideVideoFragment.isResumePlay = true;
            }
        }
    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
        RxBus.get().register(this);
        CartFragment.isShowBack = false;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            productId = bundle.getString("productId");
            MyLog.e("sss", "productId:" + productId);
            merchantId = bundle.getString("merchantId");
            if (bundle.containsKey(Const.CONS_STR_HUNTERID)) {
                hunterId = bundle.getString("hunterId");
            }
            if (bundle.containsKey(Const.CONS_STR_AUTHORID)) {
                authorid = bundle.getString(Const.CONS_STR_AUTHORID);
            }
            if (bundle.containsKey(Const.STR_STORY_INTENT)) {
                mCurrentIndex = bundle.getInt(Const.STR_STORY_INTENT);
            }
        }

        initIndicatorTitle();
        initFragments();

        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return items.get(position);
            }
        });
        indicatorProduct.bindViewPager(viewpager, true);
        viewpager.setCurrentItem(mCurrentIndex);
    }

    private void initFragments() {
        Bundle bundle = new Bundle();
        bundle.putString("productId", productId);
        bundle.putString(Const.CONS_STR_HUNTERID, hunterId);
        bundle.putString("merchantId", merchantId);
        bundle.putString(Const.CONS_STR_AUTHORID, authorid);

        mPxSlideVideoFragment = PxSlideVideoFragment.newInstance(productId, merchantId, hunterId, authorid);
        fragments.add(mPxSlideVideoFragment);

        productFragment = new ProductFragment();
        productFragment.setArguments(bundle);
        fragments.add(productFragment);

        ProductCommentsFragment productCommentsFragment = new ProductCommentsFragment();
        productCommentsFragment.setArguments(bundle);
        fragments.add(productCommentsFragment);

        ProductShareZxFragment productShareZxFragment = ProductShareZxFragment.newInstance(productId);
        fragments.add(productShareZxFragment);
    }

    /**
     * 初始化Indicator
     */
    private void initIndicatorTitle() {
        items.add("品秀");
        items.add("商品");
        items.add("评价");
        items.add("分享");
    }

    @gorden.rxbus2.Subscribe(code = RxCode.ADD_CART_DETAILS, threadMode = ThreadMode.MAIN)
    public void addCart() {
        if (productFragment != null) {
            MyLog.e("sss", "---ProductFragment.isDetailsPop: " + ProductFragment.isDetailsPop);
            if (ProductFragment.isDetailsPop) {
                productFragment.getPop();
            } else {
                productFragment.addShopCart();
            }
        }
    }

    @gorden.rxbus2.Subscribe(code = RxCode.CODE_ADD_SHOPPING_CART, threadMode = ThreadMode.MAIN)
    public void addCartNum() {
        productFragment.showCartCount();
        mPxSlideVideoFragment.showCartCount();
    }

    @gorden.rxbus2.Subscribe(code = RxCode.CODE_PX_COMMENT, threadMode = gorden.rxbus2.ThreadMode.MAIN)
    public void flushPxComment(PxVideoBean.ContentBean mContentBean) {
        if (mPxSlideVideoFragment != null) {
            mPxSlideVideoFragment.flushComment(mContentBean);
        }
    }

    @gorden.rxbus2.Subscribe(code = RxCode.CODE_PX_SHARESUCCESS, threadMode = gorden.rxbus2.ThreadMode.MAIN)
    public void updateShareNum() {
        if (mPxSlideVideoFragment != null) {
            mPxSlideVideoFragment.requestShareSuccessNet();
            mPxSlideVideoFragment.setDismissShareDialog();
        }
    }

    @gorden.rxbus2.Subscribe(code = RxCode.CODE_PX_DZ, threadMode = gorden.rxbus2.ThreadMode.MAIN)
    public void flushDz(PxVideoBean.ContentBean mContentBean) {
        if (mPxSlideVideoFragment != null) {
            mPxSlideVideoFragment.flushDz(mContentBean);
        }
    }


    @Subscribe
    public void onEventMainThread(GoCommentEvent event) {
        viewpager.setCurrentItem(2, true);
    }

    @Subscribe
    public void onEventMainThread(GoProductDetailEvent event) {
        viewpager.setCurrentItem(1, true);
    }

    @OnClick({R.id.back, R.id.iv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_share:
                String shareUrl = Constants.SHARE_CONSULT + "ty_shop.html" +  "?id=" + productId;
                if(shareCommonDialog == null){
                    shareCommonDialog = ShareCommonDialog.openShareDialog(ProductDetailActivity.this, "普济一城", "商品分享", shareUrl, "");
                    shareCommonDialog.setShareContentType(Const.SHARE_CONTENTTYPE_TEXTIMAGE);
                }
                shareCommonDialog.show(getFragmentManager(), "");
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(context).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
        EventBus.getDefault().unregister(this);
    }
}
