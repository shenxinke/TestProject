package com.yst.onecity.activity.commissioner;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.adapter.CommomFragmentPagerAdapter;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.AdressBean;
import com.yst.onecity.bean.commissioner.PublishNumBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.fragment.commissoner.CopyFragment;
import com.yst.onecity.fragment.commissoner.NopassFragment;
import com.yst.onecity.fragment.commissoner.PubLishFragment;
import com.yst.onecity.fragment.commissoner.ShenHeingFragment;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.viewpagerindicator.ViewPagerIndicator;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import gorden.rxbus2.RxBus;
import gorden.rxbus2.Subscribe;
import gorden.rxbus2.ThreadMode;
import okhttp3.Call;

/**
 * 我的分享的页面
 *
 * @author wuxiaofang
 * @version 4.2.0
 * @date 2018/6/5
 */

public class MyShareActivity extends BaseActivity {

    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.tab)
    ViewPagerIndicator tab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private String[] str;
    private ArrayList<Fragment> fraglist;
    private CommomFragmentPagerAdapter adapter;
    private String index;

    @Override
    public int bindLayout() {
        return R.layout.activity_mine_share;
    }

    @Override
    public void initData() {
        tvMainTitle.setText(getString(R.string.tv_my_share));
        RxBus.get().register(this);
        str = new String[]{"已发布(0)", "审核中(0)", "未通过(0)", "草稿箱(0)"};
        fraglist = new ArrayList<>();
        fraglist.add(new PubLishFragment());
        fraglist.add(new ShenHeingFragment());
        fraglist.add(new NopassFragment());
        fraglist.add(new CopyFragment());
        adapter = new CommomFragmentPagerAdapter(getSupportFragmentManager(), fraglist, str);
        viewpager.setAdapter(adapter);
        tab.bindViewPager(viewpager, true);
        getShareListNum("");
    }

    @OnClick(R.id.ll_back)
    public void toBack() {
        finish();
    }

    /**
     * 获取列表条目数
     */
    private void getShareListNum(final String index) {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "timestamp", timestamp);
        if (TextUtils.isEmpty(sign)) {
            return;
        }
        OkHttpUtils.post().url(Constants.GET_SHARE_NUM)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.str_net_error_message));
                MyLog.e("sss", "-error: " + e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("sss", "--count: " + response);
                if (null != response) {
                    PublishNumBean bean = new Gson().fromJson(response, PublishNumBean.class);
                    if (null != bean) {
                        if (bean.getCode() == Const.INTEGER_1 && null != bean.getContent()) {
                            PublishNumBean.ContentBean content = bean.getContent();
                            String[] numStr = new String[]{"已发布(" + content.getPublishedNum() + ")",
                                    "审核中(" + content.getUnderReviewNum() + ")",
                                    "未通过(" + content.getNotPassNum() + ")",
                                    "草稿箱(" + content.getDraftBoxNum() + ")"};
                            for (int i = 0; i < numStr.length; i++) {
                                MyLog.e("sss", "--str: " + numStr[i]);
                            }
                            CommomFragmentPagerAdapter adapter = new CommomFragmentPagerAdapter(getSupportFragmentManager(), fraglist, numStr);
                            viewpager.setAdapter(adapter);
                            tab.bindViewPager(viewpager, true);
                            if (!TextUtils.isEmpty(index)) {
                                switch (index) {
                                    case Const.STR0:
                                        viewpager.setCurrentItem(Const.INTEGER_0);
                                        break;
                                    case Const.STR1:
                                        viewpager.setCurrentItem(Const.INTEGER_1);
                                        break;
                                    case Const.STR2:
                                        viewpager.setCurrentItem(Const.INTEGER_2);
                                        break;
                                    case Const.STR3:
                                        viewpager.setCurrentItem(Const.INTEGER_3);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    @Subscribe(code = Const.INTEGER_1000, threadMode = ThreadMode.MAIN)
    public void onReceive(AdressBean adressBean) {
        Log.e("sss", "--onReceive" + adressBean.getProvince());
        getShareListNum(adressBean.getProvince());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
    }
}
