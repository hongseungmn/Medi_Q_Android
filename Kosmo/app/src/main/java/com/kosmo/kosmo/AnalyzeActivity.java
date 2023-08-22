package com.kosmo.kosmo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kosmo.kosmo.analyze.AnalyzeReportFragment_1;
import com.kosmo.kosmo.analyze.AnalyzeReportFragment_1_purpose;
import com.kosmo.kosmo.analyze.AnalyzeResultFragment;
import com.kosmo.kosmo.databinding.ActivityAnalyzeBinding;

import java.util.ArrayList;

public class AnalyzeActivity extends AppCompatActivity {
    private ActivityAnalyzeBinding binding;
    public static Context analyzeContext;
    AnalyzeReportFragment_1 analyzeReportFragment_1;
    AnalyzeResultFragment analyzeResultFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        analyzeContext = getApplicationContext();
        binding = ActivityAnalyzeBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            analyzeResultFragment = new AnalyzeResultFragment();
            getSupportFragmentManager().setFragmentResult("report_2_selected_last",bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.analyze_containers,analyzeResultFragment).commit();
        }
        else {
            analyzeReportFragment_1 = new AnalyzeReportFragment_1();
            getSupportFragmentManager().beginTransaction().add(R.id.analyze_containers,analyzeReportFragment_1).commit();
        }
        root.findViewById(R.id.foodTableBackButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("버튼 눌림", "버튼 눌림");
                onBackPressed();
            }
        });

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.analyze_containers, fragment).addToBackStack(null).commit();      // Fragment로 사용할 MainActivity내의 layout공간을 선택합니다.
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}