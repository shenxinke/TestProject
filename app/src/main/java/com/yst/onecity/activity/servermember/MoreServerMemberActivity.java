package com.yst.onecity.activity.servermember;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.im.Constant;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.commissioner.CommissionerHomePageActivity;
import com.yst.onecity.activity.member.SearchServiceSpecialistActivity;
import com.yst.onecity.activity.zxing.activity.CaptureActivity;
import com.yst.onecity.adapter.MoreServerMemberAdapter;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.MoreServerMemberBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.RxCode;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.ServerAddressPopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

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
 * @author Shenxinke
 * @version 4.2.0
 * @data 2018/6/1
 * <p>
 * <p>
 * 更多服务专员页面
 */

public class MoreServerMemberActivity extends BaseActivity {
    private MoreServerMemberAdapter moreServerMemberAdapter;
    private List<MoreServerMemberBean.ContentBean> list = new ArrayList<>();
    /**
     * 1 降序 0升序
     */
    private int applause = 0;
    private int pageNum = 1;
    private int rows = 10;
    /**
     * 1获取所有专员信息
     * 2按照好评率排序
     * 3服务范围搜索专员
     */
    private int getType = 1;
    /**
     * 0未刷新 1刷新
     */
    private int isRefresh = 0;

    @BindView(R.id.more_server_list)
    ListView lvServer;
    @BindView(R.id.server_scope)
    LinearLayout llScope;
    @BindView(R.id.more_server_arrows)
    ImageView mIvArrow;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private String did;

    @Override
    public int bindLayout() {
        return R.layout.activity_more_server_member;
    }

    @Override
    public void initData() {
        RxBus.get().register(this);
        initListener();
        getSearchSpecia();
    }

    /**
     * 获取地区ID
     *
     * @param eventBean
     */
    @Subscribe(code = RxCode.CODE_PUBLISH_SERVER_ADDRESS, threadMode = ThreadMode.MAIN)
    public void getAddressId(EventBean eventBean) {
        did = eventBean.getCID();
        getType = Const.INTEGER_3;
        pageNum = Const.INTEGER_1;
        list.clear();
        getSearchSpecia();
    }

