package com.yst.onecity.fragment.popfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.yst.onecity.R;
import com.yst.onecity.utils.WindowUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author WangJingWei
 * @version TODO
 * @date 2018/6/6.
 */

public class TransDialogFragment extends DialogFragment {

    private Unbinder unbinder;

    public static TransDialogFragment newInstance() {
        TransDialogFragment fragment = new TransDialogFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Window window = getDialog().getWindow();
        getDialog().setCanceledOnTouchOutside(true);
        View view = inflater.inflate(R.layout.fragment_dialog_trans, ((ViewGroup) window.findViewById(android.R.id.content)), false);
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = (int) (WindowUtils.getScreenHeight(getActivity()) * 0.2);
        window.setAttributes(wlp);
        window.setWindowAnimations(R.style.add_cart_pop_anim_style);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
}
