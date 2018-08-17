package com.yst.onecity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.yst.onecity.R;
import com.yst.onecity.bean.InformationBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.utils.Utils;

import java.util.List;

/**
 * 物流信息适配器
 *
 * @author Shenxinke
 * @version 4.0.0
 * @data 2018/3/30
 */

public class TimeLineAdapter extends BaseAdapter {
    private List<InformationBean.ContentBean.LogisticMessageBean> mlist=null;
    private LayoutInflater minflater;
    private String stateValue;
    private SparseArray<View> mHashMap = new SparseArray<>();

    public TimeLineAdapter ( Context context, List<InformationBean.ContentBean.LogisticMessageBean> list ,String stateValue ){
        minflater = LayoutInflater.from(context);
        mlist = list;
        this.stateValue = stateValue;
    }
    @Override
    public int getCount() {
        if( null!=mlist){
            return mlist.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if( null!=mlist){
            return mlist.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold viewHold;
        if(mHashMap.get(position) == null){
            viewHold = new ViewHold();
            convertView = minflater.inflate(R.layout.time_line, null);
            viewHold.imageView1 = (ImageView)convertView.findViewById(R.id.mgView_logistic_tracking_status);
            viewHold.textView1 = (TextView)convertView.findViewById(R.id.tv_logistic_tracking_address);
            viewHold.textView2 = (TextView)convertView.findViewById(R.id.tv_logistic_tracking_time);
            viewHold.tvMonth = (TextView)convertView.findViewById(R.id.tv_address_month);
            viewHold.tvHour = (TextView)convertView.findViewById(R.id.tv_address_hour);
            viewHold.line1 = (LinearLayout) convertView.findViewById(R.id.View_logistic_tracking_line1);
            convertView.setTag(viewHold);
            mHashMap.put(position, convertView);
        }else {
            convertView = mHashMap.get(position);
            viewHold = (ViewHold)convertView.getTag( );
        }
        int count = getCount();
        Log.e("sss---", "count:"+count+"position:"+position+"Utils.signString(stateValue):"+Utils.signString(stateValue));
        if( position==0 ){
            if(Const.CONS_STR_YIQIANSHOU.equals(Utils.signString(stateValue))){
                viewHold.imageView1.setImageResource(R.mipmap.over);
                viewHold.textView1.setText( stateValue);
                viewHold.tvMonth.setTextColor(Color.parseColor("#FA871C"));
                viewHold.tvHour.setTextColor(Color.parseColor("#FA871C"));
                viewHold.textView1.setTextColor(Color.parseColor("#FA871C"));
                viewHold.textView2.setTextColor(Color.parseColor("#FA871C"));
            }else {
                viewHold.imageView1.setImageResource(R.mipmap.course);
                viewHold.tvMonth.setTextColor(Color.parseColor("#FA871C"));
                viewHold.tvHour.setTextColor(Color.parseColor("#FA871C"));
                viewHold.textView1.setTextColor(Color.parseColor("#FA871C"));
                viewHold.textView2.setTextColor(Color.parseColor("#FA871C"));
            }
        }else{
            viewHold.imageView1.setImageResource(R.mipmap.start);
            viewHold.tvMonth.setTextSize(12);
            viewHold.tvHour.setTextSize(12);
        }
        viewHold.textView2.setText( mlist.get(position).getContext());
        /**
         * 截取返回的日期
         */
        String time = mlist.get(position).getTime();
        String timeMonth = time.substring(5, 10);
        String timeHour = time.substring(11, 19);
        viewHold.tvMonth.setText( timeMonth);
        viewHold.tvHour.setText( timeHour);
        return convertView;
    }


    private final static class ViewHold{
        ImageView imageView1;
        LinearLayout line1;
        TextView  textView1;
        TextView textView2;
        TextView tvMonth;
        TextView tvHour;
    }
}