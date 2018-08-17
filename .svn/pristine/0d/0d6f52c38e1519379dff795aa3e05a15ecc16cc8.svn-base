package com.yst.onecity.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import com.yst.onecity.R;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.eventbus.RefreshSearchEvent;
import com.yst.onecity.fragment.SearchInformationContentFragment;
import com.yst.onecity.fragment.SearchServiceSpecialistInteractionFragment;
import com.yst.onecity.utils.KeyBoardUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.ContainsEmojiEditText;
import com.yst.onecity.view.viewpagerindicator.ViewPagerIndicator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 搜索界面
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/12/18
 */
public class SearchActivity extends BaseActivity {

    ArrayList<String> items = new ArrayList<>();
    ArrayList<Fragment> fragments = new ArrayList<>();

    @BindView(R.id.et_input_content)
    ContainsEmojiEditText etInputContent;
    @BindView(R.id.search_indicator)
    ViewPagerIndicator searchIndicator;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private String searchContent;

    @Override
    public int bindLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void initData() {
        KeyBoardUtils.showSoftInputFromWindow(this, etInputContent);
        searchIndicator.setVisibility(View.GONE);
        viewpager.setVisibility(View.GONE);
        searchIndicator.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @OnClick({R.id.back, R.id.searchTV, R.id.searchRL})
    public void onViewClicked(View view) {
        if(!Utils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.searchTV:
                items.clear();
                fragments.clear();
                items.add("资讯");
                items.add("服务专员");

                searchContent = etInputContent.getText().toString().trim();

                if (TextUtils.isEmpty(searchContent)){
                    ToastUtils.show("请输入您要搜索的内容");
                    searchIndicator.setVisibility(View.GONE);
                    viewpager.setVisibility(View.GONE);
                    return;
                }
                EventBus.getDefault().post(new RefreshSearchEvent(searchContent));
                Bundle args = new Bundle();
                args.putString("searchText", searchContent);
                args.putInt("type", 2);
                SearchInformationContentFragment fragment = new SearchInformationContentFragment();
                fragment.setArguments(args);
                fragments.add(fragment);
                SearchServiceSpecialistInteractionFragment searchServerMemberFragment = new SearchServiceSpecialistInteractionFragment();
                Bundle bundle = new Bundle();
                bundle.putString("searchText",searchContent);
                searchServerMemberFragment.setArguments(bundle);
///                fragments.add(searchServerMemberFragment);
                viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
                    @Override
                    public Fragment getItem(int position) {
                        return fragments.get(position);
                    }

                    @Override
                    public int getCount() {
                        return fragments.size();
                    }

                    @Override
                    public CharSequence getPageTitle(int position) {
                        return items.get(position);
                    }
                });
                searchIndicator.bindViewPager(viewpager,true);

                searchIndicator.setVisibility(View.VISIBLE);
                viewpager.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
}
