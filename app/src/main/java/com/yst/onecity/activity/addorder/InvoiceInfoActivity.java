package com.yst.onecity.activity.addorder;

import com.yst.onecity.R;
import com.yst.onecity.base.BaseActivity;

/**
 * 发票须知页面
 *
 * @author jiaofan
 * @version 4.0.0
 * @date 2018/3/20
 */
public class InvoiceInfoActivity extends BaseActivity {

    @Override
    public int bindLayout() {
        return R.layout.activity_invoice_info;
    }

    @Override
    public void initData() {
        initTitleBar("发票须知");
    }
}
