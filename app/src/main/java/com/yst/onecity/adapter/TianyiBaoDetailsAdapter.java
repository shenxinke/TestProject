package com.yst.onecity.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.bean.BalanceDetailBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.utils.ConstUtils;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 天易宝余额明细adapter
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/0/19
 */
public class TianyiBaoDetailsAdapter extends BaseAdapter {


    private List<BalanceDetailBean.ContentBean.DatasBean> fineBeanList = null;
    private Activity context;

    public TianyiBaoDetailsAdapter(List<BalanceDetailBean.ContentBean.DatasBean> fineBeanList, Activity context) {
        this.fineBeanList = fineBeanList;
        this.context = context;
    }

    public void setFineBeanList(List<BalanceDetailBean.ContentBean.DatasBean> fineBeanList) {
        if (fineBeanList != null) {
            this.fineBeanList = fineBeanList;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        if (fineBeanList.size() != 0) {
            return fineBeanList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return fineBeanList.get(position);
    }

    public void setData(List<BalanceDetailBean.ContentBean.DatasBean> data) {
        if (fineBeanList != null) {
            this.fineBeanList = data;
            this.notifyDataSetChanged();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        InterItemHolder interItemHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_item_item_balance_detail, null);
            interItemHolder = new InterItemHolder(convertView);
            convertView.setTag(interItemHolder);
        } else {
            interItemHolder = (InterItemHolder) convertView.getTag();
        }
        if (Const.CONS_STR_TIXINA.equals(fineBeanList.get(i).getSourceType())) {
            //奖励兑换
            ConstUtils.setTextString("提现", interItemHolder.tvName);
            interItemHolder.ivState.setImageResource(R.mipmap.tixian);

        } else if (Const.CONS_STR_TIANTIANJIANG.equals(fineBeanList.get(i).getSourceType())) {
            //天天奖
            ConstUtils.setTextString("天天奖", interItemHolder.tvName);
            interItemHolder.ivState.setImageResource(R.mipmap.tiantianjiang);
        }
        ConstUtils.setTextString(fineBeanList.get(i).getTradeTime(), interItemHolder.tvTime);
        DecimalFormat decimalFormat = new DecimalFormat("0.0000");
        String format = decimalFormat.format(fineBeanList.get(i).getScore());
        ConstUtils.setTextString(format, interItemHolder.tvPrice);
        return convertView;
    }


    class InterItemHolder {
        @BindView(R.id.iv_state)
        ImageView ivState;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        InterItemHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
