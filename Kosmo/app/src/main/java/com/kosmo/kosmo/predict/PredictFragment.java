package com.kosmo.kosmo.predict;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kosmo.kosmo.databinding.FragmentPredictMainBinding;

public class PredictFragment extends Fragment {

    FragmentPredictMainBinding predictMainBinding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        predictMainBinding = FragmentPredictMainBinding.inflate(getLayoutInflater());
        predictMainBinding.predictDiabetes.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), PredictDiabetesActivity.class);
            startActivity(intent);
        });
        predictMainBinding.predictCardiovascular.setOnClickListener(view ->{
            Intent intent = new Intent(view.getContext(), PredictCardiovascularActivity.class);
            startActivity(intent);
        });
        predictMainBinding.predictParkinson.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), PredictParkinsonActivity.class);
            startActivity(intent);
        });
        return predictMainBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        predictMainBinding = null;
    }
}
