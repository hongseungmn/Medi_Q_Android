package com.kosmo.kosmo.model.repository;

import com.kosmo.kosmo.model.api.FoodListAPI;
import com.kosmo.kosmo.model.api.ReviewAPI;
import com.kosmo.kosmo.model.dto.FoodListDTO;
import com.kosmo.kosmo.model.dto.ReviewDTO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewRepository {
    private ReviewAPI reviewAPI;

    public ReviewRepository() {
        Retrofit retrofit = createRetrofit("http://192.168.0.16:9090");
        reviewAPI = retrofit.create(ReviewAPI.class);
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

    public Single<List<Map>> getReviewListById(String userId) {
        return reviewAPI.getReviewListById(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Call<Integer> deleteReview(String userId, String productNo) {
        return reviewAPI.deleteReview(userId,productNo);
    }
}
