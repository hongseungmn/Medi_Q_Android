package com.kosmo.kosmo.model.api;

import com.kosmo.kosmo.model.dto.request.PredictCardiovascularRequest;
import com.kosmo.kosmo.model.dto.request.PredictDiabetesRequest;
import com.kosmo.kosmo.model.dto.response.GoogleResponse;
import com.kosmo.kosmo.model.dto.response.PredictParkinsonResponse;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PredictModelAPI {
    @POST("diabetes")
    Call<List> predictDiabetes(@Body PredictDiabetesRequest data);

    @POST("cardiovascular")
    Call<List> predictCardiovascular(@Body PredictCardiovascularRequest data);

    @FormUrlEncoded
    @POST("parkinson")
    Call<PredictParkinsonResponse> predictParkinson(@Field("base64String") String data);

    @FormUrlEncoded
    @POST("ocr")
    Call<GoogleResponse> googleOCR(@Field("base64") String base64);
}
