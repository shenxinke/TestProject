package com.yst.onecity.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.MyCollectionActivity;
import com.yst.onecity.activity.NewDetailActivity;
import com.yst.onecity.adapter.UserCollectionMessageAdapter;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.bean.CodeMsgBean;
import com.yst.onecity.bean.UserCollectionMessageBean;
import com.yst.onecity.callbacks.AbstractCodeMsgCallback;
import com.yst.onecity.callbacks.AbstractCollectionMessageCallback;
import com.yst.onecity.config.Constants;
import com.yst.onecity.dialog.AbstractDeleteBankCardDialog;
import com.yst.onecity.eventbus.EditChangetEvent;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.xlistview.XListView;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 资讯收藏页面
 *
 * @author lixiangchao
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class CollectionMessageFragment extends BaseFragment implements XListView.IXListViewListener, UserCollectionMessageAdapter.OnClickListener, OnRefreshListener, OnLoadmoreListener {

    @BindView(R.id.txt_collection_all)
    TextView txtCollectionAll;
    @BindView(R.id.txt_collection_cancel)
    TextView txtCollectionCancel;
    @BindView(R.id.txt_collection_delete)
    TextView txtCollectionDelete;
    @BindView(R.id.llayout_collection_handle)
    LinearLayout llayoutCollectionHandle;
    @BindView(R.id.xlist_collection)
    XListView xlistCollection;
    @BindView(R.id.recycler_collection)
    RecyclerView recyclerCollection;
    @BindView(R.id.img_empty_content)
    ImageView imgEmptyContent;
    @BindView(R.id.txt_empty_content)
    TextView txtEmptyContent;
    @BindView(R.id.txt_empty_handle)
    TextView txtEmptyHandle;
    @BindView(R.id.llayout_empty_content)
    LinearLayout llayoutEmptyContent;
    @BindView(R.id.xlist_smartRefreshLayout)
    SmartRefreshLayout xlistSmartRefreshLayout;
    Unbinder unbinder;

    private int page = 1;
    private boolean isCollectionAll = false;
    /**
     * 全选左侧图标切换
     */
    private Drawable mSelectedDrawable;
    private Drawable mNormalDrawable;
    private UserCollectionMessageAdapter mAdapter;
    private List<UserCollectionMessageBean.ContentBean> mdata;
    private AbstractDeleteBankCardDialog deleteBankCardDialog;

    @Override
    public int bindLayout() {
        return R.layout.fragment_collection;
    }

    @Override
    public void initData() {
        mdata = new ArrayList<>();
        mSelectedDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.collection_edit_select_s);
        mSelectedDrawable.setBounds(0, 0, mSelectedDrawable.getMinimumWidth() - 5, mSelectedDrawable.getMinimumHeight() - 5);
        mNormalDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.collection_edit_select_n);
        mNormalDrawable.setBounds(0, 0, mSelectedDrawable.getMinimumWidth() - 5, mSelectedDrawable.getMinimumHeight() - 5);
        txtCollectionAll.setCompoundDrawables(mNormalDrawable, null, null, null);
        xlistSmartRefreshLayout.setVisibility(View.VISIBLE);
        xlistCollection.setVisibility(View.VISIBLE);
        mAdapter = new UserCollectionMessageAdapter(getActivity(), mdata);
        xlistCollection.setPullRefreshEnable(false);
        xlistCollection.setPullLoadEnable(false);
        xlistCollection.setXListViewListener(this);

        xlistSmartRefreshLayout.setOnRefreshListener(this);
        xlistSmartRefreshLayout.setOnLoadmoreListener(this);
        xlistSmartRefreshLayout.setEnableRefresh(true);
        xlistSmartRefreshLayout.setEnableLoadmore(true);
        xlistCollection.setAdapter(mAdapter);
        dismissInfoProgressDialog();
        mAdapter.checkAll(this);

    }


    @OnClick({R.id.txt_collection_cancel, R.id.txt_collection_all, R.id.txt_collection_delete})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.txt_collection_all:
                isCollectionAll = !isCollectionAll;
                txtCollectionAll.setCompoundDrawables(isCollectionAll ? mSelectedDrawable : mNormalDrawable, null, null, null);
                for (UserCollectionMessageBean.ContentBean bean : mdata) {
                    bean.setCheck(isCollectionAll);
                }
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.txt_collection_cancel:
                MyCollectionActivity.instance.setEdit(1);
                isEdit(false);
                for (UserCollectionMessageBean.ContentBean bean : mdata) {
                    bean.setCheck(false);
                }
                EventBus.getDefault().post(new EditChangetEvent());
                break;
            case R.id.txt_collection_delete:

                if (Utils.isClickable()) {
                    if (canDelete()) {
                        deleteBankCardDialog = new AbstractDeleteBankCardDialog(getActivity()) {
                            @Override
                            public void onSureClick() {

                                delCollectionMessage();
                                isEdit(false);
                                EventBus.getDefault().post(new EditChangetEvent());
                            }
                        };
                        deleteBankCardDialog.setCancleStyle(R.drawable.shape_white_10, R.color.black, "取消");
                        deleteBankCardDialog.setSureStyle(R.drawable.shape_gray_180dp_bg, R.color.black, "确认");
                        deleteBankCardDialog.showDialog();

                    } else {
                        ToastUtils.show("请选择您要删除的收藏");
                    }


                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        page = 1;
        mdata.clear();
        if(mAdapter  != null){
            mAdapter.notifyDataSetChanged();
        }
        getUserMessageList();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        page++;
        getUserMessageList();
    }
    private void onLoad() {
        xlistSmartRefreshLayout.finishRefresh(500);
        xlistSmartRefreshLayout.finishLoadmore(500);
        /*xlistCollection.stopRefresh();
        xlistCollection.stopLoadMore();
        xlistCollection.setRefreshTime(Utils.getXListViewTopTime());*/
    }

    /**
     * 获取用户的资讯列表
     */
    private void getUserMessageList() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "page", String.valueOf(page),
                "timestamp", timestamp,
                "id", TianyiApplication.appCommonBean.getId());
        OkHttpUtils
                .post()
                .url(Constants.URL_MYCOLLECTION_LIST)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("timestamp", timestamp)
                .addParams("id", TianyiApplication.appCommonBean.getId())
                .addParams("page", String.valueOf(page))
                .addParams("sign", sign)
                .addParams("client_type", "A")
                .build().execute(new AbstractCollectionMessageCallback() {
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
            public void onResponse(UserCollectionMessageBean response, int id) {
                if (response.getCode() == 1) {
                    if (response.getContent() != null && response.getContent().size() > 0) {
                        mdata.addAll(response.getContent());
                    }
                    mAdapter.notifyDataSetChanged();
                    if (mdata.size() < 1) {
                        showEmptyContent();
                    }
                } else {
                    if (mdata.size() < 1) {
                        showEmptyContent();
                    }
                }
            }
        });
    }

    /**
     * 是否显示编辑框
     *
     * @param isEdit
     */
    public void isEdit(Boolean isEdit) {
        if (mdata.size() < 1) {
            llayoutCollectionHandle.setVisibility(View.GONE);
            return;
        }
        if (isEdit == false) {
            for (UserCollectionMessageBean.ContentBean bean : mdata) {
                bean.setCheck(false);
                txtCollectionAll.setCompoundDrawables(mNormalDrawable, null, null, null);
            }
            xlistSmartRefreshLayout.setEnableRefresh(true);
            xlistSmartRefreshLayout.setEnableLoadmore(true);

        } else {
            xlistSmartRefreshLayout.setEnableRefresh(false);
            xlistSmartRefreshLayout.setEnableLoadmore(false);
        }
        llayoutCollectionHandle.setVisibility(isEdit ? View.VISIBLE : View.GONE);
        for (UserCollectionMessageBean.ContentBean bean : mdata) {
            bean.setShow(isEdit);
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 判断是否有选中的菜单
     *
     * @return
     */
    private boolean canDelete() {
        for (UserCollectionMessageBean.ContentBean bean : mdata) {
            if (bean.isCheck()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 显示内容为空的界面布局
     */
    private void showEmptyContent() {
        llayoutEmptyContent.setVisibility(View.VISIBLE);
        txtEmptyContent.setText("暂无收藏！");
        txtEmptyHandle.setVisibility(View.GONE);
        imgEmptyContent.setImageResource(R.mipmap.shoucang);
        xlistCollection.setVisibility(View.GONE);
    }

    /**
     * 删除选中的收藏资讯
     */
    private void delCollectionMessage() {
        String consultationIds = "";
        String timestamp = SignUtils.getTimeStamp();

        for (int i = 0; i < mdata.size(); i++) {
            if (mdata.get(i).isCheck()) {
                consultationIds += (mdata.get(i).getId() + ",");
            }
        }
        consultationIds = consultationIds.substring(0, consultationIds.length() - 1);
        String sign = Utils.getSign(
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "timestamp", timestamp,
                "id", TianyiApplication.appCommonBean.getId(),
                "type", String.valueOf(0),
                "consultationIds", consultationIds);
        OkHttpUtils
                .post()
                .url(Constants.URL_DEL_COLLECTION_CONTENT)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("type", String.valueOf(0))
                .addParams("consultationIds", consultationIds)
                .addParams("id", TianyiApplication.appCommonBean.getId())
                .addParams("timestamp", timestamp)
                .addParams("sign", sign)
                .addParams("client_type", "A")
                .build().execute(new AbstractCodeMsgCallback() {
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
            public void onResponse(CodeMsgBean response, int id) {
                if (response.getCode() == 1) {
                    page = 1;
                    mdata.clear();
                    mAdapter.notifyDataSetChanged();
                    getUserMessageList();
                }
                ToastUtils.show(response.getMsg());

            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            page = 1;
            mdata.clear();
            mAdapter.notifyDataSetChanged();
            getUserMessageList();
            isEdit(false);
            EventBus.getDefault().post(new EditChangetEvent());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        mdata.clear();
        mAdapter.notifyDataSetChanged();
        isEdit(false);
        getUserMessageList();
        EventBus.getDefault().post(new EditChangetEvent());
    }

    @Override
    public void checkCollectionAll() {
        if (canDeleteAll()) {
            txtCollectionAll.setCompoundDrawables(mSelectedDrawable, null, null, null);
        } else {
            txtCollectionAll.setCompoundDrawables(mNormalDrawable, null, null, null);
        }
    }

    @Override
    public void onJumpPage(String id) {
        if (llayoutCollectionHandle.getVisibility() == View.GONE) {
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            JumpIntent.jump(getActivity(), NewDetailActivity.class, bundle);
        }


    }

    private boolean canDeleteAll() {
        boolean flag = false;
        int index = 0;
        for (int i = 0; i < mdata.size(); i++) {
            if (mdata.get(i).isCheck() == true) {
                flag = true;
                index++;
            }
            if (mdata.get(i).isCheck() == false) {
                flag = false;
            }
        }
        if (index == mdata.size()) {
            return flag;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
