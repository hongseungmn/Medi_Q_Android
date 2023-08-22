package com.kosmo.kosmo.model.api;

import com.kosmo.kosmo.model.dto.AnalyzeResultDTO;
import com.kosmo.kosmo.model.dto.AnalyzeResultListDTO;
import com.kosmo.kosmo.model.dto.FoodListDTO;
import com.kosmo.kosmo.model.dto.NutIntakeDTO;
import com.kosmo.kosmo.model.dto.ReviewDTO;
import com.kosmo.kosmo.model.dto.TotalReviewDTO;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FoodListAPI {
    @GET("/androidSelectAll.do")
    Single<List<FoodListDTO>> getFoodList();
    @GET("/androidSelectByNutrient.do/{nutrient}")
    Single<List<FoodListDTO>> getFoodListByNutrient(@Path("nutrient") String nutrient);
    @GET("/androidSelectByPurpose.do/{purpose}")
    Single<List<FoodListDTO>> getFoodListByPurpose(@Path("purpose") String purpose);

    @GET("/androidSelectFood.do/{no}")
    Single<FoodListDTO> getFood(@Path("no") String no);

    @GET("/androidSelectFoodByKeyWord.do/{keyword}")
    Single<List<FoodListDTO>> getFoodListBySearchKeyWord(@Path("keyword") String keyword);


    @FormUrlEncoded
    @POST("/AndroidGetAnalyzeResultReport.do")
    Single<AnalyzeResultListDTO> getAnalyzeResultReport(@Field("takePurpose") String takePurpose, @Field("takeFood")String takeFood);

    @GET("/androidGetTotalReview/{foodNo}")
    Single<TotalReviewDTO> getTotalReview(@Path("foodNo") String foodNo);

    @GET("/androidGetReviewList")
    Single<List<ReviewDTO>> getReviewList(@Query("no") String no, @Query("current") String current);

    @GET("/androidSelectFoodByName.do/{foodName}")
    Single<FoodListDTO> getFoodByName(@Path("foodName")String foodName);
}
