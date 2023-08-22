package com.kosmo.kosmo.ranking;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.kosmo.kosmo.databinding.FragmentRankingMainBinding;
import com.kosmo.kosmo.model.repository.FoodListRepository;

import java.util.Arrays;
import java.util.List;

public class RankingFragment extends Fragment {
    FragmentRankingMainBinding fragmentRankingMainBinding;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        fragmentRankingMainBinding = FragmentRankingMainBinding.inflate(getLayoutInflater());
        TabLayout tabLayout = fragmentRankingMainBinding.tabLayout;
        tabLayout.addTab(tabLayout.newTab().setText("전체"));
        tabLayout.addTab(tabLayout.newTab().setText("건강 고민별"));
        tabLayout.addTab(tabLayout.newTab().setText("성분별"));

        TabFragmentAll tabFragmentAll = new TabFragmentAll();
        TabFragmentPurpose tabFragmentPurpose = new TabFragmentPurpose();
        TabFragmentNutrient tabFragmentNutrient = new TabFragmentNutrient();
        //3.PageAdapter 객체 생성
        List<Fragment> fragments = Arrays.asList(tabFragmentAll,tabFragmentPurpose,tabFragmentNutrient);
        RankingTabAdapter myPagerAdapter = new RankingTabAdapter(this,fragments);

        //4.ViewPager에 PageAdaper를 연결
        fragmentRankingMainBinding.viewPager.setAdapter(myPagerAdapter);
        fragmentRankingMainBinding.viewPager.setSaveEnabled(false);
        fragmentRankingMainBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragmentRankingMainBinding.viewPager.setCurrentItem(tab.getPosition());
                Log.i("com.kosmo.kosmo.ranking","탭이 선택됨");

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });



        return fragmentRankingMainBinding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentRankingMainBinding = null;
    }
}
