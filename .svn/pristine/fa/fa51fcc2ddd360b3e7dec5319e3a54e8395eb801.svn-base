package com.yst.onecity.view;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.yst.onecity.config.Const;
import com.yst.onecity.utils.ToastUtils;

/**
 * EditText监听类（不带TextView）
 *
 * @version 4.2.0
 * @author jiaofan
 * @date 2018/6/13
 */
public class NoTextEtWatcher implements TextWatcher {

    /**
     * 用来记录输入字符的时候光标的位置
     */
    int cursor = 0;
    /**
     * 用来标注输入某一内容之前的编辑框中的内容的长度
     */
    int beforeLength;
    /**
     * 字符个数限制
     */
    private int limit;
    /**
     * 编辑框控件
     */
    private EditText editText;
    /**
     * 上下文对象
     */
    private Context context;
    private Class<?> mClass;

    private int mRequestCode;
    private Activity mActivity;

    public NoTextEtWatcher(EditText text, int limit, Context context) {
        this.limit = limit;
        this.editText = text;
        this.context = context;
    }

    public NoTextEtWatcher(EditText text, int limit, Context context, Activity activity, Class<?> cls, int requestCode) {
        this.limit = limit;
        this.editText = text;
        this.context = context;
        this.mActivity = activity;
        this.mClass = cls;
        this.mRequestCode = requestCode;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
        beforeLength = s.length();
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        cursor = i;
    }

    @Override
    public void afterTextChanged(Editable s) {
        // 输入内容后编辑框所有内容的总长度
        int afterLength = s.length();
        // 如果字符添加后超过了限制的长度，那么就移除后面添加的那一部分
        if (afterLength > limit) {
            // 比限制的最大数超出了多少字
            int dValue = afterLength - limit;
            // 这时候从手机输入的字的个数
            int dNum = afterLength - beforeLength;
            // 需要删除的超出部分的开始位置
            int st = cursor + (dNum - dValue);
            // 需要删除的超出部分的末尾位置
            int en = cursor + dNum;
            // 调用delete()方法将编辑框中超出部分的内容去掉
            Editable sNew = s.delete(st, en);
            // 给编辑框重新设置文本
            editText.setText(sNew.toString());
            // 设置光标最后显示的位置为超出部分的开始位置，优化体验
            editText.setSelection(st);
            if (limit == Const.INTEGER_20) {
                ToastUtils.show("最多输入"+limit+"个字");
            } else {
                // 弹出信息提示已超出字数限制
                ToastUtils.show("已超出最大字数限制");
            }
        }
    }
}
