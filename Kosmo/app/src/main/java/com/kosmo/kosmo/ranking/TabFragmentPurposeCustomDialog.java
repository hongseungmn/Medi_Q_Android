package com.kosmo.kosmo.ranking;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.kosmo.kosmo.R;
import com.kosmo.kosmo.databinding.FragmentRankingTabNutrientBinding;
import com.kosmo.kosmo.databinding.FragmentRankingTabPurposeBinding;


public class TabFragmentPurposeCustomDialog extends Dialog {
    private FragmentRankingTabPurposeBinding binding;
    private TextView textView;
    public TabFragmentPurposeCustomDialog(@NonNull Context context, View textView) {
        super(context);
        this.textView = (TextView) textView;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ranking_tab_purpose_customdialog);
        TabFragmentCustomDialogUtil.dialogResize(getContext(),this,(float)1.0,(float)1.0);
        RadioGroup radioGroup_nutrient = findViewById(R.id.radioGroup_purpose);
        RadioButton radioButton_selected = findViewById(R.id.rg_pps_1);
        radioButton_selected.setSelected(true);
        binding = FragmentRankingTabPurposeBinding.inflate(getLayoutInflater());
        radioGroup_nutrient.setOnCheckedChangeListener((radioGroup, index) -> {
            RadioButton selectedRadioButton = findViewById(index);
            String selectedValue = selectedRadioButton.getText().toString();

            // 선택된 값 사용
            Log.d("Radio Group PurPose Selected Value", selectedValue);
            textView.setText(selectedValue);
            closeDialog();
        });
    }
    private void closeDialog() {
        new Handler().postDelayed(() -> dismiss(), 800);
    }
}
