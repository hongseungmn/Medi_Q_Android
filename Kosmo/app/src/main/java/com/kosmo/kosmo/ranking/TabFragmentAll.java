package com.kosmo.kosmo.ranking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.kosmo.kosmo.FoodTableActivity;
import com.kosmo.kosmo.R;
import com.kosmo.kosmo.databinding.FragmentRankingTabAllBinding;
import com.kosmo.kosmo.model.dto.FoodListDTO;
import com.kosmo.kosmo.model.repository.FoodListRepository;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TabFragmentAll extends Fragment implements View.OnClickListener {
    private FoodListRepository repository;
    private FragmentRankingTabAllBinding fragmentRankingTabAllBinding;
    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        repository = new FoodListRepository();
        // 기존의 레이아웃을 가져옵니다.
        View rootView = inflater.inflate(R.layout.fragment_ranking_tab_all, container, false);
        fragmentRankingTabAllBinding = FragmentRankingTabAllBinding.inflate(getLayoutInflater());
        LinearLayout  originalLayout = fragmentRankingTabAllBinding.rankingAllList;
        Single<List<FoodListDTO>> foodListSingle = repository.getFoodList();
        foodListSingle.subscribe(
                foodList -> {
                    // 성공적으로 음식 리스트를 가져왔을 때의 처리
                    for (FoodListDTO food : foodList) {
                        // 음식 데이터를 이용한 처리
                        String foodName = food.getProductName();
                        Log.i("FoodListRepository","FoodName : "+foodName);
                        LinearLayout clonedLayout = new LinearLayout(fragmentRankingTabAllBinding.getRoot().getContext());
                        clonedLayout.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        ));
                        clonedLayout.setOrientation(LinearLayout.HORIZONTAL);
                        clonedLayout.setPadding(10, 10, 10, 10);

                        // 복제된 LinearLayout에 ImageView와 TextView를 추가합니다.
                        ImageView imageView = new ImageView(getContext());
                        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                                250, 250
                        ));
                        Glide.with(clonedLayout).load(food.getImgURL()).placeholder(R.drawable.no_img).into(imageView);
                        imageView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.food_img_background));
                        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        imageView.setPadding(20,20,20,20);
                        TextView textView = new TextView(getContext());
                        textView.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT
                        ));
                        textView.setGravity(Gravity.CENTER_VERTICAL);
                        String htmlText = "<font color='#5a5a5a'>"+food.getCompany() +"</font><h6>"+ food.getProductName()+"</h6>";
                        textView.setText(Html.fromHtml(htmlText,Html.FROM_HTML_MODE_COMPACT));
                        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                        textView.setPadding(20,0,0,0);

                        // 복제된 LinearLayout에 ImageView와 TextView를 추가합니다.
                        clonedLayout.addView(imageView);
                        clonedLayout.addView(textView);
                        clonedLayout.setOnClickListener(this);
                        clonedLayout.setTag(food.getNo());
                        originalLayout.addView(clonedLayout);

                    }
                },
                throwable -> {
                    // 오류 발생 시의 처리
                    Log.e("FoodListRepository", "Error fetching food list: " + throwable.getMessage());
                }
        );
        return originalLayout.getRootView();
    }

    @Override
    public void onClick(View view) {
        Log.i("kosmo.kosmo.ranking","선택된 값의 Tag"+view.getTag());
        Intent intent = new Intent(view.getContext(), FoodTableActivity.class);
        intent.putExtra("foodNo",view.getTag().toString());
        startActivity(intent);
    }

}
