package com.kosmo.kosmo.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class AnalyzeResultDTO implements Serializable {
    @Expose
    @SerializedName("takePurpose")
    String  takePurpose;// 복용 목적
    @Expose
    @SerializedName("foodList")
    List<String> foodList;// 사용자가 복용 목적을 위해 필요한 영양소들
    @Expose
    @SerializedName("foodForHelpPurpose")
    Map<String,List> foodForHelpPurpose;//(있을경우)건기식 이름,건기식에서 사용자의 복용 목적에 도움되는 기능성 영양소List
    @Expose
    @SerializedName("ingredient_list_no_report")
    List<String> ingredient_list_no_report;//사용자가 먹는 건기식들에서 나오지 않은 복용목적에 필요한 기능성 영양소 - 섭취가 필요한

    public AnalyzeResultDTO() {
    }

    public AnalyzeResultDTO(String takePurpose, List<String> foodList, Map<String, List> foodForHelpPurpose, List<String> ingredient_list_no_report) {
        this.takePurpose = takePurpose;
        this.foodList = foodList;
        this.foodForHelpPurpose = foodForHelpPurpose;
        this.ingredient_list_no_report = ingredient_list_no_report;
    }

    public String getTakePurpose() {
        return takePurpose;
    }

    public void setTakePurpose(String takePurpose) {
        this.takePurpose = takePurpose;
    }

    public List<String> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<String> foodList) {
        this.foodList = foodList;
    }

    public Map<String, List> getFoodForHelpPurpose() {
        return foodForHelpPurpose;
    }

    public void setFoodForHelpPurpose(Map<String, List> foodForHelpPurpose) {
        this.foodForHelpPurpose = foodForHelpPurpose;
    }

    public List<String> getIngredient_list_no_report() {
        return ingredient_list_no_report;
    }

    public void setIngredient_list_no_report(List<String> ingredient_list_no_report) {
        this.ingredient_list_no_report = ingredient_list_no_report;
    }
}
