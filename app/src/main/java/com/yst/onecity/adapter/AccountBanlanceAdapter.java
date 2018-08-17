package com.yst.onecity.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.bean.tickets.AccountBalanceBean;
import com.yst.onecity.utils.ConstUtils;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 专员佣金明细适配器
 *
 * @author WangJingWei
 * @version 4.0.0
 * @date 2018/3/26.
 */
public class AccountBanlanceAdapter extends BaseAdapter{

    private List<AccountBalanceBean.ContentBean> fineBeanList = null;
    private Activity context;

    public AccountBanlanceAdapter(List<AccountBalanceBean.ContentBean> fineBeanList, Activity context) {
        this.fineBeanList = fineBeanList;
        this.context = context;
    }

    public void setFineBeanList(List<AccountBalanceBean.ContentBean> fineBeanList) {
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

    public void setData(List<AccountBalanceBean.ContentBean> data) {
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
        int type = fineBeanList.get(i).getType();
        int status = fineBeanList.get(i).getStatus();

        ConstUtils.setTypeText(type,status,interItemHolder.tvName);
//        ConstUtils.setTypeImage(type,interItemHolder.ivState);

        ConstUtils.setTextString(fineBeanList.get(i).getCreated_time(), interItemHolder.tvTime);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String format = decimalFormat.format(fineBeanList.get(i).getMoney());

       ConstUtils.setTypeSymbol(type,status,format,interItemHolder.tvPrice,context);

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
