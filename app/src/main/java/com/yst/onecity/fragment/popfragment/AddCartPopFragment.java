package com.yst.onecity.fragment.popfragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.umeng.commonsdk.statistics.common.MLog;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.addorder.AddOrderActivity;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.adapter.AbstractCommonAdapter;
import com.yst.onecity.adapter.CommonViewHolder;
import com.yst.onecity.bean.addorder.OrderBean;
import com.yst.onecity.bean.product.ProductSortStandardBean;
import com.yst.onecity.callbacks.AbstractProductSortStandardCallBack;
import com.yst.onecity.callbacks.AbstractSureOderCallBack;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.eventbus.UpdateEvent;
import com.yst.onecity.eventbus.UpdateSpEvent;
import com.yst.onecity.fragment.ProductFragment;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.KeyBoardUtils;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.RxCode;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.utils.WindowUtils;
import com.yst.onecity.view.TagView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import gorden.rxbus2.RxBus;
import okhttp3.Call;

/**
 * 加入购物车的弹框
 *
 * @author wuxiaofang
 * @version 4.0.0
 * @date 2018/4/23
 */

public class AddCartPopFragment extends DialogFragment {


    @BindView(R.id.view_pop)
    View viewPop;
    @BindView(R.id.add_cart_pop_price_tv)
    TextView addCartPopPriceTv;
    @BindView(R.id.add_cart_pop_kucun_tv)
    TextView addCartPopKucunTv;
    @BindView(R.id.add_cart_pop_choosed_tv)
    TextView addCartPopChoosedTv;
    @BindView(R.id.add_cart_close_iv)
    LinearLayout addCartCloseIv;
    @BindView(R.id.tv_size)
    TextView tvSize;
    @BindView(R.id.sizeflowlayout)
    TagView sizeflowlayout;
    @BindView(R.id.tv_color)
    TextView tvColor;
    @BindView(R.id.colorflowlayout)
    TagView colorflowlayout;
    @BindView(R.id.color_view)
    View colorView;
    @BindView(R.id.guige_taglayout)
    TagView guigeTaglayout;
    @BindView(R.id.btn_reduce)
    ImageView btnReduce;
    @BindView(R.id.edit_product_count)
    EditText editProductCount;
    @BindView(R.id.btn_increase)
    ImageView btnIncrease;
    @BindView(R.id.addCart)
    TextView addCart;
    @BindView(R.id.nowBuy)
    TextView nowBuy;
    @BindView(R.id.ll_button)
    LinearLayout llButton;
    @BindView(R.id.add_cart_pop_iv)
    ImageView addCartPopIv;
    @BindView(R.id.pop_bg)
    LinearLayout popBg;
    Unbinder unbinder;
    private String productId;
    private String merchantId;
    private String hunterId;
    private String authorid;
    private String hid;
    private List<ProductSortStandardBean.ContentBean.SpecBean.SubspecBean> data = new ArrayList<>();
    private List<ProductSortStandardBean.ContentBean.SpecBean.SubspecBean> data2 = new ArrayList<>();
    private List<ProductSortStandardBean.ContentBean.StandardBean> standardList = new ArrayList<>();
    private List<ProductSortStandardBean.ContentBean.SpecBean> spaceBeanList = new ArrayList<>();
    private AbstractCommonAdapter<ProductSortStandardBean.ContentBean.SpecBean.SubspecBean> sizeCommonAdapter;
    private AbstractCommonAdapter<ProductSortStandardBean.ContentBean.SpecBean.SubspecBean> colorCommonAdapter;
    /**
     * 商品类型ID
     */
    private String stid = "";
    /**
     * 商品规格ID
     */
    private String spid = "";
    private String sortOne;
    private String sortTwo;
    private String sortOneName;
    private String sortTwoName;
    private String standardOneName;
    private String standardTwoName;
    private String sortOneId;
    private String sortTwoId;
    private String productType;
    private boolean isMoreSp = false;
    private int count = 1;
    private int selectSizePosition = -1;
    private int selectColorPosition = -1;
    private int kuNum;
    private boolean isSizeflowClick;
    private boolean isColorflowClick;
    private int flag = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Window window = getDialog().getWindow();
        View view = inflater.inflate(R.layout.layout_pop, ((ViewGroup) window.findViewById(android.R.id.content)), false);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.height = (int) (WindowUtils.getScreenHeight(getActivity()) * 0.8);
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);
        window.setWindowAnimations(R.style.add_cart_pop_anim_style);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public static AddCartPopFragment newInstance(String productId, String merchantId, String hunterId, String authorid, String hid, String sortOne, String sortTwo, String sortOneName, String sortTwoName) {
        AddCartPopFragment fragment = new AddCartPopFragment();
        Bundle bundle = new Bundle();
        bundle.putString("productId", productId);
        bundle.putString("merchantId", merchantId);
        bundle.putString("hunterId", hunterId);
        bundle.putString("authorid", authorid);
        bundle.putString("hid", hid);
        bundle.putString("sortOne", sortOne);
        bundle.putString("sortTwo", sortTwo);
        bundle.putString("sortOneName", sortOneName);
        bundle.putString("sortTwoName", sortTwoName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            productId = bundle.getString("productId");
            merchantId = bundle.getString("merchantId");
            hunterId = bundle.getString("hunterId", "0");
            authorid = bundle.getString("authorid", "0");
            hid = bundle.getString("hid", "0");
            sortOne = bundle.getString("sortOne");
            sortTwo = bundle.getString("sortTwo");
            sortTwoName = bundle.getString("sortTwoName");
            sortOneName = bundle.getString("sortOneName");

            if (TextUtils.isEmpty(hunterId)) {
                hunterId = "0";
            }

            if (TextUtils.isEmpty(authorid)) {
                authorid = "0";
            }

            if (TextUtils.isEmpty(hid)) {
                hid = "0";
            }
        }

        editProductCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (flag == Const.INTEGER_3) {
                    if (!isSizeflowClick || !isColorflowClick) {
                        ToastUtils.show("请选择规格");
                        s.clear();
                        return;
                    }
                } else if (flag == Const.INTEGER_2) {
                    if (!isSizeflowClick) {
                        ToastUtils.show("请选择规格");
                        s.clear();
                        return;
                    }
                }
                String text = s.toString();
                int len = s.toString().length();
                if (len == 0) {
                    count = 0;
                }
                if (len == 1 && Const.STR0.equals(text)) {
                    s.clear();
                } else {
                    if (text.length() > 0) {
                        String format = String.format("", Const.INTEGER_1, kuNum);
                        count = Integer.parseInt(text);
                        if (count > kuNum) {
                            editProductCount.setText(String.valueOf(kuNum));
                            editProductCount.setSelection(editProductCount.getText().toString().trim().length());
                            ToastUtils.show(getString(R.string.current_kunum_no));
                        }
                    }
                }
            }
        });
        initCotrol();
        getProductSortStandard(productId, merchantId);
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 刷新适配器
     */
    public void initCotrol() {
        MyLog.e("sss", "sortOne === " + sortOne);
        sizeflowlayout.setAdapter(sizeCommonAdapter = new AbstractCommonAdapter<ProductSortStandardBean.ContentBean.SpecBean.SubspecBean>(getActivity(), data, R.layout.add_cart_tag_item) {

            @Override
            public void convert(final CommonViewHolder holder, final int position, final ProductSortStandardBean.ContentBean.SpecBean.SubspecBean item) {
                holder.setText(R.id.add_cart_item_tv, item.getName());

                if (data.size() == 0) {
                    return;
                }
                if (data.size() == 1) {
                    holder.getView(R.id.add_cart_item_tv).setBackgroundResource(R.drawable.add_taglayout_shape_select);
                    ((TextView) holder.getView(R.id.add_cart_item_tv)).setTextColor(ContextCompat.getColor(context, R.color.white));
                    isSizeflowClick = true;
                    sortOne = item.getName();
                    MyLog.e("@@", "data.size() == 1____sortone_____" + sortOne + "____sorttwo____" + sortTwo);
                    addCartPopChoosedTv.setText("已选择：" + sortOne);
                    sortOneId = String.valueOf(item.getId());
                    if (!TextUtils.isEmpty(sortTwo)) {
                        productType = sortOneId + "," + sortTwoId;
                    } else {
                        productType = sortOneId;
                    }
                    updateProductInfo(productType);
                } else {
                    if (String.valueOf(sortOne).equals(item.getName())) {
                        isSizeflowClick = true;
                        holder.getView(R.id.add_cart_item_tv).setBackgroundResource(R.drawable.add_taglayout_shape_select);
                        ((TextView) holder.getView(R.id.add_cart_item_tv)).setTextColor(ContextCompat.getColor(context, R.color.white));
                        sortOne = item.getName();
                        addCartPopChoosedTv.setText("已选择：" + sortOne);
                        sortOneId = String.valueOf(item.getId());
                        MyLog.e("@@", "else_____sortone_____" + sortOne + "____sorttwo____" + sortTwo);
                        if (!TextUtils.isEmpty(sortTwo)) {
                            productType = sortOneId + "," + sortTwoId;
                        } else {
                            productType = sortOneId;
                        }
                        updateProductInfo(productType);
                    }
                }

                holder.getView(R.id.add_cart_item_tv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MyLog.e("sss", "--setonone");
                        isSizeflowClick = true;
                        selectSizePosition = position;
                        sortOne = item.getName();
                        addCartPopChoosedTv.setText("已选择：" + sortOne);
                        sortOneId = String.valueOf(item.getId());
                        MyLog.e("@@", "setOnClickListener_____sortone_____" + sortOne + "____sorttwo____" + sortTwo);
                        sortOneName = spaceBeanList.get(0).getName();
                        if (!TextUtils.isEmpty(sortTwo)) {
                            productType = sortOneId + "," + sortTwoId;
                        } else {
                            productType = sortOneId;
                        }
                        updateProductInfo(productType);
                        sizeCommonAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        colorflowlayout.setAdapter(colorCommonAdapter = new AbstractCommonAdapter<ProductSortStandardBean.ContentBean.SpecBean.SubspecBean>(getActivity(), data2, R.layout.add_cart_tag_item) {

            @Override
            public void convert(final CommonViewHolder holder, final int position, final ProductSortStandardBean.ContentBean.SpecBean.SubspecBean item) {
                holder.setText(R.id.add_cart_item_tv, item.getName());
                if (data2.size() == 1) {
                    holder.getView(R.id.add_cart_item_tv).setBackgroundResource(R.drawable.add_taglayout_shape_select);
                    ((TextView) holder.getView(R.id.add_cart_item_tv)).setTextColor(ContextCompat.getColor(context, R.color.white));
                    isColorflowClick = true;
                    sortTwo = item.getName();
                    addCartPopChoosedTv.setText("已选择：" + sortTwo);
                    sortTwoId = String.valueOf(item.getId());
                    if (!TextUtils.isEmpty(sortTwo)) {
                        productType = sortOneId + "," + sortTwoId;
                    } else {
                        productType = sortOneId;
                    }
                    updateProductInfo(productType);
                } else {
                    if (String.valueOf(sortTwo).equals(item.getName())) {
                        isColorflowClick = true;
                        holder.getView(R.id.add_cart_item_tv).setBackgroundResource(R.drawable.add_taglayout_shape_select);
                        ((TextView) holder.getView(R.id.add_cart_item_tv)).setTextColor(ContextCompat.getColor(context, R.color.white));
                        sortTwo = item.getName();
                        addCartPopChoosedTv.setText("已选择：" + sortTwo);
                        sortTwoId = String.valueOf(item.getId());
                        sortTwoName = spaceBeanList.get(1).getName();
                        if (!TextUtils.isEmpty(sortTwo)) {
                            productType = sortOneId + "," + sortTwoId;
                        } else {
                            productType = sortOneId;
                        }
                        updateProductInfo(productType);
                    }
                }

                holder.getView(R.id.add_cart_item_tv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MyLog.e("sss", "--setontwo");
                        isColorflowClick = true;
                        selectColorPosition = position;
                        sortTwo = item.getName();
                        if (TextUtils.isEmpty(sortOne)) {
                            addCartPopChoosedTv.setText("已选择：" + sortTwo);
                        }
                        sortTwoId = String.valueOf(item.getId());
                        if (!TextUtils.isEmpty(sortTwo)) {
                            productType = sortOneId + "," + sortTwoId;
                        } else {
                            productType = sortOneId;
                        }
                        updateProductInfo(productType);
                        colorCommonAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    /**
     * 获取商品 分类 和  规格
     */
    private void getProductSortStandard(final String productId, String merchantId) {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("timestamp", timestamp, "pId", productId, "merchantid", merchantId);
        if (TextUtils.isEmpty(sign)) {
            return;
        }
        OkHttpUtils.post().url(Constants.GET_PRODUCT_SORT_STANDARD)
                .addParams("timestamp", timestamp)
                .addParams("pId", productId)
                .addParams("merchantid", merchantId)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractProductSortStandardCallBack() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(TianyiApplication.getInstance().getResources().getString(R.string.str_net_error_message));
            }

            @Override
            public void onResponse(ProductSortStandardBean response, int id) {
                double minprice = 0;
                double maxprice = 0;
                MyLog.e("sss", "-商品规格:" + response.getContent().getStandard().get(0).getStandard() + "id: " + productId);
                if (response.getCode() == Const.INTEGER_1 && null != response.getContent()) {
                    MyLog.e("sss", "--response.getContent().getImageUrl()" + response.getContent().getImageUrl());
                    Glide.with(TianyiApplication.getInstance()).load(response.getContent().getImageUrl()).error(R.mipmap.img_default_p).into(addCartPopIv);
                    if (response.getContent().getStandard().size() != 0) {
                        standardList.addAll(response.getContent().getStandard());
                    }
                    minprice = response.getContent().getMinPrice();
                    maxprice = response.getContent().getMaxPrice();

                    if (response.getContent().getClassify().size() != 0) {
                        stid = String.valueOf(response.getContent().getClassify().get(0).getClassId());
                    }
                    if (minprice >= maxprice) {
                        addCartPopPriceTv.setText("¥" + minprice);
                    } else {
                        addCartPopPriceTv.setText("¥" + minprice + "-" + maxprice);
                        addCartPopPriceTv.setText(String.format("¥%1$s-%2$s", String.format("%.2f", minprice), String.format("%.2f", maxprice)));
                    }

                    if (response.getContent().getSpec() != null) {
                        spaceBeanList = response.getContent().getSpec();
                        if (response.getContent().getSpec().size() > 1) {
                            flag = 3;
                            isMoreSp = true;
                            standardOneName = response.getContent().getSpec().get(0).getName();
                            tvSize.setText(response.getContent().getSpec().get(0).getName());
                            data.addAll(response.getContent().getSpec().get(0).getSubspec());
                            standardTwoName = response.getContent().getSpec().get(1).getName();
                            tvColor.setText(response.getContent().getSpec().get(1).getName());
                            data2.addAll(response.getContent().getSpec().get(1).getSubspec());

                        } else if (response.getContent().getSpec().size() == 1) {
                            List<ProductSortStandardBean.ContentBean.SpecBean.SubspecBean> subspec = response.getContent().getSpec().get(0).getSubspec();
                            if (subspec.size() == 1) {
                                flag = 1;
                            } else {
                                flag = 2;
                            }
                            colorflowlayout.setVisibility(View.GONE);
                            tvColor.setVisibility(View.GONE);
                            colorView.setVisibility(View.GONE);
                            standardOneName = response.getContent().getSpec().get(0).getName();
                            tvSize.setText(response.getContent().getSpec().get(0).getName());
                            data.addAll(response.getContent().getSpec().get(0).getSubspec());
                        }
                    }
                    colorCommonAdapter.notifyDataSetChanged();
                    sizeCommonAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @OnClick({R.id.add_cart_close_iv, R.id.nowBuy, R.id.addCart, R.id.btn_reduce, R.id.btn_increase})
    public void click(View view) {
        String spName = "";
        switch (view.getId()) {
            case R.id.add_cart_close_iv:
                MyLog.e("sss", "--flag:" + flag);
                if (flag == Const.INTEGER_3) {
                    if (!isColorflowClick || !isSizeflowClick) {
                        spName = String.format("%1$s", "null");
                    } else {
                        spName = String.format("%1$s,%2$s", sortOne, sortTwo);
                    }
                } else if (flag == Const.INTEGER_1) {
                    spName = String.format("%1$s", sortOne);
                } else if (flag == Const.INTEGER_2) {
                    if (!isSizeflowClick) {
                        spName = String.format("%1$s", "null");
                    } else {
                        spName = String.format("%1$s", sortOne);
                    }
                }
                MyLog.e("asdsada", isMoreSp + "_____");
                String choosedContent = addCartPopChoosedTv.getText().toString();
                EventBus.getDefault().post(new UpdateSpEvent(TextUtils.isEmpty(choosedContent) ? "" : choosedContent, spid, String.valueOf(count), stid, spName, sortOne, sortTwo, addCartPopPriceTv.getText().toString().trim(), sortOneName, sortTwoName, standardOneName, standardTwoName, isMoreSp));
                KeyBoardUtils.closeKeybord(editProductCount, getActivity());
                ProductFragment.isDetailsPop = false;
                this.dismiss();
                break;
            case R.id.nowBuy:
                if (!TianyiApplication.isLogin) {
                    JumpIntent.jump(getActivity(), LoginActivity.class);
                    return;
                }
                if (isMoreSp) {
                    if (TextUtils.isEmpty(sortOne) || TextUtils.isEmpty(sortTwo)) {
                        ToastUtils.show("请选择规格");
                        return;
                    }
                } else {
                    if (TextUtils.isEmpty(sortOne)) {
                        ToastUtils.show("请选择规格");
                        return;
                    }
                }
                nowBuy();
                break;
            case R.id.addCart:
                if (isMoreSp) {
                    if (TextUtils.isEmpty(sortOne) || TextUtils.isEmpty(sortTwo)) {
                        ToastUtils.show("请选择规格");
                        return;
                    }
                } else {
                    if (TextUtils.isEmpty(sortOne)) {
                        ToastUtils.show("请选择规格");
                        return;
                    }
                }

                if (TianyiApplication.isLogin) {
                    addShopCart();
                } else {
                    Bundle bundle = new Bundle();
                    if (ProductFragment.isDetailsPop) {
                        bundle.putString("addcartDetails", "addcartDetails");
                    } else {
                        bundle.putString("addcart", "addcart");
                    }
                    JumpIntent.jump(getActivity(), LoginActivity.class, bundle);
                }
                break;
            //减
            case R.id.btn_reduce:
                MyLog.e("sss", "--isSizeflowClick:" + isSizeflowClick + "--isColorflowClick" + isColorflowClick);
                if (count == 0) {
                    ToastUtils.show("你还没有输入数量");
                } else if (count == 1) {
                    ToastUtils.show("这是最少的啦~");
                } else {
                    count--;
                    editProductCount.setText(String.valueOf(count));
                }

                if (editProductCount != null) {
                    editProductCount.setSelection(editProductCount.getText().toString().length());
                }
                break;
            //加
            case R.id.btn_increase:
                MLog.e("sss", "flag: " + flag);
                if (flag == Const.INTEGER_3) {
                    if (!isSizeflowClick || !isColorflowClick) {
                        ToastUtils.show("请选择规格");
                        return;
                    }
                } else if (flag == Const.INTEGER_2) {
                    if (!isSizeflowClick) {
                        ToastUtils.show("请选择规格");
                        return;
                    }
                }
                if (kuNum == 0) {
                    ToastUtils.show(getActivity().getString(R.string.current_kunum_no));
                    btnIncrease.setEnabled(false);
                    return;
                }
                count++;
                editProductCount.setText(String.valueOf(count));
                if (Integer.parseInt(editProductCount.getText().toString()) >= kuNum) {
                    editProductCount.setText(String.valueOf(kuNum));
                    ToastUtils.show(getActivity().getString(R.string.current_kunum_shangxian));
                }
                if (editProductCount != null) {
                    editProductCount.setSelection(editProductCount.getText().toString().length());
                }
                break;
            default:
                break;
        }
    }

    /**
     * 加入购物车
     */

    public void addShopCart() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("timestamp", timestamp, "phone", TianyiApplication.appCommonBean.getPhone()
                , "uuid", TianyiApplication.appCommonBean.getUuid(), "spid", spid, "stid", stid, "num", String.valueOf(count), "hunterid", hunterId, "authorid", authorid);

        if (TextUtils.isEmpty(sign)) {
            return;
        }

        OkHttpUtils.post().url(Constants.ADD_SHOP_CART)
                .addParams("timestamp", timestamp)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("spid", spid)
                .addParams("stid", stid)
                .addParams("num", String.valueOf(count))
                .addParams("hunterid", hunterId)
                .addParams("authorid", authorid)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                MyLog.e("sss", "Exception:" + e);
                ToastUtils.show(getString(R.string.str_net_error_message));
            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("sss", "加入购物车:" + response);
                try {
                    JSONObject obj = new JSONObject(response);
                    int code = obj.getInt("code");
                    if (code == 1) {
                        RxBus.get().send(RxCode.CODE_PUBLISH_ADD_SHOPPING_CARD);
                        RxBus.get().send(RxCode.CODE_ADD_SHOPPING_CART);
                        ToastUtils.show("成功加入购物车");
                        MyLog.e("sss", "ProductFragment.isDetailsPop:" + ProductFragment.isDetailsPop);
                        if (ProductFragment.isDetailsPop) {
                            String choosedContent = addCartPopChoosedTv.getText().toString();
                            MyLog.e("sss", " choosedContent=== " + choosedContent);
                            RxBus.get().send(RxCode.CODE_ADD_CART_SUCCESSFUL, new UpdateEvent(choosedContent));
                            ProductFragment.isDetailsPop = false;
                        }
                        dismiss();
                    } else {
                        ToastUtils.show(obj.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 立即购买
     */
    private void nowBuy() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("timestamp", timestamp,
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "pid", productId,
                "longitude", "null",
                "latitude", "null",
                "authorid", authorid,
                "hid", hunterId,
                "sid", spid,
                "num", String.valueOf(count));
        if (TextUtils.isEmpty(sign)) {
            return;
        }
        OkHttpUtils.post().url(Constants.NOW_BUY)
                .addParams("timestamp", timestamp)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("pid", productId)
                .addParams("longitude", "null")
                .addParams("latitude", "null")
                .addParams("num", String.valueOf(count))
                .addParams("sid", spid)
                .addParams("authorid", authorid)
                .addParams("hid", hunterId)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractSureOderCallBack() {
            @Override
            public void onError(Call call, Exception e, int id) {
                MyLog.e("sss", "-onError:" + e.toString());
                ToastUtils.show(getActivity().getResources().getString(R.string.str_net_error_message));
            }

            @Override
            public void onResponse(OrderBean response, int id) {
                if (response.getCode() == 1) {
                    if (response.getContent() != null && response.getContent().getAddress() != null) {
                        OrderBean.ContentBean.AddressBeanX.AddressBean address = response.getContent().getAddress().getAddress();
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("nowBuy", true);
                        bundle.putString("productId", productId);
                        bundle.putString("hunterId", hunterId);
                        bundle.putString("authorid", authorid);
                        bundle.putString("sid", spid);
                        bundle.putString("longitude", address == null ? "null" : address.getLongitude());
                        bundle.putString("latitude", address == null ? "null" : address.getLatitude());
                        bundle.putString("num", String.valueOf(count));
                        JumpIntent.jump(getActivity(), AddOrderActivity.class, bundle);
                        dismiss();
                    }
                } else {
                    ToastUtils.show(response.getMsg());
                }
            }
        });
    }

    /**
     * 更新 显示商品信息
     *
     * @param productType 商品类型
     */
    private void updateProductInfo(String productType) {

        MyLog.e("@@", "productType____" + productType);
        MyLog.e("@@", "standardList.get(i).getSalePrice()______" + standardList.size() + "_______" + standardList.get(0).getSalePrice());
        for (int i = 0; i < standardList.size(); i++) {
            if (standardList.get(i).getSpecificationsid().equals(productType)) {
                addCartPopPriceTv.setText(String.format("¥%1$s", String.format("%.2f", standardList.get(i).getSalePrice())));
                addCartPopKucunTv.setText(String.format("库存%1$d", standardList.get(i).getKuNum()));
                kuNum = standardList.get(i).getKuNum();
                MyLog.e("sss", "kuNum:" + kuNum);
                if (isMoreSp) {
                    addCartPopChoosedTv.setText(String.format("已选择：%1$s,%2$s", sortOne, sortTwo));
                } else {
                    addCartPopChoosedTv.setText(String.format("已选择：%1$s", sortOne));
                }
                spid = String.valueOf(standardList.get(i).getStandardId());
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
