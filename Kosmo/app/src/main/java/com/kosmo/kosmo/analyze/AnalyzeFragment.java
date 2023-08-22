package com.kosmo.kosmo.analyze;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import com.kosmo.kosmo.AnalyzeActivity;
import com.kosmo.kosmo.FoodTableActivity;
import com.kosmo.kosmo.R;
import com.kosmo.kosmo.databinding.FragmentAnalyzeMainBinding;
import com.kosmo.kosmo.model.dto.AnalyzeReportDTO;
import com.kosmo.kosmo.model.repository.AnalyzeReportRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import io.reactivex.rxjava3.core.Single;


public class AnalyzeFragment extends Fragment implements View.OnClickListener {
    private FragmentAnalyzeMainBinding binding;
    private AnalyzeReportRepository analyzeReportRepository;
    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentAnalyzeMainBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        SharedPreferences pref = getActivity().getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        if((pref!=null) && (pref.contains("userId"))) {
            String userId = pref.getString("userId", "");
            String userName = pref.getString("userName","");
            Log.i("kosmo.AnalyzeFragment", "저장된 사용자의 아이디" + userId);
            Log.i("kosmo.AnalyzeFragment", "저장된 사용자의 이름 : " + userName);
            TextView title = (TextView) root.findViewById(R.id.analyze_fragment_userName);
            //title.setText(userName+"님의 \n영양제 분석 리포트");
            title.setText("김태현"+"님의 \n영양제 분석 리포트");
            analyzeReportRepository = new AnalyzeReportRepository();
            Single<List<AnalyzeReportDTO>> analyzeReportList = analyzeReportRepository.getAnalyzeReportAll("sem50000");
            analyzeReportList.subscribe(
                    analyzeReportDTOS -> {
                        Log.i("com.kosmo.AnalyzeFragment","분석 리스트 가져옴"+analyzeReportDTOS);
                        LinearLayoutCompat listView = (LinearLayoutCompat) root.findViewById(R.id.analyze_fragment_analyze_linearView);
                        for(AnalyzeReportDTO dto : analyzeReportDTOS) {
                            View newLayout = inflater.inflate(R.layout.analyze_report_layout, (ViewGroup) root, false);
                            TextView analyzeDate = (TextView) newLayout.findViewById(R.id.analyze_fragment_analyze_report_date);
                            analyzeDate.setText(dto.getAnalyzeDate());
                            TextView analyzeScore = (TextView) newLayout.findViewById(R.id.analyze_fragment_analyze_report_score);
                            analyzeScore.setText(dto.getScore()+" 점");
                            TextView analyzePurpose = (TextView) newLayout.findViewById(R.id.analyze_fragment_analyze_report_purpose);
                            analyzePurpose.setText(dto.getTakePurposes());
                            TextView analyzeFood = (TextView) newLayout.findViewById(R.id.analyze_fragment_analyze_report_food);
                            analyzeFood.setText(dto.getTakeFoods());
                            newLayout.setOnClickListener(this);

                            listView.addView(newLayout);
                        }
                    },
                    throwable -> {
                        Log.i("com.kosmo.AnalyzeFragment","총 분석 리스트 가져오기 실패"+throwable.getMessage());
                    }
            );

        }

        binding.analyzeNewReport.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), AnalyzeActivity.class);
            startActivity(intent);

        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {


        TextView analyzePurpose = (TextView) view.findViewById(R.id.analyze_fragment_analyze_report_purpose);
        TextView analyzeFood = (TextView) view.findViewById(R.id.analyze_fragment_analyze_report_food);
        Log.i("com.kosmo.analyzePurpose",analyzePurpose.getText().toString());
        Log.i("com.kosmo.analyzeFood",analyzeFood.getText().toString());
        Bundle bundle = new Bundle();

        List<String> arrayList = Arrays.asList(analyzePurpose.getText().toString().replace("[", "").replace("]", "").split(","));
        bundle.putStringArrayList("report_1_selected_takePurpose", new ArrayList<>(arrayList));

        List<String> selectedProductNoList = Arrays.asList(analyzeFood.getText().toString().replace("[", "").replace("]", "").split(","));
        bundle.putStringArrayList("report_2_selected_food", new ArrayList<>(selectedProductNoList));

        Intent intent = new Intent(view.getContext(), AnalyzeActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}