package com.yst.onecity.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.activity.ProductDetailActivity;
import com.yst.onecity.bean.Cart;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.interfaces.OnCartListener;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.ToastUtils;

import java.util.ArrayList;

/**
 * 购物车适配器
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/19.
 */
public class CartAdapter extends BaseExpandableListAdapter implements View.OnClickListener {

    private Context context;
    private ArrayList<Cart> mCartEntityList;
    private boolean isShowedUnuserful;

    public void setIsShowUnserful(boolean isShowedUnuserful) {
        this.isShowedUnuserful = isShowedUnuserful;
    }

    public CartAdapter(Context context, ArrayList<Cart> cartEntitiyList) {
        this.context = context;
        mCartEntityList = cartEntitiyList;
    }

    private OnCartListener mCartListener;

    public void setOnCartListener(OnCartListener cartListener) {
        mCartListener = cartListener;
    }

    @Override
    public int getGroupCount() {
        if (mCartEntityList.size() > 0) {
            return mCartEntityList.size();
        }
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mCartEntityList.get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mCartEntityList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mCartEntityList.get(groupPosition).getList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public void setData(ArrayList<Cart> list){
        if(null != list){
            this.mCartEntityList = list;
            notifyDataSetChanged();
        }
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder = null;
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cart_group, parent, false);
            convertView.setVisibility(View.GONE);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        setupGroupView(groupPosition, groupViewHolder);
        return convertView;
    }

