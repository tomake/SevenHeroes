package com.sevenheroes.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sevenheroes.R;
import com.sevenheroes.adapter.TrainingPagerAdapter;
import com.sevenheroes.bean.Role;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;


public class TrainingFragment extends Fragment implements View.OnClickListener{

    private ViewPager mViewPager;
    private TabPageIndicator mTabIndicator;

    public static TrainingFragment newInstance() {
        return new TrainingFragment();
    }

    public TrainingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
                View rootView = inflater.inflate(R.layout.fragment_training, container, false) ;
        mViewPager = (ViewPager) rootView.findViewById(R.id.vpTraining);
        mTabIndicator = (TabPageIndicator) rootView.findViewById(R.id.tabPagerIndicator);

        initViewPager(inflater);
        return rootView;
    }

    private void initViewPager(LayoutInflater inflater) {
        ArrayList<View> pagerViews = new ArrayList<>();
        View v1 = inflater.inflate(R.layout.layout_wujiang_training , null);
        pagerViews.add(v1);
        View v2 = inflater.inflate(R.layout.layout_wujiang_training , null);
        pagerViews.add(v2);

        Role role1 = new Role();
        role1.setName("孙晓武");
        role1.setExperience("已获经验：0");
        role1.setLevel("10级");
        role1.setState("空闲");

        Role role2 = new Role();
        role2.setName("钟玄奇");
        role2.setExperience("已获经验：230");
        role2.setLevel("15级");
        role2.setState("忙碌");

        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role1);
        roles.add(role2);
        roles.add(role1);
        roles.add(role2);
        roles.add(role1);
        roles.add(role2);
        roles.add(role1);
        roles.add(role2);
        roles.add(role1);
        roles.add(role2);

        mViewPager.setAdapter(new TrainingPagerAdapter(getActivity() , pagerViews , roles , new String[]{"武将修炼" , "武将领悟"}));
        mTabIndicator.setViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {

    }

}
