package com.kosmo.kosmo.model.dto;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FoodListDTO implements Serializable {


    public FoodListDTO(String no, String postNo, String productName, String company, String expiration, String intake, String shape, String nutrient, String caution, String standard, String material, String imgURL, String f_view) {
        this.no = no;
        this.postNo = postNo;
        this.productName = productName;
        this.company = company;
        this.expiration = expiration;
        this.intake = intake;
        this.shape = shape;
        this.nutrient = nutrient;
        this.caution = caution;
        this.standard = standard;
        this.material = material;
        this.imgURL = imgURL;
        this.f_view = f_view;
    }

    @Expose
    @SerializedName("no")
    private String no;

    @Expose
    @SerializedName("postNo")
    private String postNo;

    @Expose
    @SerializedName("productName")
    private String productName;

    @Expose
    @SerializedName("company")
    private String company;

    @Expose
    @SerializedName("expiration")
    private String expiration;

    @Expose
    @SerializedName("intake")
    private String intake;

    @Expose
    @SerializedName("shape")
    private String shape;

    @Expose
    @SerializedName("nutrient")
    private String nutrient;

    @Expose
    @SerializedName("caution")
    private String caution;

    @Expose
    @SerializedName("standard")
    private String standard;

    @Expose
    @SerializedName("material")
    private String material;

    @Expose
    @SerializedName("imgURL")
    private String imgURL;

    @Expose
    @SerializedName("f_view")
    private String f_view;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPostNo() {
        return postNo;
    }

    public void setPostNo(String postNo) {
        this.postNo = postNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getIntake() {
        return intake;
    }

    public void setIntake(String intake) {
        this.intake = intake;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getNutrient() {
        return nutrient;
    }

    public void setNutrient(String nutrient) {
        this.nutrient = nutrient;
    }

    public String getCaution() {
        return caution;
    }

    public void setCaution(String caution) {
        this.caution = caution;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getF_view() {
        return f_view;
    }

    public void setF_view(String f_view) {
        this.f_view = f_view;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
