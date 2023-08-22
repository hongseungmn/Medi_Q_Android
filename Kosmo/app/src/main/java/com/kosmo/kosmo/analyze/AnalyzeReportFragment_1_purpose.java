package com.kosmo.kosmo.analyze;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
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
import com.kosmo.kosmo.databinding.FragmentAnalyzeReport1PurposeBinding;

import java.util.ArrayList;

public class AnalyzeReportFragment_1_purpose extends Fragment implements View.OnClickListener {
    AnalyzeReportFragment_1 analyzeReportFragment_1;
    private FragmentAnalyzeReport1PurposeBinding fragmentAnalyzeReport1PurposeBinding;
    private AnalyzeReportFragment_1_purpose_Adaptor analyzeReportFragment_1_purpose_adaptor;
    private RecyclerView recyclerView;
    private String[] stringTakePurpose;
    private TypedArray imageTakePurposeResIds;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentAnalyzeReport1PurposeBinding = FragmentAnalyzeReport1PurposeBinding.inflate(getLayoutInflater());
        stringTakePurpose = getResources().getStringArray(R.array.take_purpose_string_ids);
        imageTakePurposeResIds = getResources().obtainTypedArray(R.array.take_purpose_img_res_ids);
        analyzeReportFragment_1_purpose_adaptor = new AnalyzeReportFragment_1_purpose_Adaptor(stringTakePurpose,imageTakePurposeResIds);
        fragmentAnalyzeReport1PurposeBinding.analyzeReportPurposeRecyclerView.setAdapter(analyzeReportFragment_1_purpose_adaptor);
        recyclerView = fragmentAnalyzeReport1PurposeBinding.analyzeReportPurposeRecyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        fragmentAnalyzeReport1PurposeBinding.saveTakePurpose.setOnClickListener(this);
        return fragmentAnalyzeReport1PurposeBinding.getRoot();
    }




    @SuppressLint("ResourceType")
    @Override
    public void onClick(View view) {
        int totalSelectedCount = 0;
        ArrayList<String> chooseTakePurpose = new ArrayList<>();
        for(int i = 0; i< analyzeReportFragment_1_purpose_adaptor.getItemCount(); i++) {
            LinearLayoutCompat cardView = (LinearLayoutCompat)recyclerView.getChildAt(i);
            if(cardView != null &&(boolean)cardView.findViewById(R.id.report_purpose_recyclerView_imageview).getTag(R.id.is_selected)) {
                totalSelectedCount++;
                String choose = ((TextView)cardView.findViewById(R.id.report_purpose_recyclerView_textview)).getText().toString();
                chooseTakePurpose.add(choose);
            }
        }
        Log.i("선택된 값의 개수 : ",totalSelectedCount+"");
        analyzeReportFragment_1 = new AnalyzeReportFragment_1();
        Bundle bundle = new Bundle();
        bundle.putInt("selectedCount",totalSelectedCount);
        bundle.putStringArrayList("chooseTakePurpose", chooseTakePurpose);
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        getActivity().getSupportFragmentManager().setFragmentResult("takePurposeCount",bundle);
        getActivity().getSupportFragmentManager().popBackStack();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentAnalyzeReport1PurposeBinding = null;
    }

}
