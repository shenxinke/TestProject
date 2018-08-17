package com.yst.onecity.activity.member;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 发布状态  成功或失败
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/12/18
 */
public class PublishStateActivity extends BaseActivity {

    @BindView(R.id.iv_pubish_state)
    ImageView ivPublishState;
    @BindView(R.id.tv_pubish_state)
    TextView tvPubishState;
    @BindView(R.id.tv_again_edit)
    TextView tvAgainEdit;
    @BindView(R.id.tv_service_fail)
    TextView tvServiceFail;
    private int flag;

    @Override
    public int bindLayout() {
        return R.layout.activity_publish_state;
    }

    @Override
    public void initData() {
        initTitleBar("");
        flag = getIntent().getExtras().getInt("flag");
        if (flag == 0) {
            //失败
            tvPubishState.setText("发布失败");
            ivPublishState.setImageResource(R.mipmap.post_failure_img);
            tvServiceFail.setVisibility(View.VISIBLE);
            tvAgainEdit.setVisibility(View.VISIBLE);
        } else {
            ToastUtils.show("资讯发布成功，后台审核中！");
        }
    }

    @OnClick(R.id.tv_again_edit)
    public void onViewClicked() {
        if (flag == 1) {
            JumpIntent.jump(PublishStateActivity.this, PublishActivity.class);
        } else {
            PublishStateActivity.this.finish();
        }
    }

}
