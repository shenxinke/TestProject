package com.yst.onecity.activity.servermember;

import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.utils.ConstUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 服务专员提现详情信息页面
 *
 * @author Chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class CashDetailInfoActivity extends BaseActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
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
    @BindView(R.id.tv_shouxufei)
    TextView tvShouxufei;
    @BindView(R.id.tv_kouchujifen)
    TextView tvKouchujifen;
    @BindView(R.id.tv_cash_band)
    TextView tvCashBand;
    @BindView(R.id.tv_band_num)
    TextView tvBandNum;

    @Override
    public int bindLayout() {
        return R.layout.activity_cash_detail_info;
    }

    @Override
    public void initData() {
        setTitleBarTitle("提现详情");
    }

    @OnClick({R.id.ll_back})
    public void onViewClicked(View view) {
        if (!ConstUtils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            //返回
            case R.id.ll_back:
                EventBus.getDefault().post(new EventBean("jump","","","",""));
                finish();
                break;
            default:
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            EventBus.getDefault().post(new EventBean("jump","","","",""));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
