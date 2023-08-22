package com.kosmo.kosmo.analyze;

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
import com.kosmo.kosmo.databinding.FragmentAnalyzeReport1ShapeBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class AnalyzeReportFragment_1_shape extends Fragment implements View.OnClickListener {
    private FragmentAnalyzeReport1ShapeBinding fragmentAnalyzeReport1ShapeBinding;
    private AnalyzeReportFragment_1_shape_Adaptor analyzeReportFragment_1_shape_adaptor;
    AnalyzeReportFragment_1 analyzeReportFragment_1;
    private RecyclerView recyclerView;

    private String[] stringPreferShape;
    private TypedArray imagePreferShapeResIds;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentAnalyzeReport1ShapeBinding = FragmentAnalyzeReport1ShapeBinding.inflate(getLayoutInflater());
        stringPreferShape = getResources().getStringArray(R.array.prefer_shape_string_ids);
        imagePreferShapeResIds = getResources().obtainTypedArray(R.array.prefer_shape_img_res_ids);
        analyzeReportFragment_1_shape_adaptor = new AnalyzeReportFragment_1_shape_Adaptor(stringPreferShape,imagePreferShapeResIds);
        fragmentAnalyzeReport1ShapeBinding.analyzeReportShapeRecyclerView.setAdapter(analyzeReportFragment_1_shape_adaptor);
        recyclerView = fragmentAnalyzeReport1ShapeBinding.analyzeReportShapeRecyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        fragmentAnalyzeReport1ShapeBinding.savePreferShape.setOnClickListener(this);
        return fragmentAnalyzeReport1ShapeBinding.getRoot();
    }

    @Override
    public void onClick(View view) {
        int totalSelectedCount = 0;
        ArrayList<String> chooseShape = new ArrayList<>();
        for(int i = 0; i< analyzeReportFragment_1_shape_adaptor.getItemCount(); i++) {
            LinearLayoutCompat cardView = (LinearLayoutCompat)recyclerView.getChildAt(i);
            if((boolean)cardView.findViewById(R.id.report_shape_recyclerView_imageview).getTag(R.id.is_selected)) {
                totalSelectedCount++;
                String choose = ((TextView)cardView.findViewById(R.id.report_shape_recyclerView_textview)).getText().toString();
                chooseShape.add(choose);
            }
        }
        Log.i("선택된 값의 개수 : ",totalSelectedCount+"");
        analyzeReportFragment_1 = new AnalyzeReportFragment_1();
        Bundle bundle = new Bundle();
        bundle.putInt("selectedCount",totalSelectedCount);
        bundle.putStringArrayList("chooseShape", chooseShape);
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        getActivity().getSupportFragmentManager().setFragmentResult("preferShape",bundle);
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentAnalyzeReport1ShapeBinding = null;
    }
}
