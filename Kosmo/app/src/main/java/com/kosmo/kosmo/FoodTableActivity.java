package com.kosmo.kosmo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.kosmo.kosmo.databinding.ActivityFoodTableBinding;
import com.kosmo.kosmo.food.FoodTableAdaptor;
import com.kosmo.kosmo.food.TabFoodDetailFragment;
import com.kosmo.kosmo.food.TabFoodReviewFragment;
import com.kosmo.kosmo.model.dto.FoodListDTO;
import com.kosmo.kosmo.model.repository.FoodListRepository;
import com.kosmo.kosmo.myDiary.MyDiaryCustomDialog;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;

import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class FoodTableActivity extends AppCompatActivity {
    private ActivityFoodTableBinding binding;
    private FoodListRepository repository;
    private FoodListDTO food;
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoodTableBinding.inflate(getLayoutInflater());
        Intent intent = getIntent();
        String foodNo = intent.getStringExtra("foodNo");
        String foodName = (String) intent.getStringExtra("foodName");
        Log.i("foodTableActivity","foodName :"+foodName);
        Log.i("foodTableActivity","foodNo :"+foodNo);
        if (foodNo != null) {
            repository = new FoodListRepository();
            Single<FoodListDTO> foodListDto = repository.getFood(foodNo);
            foodListDto.subscribe(
                    foodList-> {
                        food = foodList;
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("food",food);
                        fragmentManager.setFragmentResult("sendFoodData",bundle);
                        Log.i("com.kosmo.kosmo","가져온 데이터값 : "+foodList.getProductName());
                        Glide.with(this).load(foodList.getImgURL()).placeholder(R.drawable.no_img).into(binding.foodImg);
                        binding.foodName.setText(foodList.getProductName());
                        binding.foodCompany.setText(foodList.getCompany());
                    },
                    throwable -> {
                        // 오류 발생 시의 처리
                        Log.e("FoodListRepository", "Error fetching food list: " + throwable.getMessage());
                    }
            );
        }
        else if(foodName != null) {
            repository = new FoodListRepository();
            Single<FoodListDTO> foodListDto = repository.getFoodByName(foodName);
            Log.i("foodTableActivity","getFoodByName :"+foodName);
            foodListDto.subscribe(
                    foodList-> {
                        food = foodList;
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("food",food);
                        fragmentManager.setFragmentResult("sendFoodData",bundle);
                        Log.i("com.kosmo.kosmo","가져온 데이터값 : "+foodList.getProductName());
                        Glide.with(this).load(foodList.getImgURL()).placeholder(R.drawable.no_img).into(binding.foodImg);
                        binding.foodName.setText(foodList.getProductName());
                        binding.foodCompany.setText(foodList.getCompany());
                    },
                    throwable -> {
                        // 오류 발생 시의 처리
                        Log.e("FoodListRepository", "Error fetching food list: " + throwable.getMessage());
                    }
            );
        }

        setContentView(binding.getRoot());
        binding.foodTableBackButton.setOnClickListener(view -> {
            onBackPressed();
            finish();
        });
        binding.shareImage.setOnClickListener(view -> {
            Intent intentShare = new Intent(Intent.ACTION_SEND);
            intentShare.setType("text/plain");

            // String으로 받아서 넣기
            String sendMessage = "http://192.168.0.16:9090/detail.do?no="+food.getNo();
            intentShare.putExtra(Intent.EXTRA_TEXT, sendMessage);

            Intent shareIntent = Intent.createChooser(intentShare, "share");
            startActivity(shareIntent);
        });
        TabLayout tabLayout = binding.foodTabLayout;
        tabLayout.addTab(tabLayout.newTab().setText("상세정보"));
        tabLayout.addTab(tabLayout.newTab().setText("리뷰"));
        TabFoodDetailFragment tabFoodDetailFragment = new TabFoodDetailFragment();
        TabFoodReviewFragment tabFoodReviewFragment = new TabFoodReviewFragment();
        List<Fragment> fragments = Arrays.asList(tabFoodDetailFragment, tabFoodReviewFragment);
        FoodTableAdaptor adaptor = new FoodTableAdaptor(this,fragments);
        binding.foodViewPager.setAdapter(adaptor);
        binding.foodViewPager.setSaveEnabled(false);
        binding.foodTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.foodViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.toBuyButton.setOnClickListener(view -> {
            String url = "https://search.shopping.naver.com/search/all?query="+food.getProductName(); // 웹 페이지 URL
            Intent internetIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(internetIntent);
        });
        binding.toWriteReviewButton.setOnClickListener(view->{
            Bundle bundle = new Bundle();
            bundle.putSerializable("foodDto",food);
            Intent internetIntent = new Intent(view.getContext(),ReviewActivity.class);
            internetIntent.putExtras(bundle);
            startActivity(internetIntent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }


}