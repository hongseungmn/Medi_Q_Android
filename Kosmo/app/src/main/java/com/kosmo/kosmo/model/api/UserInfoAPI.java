package com.kosmo.kosmo.model.api;

import com.kosmo.kosmo.model.dto.AnalyzeResultListDTO;
import com.kosmo.kosmo.model.dto.FoodListDTO;
import com.kosmo.kosmo.model.dto.ReviewDTO;
import com.kosmo.kosmo.model.dto.TotalReviewDTO;
import com.kosmo.kosmo.model.dto.UserDTO;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserInfoAPI {

    @GET("/androidGetUserInfoByUserId/{userId}")
    Single<UserDTO> getUserInfo(@Path("userId")String userId);

}
