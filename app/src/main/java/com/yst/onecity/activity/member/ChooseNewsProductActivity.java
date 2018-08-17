package com.yst.onecity.activity.member;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.adapter.AbstractCommonAdapter;
import com.yst.onecity.adapter.CommonViewHolder;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.information.ProductBean;
import com.yst.onecity.bean.information.ProductContentBean;
import com.yst.onecity.callbacks.AbstractProductCallback;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.xlistview.XListView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.yst.onecity.config.Constants.ids;

/**
 * @author Chenxiaowei
 *         2017/9/19
 *         添加商品
 */

public class ChooseNewsProductActivity extends BaseActivity implements XListView.IXListViewListener {

    @BindView(R.id.listView)
    XListView listView;
    @BindView(R.id.et_input_content)
    EditText etInputContent;
    private List<ProductBean> data = new ArrayList<>();
    private AbstractCommonAdapter<ProductBean> adapter;
    private int pageSize = 10, pageNum = 1;
    private Handler handler = new Handler();

    @Override
    public int bindLayout() {
        return R.layout.activity_news_product;
    }

    @Override
    public void initData() {
        initTitleBar("选择商品");
        listView.setPullRefreshEnable(true);
        listView.setPullLoadEnable(true);
        listView.autoRefresh();
        requestProductListNetWork(true, pageNum, "");
    }

    /**
     * 获取商品列表
     */
    private void requestProductListNetWork(final boolean isRefresh, final int page, String name) {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "page", String.valueOf(page),
                "hunter_id", TianyiApplication.appCommonBean.getHunter_id(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "name", name,
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.PRODUCT_LIST)
                .addParams("page", String.valueOf(page))
                .addParams("hunter_id", TianyiApplication.appCommonBean.getHunter_id())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("name", name)
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractProductCallback() {

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onLoad();
                    }
                }, 1000);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.app_on_request_error_msg));
            }

            @Override
            public void onResponse(ProductContentBean response, int id) {
                if (response.getCode() == 1) {
                    if (isRefresh) {
                        adapter.onRefresh(response.getContent());
                        data = response.getContent();
                    } else {
                        adapter.addData(response.getContent());
                        data.addAll(response.getContent());
                    }
                } else {
                    ToastUtils.show(response.getMsg());
                }
            }
        });
    }

    @Override
    public void initControll() {
        adapter = new AbstractCommonAdapter<ProductBean>(context, data, R.layout.item_add_product) {
            @Override
            public void convert(CommonViewHolder holder, int position, ProductBean item) {
                Glide.with(context).load(item.getImage()).placeholder(R.mipmap.img_default_p)
                        .error(R.mipmap.img_default_p).into((ImageView) holder.getView(R.id.iv_product));
                holder.setText(R.id.tv_name, ConstUtils.getStringNoEmpty(item.getName()));
                holder.setText(R.id.tv_price, "￥" + item.getSalePrice());
                holder.setText(R.id.tv_sale_count, "销量：" + item.getSaleNum());
                holder.setText(R.id.tv_stock, "库存：" + item.getNum());
            }
        };
        listView.setAdapter(adapter);
    }

    @Override
    public void setListener() {
        listView.setXListViewListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (ids.size() == 0) {
                    ids.add(data.get(position - 1).getId());
                } else {
                    for (int i = 0; i < ids.size(); i++) {
                        if (ids.get(i).equals(data.get(position - 1).getId())) {
                            ToastUtils.show("不能重复添加商品");
                            return;
                        }
                    }
                }
                ids.add(data.get(position - 1).getId());
                Intent mIntent = new Intent();
                mIntent.putExtra("data", data.get(position - 1));
                ChooseNewsProductActivity.this.setResult(RESULT_OK, mIntent);
                ChooseNewsProductActivity.this.finish();
            }
        });
    }

    @OnClick(R.id.tv_search)
    public void onViewClicked() {
        if (TextUtils.isEmpty(etInputContent.getText().toString().trim())) {
            ToastUtils.show("请输入商品名称");
            return;
        }
        requestProductListNetWork(true, 1, etInputContent.getText().toString());
    }

    @Override
    public void onRefresh() {
        pageNum = 1;
        requestProductListNetWork(true, pageNum, "");
    }

    @Override
    public void onLoadMore() {
        pageNum++;
        requestProductListNetWork(false, pageNum, "");
    }

    /**
     * 用来停止列表刷新的
     **/
    private void onLoad() {
        if (null == listView) {
            return;
        }
        listView.stopLoadMore();
        listView.stopRefresh();
        listView.setRefreshTime(Utils.getXListViewTopTime());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
