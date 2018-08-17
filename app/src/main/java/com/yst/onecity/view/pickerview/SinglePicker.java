package com.yst.onecity.view.pickerview;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yst.onecity.view.pickerview.common.util.ConvertUtils;
import com.yst.onecity.view.pickerview.common.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 单选框
 *
 * @author luxuchang
 * @date 2017/8/12
 * @version 3.0.0
 */
public class SinglePicker extends AbstractWheelPicker {
    private List<String> pickerDataSource = new ArrayList<>();
    private int popWith = ScreenUtils.scaleWindowWidth(activity, 0.8f);
    /**
     * 当前选中的item
     */
    private String selectedItem;
    /**
     * 当前选中项下标
     */
    private int selectedIndex = 0;

    private SinglePickerSelector singlePickerSelector;

    public SinglePicker(Activity activity, List<String> data, SinglePickerSelector singlePickerSelector) {
        super(activity);
        pickerDataSource = data;
        this.singlePickerSelector = singlePickerSelector;
    }

    @Override
    @NonNull
    protected View makeCenterView() {
        // 为了展示选中图片
        RelativeLayout relativeLayout = new RelativeLayout(activity);

        LinearLayout layout = new LinearLayout(activity);
        layout.setPadding(70, 0, 30, 0);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        final WheelView provinceView = new WheelView(activity);
        provinceView.setLayoutParams(new LinearLayout.LayoutParams(popWith, WRAP_CONTENT));
        provinceView.setTextSize(textSize);
        provinceView.setTextColor(textColorNormal, textColorFocus);
        provinceView.setLineVisible(lineVisible);
        provinceView.setLineColor(lineColor);
        provinceView.setOffset(offset);
        layout.addView(provinceView);
        provinceView.setItems(pickerDataSource, selectedIndex);
        provinceView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                selectedItem = item;
                SinglePicker.this.selectedIndex = selectedIndex;
            }
        });

        ImageView imageView = new ImageView(activity);
///        imageView.setBackground(ContextCompat.getDrawable(activity, R.mipmap.xuanzhong));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ConvertUtils.toPx(activity, 40));
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        imageView.setLayoutParams(params);
        relativeLayout.addView(imageView);
        relativeLayout.addView(layout);
        return relativeLayout;
    }

    @Override
    protected void onSubmit() {
        super.onSubmit();
        singlePickerSelector.onSelectItem(selectedIndex);
    }

    public interface SinglePickerSelector{
        /**
         * 选中条目监听
         * @param itemIndex
         */
        void onSelectItem(int itemIndex);
    }
}
