package com.yst.onecity.fragment;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuyu.gsyvideoplayer.utils.FileUtils;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.MainActivity;
import com.yst.onecity.activity.addorder.AddOrderActivity;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.activity.video.LandLayoutVideoPx;
import com.yst.onecity.activity.video.VideoRecoderActivity;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.bean.PxVideoBean;
import com.yst.onecity.bean.addorder.OrderBean;
import com.yst.onecity.bean.product.ProductSortStandardBean;
import com.yst.onecity.callbacks.AbstractProductSortStandardCallBack;
import com.yst.onecity.callbacks.AbstractPxVideoCallback;
import com.yst.onecity.callbacks.AbstractSureOderCallBack;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.eventbus.UpdateSpEvent;
import com.yst.onecity.fragment.popfragment.AddCartPopFragment;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.RxCode;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;
import gorden.rxbus2.RxBus;
import okhttp3.Call;
import pub.devrel.easypermissions.EasyPermissions;

import static com.yst.onecity.config.Const.CONS_STR_HUNTER;

/**
 * 品秀滑动视频播放页面
 *
 * @author WangJingWei
 * @version 4.2.0
 * @date 2018/5/31.
 */
public class PxSlideVideoFragment extends BaseFragment {
    @BindView(R.id.viewpager)
    VerticalViewPager mViewPager;
    @BindView(R.id.include_empty_videoView)
    ConstraintLayout includeEmptyVideoView;
    @BindView(R.id.tv_point)
    TextView tvPoint;
    @BindView(R.id.iv_jrgwc)
    ImageView ivJrgwc;
    @BindView(R.id.iv_ljgm)
    ImageView ivLjgm;
    @BindView(R.id.tv_empty_addVideo)
    TextView tvEmptyAddVideo;
    @BindView(R.id.iv_empty_sy)
    ImageView ivEmptySy;
    Unbinder unbinder;

    private LandLayoutVideoPx videoPlayer;
    private OrientationUtils orientationUtils;

    private RelativeLayout mRoomContainer;
    private FrameLayout mFragmentContainer;

    private FragmentManager mFragmentManager;
    private PagerAdapter mPagerAdapter;

    private int mCurrentItem;
    private int mRoomId = -1;
    private boolean mInit = false;
    private List<PxVideoBean.ContentBean> mVideoData = new ArrayList();
    private int page = 1;
    private int rows = 10;
    private String TAG = "sss";
    public boolean isResumePlay = true;

    /**
     * 混乱代码购物车参数
     */
    private String eSpid = "";
    private String eStid = "";
    private String sortOneName = "";
    private String sortTwoName = "";
    private String selectOne = "";
    private String selectTwo = "";
    private String productId = "";
    private String merchantId = "";
    private String hunterid = "0";
    private String authorid = "0";
    private String hid = "0";

    /**
     * 商品规格ID
     */
    private String num = "1";
    private AddCartPopFragment fragment;

