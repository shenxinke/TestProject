package com.yst.onecity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.addorder.AddOrderActivity;
import com.yst.onecity.activity.chat.ConvaActivity;
import com.yst.onecity.adapter.CartAdapter;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.bean.BigCartBean;
import com.yst.onecity.bean.Cart;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.dialog.AbstractDeleteCartDialog;
import com.yst.onecity.eventbus.GoMallEvent;
import com.yst.onecity.interfaces.OnCartListener;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.RxCode;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
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
import okhttp3.Request;

import static com.yst.onecity.R.id.ll_product_integal;


/**
 * 购物车
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class CartFragment extends BaseFragment implements OnCartListener, View.OnClickListener, OnRefreshListener {

    @BindView(R.id.activity_back_iv)
    ImageView ivBack;
    @BindView(R.id.all_cost_tv)
    TextView allCostTv;
    @BindView(R.id.jiesuan_tv)
    TextView jiesuanTv;
    @BindView(R.id.checkbox_edit)
    TextView checkBoxEdit;
    @BindView(R.id.cartlv)
    ExpandableListView mCartExpandableListView;
    @BindView(R.id.all_check)
    ImageButton allCheck;
    @BindView(R.id.linear_all_check)
    LinearLayout linearAllCheck;
    @BindView(R.id.txt_all_check)
    TextView txtAllCheck;
    @BindView(R.id.activity_title_tv)
    TextView titleTv;
    @BindView(R.id.activity_title_right_chat_say_img)
    ImageView titleRightIv;
    @BindView(R.id.shop_cart_linear)
    LinearLayout shopCartLinear;
    @BindView(R.id.llayout_bottom)
    LinearLayout llayoutBottom;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.tv_cart_chat_num)
    TextView tvChatNum;
    @BindView(R.id.ll_jiesuan)
    LinearLayout llJiesuan;
    @BindView(R.id.ll_cancel_delete)
    LinearLayout llCancelDelete;
    /**
     * 是否可以下拉刷新
     */
    public static boolean isCartRrefresh;

    public static boolean shopCartisEmpty = false;
    private static final boolean DEBUG = true;
    private static final String TAG = CartFragment.class.getSimpleName();
    @BindView(R.id.flayout_say)
    FrameLayout flayoutSay;
    @BindView(R.id.title_layout)
    RelativeLayout titleLayout;
    @BindView(R.id.linear_bottom)
    LinearLayout linearBottom;
    Unbinder unbinder;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.txt_empty_handle)
    TextView txtemptyhandle;
    Unbinder unbinder1;
    @BindView(R.id.all_score_tv)
    TextView allScoreTv;
    @BindView(ll_product_integal)
    LinearLayout llIntegal;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.bulk_delete)
    TextView bulkDelete;
    @BindView(R.id.shop_cart)
    TextView shopCart;
    @BindView(R.id.ll_all_cost)
    LinearLayout llAllCost;
    @BindView(R.id.tv_no_expense)
    TextView tvNoExpense;
    private CartAdapter mCartAdapter;
    private ArrayList<Cart> mCartEntityList = new ArrayList<Cart>();
    private boolean isAllCheck;
    private String cid = "";
    private int mProductCount;
    /**
     * 是否展示返回按钮
     */
    public static boolean isShowBack;
    public static String hunterId;
    public static String productId;
    private StringBuilder cIds;
    private StringBuilder hunterIds;
    private StringBuilder authorIds;


    private int mfirstVisibleItem;
    private int mvisibleItemCount;
    private String timestamp;
    private boolean isEdit = false;
    private ArrayList<Integer> numlist = new ArrayList<>();
    private List<BigCartBean.ContentBean> userFulList = new ArrayList<>();
    private List<BigCartBean.ContentBean> unUserFul = new ArrayList<>();
    private int allCount;
    private boolean isNewYt;
    private ArrayList<Integer> idList;
    private ArrayList<Integer> hunterIdsList;
    private ArrayList<Integer> authorIdsList;


    @Override
    public int bindLayout() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return R.layout.fragment_cartnew;
    }

    @Override
    public void initData() {
        RxBus.get().register(this);
        titleTv.setText("购物车");
        titleRightIv.setVisibility(View.VISIBLE);
        if (shopCartisEmpty) {
            checkBoxEdit.setVisibility(View.GONE);
            shopCartLinear.setVisibility(View.VISIBLE);
            smartRefreshLayout.setEnabled(false);
        }
        llayoutBottom.setVisibility(View.INVISIBLE);

        requestCartData();
    }

    @gorden.rxbus2.Subscribe(code = RxCode.CODE_PUBLISH_ADD_SHOPPING_CARD, threadMode = ThreadMode.MAIN)
    public void getRefresh() {
        MyLog.e("aaa", "getRefresh");
        requestCartData();
    }

    public void getResume() {
        requestCartData();
    }

    @Override
    public void onResume() {
        Log.e("aaa", "onResume");
        isAllCheck = false;
        allCostTv.setText(String.format("%.2f", 0.00));
        jiesuanTv.setText("结算(0)");
        allCount = 0;
        if (allCount > 0) {
            llAllCost.setVisibility(View.VISIBLE);
            jiesuanTv.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_jiesuan_noempty));
            if (isNewYt) {
                llIntegal.setVisibility(View.VISIBLE);
            } else {
                llIntegal.setVisibility(View.INVISIBLE);
            }
        } else {
            llIntegal.setVisibility(View.INVISIBLE);
            llAllCost.setVisibility(View.INVISIBLE);
            jiesuanTv.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_jiesuan));
        }
        jiesuanTv.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_jiesuan));
