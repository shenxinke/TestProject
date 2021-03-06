package com.yst.onecity.adapter.malladapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.bean.mall.ClassfityBean;
import com.yst.onecity.utils.CircleTransFrom;
import com.yst.onecity.utils.MyLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商城分类gridview的适配器
 *
 * @author wuxiaofang
 * @version 3.1.0
 * @date 2018/3/20
 */

public class GridviewAdapter extends BaseAdapter {
    private Context context;
    private int index;
    private int pageSize;
    private List<ClassfityBean.ContentBean.ProductTypeBean> classList;


    public GridviewAdapter(Context context, int index, int pageSize, List<ClassfityBean.ContentBean.ProductTypeBean> classList) {
        this.context = context;
        this.index = index;
        this.pageSize = pageSize;
        this.classList = classList;
    }


    @Override
    public int getCount() {
        MyLog.e("sss", "-classList: " + classList.size());
        MyLog.e("sss", "-(index + 1) * pageSize:" + (index + 1) * pageSize + "-" + pageSize + "-(classList.size() - index * pageSize)" + (classList.size() - index * pageSize));
        return classList.size() > (index + 1) * pageSize ? pageSize : (classList.size() - index * pageSize);
    }

    @Override
    public Object getItem(int position) {
        return classList.get(position + index * pageSize);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_grid_mall, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        /**
         * 在给View绑定显示的数据时，计算正确的position = position + mIndex * mPageSize，
         */
        int pos = position + index * pageSize;
        viewHolder.tvNameClassfity.setText(classList.get(pos).getClassifyName());
        String address = classList.get(pos).getAddress();
        MyLog.e("sss", "address: " + classList.get(pos).getAddress());
        if (TextUtils.isEmpty(address)) {
            Glide.with(context).load(R.mipmap.iv_default_class).transform(new CircleTransFrom(context)).error(R.mipmap.iv_default_class).into(viewHolder.imgClassfity);
        } else {
            Glide.with(context).load(address).transform(new CircleTransFrom(context)).error(R.mipmap.iv_default_class).into(viewHolder.imgClassfity);
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.img_classfity)
        ImageView imgClassfity;
        @BindView(R.id.tv_name_classfity)
        TextView tvNameClassfity;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
