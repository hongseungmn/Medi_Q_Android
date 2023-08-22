package com.kosmo.kosmo.analyze;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kosmo.kosmo.R;
import com.kosmo.kosmo.databinding.FragmentAnalyzeReport1ShapeBinding;
import com.kosmo.kosmo.databinding.FragmentAnalyzeReport1TypeBinding;

import java.util.ArrayList;

public class AnalyzeReportFragment_1_type extends Fragment implements View.OnClickListener {
    private FragmentAnalyzeReport1TypeBinding fragmentAnalyzeReport1TypeBinding;
    private AnalyzeReportFragment_1_type_Adaptor analyzeReportFragment_1_type_adaptor;
    AnalyzeReportFragment_1 analyzeReportFragment_1;
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentAnalyzeReport1TypeBinding = FragmentAnalyzeReport1TypeBinding.inflate(getLayoutInflater());
        String[] test = {"1","2","3","4","5","6","7","8"};
        analyzeReportFragment_1_type_adaptor = new AnalyzeReportFragment_1_type_Adaptor(test);
        fragmentAnalyzeReport1TypeBinding.analyzeReportTypeRecyclerView.setAdapter(analyzeReportFragment_1_type_adaptor);
        recyclerView = fragmentAnalyzeReport1TypeBinding.analyzeReportTypeRecyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        fragmentAnalyzeReport1TypeBinding.savePreferType.setOnClickListener(this);
        return fragmentAnalyzeReport1TypeBinding.getRoot();
    }

    @Override
    public void onClick(View view) {
        int totalSelectedCount = 0;
        ArrayList<String> chooseType = new ArrayList<>();
        for(int i = 0; i< analyzeReportFragment_1_type_adaptor.getItemCount(); i++) {
            LinearLayoutCompat cardView = (LinearLayoutCompat)recyclerView.getChildAt(i);
            if((boolean)cardView.findViewById(R.id.report_type_recyclerView_textview).getTag(R.id.is_selected)) {
                totalSelectedCount++;
                String choose = ((TextView)cardView.findViewById(R.id.report_type_recyclerView_textview)).getText().toString();
                chooseType.add(choose);
            }
        }
        Log.i("선택된 값의 개수 : ",totalSelectedCount+"");
        analyzeReportFragment_1 = new AnalyzeReportFragment_1();
        Bundle bundle = new Bundle();
        bundle.putInt("selectedCount",totalSelectedCount);
        bundle.putStringArrayList("chooseType", chooseType);
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        getActivity().getSupportFragmentManager().setFragmentResult("preferType",bundle);
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentAnalyzeReport1TypeBinding = null;
    }
}
