package com.yst.onecity.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.dialog.CommonProgressDialog;
import com.yst.onecity.view.MyProcessDialog;

import butterknife.ButterKnife;

/**
 * fragment基类
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/0/19
 */
public abstract class BaseFragment extends Fragment {
    protected boolean isVisible;
    protected Context context;
    protected TextView tvMainTitle;
    protected TextView tvRight;
    protected LinearLayout back;
    public View rootView;
    /**
     * 显示界面等待条
     */
    private MyProcessDialog mInfoProgressDialog;
    private CommonProgressDialog onLineDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        if (rootView == null) {
            rootView = inflater.inflate(bindLayout(), container, false);
            ButterKnife.bind(this, rootView);
            initData();
            initControll();
            setListener();
        }
        return rootView;
    }

    /**
     * 绑定布局
     *
     * @return 布局文件id
     */
    public abstract int bindLayout();

    protected void setBackVisibility(int visible){
        if (back != null) {
            back.setVisibility(visible);
        } else {
            back = (LinearLayout) rootView.findViewById(R.id.ll_back);
            back.setVisibility(visible);
        }
    }
    protected void setTitleBarTitle(String title) {
        if (tvMainTitle != null) {
            tvMainTitle.setText(title);
        } else {
            tvMainTitle = (TextView) rootView.findViewById(R.id.tv_main_title);
            tvMainTitle.setText(title);
        }
    }

    protected void setRightTextVisibility(int visible) {
        if (tvRight != null) {
            tvRight.setVisibility(visible);
        } else {
            tvRight = (TextView) rootView.findViewById(R.id.tv_right);
            tvRight.setVisibility(visible);
        }
    }

    protected void setRightText(String title) {
        if (tvRight != null) {
            tvRight.setText(title);
        } else {
            tvRight = (TextView) rootView.findViewById(R.id.tv_right);
            tvRight.setText(title);
        }
    }

    /**
     * 初始化页面数据
     */
    public abstract void initData();
    public void initControll() {

    }

    public void setListener() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != rootView && null != rootView.getParent()) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
    }

    /**
     * 显示界面等待条
     */
    public void showInfoProgressDialog() {
        if (null == mInfoProgressDialog) {
            mInfoProgressDialog = new MyProcessDialog(getActivity());
        }
        if (!getActivity().isFinishing()) {
            try {
                mInfoProgressDialog.show();
            } catch (Exception e) {

            }
        }
    }

    /**
     * 关闭进度条
     */
    public void dismissInfoProgressDialog() {
        if (null != getActivity() && null != mInfoProgressDialog) {
            mInfoProgressDialog.dismiss();
        }
    }

    /**
     * 显示界面等待条
     */
    public void showProgressDialog() {
        if(null == getActivity()){
            return;
        }
        if (null == onLineDialog) {
            onLineDialog = CommonProgressDialog.showDialog(getActivity());
        }
        if (!getActivity().isFinishing()) {
            try {
                onLineDialog.show();
            } catch (Exception e) {

            }
        }
    }

    /**
     * 关闭进度条
     */
    public void dismissProgressDialog() {
        if (null != onLineDialog) {
            onLineDialog.dismiss();
        }
    }

}
