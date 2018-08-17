package com.yst.onecity.bean;

import android.widget.TextView;

/**
 * 放textview的bean
 *
 * @author wuxiaofang
 * @version 4.2.0
 * @date 2018/6/2
 */

public class TextViewBean {
    private TextView textView;
    private TextView textViewNuml;

    public TextViewBean(TextView textView, TextView textViewNuml) {
        this.textView = textView;
        this.textViewNuml = textViewNuml;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public TextView getTextViewNuml() {
        return textViewNuml;
    }

    public void setTextViewNuml(TextView textViewNuml) {
        this.textViewNuml = textViewNuml;
    }
}
