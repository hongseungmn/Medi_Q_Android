package com.kosmo.kosmo.model.api;

import com.kosmo.kosmo.model.dto.ReviewDTO;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReviewAPI {


    @GET("/androidGetReviewListById/{userId}")
    Single<List<Map>> getReviewListById(@Path("userId") String userId);
    @FormUrlEncoded
    @POST("/androidDeleteReview")
    Call<Integer> deleteReview(@Field(value = "id") String id, @Field(value = "no") String no);
}
