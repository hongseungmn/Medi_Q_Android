package com.kosmo.kosmo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kosmo.kosmo.databinding.ActivityReviewBinding;
import com.kosmo.kosmo.model.dto.FoodListDTO;
import com.kosmo.kosmo.review.ReviewActivityAdapter;

public class ReviewActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityReviewBinding activityReviewBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityReviewBinding = ActivityReviewBinding.inflate(getLayoutInflater());
        setContentView(activityReviewBinding.getRoot());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            FoodListDTO foodDto = (FoodListDTO) bundle.getSerializable("foodDto");
            // foodDto를 사용하여 처리
            Log.i("kosmo.ReviewActivity","리뷰를 작성할 상품 이름 : "+foodDto.getProductName());
            ImageView foodImage = (ImageView) activityReviewBinding.reviewFoodImg;
            Glide.with(this).load(foodDto.getImgURL()).placeholder(R.drawable.no_img).into(foodImage);
            TextView foodCompany = (TextView) activityReviewBinding.reviewFoodCompany;
            foodCompany.setText(foodDto.getCompany());
            TextView foodName = (TextView) activityReviewBinding.reviewFoodProductName;
            foodName.setText(foodDto.getProductName());
        }
        SharedPreferences pref = getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        if((pref!=null) && (pref.contains("userId"))){
            String text = pref.getString("userId", "");
            Log.i("kosmo","저장된 사용자의 아이디"+text);
        }
        activityReviewBinding.toBackBtnInreview.setOnClickListener(this);
        activityReviewBinding.toCompleteBtn.setOnClickListener(this);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView reviewHowLongRecyclerView = (RecyclerView) activityReviewBinding.reviewHowLongRecyclerView;
        String[] firstStringArray = getResources().getStringArray(R.array.review_question_first);
        ReviewActivityAdapter reviewActivityAdapter1 = new ReviewActivityAdapter(firstStringArray);
        reviewHowLongRecyclerView.setAdapter(reviewActivityAdapter1);
        reviewHowLongRecyclerView.setLayoutManager(layoutManager1);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView reviewEffectRecyclerView = (RecyclerView) activityReviewBinding.reviewEffectRecyclerview;
        String[] secondStringArray = getResources().getStringArray(R.array.review_question_second);
        ReviewActivityAdapter reviewActivityAdapter2 = new ReviewActivityAdapter(secondStringArray);
        reviewEffectRecyclerView.setAdapter(reviewActivityAdapter2);
        reviewEffectRecyclerView.setLayoutManager(layoutManager2);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView reviewNoEffectRecyclerView = (RecyclerView) activityReviewBinding.reviewNoEffectRecyclerview;
        String[] thirdStringArray = getResources().getStringArray(R.array.review_question_third);
        ReviewActivityAdapter reviewActivityAdapter3 = new ReviewActivityAdapter(thirdStringArray);
        reviewNoEffectRecyclerView.setAdapter(reviewActivityAdapter3);

        reviewNoEffectRecyclerView.setLayoutManager(layoutManager3);
    }

    @Override
    public void onClick(View view) {
        if(view instanceof ImageButton){
            onBackPressed();
        }
        else {
            Log.i("com.reviewActivity","리뷰 작성 완료");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityReviewBinding = null;
    }
}