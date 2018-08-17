package com.yst.onecity.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.bean.ServerBalanceDetailBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.utils.ConstUtils;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 余额明细adapter
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/0/19
 */
public class ServerBalanceDetailsAdapter extends BaseAdapter {

    private List<ServerBalanceDetailBean.ContentBean> fineBeanList = null;
    private Activity context;

    public ServerBalanceDetailsAdapter(List<ServerBalanceDetailBean.ContentBean> fineBeanList, Activity context) {
        this.fineBeanList = fineBeanList;
        this.context = context;
    }

    public void setFineBeanList(List<ServerBalanceDetailBean.ContentBean> fineBeanList) {
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

    public void setData(List<ServerBalanceDetailBean.ContentBean> data) {
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

        ServerBalanceDetailBean.ContentBean contentBean = fineBeanList.get(i);

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String format = decimalFormat.format(contentBean.getMoney());

        int type = contentBean.getType();
        switch (type){
            /**
             * 支付订单
             */
            case Const.INTEGER_0:
                interItemHolder.tvName.setText("余额支付");
                interItemHolder.tvPrice.setText("-" + ConstUtils.changeEmptyStringToZero(format));
                interItemHolder.tvPrice.setTextColor(ContextCompat.getColor(context,R.color.color_333333));
                break;
            /**
             * 余额撤销
             */
            case Const.INTEGER_4:
                interItemHolder.tvName.setText("余额撤销");
                interItemHolder.tvPrice.setText("+" + ConstUtils.changeEmptyStringToZero(format));
                interItemHolder.tvPrice.setTextColor(ContextCompat.getColor(context,R.color.color_FF8A00));
                break;
            /**
             * 新业态余额增加
             */
            case Const.INTEGER_17:
                interItemHolder.tvName.setText("新业态补贴金额");
                interItemHolder.tvPrice.setText("+" + ConstUtils.changeEmptyStringToZero(format));
                interItemHolder.tvPrice.setTextColor(ContextCompat.getColor(context,R.color.color_FF8A00));
                break;
            default:
                break;
        }

        ConstUtils.setTextString(fineBeanList.get(i).getCreated_time(), interItemHolder.tvTime);
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