    @SuppressLint("NewApi")
    private void setupGroupView(int groupPosition, GroupViewHolder groupViewHolder) {

        if (mCartEntityList.size() == groupPosition) {
            return;
        }
        groupViewHolder.checkBox.setChecked(mCartEntityList.get(groupPosition).isGroupCheck());
        groupViewHolder.checkBox.setTag(R.integer.view_group_tag, groupPosition);
        groupViewHolder.shoplayout.setTag(R.integer.view_group_tag, groupPosition);
        groupViewHolder.checkBox.setOnClickListener(this);
        groupViewHolder.shoplayout.setOnClickListener(this);
        groupViewHolder.checkBox.setVisibility(View.GONE);
        groupViewHolder.shopName.setVisibility(View.GONE);
        groupViewHolder.shoplayout.setVisibility(View.GONE);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder = null;
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_child_new, parent, false);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        setupChildView(groupPosition, childPosition, childViewHolder);
        return convertView;
    }

    private void setupChildView(final int groupPosition, final int childPosition, final ChildViewHolder childViewHolder) {


        if (mCartEntityList.get(groupPosition).isGroupEdit()) {
            childViewHolder.viewEdit.setVisibility(View.VISIBLE);
            childViewHolder.viewComplete.setVisibility(View.INVISIBLE);
        } else {
            childViewHolder.viewEdit.setVisibility(View.INVISIBLE);
            childViewHolder.viewComplete.setVisibility(View.VISIBLE);
        }

        if (mCartEntityList.get(groupPosition).getList().get(childPosition).getIsAbate() == 0) {
            MyLog.e("sss", "-----" + mCartEntityList.get(groupPosition).getList().get(childPosition).isUnUserful());
            if (mCartEntityList.get(groupPosition).getList().get(childPosition).isUnUserful()) {
                childViewHolder.tvUnseful.setVisibility(View.VISIBLE);
            } else {
                childViewHolder.tvUnseful.setVisibility(View.GONE);
            }
            childViewHolder.checkBox.setVisibility(View.GONE);
        } else {
            childViewHolder.tvUnseful.setVisibility(View.GONE);
            childViewHolder.checkBox.setVisibility(View.VISIBLE);
        }

        MyLog.e("sss", "isNyt:" + mCartEntityList.get(groupPosition).getList().get(childPosition).getIsNyt());
        if (mCartEntityList.get(groupPosition).getList().get(childPosition).getIsNyt() == 0) {
            childViewHolder.itemIntegal.setVisibility(View.VISIBLE);
            childViewHolder.proIntegal.setText(ConstUtils.changeEmptyStringToZero(mCartEntityList.get(groupPosition).getList().get(childPosition).getScorePrice()));
        } else {
            childViewHolder.itemIntegal.setVisibility(View.INVISIBLE);
        }
        childViewHolder.checkBox.setChecked(mCartEntityList.get(groupPosition).getList().get(childPosition).isClick());
        childViewHolder.productSpecification.setText(mCartEntityList.get(groupPosition).getList().get(childPosition).getProductSpecification());
        childViewHolder.productPrice.setText(String.format("%.2f", mCartEntityList.get(groupPosition).getList().get(childPosition).getPrice()));
        childViewHolder.productName.setText(mCartEntityList.get(groupPosition).getList().get(childPosition).getName());
        childViewHolder.productSpecification.setText(mCartEntityList.get(groupPosition).getList().get(childPosition).getProductSpecification());
        if (childViewHolder.editProdcutCount.getTag() instanceof TextWatcher) {
            childViewHolder.editProdcutCount.removeTextChangedListener((TextWatcher) (childViewHolder.editProdcutCount.getTag()));
        }
        childViewHolder.editProdcutCount.setText(String.valueOf(mCartEntityList.get(groupPosition).getList().get(childPosition).getBuyNum()));
        childViewHolder.productCount.setText("x" + mCartEntityList.get(groupPosition).getList().get(childPosition).getBuyNum());
        childViewHolder.proIntegal.setText(mCartEntityList.get(groupPosition).getList().get(childPosition).getScorePrice());
//childViewHolder.t
        final ChildViewHolder holder = childViewHolder;
        // 根据手指触碰的位置，获取当前EditText的位置；
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !TextUtils.isEmpty(s.toString())) {
                    int minNum = 1;
                    int len = s.length();
                    if (len == 1 && Const.STR0.equals(s.toString())) {
                        s.clear();
                        return;
                    }
                    if (s.length() == 0) {
                        mCartEntityList.get(groupPosition).getList().get(childPosition).setBuyNum(minNum);
                        notifyDataSetChanged();
                        return;
                    }
                    if (Const.STR0.equals(s.toString().trim())) {
                        mCartEntityList.get(groupPosition).getList().get(childPosition).setBuyNum(minNum);
                        notifyDataSetChanged();
                        return;
                    }
                    if (Long.parseLong(s.toString().trim()) >= mCartEntityList.get(groupPosition).getList().get(childPosition).getNum()) {
                        // 动态存储数据
                        ToastUtils.show("已达库存上限");
                        mCartEntityList.get(groupPosition).getList().get(childPosition).setBuyNum(mCartEntityList.get(groupPosition).getList().get(childPosition).getNum());
                        notifyDataSetChanged();
                    } else {
                        // 动态存储数据
                        if (Long.parseLong(s.toString()) < minNum) {
                            mCartEntityList.get(groupPosition).getList().get(childPosition).setBuyNum(minNum);
                        } else {
                            mCartEntityList.get(groupPosition).getList().get(childPosition).setBuyNum(Integer.parseInt(s.toString()));
                        }
                    }
                }
            }
        };
        childViewHolder.editProdcutCount.addTextChangedListener(watcher);
        childViewHolder.editProdcutCount.setTag(watcher);
        if (mCartEntityList.get(groupPosition).getList().get(childPosition).getAddress().contains(Const.CONS_STR_HTTP)) {
            Glide.with(context)
                    .load(mCartEntityList.get(groupPosition).getList().get(childPosition).getAddress())
                    .placeholder(R.mipmap.img_default_p)
                    .error(R.mipmap.img_default_p)
                    .into(childViewHolder.productIcon);
        } else {
            Glide.with(context)
                    .load(Constants.URL_IMAGE_HEAD + mCartEntityList.get(groupPosition).getList().get(childPosition).getAddress())
                    .placeholder(R.mipmap.img_default_p)
                    .error(R.mipmap.img_default_p)
                    .into(childViewHolder.productIcon);
        }

        childViewHolder.checkBox.setTag(R.integer.view_group_tag, groupPosition);
        childViewHolder.checkBox.setTag(R.integer.view_child_tag, childPosition);
        childViewHolder.checkBox.setOnClickListener(this);

        childViewHolder.childDeleteButton.setTag(R.integer.view_group_tag, groupPosition);
        childViewHolder.childDeleteButton.setTag(R.integer.view_child_tag, childPosition);
        childViewHolder.childDeleteButton.setOnClickListener(this);
        int abate = mCartEntityList.get(groupPosition).getList().get(childPosition).getIsAbate();
        if (abate == 0) {
            childViewHolder.editProdcutCount.setEnabled(false);
        } else {
            childViewHolder.editProdcutCount.setEnabled(true);
        }

        childViewHolder.increaseBtn.setTag(R.integer.view_group_tag, groupPosition);
        childViewHolder.increaseBtn.setTag(R.integer.view_child_tag, childPosition);
        childViewHolder.increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int abate = mCartEntityList.get(groupPosition).getList().get(childPosition).getIsAbate();
                if (abate == 0) {
                    childViewHolder.increaseBtn.setEnabled(false);
                    ToastUtils.show("该商品已失效");
                } else {
                    childViewHolder.increaseBtn.setEnabled(true);
                    mCartListener.childIncrease((Integer) v.getTag(R.integer.view_group_tag), (Integer) v.getTag(R.integer.view_child_tag));
                }
            }
        });

        childViewHolder.reduceBtn.setTag(R.integer.view_group_tag, groupPosition);
        childViewHolder.reduceBtn.setTag(R.integer.view_child_tag, childPosition);
        childViewHolder.reduceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int abate = mCartEntityList.get(groupPosition).getList().get(childPosition).getIsAbate();
                if (abate == 0) {
                    childViewHolder.reduceBtn.setEnabled(false);
                    ToastUtils.show("该商品已失效");
                } else {
                    childViewHolder.reduceBtn.setEnabled(true);
                    mCartListener.childReduce((Integer) v.getTag(R.integer.view_group_tag), (Integer) v.getTag(R.integer.view_child_tag));
                }
            }
        });
        childViewHolder.linearChildAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCartEntityList.get(groupPosition).getList().get(childPosition).getIsAbate() != 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString("merchantId", String.valueOf(mCartEntityList.get(groupPosition).getList().get(childPosition).getId()));
                    bundle.putString("productId", String.valueOf(mCartEntityList.get(groupPosition).getList().get(childPosition).getProductId()));
                    JumpIntent.jump((Activity) context, ProductDetailActivity.class, bundle);
                } else {
                    ToastUtils.show("该商品已失效");
                }
            }
        });
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        if (null == mCartListener) {
            return;
        }
        switch (v.getId()) {
            case R.id.child_checkBox:
                mCartListener.childCheck((Integer) v.getTag(R.integer.view_group_tag), (Integer) v.getTag(R.integer.view_child_tag));
                break;
            case R.id.linear_child_all:
                mCartListener.childClick((Integer) v.getTag(R.integer.view_group_tag), (Integer) v.getTag(R.integer.view_child_tag));
                break;
            case R.id.child_delete:
                mCartListener.childDelete((Integer) v.getTag(R.integer.view_group_tag), (Integer) v.getTag(R.integer.view_child_tag));
                break;
///            case R.id.btn_increase:
//                mCartListener.childIncrease((Integer) v.getTag(R.integer.view_group_tag), (Integer) v.getTag(R.integer.view_child_tag));
//                break;
//            case R.id.btn_reduce:
//
//                mCartListener.childReduce((Integer) v.getTag(R.integer.view_group_tag), (Integer) v.getTag(R.integer.view_child_tag));
//                break;
            default:
                break;
        }
    }

    private static class GroupViewHolder {
        CheckBox checkBox;
        TextView shopName;
        View shoplayout;

        public GroupViewHolder(View convertView) {
            checkBox = (CheckBox) convertView.findViewById(R.id.group_checkBox);
            shopName = (TextView) convertView.findViewById(R.id.shop_name);
            shoplayout = convertView.findViewById(R.id.shop_layout);
            checkBox.setVisibility(View.GONE);
        }
    }

    private static class ChildViewHolder {
        TextView proIntegal;
        CheckBox checkBox;
        ImageView productIcon;
        TextView productName;
        TextView productSpecification;
        TextView productPrice;
        TextView productCount;
        TextView childDeleteButton;
        View viewEdit;
        View viewComplete;
        ImageView increaseBtn;
        ImageView reduceBtn;
        EditText editProdcutCount;
        View groupView;
        LinearLayout linearChildAll;
        TextView txtYixiajiaIcon;
        /**
         * 起批数量展示
         */
        TextView txtCartWholesaleDesc;
        TextView tvScore;
        TextView tvUnseful;
        LinearLayout itemIntegal;

        public ChildViewHolder(View convertView) {
            checkBox = (CheckBox) convertView.findViewById(R.id.child_checkBox);
            productName = (TextView) convertView.findViewById(R.id.product_name);
            productIcon = (ImageView) convertView.findViewById(R.id.product_icon);
            productCount = (TextView) convertView.findViewById(R.id.product_count);
            productPrice = (TextView) convertView.findViewById(R.id.product_price);
            productSpecification = (TextView) convertView.findViewById(R.id.specification_tv);
            childDeleteButton = (TextView) convertView.findViewById(R.id.child_delete);
            viewEdit = convertView.findViewById(R.id.layout_edit);
            viewComplete = convertView.findViewById(R.id.layout_complete);
            increaseBtn = (ImageView) convertView.findViewById(R.id.btn_increase);
            reduceBtn = (ImageView) convertView.findViewById(R.id.btn_reduce);
            editProdcutCount = (EditText) convertView.findViewById(R.id.edit_product_count);
            groupView = (View) convertView.findViewById(R.id.group_view);
            linearChildAll = (LinearLayout) convertView.findViewById(R.id.linear_child_all);
            txtYixiajiaIcon = (TextView) convertView.findViewById(R.id.txt_yixiajia_icon);
            txtCartWholesaleDesc = (TextView) convertView.findViewById(R.id.txt_cart_wholesale_desc);
            tvScore = (TextView) convertView.findViewById(R.id.tv_score);
            tvUnseful = (TextView) convertView.findViewById(R.id.ll_unuseful);
            itemIntegal = (LinearLayout) convertView.findViewById(R.id.ll_item_integal);
            proIntegal = (TextView) convertView.findViewById(R.id.product_intergal);

        }
    }
}
