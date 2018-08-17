package com.yst.onecity.fragment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.bean.product.ProductPicListBean;
import com.yst.onecity.callbacks.AbstractProductPicCallBack;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.utils.WindowUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 商品图片详情
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class ProductDetailFragment extends BaseFragment {

    private List<ProductPicListBean.ContentBean> list;
    private ProductPicAdapter adapter;

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.tv_empty)
    TextView empty;
    private String productId;

    @Override
    public int bindLayout() {
        return R.layout.fragment_product_detail;
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            productId = getArguments().getString("productId");
        }

        if (list == null) {
            list = new ArrayList<>();
        }
        getData();

        adapter = new ProductPicAdapter();
        listView.setAdapter(adapter);

    }

    /**
     * 获取商品详情
     */
    private void getData() {
        if (list != null) {
            list.clear();
        }
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("timestamp", timestamp, "productId", productId);

        OkHttpUtils.post().url(Constants.PRODUCT_DETAIL_PIC)
                .addParams("productId", productId)
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractProductPicCallBack() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show("网络请求失败，请重试");
                empty.setVisibility(View.VISIBLE);
            }

            @Override
            public void onResponse(ProductPicListBean response, int id) {
                if (response.getCode() == 1) {
                    if (response.getContent() == null) {
                        return;
                    }
                    list.addAll(response.getContent());
                    if (response.getContent().size() < 1) {
                        empty.setVisibility(View.VISIBLE);
                    } else {
                        empty.setVisibility(View.GONE);
                    }
                } else {
                    empty.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }


    class ProductPicAdapter extends BaseAdapter {

        public static final int TYPE_CONTENT = 0;
        public static final int TYPE_PIC = 1;

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            switch (list.get(position).getType()) {
                case TYPE_CONTENT:
                    return TYPE_CONTENT;
                case TYPE_PIC:
                    return TYPE_PIC;
                default:
                    break;
            }
            return Integer.MAX_VALUE;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = null;
            switch (getItemViewType(position)) {
                case TYPE_CONTENT:
                    ContentHolder contentHolder = null;
                    if (convertView == null) {
                        convertView = LayoutInflater.from(context).inflate(R.layout.item_publish_content, null);
                        contentHolder = new ContentHolder(convertView);
                        convertView.setTag(contentHolder);
                    } else {
                        contentHolder = (ContentHolder) convertView.getTag();
                    }
                    contentHolder.content.setText(list.get(position).getContent());
                    break;
                case TYPE_PIC:
                    ViewHolder holder = null;
                    if (convertView == null) {
                        convertView = LayoutInflater.from(context).inflate(R.layout.item_product_pic, null);
                        holder = new ViewHolder(convertView);
                        convertView.setTag(holder);
                    } else {
                        holder = (ViewHolder) convertView.getTag();
                    }

                    ViewGroup.LayoutParams layoutParams = holder.productPic.getLayoutParams();
                    layoutParams.height = WindowUtils.getScreenWidth((Activity) context);
                    holder.productPic.setLayoutParams(layoutParams);
                    MyLog.e("sss","--list.get(position).getContent: "+list.get(position).getContent());

                    try {
                        Glide.with(TianyiApplication.getContext()).
                                load(list.get(position).getContent())
                                .thumbnail(0.1f)
                                .placeholder(R.mipmap.img_default_bg).
                                error(R.mipmap.img_default_bg).diskCacheStrategy(DiskCacheStrategy.SOURCE).
                                crossFade().into(holder.productPic);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }

            return convertView;
        }

        class ContentHolder {
            @BindView(R.id.content)
            TextView content;

            ContentHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

        class ViewHolder {
            @BindView(R.id.product_pic)
            ImageView productPic;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

}
