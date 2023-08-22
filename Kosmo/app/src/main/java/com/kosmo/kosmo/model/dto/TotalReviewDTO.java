package com.kosmo.kosmo.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TotalReviewDTO implements Serializable {
    @Expose
    @SerializedName("starScoreTotal")
    float starScoreTotal;
    @Expose
    @SerializedName("starScore")
    Map starScore;
    @Expose
    @SerializedName("effectList")
    List<Map<String,Object>> effectList;
    @Expose
    @SerializedName("noEffectList")
    List<Map<String,Object>> noEffectList;

    public TotalReviewDTO() {
    }

    public TotalReviewDTO(float starScoreTotal, Map starScore, List<Map<String, Object>> effectList, List<Map<String, Object>> noEffectList) {
        this.starScoreTotal = starScoreTotal;
        this.starScore = starScore;
        this.effectList = effectList;
        this.noEffectList = noEffectList;
    }

    public float getStarScoreTotal() {
        return starScoreTotal;
    }

    public void setStarScoreTotal(float starScoreTotal) {
        this.starScoreTotal = starScoreTotal;
    }

    public Map getStarScore() {
        return starScore;
    }

    public void setStarScore(Map starScore) {
        this.starScore = starScore;
    }

    public List<Map<String, Object>> getEffectList() {
        return effectList;
    }

    public void setEffectList(List<Map<String, Object>> effectList) {
        this.effectList = effectList;
    }

    public List<Map<String, Object>> getNoEffectList() {
        return noEffectList;
    }

    public void setNoEffectList(List<Map<String, Object>> noEffectList) {
        this.noEffectList = noEffectList;
    }
}
