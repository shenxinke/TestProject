package com.yst.onecity.activity.member;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.adapter.AddressManageAdapter;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.AddressEntity;
import com.yst.onecity.bean.AddressListEntity;
import com.yst.onecity.bean.CodeMsgBean;
import com.yst.onecity.callbacks.AbstractCodeMsgCallback;
import com.yst.onecity.callbacks.AbstractReceiverAddressListCallBack;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.dialog.AbstractDeleteBankCardDialog;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.xlistview.XListView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 会员管理地址页面
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class AddressManageActivity extends BaseActivity implements XListView.IXListViewListener {

    @BindView(R.id.ll_back_address)
    LinearLayout llBackAddress;
    @BindView(R.id.manage_listview)
    XListView listview;
    @BindView(R.id.ll_add_address)
    LinearLayout llAddAddress;
    @BindView(R.id.activity_address_manage)
    LinearLayout activityAddressManage;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    private List<AddressEntity> list = new ArrayList<>();
    private AddressManageAdapter addressManageAdapter;
    private int page = 1;
    private int flag = 0;
    private AbstractDeleteBankCardDialog deleteBankCardDialog;

    @Override
    public int bindLayout() {
        return R.layout.activity_address_manage;
    }

    @Override
    public void initData() {
        listview.setPullRefreshEnable(false);
        listview.setPullLoadEnable(false);
        listview.setXListViewListener(this);
        flag = getIntent().getIntExtra("ADDRESSCODE", 0);
        if (flag == Const.INTEGER_1000) {
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        return;
                    }
                    Intent intent = new Intent();
                    intent.putExtra("name", list.get(position - 1).getUserName());
                    intent.putExtra("phone", list.get(position - 1).getMobile());
                    intent.putExtra("province", list.get(position - 1).getPname());
                    intent.putExtra("city", list.get(position - 1).getCname());
                    intent.putExtra("district", list.get(position - 1).getDname());
                    intent.putExtra("address", list.get(position - 1).getDetailAddress());
                    intent.putExtra("id", Integer.parseInt(list.get(position - 1).getId()));
                    intent.putExtra("longitudeResult", list.get(position - 1).getLongitude());
                    intent.putExtra("latitudeResult", list.get(position - 1).getLatitude());
                    setResult(100, intent);
                    finish();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        page = 1;
        requestAddress();
    }

    @OnClick({R.id.ll_add_address, R.id.ll_back_address})
    public void onViewClicked(View view) {
        if (!ConstUtils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            //新增收货地址
            case R.id.ll_add_address:
                Bundle bundle = new Bundle();
                bundle.putString("flag", "add");
                JumpIntent.jump(this, AddAddressActivity.class, bundle);
                break;
            case R.id.ll_back_address:
                if (flag == Const.INTEGER_1000) {
                    Intent intent = new Intent();
                    setResult(101, intent);
                    finish();
                } else {
                    finish();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 请求管理地址
     */
    private void requestAddress() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.ADDRESS_LIST)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractReceiverAddressListCallBack() {
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
                MyLog.e("RecieveAddress", "管理收货地址列表onError______" + e);
            }

            @Override
            public void onResponse(AddressListEntity addressListEntity, int id) {
                if (addressListEntity.getCode() == 1 && addressListEntity.getContent() != null) {
                    if (page == 1) {
                        list = addressListEntity.getContent();
                    } else {
                        list.addAll(addressListEntity.getContent());
                    }
                } else {
                    if (page == 1) {
                        list = new ArrayList<>();
                    } else {
                        ToastUtils.show(TianyiApplication.instance.getResources().getString(R.string.xlist_add_all_msg));
                    }
                }
                flushAddressData();
            }
        });
    }

    private void flushAddressData() {
        if (null == listview) {
            return;
        }
        if (list.size() == 0) {
            llEmpty.setVisibility(View.VISIBLE);
        } else {
            llEmpty.setVisibility(View.GONE);
        }
        if (addressManageAdapter == null) {
            addressManageAdapter = new AddressManageAdapter(list, this);
            listview.setAdapter(addressManageAdapter);
            addressManageAdapter.setIScrollPositon(new AddressManageAdapter.IGetScrollPosition() {
                @Override
                public void delete(final int position) {
                    deleteBankCardDialog = new AbstractDeleteBankCardDialog(AddressManageActivity.this) {
                        @Override
                        public void onSureClick() {
                            requestDeleteAddress(list.get(position).getId());
                        }
                    };
                    deleteBankCardDialog.setCancleStyle(R.drawable.shape_white_10, R.color.black, "取消");
                    deleteBankCardDialog.setSureStyle(R.drawable.shape_gray_180dp_bg, R.color.black, "确认");
                    deleteBankCardDialog.showDialog();

                }
            });
        } else {
            addressManageAdapter.setData(list);
        }
    }

    /**
     * 删除管理地址
     */
    private void requestDeleteAddress(String addressId) {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "id", addressId,
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.DELETE_ADDRESS)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("id", addressId)
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractCodeMsgCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show("网络访问失败，请重试");
            }

            @Override
            public void onResponse(CodeMsgBean response, int id) {
                MyLog.e("delete", "onresponse__" + response);
                ToastUtils.show(response.getMsg());
                if (response.getCode() == 1) {
                    requestAddress();
                }
            }

        });
    }

    @Override
    public void onRefresh() {
    }

    @Override
    public void onLoadMore() {
    }

}
