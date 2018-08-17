package com.yst.onecity.activity.addorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.member.AddressManageActivity;
import com.yst.onecity.activity.member.PayCenterActivity;
import com.yst.onecity.adapter.addorder.MyExpandableAdapter;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.addorder.OrderBean;
import com.yst.onecity.callbacks.AbstractSureOderCallBack;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.dialog.AbstractLogoutDialog;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.RxCode;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.UiUtil;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import gorden.rxbus2.RxBus;
import okhttp3.Call;
import okhttp3.Request;

import static com.yst.onecity.config.Const.STR0;
import static com.yst.onecity.config.Const.STR1;

/**
 * 确认订单页面
 *
 * @author jiaofan
 * @version 4.0.0
 * @date 2018/3/22
 */
public class AddOrderActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.expandableListView)
    ExpandableListView listview;
    @BindView(R.id.tv_total_huo)
    TextView tvTotalHuo;
    @BindView(R.id.tv_product_num)
    TextView tvProductNum;
    @BindView(R.id.tv_total_score)
    TextView tvTotalScore;
    @BindView(R.id.iv_total_score)
    ImageView ivTotalScore;
    @BindView(R.id.order_bottom)
    RelativeLayout orderBottom;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.to_pay_tv)
    TextView toPay;
    /**
     * 收货人姓名
     */
    private TextView mAddressName;
    /**
     * 收货人手机号
     */
    private TextView mAddressPhone;
    /**
     * 收货人详细地址
     */
    private TextView mAddressDetail;
    /**
     * 没有默认收货地址提示语
     */
    private RelativeLayout mAddressNo;
    /**
     * 收货地址头布局
     */
    private RelativeLayout mAddressRel;
    public static String CIDS = "cIds";
    public static String HUNTERIDS = "hunterIds";
    public static String AUTHORIDS = "authorIds";

    private int addressID = -1;
    public static int ADDRESSCODE = 1000;
    /**
     * 商品总金额
     */
    private double totalMoney;
    /**
     * 商品总数量
     */
    private int productNum;
    private double freight;
    private double scoreFreight;
    /**
     * 商品总积分
     */
    private double totalScore;
    private List<OrderBean.ContentBean.MerchantListBean> merchantList = new ArrayList<>();
    private MyExpandableAdapter adapter;
    /**
     * 购物车id
     */
    private String cIds;
    private String hunterIds;
    private String authorIds;

    /**
     * 商品id
     */
    private String productId;
    /**
     * 规格id
     */
    private String sid;
    /**
     * 是否立即购买
     */
    private boolean isNowBuy;
    /**
     * 购买数量
     */
    private String num;
    private String authorid;
    private String hunterId;
    private String longitudeId = "";
    private String latitudeId = "";
    /**
     * type 区分从购物车跳转还是立即购买 0： 购物车 1：立即购买
     */
    private int type;
    /**
     * 跳转发票页面请求码
     */
    public static int INVOICE_CODE = 123;
    /**
     * 所在店铺的下标
     */
    private int mCurrentPosition = -1;
    /**
     * 发票个人名称
     */
    private String mInvoiceName;
    /**
     * 发票单位名称
     */
    private String mInvoiceHead;
    /**
     * 发票识别号
     */
    private String mInvoiceCode;
    /**
     * 发票类型
     */
    private int mInvoiceType;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;
    private String province;
    private String city;
    private String district;
    private boolean flag = false;

    @Override
    public int bindLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return R.layout.activity_new_add_order;
    }

    @Override
    public void initData() {
        setTitleBarTitle("确认订单");
        addHeader();
        initOrderData();
        isNowBuy = getIntent().getBooleanExtra("nowBuy", false);
        if (isNowBuy) {
            type = 1;
            productId = getIntent().getStringExtra("productId");
            sid = getIntent().getStringExtra("sid");
            num = getIntent().getStringExtra("num");
            authorid = getIntent().getStringExtra("authorid");
            hunterId = getIntent().getStringExtra("hunterId");
            longitudeId = getIntent().getStringExtra("longitude");
            latitudeId = getIntent().getStringExtra("latitude");
            MyLog.e("zzz", "productId===" + productId);
            MyLog.e("zzz", "sid===" + sid);
            MyLog.e("zzz", "num===" + num);
            MyLog.e("zzz", "authorid===" + authorid);
            MyLog.e("zzz", "hunterId===" + hunterId);
            MyLog.e("zzz", "longitudeId===" + longitudeId);
            MyLog.e("zzz", "latitudeId===" + latitudeId);
        } else {
            type = 0;
            cIds = getIntent().getStringExtra(AddOrderActivity.CIDS);
            hunterIds = getIntent().getStringExtra(AddOrderActivity.HUNTERIDS);
            authorIds = getIntent().getStringExtra(AddOrderActivity.AUTHORIDS);
            MyLog.e("zzz", "cIds===" + cIds);
            MyLog.e("zzz", "hunterIds===" + hunterIds);
            MyLog.e("zzz", "authorIds===" + authorIds);
        }

        if (isNowBuy) {
            //从立即购买跳转过来，获取收货地址的经纬度
            getNowBuyPosition(longitudeId, latitudeId);
        } else {
            //从购物车跳转过来，获取收货地址的经纬度
            getAddressPosition("", "");
        }

        listview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

    }

    /**
     * 初始化确认订单页面数据
     */
    private void initOrderData() {
        adapter = new MyExpandableAdapter(merchantList, AddOrderActivity.this);
        listview.setAdapter(adapter);

        //发票点击事件
        adapter.setOnInvoiceClick(new MyExpandableAdapter.InvoiceClick() {
            @Override
            public void onInvoiceClick(int groupPosition) {
                MyLog.e("zzz", "groupPosition" + groupPosition);
                mCurrentPosition = groupPosition;
                Bundle bundle = new Bundle();
                String typeStr = merchantList.get(groupPosition).getInvoiceType();
                String name = merchantList.get(groupPosition).getInvoiceName();
                String mark = merchantList.get(groupPosition).getInvoiceMark();
                MyLog.e("zzz--------------------------", typeStr + "---" + name + "---" + mark);
                if (STR0.equals(typeStr)) {
                    bundle.putString("typeSecond", typeStr);
                    bundle.putString("nameSecond", name);
                } else if (STR1.equals(typeStr)) {
                    bundle.putString("typeSecond", typeStr);
                    bundle.putString("headSecond", name);
                    bundle.putString("codeSecond", mark);
                }
                JumpIntent.jump(AddOrderActivity.this, InvoiceActivity.class, bundle, INVOICE_CODE);
            }
        });
    }

    /**
     * 获取收货地址的经纬度，从立即购买跳转
     *
     * @version 4.0.0
     */
    private void getNowBuyPosition(String nowLongid, String nowLatid) {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "timestamp", timestamp,
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "pid", productId,
                "sid", sid,
                "num", num,
                "longitude", TextUtils.isEmpty(nowLongid) ? "" : nowLongid,
                "latitude", TextUtils.isEmpty(nowLatid) ? "" : nowLatid);
        OkHttpUtils.post().url(Constants.NOW_BUY)
                .addParams("timestamp", timestamp)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("pid", productId)
                .addParams("sid", sid)
                .addParams("num", num)
                .addParams("longitude", TextUtils.isEmpty(nowLongid) ? "" : nowLongid)
                .addParams("latitude", TextUtils.isEmpty(nowLatid) ? "" : nowLatid)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractSureOderCallBack() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.str_net_error_message));
                MyLog.e("zzz_getNowBuyPosition", e.toString());
            }

            @Override
            public void onResponse(OrderBean response, int id) {
                MyLog.e("zzz_getNowBuyPosition", "response_getNowBuyPosition");
                if (response.getCode() == Const.INTEGER_1) {
                    if (response.getContent().getAddress().getAddress().getDefAdd() == Const.INTEGER_1) {
                        //得到经纬度
                        longitude = response.getContent().getAddress().getAddress().getLongitude();
                        latitude = response.getContent().getAddress().getAddress().getLatitude();
                        province = response.getContent().getAddress().getAddress().getProName();
                        city = response.getContent().getAddress().getAddress().getCityName();
                        district = response.getContent().getAddress().getAddress().getDistrictName();
                        MyLog.e("zzz", "longitude===" + longitude);
                        MyLog.e("zzz", "latitude===" + latitude);
                        MyLog.e("zzz", "province===" + province);
                        MyLog.e("zzz", "city===" + city);
                        MyLog.e("zzz", "district===" + district);
                    }
                    nowBuy(longitude, latitude, province, city, district);
                } else {
                    ToastUtils.show(response.getMsg());
                }
            }
        });
    }

    /**
     * 获取收货地址的经纬度，从购物车跳转
     *
     * @version 4.0.0
     */
    private void getAddressPosition(String longid, String latid) {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "timestamp", timestamp,
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "longitude", TextUtils.isEmpty(longid) ? "" : longid,
                "latitude", TextUtils.isEmpty(latid) ? "" : latid,
                "cids", cIds);
        OkHttpUtils.post().url(Constants.TO_PAY)
                .addParams("timestamp", timestamp)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("longitude", TextUtils.isEmpty(longid) ? "" : longid)
                .addParams("latitude", TextUtils.isEmpty(latid) ? "" : latid)
                .addParams("cids", cIds)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractSureOderCallBack() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.str_net_error_message));
                MyLog.e("zzz_getAddressPosition", e.toString());
            }

            @Override
            public void onResponse(OrderBean response, int id) {
                MyLog.e("zzz_getAddressPosition", "response_getAddressPosition");
                if (response.getCode() == Const.INTEGER_1) {
                    if (response.getContent().getAddress().getAddress().getDefAdd() == Const.INTEGER_1) {
                        //得到经纬度
                        longitude = response.getContent().getAddress().getAddress().getLongitude();
                        latitude = response.getContent().getAddress().getAddress().getLatitude();
                        province = response.getContent().getAddress().getAddress().getProName();
                        city = response.getContent().getAddress().getAddress().getCityName();
                        district = response.getContent().getAddress().getAddress().getDistrictName();
                        MyLog.e("zzz", "longitude===" + longitude);
                        MyLog.e("zzz", "latitude===" + latitude);
                        MyLog.e("zzz", "province===" + province);
                        MyLog.e("zzz", "city===" + city);
                        MyLog.e("zzz", "district===" + district);
                    }
                    getData(longitude, latitude, province, city, district);
                } else {
                    ToastUtils.show(response.getMsg());
                }
            }
        });
    }

    /**
     * 添加收货地址头布局
     *
     * @version 4.0.0
     */
    private void addHeader() {
        View headerView = View.inflate(this, R.layout.header_addorder, null);
        mAddressName = (TextView) headerView.findViewById(R.id.address_name_tv);
        mAddressPhone = (TextView) headerView.findViewById(R.id.address_phone);
        mAddressDetail = (TextView) headerView.findViewById(R.id.address_tv);
        mAddressNo = (RelativeLayout) headerView.findViewById(R.id.address_no_tv);
        mAddressRel = (RelativeLayout) headerView.findViewById(R.id.adress_ll);
        listview.addHeaderView(headerView);
        mAddressNo.setOnClickListener(this);
        mAddressRel.setOnClickListener(this);
        mAddressRel.setVisibility(View.GONE);
        mAddressNo.setVisibility(View.VISIBLE);
    }

    /**
     * 从立即购买按钮跳转过来，不需要购物车id
     * <p>
     * 商品结算，得到收货地址，得到订单数据，适配二级列表
     *
     * @version 4.0.0
     */
    private void nowBuy(String longitude, String latitude, String province, String city, String district) {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "timestamp", timestamp,
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "pid", productId,
                "sid", sid,
                "num", num,
                "authorid", authorid,
                "hid", TextUtils.isEmpty(hunterId) ? "" : hunterId,
                "longitude", TextUtils.isEmpty(longitude) ? "" : longitude,
                "latitude", TextUtils.isEmpty(latitude) ? "" : latitude,
                "provinceName", TextUtils.isEmpty(province) ? "" : province,
                "cityName", TextUtils.isEmpty(city) ? "" : city,
                "countyName", TextUtils.isEmpty(district) ? "" : district);
        OkHttpUtils.post().url(Constants.NOW_BUY)
                .addParams("timestamp", timestamp)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("pid", productId)
                .addParams("sid", sid)
                .addParams("num", num)
                .addParams("authorid", authorid)
                .addParams("hid", TextUtils.isEmpty(hunterId) ? "" : hunterId)
                .addParams("longitude", TextUtils.isEmpty(longitude) ? "" : longitude)
                .addParams("latitude", TextUtils.isEmpty(latitude) ? "" : latitude)
                .addParams("provinceName", TextUtils.isEmpty(province) ? "" : province)
                .addParams("cityName", TextUtils.isEmpty(city) ? "" : city)
                .addParams("countyName", TextUtils.isEmpty(district) ? "" : district)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractSureOderCallBack() {
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
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.str_net_error_message));
                MyLog.e("zzz_nowBuy", e.toString());
            }

            @Override
            public void onResponse(OrderBean response, int id) {
                MyLog.e("zzz_nowBuy", "response_nowBuy");
                if (response.getCode() == Const.INTEGER_1) {

                    if (response.getContent() != null &&
                        response.getContent().getAddress() != null &&
                        response.getContent().getAddress().getAddress() != null) {
                        OrderBean.ContentBean.AddressBeanX.AddressBean addressContent = response.getContent().getAddress().getAddress();

                        if (addressContent.getDefAdd() == Const.INTEGER_1) {
                            mAddressRel.setVisibility(View.VISIBLE);
                            mAddressNo.setVisibility(View.GONE);
                            mAddressName.setText(Utils.signString(addressContent.getUser_name()));
                            mAddressPhone.setText(addressContent.getMobile());
                            mAddressDetail.setText("收货地址: "
                                    + addressContent.getProName()
                                    + addressContent.getCityName()
                                    + addressContent.getDistrictName()
                                    + addressContent.getDetail_address());
                            addressID = addressContent.getId();
                        } else {
                            mAddressRel.setVisibility(View.GONE);
                            mAddressNo.setVisibility(View.VISIBLE);
                        }
                        setListData(response.getContent());
                        updateProductNum(response.getContent());
                    }
                } else {
                    ToastUtils.show(response.getMsg());
                }
            }
        });
    }

    /**
     * 从购物车跳转过来，需要购物车id
     * <p>
     * 商品结算，得到收货地址，得到订单数据，适配二级列表
     *
     * @version 4.0.0
     */
    private void getData(String longitude, String latitude, String province, String city, String district) {
        totalMoney = 0;
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "timestamp", timestamp,
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "longitude", TextUtils.isEmpty(longitude) ? "" : longitude,
                "latitude", TextUtils.isEmpty(latitude) ? "" : latitude,
                "provinceName", TextUtils.isEmpty(province) ? "" : province,
                "cityName", TextUtils.isEmpty(city) ? "" : city,
                "countyName", TextUtils.isEmpty(district) ? "" : district,
                "cids", cIds);

        OkHttpUtils.post().url(Constants.TO_PAY)
                .addParams("timestamp", timestamp)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("longitude", TextUtils.isEmpty(longitude) ? "" : longitude)
                .addParams("latitude", TextUtils.isEmpty(latitude) ? "" : latitude)
                .addParams("provinceName", TextUtils.isEmpty(province) ? "" : province)
                .addParams("cityName", TextUtils.isEmpty(city) ? "" : city)
                .addParams("countyName", TextUtils.isEmpty(district) ? "" : district)
                .addParams("cids", cIds)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractSureOderCallBack() {
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
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.str_net_error_message));
                MyLog.e("zzz_getData", e.toString());
            }

            @Override
            public void onResponse(OrderBean response, int id) {
                MyLog.e("zzz_getData", "response_getData");
                if (response.getCode() == Const.INTEGER_1) {

                    if (response.getContent() != null &&
                        response.getContent().getAddress() != null &&
                        response.getContent().getAddress().getAddress() != null) {
                        OrderBean.ContentBean.AddressBeanX.AddressBean addressContent = response.getContent().getAddress().getAddress();

                        if (addressContent.getDefAdd() == Const.INTEGER_1) {
                            mAddressRel.setVisibility(View.VISIBLE);
                            mAddressNo.setVisibility(View.GONE);
                            mAddressName.setText(Utils.signString(addressContent.getUser_name()));
                            mAddressPhone.setText(addressContent.getMobile());
                            mAddressDetail.setText("收货地址: "
                                    + addressContent.getProName()
                                    + addressContent.getCityName()
                                    + addressContent.getDistrictName()
                                    + addressContent.getDetail_address());
                            addressID = addressContent.getId();
                        } else {
                            mAddressRel.setVisibility(View.GONE);
                            mAddressNo.setVisibility(View.VISIBLE);
                        }
                        setListData(response.getContent());
                        updateProductNum(response.getContent());
                    }
                } else {
                    ToastUtils.show(response.getMsg());
                }
            }
        });
    }

    /**
     * 设置二级列表适配器
     *
     * @param response 确认订单返回数据
     * @version 4.0.0
     */
    private void setListData(OrderBean.ContentBean response) {
        if (response == null || response.getMerchantList() == null) {
            return;
        }
        merchantList = response.getMerchantList();
        adapter.setDataList(merchantList);
        for (int i = 0; i < merchantList.size(); i++) {
            listview.expandGroup(i, true);
        }
        listview.smoothScrollToPositionFromTop(0, 0);
    }

    /**
     * 更新商品数量，总金额，总积分价
     *
     * @version 4.0.0
     */
    private void updateProductNum(OrderBean.ContentBean response) {
        flag = false;
        totalMoney = 0;
        productNum = 0;
        totalScore = 0;
        freight = 0;
        scoreFreight = 0;

        for (int i = 0; i < response.getMerchantList().size(); i++) {
            for (int k = 0; k < response.getMerchantList().get(i).getProductDetail().size(); k++) {
                OrderBean.ContentBean.MerchantListBean.ProductDetailBean productBean = response.getMerchantList().get(i).getProductDetail().get(k);
                if (productBean.getIsScope() == 0) {
                    flag = true;
                    totalMoney += productBean.getBuy_num() * productBean.getPrice();
                    productNum += productBean.getBuy_num();
                    totalScore += productBean.getBuy_num() * productBean.getScorePrice();
                }
            }

            if (response.getMerchantList().get(i).getIsNyt() == Const.INTEGER_1) {
                Const.ISNEWYETAI = Const.INTEGER_1;
                tvTotalScore.setVisibility(View.GONE);
                ivTotalScore.setVisibility(View.GONE);
                tvTotalHuo.setVisibility(View.GONE);
            } else {
                Const.ISNEWYETAI = Const.INTEGER_0;
                tvTotalScore.setVisibility(View.VISIBLE);
                ivTotalScore.setVisibility(View.VISIBLE);
                tvTotalHuo.setVisibility(View.VISIBLE);
            }
        }
        if (!flag) {
            toPay.setEnabled(false);
            toPay.setBackgroundColor(ContextCompat.getColor(this, R.color.color_eeeeee));
        } else {
            toPay.setEnabled(true);
            toPay.setBackgroundColor(ContextCompat.getColor(this, R.color.color_bank_card_btn));
        }
        tvProductNum.setText(String.format("共%1$d件商品", productNum));
        tvTotalMoney.setText(String.format(getString(R.string.string_money), String.format("%.2f", totalMoney)));
        tvTotalScore.setText(String.format("%.2f", totalScore));
    }

    @OnClick({R.id.ll_back, R.id.to_pay_tv})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                onBackPressed();
                break;
            //去付款
            case R.id.to_pay_tv:
                if (!Utils.isClickable()) {
                    return;
                }
                toCreateOrder();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        showTipsDialog();
    }

    /**
     * 显示退出此页面的提示弹框
     */
    private void showTipsDialog() {
        AbstractLogoutDialog dialog = new AbstractLogoutDialog(this) {
            @Override
            public void onSureClick() {
                RxBus.get().send(RxCode.CODE_PUBLISH_ADD_SHOPPING_CARD);
                AddOrderActivity.this.finish();
            }

            @Override
            public void onCancelClick() {

            }
        };
        dialog.setText("", Const.CONS_STR_LOGOUTORDER);
        dialog.showDialog();
    }

    /**
     * 去下单
     *
     * @version 4.0.0
     */
    private void toCreateOrder() {
        if (addressID == -1) {
            ToastUtils.show(getString(R.string.addres_no_error));
            return;
        }
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "timestamp", timestamp,
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "type", String.valueOf(type),
                "list", getJson(),
                "aId", String.valueOf(addressID));

        if (TextUtils.isEmpty(sign)) {
            return;
        }

        OkHttpUtils.post().url(Constants.CREATE_ORDER)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("list", getJson())
                .addParams("type", String.valueOf(type))
                .addParams("aId", String.valueOf(addressID))
                .addParams("sign", sign)
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
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
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(R.string.app_on_request_error_msg, Toast.LENGTH_SHORT);
            }

            @Override
            public void onResponse(String s, int id) {
                MyLog.e("response", "order-response" + s);
                try {
                    JSONObject obj = new JSONObject(s);
                    if (obj.getInt(Const.CONS_STR_CODE) == Const.INTEGER_1) {
                        ToastUtils.show("下单成功");
                        Constants.ORDER_ISMEMBER = Const.INTEGER_0;
                        RxBus.get().send(RxCode.CODE_PUBLISH_ADD_SHOPPING_CARD);
                        Bundle bundle = new Bundle();
                        bundle.putString("orderIds", obj.getJSONObject("content").getString("orderids"));
                        bundle.putString("price", String.valueOf(totalMoney + freight));
                        bundle.putString("score", String.valueOf(totalScore + scoreFreight));
                        JumpIntent.jump(AddOrderActivity.this, PayCenterActivity.class, bundle, true);
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
     * 订单数据转化json串
     *
     * @return json串
     * @version 4.0.0
     */
    private String getJson() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            for (int i = 0; i < merchantList.size(); i++) {
                JSONArray jsonArrayChild = new JSONArray();
                JSONObject serverObj = new JSONObject();
                int count = 0;
                for (int k = 0; k < merchantList.get(i).getProductDetail().size(); k++) {
                    if (merchantList.get(i).getProductDetail().get(k).getIsScope() == 0) {
                        OrderBean.ContentBean.MerchantListBean.ProductDetailBean productBean = merchantList.get(i).getProductDetail().get(k);
                        JSONObject jsonObjectChild = new JSONObject();
                        jsonObjectChild.put("psId", productBean.getSpid());
                        jsonObjectChild.put("count", productBean.getBuy_num());
                        jsonObjectChild.put("payType", "1");
                        jsonObjectChild.put("hid", productBean.getHunterid());
                        jsonObjectChild.put("authorId", productBean.getAuthorid());
                        jsonArrayChild.put(jsonObjectChild);
                        serverObj.put("psList", jsonArrayChild);
                        count ++;
                    }
                }

                if (count == 0){
                    continue;
                }
                JSONObject merchantObj = new JSONObject();
                merchantObj.put("id", merchantList.get(i).getMerchantId());

                serverObj.put("merchant", merchantObj);
                serverObj.put("message", merchantList.get(i).getMessage());
                serverObj.put("invoiceType", merchantList.get(i).getInvoiceType());
                serverObj.put("invoiceName", merchantList.get(i).getInvoiceName());
                serverObj.put("invoiceMark", merchantList.get(i).getInvoiceMark());

                jsonArray.put(serverObj);
            }
            jsonObject.put("list", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyLog.e("zzz", jsonObject.toString());
        return jsonObject.toString();
    }

    /**
     * 选择地址点击事件
     *
     * @version 4.0.0
     */
    @Override
    public void onClick(View view) {
        if (Utils.isClickable()) {
            switch (view.getId()) {
                case R.id.address_no_tv:
                case R.id.adress_ll:
                    Intent intent = new Intent(this, AddressManageActivity.class);
                    intent.putExtra("ADDRESSCODE", ADDRESSCODE);
                    intent.putExtra("ADDRESSID", addressID);
                    startActivityForResult(intent, ADDRESSCODE);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UiUtil.getHandler().removeCallbacksAndMessages(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //地址管理回传数据
        if (resultCode == Const.INTEGER_100) {
            if (requestCode == ADDRESSCODE) {
                mAddressRel.setVisibility(View.VISIBLE);
                mAddressNo.setVisibility(View.GONE);
                String longitudeResult = data.getStringExtra("longitudeResult");
                String latitudeResult = data.getStringExtra("latitudeResult");
                String province = data.getStringExtra("province");
                String city = data.getStringExtra("city");
                String district = data.getStringExtra("district");
                String address = data.getStringExtra("address");
                mAddressName.setText(data.getStringExtra("name"));
                mAddressPhone.setText(data.getStringExtra("phone"));
                mAddressDetail.setText("收货地址: " + province + city + district + address);
                addressID = data.getIntExtra("id", -1);

                if (isNowBuy) {
                    nowBuys(longitudeResult, latitudeResult, province, city, district);
                } else {
                    cartBuys(longitudeResult, latitudeResult, province, city, district);
                }
            }
        }

        //发票信息回传
        if (resultCode == Const.INTEGER_456) {
            if (requestCode == INVOICE_CODE) {
                mInvoiceType = data.getIntExtra("invoiceType", -1);
                MyLog.e("zzz", "invoiceType===" + mInvoiceType);
                if (mInvoiceType == -1) {
                    merchantList.get(mCurrentPosition).setFapiao(" ");
                } else if (mInvoiceType == Const.INTEGER_0) {
                    merchantList.get(mCurrentPosition).setFapiao("普通(商品明细-个人)");

                    mInvoiceName = data.getStringExtra("invoiceName");
                    String invoiceHead = data.getStringExtra("invoiceHead");
                    String invoiceCode = data.getStringExtra("invoiceCode");
                    MyLog.e("adapter0===", mInvoiceName + "," + invoiceHead + "," + invoiceCode);

                    merchantList.get(mCurrentPosition).setInvoiceType(String.valueOf(mInvoiceType));
                    merchantList.get(mCurrentPosition).setInvoiceName(mInvoiceName);
                } else if (mInvoiceType == Const.INTEGER_1) {
                    merchantList.get(mCurrentPosition).setFapiao("普通(商品明细-单位)");

                    String invoiceName = data.getStringExtra("invoiceName");
                    mInvoiceHead = data.getStringExtra("invoiceHead");
                    mInvoiceCode = data.getStringExtra("invoiceCode");
                    MyLog.e("adapter1===", invoiceName + "," + mInvoiceHead + "," + mInvoiceCode);

                    merchantList.get(mCurrentPosition).setInvoiceType(String.valueOf(mInvoiceType));
                    merchantList.get(mCurrentPosition).setInvoiceName(mInvoiceHead);
                    merchantList.get(mCurrentPosition).setInvoiceMark(mInvoiceCode);
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 立即购买流程，更新收货地址
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @version 4.0.0
     */
    private void nowBuys(String longitude, String latitude, String province, String city, String district) {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "timestamp", timestamp,
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "pid", productId,
                "sid", sid,
                "num", num,
                "authorid", authorid,
                "hid", TextUtils.isEmpty(hunterId) ? "" : hunterId,
                "longitude", TextUtils.isEmpty(longitude) ? "" : longitude,
                "latitude", TextUtils.isEmpty(latitude) ? "" : latitude,
                "provinceName", TextUtils.isEmpty(province) ? "" : province,
                "cityName", TextUtils.isEmpty(city) ? "" : city,
                "countyName", TextUtils.isEmpty(district) ? "" : district);
        OkHttpUtils.post().url(Constants.NOW_BUY)
                .addParams("timestamp", timestamp)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("pid", productId)
                .addParams("sid", sid)
                .addParams("num", num)
                .addParams("authorid", authorid)
                .addParams("hid", TextUtils.isEmpty(hunterId) ? "" : hunterId)
                .addParams("longitude", TextUtils.isEmpty(longitude) ? "" : longitude)
                .addParams("latitude", TextUtils.isEmpty(latitude) ? "" : latitude)
                .addParams("provinceName", TextUtils.isEmpty(province) ? "" : province)
                .addParams("cityName", TextUtils.isEmpty(city) ? "" : city)
                .addParams("countyName", TextUtils.isEmpty(district) ? "" : district)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractSureOderCallBack() {
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
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.str_net_error_message));
                MyLog.e("zzz_nowBuys", e.toString());
            }

            @Override
            public void onResponse(OrderBean response, int id) {
                MyLog.e("zzz_nowBuys", "response_nowBuys");
                if (response.getCode() == Const.INTEGER_1) {
                    if (response.getContent() != null) {
                        setListData(response.getContent());
                        updateProductNum(response.getContent());
                    }
                } else {
                    ToastUtils.show(response.getMsg());
                }
            }
        });
    }

    /**
     * 购物车流程，更新收货地址
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @version 4.0.0
     */
    private void cartBuys(String longitude, String latitude, String province, String city, String district) {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "timestamp", timestamp,
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "longitude", TextUtils.isEmpty(longitude) ? "" : longitude,
                "latitude", TextUtils.isEmpty(latitude) ? "" : latitude,
                "provinceName", TextUtils.isEmpty(province) ? "" : province,
                "cityName", TextUtils.isEmpty(city) ? "" : city,
                "countyName", TextUtils.isEmpty(district) ? "" : district,
                "cids", cIds);
        OkHttpUtils.post().url(Constants.TO_PAY)
                .addParams("timestamp", timestamp)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("longitude", TextUtils.isEmpty(longitude) ? "" : longitude)
                .addParams("latitude", TextUtils.isEmpty(latitude) ? "" : latitude)
                .addParams("provinceName", TextUtils.isEmpty(province) ? "" : province)
                .addParams("cityName", TextUtils.isEmpty(city) ? "" : city)
                .addParams("countyName", TextUtils.isEmpty(district) ? "" : district)
                .addParams("cids", cIds)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractSureOderCallBack() {
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
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.str_net_error_message));
                MyLog.e("zzz_cartBuys", e.toString());
            }

            @Override
            public void onResponse(OrderBean response, int id) {
                MyLog.e("zzz_cartBuys", "response_cartBuys");
                if (response.getCode() == Const.INTEGER_1) {
                    if (response.getContent() != null) {
                        setListData(response.getContent());
                        updateProductNum(response.getContent());
                    }
                } else {
                    ToastUtils.show(response.getMsg());
                }
            }
        });
    }
}