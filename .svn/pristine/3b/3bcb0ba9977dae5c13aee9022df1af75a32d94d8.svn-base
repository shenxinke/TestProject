package com.yst.onecity.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.NewDetailActivity;
import com.yst.onecity.activity.member.TrademarkActivity;
import com.yst.onecity.adapter.TrademarkShareAdapter;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.bean.TrademarkShareBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 商城分享页面
 *
 * @author Shenxinke
 * @version 4.2.0
 * @data 2018/6/5
 */

public class TrademarkShareFragment extends BaseFragment {
    private TrademarkShareAdapter trademarkShareAdapter;
    private List<TrademarkShareBean.ContentBean.ConsultationInfoListBean> consultationInfoList = new ArrayList<>();
    private int pageNum = 1;
    private int rows = 2;

    @BindView(R.id.lv_information)
    ListView rvInformation;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private String brandId;

    @Override
    public int bindLayout() {
        return R.layout.fragment_infomation_content;
    }


    @Override
    public void initData() {
        brandId = TrademarkActivity.getBrandId();
        getBrandShareList();
        initListener();
    }

    /**
     * 获取品牌分享信息
     */
    public void getBrandShareList() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "memberid", TianyiApplication.appCommonBean.getId(),
                "brandId", brandId,
                "page", String.valueOf(pageNum),
                "rows", String.valueOf(rows),
                "type", String.valueOf(Const.STR1),
                "timestamp", timestamp);
        if (TextUtils.isEmpty(sign)) {
            return;
        }
        OkHttpUtils.post()
                .url(Constants.GET_BRAND_LIST)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("memberid", TianyiApplication.appCommonBean.getId())
                .addParams("brandId", brandId)
                .addParams("rows", String.valueOf(rows))
                .addParams("page", String.valueOf(pageNum))
                .addParams("type", Const.STR1)
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
                MyLog.e("sss", "-品牌分享Fragment: " + response);
                if (response != null) {
                    TrademarkShareBean trademarkBean = new Gson().fromJson(response, TrademarkShareBean.class);
                    if (trademarkBean.getCode() == Const.INTEGER_1) {
                        consultationInfoList.addAll(trademarkBean.getContent().getConsultationInfoList());
                        setAdapter();
                    } else {
                        ToastUtils.show(getString(R.string.tv_net_error));
                    }
                } else {
                    ToastUtils.show(getString(R.string.tv_net_error));
                }
            }
        });
    }

    /**
     * 上拉加载下拉刷新
     */

    private void initListener() {
        rvInformation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(consultationInfoList.get(i).getId()));
                JumpIntent.jump(getActivity(), NewDetailActivity.class, bundle);
            }
        });

        smartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageNum++;
                getBrandShareList();
                smartRefreshLayout.finishLoadmore(1000);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNum = 1;
                if (consultationInfoList != null) {
                    consultationInfoList.clear();
                }
                getBrandShareList();
                smartRefreshLayout.finishRefresh(500);
            }
        });
    }

    /**
     * 绑定适配器
     */
    private void setAdapter() {
        if (trademarkShareAdapter == null) {
            trademarkShareAdapter = new TrademarkShareAdapter(getActivity());
            rvInformation.setAdapter(trademarkShareAdapter);
            trademarkShareAdapter.setData(consultationInfoList);
        } else {
            trademarkShareAdapter.setData(consultationInfoList);
        }
    }
}
