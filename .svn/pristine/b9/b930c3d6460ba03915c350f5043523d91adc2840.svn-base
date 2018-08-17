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
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 会员新增收获地址选择市页面
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class CityActivity extends BaseActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.province_listView)
    ListView listview;
    private String provinceName, provinceId;
    private int page = 1;
    private ProvinceAdapter myAdapter;
    private List<ProvinceBean.ContentBean> provinceList = new ArrayList<>();
    private String finish = "finish";

    @Override
    public int bindLayout() {
        return R.layout.activity_city;
    }

    @Override
    public void initData() {
        initTitleBar("选择市");
        EventBus.getDefault().register(this);
        if (getIntent() != null) {
            Intent intent = getIntent();
            provinceId = intent.getStringExtra("provinceId");
            provinceName = getIntent().getStringExtra("provincename");
            MyLog.e("city", "市收到______" + provinceId + "_____" + provinceName);
            requestCity();
        }
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                requestArea(position);
            }
        });
    }

    /**
     * 获取市列表
     */
    private void requestCity() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "pId", provinceId,
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.GET_CITY)
                .addParams("pId", provinceId)
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
                MyLog.e("city", "市onError______" + e);
            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("city", "市response______" + response);
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

    /**
     * 刷新数据
     */
    private void flushAddressData() {
        if (null == listview) {
            return;
        }
        if (myAdapter == null) {
            myAdapter = new ProvinceAdapter(provinceList, this);
            listview.setAdapter(myAdapter);
        } else {
            myAdapter.setData(provinceList);
        }
    }

    /**
     * 获取区列表
     */
    private void requestArea(final int position) {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "cId", provinceList.get(position).getId() + "",
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.GET_AREA)
                .addParams("cId", provinceList.get(position).getId() + "")
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
                if (provinceBean.getContent() != null && provinceBean.getContent().size() != 0) {
                    Intent intent = new Intent(CityActivity.this, AreaActivity.class);
                    intent.putExtra("pId", provinceId);
                    intent.putExtra("pName", provinceName);
                    intent.putExtra("Cid", provinceList.get(position).getId() + "");
                    intent.putExtra("CName", provinceList.get(position).getLetter());
                    startActivityForResult(intent, 333);
                } else {
                    EventBus.getDefault().post(new EventBean("finish", provinceName + "-" + provinceList.get(position).getLetter(), provinceId, provinceList.get(position).getId() + "", ""));
                    finish();
                }
            }
        });
    }

    @Subscribe
    public void onEventMainThread(EventBean event) {
        if (finish.equals(event.getMsg())) {
            finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Const.INTEGER_333 && resultCode == Const.INTEGER_444) {
            Intent intent1 = new Intent();
            setResult(222, intent1);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
