package com.yst.onecity.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.yst.onecity.R;
import com.yst.onecity.utils.WindowUtils;

import java.util.ArrayList;


/**
 * 商城商品类型选择器
 *
 * @author Shenxinke
 * @version 4.2.0
 * @data 2018/6/5
 */

public class ShoppingMallPopupWindow extends PopupWindow {
    private View conentView;
    private OnRightItemCheckListener rightItemCheckListener;
    private final FlowCheckLayout flowlayout;

    public ShoppingMallPopupWindow(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.shopping_mall_popwindow, null);
        setAttribute((Activity) context);
        flowlayout = (FlowCheckLayout) conentView.findViewById(R.id.flowlayout);
        flowlayout.setOnItemListener(new FlowCheckLayout.OnItemClickListener() {
            @Override
            public void onItemClick(String str, int pos) {
                rightItemCheckListener.onItemCheck(pos);
            }
        });
    }

    /**
     * 设置pop属性
     */
    private void setAttribute(Activity activity) {
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(WindowUtils.getScreenWidth(activity));
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(WindowUtils.getScreenHeight(activity) / 3);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismissListener
        this.setBackgroundDrawable(dw);
    }

    /**
     * 显示popupWindow
     */
    public void showPop(View parent, ImageView iv, ArrayList<String> items, int curPos) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent);
            iv.setImageResource(R.mipmap.shang);
            flowlayout.setVisibility(View.VISIBLE);
            flowlayout.addDatas(items);
            flowlayout.setCurView(curPos);
        } else {
            this.dismiss();
            iv.setImageResource(R.mipmap.xia);
        }
    }

    public interface OnRightItemCheckListener {
        /**
         * 右侧点击事件
         *
         * @param pos
         */
        void onItemCheck(int pos);
    }

    public void setOnRightItemCheckListener(OnRightItemCheckListener buttonClickedListener) {
        this.rightItemCheckListener = buttonClickedListener;
    }
}
