package com.yst.onecity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.onecity.R;
import com.yst.onecity.activity.NewDetailActivity;
import com.yst.onecity.adapter.InfomationAdapter;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.bean.consult.ConsultListBean;
import com.yst.onecity.callbacks.AbstractConsultListCallback;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

import static com.yst.onecity.utils.SignUtils.getTimeStamp;

/**
 * 服务专员资讯列表
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class ServerMemberInformationContentFragment extends BaseFragment {

    @BindView(R.id.swipe)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.empty)
    TextView empty;
    @BindView(R.id.lv_information)
    ListView lvInformation;

    private String userId;
    /**
     * 资讯分类下的id
     */
    private String id;
    private InfomationAdapter adapter;
    private ArrayList<ConsultListBean.ContentBean.ContentListBean> datas = new ArrayList<>();
    private boolean isRefresh = true;
    private int pageNum = 1;
    private int rows = 10;

    @Override
    public int bindLayout() {
        return R.layout.fragment_server_member_infomation_content;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(getActivity());
        if (datas != null) {
            datas.clear();
        }
        Bundle bundle = getArguments();
        if (bundle != null) {
            userId = bundle.containsKey("userId") ? bundle.get("userId").toString() : "";
            id = bundle.containsKey("id") ? bundle.get("id").toString() : "";
        }
    }

    @Override
    public void initData() {
        adapter = new InfomationAdapter(getContext(), datas);
        lvInformation.setAdapter(adapter);
        lvInformation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(datas.get(position).getId()));
                JumpIntent.jump(getActivity(), NewDetailActivity.class, bundle);
            }
        });


        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNum = 1;
                isRefresh = true;
                datas.clear();
                adapter.notifyDataSetChanged();
                getServerMemberConsultList();
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
                getServerMemberConsultList();

            }
        });
    }

    /**
     * 获取服务专员咨询列表
     */
    private void getServerMemberConsultList() {
        showInfoProgressDialog();
        String timestamp = getTimeStamp();
        String sign = Utils.getSign("timestamp", timestamp, "userId", userId, "conId", id, "page", String.valueOf(pageNum), "rows", String.valueOf(rows));

        OkHttpUtils.post().url(Constants.SERVER_MEMBER_CONSULT_LIST)
                .addParams("userId", userId)
                .addParams("conId", id)
                .addParams("page", String.valueOf(pageNum))
                .addParams("rows", String.valueOf(rows))
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractConsultListCallback() {

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
                dismissInfoProgressDialog();
            }

            @Override
            public void onResponse(ConsultListBean consultListBean, int id) {

                try {
                    if (consultListBean.getContent() == null || consultListBean.getContent().getConsultationInfoList() == null) {
                        return;
                    }

                    List<ConsultListBean.ContentBean.ContentListBean> consultationInfoList = consultListBean.getContent().getConsultationInfoList();

                    if (pageNum == 1 && consultationInfoList.size() < 1) {
                        empty.setVisibility(View.VISIBLE);
                    } else {
                        empty.setVisibility(View.GONE);
                    }

                    datas.addAll(consultationInfoList);
                    adapter.notifyDataSetChanged();
                    dismissInfoProgressDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                    dismissInfoProgressDialog();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        pageNum = 1;
        datas.clear();
        getServerMemberConsultList();
    }

    /**
     * 推广师详情的资讯fragment
     *
     * @param userId 用户id
     * @return id 分类id
     */
    public static ServerMemberInformationContentFragment newTypeInstance(int userId, long id) {
        Bundle args = new Bundle();
        args.putInt("userId", userId);
        args.putLong("id", id);
        ServerMemberInformationContentFragment fragment = new ServerMemberInformationContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
