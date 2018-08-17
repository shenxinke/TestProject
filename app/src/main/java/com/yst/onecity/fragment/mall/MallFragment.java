package com.yst.onecity.fragment.mall;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.amap.Location;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.activity.mall.GoodsListActivity;
import com.yst.onecity.activity.mall.MallSearchActivity;
import com.yst.onecity.activity.member.MyMessageActivity;
import com.yst.onecity.adapter.malladapter.GridviewAdapter;
import com.yst.onecity.adapter.malladapter.ViewpagerAdapter;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.mall.BrandBean;
import com.yst.onecity.bean.mall.ClassfityBean;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.utils.WindowUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 主页面的商城页面
 *
 * @author wuxiaofang
 * @version 4.0.0
 * @date 2018/3/20
 */

public class MallFragment extends BaseFragment {

    @BindView(R.id.position)
    TextView position;
    @BindView(R.id.searchBarLayout)
    LinearLayout searchBarLayout;
    @BindView(R.id.messages)
    ImageView messages;
    @BindView(R.id.messageTagTV)
    TextView messageTagTV;
    @BindView(R.id.messagesRL)
    RelativeLayout messagesRL;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.ll_vp_selectview)
    LinearLayout llVpSelectview;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_list_contaner)
    LinearLayout llListContaner;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    private String locationPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    private ArrayList<View> gridviewList = new ArrayList<>();
    private int oldPosition;
    private int page = 1;
    private ViewpagerAdapter viewpagerAdapter;
    private int currentItem;
    public static final String MALL = "mall";
    private List<BrandBean.ContentBean.BrandListBean> mBrandList = new ArrayList<>();
    private List<ClassfityBean.ContentBean.ProductTypeBean> productType;
    private List<ClassfityBean.ContentBean.ProductTypeBean> classList = new ArrayList<>();
    private int checkPosition;
    private boolean isLoadMore;

    @Override
    public int bindLayout() {
        return R.layout.fragment_mall;
    }

    @Override
    public void initData() {
        startLocation();
        viewpagerAdapter = new ViewpagerAdapter(getActivity());
        vp.setAdapter(viewpagerAdapter);
        requestClassfityNet();
        requestBrandNet(1);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MyLog.e("sss", "-position: " + position);
                currentItem = position;
                llVpSelectview.getChildAt(currentItem).setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.indicate));
                llVpSelectview.getChildAt(oldPosition).setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.gray));
                oldPosition = currentItem;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        refresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                isLoadMore = true;
                page++;
                requestBrandNet(page);
                refreshlayout.finishLoadmore(500);
            }

            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                page = 1;
                classList.clear();
                vp.setCurrentItem(0);
                requestBrandNet(page);
                requestClassfityNet();
                refreshlayout.finishRefresh(500);
            }
        });
    }

    /**
     * 请求品牌数据
     */
    private void requestBrandNet(final int page) {
        String brandPage = String.valueOf(page);
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "page", brandPage,
                "timestamp", timestamp);
        OkHttpUtils
                .post()
                .url(Constants.GET_MALL_BRAND)
                .addParams("page", brandPage)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .addParams("timestamp", timestamp)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.tv_net_error));
                MyLog.e("sss", "e: " + e.getMessage());
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
            public void onResponse(final String response, int id) {
                MyLog.e("sss", "-page: " + page + "-商城品牌列表: " + response);
                if (response != null) {
                    BrandBean brandBean = new Gson().fromJson(response, BrandBean.class);
                    if (brandBean.getCode() == 1) {
                        final List<BrandBean.ContentBean.BrandListBean> brandList = brandBean.getContent().getBrandList();
                        MyLog.e("sss", "-page: " + page);
                        llListContaner.removeAllViews();
                        if (page == 1) {
                            mBrandList.clear();
                        }
                        MyLog.e("sss", "-isLoadMore: " + isLoadMore);
                        if (isLoadMore) {
                            if (brandList.size() == 0) {
                                ToastUtils.showToastCenter("已加载完毕");
                            }
                            isLoadMore = false;
                        }
                        mBrandList.addAll(brandList);
                        MyLog.e("sss", "-mBrandList: " + mBrandList.size());
                        for (int i = 0; i < mBrandList.size(); i++) {
                            View view = View.inflate(getActivity(), R.layout.item_list_mall, null);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            params.topMargin = 15;
                            view.setLayoutParams(params);
                            final RoundedImageView imgBackground = (RoundedImageView) view.findViewById(R.id.img_brand_background);
                            RelativeLayout relaBg = (RelativeLayout) view.findViewById(R.id.rela_bg);
                            TextView tvBrandName = (TextView) view.findViewById(R.id.tv_brand_name);
                            ImageView ivLogo = (ImageView) view.findViewById(R.id.iv_brand_logo);
                            ViewGroup.LayoutParams layoutParams = relaBg.getLayoutParams();
                            layoutParams.width = (int) (WindowUtils.getScreenWidth(getActivity()) * 3 / 5 - 16 * WindowUtils.getDesity(getActivity()));
                            relaBg.setLayoutParams(layoutParams);
                            ViewGroup.LayoutParams logoParams = ivLogo.getLayoutParams();
                            logoParams.width = layoutParams.width * 3 / 5;
                            ivLogo.setLayoutParams(logoParams);
                            String addressBackground = mBrandList.get(i).getAddress();
                            String addressLogo = mBrandList.get(i).getLogo();
                            if (TextUtils.isEmpty(addressBackground)) {
                                Glide.with(getActivity()).load(R.mipmap.brand_default).asBitmap().into(imgBackground);
                            } else {
                                Glide.with(getActivity()).load(addressBackground).asBitmap().into(imgBackground);
                            }
                            if (TextUtils.isEmpty(addressLogo)) {
                                Glide.with(getActivity()).load(R.mipmap.img_default_p).into(ivLogo);
                            } else {
                                Glide.with(getActivity()).load(addressLogo).into(ivLogo);
                            }
                            tvBrandName.setText(mBrandList.get(i).getName());
                            final int finalI = i;
                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (ConstUtils.isClickable()) {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("type", "brand");
                                        bundle.putString("name", mBrandList.get(finalI).getName());
                                        bundle.putInt("id", Integer.valueOf(mBrandList.get(finalI).getId()).intValue());
                                        JumpIntent.jump(getActivity(), GoodsListActivity.class, bundle);
                                    }
                                }
                            });
                            llListContaner.addView(view);
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
     * 请求分类数据
     */
    private void requestClassfityNet() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "timestamp", timestamp);
        OkHttpUtils
                .post()
                .url(Constants.GET_MALL_CLASSFITY)
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
                MyLog.e("sss", "-商城分类列表: " + response);
                if (response != null) {
                    productType = null;
                    ClassfityBean classfityBean = new Gson().fromJson(response, ClassfityBean.class);
                    if (classfityBean.getCode() == 1 && classfityBean.getContent() != null && classfityBean.getContent().getProductType() != null) {
                        classList.addAll(classfityBean.getContent().getProductType());
                        setData(classList, currentItem);
                    } else {
                        ToastUtils.show(classfityBean.getMsg());
                    }
                } else {
                    ToastUtils.show(getString(R.string.tv_check_service));
                }
            }
        });
    }

    /**
     * 商城页面的监听事件
     */
    @OnClick({R.id.messages, R.id.position, R.id.messagesRL, R.id.searchBarLayout})
    public void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.position:
                startLocation();
                break;
            case R.id.messages:
            case R.id.messagesRL:
                if (!TianyiApplication.isLogin) {
                    JumpIntent.jump(getActivity(), LoginActivity.class);
                    return;
                }
                JumpIntent.jump(getActivity(), MyMessageActivity.class);
                break;
            case R.id.searchBarLayout:
                JumpIntent.jump(getActivity(), MallSearchActivity.class);
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void onEventMainThread(EventBean event) {
        if (MALL.equals(event.getMsg())) {
            if (TianyiApplication.isLogin) {
                requestClassfityNet();
                requestBrandNet(1);
            }
        }
    }

    /**
     * 定位
     */
    private void startLocation() {
        if (EasyPermissions.hasPermissions(getActivity(), locationPermission)) {
            //启动定位
            new Location(locationCallback).startLocation();
            position.setText("定位中…");
        } else {
            EasyPermissions.requestPermissions(this, "请打开定位权限", 100, locationPermission);
        }
    }

    Location.LocationCallback locationCallback = new Location.LocationCallback() {
        @Override
        public void locSuccess(AMapLocation amapLocation) {
            if (amapLocation.getAoiName() == null || "".equals(amapLocation.getAoiName())) {
                position.setText(amapLocation.getAddress());
            } else {
                position.setText(amapLocation.getAoiName());
            }
        }

        @Override
        public void locFailure(int code, String errorInfo) {
            position.setText("定位失败");
        }
    };

    /**
     * 设置顶部轮播分类数据
     *
     * @param productType 分类的集合
     * @param mCurrent    当前索引
     */
    public void setData(final List<ClassfityBean.ContentBean.ProductTypeBean> productType, int mCurrent) {
        gridviewList.removeAll(gridviewList);
        llVpSelectview.removeAllViews();
        int gridItemCount = (int) Math.ceil(productType.size() * 1.0 / 10);
        for (int i = 0; i < gridItemCount; i++) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            GridView gridView = (GridView) inflater.inflate(R.layout.fragment_grid_mall, null);
            gridView.setNumColumns(5);

            gridView.setAdapter(new GridviewAdapter(getActivity(), i, 10, productType));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (ConstUtils.isClickable()) {
                        if (currentItem == 0) {
                            checkPosition = position;
                        } else {
                            checkPosition = currentItem * 10 + position;
                        }
                        MyLog.e("sss", "onItem: " + productType.get(checkPosition).getClassifyName());
                        MyLog.e("sss", "position: " + checkPosition);
                        Bundle bundle = new Bundle();
                        bundle.putString("type", "classifity");
                        bundle.putString("name", productType.get(checkPosition).getClassifyName());
                        bundle.putInt("id", Integer.valueOf(productType.get(checkPosition).getId()).intValue());
                        JumpIntent.jump(getActivity(), GoodsListActivity.class, bundle);
                    }
                }
            });
            gridviewList.add(gridView);
            View view = new View(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 4);
            if (i != 0) {
                params.leftMargin = 10;
            }
            view.setLayoutParams(params);
            MyLog.e("sss", "-llVpSelectview: " + llVpSelectview.getChildCount());
            if (i == mCurrent) {
                view.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));
            } else {
                view.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.gray));
            }
            llVpSelectview.addView(view);
        }
        viewpagerAdapter.addVpData(gridviewList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        gridviewList.clear();
        llListContaner.removeAllViews();
    }
}
