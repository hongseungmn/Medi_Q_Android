package com.kosmo.kosmo.main;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationBarView;
import com.kosmo.kosmo.MainActivity;
import com.kosmo.kosmo.R;
import com.kosmo.kosmo.SearchCameraActivity;
import com.kosmo.kosmo.analyze.AnalyzeFragment;
import com.kosmo.kosmo.databinding.FragmentMainBinding;
import com.kosmo.kosmo.model.dto.AnalyzeReportDTO;
import com.kosmo.kosmo.model.dto.UserDTO;
import com.kosmo.kosmo.model.repository.AnalyzeReportRepository;
import com.kosmo.kosmo.model.repository.UserInfoRepository;
import com.kosmo.kosmo.predict.PredictDiabetesActivity;
import com.kosmo.kosmo.predict.PredictFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class MainFragment extends Fragment {

    AnalyzeFragment analyzeFragment;
    PredictFragment predictFragment;
    private View rootView;
    private UserInfoRepository userInfoRepository;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        // Setting_layout.xml의 레이아웃을 인플레이션하여 rootView에 저장
        rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // main_ranking.xml에 포함된 뷰들을 찾아서 처리하는 작업
        View includedView = rootView.findViewById(R.id.include_main_ranking);
        // rootView 내부에 있는 모든 ImageView 찾기
        List<ImageView> imageViews = new ArrayList<>();
        findAllImageViews(includedView, imageViews);
        // 모든 ImageView에 원하는 작업을 수행
        for (ImageView imageView : imageViews) {
            // 여기에 원하는 작업을 수행 (예: 이미지 설정)
            imageView.setOnClickListener(view -> Log.i("com.kosmo.kosmo","이미지 뷰가 선택됨"));
            imageView.setClipToOutline(true);
        }


        analyzeFragment = new AnalyzeFragment();
        predictFragment = new PredictFragment();
        rootView.findViewById(R.id.main_to_analyze_btn).setOnClickListener(view -> {

            ((MainActivity)container.getContext()).replaceFragment(analyzeFragment,null);
            ((NavigationBarView)((MainActivity)getContext()).findViewById(R.id.bottom_navigationview)).setSelectedItemId(R.id.analyze);
        });
        rootView.findViewById(R.id.main_to_predict_btn).setOnClickListener(view -> {
            ((MainActivity)getContext()).replaceFragment(predictFragment,null);
            ((NavigationBarView)((MainActivity)getContext()).findViewById(R.id.bottom_navigationview)).setSelectedItemId(R.id.predict);
        });
        rootView.findViewById(R.id.main_recent_analyze_report).setOnClickListener(view -> {
            Log.i("com.kosmo.kosmo","로그인 후 추후 작업 필요");
        });
        rootView.findViewById(R.id.search_food_by_camera).setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), SearchCameraActivity.class);
            startActivity(intent);
        });
        return rootView;
    }
    // ImageView를 재귀적으로 찾는 메서드
    private void findAllImageViews(View view, List<ImageView> imageViews) {
        if (view instanceof ImageView) {
            imageViews.add((ImageView) view);
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childView = viewGroup.getChildAt(i);
                findAllImageViews(childView, imageViews);
            }
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences pref = getActivity().getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        if((pref!=null) && (pref.contains("userId"))){
            String text = pref.getString("userId", "");
            Log.i("kosmo","저장된 사용자의 아이디"+text);
            userInfoRepository = new UserInfoRepository();
            Single<UserDTO> userDto = userInfoRepository.getUserInfo("sem50000");
            userDto.subscribe(
                    userDTO -> {
                        Log.i("com.kosmo.kosmo","사용자 이름 : "+userDTO.getName());
                        TextView analyzeTitleView = (TextView) rootView.findViewById(R.id.main_fragment_analyze_report_title);
                        analyzeTitleView.setText(userDTO.getName()+"님의 최근 분석 리포트");
                        AnalyzeReportRepository analyzeReportRepository = new AnalyzeReportRepository();
                        Single<AnalyzeReportDTO> reportDTOSingle =  analyzeReportRepository.getAnalyzeReport("sem50000");
                        reportDTOSingle.subscribe(
                                report->{
                                    Log.i("com.kosmo.kosmo","분석 리포트 결과 성공 :"+report.getTakeFoods());
                                    TextView analyzeDate = (TextView) rootView.findViewById(R.id.main_fragment_analyze_report_date);
                                    analyzeDate.setText(report.getAnalyzeDate()+" 기준");
                                    TextView analyzeScore = (TextView) rootView.findViewById(R.id.main_fragment_analyze_report_score);
                                    analyzeScore.setText(report.getScore()+" 점");
                                    TextView analyzePurpose = (TextView) rootView.findViewById(R.id.main_fragment_analyze_report_purpose);
                                    analyzePurpose.setText(report.getTakePurposes());
                                    TextView analyzeFood = (TextView) rootView.findViewById(R.id.main_fragment_analyze_report_food);
                                    analyzeFood.setText(report.getTakeFoods());
                                },
                                throwable -> {
                                    Log.i("com.kosmo.kosmo","분석 리포트 가져오기 실패 :"+throwable.getMessage());
                                }
                        );

                    },
                    throwable -> {
                        Log.i("com.kosmo.kosmo","사용자 정보 가져오기 실패"+throwable.getMessage());
                    }
            );
        }
    }
}