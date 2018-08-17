package com.yst.onecity.activity.member;

import android.view.View;

import com.yst.onecity.R;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.utils.JumpIntent;

import butterknife.OnClick;

/**
 * 我的发布
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/12/18
 */
public class MyPublishActivity extends BaseActivity {

    @Override
    public int bindLayout() {
        return R.layout.activity_my_publish;
    }

    @Override
    public void initData() {
        initTitleBar("我的发布");

    }

    @OnClick({R.id.rl_publish_list, R.id.rl_draft_box})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //发布列表
            case R.id.rl_publish_list:
                JumpIntent.jump(this, PublishActivity.class);
                break;
            //草稿箱
            case R.id.rl_draft_box:
                JumpIntent.jump(this, DraftBoxActivity.class);
                break;
            default:
                break;
        }
    }
}
