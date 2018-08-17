package com.yst.onecity.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.adapter.OrderProductAdapter;
import com.yst.onecity.bean.order.MemberOnLineOrderBean;
import com.yst.onecity.bean.order.SonBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.view.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 线上订单的viewhold
 *
 * @author Shenxinke
 * @version 4.0.2
 * @data 2018/4/24
 */
public class OnLineOrderViewHolder extends AbstractTypeViewHolder<MemberOnLineOrderBean.OnLineBean> {
    @BindView(R.id.iv_shop_head)
    public RoundedImageView ivShopImage;
    @BindView(R.id.tv_shop_name)
    public TextView tvShopName;
    @BindView(R.id.tv_order_status)
    public TextView tvOrderStates;
    @BindView(R.id.tv_bt_state)
    public TextView tvBtState;
    @BindView(R.id.tv_info)
    public TextView tvInfo;
    @BindView(R.id.im_kuaijiezhifu)
    public ImageView imKuaiJieZhiFu;
    @BindView(R.id.im_yuezhifu)
    public ImageView imYuEZhiFu;
    @BindView(R.id.im_jifenzhifu)
    public ImageView imJiFenZhiFu;
    @BindView(R.id.tv_shifukuan)
    public TextView tvShifukuan;
    @BindView(R.id.im_jifen)
    public ImageView imJifen;
    @BindView(R.id.tv_shop_price)
    public TextView tvShopPrice;
    @BindView(R.id.tv_yunfei)
    public TextView tvYunfei;
    @BindView(R.id.tv_shop_jifen)
    public TextView tvShopJifen;
    @BindView(R.id.tv_shop_huo)
    public TextView tvHuo;
    @BindView(R.id.ll_item)
    public LinearLayout llItem;
    @BindView(R.id.ll_root)
    public LinearLayout llRoot;
    public int state;

