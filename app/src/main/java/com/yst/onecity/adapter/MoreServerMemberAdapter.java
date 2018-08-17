package com.yst.onecity.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.bean.CodeMsgBean;
import com.yst.onecity.bean.MoreServerMemberBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.roundedimageview.RoundedImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Request;

/**
 * @author Shenxinke
 * @version 4.2.2
 * @data 2018/6/1
 * <p>
 * 更多专员适配器
 */

public class MoreServerMemberAdapter extends BaseAdapter {
    private Context context;
    private List<MoreServerMemberBean.ContentBean> mDatas = new ArrayList<>();
    private LayoutInflater inflater;

    public MoreServerMemberAdapter(Context context, List<MoreServerMemberBean.ContentBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        inflater = LayoutInflater.from(context);
    }

    public void addAll(List<MoreServerMemberBean.ContentBean> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setData(List<MoreServerMemberBean.ContentBean> data) {
        if (mDatas != null) {
            this.mDatas = data;
            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        HolderView holderView = null;
        if (view == null) {
            view = inflater.inflate(R.layout.more_server_item, null);
            holderView = new HolderView(view);
            view.setTag(holderView);
        } else {
            holderView = (MoreServerMemberAdapter.HolderView) view.getTag();
        }
        holderView.tvName.setText(Utils.signString(mDatas.get(i).getNickname()));
        holderView.tvText.setText(Utils.signString(mDatas.get(i).getServiceDigest()));
        holderView.tvFavorable.setText(Utils.signString(Const.CONS_STR_FAVORABLE_RATE + mDatas.get(i).getRate() + Const.CONS_STR_RATE));
        holderView.tvAddress.setText(Utils.signString(mDatas.get(i).getAddress()));
        Glide.with(TianyiApplication.getInstance())
                .load(ConstUtils.matchingImageUrl(ConstUtils.getStringNoEmpty(mDatas.get(i).getLogoImg())))
                .asBitmap()
                .error(R.mipmap.head_2)
                .into(holderView.imgHeadPic);
        HolderView finalHolderView = holderView;
        if(mDatas.get(i).getIsBind() == Const.INTEGER_1){
            holderView.tvItemBinding.setVisibility(View.GONE);
        }else {
            holderView.tvItemBinding.setVisibility(View.VISIBLE);
        }
        holderView.tvItemBinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TianyiApplication.isLogin) {
                    JumpIntent.jump((Activity) context, LoginActivity.class);
                    return;
                }
                String timestamp = SignUtils.getTimeStamp();
                String sign = Utils.getSign(
                        "uuid", TianyiApplication.appCommonBean.getUuid(),
                        "phone", TianyiApplication.appCommonBean.getPhone(),
                        "hid ", String.valueOf(String.valueOf(mDatas.get(i).getHid())),
                        "timestamp", timestamp);
                if (TextUtils.isEmpty(sign)) {
                    return;
                }
                OkHttpUtils.post()
                        .url(Constants.BIND_ZHUAN_YUAN)
                        .addParams("hid", String.valueOf(mDatas.get(i).getHid()))
                        .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                        .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                        .addParams("client_type", "A")
                        .addParams("sign", sign)
                        .addParams("timestamp", timestamp)
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.show("请求失败");
                    }

                    @Override
                    public void onBefore(Request request, int id) {
                        super.onBefore(request, id);
                    }

                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MyLog.e("sss", "-绑定: " + response);
                        if (response != null) {
                            CodeMsgBean codeMsgBean = new Gson().fromJson(response, CodeMsgBean.class);
                            if (codeMsgBean.getCode() == Const.INTEGER_1) {
                                ToastUtils.show(Utils.signString(codeMsgBean.getMsg()));
                                finalHolderView.tvItemBinding.setVisibility(View.GONE);
                            } else {
                                ToastUtils.show(Utils.signString(codeMsgBean.getMsg()));
                            }
                        } else {
                            ToastUtils.show("请求失败");
                        }
                    }
                });
            }
        });
        int size = mDatas.get(i).getCons().size();
        if(size == Const.INTEGER_0){
            holderView.tvServerMore.setVisibility(View.GONE);
            holderView.imgCard3.setVisibility(View.GONE);
            holderView.imgServerImage3.setVisibility(View.GONE);
            holderView.tvServerText3.setVisibility(View.GONE);
            holderView.imgCard2.setVisibility(View.GONE);
            holderView.imgServerImage2.setVisibility(View.GONE);
            holderView.tvServerText2.setVisibility(View.GONE);
            holderView.imgCard.setVisibility(View.GONE);
            holderView.imgServerImage.setVisibility(View.GONE);
            holderView.tvServerText.setVisibility(View.GONE);
        } else if (size == Const.INTEGER_1) {
            holderView.tvServerMore.setVisibility(View.VISIBLE);
            holderView.imgCard3.setVisibility(View.VISIBLE);
            holderView.imgServerImage3.setVisibility(View.VISIBLE);
            holderView.tvServerText3.setVisibility(View.VISIBLE);
            holderView.imgCard2.setVisibility(View.GONE);
            holderView.imgServerImage2.setVisibility(View.GONE);
            holderView.tvServerText2.setVisibility(View.GONE);
            holderView.imgCard.setVisibility(View.GONE);
            holderView.imgServerImage.setVisibility(View.GONE);
            holderView.tvServerText.setVisibility(View.GONE);
            Glide.with(TianyiApplication.getInstance())
                    .load(ConstUtils.matchingImageUrl(ConstUtils.getStringNoEmpty(mDatas.get(i).getCons().get(Const.INTEGER_0).getAddress())))
                    .asBitmap()
                    .error(R.mipmap.iv_default_class)
                    .into(holderView.imgServerImage3);
            holderView.tvServerText3.setText(mDatas.get(i).getCons().get(Const.INTEGER_0).getTitle());
        } else if (size == Const.INTEGER_2) {
            holderView.tvServerMore.setVisibility(View.VISIBLE);
            holderView.imgCard3.setVisibility(View.VISIBLE);
            holderView.imgServerImage3.setVisibility(View.VISIBLE);
            holderView.tvServerText3.setVisibility(View.VISIBLE);
            holderView.imgCard2.setVisibility(View.VISIBLE);
            holderView.imgServerImage2.setVisibility(View.VISIBLE);
            holderView.tvServerText2.setVisibility(View.VISIBLE);
            holderView.imgCard.setVisibility(View.GONE);
            holderView.imgServerImage.setVisibility(View.GONE);
            holderView.tvServerText.setVisibility(View.GONE);
            Glide.with(TianyiApplication.getInstance())
                    .load(ConstUtils.matchingImageUrl(ConstUtils.getStringNoEmpty(mDatas.get(i).getCons().get(Const.INTEGER_0).getAddress())))
                    .asBitmap()
                    .error(R.mipmap.iv_default_class)
                    .into(holderView.imgServerImage3);
            holderView.tvServerText3.setText(mDatas.get(i).getCons().get(Const.INTEGER_0).getTitle());

            Glide.with(TianyiApplication.getInstance())
                    .load(ConstUtils.matchingImageUrl(ConstUtils.getStringNoEmpty(mDatas.get(i).getCons().get(Const.INTEGER_1).getAddress())))
                    .asBitmap()
                    .error(R.mipmap.iv_default_class)
                    .into(holderView.imgServerImage2);
            holderView.tvServerText2.setText(mDatas.get(i).getCons().get(Const.INTEGER_1).getTitle());
        } else if (size == Const.INTEGER_3) {
            holderView.tvServerMore.setVisibility(View.VISIBLE);
            holderView.imgCard3.setVisibility(View.VISIBLE);
            holderView.imgServerImage3.setVisibility(View.VISIBLE);
            holderView.tvServerText3.setVisibility(View.VISIBLE);
            holderView.imgCard2.setVisibility(View.VISIBLE);
            holderView.imgServerImage2.setVisibility(View.VISIBLE);
            holderView.tvServerText2.setVisibility(View.VISIBLE);
            holderView.imgCard.setVisibility(View.VISIBLE);
            holderView.imgServerImage.setVisibility(View.VISIBLE);
            holderView.tvServerText.setVisibility(View.VISIBLE);
            Glide.with(TianyiApplication.getInstance())
                    .load(ConstUtils.matchingImageUrl(ConstUtils.getStringNoEmpty(mDatas.get(i).getCons().get(Const.INTEGER_0).getAddress())))
                    .asBitmap()
                    .error(R.mipmap.iv_default_class)
                    .into(holderView.imgServerImage3);
            holderView.tvServerText3.setText(mDatas.get(i).getCons().get(Const.INTEGER_0).getTitle());

            Glide.with(TianyiApplication.getInstance())
                    .load(ConstUtils.matchingImageUrl(ConstUtils.getStringNoEmpty(mDatas.get(i).getCons().get(Const.INTEGER_1).getAddress())))
                    .asBitmap()
                    .error(R.mipmap.iv_default_class)
                    .into(holderView.imgServerImage2);
            holderView.tvServerText2.setText(mDatas.get(i).getCons().get(Const.INTEGER_1).getTitle());

            Glide.with(TianyiApplication.getInstance())
                    .load(ConstUtils.matchingImageUrl(ConstUtils.getStringNoEmpty(mDatas.get(i).getCons().get(Const.INTEGER_2).getAddress())))
                    .asBitmap()
                    .error(R.mipmap.iv_default_class)
                    .into(holderView.imgServerImage);
            holderView.tvServerText.setText(mDatas.get(i).getCons().get(Const.INTEGER_2).getTitle());
        }

        return view;
    }

    class HolderView {
        @BindView(R.id.more_item_name)
        TextView tvName;
        @BindView(R.id.more_item_head)
        RoundedImageView imgHeadPic;
        @BindView(R.id.more_item_text)
        TextView tvText;
        @BindView(R.id.more_item_favorable)
        TextView tvFavorable;
        @BindView(R.id.more_item_set)
        TextView tvItemBinding;
        @BindView(R.id.more_item_address)
        TextView tvAddress;

        @BindView(R.id.more_server_card)
        ImageView imgCard;
        @BindView(R.id.server_text)
        TextView tvServerText;
        @BindView(R.id.server_image)
        ImageView imgServerImage;

        @BindView(R.id.more_server_card2)
        ImageView imgCard2;
        @BindView(R.id.server_text2)
        TextView tvServerText2;
        @BindView(R.id.server_image2)
        ImageView imgServerImage2;

        @BindView(R.id.more_server_card3)
        ImageView imgCard3;
        @BindView(R.id.server_text3)
        TextView tvServerText3;
        @BindView(R.id.server_image3)
        ImageView imgServerImage3;

        @BindView(R.id.server_more)
        TextView tvServerMore;

        HolderView(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
