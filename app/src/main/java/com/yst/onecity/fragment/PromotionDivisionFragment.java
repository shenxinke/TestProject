package com.yst.onecity.fragment;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.SearchActivity;
import com.yst.onecity.activity.amap.Location;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.activity.member.MyMessageActivity;
import com.yst.onecity.activity.servermember.ServerMemberProductDetailActivity;
import com.yst.onecity.activity.servermember.StoreDetailActivity;
import com.yst.onecity.adapter.AbstractCommonAdapter;
import com.yst.onecity.adapter.CommonViewHolder;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.bean.ServerMemberListBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.eventbus.UpdateEvent;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.FlowLayout;
import com.yst.onecity.view.roundedimageview.RoundedImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 服务专员
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class PromotionDivisionFragment extends BaseFragment {

    @BindView(R.id.searchBarLayout)
    LinearLayout llSerachTitle;
    @BindView(R.id.messages)
    ImageView ivMessage;
    @BindView(R.id.messageTagTV)
    TextView tvMessageCount;
    @BindView(R.id.messagesRL)
    RelativeLayout rlMessage;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.empty)
    TextView empty;
    @BindView(R.id.position)
    TextView position;

    private String locationPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    private List<ServerMemberListBean.ContentBean> serverMemberList = new ArrayList<>();
    private AbstractCommonAdapter<ServerMemberListBean.ContentBean> adapter;
    private boolean isRefresh = true;
    private int pageNum = 1;
    private int rows = 10;

    @Override
    public int bindLayout() {
        return R.layout.fragment_promotion_division;
    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
        serverMemberList.clear();
        startLocation();
        getData(pageNum);
        getUnReadMessageNum();
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNum = 1;
                isRefresh = true;
                serverMemberList.clear();
                adapter.notifyDataSetChanged();
                getData(pageNum);
            }
        });
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

                if (serverMemberList.size() < 1) {
                    pageNum = 1;
                } else {
                    pageNum++;
                }

                isRefresh = false;
                getData(pageNum);

            }
        });

    }

    private void getData(final int pageNum) {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("sortType", "0", "page", String.valueOf(pageNum), "rows", String.valueOf(rows), "timestamp", timestamp);

        OkHttpUtils.post().url(Constants.GET_SERVER_MEMBER_LIST)
                .addParams("sortType", "0")
                .addParams("page", String.valueOf(pageNum))
                .addParams("rows", String.valueOf(rows))
                .addParams("client_type","A")
                .addParams("timestamp",timestamp)
                .addParams("sign", sign)
                .build().execute(new StringCallback() {

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (isRefresh) {
                    smartRefreshLayout.finishRefresh(500);
                } else {
                    smartRefreshLayout.finishLoadmore(1500);
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getActivity().getResources().getString(R.string.str_net_error_message));
                empty.setVisibility(View.VISIBLE);
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    Gson gson = new Gson();
                    ServerMemberListBean serverMemberListBean = gson.fromJson(response, ServerMemberListBean.class);
                    if (serverMemberListBean.getCode() == 1){
                        if (serverMemberListBean.getContent()==null){
                            empty.setVisibility(View.VISIBLE);
                            return;
                        }
                        empty.setVisibility(View.GONE);
                        if (pageNum == 1 && serverMemberListBean.getContent().size()<1){
                            empty.setVisibility(View.VISIBLE);
                        }else {
                            empty.setVisibility(View.GONE);
                        }
                        serverMemberList.addAll(serverMemberListBean.getContent());
                    }else {
                        empty.setVisibility(View.VISIBLE);
                        ToastUtils.show(serverMemberListBean.getMsg());
                    }

                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void getUnReadMessageNum(){
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "timestamp", timestamp,
                "type","0"
        );

        OkHttpUtils.post().url(Constants.GET_UNREADMESSAGE)
                .addParams("type","0")
                .addParams("timestamp",timestamp)
                .addParams("client_type","A")
                .addParams("sign",sign)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt(Const.CONS_STR_CODE) == 1){
                        if (jsonObject.getInt(Const.CONS_STR_CONTENT)>0){
                            tvMessageCount.setVisibility(View.VISIBLE);
                            tvMessageCount.setText(String.valueOf(jsonObject.getInt("content")));
                        }else{
                            tvMessageCount.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Subscribe
    public void onEventMainThread(UpdateEvent event) {
        smartRefreshLayout.autoRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void initControll() {
        adapter = new AbstractCommonAdapter<ServerMemberListBean.ContentBean>(context, serverMemberList, R.layout.item_promotion_division) {
            @Override
            public void convert(CommonViewHolder holder, int position, final ServerMemberListBean.ContentBean item) {
                Glide.with(getContext()).load(item.getBackImg()).error(R.mipmap.bg_list_default).into((ImageView) holder.getView(R.id.background));
                if (item.getNickname()== null){
                    holder.setText(R.id.tv_name, "暂无昵称");
                }else{
                    holder.setText(R.id.tv_name, item.getNickname());
                }
                if (item.getServiceDigest() == null){
                    holder.setText(R.id.tv_des, "暂无简介");
                }else{
                    holder.setText(R.id.tv_des, item.getServiceDigest()+"");
                }
                Glide.with(getContext()).load(ConstUtils.matchingImageUrl(item.getLogoImg())).error(R.mipmap.head_2).into((RoundedImageView) holder.getView(R.id.iv_head));
                holder.setText(R.id.tv_shop_name, item.getMerchantName());
                if (item.getMerchantAdress() == null || Const.CONS_STR_NULL.equals(item.getMerchantAdress())){
                    holder.setText(R.id.tv_shop_address,"未填写店铺地址");
                }else{
                    holder.setText(R.id.tv_shop_address, item.getMerchantAdress() + "");
                }

                if (item.getHunterLable() != null){

                    for (int i = 0; i < item.getHunterLable().size(); i++) {
                        View view = LayoutInflater.from(context).inflate(R.layout.item_lable, null);
                        final TextView columnTextView = (TextView) view.findViewById(R.id.txt_lable);
                        columnTextView.setTextSize(12);
                        columnTextView.setPadding(5,3,5,3);
                        columnTextView.setText(item.getHunterLable().get(i).getName());
                        ((FlowLayout)holder.getView(R.id.flow_label)).addView(view);
                    }
                }
                holder.setText(R.id.tv_find_count,String.format(getResources().getString(R.string.findTa),item.getNum()));
                holder.getView(R.id.ll_store_detail).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("merchantId",String.valueOf(item.getMerchantId()));
                        JumpIntent.jump(getActivity(), StoreDetailActivity.class,bundle);
                    }
                });
            }
        };
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("hunterId", serverMemberList.get(position).getHunterId());
                JumpIntent.jump(getActivity(), ServerMemberProductDetailActivity.class, bundle);
            }
        });
    }

    @OnClick({R.id.searchBarLayout, R.id.messagesRL, R.id.messages,R.id.position})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.searchBarLayout:
                JumpIntent.jump(getActivity(), SearchActivity.class);
                break;
            case R.id.messages:
            case R.id.messagesRL:
                if (!TianyiApplication.isLogin){
                    JumpIntent.jump(getActivity(), LoginActivity.class);
                    return;
                }
                JumpIntent.jump(getActivity(), MyMessageActivity.class);
                break;
            case R.id.position:
                startLocation();
                break;
            default:
                break;
        }
    }

    /**
     * 开启定位
     */
    private void startLocation() {
        if (EasyPermissions.hasPermissions(getActivity(), locationPermission)) {
            //启动定位
            new Location(locationCallback).startLocation();
            position.setText("定位中…");
        } else {
            EasyPermissions.requestPermissions(this, "请打开定位权限", 100, locationPermission);
        }
    }

    Location.LocationCallback locationCallback = new Location.LocationCallback() {
        @Override
        public void locSuccess(AMapLocation amapLocation) {
            if (amapLocation.getAoiName()== null || "".equals(amapLocation.getAoiName())){
                position.setText(amapLocation.getAddress());
            }else{
                position.setText(amapLocation.getAoiName());
            }

        }

        @Override
        public void locFailure(int code, String errorInfo) {
            position.setText("定位失败");
            position.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startLocation();
                }
            });
        }
    };
}
