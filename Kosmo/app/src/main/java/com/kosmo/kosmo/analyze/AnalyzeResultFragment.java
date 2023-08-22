package com.kosmo.kosmo.analyze;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kosmo.kosmo.R;
import com.kosmo.kosmo.databinding.FragmentAnalyzeReportFinalResultBinding;
import com.kosmo.kosmo.model.dto.AnalyzeResultDTO;
import com.kosmo.kosmo.model.dto.AnalyzeResultListDTO;
import com.kosmo.kosmo.model.repository.FoodListRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;

public class AnalyzeResultFragment extends Fragment {

    private ArrayList<String> selected_takePurpose;
    private HashSet<String> selectedProductNoSet;
    private FoodListRepository repository;
    private FragmentAnalyzeReportFinalResultBinding fragmentAnalyzeReportFinalResultBinding;
    private AnalyzeResultFragmentRecyclerAdapter analyzeResultFragmentRecyclerAdapter;
    @SuppressLint({"CheckResult", "SetTextI18n"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentAnalyzeReportFinalResultBinding = FragmentAnalyzeReportFinalResultBinding.inflate(getLayoutInflater());

        repository = new FoodListRepository();
        getActivity().getSupportFragmentManager().setFragmentResultListener(
                "report_2_selected_last",
                getActivity(),
                (requestKey, results) -> {
                    if ("report_2_selected_last".equals(requestKey)) {
                        selected_takePurpose = results.getStringArrayList("report_1_selected_takePurpose");
                        ArrayList<String> arrayList = results.getStringArrayList("report_2_selected_food");
                        selectedProductNoSet = new HashSet<>(arrayList);
                        Log.i("마지막 결과 report_2에서 받음 : ",selected_takePurpose.toString());
                        Log.i("마지막 결과 report_2에서 받음 : ",selectedProductNoSet.toString());

                        Single<AnalyzeResultListDTO> map = repository.getAnalyzeResultReport(selected_takePurpose.toString(),selectedProductNoSet.toString());
                        map.subscribe(
                                result->{
                                    Log.i("분석 요청 결과 받기 성공", result.toString());
                                    AnalyzeResultListDTO analyzeResultListDTO = (AnalyzeResultListDTO) result;
                                    Log.i("AnalyzeResultListDTO : ",analyzeResultListDTO.getResultScore()+"");
                                    Log.i("AnalyzeResultListDTO : ",analyzeResultListDTO.getListdto().toString()+"");
                                    Log.i("AnalyzeResultListDTO : ",analyzeResultListDTO.getIngredientCount()+"");
                                    Log.i("AnalyzeResultListDTO : ", analyzeResultListDTO.getNutIntakeDTOs().toString()+"");
                                    Log.i("AnalyzeResultListDTO : ", analyzeResultListDTO.getNutrient_list_report().toString()+"");
                                    Log.i("AnalyzeResultListDTO : ", analyzeResultListDTO.getNutrient_list_no_report().toString()+"");
                                    // Map에서 "result" 키로 값을 가져옴

                                    //DATE

                                    String currentDate = getCurrentDate();
                                    fragmentAnalyzeReportFinalResultBinding.resultDate.setText(currentDate);

                                    //COMMENT 1
                                    fragmentAnalyzeReportFinalResultBinding.resultScore.setText((int)analyzeResultListDTO.getResultScore()+"");
                                    // SpannableString 생성
                                    SpannableString spannableString = new SpannableString("▪ 홍성민 님은 "+analyzeResultListDTO.getListdto().size()+"개의 목적을 위해 영양제를 섭취 중이시네요!");
                                    // 스타일 적용
                                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.BLACK); // 글자 색상을 빨간색으로 지정
                                    StyleSpan boldSpan = new StyleSpan(Typeface.BOLD); // 글자를 진하게(Bold) 지정
                                    AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(80);
                                    // 특정 범위에 스타일 적용
                                    spannableString.setSpan(colorSpan, 9, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // 0~4번째 글자에 색상 적용
                                    spannableString.setSpan(boldSpan, 9, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // 7~20번째 글자에 진하게(Bold) 적용
                                    spannableString.setSpan(sizeSpan,9,10,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    // TextView에 SpannableString 적용
                                    fragmentAnalyzeReportFinalResultBinding.resultComment1.setText(spannableString);

                                    //COMMENT 2
                                    SpannableString spannableString2 = new SpannableString("▪ 현재 섭취하고 있는 영양제의 수는 "+ (int)analyzeResultListDTO.getIngredientCount() +" 개입니다.");
                                    ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(Color.BLACK); // 글자 색상을 빨간색으로 지정
                                    StyleSpan boldSpan2 = new StyleSpan(Typeface.BOLD); // 글자를 진하게(Bold) 지정
                                    AbsoluteSizeSpan sizeSpan2 = new AbsoluteSizeSpan(80);
                                    // 특정 범위에 스타일 적용
                                    spannableString2.setSpan(colorSpan2, 21, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // 0~4번째 글자에 색상 적용
                                    spannableString2.setSpan(boldSpan2, 21, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // 7~20번째 글자에 진하게(Bold) 적용
                                    spannableString2.setSpan(sizeSpan2,21,22,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    // TextView에 SpannableString 적용
                                    fragmentAnalyzeReportFinalResultBinding.resultComment2.setText(spannableString2);

                                    //COMMENT 3
                                    int totalNutirent = 0;
                                    int totalIngredient = 0;
                                    for(AnalyzeResultDTO dto : analyzeResultListDTO.getListdto()) {
                                        totalNutirent += dto.getFoodForHelpPurpose().size();
                                    }
                                    SpannableString spannableString3 = new SpannableString("▪ 영양제를 통해 섭취하고 있는 \n ▪ 기능성 원료는 "+totalNutirent+" 개, 5대 영양소 총 "+ analyzeResultListDTO.getNutrient_list_report().size() +" 개 이고\n" +
                                            "▪ 따라서 합산한 총 점수는 "+(int)analyzeResultListDTO.getResultScore()+" 점 입니다");
                                    fragmentAnalyzeReportFinalResultBinding.resultComment3.setText(spannableString3);

                                    //RECYCLER VIEW

                                    analyzeResultFragmentRecyclerAdapter = new AnalyzeResultFragmentRecyclerAdapter(getContext(),analyzeResultListDTO.getListdto());
                                    RecyclerView resultRecyclerView = fragmentAnalyzeReportFinalResultBinding.resultReportRecycleview;
                                    resultRecyclerView.setAdapter(analyzeResultFragmentRecyclerAdapter);
                                    resultRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                                    //SCROLL VIEW
                                    NestedScrollView takeGoodNutrientScrollView = (NestedScrollView)fragmentAnalyzeReportFinalResultBinding.takeGoodNutrient;
                                    NestedScrollView takeBadNutrientScrollView = (NestedScrollView)fragmentAnalyzeReportFinalResultBinding.takeBadNutrient;
                                    List<String> takeGoodNutrientList = analyzeResultListDTO.getNutrient_list_report();
                                    List<String> takebadNutrientList = analyzeResultListDTO.getNutrient_list_no_report();
                                    LinearLayout rootLayout1 = new LinearLayout(getContext());
                                    rootLayout1.setLayoutParams(new LinearLayout.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT
                                    ));
                                    rootLayout1.setOrientation(LinearLayout.VERTICAL);
                                    for(String nutrient : takeGoodNutrientList) {


                                        // 첫 번째 TextView 생성
                                        TextView titleTextView = new TextView(getContext());
                                        titleTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT
                                        ));
                                        titleTextView.setText("✅ "+nutrient);
                                        titleTextView.setPadding(5, 5, 5, 5);
                                        titleTextView.setTextColor(Color.BLACK);
                                        titleTextView.setTextSize(12);
                                        titleTextView.setTypeface(null, Typeface.BOLD);

                                        // 루트 레이아웃에 첫 번째 TextView 추가
                                        rootLayout1.addView(titleTextView);
                                        // 액티비티의 루트 레이아웃 설정

                                    }
                                    takeGoodNutrientScrollView.addView(rootLayout1);
                                    LinearLayout rootLayout2 = new LinearLayout(getContext());
                                    rootLayout2.setLayoutParams(new LinearLayout.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT
                                    ));
                                    rootLayout2.setOrientation(LinearLayout.VERTICAL);
                                    for(String nutrient : takebadNutrientList) {
                                        // 첫 번째 TextView 생성
                                        TextView titleTextView = new TextView(getContext());
                                        titleTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT
                                        ));
                                        titleTextView.setText("❌ "+nutrient);
                                        titleTextView.setPadding(5, 5, 5, 5);
                                        titleTextView.setTextColor(Color.BLACK);
                                        titleTextView.setTextSize(12);
                                        titleTextView.setTypeface(null, Typeface.BOLD);

                                        // 루트 레이아웃에 첫 번째 TextView 추가
                                        rootLayout2.addView(titleTextView);
                                        // 액티비티의 루트 레이아웃 설정
                                    }
                                    takeBadNutrientScrollView.addView(rootLayout2);

                                    //PROGRESS RECYCLERVIEW
                                    AnalyzeResultFragmentProgressRecyclerAdapter analyzeResultFragmentProgressRecyclerAdapter = new AnalyzeResultFragmentProgressRecyclerAdapter(getContext(),analyzeResultListDTO.getNutIntakeDTOs());
                                    RecyclerView progressRecyclerView = (RecyclerView)fragmentAnalyzeReportFinalResultBinding.resultReportProgressRecyclerview;
                                    progressRecyclerView.setAdapter(analyzeResultFragmentProgressRecyclerAdapter);
                                    progressRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                                },
                                throwable -> {
                                    Log.i("받아온 값이 없어요","해당 값이 없어요");
                                }
                        );
                    }
                }
        );



        return fragmentAnalyzeReportFinalResultBinding.getRoot();
    }
    public String getCurrentDate() {
        // 현재 날짜를 가져오는 코드
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd일 기준", Locale.getDefault());
        String currentDate = sdf.format(new Date());
        return currentDate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("onDestroyView()","fragment 제거됨");
        if(selected_takePurpose!= null) {
            selected_takePurpose = null;
        }
        if(selectedProductNoSet != null) {
            selectedProductNoSet = null;
        }
    }
}

