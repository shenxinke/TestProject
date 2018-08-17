package com.yst.onecity.activity.member;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.adapter.TimeLineAdapter;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.InformationBean;
import com.yst.onecity.bean.TimeLine;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 查看物流页面
 *
 * @author Shenxinke
 * @version 4.0.0
 * @data 2018/3/21
 */

public class InformationAcitivity extends BaseActivity {

    @BindView(R.id.lv_address_list)
    ListView lvAddressList;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_logiName)
    TextView tvLogiName;
    @BindView(R.id.tv_telPhone)
    TextView tvTelPhone;
    @BindView(R.id.ll_header)
    LinearLayout llHeader;
    @BindView(R.id.img_default)
    ImageView imgDefault;
    TimeLineAdapter timeLineAdapter;
    List<TimeLine> list = new ArrayList<>();

    @Override
    public int bindLayout() {
        return R.layout.activity_logistics_information;
    }

    @Override
    public void initData() {
        initTitleBar("查看物流");
        Intent intent = getIntent();
        String orderNo = intent.getStringExtra("phone");
        String orderImage = intent.getStringExtra("image");
        Glide.with(InformationAcitivity.this).load(orderImage).error(R.mipmap.head_2).into(imgDefault);
        getLogisticsInformation(orderNo);
    }
    /**
     * 物流信息
     */
    private  void getLogisticsInformation(String orderNo){
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "orderNo",orderNo,
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.LOGISTICSINFORMATION)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("orderNo", orderNo)
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
                ToastUtils.show(R.string.app_on_request_error_msg, Toast.LENGTH_SHORT);
            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("register", "getcode__onResponse___" + response);
                Gson gson = new Gson();
                InformationBean informationBean = gson.fromJson(response, InformationBean.class);
                int code = informationBean.getCode();
                if(code==1){
                    llHeader.setVisibility(View.VISIBLE);
                    lvAddressList.setVisibility(View.VISIBLE);
                    String stateValue = informationBean.getContent().getStateValue();
                    List<InformationBean.ContentBean.LogisticMessageBean> logisticMessage = informationBean.getContent().getLogisticMessage();
                    timeLineAdapter = new TimeLineAdapter(InformationAcitivity.this,logisticMessage,stateValue);
                    lvAddressList.setAdapter(timeLineAdapter);
                    tvState.setText(informationBean.getContent().getStateValue());
                    tvLogiName.setText(informationBean.getContent().getLogiName()+":"+informationBean.getContent().getOrderNum());
                    tvTelPhone.setText(informationBean.getContent().getTelPhone());
                }else{
                    ToastUtils.show(informationBean.getMsg(), Toast.LENGTH_SHORT);
                }
            }
        });
    }
}
