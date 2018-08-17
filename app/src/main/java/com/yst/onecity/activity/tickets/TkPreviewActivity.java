package com.yst.onecity.activity.tickets;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.adapter.AbstractCommonAdapter;
import com.yst.onecity.adapter.CommonViewHolder;
import com.yst.onecity.adapter.TicketsInfoAdapter;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.tickets.TableTicketsBean;
import com.yst.onecity.callbacks.AbstractTableTicketsBeanCallback;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.CustomAlertDialogUtil;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.NoScrollGridView;
import com.yst.onecity.view.NoScrollListView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 奖券预览页面
 *
 * @author WangJingWei
 * @version 4.0.0
 * @date 2018/3/21.
 */
public class TkPreviewActivity extends BaseActivity {

    @BindView(R.id.activity_title_right_img)
    ImageView rightImageView;
    @BindView(R.id.activity_title_tv)
    TextView title;
    @BindView(R.id.activity_back_iv)
    ImageView activityBackIv;
    @BindView(R.id.lv_list)
    NoScrollListView lvList;
    @BindView(R.id.lv_detail_list)
    NoScrollListView lvDetailList;

    private AbstractCommonAdapter<TableTicketsBean.ContentBean.CurrentPeriodBean>   mTicketsCommonAdapter;
    private AbstractCommonAdapter<TableTicketsBean.ContentBean.RecordBean> mTicketsDetailCommonAdapter;

    private List<TableTicketsBean.ContentBean.CurrentPeriodBean> mTicketsTopList = new ArrayList<>();
    private List<TableTicketsBean.ContentBean.RecordBean> mTicketsBtList = new ArrayList<>();
    private String lotteryNum = "";

    @Override
    public int bindLayout() {
        return R.layout.activity_tickets_preview;
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            lotteryNum = bundle.getString("lotteryNum");
        }

        rightImageView.setVisibility(View.VISIBLE);
        rightImageView.setImageResource(R.mipmap.question);
        title.setText(getResources().getString(R.string.tickets_preview));
        requestTicketsListInfo();
    }

    @OnClick(R.id.activity_title_right_img)
    public void toPopQuestion() {
        CustomAlertDialogUtil.remindTicketsRule(this);
    }

    @OnClick(R.id.activity_back_iv)
    public void toBack() {
        finish();
    }

    /**
     * 适配奖项表单数据
     */
    private void setTableTicketsData() {

        mTicketsCommonAdapter = new AbstractCommonAdapter<TableTicketsBean.ContentBean.CurrentPeriodBean>(TianyiApplication.getInstance(), mTicketsTopList, R.layout.item_tickets_list_row) {

            @Override
            public void convert(CommonViewHolder holder, int position, TableTicketsBean.ContentBean.CurrentPeriodBean item) {
                holder.setText(R.id.tv_jiang, Utils.getStringNoEmpty(item.getName()));
                holder.setText(R.id.tv_money, Utils.changeEmptyStringToZero(item.getPrice()));
                holder.setText(R.id.tv_num, Utils.changeEmptyStringToZero(item.getGiveOutNum()));
                holder.setText(R.id.tv_gailv, Utils.changeEmptyStringToZero(item.getScale()));
            }
        };
        lvList.setAdapter(mTicketsCommonAdapter);
    }

    /**
     * 适配获奖详情信息数据
     */
    private void setTicketsListDetail() {

        mTicketsDetailCommonAdapter = new AbstractCommonAdapter<TableTicketsBean.ContentBean.RecordBean>(TianyiApplication.getInstance(), mTicketsBtList, R.layout.item_tickets_list_detail_row) {
            @Override
            public void convert(CommonViewHolder holder, int position, final TableTicketsBean.ContentBean.RecordBean item) {
                List<TableTicketsBean.ContentBean.RecordBean.NameListBean> nameList = item.getNameList();

                NoScrollGridView gridview = holder.getView(R.id.gridview);
                ImageView imageView = holder.getView(R.id.img_tickets);

                int awards = Integer.parseInt(ConstUtils.changeEmptyStringToZero(item.getAwards()));
                switch (awards) {
                    case 0:
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setImageResource(R.drawable.tickets_one);
                        break;
                    case 1:
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setImageResource(R.drawable.tickets_two);
                        break;
                    case 2:
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setImageResource(R.drawable.tickets_three);
                        break;
                    default:
                        imageView.setVisibility(View.INVISIBLE);
                        break;
                }

                holder.setText(R.id.tv_tickets_name, Utils.getStringNoEmpty(item.getName()));

                holder.setText(R.id.tv_tickets_money, "奖励金额: " + Utils.getStringNoEmpty(item.getPrice()) + "元");

                gridview.setAdapter(new TicketsInfoAdapter(TkPreviewActivity.this, nameList, awards));

                holder.setButtonListener(R.id.ll_awards, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("name", Utils.getStringNoEmpty(item.getName()));
                        bundle.putString("awards", Utils.changeEmptyStringToZero(item.getAwards()));
                        JumpIntent.jump(TkPreviewActivity.this, TkPreviewDetailActivity.class, bundle);
                    }
                });
            }
        };
        lvDetailList.setAdapter(mTicketsDetailCommonAdapter);
    }

    /**
     * 获取奖项列表信息
     */
    private void requestTicketsListInfo() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "orderNo", lotteryNum,
                "timestamp", timestamp
        );

        if (TextUtils.isEmpty(sign)) {
            return;
        }

        OkHttpUtils
                .post()
                .url(Constants.TICKETS_PREVIEWS)
                .addParams("orderNo", lotteryNum)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("sign", sign)
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .build().execute(new AbstractTableTicketsBeanCallback() {


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
            public void onResponse(TableTicketsBean tableTicketsBean, int id) {
                if (tableTicketsBean != null && tableTicketsBean.getCode() == 1 && tableTicketsBean.getContent() != null) {
                    mTicketsTopList = tableTicketsBean.getContent().getCurrentPeriod();
                    mTicketsBtList = tableTicketsBean.getContent().getRecord();
                }
                setTableTicketsData();
                setTicketsListDetail();
            }
        });
    }

}
