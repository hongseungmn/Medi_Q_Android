package com.kosmo.kosmo.analyze;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kosmo.kosmo.R;
import com.kosmo.kosmo.databinding.FragmentAnalyzeReport2ResultBinding;
import com.kosmo.kosmo.model.dto.FoodListDTO;
import com.kosmo.kosmo.model.repository.FoodListRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class AnalyzeReportFragment_2_confirm extends Fragment implements AnalyzeReportFragment_2_confirm_Adaptor.OnItemClickListener, View.OnClickListener {
    private FragmentAnalyzeReport2ResultBinding fragmentAnalyzeReport2ResultBinding;
    private AnalyzeReportFragment_2_confirm_Adaptor analyzeReportFragment_2_confirm_adaptor;
    private ArrayList<String> selected_takePurpose;
    private HashSet<String> selectedProductNoSet;
    private AnalyzeResultFragment analyzeResultFragment;
    private FoodListRepository repository;
    @SuppressLint("CheckResult")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentAnalyzeReport2ResultBinding = FragmentAnalyzeReport2ResultBinding.inflate(getLayoutInflater());
        getActivity().getSupportFragmentManager().setFragmentResultListener(
                "report_2_selected",
                getActivity(),
                (requestKey, result) -> {

                    if ("report_2_selected".equals(requestKey)) {
                        selected_takePurpose = result.getStringArrayList("report_1_selected_takePurpose");
                        ArrayList<String> arrayList = result.getStringArrayList("report_2_selected_food");
                        selectedProductNoSet = new HashSet<>(arrayList);
                        Log.i("report_2에서 받음 : ",selected_takePurpose.toString());
                        Log.i("report_2에서 받음 : ",selectedProductNoSet.toString());
                    }
                }
        );
        repository = new FoodListRepository();
        List<FoodListDTO> chooseFoodList = new ArrayList<>();
        for (String no : selectedProductNoSet) {
            Single<FoodListDTO> foodSingle = repository.getFood(no);
            foodSingle.subscribe(
                    foodList -> {
                        chooseFoodList.add(foodList);
                        analyzeReportFragment_2_confirm_adaptor.setDataList(chooseFoodList);
                    },
                    throwable -> {
                        Log.i("받아온 값이 없어요","해당 값이 없어요");
                    }
            );
        }
        fragmentAnalyzeReport2ResultBinding.totalFoodCount.setText("💊 홍성민님의 영양제가 총 "+ selectedProductNoSet.size() +"개 등록되었습니다");
        analyzeResultFragment = new AnalyzeResultFragment();
        analyzeReportFragment_2_confirm_adaptor = new AnalyzeReportFragment_2_confirm_Adaptor(getContext());
        RecyclerView resultRecyclerView = fragmentAnalyzeReport2ResultBinding.confirmResultListview;
        resultRecyclerView.setAdapter(analyzeReportFragment_2_confirm_adaptor);
        analyzeReportFragment_2_confirm_adaptor.setOnItemClickListener(this);
        resultRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        fragmentAnalyzeReport2ResultBinding.requestAnalyze.setOnClickListener(this);
        return fragmentAnalyzeReport2ResultBinding.getRoot();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentAnalyzeReport2ResultBinding = null;
    }

    @Override
    public void onItemClick(View v, int pos) {

    }

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("report_1_selected_takePurpose",selected_takePurpose);
        ArrayList<String> selectedProductNoList = new ArrayList<>(selectedProductNoSet);
        bundle.putSerializable("report_2_selected_food",selectedProductNoList);
        getActivity().getSupportFragmentManager().setFragmentResult("report_2_selected_last",bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.analyze_containers,analyzeResultFragment).commit();
    }
}
