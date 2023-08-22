package com.kosmo.kosmo.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AnalyzeResultListDTO implements Serializable {
    @Expose
    @SerializedName("resultScore")
    float resultScore;
    @Expose
    @SerializedName("listdto")
    List<AnalyzeResultDTO> listdto; //복용 목적별 정보
    @Expose
    @SerializedName("nutrient_list_report")
    List<String> nutrient_list_report; //복용중 비타민
    @Expose
    @SerializedName("nutrient_list_no_report")
    List<String> nutrient_list_no_report; // 아직 복용하지 않고 있는 비타민
    @Expose
    @SerializedName("ingredientCount")
    float ingredientCount;
    //List<String> nutnumlist;
    @Expose
    @SerializedName("nutIntakeDTOs")
    List<NutIntakeDTO> nutIntakeDTOs;


    public AnalyzeResultListDTO(float resultScore, List<AnalyzeResultDTO> listdto, List<String> nutrient_list_report, List<String> nutrient_list_no_report, float ingredientCount, List<NutIntakeDTO> nutIntakeDTOs) {
        this.resultScore = resultScore;
        this.listdto = listdto;
        this.nutrient_list_report = nutrient_list_report;
        this.nutrient_list_no_report = nutrient_list_no_report;
        this.ingredientCount = ingredientCount;
        this.nutIntakeDTOs = nutIntakeDTOs;
    }

    public AnalyzeResultListDTO() {

    }

    public float getResultScore() {
        return resultScore;
    }

    public void setResultScore(float resultScore) {
        this.resultScore = resultScore;
    }

    public List<AnalyzeResultDTO> getListdto() {
        return listdto;
    }

    public void setListdto(List<AnalyzeResultDTO> listdto) {
        this.listdto = listdto;
    }

    public List<String> getNutrient_list_report() {
        return nutrient_list_report;
    }

    public void setNutrient_list_report(List<String> nutrient_list_report) {
        this.nutrient_list_report = nutrient_list_report;
    }

    public List<String> getNutrient_list_no_report() {
        return nutrient_list_no_report;
    }

    public void setNutrient_list_no_report(List<String> nutrient_list_no_report) {
        this.nutrient_list_no_report = nutrient_list_no_report;
    }

    public float getIngredientCount() {
        return ingredientCount;
    }

    public void setIngredientCount(float ingredientCount) {
        this.ingredientCount = ingredientCount;
    }

    public List<NutIntakeDTO> getNutIntakeDTOs() {
        return nutIntakeDTOs;
    }

    public void setNutIntakeDTOs(List<NutIntakeDTO> nutIntakeDTOs) {
        this.nutIntakeDTOs = nutIntakeDTOs;
    }
}
