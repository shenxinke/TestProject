package com.yst.onecity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.servermember.ServerMemberProductDetailActivity;
import com.yst.onecity.activity.servermember.StoreDetailActivity;
import com.yst.onecity.adapter.AbstractCommonAdapter;
import com.yst.onecity.adapter.CommonViewHolder;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.bean.servermember.SearchServerMemberBean;
import com.yst.onecity.callbacks.AbstractSearchServerMemberCallback;
import com.yst.onecity.config.Constants;
import com.yst.onecity.eventbus.RefreshSearchEvent;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.FlowLayout;
import com.yst.onecity.view.roundedimageview.RoundedImageView;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 首页资讯
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class SearchServiceSpecialistInteractionFragment extends BaseFragment {

    AbstractCommonAdapter<SearchServerMemberBean.ContentBean> adapter;
    List<SearchServerMemberBean.ContentBean> datas = new ArrayList<>();

    @BindView(R.id.lv_search_service_specialist_interaction)
    ListView listView;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.tv_empty)
    TextView empty;

    private int pageNum = 1;
    private int rows = 10;
    private String searchText;
    private boolean isRefresh = true;

    @Override
    public int bindLayout() {
        return R.layout.fragment_serach_service_specialist_interaction;
    }

    @Override
    public void initData() {

        EventBus.getDefault().register(this);
        Bundle args = getArguments();
        searchText = args.getString("searchText","");
        MyLog.e("@@@@","initData===");
        searchServerMemberData();

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNum = 1;
                isRefresh = true;
                datas.clear();
                adapter.notifyDataSetChanged();
                searchServerMemberData();
            }
        });
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

                if (datas.size() < 1) {
                    pageNum = 1;
                } else {
                    pageNum++;
                }

                isRefresh = false;
                searchServerMemberData();

            }
        });

        adapter = new AbstractCommonAdapter<SearchServerMemberBean.ContentBean>(getContext(),datas,R.layout.item_serach_service_specialist_interaction) {
            @Override
            public void convert(CommonViewHolder holder, int position, final SearchServerMemberBean.ContentBean item) {
                Glide.with(context).load(Constants.URL_IMAGE_HEAD + item.getLogoImg()).into((RoundedImageView)holder.getView(R.id.iv_head));
                holder.setText(R.id.tv_find_count,String.format(getString(R.string.findTa),item.getNum()));
                holder.setText(R.id.tv_name,item.getNickname());
                holder.setText(R.id.tv_des,item.getServiceDigest());

                if (item.getHunterLable() != null){

                    for (int i = 0; i < item.getHunterLable().size(); i++) {
                        View view = LayoutInflater.from(context).inflate(R.layout.item_lable, null);
                        final TextView columnTextView = (TextView) view.findViewById(R.id.txt_lable);
                        columnTextView.setText(item.getHunterLable().get(i).getName());
                        ((FlowLayout)holder.getView(R.id.flow_label)).addView(view);
                    }
                }

                holder.setText(R.id.tv_shop_name,item.getMerchantName());
                holder.setText(R.id.tv_shop_address,item.getMerchantAdress());

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
                bundle.putInt("hunterId", datas.get(position).getHunterId());
                JumpIntent.jump(getActivity(), ServerMemberProductDetailActivity.class, bundle);
            }
        });
    }

    @Subscribe
    public void onEventMainThread(RefreshSearchEvent event) {
        MyLog.e("@@@@","RefreshSearchEvent===");
        datas.clear();
        smartRefreshLayout.autoRefresh(200);
        searchText = event.getMsg();
        pageNum = 1;
        searchServerMemberData();
    }
    private void searchServerMemberData() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("timestamp",timestamp,"memberId", TianyiApplication.appCommonBean.getId()
                        ,"keyword",searchText,"page",String.valueOf(pageNum),"rows",String.valueOf(rows));

        OkHttpUtils.post().url(Constants.SEARCH_SERVER_MEMBER)
                .addParams("memberId",TianyiApplication.appCommonBean.getId())
                .addParams("keyword",searchText)
                .addParams("page",String.valueOf(pageNum))
                .addParams("rows",String.valueOf(rows))
                .addParams("timestamp",timestamp)
                .addParams("client_type","A")
                .addParams("sign",sign)
                .build().execute(new AbstractSearchServerMemberCallback() {

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
            }

            @Override
            public void onResponse(SearchServerMemberBean response, int id) {
                try{
                    if (response != null && response.getCode() ==1 ){
                        if (pageNum == 1 && response.getContent().size()<1){
                            empty.setVisibility(View.VISIBLE);
                        }else{
                            empty.setVisibility(View.GONE);
                        }
                        datas.addAll(response.getContent());
                        adapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
}
