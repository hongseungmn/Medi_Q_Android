package com.kosmo.kosmo.food;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kosmo.kosmo.databinding.FragmentFoodtableDetailTabBinding;
import com.kosmo.kosmo.model.dto.FoodListDTO;

public class TabFoodDetailFragment extends Fragment {
    private FragmentFoodtableDetailTabBinding fragmentFoodtableDetailTabBinding;
    private FoodSharedViewModel foodSharedViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentFoodtableDetailTabBinding = FragmentFoodtableDetailTabBinding.inflate(getLayoutInflater());
        getParentFragmentManager().setFragmentResultListener("sendFoodData", getActivity(),(requestKey, result) -> {
            FoodListDTO food = (FoodListDTO)result.getSerializable("food");
            fragmentFoodtableDetailTabBinding.material.setText(food.getMaterial().replace('$','\n').trim());
            fragmentFoodtableDetailTabBinding.shape.setText(food.getShape());
            fragmentFoodtableDetailTabBinding.intake.setText(food.getIntake());
            fragmentFoodtableDetailTabBinding.nutrient.setText(food.getNutrient());
            fragmentFoodtableDetailTabBinding.caution.setText(food.getCaution().replace('$','\n').trim());
            foodSharedViewModel.setSharedData(food.getNo());
        });
        return fragmentFoodtableDetailTabBinding.getRoot();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentFoodtableDetailTabBinding = null;
    }
    public void setSharedViewModel(FoodSharedViewModel foodSharedViewModel) {
        this.foodSharedViewModel = foodSharedViewModel;
    }

}
