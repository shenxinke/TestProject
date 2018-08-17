package com.yst.onecity.activity.member;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.google.gson.Gson;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.addressmanager.ShopAddressActivity;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.AreaBean;
import com.yst.onecity.bean.CodeMsgBean;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.callbacks.AbstractCodeMsgCallback;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.dialog.AbstractDeleteDialog;
import com.yst.onecity.fragment.HomeFragment;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.ContainsEmojiEditText;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

import static com.yst.onecity.utils.ToastUtils.show;

/**
 * 会员新增收获地址页面
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class AddAddressActivity extends BaseActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.et_name)
    ContainsEmojiEditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_detail_address)
    ContainsEmojiEditText etDetailAddress;
    @BindView(R.id.cb_more)
    CheckBox cbMore;
    @BindView(R.id.activity_add_address)
    LinearLayout activityAddAddress;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_location)
    LinearLayout llLocation;
    @BindView(R.id.tv_locationed_address)
    TextView tvLocationedAddress;
    private AbstractDeleteDialog deleteDialog;
    private String edit = "";
    private String pId, cId, aId, mAddress;
    private String streetName;
    private String name, phone, detailAddress, isDefault, addressId;
    /**
     * 选择地址请求码
     */
    public static final int REQUESTCODE_HOME_ADRESS = 12;
    public static String district;
    private String cityName;
    private String longitude;
    private String latitude;
    private String position;
    private String dName;
    private String longituteHuix;
    private String lantituteuixian;
    private String title;
    private String addFlag = "add";

    @Override
    public int bindLayout() {
        return R.layout.activity_add_address;
    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
        etName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        etDetailAddress.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        deleteDialog = new AbstractDeleteDialog(this) {
            @Override
            public void onSureClick() {
                finish();
            }
        };
        if (getIntent().getExtras() != null) {
            edit = getIntent().getExtras().getString("edit");
            String flag = getIntent().getExtras().getString("flag");
            if (addFlag.equals(flag)) {
                tvMainTitle.setText("新增收货地址");
                MyLog.e("sss","--tvAddress:"+tvAddress.getText().toString());
                return;
            } else {
                tvMainTitle.setText("编辑收货地址");
            }
            name = getIntent().getExtras().getString("name", "");
            phone = getIntent().getExtras().getString("phone", "");
            detailAddress = getIntent().getExtras().getString("detailAddress", "");
            isDefault = getIntent().getExtras().getString("isdefault", "");
            pId = getIntent().getExtras().getString("provinceid", "");
            cId = getIntent().getExtras().getString("cityid", "");
            aId = getIntent().getExtras().getString("areaid", "");
            mAddress = getIntent().getExtras().getString("pname", "") + "-" + getIntent().getExtras().getString("cname", "") + "-" + getIntent().getExtras().getString("dname", "");
            addressId = getIntent().getExtras().getString("addressId", "");
            position = getIntent().getExtras().getString("position", "");
            dName = getIntent().getExtras().getString("dname", "");
            MyLog.e("mTag", "position:" + position);
            longituteHuix = getIntent().getExtras().getString("longitute", "");
            lantituteuixian = getIntent().getExtras().getString("lantitute", "");
            if (Const.STR_EDIT.equals(edit)) {
                setAddressInfo(name, phone, detailAddress, isDefault, mAddress, position);
                tvRight.setText(getResources().getString(R.string.chat_save));
                tvMainTitle.setText(getResources().getString(R.string.edit_address));
            }else{
                tvMainTitle.setText(getResources().getString(R.string.add_address));
            }
        }
    }

    /**
     * 地址信息回显
     *
     * @param name          姓名
     * @param phone         手机号
     * @param detailAddress 详细地址
     * @param isDefault     是否默认
     */
    private void setAddressInfo(String name, String phone, String detailAddress, String isDefault, String address, String poition) {
        etName.setText(name);
        etName.setSelection(etName.getText().toString().length());
        etPhone.setText(phone);
        MyLog.e("sss","-address1:"+address);
        tvAddress.setText(address);
        etDetailAddress.setText(detailAddress);
        if (poition != null) {
            tvLocationedAddress.setText(poition);
        }else{
            tvLocationedAddress.setText(dName);
        }
        if (Const.STR1.equals(isDefault)) {
            cbMore.setChecked(true);
        }
    }

    @OnClick({R.id.tv_address, R.id.ll_back, R.id.tv_right, R.id.ll_location})
    public void onViewClicked(View view) {
        if (!ConstUtils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            //保存（编辑）
            case R.id.tv_right:
                if (!"".equals(edit) && Const.STR_EDIT.equals(edit)) {
                    //编辑
                    goEditAddress();
                } else {
                    //保存
                    goAddAddress();
                }
                break;
            //选择地址
            case R.id.tv_address:
                JumpIntent.jump(this, ProvinceActivity.class);
                break;
            //取消
            case R.id.ll_back:
                deleteDialog.setText("还未保存，是否放弃编辑，确认返回？");
                deleteDialog.showDialog();
                break;
            case R.id.ll_location:
                MyLog.e("mTag", "ll_location " + mAddress);

                if (TextUtils.isEmpty(mAddress)) {
                    ShopAddressActivity.locationHomeAddress(AddAddressActivity.this, new LatLng(HomeFragment.locationLat, HomeFragment.locationLon), HomeFragment.street, HomeFragment.district, REQUESTCODE_HOME_ADRESS);
                } else {
                    ShopAddressActivity.locationHomeAddress(AddAddressActivity.this, new LatLng(HomeFragment.locationLat, HomeFragment.locationLon), mAddress, HomeFragment.district, REQUESTCODE_HOME_ADRESS);
                }

                break;
            default:
                break;
        }
    }

    /**
     * 新增保存收货地址
     */
    private void goAddAddress() {
        String name = etName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            show("请输入收货人姓名");
            return;
        }
        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            show("请输入联系电话");
            return;
        }
        if (!ConstUtils.isMobileNO(phone)) {
            show("请输入正确的手机号");
            return;
        }
        String address = tvAddress.getText().toString().trim();
        if (TextUtils.isEmpty(address) || address.length() < Integer.parseInt(Const.STR4)) {
            show("请选择收货地址");
            return;
        }
        String detailAddress = etDetailAddress.getText().toString().trim();
        if (TextUtils.isEmpty(detailAddress)) {
            show("请输入街道及详细地址");
            return;
        }
        String locationedAddress = tvLocationedAddress.getText().toString();
        if (TextUtils.isEmpty(locationedAddress) || TextUtils.isEmpty(longitude) || TextUtils.isEmpty(latitude)) {
            show("请定位");
            return;
        }

        if (cbMore.isChecked()) {
            isDefault = "1";
        } else {
            isDefault = "0";
        }
        String timestamp = SignUtils.getTimeStamp();
        Map<String, String> map = new HashMap(16);
        map.put("uuid", TianyiApplication.appCommonBean.getUuid());
        map.put("phone", TianyiApplication.appCommonBean.getPhone());
        map.put("timestamp", timestamp);
        map.put("client_type", "A");
        map.put("userName", name);
        map.put("mobile", phone);
        map.put("pId", pId);
        map.put("cId", cId);
        map.put("position", locationedAddress);
        map.put("isDefault", isDefault);
        map.put("longitude", longitude);
        map.put("latitude", latitude);
        map.put("detailAddress", detailAddress);
        if (!TextUtils.isEmpty(aId)) {
            map.put("dId", aId);
        }
        String sign = SignUtils.mapToSign(map);

        if (TextUtils.isEmpty(sign)) {
            return;
        }
        OkHttpUtils.post().url(Constants.ADD_RECEIVE_ADDRESS)
                .params(map)
                .addParams("sign", sign)
                .build().execute(new AbstractCodeMsgCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                showInfoProgressDialog();
            }

            @Override
            public void onAfter(int id) {
                dismissInfoProgressDialog();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.app_on_request_error_msg));
                MyLog.e("AddAddress", "保存地址onerror____" + e);
            }

            @Override
            public void onResponse(CodeMsgBean deleteAddressEntity, int id) {
                MyLog.e("AddAddress", "保存地址onResponse____" + deleteAddressEntity.getMsg());
                if (deleteAddressEntity.getCode() == 1) {
                    finish();
                }
                ToastUtils.show(deleteAddressEntity.getMsg());
            }
        });
    }

    /**
     * 编辑修改收货地址
     */
    private void goEditAddress() {
        String name = etName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            show("请输入收货人姓名");
            return;
        }
        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            show("请输入联系电话");
            return;
        }
        if (!ConstUtils.isMobileNO(phone)) {
            show("请输入正确的手机号");
            return;
        }
        String address = tvAddress.getText().toString().trim();
        if (TextUtils.isEmpty(address) || address.length() < Integer.parseInt(Const.STR4)) {
            show("请选择收货地址");
            return;
        }
        String detailAddress = etDetailAddress.getText().toString().trim();
        if (TextUtils.isEmpty(detailAddress)) {
            show("请输入街道及详细地址");
            return;
        }

        String locationedAddress = tvLocationedAddress.getText().toString();
        if (TextUtils.isEmpty(locationedAddress) || TextUtils.isEmpty(longituteHuix) || TextUtils.isEmpty(lantituteuixian)) {
            show("请定位");
            return;
        }

        if (cbMore.isChecked()) {
            isDefault = "1";
        } else {
            isDefault = "0";
        }
        String timestamp = SignUtils.getTimeStamp();
        Map<String, String> map = new HashMap(16);
        map.put("uuid", TianyiApplication.appCommonBean.getUuid());
        map.put("phone", TianyiApplication.appCommonBean.getPhone());
        map.put("timestamp", timestamp);
        map.put("client_type", "A");
        map.put("userName", name);
        map.put("mobile", phone);
        map.put("pId", pId);
        map.put("cId", cId);
        map.put("position", locationedAddress);
        map.put("isDefault", isDefault);
        map.put("detailAddress", detailAddress);
        map.put("id", addressId);
        map.put("longitude", longituteHuix);
        map.put("latitude", lantituteuixian);
        if (aId != null && aId != "") {
            map.put("dId", aId);
        }
        String sign = SignUtils.mapToSign(map);

        if (TextUtils.isEmpty(sign)) {
            return;
        }

        OkHttpUtils.post().url(Constants.UPDATE_RECEIVE_ADDRESS)
                .params(map)
                .addParams("sign", sign)
                .build().execute(new AbstractCodeMsgCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                showInfoProgressDialog();
            }

            @Override
            public void onAfter(int id) {
                dismissInfoProgressDialog();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.app_on_request_error_msg));
                MyLog.e("AddAddress", "编辑地址onerror____" + e);
            }

            @Override
            public void onResponse(CodeMsgBean deleteAddressEntity, int id) {
                MyLog.e("AddAddress", "编辑地址onResponse____" + deleteAddressEntity.getMsg());
                if (deleteAddressEntity.getCode() == 1) {
                    finish();
                }
                ToastUtils.show(deleteAddressEntity.getMsg());
            }
        });
    }

    @Subscribe
    public void onEventMainThread(EventBean event) {
        if (Const.STR_FINISH.equals(event.getMsg())) {
            aId = "";
            String addressStr = event.getAdress();
            pId = event.getPID();
            MyLog.e("mTag", "pid:" + pId);
            cId = event.getCID();
            if (!TextUtils.isEmpty(event.getAID())) {
                aId = event.getAID();
            }
            MyLog.e("sss","-address2:"+addressStr);
            tvAddress.setText(addressStr);
            mAddress = addressStr;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUESTCODE_HOME_ADRESS:
                if (resultCode == RESULT_OK) {
                    district = data.getStringExtra("district");
                    cityName = data.getStringExtra("cityName");
                    streetName = data.getStringExtra("street");
                    title = data.getStringExtra("title");
                    longitude = data.getStringExtra("longitude");
                    latitude = data.getStringExtra("latitude");

                    longituteHuix = longitude;
                    lantituteuixian = latitude;

                    MyLog.e("sss", "-longitude:" + longitude + "-latitude:" + latitude);
                    tvLocationedAddress.setText(title);
                    tvLocationedAddress.setTextColor(ContextCompat.getColor(this, R.color.tv_checked));
                    MyLog.e("mTag", "current_district" + district + "  streetName -" + streetName);
                    packageLocationParams();
                    requestArea();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 二次修改市区参数值
     */
    private void packageLocationParams() {
        if (!TextUtils.isEmpty(cityName) && !TextUtils.isEmpty(district)) {
            if (cityName.length() > Const.INTEGER_4) {
                cityName = cityName.substring(0, 2);
            } else {
                if (cityName.contains(Const.CONS_STR_SHI)) {
                    cityName = cityName.substring(0, cityName.length() - 1);
                }
            }

            if (district.length() > Const.INTEGER_4) {
                district = district.substring(0, 2);
            } else {
                if (district.contains(Const.CONS_STR_QU) || district.contains(Const.CONS_STR_XIAN)) {
                    district = district.substring(0, district.length() - 1);
                }
            }
        }
    }

    /**
     * 根据定位地址获取省市区
     */
    private void requestArea() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "countyName", district,
                "cityName", cityName,
                "timestamp", timestamp);
        if (TextUtils.isEmpty(sign)) {
            return;
        }

        OkHttpUtils.post().url(Constants.GET_AREA_BY_LOCATION)
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("countyName", district)
                .addParams("cityName", cityName)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("mTag", "-获取所在地区：" + response);
                if (response != null) {
                    AreaBean areaBean = new Gson().fromJson(response, AreaBean.class);
                    AreaBean.ContentBean content = areaBean.getContent();
                    if (areaBean != null && content != null) {
                        String provinceName = areaBean.getContent().getProvinceName();
                        String cityName = areaBean.getContent().getCityName();
                        String countyName = areaBean.getContent().getCountyName();
                        pId = String.valueOf(areaBean.getContent().getProId());
                        cId = String.valueOf(areaBean.getContent().getCityId());
                        aId = String.valueOf(areaBean.getContent().getDistrictId());
                        MyLog.e("sss","-address3:"+provinceName + "-" + cityName + "-" + countyName);
                        tvAddress.setText(provinceName + "-" + cityName + "-" + countyName);
                        mAddress = provinceName + "-" + cityName + "-" + countyName + "-" + streetName;
                    } else {
                        tvAddress.setText(ConstUtils.getStringNoEmpty(areaBean.getMsg()));
                    }
                } else {
                    ToastUtils.show(getString(R.string.tv_check_service));
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            deleteDialog.setText("还未保存，是否放弃编辑，确认返回？");
            deleteDialog.showDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