    private PxRoomViewFragment mRoomFragment = PxRoomViewFragment.newInstance();
    private String[] videoPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};

    /**
     * @param productId
     * @param merchantId
     * @param hunterid
     * @param authorid
     * @return
     */
    public static PxSlideVideoFragment newInstance(String productId, String merchantId, String hunterid, String authorid) {
        Bundle bundle = new Bundle();
        PxSlideVideoFragment mPxSlideVideoFragment = new PxSlideVideoFragment();
        bundle.putString("productId", productId);
        bundle.putString("merchantId", merchantId);
        bundle.putString("hunterid", hunterid);
        bundle.putString("authorid", authorid);
        mPxSlideVideoFragment.setArguments(bundle);
        return mPxSlideVideoFragment;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_px_video;
    }

    @Override
    public void initData() {
        initAuguments();
        initView();
        showCartCount();
        requestPxVideoInfo();
        getProductSortStandard(productId, merchantId);
    }

    @OnClick(R.id.iv_empty_sy)
    public void toClick() {
        ivEmptySy.setVisibility(View.GONE);
    }

    @OnClick(R.id.tv_empty_addVideo)
    public void addVideo() {
        if (!TianyiApplication.isLogin) {
            JumpIntent.jump((Activity) context, LoginActivity.class);
            return;
        }

        if (TextUtils.isEmpty(TianyiApplication.appCommonBean.getIsCertification())) {
            ToastUtils.show("未认证用户不可添加");
            return;
        }

        if (EasyPermissions.hasPermissions(getActivity(), videoPermission)) {
            Bundle bundle = new Bundle();
            bundle.putInt(Const.VIDEORECODER_FROM, Const.INTEGER_0);
            JumpIntent.jump(getActivity(), VideoRecoderActivity.class, bundle);

        } else {
            EasyPermissions.requestPermissions(this, "请打开相机权限", 100, videoPermission);
        }
    }

    @OnClick(R.id.iv_ljgm)
    public void cNowBuy() {
        if (Utils.isClickable()) {
            if (!TianyiApplication.isLogin) {
                JumpIntent.jump(getActivity(), LoginActivity.class);
                return;
            }

            if (Const.isOutRange != Const.INTEGER_0) {
                ToastUtils.show("该商品超出配送范围");
                return;
            }

            if (!TextUtils.isEmpty(eSpid)) {
                nowBuy();
            } else {
                showAddCartDialog();
            }
        }
    }

    @OnClick(R.id.iv_jrgwc)
    public void cAddCart() {
        if (Utils.isClickable()) {
            if (!TextUtils.isEmpty(eSpid) && !TextUtils.isEmpty(eStid)) {
                if (TianyiApplication.isLogin) {
                    if (Const.isOutRange != Const.INTEGER_0) {
                        ToastUtils.show("该商品超出配送范围");
                        return;
                    }
                    addShopCart();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("addcartDetails", "addcartDetails");
                    JumpIntent.jump(getActivity(), LoginActivity.class, bundle);
                }
            } else {
                if (Const.isOutRange != Const.INTEGER_0) {
                    ToastUtils.show("该商品超出配送范围");
                    return;
                }
                showAddCartDialog();
            }
        }
    }

    @OnClick(R.id.iv_gwc)
    public void toCart() {
        if (!Utils.isClickable()) {
            return;
        }

        if (!TianyiApplication.isLogin) {
            JumpIntent.jump(getActivity(), LoginActivity.class);
            return;
        }
        MainActivity.isCart = true;
        CartFragment.productId = productId;
        CartFragment.isShowBack = true;
        JumpIntent.jump(getActivity(), MainActivity.class);
    }

    private void initAuguments() {
        if (getArguments() != null) {
            productId = getArguments().getString("productId", "-1");
            merchantId = getArguments().getString("merchantId", "-1");
            if (getArguments().containsKey(Const.CONS_STR_HUNTER)) {
                hunterid = getArguments().getString(Const.CONS_STR_HUNTER, "0");
            }
            if (getArguments().containsKey(Const.CONS_STR_AUTHORID)) {
                authorid = getArguments().getString(Const.CONS_STR_AUTHORID, "0");
            }
            if (getArguments().containsKey(Const.CONS_STR_HID)) {
                hid = getArguments().getString(Const.CONS_STR_HID, "0");
            }
        }
    }

    private void initView() {
        RxBus.get().register(getActivity());

        mRoomContainer = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_px_video_container, null);
        mFragmentContainer = (FrameLayout) mRoomContainer.findViewById(R.id.fragment_container);
        videoPlayer = (LandLayoutVideoPx) mRoomContainer.findViewById(R.id.videoPlayer);

        mFragmentManager = getActivity().getSupportFragmentManager();
    }

    private void initVideo(int position) {

        if (null != mVideoData.get(position).getVideoAddress()) {
            videoPlayer.setUp(mVideoData.get(position).getVideoAddress(), true, new File(FileUtils.getPath()), "");
        }
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.INVISIBLE);
        videoPlayer.getFullscreenButton().setVisibility(View.GONE);

        //设置旋转
        orientationUtils = new OrientationUtils(getActivity(), videoPlayer);
        orientationUtils.setEnable(false);

        videoPlayer.setShowFullAnimation(false);
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);
        videoPlayer.startPlayLogic();
    }

    private void initVideoViewAdapter() {

        mPagerAdapter = new PagerAdapter();

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e(TAG, "mCurrentId == " + position + ", positionOffset == " + positionOffset +
                        ", positionOffsetPixels == " + positionOffsetPixels);
                mCurrentItem = position;
            }
        });

        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                ViewGroup viewGroup = (ViewGroup) page;
                Log.e(TAG, "page.id == " + page.getId() + ", position == " + position);

                if ((position < 0 && viewGroup.getId() != mCurrentItem)) {
                    View roomContainer = viewGroup.findViewById(R.id.room_container);
                    if (roomContainer != null && roomContainer.getParent() != null && roomContainer.getParent() instanceof ViewGroup) {
                        ((ViewGroup) (roomContainer.getParent())).removeView(roomContainer);
                    }
                }
                // 满足此种条件，表明需要加载直播视频，以及聊天室了
                if (viewGroup.getId() == mCurrentItem && position == 0 && mCurrentItem != mRoomId) {
                    if (mRoomContainer.getParent() != null && mRoomContainer.getParent() instanceof ViewGroup) {
                        ((ViewGroup) (mRoomContainer.getParent())).removeView(mRoomContainer);
                    }
                    loadVideo(viewGroup, mCurrentItem);
                }
            }
        });
        mViewPager.setAdapter(mPagerAdapter);
    }

    /**
     * 加载视频页面
     *
     * @param viewGroup
     * @param currentItem
     */
    private void loadVideo(ViewGroup viewGroup, int currentItem) {
        if (mRoomFragment != null) {
            PxVideoBean.ContentBean contentBean = mVideoData.get(mCurrentItem);
            mRoomFragment.setFragmentAuguments(contentBean);
        }

        if (!mInit) {
            mFragmentManager.beginTransaction().add(mFragmentContainer.getId(), mRoomFragment).commitAllowingStateLoss();
            mInit = true;
        }

        initVideo(currentItem);
        viewGroup.addView(mRoomContainer);
        mRoomId = currentItem;
    }

    /**
     * 刷新评论
     */
    public void flushComment(PxVideoBean.ContentBean mContentBean) {
        if (mRoomFragment != null) {
            mRoomFragment.setFragmentAuguments(mContentBean);
        }
    }

    /**
     * 刷新点赞
     */
    public void flushDz(PxVideoBean.ContentBean mContentBean) {
        if (mRoomFragment != null) {
            mRoomFragment.setFragmentAuguments(mContentBean);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    class PagerAdapter extends android.support.v4.view.PagerAdapter {

        @Override
        public int getCount() {
            return mVideoData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_px_video_item_df, null);
            view.setId(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(container.findViewById(position));
        }
    }

    /**
     * 获取品秀视频页面接口数据
     */
    private void requestPxVideoInfo() {

        mVideoData.clear();
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "memberId", TianyiApplication.appCommonBean.getId(),
                "productId", productId,
                "page", String.valueOf(page),
                "rows", String.valueOf(rows),
                "timestamp", timestamp);
        if (TextUtils.isEmpty(sign)) {
            return;
        }

        OkHttpUtils.post().url(Constants.VIDEO_PX_LIST)
                .addParams("memberId", TianyiApplication.appCommonBean.getId())
                .addParams("productId", productId)
                .addParams("page", String.valueOf(page))
                .addParams("rows", String.valueOf(rows))
                .addParams("timestamp", timestamp)
                .addParams("sign", sign)
                .addParams("client_type", "A")
                .build().execute(new AbstractPxVideoCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(TianyiApplication.getInstance().getString(R.string.app_on_request_error_msg));
            }

            @Override
            public void onResponse(PxVideoBean response, int id) {
                if (Const.INTEGER_1 == response.getCode()) {
                    if (response.getContent() != null) {
                        mVideoData.addAll(response.getContent());
                        if (mVideoData.size() > 0) {
                            mViewPager.setVisibility(View.VISIBLE);
                            includeEmptyVideoView.setVisibility(View.GONE);
                            initVideoViewAdapter();
                            requestAddPxLook();
                        } else {
                            mViewPager.setVisibility(View.GONE);
                            includeEmptyVideoView.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
    }

    /**
     * 增加品秀浏览量
     */
    private void requestAddPxLook() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "showId", String.valueOf(mVideoData.get(mCurrentItem).getId()),
                "clientId", Utils.getDeviceId(TianyiApplication.getInstance()),
                "timestamp", timestamp);
        if (TextUtils.isEmpty(sign)) {
            return;
        }

        OkHttpUtils.post().url(Constants.VIDEO_PX_ADDSHOW)
                .addParams("showId", String.valueOf(mVideoData.get(mCurrentItem).getId()))
                .addParams("clientId", Utils.getDeviceId(TianyiApplication.getInstance()))
                .addParams("timestamp", timestamp)
                .addParams("sign", sign)
                .addParams("client_type", "A")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(TianyiApplication.getInstance().getString(R.string.app_on_request_error_msg));
            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("sss", "response == " + response);
            }
        });
    }

    /**
     * --------------------------------------------------------------------------------------------
     * 混乱代码区域start
     *
     */

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
                MyLog.e("xxx", "-获取规格：" + response);
                if (response.getCode() == 1) {
                    if (response.getContent().getStandard().size() == 1) {
                        eSpid = response.getContent().getStandard().get(0).getStandardId() + "";
                    }
                    if (response.getContent().getClassify().size() != 0) {
                        eStid = String.valueOf(response.getContent().getClassify().get(0).getClassId());
                    }
                }
            }
        });
    }

    /**
     * 加入购物车接口
     */
    private void addShopCart() {

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

    /**
     * 立即购买接口
     */
    private void nowBuy() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("timestamp", timestamp,
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "pid", productId,
                "longitude", "null",
                "latitude", "null",
                "provinceName", TextUtils.isEmpty(TianyiApplication.appCommonBean.getProvinceName()) ? "null" : TianyiApplication.appCommonBean.getProvinceName(),
                "cityName", TextUtils.isEmpty(TianyiApplication.appCommonBean.getCityName()) ? "null" : TianyiApplication.appCommonBean.getCityName(),
                "countyName", TextUtils.isEmpty(TianyiApplication.appCommonBean.getCountyName()) ? "null" : TianyiApplication.appCommonBean.getCountyName(),
                "sid", eSpid,
                "num", num,
                "authorid", authorid,
                "hid", hunterid

        );

        OkHttpUtils.post().url(Constants.NOW_BUY)
                .addParams("timestamp", timestamp)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("pid", productId)
                .addParams("longitude", "null")
                .addParams("latitude", "null")
                .addParams("provinceName", TextUtils.isEmpty(TianyiApplication.appCommonBean.getProvinceName()) ? "null" : TianyiApplication.appCommonBean.getProvinceName())
                .addParams("cityName", TextUtils.isEmpty(TianyiApplication.appCommonBean.getCityName()) ? "null" : TianyiApplication.appCommonBean.getCityName())
                .addParams("countyName", TextUtils.isEmpty(TianyiApplication.appCommonBean.getCountyName()) ? "null" : TianyiApplication.appCommonBean.getCountyName())
                .addParams("num", num)
                .addParams("sid", eSpid)
                .addParams("authorid", authorid)
                .addParams("hid", hunterid)
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

    public void showAddCartDialog() {
        ProductFragment.isDetailsPop = true;
        fragment = AddCartPopFragment.newInstance(productId, merchantId, hunterid, authorid, hid, selectOne, selectTwo, sortOneName, sortTwoName);
        fragment.show(getFragmentManager(), "details");
    }

    @Subscribe
    public void onEventMainThread(UpdateSpEvent event) {
        MyLog.e("asdsada", event.isMoreSp() + "___name__" + event.getSpName());
        MyLog.e("asdsada", event.isMoreSp() + "___guige__" + event.getStandardOneName());

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
    }

    /**
     * 分享成功的接口
     */
    public void requestShareSuccessNet() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "showId", String.valueOf(mVideoData.get(mCurrentItem).getId()),
                "timestamp", timestamp);
        OkHttpUtils
                .post()
                .url(Constants.VIDEO_PX_SHARE_UPDATE_NUM)
                .addParams("showId", String.valueOf(mVideoData.get(mCurrentItem).getId()))
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .addParams("timestamp", timestamp)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                MyLog.e("sss", e.toString());
                ToastUtils.show(getString(R.string.str_net_error_message));
            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("sss", response);
            }
        });
    }

    public void setDismissShareDialog() {
        if (mRoomFragment != null) {
            mRoomFragment.dismissShareDialog();
        }
    }

    /**
     * --------------------------------------------------------------------------------------------
     * 混乱代码区域end
     */


    private GSYVideoPlayer getCurPlay() {
        if (videoPlayer != null && videoPlayer.getFullWindowPlayer() != null) {
            return videoPlayer.getFullWindowPlayer();
        }
        return videoPlayer;
    }

    public void setPause() {
        onPause();
    }

    public void setResume() {
        onResume();
    }

    @Override
    public void onPause() {
        if (videoPlayer != null) {
            getCurPlay().onVideoPause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (isResumePlay && videoPlayer != null) {
            getCurPlay().onVideoResume();
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (videoPlayer != null) {
            videoPlayer.release();
        }

        if (orientationUtils != null) {
            orientationUtils.releaseListener();
        }

    }
}
