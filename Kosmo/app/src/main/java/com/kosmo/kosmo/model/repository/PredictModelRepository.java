package com.kosmo.kosmo.model.repository;

import com.kosmo.kosmo.model.api.PredictModelAPI;
import com.kosmo.kosmo.model.dto.request.PredictCardiovascularRequest;
import com.kosmo.kosmo.model.dto.request.PredictDiabetesRequest;
import com.kosmo.kosmo.model.dto.response.GoogleResponse;
import com.kosmo.kosmo.model.dto.response.PredictParkinsonResponse;

import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PredictModelRepository {
    private PredictModelAPI predictModelAPI;

    public PredictModelRepository() {
        Retrofit retrofit = createRetrofit("http://192.168.0.16/");
        predictModelAPI = retrofit.create(PredictModelAPI.class);
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

    public Call<List> predictDiabetes(PredictDiabetesRequest data) {
        return predictModelAPI.predictDiabetes(data);
    }

    public Call<List> predictCardiovascular(PredictCardiovascularRequest data) {
        return predictModelAPI.predictCardiovascular(data);
    }


    public Call<PredictParkinsonResponse> predictParkinson(String data) {
        return predictModelAPI.predictParkinson(data);
    }
    public Call<GoogleResponse> googleOCR(String data) {
        return predictModelAPI.googleOCR(data);
    }
}
