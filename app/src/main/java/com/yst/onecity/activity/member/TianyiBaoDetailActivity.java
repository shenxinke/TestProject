package com.yst.onecity.activity.member;

import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.adapter.TianyiBaoDetailsAdapter;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.BalanceDetailBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.xlistview.XListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 会员天易宝余额明细
 *
 * @author Chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class TianyiBaoDetailActivity extends BaseActivity implements XListView.IXListViewListener {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.xListView_dictation)
    XListView xListViewDictation;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.activity_my_account2)
    LinearLayout activityMyAccount2;
    private Handler mHandler;
    private TianyiBaoDetailsAdapter newsListAdapter;
    private List<BalanceDetailBean.ContentBean.DatasBean> balanceDetailBeanList = new ArrayList<>();
    private String currentMonth;

    @Override
    public int bindLayout() {
        return R.layout.activity_tianyi_bao_detail;
    }

    @Override
    public void initData() {
        initTitleBar("余额明细");
        xListViewDictation.setPullLoadEnable(false);
        xListViewDictation.setPullRefreshEnable(false);
        xListViewDictation.setXListViewListener(this);
        mHandler = new Handler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Calendar c = Calendar.getInstance();
        // 获取当前年份
        int year = c.get(Calendar.YEAR);
        // 获取当前月份
        int month = c.get(Calendar.MONTH) + 1;
        currentMonth = year + "-" + month;
        tvMonth.setText(month + "月");
        balanceDetailBeanList.clear();
        requestBalanceDetail();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @OnClick({R.id.tv_month})
    public void onViewClicked(View view) {
        if (!ConstUtils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            //选择月份
            case R.id.tv_month:
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
                pickers.setRange(year, year);
                pickers.setSelectedItem(year, month, day);
                pickers.setLineColor(Color.parseColor("#FFCE1C"));
                pickers.setCancelTextColor(Color.parseColor("#333333"));
                pickers.setSubmitTextColor(Color.parseColor("#FFCE1C"));
                pickers.setTextColor(Color.parseColor("#FFCE1C"), Color.parseColor("#333333"));
                pickers.setOnDatePickListener(new DatePicker.OnYearMonthPickListener() {
                    @Override
                    public void onDatePicked(String year, String month) {
                        currentMonth = year + "-" + month;
                        tvMonth.setText(month + "月");
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
     * 获取余额明细
     */
    private void requestBalanceDetail() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "userid", TianyiApplication.appCommonBean.getId(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "currentMonth", currentMonth,
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.MEMBER_DAYDAYLIST)
                .addParams("userid", TianyiApplication.appCommonBean.getId())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("currentMonth", currentMonth)
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
                MyLog.e("BalanceDetail", "会员零钱余额明细requestBalanceDetail____onerror___" + e);
            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("BalanceDetail", "会员零钱余额明细requestBalanceDetail____onResponse___" + response);
                Gson gson = new Gson();
                BalanceDetailBean balanceDetailBean = gson.fromJson(response, BalanceDetailBean.class);
                if (balanceDetailBean != null && balanceDetailBean.getCode() == Const.INTEGER_200 && balanceDetailBean.getContent() != null) {
                    BalanceDetailBean.ContentBean content = balanceDetailBean.getContent();
                    if (content.getDatas() != null) {
                        for (int i = 0; i < content.getDatas().size(); i++) {
                            if ("天天奖".equals(content.getDatas().get(i).getSourceType()) || "提现".equals(content.getDatas().get(i).getSourceType())) {
                                balanceDetailBeanList.add(content.getDatas().get(i));
                            }
                        }
                    }
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
        if (balanceDetailBeanList.size() == 0) {
            llEmpty.setVisibility(View.VISIBLE);
        } else {
            llEmpty.setVisibility(View.GONE);
        }
        if (newsListAdapter == null) {
            newsListAdapter = new TianyiBaoDetailsAdapter(balanceDetailBeanList, this);
            xListViewDictation.setAdapter(newsListAdapter);
        } else {
            newsListAdapter.setFineBeanList(balanceDetailBeanList);
        }
    }


    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoad();
            }
        }, 100);
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoad();
            }
        }, 100);
    }

    /**
     * 用来停止列表刷新的
     **/

    private void onLoad() {
        if (null == xListViewDictation) {
            return;
        }
        xListViewDictation.stopLoadMore();
        xListViewDictation.stopRefresh();
        xListViewDictation.setRefreshTime(Utils.getXListViewTopTime());
    }
}
