package com.kosmo.kosmo.analyze;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.kosmo.kosmo.AnalyzeActivity;
import com.kosmo.kosmo.MainActivity;
import com.kosmo.kosmo.R;
import com.kosmo.kosmo.databinding.FragmentAnalyzeReport1Binding;
import com.kosmo.kosmo.databinding.FragmentAnalyzeReport1PurposeBinding;
//import com.kosmo.kosmo.main.MainWebViewFragment;

import java.util.ArrayList;

public class AnalyzeReportFragment_1 extends Fragment {
    private FragmentAnalyzeReport1Binding fragmentAnalyzeReport1Binding;
    private AnalyzeReportFragment_1_purpose analyzeReportFragment_1_purpose;
    private AnalyzeReportFragment_1_shape analyzeReportFragment_1_shape;
    private AnalyzeReportFragment_1_type analyzeReportFragment_1_type;
    private AnalyzeReportFragment_2 analyzeReportFragment_2;
    private ArrayList<String> selected_takePurpose;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentAnalyzeReport1Binding = FragmentAnalyzeReport1Binding.inflate(getLayoutInflater());

        analyzeReportFragment_1_purpose = new AnalyzeReportFragment_1_purpose();
        analyzeReportFragment_1_shape = new AnalyzeReportFragment_1_shape();
        analyzeReportFragment_1_type = new AnalyzeReportFragment_1_type();
        analyzeReportFragment_2 = new AnalyzeReportFragment_2();

        fragmentAnalyzeReport1Binding.takePurposeBtn.setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.analyze_containers,analyzeReportFragment_1_purpose).commit();
        });
        fragmentAnalyzeReport1Binding.preferShapeBtn.setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.analyze_containers,analyzeReportFragment_1_shape).commit();
        });
        fragmentAnalyzeReport1Binding.preferTypeBtn.setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.analyze_containers,analyzeReportFragment_1_type).commit();
        });
        fragmentAnalyzeReport1Binding.toFragment2.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("report_1_selected_takePurpose",selected_takePurpose);
            getActivity().getSupportFragmentManager().setFragmentResult("report_1_selected_takePurpose",bundle);
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.analyze_containers,analyzeReportFragment_2).commit();

        });

        getActivity().getSupportFragmentManager().setFragmentResultListener(
                "takePurposeCount",
                getActivity(), // 액티비티가 FragmentResultListener를 구현하므로 this를 사용
                (requestKey, result) -> {
                    // 이 부분에서 프래그먼트로부터 전달받은 결과를 처리하면 됨
                    // onFragmentResult() 메서드와 동일한 내용
                    if ("takePurposeCount".equals(requestKey)) {
                        String purposeCount = result.getInt("selectedCount")+"";
                        ArrayList<String> chooseTakePurpose = result.getStringArrayList("chooseTakePurpose");
                        selected_takePurpose = chooseTakePurpose;
                        Log.i("돌아온 뒤 :",purposeCount);
                        TextView countTextView = fragmentAnalyzeReport1Binding.takePurposeCount;
                        TextView stringTextView = fragmentAnalyzeReport1Binding.takePurposeText;
                        stringTextView.setText(chooseTakePurpose.toString());
                        countTextView.setText(purposeCount);
                        // 원하는 동작을 수행
                    }
                }
        );

        getActivity().getSupportFragmentManager().setFragmentResultListener(
                "preferShape",
                getActivity(),
                (requestKey, result) -> {
                    if ("preferShape".equals(requestKey)) {
                        String purposeCount = String.valueOf(result.getInt("selectedCount"));
                        Log.i("선택한 값 개수 :",purposeCount);
                        ArrayList<String> chooseShape = result.getStringArrayList("chooseShape");
                        Log.i("선택한 값 개수 :",chooseShape.toString());
                        TextView preferShapeTextView = fragmentAnalyzeReport1Binding.preferShapeText;
                        preferShapeTextView.setText(chooseShape.toString());
                        // 원하는 동작을 수행
                    }
                }
        );

        getActivity().getSupportFragmentManager().setFragmentResultListener(
                "preferType",
                getActivity(),
                (requestKey, result) -> {
                    if ("preferType".equals(requestKey)) {
                        String purposeCount = String.valueOf(result.getInt("selectedCount"));
                        Log.i("선택한 값 개수 :",purposeCount);
                        ArrayList<String> chooseShape = result.getStringArrayList("chooseType");
                        Log.i("선택한 값 개수 :",chooseShape.toString());
                        TextView preferTypeTextView = fragmentAnalyzeReport1Binding.preferTypeText;
                        preferTypeTextView.setText(chooseShape.toString());
                        // 원하는 동작을 수행
                    }
                }
        );



        return fragmentAnalyzeReport1Binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentAnalyzeReport1Binding = null;
    }


}
