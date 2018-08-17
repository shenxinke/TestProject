package com.yst.onecity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.adapter.CommomFragmentPagerAdapter;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.bean.consult.ConsultSortBean;
import com.yst.onecity.callbacks.AbstractConsultSortBeanCallBack;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.viewpagerindicator.ViewPagerIndicator;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 服务专员咨询列表分类
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class ServerMemberInformationFragment extends BaseFragment {

    ArrayList<String> sortList = new ArrayList<>();
    ArrayList<Fragment> fragments = new ArrayList<>();
    private CommomFragmentPagerAdapter adapter;

    @BindView(R.id.indicator)
    ViewPagerIndicator indicator;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.empty)
    TextView empty;

    private String page = "1";
    private String rows = "100";
    private int userId;
    private int type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userId = getArguments().getInt("id");
        type = getArguments().getInt("type");

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_server_member_information;
    }

    @Override
    public void initData() {
        getSort(userId, type);
    }


    private void getSort(final int userId, int type) {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("timestamp", timestamp, "userId", String.valueOf(userId),
                "page",page,"rows",rows,
                "userType", String.valueOf(type));
        OkHttpUtils.post().url(Constants.SERVER_MEMBER_SORT)
                .addParams("userId", String.valueOf(userId))
                .addParams("userType", String.valueOf(type))
                .addParams("page",page)
                .addParams("rows",rows)
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractConsultSortBeanCallBack() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getActivity().getResources().getString(R.string.str_net_error_message));
            }

            @Override
            public void onResponse(ConsultSortBean sortBean, int id) {
                if (sortBean.getContent().size() <= 0){
                    empty.setVisibility(View.VISIBLE);
                    empty.setText("暂无资讯");
                    viewpager.setVisibility(View.GONE);
                }else{
                    empty.setVisibility(View.GONE);
                    viewpager.setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < sortBean.getContent().size(); i++) {
                    sortList.add(sortBean.getContent().get(i).getConsultation_classify_name());
                    fragments.add(ServerMemberInformationContentFragment.newTypeInstance(userId, sortBean.getContent().get(i).getConsultation_classify_id()));
                }
                adapter = new CommomFragmentPagerAdapter(getChildFragmentManager(), fragments, sortList);
                viewpager.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                indicator.bindViewPager(viewpager, false);
            }
        });
    }
}
