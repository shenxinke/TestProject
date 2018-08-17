package com.yst.onecity.activity.member;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.commissioner.CommissionerHomePageActivity;
import com.yst.onecity.adapter.MoreServerMemberAdapter;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.MoreServerMemberBean;
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
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;


/**
 * 搜索服务专员页面
 *
 * @author Shenxinke
 * @version 4.2.0
 * @data 2018/6/6
 */

public class SearchServiceSpecialistActivity extends BaseActivity {

    private MoreServerMemberAdapter moreServerMemberAdapter;
    private List<MoreServerMemberBean.ContentBean> list = new ArrayList<>();
    private int pageNum = 1;
    private int rows = 10;
    private String name;

    @BindView(R.id.search_list)
    ListView lvServer;
    @BindView(R.id.et_search_etext)
    EditText etSearchText;
    @BindView(R.id.tv_search)
    TextView tvSearchText;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;


    @Override
    public int bindLayout() {
        return R.layout.activity_search_service_specialist;
    }

    @Override
    public void initData() {
        getEditText();
        initListener();
    }

    /**
     * 点击事件
     */
    @OnClick({R.id.activity_back_iv})
    public void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.activity_back_iv:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 输入框逻辑
     */
    public void getEditText() {
        etSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                name = editable.toString();
                if (TextUtils.isEmpty(name)) {
                    tvSearchText.setVisibility(View.GONE);
                } else {
                    tvSearchText.setVisibility(View.VISIBLE);
                }
                tvSearchText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        list.clear();
                        getSearchSpecia();
                    }
                });
            }
        });
    }

    /**
     * 搜索服务专员
     */
    private void getSearchSpecia() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "name", name,
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "page", String.valueOf(pageNum),
                "rows", String.valueOf(rows),
                "timestamp", timestamp);
        if (TextUtils.isEmpty(sign)) {
            return;
        }
        OkHttpUtils.post()
                .url(Constants.SEARCH_COMMISSIONER_RECOMMENDED)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("page", String.valueOf(pageNum))
                .addParams("rows", String.valueOf(rows))
                .addParams("client_type", "A")
                .addParams("name", name)
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
                MyLog.e("sss", "-搜索专员: " + response);
                if (response != null) {
                    MoreServerMemberBean moreServerMemberBean = new Gson().fromJson(response, MoreServerMemberBean.class);
                    if (moreServerMemberBean.getCode() == Const.INTEGER_1) {
                        list.addAll(moreServerMemberBean.getContent());
                        setAdapter();
                    }
                } else {
                    ToastUtils.show(getString(R.string.tv_check_service));
                }
            }
        });
    }

    /**
     * 设置数据适配器
     */
    private void setAdapter() {
        if (moreServerMemberAdapter == null) {
            moreServerMemberAdapter = new MoreServerMemberAdapter(SearchServiceSpecialistActivity.this, list);
            lvServer.setAdapter(moreServerMemberAdapter);
        } else {
            moreServerMemberAdapter.setData(list);
        }
    }

    /**
     * 上拉加载下拉刷新
     */
    private void initListener() {
        lvServer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("hid", String.valueOf(list.get(i).getHid()));
                bundle.putString("from", Const.STR_FROM_LOGINED);
                JumpIntent.jump(SearchServiceSpecialistActivity.this, CommissionerHomePageActivity.class, bundle);
            }
        });

        smartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageNum++;
                getSearchSpecia();
                smartRefreshLayout.finishLoadmore(1000);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNum = 1;
                if (list != null) {
                    list.clear();
                }
                getSearchSpecia();
                smartRefreshLayout.finishRefresh(500);
            }
        });
    }
}