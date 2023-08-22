package com.kosmo.kosmo.model.api;

import com.kosmo.kosmo.model.dto.AnalyzeReportDTO;
import com.kosmo.kosmo.model.dto.UserDTO;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AnalyzeReportAPI {

    @GET("/androidSelectAnalyzeReportOne/{userId}")
    Single<AnalyzeReportDTO> getUserInfo(@Path("userId")String userId);
    @GET("/androidSelectAllAnalyzeReport/{userId}")
    Single<List<AnalyzeReportDTO>> getAnalyzeReportAll(@Path("userId")String userId);
}
