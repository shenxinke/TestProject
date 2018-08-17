package com.yst.onecity.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.bean.AccountIntegralBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.utils.ConstUtils;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 会员天天奖余额明细adapter
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/0/19
 */
public class DayDayMoneyAdapter extends BaseAdapter {

    private List<AccountIntegralBean.ContentBean> fineBeanList = null;
    private Activity context;

    public DayDayMoneyAdapter(List<AccountIntegralBean.ContentBean> fineBeanList, Activity context) {
        this.fineBeanList = fineBeanList;
        this.context = context;
    }

    public void setFineBeanList(List<AccountIntegralBean.ContentBean> fineBeanList) {
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

    public void setData(List<AccountIntegralBean.ContentBean> data) {
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        InterItemHolder interItemHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_item_item_balance_detail, null);
            interItemHolder = new InterItemHolder(convertView);
            convertView.setTag(interItemHolder);
        } else {
            interItemHolder = (InterItemHolder) convertView.getTag();
        }

        AccountIntegralBean.ContentBean contentBean = fineBeanList.get(position);

        if (context.getResources().getString(R.string.zmjf).equals(contentBean.getIntegralType())) {
            interItemHolder.ivState.setImageResource(R.drawable.account_zmjfs);
        } else {
            interItemHolder.ivState.setImageResource(R.drawable.account_zhifu);
        }

        float m = Float.parseFloat(ConstUtils.changeEmptyStringToZero(contentBean.getIntegralValue()));
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String format = decimalFormat.format(m);

        if(Const.INTEGER_1 == contentBean.getScoreType()){
            interItemHolder.tvPrice.setText("+" + ConstUtils.changeEmptyStringToZero(format));
            interItemHolder.tvPrice.setTextColor(ContextCompat.getColor(context,R.color.color_FF8A00));
        }else {
            interItemHolder.tvPrice.setText("-" + ConstUtils.changeEmptyStringToZero(format));
            interItemHolder.tvPrice.setTextColor(ContextCompat.getColor(context,R.color.color_333333));
        }

        ConstUtils.setTextString(contentBean.getIntegralTime(), interItemHolder.tvTime);
        ConstUtils.setTextString(contentBean.getIntegralType(),interItemHolder.tvName);

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