//        reflushCart(mCartEntityList);
        isCartRrefresh = true;
        initListener();
        super.onResume();
    }

    @OnClick(R.id.activity_title_right_chat_say_img)
    public void toSay() {
        startActivity(new Intent(getActivity(), ConvaActivity.class));
    }

    private void initListener() {
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setEnableLoadmore(false);
        smartRefreshLayout.setEnableRefresh(true);
        linearAllCheck.setOnClickListener(this);
        txtAllCheck.setOnClickListener(this);
        allCheck.setOnClickListener(this);
        jiesuanTv.setOnClickListener(this);

        checkBoxEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEdit) {
                    for (int i = 0; i < mCartEntityList.size(); i++) {
                        mCartEntityList.get(i).setGroupEdit(!isEdit);
                    }
                    checkBoxEdit.setText("完成");
                    isEdit = true;
                    llJiesuan.setVisibility(View.GONE);
                    llCancelDelete.setVisibility(View.VISIBLE);
                    isCartRrefresh = false;
                    mCartAdapter.setIsShowUnserful(false);
                } else {
                    for (int i = 0; i < mCartEntityList.size(); i++) {
                        mCartEntityList.get(i).setGroupEdit(false);
                    }
                    checkBoxEdit.setText("编辑");
                    llJiesuan.setVisibility(View.VISIBLE);
                    llCancelDelete.setVisibility(View.GONE);
                    updateAllProductNum();
                    isEdit = false;
                    isCartRrefresh = true;
                    mCartAdapter.setIsShowUnserful(false);
                }
                if (mCartAdapter == null) {
                    return;
                }
                mCartAdapter.notifyDataSetChanged();
            }
        });

        mCartExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });

        mCartExpandableListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (mvisibleItemCount > 0 && mfirstVisibleItem == 0) {
                        if (view.getChildAt(0) != null && view.getChildAt(0).getTop() >= 0) {
                            isCartRrefresh = true;
                        } else {
                            isCartRrefresh = false;
                        }
                    } else {
                        isCartRrefresh = false;
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mfirstVisibleItem = firstVisibleItem;
                mvisibleItemCount = visibleItemCount;
            }
        });
    }

    private void flushCart() {

        if (mCartAdapter == null) {
            mCartAdapter = new CartAdapter(getActivity(), mCartEntityList);
            mCartExpandableListView.setAdapter(mCartAdapter);
            mCartAdapter.setOnCartListener(this);
        } else {
            mCartAdapter.setData(mCartEntityList);
        }

        for (int i = 0; i < mCartEntityList.size(); i++) {
            mCartExpandableListView.expandGroup(i);
        }
    }

    private void reflushCart(ArrayList<Cart> mCartEntityList) {
        mCartEntityList.clear();
        CartAdapter mCartAdapter = new CartAdapter(getActivity(), mCartEntityList);
        mCartExpandableListView.setAdapter(mCartAdapter);
        mCartAdapter.setOnCartListener(this);
        for (int i = 0; i < mCartEntityList.size(); i++) {
            mCartExpandableListView.expandGroup(i);
        }
    }

    /**
     * 请求购物车数据
     */
    private void requestCartData() {
        userFulList.clear();
        unUserFul.clear();
        mCartEntityList.clear();
        flushCart();
        mProductCount = 0;
        allCheck.setBackgroundResource(R.mipmap.shoppingcart_all_n);

        timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.GET_CART_LIST)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("sign", sign)
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .build().execute(new StringCallback() {
            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                smartRefreshLayout.finishRefresh(500);
                smartRefreshLayout.finishLoadmore(500);
                dismissInfoProgressDialog();
                smartRefreshLayout.setEnableRefresh(true);
            }

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                showInfoProgressDialog();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(TianyiApplication.getContext().getResources().getString(R.string.app_on_request_error_msg));
                if (null != shopCartLinear) {
                    smartRefreshLayout.setEnabled(true);
                    shopCartLinear.setVisibility(View.INVISIBLE);
                    checkBoxEdit.setVisibility(View.VISIBLE);
                }
                llayoutBottom.setVisibility(View.INVISIBLE);
                viewLine.setVisibility(View.INVISIBLE);
                shopCartLinear.setVisibility(View.VISIBLE);
                checkBoxEdit.setVisibility(View.GONE);
                smartRefreshLayout.setEnabled(false);
                if (smartRefreshLayout != null && smartRefreshLayout.isRefreshing()) {
                    smartRefreshLayout.setEnableRefresh(false);
                }
            }

            @Override
            public void onResponse(String s, int id) {
                MyLog.e("sss", "-gwc:" + s);
                if (smartRefreshLayout != null && smartRefreshLayout.isRefreshing()) {
                    smartRefreshLayout.setEnableRefresh(false);
                }
                BigCartBean cartBean = new Gson().fromJson(s, BigCartBean.class);
                if (cartBean.getCode() == 1) {
                    checkBoxEdit.setText("编辑");
                    isEdit = false;
                    List<BigCartBean.ContentBean> content = cartBean.getContent();
                    ArrayList<BigCartBean.ContentBean> productEntityList = new ArrayList<>();
                    ArrayList<Cart> cartList = new ArrayList<>();
                    if (content.size() < 1) {
                        checkBoxEdit.setClickable(false);
                        llCancelDelete.setVisibility(View.GONE);
                        llJiesuan.setVisibility(View.VISIBLE);
                        llayoutBottom.setVisibility(View.INVISIBLE);
                        viewLine.setVisibility(View.INVISIBLE);
                        shopCartLinear.setVisibility(View.VISIBLE);
                        checkBoxEdit.setVisibility(View.GONE);
                        smartRefreshLayout.setEnabled(false);
                    } else {
                        checkBoxEdit.setClickable(true);
                        llJiesuan.setVisibility(View.VISIBLE);
                        llCancelDelete.setVisibility(View.GONE);
                        llayoutBottom.setVisibility(View.VISIBLE);
                        viewLine.setVisibility(View.VISIBLE);
                        shopCartLinear.setVisibility(View.GONE);
                        checkBoxEdit.setVisibility(View.VISIBLE);
                        smartRefreshLayout.setEnabled(true);
                        int nyt = content.get(0).getIsNyt();
                        if (nyt == 0) {
                            isNewYt = true;
                        } else {
                            isNewYt = false;
                        }
                        if (isNewYt) {
                            for (int i = 0; i < content.size(); i++) {
                                mProductCount++;
                                numlist.add(content.get(i).getBuyNum());
                                if (content.get(i).getIsAbate() == 0) {
                                    unUserFul.add(content.get(i));
                                }
                                if (content.get(i).getIsAbate() == 1) {
                                    userFulList.add(content.get(i));
                                }
                            }
                            for (int i = 0; i < unUserFul.size(); i++) {
                                if (i == 0) {
                                    unUserFul.get(i).setUnUserful(true);
                                } else {
                                    unUserFul.get(i).setUnUserful(false);
                                }
                            }
                            productEntityList.addAll(userFulList);
                            productEntityList.addAll(unUserFul);
                            for (int i = 0; i < productEntityList.size(); i++) {
                                MyLog.e("sss", "---pro:" + productEntityList.get(i));
                            }
                        } else {
                            for (int i = 0; i < content.size(); i++) {
                                mProductCount++;
                                numlist.add(content.get(i).getBuyNum());
                                if (content.get(i).getIsAbate() == 0) {
                                    unUserFul.add(content.get(i));
                                }
                                if (content.get(i).getIsAbate() == 1) {
                                    userFulList.add(content.get(i));
                                }
                            }
                            for (int i = 0; i < unUserFul.size(); i++) {
                                if (i == 0) {
                                    unUserFul.get(i).setUnUserful(true);
                                } else {
                                    unUserFul.get(i).setUnUserful(false);
                                }
                            }
                            productEntityList.addAll(userFulList);
                            productEntityList.addAll(unUserFul);
                        }
                    }
                    MyLog.e("sss", "-productEntityList.size():" + productEntityList.size());
                    cartList.add(new Cart(false, false, productEntityList));
                    mCartEntityList.addAll(cartList);
                    flushCart();
                }
                MyLog.i("onResponse", "cart " + s);
            }
        });
    }

    /**
     * 批量删除购物车
     */
    private void deleteCart() {
        String cid = "";
        final StringBuilder cids = new StringBuilder();
        cids.delete(0, cids.length());
        int groupLen = mCartEntityList.size();
        for (int i = 0; i < groupLen; i++) {
            int childLen = mCartEntityList.get(i).getList().size();
            for (int j = 0; j < childLen; j++) {
                if (mCartEntityList.get(i).getList().get(j).isClick()) {
                    cids.append(mCartEntityList.get(i).getList().get(j).getId() + ",");
                }
            }
        }
        if (TextUtils.isEmpty(cids.toString())) {
            ToastUtils.show("请选择要删除的商品");
            return;
        }
        AbstractDeleteCartDialog deleteCartDialog = new AbstractDeleteCartDialog(getActivity()) {
            @Override
            public void onSureClick() {
                bulkDeleteCart(cids.toString());
            }
        };
        deleteCartDialog.setCancleStyle(R.drawable.shape_white_10, R.color.black, "取消");
        deleteCartDialog.setSureStyle(R.drawable.shape_gray_180dp_bg, R.color.black, "确认");
        deleteCartDialog.showDialog();


    }

    /**
     * 批量删除购物车请求
     */
    private void bulkDeleteCart(String cid) {
        timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "cid", cid,
                "timestamp", timestamp);

        OkHttpUtils.post()
                .url(Constants.DELETE_CART_PRODUCT)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("cid", cid)
                .addParams("sign", sign)
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.show(getActivity().getResources().getString(R.string.app_on_request_error_msg), Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onResponse(String s, int id) {
                        MyLog.e("luxc", s);
                        try {
                            JSONObject mJSONObject = new JSONObject(s);
                            if (mJSONObject.getInt(Const.CONS_STR_CODE) == 1) {
                                userFulList.clear();
                                unUserFul.clear();
                                requestCartData();
                                handleAllCost();
                            } else {
                                ToastUtils.show(mJSONObject.getString("msg"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.show("JSONException", Toast.LENGTH_SHORT);
                            llayoutBottom.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

    /**
     * 删除购物车请求
     */
    private void requestDeleteCart(String cid, final int groupPosition, final int childPosition) {
        timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "cid", cid,
                "timestamp", timestamp);

        OkHttpUtils.post()
                .url(Constants.DELETE_CART_PRODUCT)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("cid", cid)
                .addParams("sign", sign)
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.show(getActivity().getResources().getString(R.string.app_on_request_error_msg), Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onResponse(String s, int id) {
                        try {
                            JSONObject mJSONObject = new JSONObject(s);
                            if (mJSONObject.getInt(Const.CONS_STR_CODE) == 1) {
                                deleteProduct(groupPosition, childPosition);
                                mCartAdapter.setIsShowUnserful(false);
                                mCartAdapter.notifyDataSetChanged();
                                userFulList.clear();
                                unUserFul.clear();
                                requestCartData();
                                handleAllCost();
                            } else {
                                ToastUtils.show(mJSONObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.show("JSONException", Toast.LENGTH_SHORT);
                            llayoutBottom.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

    /**
     * 更新购物车请求
     */
    private void requestUpdateCart(String num, String cid) {
        timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "num", num,
                "cid", cid,
                "timestamp", timestamp);
        MyLog.e("ss", "cid:" + cid + "num:" + num);
        OkHttpUtils.post()
                .url(Constants.UPDATE_CART_LIST)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("num", num)
                .addParams("cid", cid)
                .addParams("sign", sign)
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.app_on_request_error_msg));
            }

            @Override
            public void onResponse(String s, int id) {
                MyLog.e("sss", "-更新：" + s);
                try {
                    JSONObject mJSONObject = new JSONObject(s);
                    if (mJSONObject.getInt(Const.CONS_STR_CODE) == 1) {
                        handleAllCost();
                    } else {
                        ToastUtils.show(mJSONObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 设置当前组的商品列表check状态
     *
     * @param groupPosition
     * @param check
     */
    private void setCheckGroup(int groupPosition, boolean check) {
        int len = mCartEntityList.get(groupPosition).getList().size();
        mCartEntityList.get(groupPosition).setGroupCheck(check);
        for (int i = 0; i < len; i++) {
            mCartEntityList.get(groupPosition).getList().get(i).setClick(check);
        }
    }

    private void setCheckChild(int groupPosition, int childPosition, boolean check) {
        if (mCartEntityList.get(groupPosition).getList().get(childPosition).getNum() != 0) {
            mCartEntityList.get(groupPosition).getList().get(childPosition).setClick(check);
        } else {
            ToastUtils.show("没有库存了~");
        }

        int len = mCartEntityList.get(groupPosition).getList().size();
        boolean groupCheck = true;
        for (int i = 0; i < len; i++) {
            if (mCartEntityList.get(groupPosition).getList().get(i).getIsAbate() != 0) {
                if (!mCartEntityList.get(groupPosition).getList().get(i).isClick()) {
                    groupCheck = false;
                }
            }
        }
        mCartEntityList.get(groupPosition).setGroupCheck(groupCheck);
    }

    private void handleAllCost() {
        double allCost = 0.0f;
        double allIntegal = 0.0f;
        allCount = 0;
        int groupLen = mCartEntityList.size();
        for (int i = 0; i < groupLen; i++) {
            int childLen = mCartEntityList.get(i).getList().size();
            for (int j = 0; j < childLen; j++) {
                if (mCartEntityList.get(i).getList().get(j).isClick() && mCartEntityList.get(i).getList().get(j).getIsAbate() != 0) {
                    allCost += mCartEntityList.get(i).getList().get(j).getPrice()
                            * mCartEntityList.get(i).getList().get(j).getBuyNum();
                    allCount++;
                    allIntegal += Double.parseDouble(ConstUtils.changeEmptyStringToZero(mCartEntityList.get(i).getList().get(j).getScorePrice()));
                }
            }
        }

        if (allCount > 0) {
            llAllCost.setVisibility(View.VISIBLE);
            jiesuanTv.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_jiesuan_noempty));
            if (isNewYt) {
                llIntegal.setVisibility(View.VISIBLE);
            } else {
                llIntegal.setVisibility(View.INVISIBLE);
            }
        } else {
            llIntegal.setVisibility(View.INVISIBLE);
            llAllCost.setVisibility(View.INVISIBLE);
            jiesuanTv.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_jiesuan));
        }

        allScoreTv.setText(String.format("%.2f", allIntegal));
        allCostTv.setText(String.format("%.2f", allCost));
        jiesuanTv.setText("去结算(" + allCount + ")");
        if (mCartEntityList.isEmpty()) {
            llayoutBottom.setVisibility(View.INVISIBLE);
        }
    }

    private void deleteProduct(int groupPosition, int childPosition) {
        mProductCount--;
        Cart cart = mCartEntityList.get(groupPosition);
        List<BigCartBean.ContentBean> productEntityList = cart.getList();
        productEntityList.remove(childPosition);
        if (productEntityList.size() == 0) {
            productEntityList.removeAll(productEntityList);
            cart = null;
            mCartEntityList.remove(groupPosition);
        }
        if (productEntityList.size() == 0 && mCartEntityList.size() == 0) {
            shopCartLinear.setVisibility(View.VISIBLE);
            checkBoxEdit.setVisibility(View.GONE);
            viewLine.setVisibility(View.INVISIBLE);
            smartRefreshLayout.setEnabled(false);
            shopCartisEmpty = true;
        }
        handleAllCost();
        flushCart();
    }

    @Override
    public void groupCheck(int groupPosition) {
        log("---groupCheck()---" + "groupPosition:" + groupPosition);
        setCheckGroup(groupPosition, !mCartEntityList.get(groupPosition).isGroupCheck());
        handleAllCost();
        mCartAdapter.setIsShowUnserful(false);
        mCartAdapter.notifyDataSetChanged();
        updateAllState();
    }

    @Override
    public void childCheck(int groupPosition, int childPosition) {
        log("---childCheck()---" + "groupPosition:" + groupPosition + "childPosition:" + childPosition);
        setCheckChild(groupPosition, childPosition, !mCartEntityList.get(groupPosition).getList().get(childPosition)
                .isClick());
        handleAllCost();
        mCartAdapter.setIsShowUnserful(false);
        mCartAdapter.notifyDataSetChanged();
        updateAllState();
    }

    @Override
    public void groupClick(int groupPosition) {
    }

    @Override
    public void childDelete(final int groupPosition, final int childPosition) {
        AbstractDeleteCartDialog deleteCartDialog = new AbstractDeleteCartDialog(getActivity()) {
            @Override
            public void onSureClick() {
                cid = String.valueOf(mCartEntityList.get(groupPosition).getList().get(childPosition).getId());
                //删除
                requestDeleteCart(cid, groupPosition, childPosition);
                handleAllCost();
            }
        };
        deleteCartDialog.setCancleStyle(R.drawable.shape_white_10, R.color.black, "取消");
        deleteCartDialog.setSureStyle(R.drawable.shape_gray_180dp_bg, R.color.black, "确认");
        deleteCartDialog.showDialog();
    }

    @Override
    public void childIncrease(int groupPosition, int childPosition) {
        log("---childIncrease()---");
        long productCount = mCartEntityList.get(groupPosition).getList().get(childPosition).getBuyNum();
        int productStock = mCartEntityList.get(groupPosition).getList().get(childPosition).getNum();


        if (productCount >= productStock) {
            ToastUtils.show("已达库存上限");
            mCartEntityList.get(groupPosition).getList().get(childPosition).setBuyNum(productStock);
        } else {
            mCartEntityList.get(groupPosition).getList().get(childPosition)
                    .setBuyNum(mCartEntityList.get(groupPosition).getList().get(childPosition).getBuyNum() + 1);
        }

        handleAllCost();
        mCartAdapter.setIsShowUnserful(false);
        mCartAdapter.notifyDataSetChanged();
    }

    @Override
    public void childReduce(int groupPosition, int childPosition) {
        int minNum = 1;
        if (mCartEntityList.get(groupPosition).getList().get(childPosition).getBuyNum() <= minNum) {
            ToastUtils.show("不能再减了");
            mCartEntityList.get(groupPosition).getList().get(childPosition)
                    .setBuyNum(1);
            return;
        }
        mCartEntityList.get(groupPosition).getList().get(childPosition)
                .setBuyNum(mCartEntityList.get(groupPosition).getList().get(childPosition).getBuyNum() - 1);
        handleAllCost();
        mCartAdapter.setIsShowUnserful(false);
        mCartAdapter.notifyDataSetChanged();
    }

    @Override
    public void childClick(int groupPosition, int childPosition) {
        log("---childClick()---");
    }

    @Override
    public void childEdit() {
        log("---childEdit()---");
        mCartAdapter.setIsShowUnserful(false);
        mCartAdapter.notifyDataSetChanged();
    }

    private void log(String message) {
        if (DEBUG) {
            MyLog.d(TAG, message);
        }
    }

    @Override
    public void onClick(View v) {
        if (!Utils.isClickable()) {
            return;
        }
        switch (v.getId()) {
            case R.id.delete:
                break;
            case R.id.linear_all_check:
            case R.id.txt_all_check:
            case R.id.all_check:
                isAllCheck = !isAllCheck;
                setAllCheck(isAllCheck);
                break;
            case R.id.jiesuan_tv:
                if (Utils.isClickable()) {
                    if (!verifyCart()) {
                        ToastUtils.show(getString(R.string.select_tip));
                    } else {
                        getProIdAndNums();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void setAllCheck(boolean check) {
        int shixiaoCount = 0;
        for (int i = 0; i < mCartEntityList.get(0).getList().size(); i++) {
            int isAbate = mCartEntityList.get(0).getList().get(i).getIsAbate();
            if (isAbate == 0) {
                shixiaoCount++;
            }
        }
        if (isEdit || shixiaoCount != mCartEntityList.get(0).getList().size()) {
            if (check) {
                allCheck.setBackgroundResource(R.mipmap.shoppingcart_all_s);
            } else {
                allCheck.setBackgroundResource(R.mipmap.shoppingcart_all_n);
            }
            int groupLen = mCartEntityList.size();
            for (int i = 0; i < groupLen; i++) {
                setCheckGroup(i, check);
            }
            handleAllCost();
            mCartAdapter.setIsShowUnserful(false);
            mCartAdapter.notifyDataSetChanged();
            updateAllState();
        } else {
            ToastUtils.show("选择的商品都失效");
        }

    }

    /**
     * 是否有一个子商品选中
     *
     * @return
     */
    private boolean verifyCart() {
        int groupLength = mCartEntityList.size();
        for (int i = 0; i < groupLength; i++) {
            int childLen = mCartEntityList.get(i).getList().size();
            for (int j = 0; j < childLen; j++) {
                if (mCartEntityList.get(i).getList().get(j).isClick()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void updateAllState() {
        int groupLen = mCartEntityList.size();
        isAllCheck = true;
        for (int i = 0; i < groupLen; i++) {
            if (!mCartEntityList.get(i).isGroupCheck()) {
                isAllCheck = false;
            }
        }
        if (isAllCheck) {
            allCheck.setBackgroundResource(R.mipmap.shoppingcart_all_s);
        } else {
            allCheck.setBackgroundResource(R.mipmap.shoppingcart_all_n);
        }
    }

    private void getProIdAndNums() {
        idList = new ArrayList<>();
        hunterIdsList = new ArrayList<>();
        authorIdsList = new ArrayList<>();

        idList.clear();
        hunterIdsList.clear();
        authorIdsList.clear();

        cIds = new StringBuilder();
        hunterIds = new StringBuilder();
        authorIds = new StringBuilder();

        cIds.delete(0, cIds.length());
        hunterIds.delete(0, hunterIds.length());
        authorIds.delete(0, authorIds.length());

        for (int i = 0; i < mCartEntityList.size(); i++) {
            Cart cartEntity = mCartEntityList.get(i);
            List<BigCartBean.ContentBean> productEntityList = cartEntity.getList();
            for (int j = 0; j < productEntityList.size(); j++) {
                BigCartBean.ContentBean productEntity = productEntityList.get(j);
                if (productEntity.getIsAbate() != 0) {
                    if (productEntity.isClick()) {
                        MyLog.e("sss", "cid: " + productEntity.getId());
                        idList.add(productEntity.getId());
                        hunterIdsList.add(productEntity.getHunterId());
                        authorIdsList.add(productEntity.getAuthorId());
                    }
                }
            }
        }

        for (int j = 0; j < idList.size(); j++) {
            cIds.append(idList.get(j));
            if (j != idList.size() - 1) {
                cIds.append(",");
            }
        }
        for (int j = 0; j < hunterIdsList.size(); j++) {
            hunterIds.append(hunterIdsList.get(j));
            if (j != hunterIdsList.size() - 1) {
                hunterIds.append(",");
            }
        }

        for (int j = 0; j < authorIdsList.size(); j++) {
            authorIds.append(authorIdsList.get(j));
            if (j != authorIdsList.size() - 1) {
                authorIds.append(",");
            }
        }

        String strCids = cIds.toString();
        String strHunterIds = hunterIds.toString();
        String strAuthorIds = authorIds.toString();

        MyLog.e("sss", "cidss: " + strCids);
        if (TextUtils.isEmpty(strCids)) {
            ToastUtils.show("请选择商品");
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString(AddOrderActivity.CIDS, strCids);
        bundle.putString(AddOrderActivity.HUNTERIDS, strHunterIds);
        bundle.putString(AddOrderActivity.AUTHORIDS, strAuthorIds);
        JumpIntent.jump(getActivity(), AddOrderActivity.class, bundle);
    }

    /**
     * 更新所有购物车购买数量
     */
    private void updateAllProductNum() {
        StringBuilder sbNums = new StringBuilder();
        StringBuilder sbCids = new StringBuilder();
        sbNums.delete(0, sbNums.length());
        sbCids.delete(0, sbCids.length());
        for (int i = 0; i < mCartEntityList.size(); i++) {
            Cart cartEntity = mCartEntityList.get(i);
            List<BigCartBean.ContentBean> productEntityList = cartEntity.getList();
            for (int j = 0; j < productEntityList.size(); j++) {
                BigCartBean.ContentBean productEntity = productEntityList.get(j);
                if (productEntity.getIsAbate() != 0) {
                    sbNums.append(productEntity.getBuyNum());
                    sbNums.append(",");
                    sbCids.append(productEntity.getId());
                    sbCids.append(",");
                }
            }
        }


        if (TextUtils.isEmpty(sbNums) || TextUtils.isEmpty(sbCids)) {
            return;
        }
        String num = sbNums.toString();
        String cid = sbCids.toString();

        MyLog.e("sss", "-num:" + num + "cid:" + cid);
        requestUpdateCart(num, cid);
    }

    @OnClick({R.id.cancel, R.id.bulk_delete, R.id.txt_empty_handle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                for (int i = 0; i < mCartEntityList.size(); i++) {
                    mCartEntityList.get(i).setGroupEdit(false);
                }
                checkBoxEdit.setText("编辑");
                llCancelDelete.setVisibility(View.GONE);
                llJiesuan.setVisibility(View.VISIBLE);
                mCartEntityList.clear();
                flushCart();
                requestCartData();
                handleAllCost();
                break;
            case R.id.bulk_delete:
                deleteCart();
                break;
            case R.id.txt_empty_handle:
                EventBus.getDefault().post(new GoMallEvent());
                break;
            default:
                break;
        }
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        isAllCheck = false;
        isEdit = false;
        checkBoxEdit.setText("编辑");
        requestCartData();
        handleAllCost();
        Log.e("aaa", "onRefresh");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("aaa", "onStop");
        RxBus.get().unRegister(this);
    }


    /**
     * 切换Fragment时每次都会执行
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        requestCartData();
    }
}
