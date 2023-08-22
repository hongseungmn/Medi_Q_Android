package com.kosmo.kosmo.analyze;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kosmo.kosmo.R;
import com.kosmo.kosmo.databinding.FragmentAnalyzeReport2Binding;
import com.kosmo.kosmo.model.dto.FoodListDTO;
import com.kosmo.kosmo.model.repository.FoodListRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.rxjava3.core.Single;

public class AnalyzeReportFragment_2 extends Fragment implements TextWatcher, View.OnClickListener, AutoCompleteResultRecyclerAdapter.OnItemClickListener, View.OnKeyListener {
    private FragmentAnalyzeReport2Binding fragmentAnalyzeReport2Binding;
    private FoodListRepository repository;
    private AutoCompleteRecyclerAdapter autoCompleteAdapter;
    private AutoCompleteResultRecyclerAdapter autoCompleteResultRecyclerAdapter;
    public static Set<String> selectedProductNoSet = new HashSet<>();
    private ArrayList<String> selected_takePurpose;
    private AnalyzeReportFragment_2_confirm analyzeReportFragment_2_confirm;

    @SuppressLint("CheckResult")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentAnalyzeReport2Binding = FragmentAnalyzeReport2Binding.inflate(getLayoutInflater());
        getActivity().getSupportFragmentManager().setFragmentResultListener(
                "report_1_selected_takePurpose",
                getActivity(), // 액티비티가 FragmentResultListener를 구현하므로 this를 사용
                (requestKey, result) -> {
                    // 이 부분에서 프래그먼트로부터 전달받은 결과를 처리하면 됨
                    // onFragmentResult() 메서드와 동일한 내용
                    if ("report_1_selected_takePurpose".equals(requestKey)) {
                        selected_takePurpose = result.getStringArrayList("report_1_selected_takePurpose");
                        Log.i("report_2에서 받음 :",selected_takePurpose.toString());
                        // 원하는 동작을 수행
                    }
                }
        );

        AutoCompleteTextView autoCompleteTextView = fragmentAnalyzeReport2Binding.report2SearchTextView;
        autoCompleteAdapter = new AutoCompleteRecyclerAdapter();
        RecyclerView recyclerView = fragmentAnalyzeReport2Binding.autoCompleteListview;
        recyclerView.setAdapter(autoCompleteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        autoCompleteResultRecyclerAdapter = new AutoCompleteResultRecyclerAdapter(getContext());
        RecyclerView resultRecyclerView = fragmentAnalyzeReport2Binding.searchResultListview;
        resultRecyclerView.setAdapter(autoCompleteResultRecyclerAdapter);
        autoCompleteResultRecyclerAdapter.setOnItemClickListener(this);
        resultRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        repository = new FoodListRepository();
        analyzeReportFragment_2_confirm = new AnalyzeReportFragment_2_confirm();
        autoCompleteTextView.addTextChangedListener(this);
        autoCompleteTextView.setOnClickListener(this);
        autoCompleteTextView.setOnKeyListener(this);
        autoCompleteAdapter.setOnItemClickListener((v, pos) -> {
            autoCompleteAdapter.clearItems();
            String searchWord = ((TextView)v).getText().toString();
            Single<List<FoodListDTO>> foodListSingle = repository.getFoodListBySearchKeyWord(searchWord);
            foodListSingle.subscribe(
                    foodList -> {
                        List<FoodListDTO> newDataList = new ArrayList<>();
                        for (FoodListDTO food : foodList) {
                            newDataList.add(food);
                        }
                        autoCompleteResultRecyclerAdapter.clearItems();
                        autoCompleteResultRecyclerAdapter.setDataList(newDataList);
                    },
                    throwable -> {
                        Log.i("받아온 값이 없어요","해당 값이 없어요");
                    }
            );
        });

        fragmentAnalyzeReport2Binding.toFragment3.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("report_1_selected_takePurpose",selected_takePurpose);
            ArrayList<String> selectedProductNoList = new ArrayList<>(selectedProductNoSet);
            bundle.putSerializable("report_2_selected_food",selectedProductNoList);
            getActivity().getSupportFragmentManager().setFragmentResult("report_2_selected",bundle);
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.analyze_containers,analyzeReportFragment_2_confirm).commit();
        });

        return fragmentAnalyzeReport2Binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentAnalyzeReport2Binding = null;
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
    @SuppressLint("CheckResult")
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String keyword = charSequence.toString();
        Single<List<FoodListDTO>> foodListSingle = repository.getFoodListBySearchKeyWord(keyword);
        foodListSingle.subscribe(
                foodList -> {
                    List<String> newDataList = new ArrayList<>();
                    for (FoodListDTO food : foodList) {
                        newDataList.add(food.getProductName());
                    }
                    autoCompleteAdapter.setDataList(newDataList);
                },
                throwable -> {
                    Log.i("받아온 값이 없어요","해당 값이 없어요");
                }
        );
    }
    @SuppressLint("CheckResult")
    @Override
    public void afterTextChanged(Editable editable) {}

    @Override
    public void onClick(View view) {
        autoCompleteResultRecyclerAdapter.clearItems();
    }

    @Override
    public void onItemClick(View v, int pos) {
        ImageView selectedImageView = v.findViewById(R.id.report_search_result_imageview);
        TextView selectedTextViewCompany = v.findViewById(R.id.report_search_result_productCompany);
        TextView selectedTextViewProductName = v.findViewById(R.id.report_search_result_productName);
        if(!(boolean)selectedImageView.getTag(R.id.is_selected)) {
            selectedImageView.setTag(R.id.is_selected,true);
            selectedImageView.setBackgroundResource(R.drawable.report_search_result_selected_bg);
            int color = ContextCompat.getColor(v.getContext(), R.color.orange);
            selectedTextViewProductName.setTextColor(color);
            selectedProductNoSet.add(selectedImageView.getTag(R.id.productNo).toString());
            Toast.makeText(getContext(), selectedTextViewProductName.getText()+"가 선택되었습니다.", Toast.LENGTH_SHORT).show();
        }
        else {
            selectedImageView.setTag(R.id.is_selected,false);
            selectedImageView.setBackgroundResource(R.drawable.report_search_result_bg);
            int color = ContextCompat.getColor(v.getContext(), R.color.black);
            selectedTextViewProductName.setTextColor(color);
            selectedProductNoSet.remove(selectedImageView.getTag(R.id.productNo).toString());
            Toast.makeText(getContext(), selectedTextViewProductName.getText()+"가 선택해제 되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

        return false;
    }
}
