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
import com.yst.onecity.adapter.DayDayMoneyAdapter;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.AccountIntegralBean;
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
 * 会员待返积分天天奖页面
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class IntegralReturnedActivity extends BaseActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_select_months)
    TextView tvSeelctMonth;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.activity_balance_details)
    LinearLayout activityBalanceDetails;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.mSmartRefresh)
    SmartRefreshLayout mSmartRefresh;

    private DayDayMoneyAdapter newsListAdapter;
    private List<AccountIntegralBean.ContentBean> balanceDetailBeanList = new ArrayList<>();
    private String currentMonth;
    private int pageindex = 1;
    private int pagesize = 10;

    @Override
    public int bindLayout() {
        return R.layout.activity_integral_returned;
    }

    @Override
    public void initData() {
        initTitleBar("积分明细");
        initRefreshListener();
    }

    /**
     * 初始化刷新监听
     */
    private void initRefreshListener() {

        mSmartRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageindex++;
                requestBalanceDetail();
                mSmartRefresh.finishLoadmore(500);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                balanceDetailBeanList.clear();
                pageindex = 1;
                requestBalanceDetail();
                mSmartRefresh.finishRefresh(500);
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
     * 请求专卖积分明细列表
     */
    private void requestBalanceDetail() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "month", currentMonth,
                "pageindex", String.valueOf(pageindex),
                "pagesize", String.valueOf(pagesize),
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.MYACCOUNT_MAIN_INTEGRAL_LIST)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("month", currentMonth)
                .addParams("pageindex", String.valueOf(pageindex))
                .addParams("pagesize", String.valueOf(pagesize))
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
                MyLog.e("BalanceDetail", "会员天天奖余额明细requestBalanceDetail____onerror___" + e);
            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("BalanceDetail", "会员天天奖余额明细requestBalanceDetail____onResponse___" + response);
                Gson gson = new Gson();
                AccountIntegralBean mAccountIntegralBean = gson.fromJson(response, AccountIntegralBean.class);
                if (mAccountIntegralBean != null && mAccountIntegralBean.getCode() == 1 && mAccountIntegralBean.getContent() != null) {
                    balanceDetailBeanList.addAll(mAccountIntegralBean.getContent());
                }
                flushBalanceList();
            }
        });
    }

    /**
     * 刷新适配器
     */
    private void flushBalanceList() {
        if (null == listView) {
            return;
        }
        if (balanceDetailBeanList.size() == 0) {
            llEmpty.setVisibility(View.VISIBLE);
        } else {
            llEmpty.setVisibility(View.GONE);
        }
        if (newsListAdapter == null) {
            newsListAdapter = new DayDayMoneyAdapter(balanceDetailBeanList, this);
            listView.setAdapter(newsListAdapter);
        } else {
            newsListAdapter.setFineBeanList(balanceDetailBeanList);
        }
    }
}
