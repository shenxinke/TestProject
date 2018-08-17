package com.yst.onecity.activity.servermember;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.TIMConversationType;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.chat.ChatActivity;
import com.yst.onecity.adapter.ServerMemberListAdapter;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.ServerMemberMessageBean;
import com.yst.onecity.callbacks.AbstractServerMemberListCallback;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.xlistview.XListView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 会员列表
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/12/18
 */
public class ServerMemberListActivity extends BaseActivity implements XListView.IXListViewListener, AdapterView.OnItemClickListener {

    @BindView(R.id.activity_back_iv1)
    ImageView activityBackIv1;
    @BindView(R.id.activity_back_iv)
    ImageView activityBackIv;
    @BindView(R.id.activity_title_tv)
    TextView activityTitleTv;
    @BindView(R.id.activity_title_right_img)
    ImageView activityTitleRightImg;
    @BindView(R.id.activity_title_right_chat_say_img)
    ImageView activityTitleRightChatSayImg;
    @BindView(R.id.activity_complete_tv)
    TextView activityCompleteTv;
    @BindView(R.id.xlist_server_member_list)
    XListView xlistServerMemberList;
    @BindView(R.id.activity_server_member_list)
    LinearLayout activityServerMemberList;
    @BindView(R.id.empty)
    TextView empty;

    private int page = 1;
    private ServerMemberListAdapter mAdapter;
    private List<ServerMemberMessageBean.ContentBean> mdata;

    @Override
    public int bindLayout() {
        return R.layout.activity_server_member_list;
    }

    @Override
    public void initData() {
        mdata = new ArrayList<>();
        mAdapter = new ServerMemberListAdapter(this, mdata);
        activityTitleTv.setText(getString(R.string.str_server_member_list_title));
        xlistServerMemberList.setOnItemClickListener(this);
        xlistServerMemberList.setPullLoadEnable(true);
        xlistServerMemberList.setPullRefreshEnable(true);
        xlistServerMemberList.setXListViewListener(this);
        xlistServerMemberList.setAdapter(mAdapter);
        getMemberList();
    }


    @OnClick(R.id.activity_back_iv)
    public void onViewClick(View view) {
        finish();
    }

    @Override
    public void onRefresh() {
        page = 1;
        mdata.clear();
        mAdapter.notifyDataSetChanged();
        getMemberList();
    }

    /**
     * 获取列表成功后恢复列表的normal状态
     */
    private void onLoad() {
        xlistServerMemberList.stopRefresh();
        xlistServerMemberList.stopLoadMore();
        xlistServerMemberList.setRefreshTime(Utils.getXListViewTopTime());
    }

    @Override
    public void onLoadMore() {
        page++;
        getMemberList();
    }

    /**
     * 获取会员列表
     */
    private void getMemberList() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("timestamp", timestamp, "page", String.valueOf(page)
                , "rows", String.valueOf(10), "phone", TianyiApplication.appCommonBean.getPhone()
                , "uuid", TianyiApplication.appCommonBean.getUuid());
        OkHttpUtils
                .post()
                .url(Constants.SERVER_MEMBER_LIST)
                .addParams("page", String.valueOf(page))
                .addParams("rows", String.valueOf(10))
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("timestamp", timestamp)
                .addParams("sign", sign)
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
                onLoad();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.str_net_error_message));
            }

            @Override
            public void onResponse(ServerMemberMessageBean response, int id) {
                if (response.getCode() == 1) {
                    if (response.getContent() != null && response.getContent().size() > 0) {
                        mdata.addAll(response.getContent());
                        empty.setVisibility(View.GONE);
                    } else {
                        if (mdata.size() > 0) {
                            empty.setVisibility(View.GONE);
                        } else {
                            empty.setVisibility(View.VISIBLE);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int heard = xlistServerMemberList.getHeaderViewsCount();
        int newposition = position - heard;
        ChatActivity.navToChat(context,
                mdata.get(newposition).getImid(),
                String.valueOf(mdata.get(newposition).getRecive_hunter_id()),
                TIMConversationType.C2C);
    }
}