    public OnLineOrderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindHolder(final MemberOnLineOrderBean.OnLineBean item) {
        List<SonBean> sonData = item.getSon();
        final int commentStatus = item.getCommentStatus();
        int status = sonData.get(0).getStatus();
        state = status;
        //0待付款 2待发货（已付款） 3待收货 4已评价  6已撤销 7已收货（待评价）8已完成 -1全部
        switch (status) {
            case Const.INTEGER_0:
                imKuaiJieZhiFu.setVisibility(View.GONE);
                imJiFenZhiFu.setVisibility(View.GONE);
                imYuEZhiFu.setVisibility(View.GONE);
                tvOrderStates.setText("待付款");
                tvBtState.setText("付\t\t款");
                tvBtState.setVisibility(View.VISIBLE);
                tvShopPrice.setVisibility(View.VISIBLE);
                tvBtState.setTextColor(0xffffffff);
                tvBtState.setEnabled(true);
                tvBtState.setBackgroundResource(R.drawable.shape_order_bg);
                break;
            case Const.INTEGER_2:
                tvBtState.setVisibility(View.GONE);
                tvOrderStates.setText("待发货");
                break;
            case Const.INTEGER_3:
                tvOrderStates.setText("待收货");
                tvBtState.setVisibility(View.VISIBLE);
                tvBtState.setText("确认收货");
                tvBtState.setTextColor(0xffffffff);
                tvBtState.setEnabled(true);
                tvBtState.setBackgroundResource(R.drawable.shape_order_bg);
                break;
            case Const.INTEGER_6:
                tvBtState.setVisibility(View.VISIBLE);
                tvOrderStates.setText("已撤销");
                tvBtState.setText("已撤销");
                tvBtState.setTextColor(0xff999999);
                tvBtState.setEnabled(false);
                tvBtState.setBackgroundResource(R.drawable.shape_order_gray);
                break;
            case Const.INTEGER_7:
                tvBtState.setVisibility(View.VISIBLE);
                tvOrderStates.setText("已收货");
                //0 发布 1 查看
                if (commentStatus == 0) {
                    tvBtState.setText("评\t\t价");
                } else {
                    tvBtState.setText("查看评价");
                }
                tvBtState.setTextColor(0xffffffff);
                tvBtState.setEnabled(true);
                tvBtState.setBackgroundResource(R.drawable.shape_order_bg);
                break;
            case Const.INTEGER_8:
                tvOrderStates.setText("已完成");
                tvBtState.setVisibility(View.VISIBLE);
                //0 发布 1 查看
                if (commentStatus == Const.INTEGER_0) {
                    tvBtState.setText("评\t\t价");
                } else {
                    tvBtState.setText("查看评价");
                }
                tvBtState.setTextColor(0xffffffff);
                tvBtState.setEnabled(true);
                tvBtState.setBackgroundResource(R.drawable.shape_order_bg);
                break;
            default:
                break;
        }
        if (status == Const.INTEGER_0) {
            imKuaiJieZhiFu.setVisibility(View.GONE);
            imJiFenZhiFu.setVisibility(View.GONE);
            imYuEZhiFu.setVisibility(View.GONE);
            if (item.getIsXYT() == Const.INTEGER_1) {
                Const.ISNEWYETAI = Const.INTEGER_1;
                tvHuo.setVisibility(View.GONE);
                imJifen.setVisibility(View.GONE);
                tvShopJifen.setVisibility(View.GONE);
            } else {
                if (item.getScoreStatus() == Const.INTEGER_0) {
                    Const.ISNEWYETAI = Const.INTEGER_0;
                    tvHuo.setVisibility(View.VISIBLE);
                    imJifen.setVisibility(View.VISIBLE);
                    tvShopJifen.setVisibility(View.VISIBLE);
                } else {
                    Const.ISNEWYETAI = Const.INTEGER_1;
                    tvHuo.setVisibility(View.GONE);
                    imJifen.setVisibility(View.GONE);
                    tvShopJifen.setVisibility(View.GONE);
                }
            }
        } else {
            if (Const.CONS_STR_JIFENZHIFU.equals(item.getPayType())) {
                imKuaiJieZhiFu.setVisibility(View.GONE);
                imJiFenZhiFu.setVisibility(View.VISIBLE);
                imYuEZhiFu.setVisibility(View.GONE);
                imJifen.setVisibility(View.VISIBLE);
                tvShopJifen.setVisibility(View.VISIBLE);
                tvShifukuan.setText("合计");
                tvShopPrice.setVisibility(View.GONE);
                tvHuo.setVisibility(View.GONE);
            } else if (Const.CONS_STR_YUEZHIFU.equals(item.getPayType())) {
                tvHuo.setVisibility(View.GONE);
                imJifen.setVisibility(View.GONE);
                tvShopJifen.setVisibility(View.GONE);
                imJiFenZhiFu.setVisibility(View.GONE);
                imKuaiJieZhiFu.setVisibility(View.GONE);
                tvShopPrice.setVisibility(View.VISIBLE);
                imYuEZhiFu.setVisibility(View.VISIBLE);
            } else if (Const.CONS_STR_KUAIJIEZHIFU.equals(item.getPayType())) {
                tvHuo.setVisibility(View.GONE);
                imJifen.setVisibility(View.GONE);
                imYuEZhiFu.setVisibility(View.GONE);
                tvShopJifen.setVisibility(View.GONE);
                imJiFenZhiFu.setVisibility(View.GONE);
                tvShopPrice.setVisibility(View.VISIBLE);
                imKuaiJieZhiFu.setVisibility(View.VISIBLE);
            }
        }
        tvShopName.setText(ConstUtils.getStringNoEmpty(item.getMerchant_name()));
        tvInfo.setText("共" + item.getTotalNum() + "件商品 ");
        tvShopPrice.setText("¥" + item.getTotal_price());
        tvShopJifen.setText(String.valueOf(item.getTotalScore()));
        tvYunfei.setText("(含运费" + item.getFreight_fee() + "元)");
        Glide.with(TianyiApplication.getContext()).load(item.getMechantImg()).error(R.mipmap.store_icon).into(ivShopImage);

        if (sonData != null) {
            List<SonBean> sonBeen;
            if (sonData.size() > Const.INTEGER_3) {
                sonBeen = sonData.subList(0, 3);
            } else {
                sonBeen = sonData;
            }
            OrderProductAdapter adapter = new OrderProductAdapter(TianyiApplication.getContext(), sonBeen, item.getScoreStatus(), item.getPayType(), String.valueOf(state), item.getIsXYT());
            llItem.removeAllViews();
            for (int i = 0; i < sonBeen.size(); i++) {
                View itemView = adapter.getView(i, null, null);
                llItem.addView(itemView);
            }
        }
    }
}
