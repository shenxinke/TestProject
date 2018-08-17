package com.yst.onecity.adapter.viewholder;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.bean.livevideo.LiveVideoBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页直播列表的ViewHolder
 *
 * @author WangJingWei
 * @version 4.1.0
 * @date 2018/5/14.
 */
public class HomeLiveViewHolder extends AbstractTypeViewHolder<LiveVideoBean.ContentBean> {


    @BindView(R.id.view_root)
    public ConstraintLayout viewRoot;
    @BindView(R.id.img_bg)
    RoundedImageView imgBg;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.img_play)
    ImageView imgPlay;

    public HomeLiveViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindHolder(LiveVideoBean.ContentBean item) {

        String type = Utils.changeEmptyStringToZero(item.getType());
        switch (type) {
            case Const.TYPE_LIVE:
                imgPlay.setVisibility(View.GONE);
                tvType.setText(Const.CONS_STR_LIVE);
                tvNum.setText(ConstUtils.changeEmptyStringToZero(item.getOnLineNum()) + Const.CONS_STR_LIVE_ONLINE);
                break;
            case Const.TYPE_VIDEO:
                imgPlay.setVisibility(View.VISIBLE);
                tvType.setText(Const.CONS_STR_VIDEO);
                tvNum.setText(Const.CONS_STR_VIDEO_NUM + ConstUtils.changeEmptyStringToZero(item.getPlayNum()));
                break;
            default:
                break;
        }
        tvDesc.setText(Utils.getStringNoEmpty(item.getDesc()));
        Glide.with(TianyiApplication.getContext()).load(ConstUtils.matchingImageUrl(item.getImgUrl())).error(R.mipmap.brand_default).into(imgBg);

    }
}