    /**
     * 商城页面的监听事件
     */
    @OnClick({R.id.activity_back_iv, R.id.server_scope, R.id.server_scan_code, R.id.searchBarLayout, R.id.favorable_rate})
    public void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.activity_back_iv:
                finish();
                break;
            case R.id.server_scope:
                showAddressPop();
                break;
            case R.id.searchBarLayout:
                JumpIntent.jump(this, SearchServiceSpecialistActivity.class);
                break;
            case R.id.server_scan_code:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, Constant.OPENIM_TYPE);
                    return;
                }
                Intent intent = new Intent(this, CaptureActivity.class);
                startActivityForResult(intent, Const.REQ_QR_CODE);
                break;
            case R.id.favorable_rate:
                if (Utils.isClickable()) {
                    getType = Const.INTEGER_2;
                    pageNum = Const.INTEGER_1;
                    list.clear();
                    getSearchSpecia();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 显示省市区选择器弹窗
     */
    private void showAddressPop() {
        ServerAddressPopupWindow pop = new ServerAddressPopupWindow(this);
        pop.showPop(llScope, mIvArrow);
        pop.setOnDismissListener(() -> mIvArrow.setImageResource(R.mipmap.xia));
    }

    /**
     * 扫描二维码
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.REQ_QR_CODE && resultCode == RESULT_OK) {
            Bundle bundles = data.getExtras();
            String scanResult = bundles.getString(Const.INTENT_EXTRA_KEY_QR_SCAN);
            MyLog.e("aaa", "scanResult___" + scanResult);
            String hid = Uri.parse(scanResult).getQueryParameter("hid");
            String hunterId = Uri.parse(scanResult).getQueryParameter("hunterId");
            MyLog.e("aaa", "hid___" + hid);
            if (!TextUtils.isEmpty(hid)) {
                Bundle bundle = new Bundle();
                bundle.putString("hid", hid);
                bundle.putString("from", Const.STR_FROM_NOLOGIN);
                JumpIntent.jump(MoreServerMemberActivity.this, CommissionerHomePageActivity.class, bundle);
            } else if(!TextUtils.isEmpty(hunterId)){
                Bundle bundle = new Bundle();
                bundle.putString("hid", hunterId);
                bundle.putString("from", Const.STR_FROM_NOLOGIN);
                JumpIntent.jump(MoreServerMemberActivity.this, CommissionerHomePageActivity.class, bundle);
            } else {
                ToastUtils.show("扫码失败");
            }
        }
    }

    /**
     * 上拉加载下拉刷新
     */
    private void initListener() {

        lvServer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("hid", String.valueOf(list.get(i).getHid()));
                bundle.putString("from", Const.STR_FROM_LOGINED);
                JumpIntent.jump(MoreServerMemberActivity.this, CommissionerHomePageActivity.class, bundle);
            }
        });

        smartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageNum++;
                isRefresh = Const.INTEGER_1;
                getSearchSpecia();
                smartRefreshLayout.finishLoadmore(1000);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNum = 1;
                if (list != null) {
                    list.clear();
                }
                isRefresh = Const.INTEGER_1;
                getSearchSpecia();
                smartRefreshLayout.finishRefresh(500);
            }
        });
    }

    /**
     * 获取数据
     */
    private void getSearchSpecia() {
        String timestamp = SignUtils.getTimeStamp();
        String[] strings;
        String sign;
        PostFormBuilder post = OkHttpUtils.post();

        if (getType == Const.INTEGER_1) {
            strings = new String[]{"uuid", TianyiApplication.appCommonBean.getUuid(),
                    "phone", TianyiApplication.appCommonBean.getPhone(), "page", String.valueOf(pageNum),
                    "rows", String.valueOf(rows), "timestamp", timestamp};
            sign = Utils.getSign(strings);
            if (TextUtils.isEmpty(sign)) {
                return;
            }
            post.addParams("sign", sign);
        } else if (getType == Const.INTEGER_2) {
            if (applause == Const.INTEGER_1 && isRefresh == Const.INTEGER_1) {
                applause = Const.INTEGER_1;
                isRefresh = Const.INTEGER_0;
            } else if (applause == Const.INTEGER_0 && isRefresh == Const.INTEGER_1) {
                applause = Const.INTEGER_0;
                isRefresh = Const.INTEGER_0;
            } else if (applause == Const.INTEGER_1) {
                applause = Const.INTEGER_0;
            } else if (applause == Const.INTEGER_0) {
                applause = Const.INTEGER_1;
            }
            MyLog.e("aaa", "applause____" + applause);
            MyLog.e("aaa", "isRefresh2____" + isRefresh);
            strings = new String[]{"applause", String.valueOf(applause), "uuid", TianyiApplication.appCommonBean.getUuid(),
                    "phone", TianyiApplication.appCommonBean.getPhone(), "page", String.valueOf(pageNum),
                    "rows", String.valueOf(rows), "timestamp", timestamp};
            sign = Utils.getSign(strings);
            if (TextUtils.isEmpty(sign)) {
                return;
            }
            post.addParams("applause", String.valueOf(applause));
            post.addParams("sign", sign);
        } else if (getType == Const.INTEGER_3) {
            strings = new String[]{"did", did, "uuid", TianyiApplication.appCommonBean.getUuid(),
                    "phone", TianyiApplication.appCommonBean.getPhone(), "page", String.valueOf(pageNum),
                    "rows", String.valueOf(rows), "timestamp", timestamp};
            sign = Utils.getSign(strings);
            if (TextUtils.isEmpty(sign)) {
                return;
            }
            post.addParams("did", did);
            post.addParams("sign", sign);
        }
        post.url(Constants.SEARCH_COMMISSIONER_RECOMMENDED);
        post.addParams("uuid", TianyiApplication.appCommonBean.getUuid());
        post.addParams("phone", TianyiApplication.appCommonBean.getPhone());
        post.addParams("page", String.valueOf(pageNum));
        post.addParams("rows", String.valueOf(rows));
        post.addParams("client_type", "A");
        post.addParams("timestamp", timestamp);
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
                MyLog.e("sss", "-更多专员: " + response);
                if (response != null) {
                    MoreServerMemberBean moreServerMemberBean = new Gson().fromJson(response, MoreServerMemberBean.class);
                    if (moreServerMemberBean.getCode() == Const.INTEGER_1) {
                        if (moreServerMemberBean.getContent() != null) {
                            list.addAll(moreServerMemberBean.getContent());
                            setAdapter();
                        }
                    }
                } else {
                    ToastUtils.show(getString(R.string.tv_check_service));
                }
            }
        });
    }

    /**
     * 绑定适配器
     */
    private void setAdapter() {
        if (moreServerMemberAdapter == null) {
            moreServerMemberAdapter = new MoreServerMemberAdapter(MoreServerMemberActivity.this, list);
            lvServer.setAdapter(moreServerMemberAdapter);
        } else {
            moreServerMemberAdapter.setData(list);
        }
    }


    @Override
    protected void onDestroy() {
        RxBus.get().unRegister(this);
        super.onDestroy();
    }
}
