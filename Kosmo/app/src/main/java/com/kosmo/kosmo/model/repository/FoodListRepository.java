package com.kosmo.kosmo.model.repository;

import android.text.Editable;

import com.kosmo.kosmo.model.dto.AnalyzeResultDTO;
import com.kosmo.kosmo.model.dto.AnalyzeResultListDTO;
import com.kosmo.kosmo.model.dto.FoodListDTO;
import com.kosmo.kosmo.model.api.FoodListAPI;
import com.kosmo.kosmo.model.dto.NutIntakeDTO;
import com.kosmo.kosmo.model.dto.ReviewDTO;
import com.kosmo.kosmo.model.dto.TotalReviewDTO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodListRepository {
    private FoodListAPI foodFoodListAPI;

    public FoodListRepository() {
        Retrofit retrofit = createRetrofit("http://192.168.0.16:9090");
        foodFoodListAPI = retrofit.create(FoodListAPI.class);
    }

    private Retrofit createRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .client(new OkHttpClient.Builder()
                        .connectionPool(new ConnectionPool(5, 20, TimeUnit.SECONDS))
                        .build())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    public Single<List<FoodListDTO>> getFoodList() {
        return foodFoodListAPI.getFoodList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Single<List<FoodListDTO>> getFoodListByNutrient(String nutrient) {
        return foodFoodListAPI.getFoodListByNutrient(nutrient)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<FoodListDTO>> getFoodListByPurpose(String purpose) {
        return foodFoodListAPI.getFoodListByPurpose(purpose)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<FoodListDTO> getFood(String no) {
        return foodFoodListAPI.getFood(no)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<FoodListDTO>> getFoodListBySearchKeyWord(String keyword) {
        return foodFoodListAPI.getFoodListBySearchKeyWord(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Single<AnalyzeResultListDTO> getAnalyzeResultReport(String takePurpose, String takeFood) {
        return foodFoodListAPI.getAnalyzeResultReport(takePurpose,takeFood)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<TotalReviewDTO> getTotalReview(String foodNo) {
        return foodFoodListAPI.getTotalReview(foodNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<ReviewDTO>> getReviewList(String no, String current) {
        return foodFoodListAPI.getReviewList(no,current)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<FoodListDTO> getFoodByName(String foodName) {
        return foodFoodListAPI.getFoodByName(foodName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
