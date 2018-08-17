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
 * 会员新增收获地址选择省页面
 *
 * @author Chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class ProvinceActivity extends BaseActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.province_listView)
    ListView provinceListView;
    private ProvinceAdapter myAdapter;
    private List<ProvinceBean.ContentBean> provinceList = new ArrayList<>();
    private int page = 1;
    @Override
    public int bindLayout() {
        return R.layout.activity_province;
    }

    @Override
    public void initData() {
        initTitleBar("选择省份");
        EventBus.getDefault().register(this);
        provinceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProvinceActivity.this, CityActivity.class);
                intent.putExtra("provinceId", provinceList.get(position).getId()+"");
                intent.putExtra("provincename", provinceList.get(position).getLetter());
                startActivityForResult(intent, 111);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        requestProvince();
    }

    /**
     * 请求省列表
     */
    private void requestProvince() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "timestamp", timestamp);
        OkHttpUtils.post() .url(Constants.GET_PROVINCE)
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);

            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.app_on_request_error_msg));
                MyLog.e("provinec","省onError______"+e);
            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("provinec","省response______"+response);
                Gson gson = new Gson();
                ProvinceBean provinceBean = gson.fromJson(response, ProvinceBean.class);
                if (provinceBean!=null&&provinceBean.getCode()==1 && provinceBean.getContent() != null){
                    if (page == 1) {
                        provinceList = provinceBean.getContent();
                    } else {
                        provinceList.addAll(provinceBean.getContent());
                    }
                }else {
                    if (page == 1) {
                    } else {
                        ToastUtils.show(TianyiApplication.instance.getResources().getString(R.string.xlist_add_all_msg));
                    }
                }
                flushAddressData();
            }


        });
    }

    /**
     * 刷新适配器
     */
    private void flushAddressData() {
        if (null == provinceListView) {return;}
        if (myAdapter == null) {
            myAdapter = new ProvinceAdapter(provinceList, this);
            provinceListView.setAdapter(myAdapter);
        } else {
            myAdapter.setData(provinceList);
        }
    }

    @Subscribe
    public void onEventMainThread(EventBean event) {
        if (Const.STR_FINISH.equals(event.getMsg())) {
            finish();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
