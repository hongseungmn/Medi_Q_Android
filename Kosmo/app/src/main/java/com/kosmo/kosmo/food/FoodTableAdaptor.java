package com.kosmo.kosmo.food;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class FoodTableAdaptor extends FragmentStateAdapter {
    private List<Fragment> fragments;
    private FoodSharedViewModel foodSharedViewModel;

    public FoodTableAdaptor(@NonNull FragmentActivity activity, List<Fragment> fragments) {
        super(activity);
        this.fragments = fragments;

        // ViewModel 인스턴스 생성
        foodSharedViewModel = new ViewModelProvider(activity).get(FoodSharedViewModel.class);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = fragments.get(position);
        // 각 프래그먼트에 ViewModel 인스턴스 전달
        if (fragment instanceof TabFoodReviewFragment) {
            ((TabFoodReviewFragment) fragment).setSharedViewModel(foodSharedViewModel);
        }
        else if(fragment instanceof  TabFoodDetailFragment) {
            ((TabFoodDetailFragment) fragment).setSharedViewModel(foodSharedViewModel);
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}

