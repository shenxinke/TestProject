package com.yst.onecity.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.TIMConversationType;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.chat.ChatActivity;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.adapter.ServerMemberListAdapter;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.bean.ServerMemberMessageBean;
import com.yst.onecity.callbacks.AbstractServerMemberListCallback;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Request;


/**
 * 会员列表
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class MemberListFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @BindView(R.id.lv_member_list)
    ListView lvMemberList;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.empty)
    TextView empty;

    private List<ServerMemberMessageBean.ContentBean> datas;
    private ServerMemberListAdapter mAdapter;

    @Override
    public int bindLayout() {
        return R.layout.fragment_member_list;
    }

    @Override
    public void initData() {

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                smartRefreshLayout.finishRefresh(2000);
                page = 1;
                datas.clear();
                mAdapter.notifyDataSetChanged();
                getMemberList();
            }
        });
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                smartRefreshLayout.finishLoadmore(2000);
                page++;
                getMemberList();
            }
        });

        datas = new ArrayList<>();
        mAdapter = new ServerMemberListAdapter(getContext(),datas);
        lvMemberList.setAdapter(mAdapter);
        getMemberList();
        lvMemberList.setOnItemClickListener(this);
    }
    private int page=1;

    /**
     * 获取会员列表
     */
    private void getMemberList() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "rows",String.valueOf(10),
                "timestamp",timestamp,
                "page",String.valueOf(page));
        OkHttpUtils
                .post()
                .url(Constants.SERVER_MEMBER_LIST)
                .addParams("page",String.valueOf(page))
                .addParams("rows",String.valueOf(10))
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid",TianyiApplication.appCommonBean.getUuid())
                .addParams("sign",sign)
                .addParams("timestamp",timestamp)
                .addParams("client_type", "A")
                .build().execute(new AbstractServerMemberListCallback() {
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
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.str_net_error_message));
            }

            @Override
            public void onResponse(ServerMemberMessageBean response, int id) {
                if(response.getCode()==1) {
                    if(response.getContent() !=null && response.getContent().size() > 0){
                        datas.addAll(response.getContent());
                        empty.setVisibility(View.GONE);
                    }else {
                        empty.setVisibility(View.VISIBLE);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!TianyiApplication.isLogin){
            ToastUtils.show("您还没有登录");
            JumpIntent.jump(getActivity(), LoginActivity.class);
            return;
        }
        ChatActivity.navToChat(context, datas.get(position).getImid(), String.valueOf(datas.get(position).getRecive_hunter_id()) ,TIMConversationType.C2C);
    }
}
