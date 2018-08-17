package com.yst.onecity.activity.servermember;

import com.yst.onecity.R;
import com.yst.onecity.base.BaseActivity;

/**
 * 服务专员余额明细分润金额详情页面
 *
 * @author Chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class BalanceDetailsDetailsActivity extends BaseActivity {

    @Override
    public int bindLayout() {
        return R.layout.activity_balance_details_details;
    }

    @Override
    public void initData() {
        initTitleBar("分润金额详情");
    }
}
