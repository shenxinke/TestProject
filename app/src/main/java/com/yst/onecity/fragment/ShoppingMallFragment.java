package com.yst.onecity.fragment;

import android.Manifest;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.taobao.av.util.StringUtil;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.ChooseAddressActivity;
import com.yst.onecity.activity.amap.Location;
import com.yst.onecity.activity.commissioner.CommissionerHomePageActivity;
import com.yst.onecity.activity.member.SearchShoppingActivity;
import com.yst.onecity.activity.servermember.MoreServerMemberActivity;
import com.yst.onecity.adapter.CommomFragmentPagerAdapter;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.bean.CommissionerProductBean;
import com.yst.onecity.bean.CommissionerRecommendedBean;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.mall.ClassfityBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.GlideCircleTransform;
import com.yst.onecity.view.ShoppingMallPopupWindow;
import com.yst.onecity.view.roundedimageview.RoundedImageView;
import com.yst.onecity.view.viewpagerindicator.ViewPagerIndicator;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author Shenxinke
 * @version 4.2.0
 * @data 2018/5/31
 * <p>
 * 商城页面
 */

public class ShoppingMallFragment extends BaseFragment {
    private String locationPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    private ArrayList<String> sortList = new ArrayList<>();
    private List<ClassfityBean.ContentBean.ProductTypeBean> classList = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private CommomFragmentPagerAdapter adapter;

