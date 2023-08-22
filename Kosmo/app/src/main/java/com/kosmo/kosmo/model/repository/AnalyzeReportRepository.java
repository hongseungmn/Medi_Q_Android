package com.kosmo.kosmo.model.repository;

import com.kosmo.kosmo.model.api.AnalyzeReportAPI;
import com.kosmo.kosmo.model.api.UserInfoAPI;
import com.kosmo.kosmo.model.dto.AnalyzeReportDTO;
import com.kosmo.kosmo.model.dto.UserDTO;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnalyzeReportRepository {
    private AnalyzeReportAPI analyzeReportAPI;
    public AnalyzeReportRepository() {
        Retrofit retrofit = createRetrofit("http://192.168.0.16:9090");
        analyzeReportAPI = retrofit.create(AnalyzeReportAPI.class);
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

    public Single<AnalyzeReportDTO> getAnalyzeReport(String userId) {
        return analyzeReportAPI.getUserInfo(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Single<List<AnalyzeReportDTO>> getAnalyzeReportAll(String userId) {
        return analyzeReportAPI.getAnalyzeReportAll(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
