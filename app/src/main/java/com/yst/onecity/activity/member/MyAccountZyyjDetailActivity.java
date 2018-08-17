package com.yst.onecity.activity.member;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.adapter.AccountBanlanceAdapter;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.tickets.AccountBalanceBean;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.util.DateUtils;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 专员佣金明细
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class MyAccountZyyjDetailActivity extends BaseActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.xListView_dictation)
    ListView xListViewDictation;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.activity_balance_details)
    LinearLayout activityBalanceDetails;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private AccountBanlanceAdapter newsListAdapter;
    private List<AccountBalanceBean.ContentBean> balanceDetailBeanList = new ArrayList<>();
    private int page = 1;
    private int rows = 15;
    private String currentMonth;

    @Override
    public int bindLayout() {
        return R.layout.activity_balance_details;
    }

    @Override
    public void initData() {
        initTitleBar("佣金明细");
        initRefreshListener();
    }

    /**
     * 初始化刷新监听
     */
    private void initRefreshListener() {

        smartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                requestBalanceDetail();
                smartRefreshLayout.finishLoadmore(500);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                balanceDetailBeanList.clear();
                page = 1;
                requestBalanceDetail();
                smartRefreshLayout.finishRefresh(500);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Calendar c = Calendar.getInstance();
        // 获取当前年份
        int year = c.get(Calendar.YEAR);
        // 获取当前月份
        int month = c.get(Calendar.MONTH) + 1;
        currentMonth = year + "-" + DateUtils.fillZero(month);
        tvMonth.setText(currentMonth);
        balanceDetailBeanList.clear();
        requestBalanceDetail();
    }

    @OnClick({R.id.tv_select_months})
    public void onViewClicked(View view) {
        if (!ConstUtils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            //选择月份
            case R.id.tv_select_months:
                DatePicker pickers = new DatePicker(this, DatePicker.YEAR_MONTH);
                //偏移量
                pickers.setOffset(1);
                pickers.setTextSize(15);
                Calendar c = Calendar.getInstance();
                // 获取当前年份
                int year = c.get(Calendar.YEAR);
                // 获取当前月份
                int month = c.get(Calendar.MONTH) + 1;
                // 获取当日期
                int day = c.get(Calendar.DAY_OF_MONTH);
                //年份范围
                pickers.setRange(year - 1, year);
                pickers.setSelectedItem(year, month, day);
                pickers.setLineColor(Color.parseColor("#FFCE1C"));
                pickers.setCancelTextColor(Color.parseColor("#333333"));
                pickers.setSubmitTextColor(Color.parseColor("#FFCE1C"));
                pickers.setTextColor(Color.parseColor("#FFCE1C"), Color.parseColor("#333333"));
                pickers.setOnDatePickListener(new DatePicker.OnYearMonthPickListener() {
                    @Override
                    public void onDatePicked(String year, String month) {
                        currentMonth = year + "-" + month;
                        tvMonth.setText(currentMonth);
                        balanceDetailBeanList.clear();
                        requestBalanceDetail();
                    }
                });
                pickers.show();
                break;
            default:
                break;
        }
    }

    /**
     * 请求余额明细
     */
    private void requestBalanceDetail() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "month", currentMonth,
                "page", String.valueOf(page),
                "rows", String.valueOf(rows),
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.MYACCOUNT_MAIN_ZYYJ_LIST)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("month", currentMonth)
                .addParams("page", String.valueOf(page))
                .addParams("rows", String.valueOf(rows))
                .addParams("timestamp", timestamp)
                .addParams("sign", sign)
                .addParams("client_type", "A")
                .build().execute(new StringCallback() {
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
                MyLog.e("BalanceDetail", "会员余额明细requestBalanceDetail____onerror___" + e);
            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("BalanceDetail", "会员余额明细requestBalanceDetail____onResponse___" + response);
                Gson gson = new Gson();
                AccountBalanceBean balanceDetailBean = gson.fromJson(response, AccountBalanceBean.class);
                if (balanceDetailBean != null && balanceDetailBean.getCode() == 1) {
                    balanceDetailBeanList.addAll(balanceDetailBean.getContent());
                }

                if(balanceDetailBeanList.size() > 0){
                    llEmpty.setVisibility(View.GONE);
                }else{
                    llEmpty.setVisibility(View.VISIBLE);
                }
                flushBalanceList();

            }
        });
    }

    /**
     * 刷新适配器
     */
    private void flushBalanceList() {
        if (null == xListViewDictation) {
            return;
        }
        MyLog.e("xxx","-----" + balanceDetailBeanList.toString());

        if (newsListAdapter == null) {
            newsListAdapter = new AccountBanlanceAdapter(balanceDetailBeanList, this);
            xListViewDictation.setAdapter(newsListAdapter);
        } else {
            newsListAdapter.setFineBeanList(balanceDetailBeanList);
        }
    }
}
