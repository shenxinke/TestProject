package com.yst.onecity.activity.member;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.adapter.ProvinceAdapter;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.ProvinceBean;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 会员新增收获地址选择区页面
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class AreaActivity extends BaseActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.province_listView)
    ListView provinceListView;
    @BindView(R.id.activity_area)
    LinearLayout activityArea;
    private String proId, proName, cityId, cityName;
    private int page = 1;
    private ProvinceAdapter myAdapter;
    private List<ProvinceBean.ContentBean> provinceList = new ArrayList<>();

    @Override
    public int bindLayout() {
        return R.layout.activity_area;
    }

    @Override
    public void initData() {
        initTitleBar("选择区");
        if (getIntent() != null) {
            Intent intent = getIntent();
            proId = intent.getStringExtra("pId");
            proName = getIntent().getStringExtra("pName");
            cityId = getIntent().getStringExtra("Cid");
            cityName = getIntent().getStringExtra("CName");
            requestArea();
        }
        MyLog.e("city", "市proName______" + proName);
        provinceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().post(new EventBean("finish", proName + "-" + cityName + "-" + provinceList.get(position).getLetter(), proId, cityId, provinceList.get(position).getId() + ""));
                finish();
            }
        });
    }

    /**
     * 获取区列表
     */
    private void requestArea() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "cId", cityId,
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.GET_AREA)
                .addParams("cId", cityId)
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
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
                MyLog.e("area", "市onError______" + e);
            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("area", "市response______" + response);
                Gson gson = new Gson();
                ProvinceBean provinceBean = gson.fromJson(response, ProvinceBean.class);
                if (provinceBean != null && provinceBean.getCode() == 1 && provinceBean.getContent() != null) {
                    if (page == 1) {
                        provinceList = provinceBean.getContent();
                    } else {
                        provinceList.addAll(provinceBean.getContent());
                    }
                } else {
                    if (page != 1) {
                        ToastUtils.show(TianyiApplication.instance.getResources().getString(R.string.xlist_add_all_msg));
                    }
                }
                flushAddressData();
            }

        });
    }

    private void flushAddressData() {
        if (null == provinceListView) {
            return;
        }
        if (myAdapter == null) {
            myAdapter = new ProvinceAdapter(provinceList, this);
            provinceListView.setAdapter(myAdapter);
        } else {
            myAdapter.setData(provinceList);
        }
    }

}
