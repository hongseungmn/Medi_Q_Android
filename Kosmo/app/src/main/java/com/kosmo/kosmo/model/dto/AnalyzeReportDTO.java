package com.kosmo.kosmo.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class AnalyzeReportDTO {
    @Expose
    @SerializedName("analyzeno")
    private int analyzeno;
    @Expose
    @SerializedName("userId")
    private String userId;
    @Expose
    @SerializedName("takePurposes")
    private String takePurposes;
    @Expose
    @SerializedName("takeFoods")
    private String takeFoods;
    @Expose
    @SerializedName("analyzeDate")
    private String analyzeDate;
    @Expose
    @SerializedName("score")
    private int score;

    public AnalyzeReportDTO(int analyzeno, String userId, String takePurposes, String takeFoods, String analyzeDate, int score) {
        this.analyzeno = analyzeno;
        this.userId = userId;
        this.takePurposes = takePurposes;
        this.takeFoods = takeFoods;
        this.analyzeDate = analyzeDate;
        this.score = score;
    }

    public int getAnalyzeno() {
        return analyzeno;
    }

    public void setAnalyzeno(int analyzeno) {
        this.analyzeno = analyzeno;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTakePurposes() {
        return takePurposes;
    }

    public void setTakePurposes(String takePurposes) {
        this.takePurposes = takePurposes;
    }

    public String getTakeFoods() {
        return takeFoods;
    }

    public void setTakeFoods(String takeFoods) {
        this.takeFoods = takeFoods;
    }

    public String getAnalyzeDate() {
        return analyzeDate;
    }

    public void setAnalyzeDate(String analyzeDate) {
        this.analyzeDate = analyzeDate;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