    @BindView(R.id.position)
    TextView tvPosition;
    @BindView(R.id.text_position)
    TextView tvPositionTwo;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.indicator)
    ViewPagerIndicator indicator;
    @BindView(R.id.shopping_mall_arrows)
    ImageView mIvArrow;
    @BindView(R.id.ConstraintLayout)
    ConstraintLayout clConstraintLayout;
    @BindView(R.id.headConstraintLayout)
    LinearLayout headCommissionerCollect;
    @BindView(R.id.zhuanyuan_name)
    TextView tvCommissionerName;
    @BindView(R.id.zhuanyuan_address)
    TextView tvCommissionerAddress;
    @BindView(R.id.iv_head)
    RoundedImageView mIvHead;
    @BindView(R.id.zhuanyuan_pic)
    ImageView mIvPic;
    @BindView(R.id.text_backgroud)
    ImageView mIvBackgroud;
    @BindView(R.id.zhuanyuan_fenxiang)
    TextView tvCommissionerShare;
    @BindView(R.id.zhuanyuan_pinglun)
    TextView tvCommissionerComment;
    @BindView(R.id.zhuanyuan_zan)
    TextView tvCommissionerPraise;
    @BindView(R.id.zhuanyuan_text)
    TextView tvCommissionerText;
    @BindView(R.id.zhuanyuan_sale)
    TextView tvCommissionerSale;
    @BindView(R.id.zhuanyuan_collect)
    TextView tvCommissionerCollect;
    @BindView(R.id.zhuanyuan_comment)
    TextView tvComment;


    @Override
    public int bindLayout() {
        return R.layout.fragment_shopping_mall;
    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);

        getAddres();

        requestClassfityNet();
    }


    /**
     * 商城页面的监听事件
     */
    @OnClick({R.id.position, R.id.text_position, R.id.searchBarLayout, R.id.searchLayout, R.id.zhuanyan_gengduo, R.id.shopping_mall_arrows})
    public void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.text_position:
            case R.id.position:
                JumpIntent.jump(getActivity(), ChooseAddressActivity.class);
                break;
            case R.id.searchLayout:
            case R.id.searchBarLayout:
                JumpIntent.jump(getActivity(), SearchShoppingActivity.class);
                break;
            case R.id.zhuanyan_gengduo:
                JumpIntent.jump(getActivity(), MoreServerMemberActivity.class);
                break;
            case R.id.shopping_mall_arrows:
                if (sortList == null || sortList.size() < 1) {
                    return;
                }
                if (Utils.isClickable()) {
                    ShoppingMallPopupWindow pop = new ShoppingMallPopupWindow(getActivity());
                    pop.showPop(mIvArrow, mIvArrow, sortList, viewpager.getCurrentItem());
                    pop.setOnDismissListener(() -> mIvArrow.setImageResource(R.mipmap.xia));
                    pop.setOnRightItemCheckListener(new ShoppingMallPopupWindow.OnRightItemCheckListener() {
                        @Override
                        public void onItemCheck(int pos) {
                            viewpager.setCurrentItem(pos, true);
                            pop.showPop(mIvArrow, mIvArrow, sortList, viewpager.getCurrentItem());
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    /**
     * 定位
     */
    private void startLocation() {
        if (EasyPermissions.hasPermissions(getActivity(), locationPermission)) {
            //启动定位
            new Location(locationCallback).startLocation();
            tvPosition.setText("定位中…");
        } else {
            EasyPermissions.requestPermissions(this, "请打开定位权限", 100, locationPermission);
        }
    }

    Location.LocationCallback locationCallback = new Location.LocationCallback() {
        @Override
        public void locSuccess(AMapLocation amapLocation) {
            if (amapLocation.getAoiName() == null || "".equals(amapLocation.getAoiName())) {
                tvPosition.setText("定位失败");
            } else {
                tvPosition.setText(amapLocation.getAoiName());
            }
        }

        @Override
        public void locFailure(int code, String errorInfo) {
            tvPosition.setText("定位失败");
        }
    };

    /**
     * 添加自定义Tab栏
     */
    private void addCustomTab() {
        sortList.add("全部");
        fragments.add(ShoppingMallAllFragment.newInstance());
        sortList.add("本地直供");
        fragments.add(ThisLocalityFragment.newInstance());
    }

    /**
     * 商品分类数据请求
     */
    private void requestClassfityNet() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("timestamp", timestamp);
        if (TextUtils.isEmpty(sign)) {
            return;
        }
        OkHttpUtils.post()
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
                MyLog.e("sss", "-商城分类: " + response);
                fragments.clear();
                classList.clear();
                sortList.clear();
                if (response != null) {
                    ClassfityBean classfityBean = new Gson().fromJson(response, ClassfityBean.class);
                    if (classfityBean.getCode() == Const.INTEGER_1 && classfityBean.getContent() != null && classfityBean.getContent().getProductType() != null) {
                        addCustomTab();
                        classList.addAll(classfityBean.getContent().getProductType());
                        for (int i = 0; i < classList.size(); i++) {
                            sortList.add(classList.get(i).getClassifyName());
                            fragments.add(MallAllFragment.newInstance(String.valueOf(classList.get(i).getId())));
                        }
                        adapter = new CommomFragmentPagerAdapter(getChildFragmentManager(), fragments, sortList);
                        viewpager.setAdapter(adapter);
                        indicator.bindViewPager(viewpager, false);
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
     * 专员推荐
     */
    private void getCommissionerRecommended() {
        String timestamp = SignUtils.getTimeStamp();
        String[] strings;
        String sign;
        PostFormBuilder post = OkHttpUtils.post();

        if (!TianyiApplication.isLogin) {
            strings = new String[]{"timestamp", timestamp};
            sign = Utils.getSign(strings);
            if (TextUtils.isEmpty(sign)) {
                return;
            }
            post.addParams("sign", sign);
        } else {
            String phone = TianyiApplication.appCommonBean.getPhone();
            String uuid = TianyiApplication.appCommonBean.getUuid();
            strings = new String[]{"phone", phone, "uuid", uuid, "timestamp", timestamp};
            sign = Utils.getSign(strings);
            if (TextUtils.isEmpty(sign)) {
                return;
            }
            post.addParams("phone", phone);
            post.addParams("uuid", uuid);
            post.addParams("sign", sign);
        }
        post.url(Constants.COMMISSIONER_RECOMMENDED);
        post.addParams("timestamp", timestamp);
        post.addParams("client_type", "A");
        post.build().execute(new StringCallback() {
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
                MyLog.e("sss", "-专员推荐: " + response);
                if (!TextUtils.isEmpty(response)) {
                    CommissionerRecommendedBean commissionerRecommendedBean = new Gson().fromJson(response, CommissionerRecommendedBean.class);
                    int code = commissionerRecommendedBean.getCode();
                    if (code == Const.INTEGER_1 && commissionerRecommendedBean.getContent() != null) {
                        if (Const.CONS_STR_NO_SERVER_PEOPLE.equals(commissionerRecommendedBean.getMsg())) {
                            headCommissionerCollect.setVisibility(View.VISIBLE);
                            clConstraintLayout.setVisibility(View.GONE);
                        } else {
                            headCommissionerCollect.setVisibility(View.GONE);
                            clConstraintLayout.setVisibility(View.VISIBLE);
                            int flag = commissionerRecommendedBean.getContent().getFlag();
                            int hid = commissionerRecommendedBean.getContent().gethId();
                            tvCommissionerAddress.setText(Utils.signString(commissionerRecommendedBean.getContent().getServiceArea()));
                            tvCommissionerName.setText(Utils.signString(commissionerRecommendedBean.getContent().getNickName()));
                            Glide.with(TianyiApplication.getInstance())
                                    .load(ConstUtils.matchingImageUrl(ConstUtils.getStringNoEmpty(commissionerRecommendedBean.getContent().getHImg())))
                                    .asBitmap()
                                    .transform(new GlideCircleTransform(context))
                                    .error(R.mipmap.member_head)
                                    .into(mIvHead);
                            Bundle bundle = new Bundle();
                            bundle.putString("hid", String.valueOf(hid));
                            bundle.putString("from", Const.STR_FROM_NOLOGIN);
                            mIvHead.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    JumpIntent.jump(getActivity(), CommissionerHomePageActivity.class, bundle);
                                }
                            });
                            if (flag == Const.INTEGER_0) {
                                mIvBackgroud.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        CommissionerHomePageActivity.isFirst = false;
                                        bundle.putInt(Const.STR_SHARE_INTENT, Const.INTEGER_1);
                                        JumpIntent.jump(getActivity(), CommissionerHomePageActivity.class, bundle);
                                    }
                                });
                            } else {
                                mIvBackgroud.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        JumpIntent.jump(getActivity(), CommissionerHomePageActivity.class, bundle);
                                    }
                                });
                            }

                            /**
                             * flag 1商品 0资讯 2没有专员信息
                             */
                            if (flag == Const.INTEGER_0) {
                                if (commissionerRecommendedBean.getContent().getConsultation() == null) {
                                    return;
                                }
                                tvCommissionerCollect.setVisibility(View.INVISIBLE);
                                tvComment.setVisibility(View.INVISIBLE);
                                tvCommissionerSale.setVisibility(View.INVISIBLE);
                                mIvPic.setVisibility(View.VISIBLE);
                                tvCommissionerComment.setVisibility(View.VISIBLE);
                                tvCommissionerShare.setVisibility(View.VISIBLE);
                                tvCommissionerPraise.setVisibility(View.VISIBLE);
                                Glide.with(TianyiApplication.getInstance())
                                        .load(ConstUtils.matchingImageUrl(ConstUtils.getStringNoEmpty(commissionerRecommendedBean.getContent().getConsultation().getAddress())))
                                        .asBitmap()
                                        .error(R.mipmap.member_back)
                                        .into(mIvPic);
                                tvCommissionerComment.setText(String.valueOf(commissionerRecommendedBean.getContent().getConsultation().getCommentNum()));
                                tvCommissionerShare.setText(String.valueOf(commissionerRecommendedBean.getContent().getConsultation().getShareNum()));
                                tvCommissionerPraise.setText(String.valueOf(commissionerRecommendedBean.getContent().getConsultation().getFabulousNum()));
                                tvCommissionerText.setText(Utils.signString(commissionerRecommendedBean.getContent().getConsultation().getContent()));
                            } else if (flag == Const.INTEGER_1) {
                                CommissionerProductBean commissionerProductBean = new Gson().fromJson(response, CommissionerProductBean.class);
                                if (commissionerProductBean.getContent().getProduct() == null) {
                                    return;
                                }
                                tvCommissionerPraise.setVisibility(View.INVISIBLE);
                                tvCommissionerComment.setVisibility(View.INVISIBLE);
                                tvCommissionerShare.setVisibility(View.INVISIBLE);
                                mIvPic.setVisibility(View.VISIBLE);
                                tvComment.setVisibility(View.VISIBLE);
                                tvCommissionerSale.setVisibility(View.VISIBLE);
                                tvCommissionerCollect.setVisibility(View.VISIBLE);
                                Glide.with(TianyiApplication.getInstance())
                                        .load(ConstUtils.matchingImageUrl(ConstUtils.getStringNoEmpty(commissionerProductBean.getContent().getProduct().getImageurl())))
                                        .asBitmap()
                                        .error(R.mipmap.member_back)
                                        .into(mIvPic);
                                tvCommissionerText.setText(Utils.signString(commissionerProductBean.getContent().getProduct().getPName()));
                                tvCommissionerSale.setText(String.valueOf(commissionerProductBean.getContent().getProduct().getSaleNum()));
                                tvCommissionerCollect.setText(String.valueOf(commissionerProductBean.getContent().getProduct().getCollectNum()));
                                tvComment.setText(String.valueOf(commissionerProductBean.getContent().getProduct().getCommentNum()));
                            } else {
                                tvCommissionerText.setText("该专员还没有上传内容哦~请您耐心等待");
                                Glide.with(TianyiApplication.getInstance())
                                        .load(ConstUtils.matchingImageUrl(String.valueOf(R.mipmap.member_back)))
                                        .asBitmap()
                                        .error(R.mipmap.member_back)
                                        .into(mIvPic);
                                tvCommissionerShare.setVisibility(View.INVISIBLE);
                                tvCommissionerComment.setVisibility(View.INVISIBLE);
                                tvCommissionerPraise.setVisibility(View.INVISIBLE);
                                tvCommissionerSale.setVisibility(View.INVISIBLE);
                                tvComment.setVisibility(View.INVISIBLE);
                                tvCommissionerCollect.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                } else {
                    headCommissionerCollect.setVisibility(View.VISIBLE);
                    clConstraintLayout.setVisibility(View.GONE);
                }
            }
        });
    }


    @Subscribe
    public void onEventMainThread(EventBean event) {
        String choose = "choose";
        String location = "location";
        String resultAddress = "resultAddress";
            if (choose.equals(event.getMsg())) {
                tvPosition.setText(event.getAdress());
                tvPositionTwo.setText(event.getAdress());

                MyLog.e("aaa", "choose___" + event.getAdress());
            } else if (location.equals(event.getMsg())) {
                tvPosition.setText(event.getAdress());
                tvPositionTwo.setText(event.getAdress());

                MyLog.e("aaa", "location___" + event.getAdress());
            } else if (resultAddress.equals(event.getMsg()) && Const.positionFlag == event.getPosition()) {
                tvPosition.setText(event.getAID());
                tvPositionTwo.setText(event.getAID());

                MyLog.e("aaa", "resultAddress___" + event.getAdress());
            }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        getCommissionerRecommended();
    }

    /**
     * 获取位置信息
     */
    public void getAddres() {
        if(StringUtil.isEmpty(Const.HOME_ADDRES)){
            startLocation();
        }else {
            tvPosition.setText(Const.HOME_ADDRES);
            tvPositionTwo.setText(Const.HOME_ADDRES);
        }
    }
}
