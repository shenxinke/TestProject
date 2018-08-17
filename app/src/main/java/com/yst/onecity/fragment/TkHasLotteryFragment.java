package com.yst.onecity.fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.adapter.AbstractCommonAdapter;
import com.yst.onecity.adapter.CommonViewHolder;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.bean.tickets.TicketsBean;
import com.yst.onecity.bean.tickets.TicketsContent;
import com.yst.onecity.callbacks.AbstractTicketsListCallback;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.SoftKeyBoardListener;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 我的奖券（已开奖订单页面）
 *
 * @author WangJingWei
 * @version 4.0.0
 * @date 2018/3/20.
 */
public class TkHasLotteryFragment extends BaseFragment {
    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.rela_search)
    RelativeLayout relaSearch;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.mSmartRefresh)
    SmartRefreshLayout mSmartRefresh;
    @BindView(R.id.img_empty)
    ImageView imgEmpty;

    private int pageSize = 1;
    private int rows = 10;
    private List<TicketsContent> mData = new ArrayList<>();
    private AbstractCommonAdapter<TicketsContent> mCommonAdapter;
    private String searchContent;
    private boolean isSeachContent = false;


    @Override
    public int bindLayout() {
        return R.layout.fragment_has_lottery;
    }

    @Override
    public void initData() {
        initListener();
        initTicketsData();
        requestTicketsList();
    }

    /**
     * 实例化当前页面监听事件
     */
    private void initListener() {
        mSmartRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageSize++;
                requestTicketsList();
                mSmartRefresh.finishLoadmore(500);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mData.clear();
                pageSize = 1;
                requestTicketsList();
                mSmartRefresh.finishRefresh(500);
            }
        });

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchContent = editable.toString();
            }
        });

        editSearch.setOnEditorActionListener((v, actionId, event) -> {
            searchContent = v.getText().toString();
            if ("".equals(searchContent = searchContent.replaceAll("", ""))) {
                ToastUtils.show("请填写搜索内容");
                return false;
            }
            isSeachContent = true;
            mData.clear();
            mCommonAdapter.notifyDataSetChanged();
            requestTicketsList();
            return false;
        });

        SoftKeyBoardListener.setListener(getActivity(), new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                tvCancle.setVisibility(View.VISIBLE);
            }

            @Override
            public void keyBoardHide(int height) {
                if (TextUtils.isEmpty(searchContent)) {
                    tvCancle.setVisibility(View.GONE);
                }
            }
        });

        imgSearch.setOnClickListener(v -> {
            if (TextUtils.isEmpty(searchContent)) {
                ToastUtils.show("请填写搜索内容");
                return;
            }
            isSeachContent = true;
            mData.clear();
            mCommonAdapter.notifyDataSetChanged();
            requestTicketsList();
        });

        tvCancle.setOnClickListener(v -> {
            if (editSearch != null) {
                editSearch.getText().clear();
                searchContent = "";
                mData.clear();
                mCommonAdapter.notifyDataSetChanged();
                requestTicketsList();
                tvCancle.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 设置奖券数据
     */
    private void initTicketsData() {

        mCommonAdapter = new AbstractCommonAdapter<TicketsContent>(TianyiApplication.instance, mData, R.layout.item_tickets_has_lottery) {
            @Override
            public void convert(CommonViewHolder holder, int position, TicketsContent item) {
                LinearLayout llBg = holder.getView(R.id.ll_bg);
                if (Integer.parseInt(Utils.changeEmptyStringToZero(item.getLottery_price())) > 0) {
                    llBg.setBackgroundResource(R.drawable.img_ticket_has_left);
                    holder.setText(R.id.tv_award, Utils.getStringNoEmpty(item.getTkAward()));
                } else {
                    llBg.setBackgroundResource(R.drawable.img_ticket_has_zero);
                    holder.setText(R.id.tv_award, "谢谢惠顾");
                }
                holder.setText(R.id.tv_ticket_num, "劵号: " + Utils.getStringNoEmpty(item.getNum()));
                holder.setText(R.id.tv_money, Utils.changeEmptyStringToZero(item.getLottery_price()));
            }
        };
        listview.setAdapter(mCommonAdapter);
    }

    /**
     * 请求某一订单下未开奖的奖券
     */
    private void requestTicketsList() {
        String timestamp = SignUtils.getTimeStamp();
        String sign;

        if (isSeachContent) {
            sign = Utils.getSign(
                    "phone", TianyiApplication.appCommonBean.getPhone(),
                    "uuid", TianyiApplication.appCommonBean.getUuid(),
                    "rows", String.valueOf(rows),
                    "timestamp", timestamp,
                    "lotteryName", searchContent,
                    "page", String.valueOf(pageSize));
        } else {
            sign = Utils.getSign(
                    "phone", TianyiApplication.appCommonBean.getPhone(),
                    "uuid", TianyiApplication.appCommonBean.getUuid(),
                    "rows", String.valueOf(rows),
                    "timestamp", timestamp,
                    "page", String.valueOf(pageSize));
        }
        if (TextUtils.isEmpty(sign)) {
            return;
        }

        PostFormBuilder mPostFormBuilder = OkHttpUtils.post().url(Constants.TICKETS_HAS_LOTTERY_RP_LIST);
        if (isSeachContent) {
            mPostFormBuilder.addParams("lotteryName", searchContent);
        }
        mPostFormBuilder.url(Constants.TICKETS_HAS_LOTTERY_RP_LIST);
        mPostFormBuilder.addParams("page", String.valueOf(pageSize));
        mPostFormBuilder.addParams("rows", String.valueOf(rows));
        mPostFormBuilder.addParams("phone", TianyiApplication.appCommonBean.getPhone());
        mPostFormBuilder.addParams("uuid", TianyiApplication.appCommonBean.getUuid());
        mPostFormBuilder.addParams("sign", sign);
        mPostFormBuilder.addParams("timestamp", timestamp);
        mPostFormBuilder.addParams("client_type", "A");
        mPostFormBuilder.build().execute(new AbstractTicketsListCallback() {

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
            public void onResponse(TicketsBean response, int id) {
                if (response.getCode() == 1) {
                    if (response.getContent() != null && response.getContent().size() > 0) {
                        mData.addAll(response.getContent());
                    }

                    if (mData.size() > 0) {
                        imgEmpty.setVisibility(View.GONE);
                    } else {
                        imgEmpty.setVisibility(View.VISIBLE);
                    }

                    mCommonAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
