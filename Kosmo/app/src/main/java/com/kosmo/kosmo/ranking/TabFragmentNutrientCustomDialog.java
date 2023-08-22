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


public class TabFragmentNutrientCustomDialog extends Dialog {
    private FragmentRankingTabNutrientBinding binding;

    private TextView textView;
    public TabFragmentNutrientCustomDialog(@NonNull Context context, View textView) {
        super(context);
        this.textView = (TextView) textView;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ranking_tab_nutrient_customdialog);
        TabFragmentCustomDialogUtil.dialogResize(getContext(),this,(float)1.0,(float)1.0);
        RadioGroup radioGroup_nutrient = findViewById(R.id.radioGroup_nutrient);
        RadioButton radioButton_selected = findViewById(R.id.rg_vtm_a);
        radioButton_selected.setSelected(true);
        binding = FragmentRankingTabNutrientBinding.inflate(getLayoutInflater());
        radioGroup_nutrient.setOnCheckedChangeListener((radioGroup, index) -> {
            RadioButton selectedRadioButton = findViewById(index);
            String selectedValue = selectedRadioButton.getText().toString();

            // 선택된 값 사용
            Log.d("Radio Group Nutrient Selected Value", selectedValue);
            textView.setText(selectedValue);
            closeDialog();
        });
    }
    private void closeDialog() {
        new Handler().postDelayed(() -> dismiss(), 800);
    }
}
