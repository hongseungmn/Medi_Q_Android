package com.kosmo.kosmo.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Date;

public class ReviewDTO implements Serializable {
    @Expose
    @SerializedName("r_id")
    private String r_id;
    @Expose
    @SerializedName("takeTime")
    private String takeTime;
    @Expose
    @SerializedName("starScore")
    private String starScore;
    @Expose
    @SerializedName("effect")
    private String effect;
    @Expose
    @SerializedName("noEffect")
    private String noEffect;
    @Expose
    @SerializedName("content")
    private String content;
    @Expose
    @SerializedName("r_productNo")
    private int r_productNo;
    @Expose
    @SerializedName("r_regidate")
    private String r_regidate;
    @Expose
    @SerializedName("gender")
    private String gender;
    @Expose
    @SerializedName("birth")
    private String birth;

    public ReviewDTO(String r_id, String takeTime, String starScore, String effect, String noEffect, String content, int r_productNo, String r_regidate, String gender, String birth) {
        this.r_id = r_id;
        this.takeTime = takeTime;
        this.starScore = starScore;
        this.effect = effect;
        this.noEffect = noEffect;
        this.content = content;
        this.r_productNo = r_productNo;
        this.r_regidate = r_regidate;
        this.gender = gender;
        this.birth = birth;
    }

    public String getR_id() {
        return r_id;
    }

    public void setR_id(String r_id) {
        this.r_id = r_id;
    }

    public String getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(String takeTime) {
        this.takeTime = takeTime;
    }

    public String getStarScore() {
        return starScore;
    }

    public void setStarScore(String starScore) {
        this.starScore = starScore;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getNoEffect() {
        return noEffect;
    }

    public void setNoEffect(String noEffect) {
        this.noEffect = noEffect;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getR_productNo() {
        return r_productNo;
    }

    public void setR_productNo(int r_productNo) {
        this.r_productNo = r_productNo;
    }

    public String getR_regidate() {
        return r_regidate;
    }

    public void setR_regidate(String r_regidate) {
        this.r_regidate = r_regidate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}
