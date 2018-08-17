package com.yst.onecity.activity.tickets;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.adapter.AbstractCommonAdapter;
import com.yst.onecity.adapter.CommonViewHolder;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.tickets.TicketsDetaiBean;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 我的奖券预览详情页面
 *
 * @author WangJingWei
 * @version 4.0.0
 * @date 2018/3/29.
 */
public class TkPreviewDetailActivity extends BaseActivity {

    @BindView(R.id.activity_title_tv)
    TextView activityTitleTv;
    @BindView(R.id.activity_back_iv)
    ImageView imgBack;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.mSmartRefresh)
    SmartRefreshLayout mSmartRefresh;
    @BindView(R.id.img_empty)
    ImageView imgEmpty;

    private String awards = "";
    private String name = "";
    private int page = 1;
    private int rows = 15;
    private List<TicketsDetaiBean.ContentBean> mInfoList = new ArrayList<>();
    private AbstractCommonAdapter<TicketsDetaiBean.ContentBean> mCommonAdapter;

    @Override
    public int bindLayout() {
        return R.layout.activity_tickets_preview_detail;
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            awards = bundle.getString("awards");
            name = bundle.getString("name");
        }
        activityTitleTv.setText(name);

        initListener();
        initTicketsData();
        requestTicketsList();

    }

    @OnClick(R.id.activity_back_iv)
    public void toBack() {
        finish();
    }

    /**
     * 实例化监听事件
     */
    private void initListener() {
        mSmartRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                requestTicketsList();
                mSmartRefresh.finishLoadmore(500);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mInfoList.clear();
                page = 1;
                requestTicketsList();
                mSmartRefresh.finishRefresh(500);
            }
        });
    }

    /**
     * 初始化化数据源适配
     */
    private void initTicketsData() {
        mCommonAdapter = new AbstractCommonAdapter<TicketsDetaiBean.ContentBean>(TianyiApplication.instance, mInfoList, R.layout.item_tickets_info_list) {
            @Override
            public void convert(CommonViewHolder holder, int position, TicketsDetaiBean.ContentBean item) {
                holder.setText(R.id.tv_name, ConstUtils.getStringNoEmpty(item.getNickname()));
                holder.setText(R.id.tv_phone, ConstUtils.settingPhone(ConstUtils.getStringNoEmpty(item.getPhone())));
            }
        };

        listView.setAdapter(mCommonAdapter);
    }

    /**
     * 获取奖券信息列表
     */
    private void requestTicketsList() {

        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "rows", String.valueOf(rows),
                "awards", awards,
                "timestamp", timestamp,
                "page", String.valueOf(page));

        if (TextUtils.isEmpty(sign)) {
            return;
        }

        OkHttpUtils
                .post()
                .url(Constants.TICKETS_PREVIEWS_DETAILLIST)
                .addParams("page", String.valueOf(page))
                .addParams("rows", String.valueOf(rows))
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("awards", awards)
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
                ToastUtils.show(getString(R.string.str_net_error_message));
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                TicketsDetaiBean ticketsDetaiBean = gson.fromJson(response, TicketsDetaiBean.class);
                if (ticketsDetaiBean.getCode() == 1) {
                    if (ticketsDetaiBean.getContent() != null && ticketsDetaiBean.getContent().size() > 0) {
                        mInfoList.addAll(ticketsDetaiBean.getContent());
                    }

                    if (mInfoList.size() > 0) {
                        imgEmpty.setVisibility(View.GONE);
                    } else {
                        imgEmpty.setVisibility(View.VISIBLE);
                    }
                    mCommonAdapter.notifyDataSetChanged();
                }
            }

        });
    }
}
