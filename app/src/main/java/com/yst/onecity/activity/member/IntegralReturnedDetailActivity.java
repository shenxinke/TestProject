package com.yst.onecity.activity.member;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.base.BaseActivity;

import butterknife.BindView;

/**
 * 会员奖励兑换详情详情页面
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class IntegralReturnedDetailActivity extends BaseActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.tv_money)
    TextView tvMoney;

    @Override
    public int bindLayout() {
        return R.layout.activity_integral_returned_detail;
    }

    @Override
    public void initData() {
        initTitleBar("奖励兑换详情");
    }

}
