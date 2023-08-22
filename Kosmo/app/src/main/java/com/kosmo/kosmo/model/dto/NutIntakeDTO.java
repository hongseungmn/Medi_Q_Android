package com.kosmo.kosmo.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NutIntakeDTO implements Serializable {
    @Expose
    @SerializedName("nutNumber")
    private float nutNumber;
    @Expose
    @SerializedName("dri")
    private float DRI;
    @Expose
    @SerializedName("ul")
    private float UL;
    @Expose
    @SerializedName("nut")
    private String nut;
    @Expose
    @SerializedName("driunit")
    private String DRIUnit;
    @Expose
    @SerializedName("ulunit")
    private String ULUnit;


    public NutIntakeDTO(float nutNumber, float DRI, float UL, String nut, String DRIUnit, String ULUnit) {
        this.nutNumber = nutNumber;
        this.DRI = DRI;
        this.UL = UL;
        this.nut = nut;
        this.DRIUnit = DRIUnit;
        this.ULUnit = ULUnit;
    }

    public float getNutNumber() {
        return nutNumber;
    }

    public void setNutNumber(float nutNumber) {
        this.nutNumber = nutNumber;
    }

    public float getDRI() {
        return DRI;
    }

    public void setDRI(float DRI) {
        this.DRI = DRI;
    }

    public float getUL() {
        return UL;
    }

    public void setUL(float UL) {
        this.UL = UL;
    }

    public String getNut() {
        return nut;
    }

    public void setNut(String nut) {
        this.nut = nut;
    }

    public String getDRIUnit() {
        return DRIUnit;
    }

    public void setDRIUnit(String DRIUnit) {
        this.DRIUnit = DRIUnit;
    }

    public String getULUnit() {
        return ULUnit;
    }

    public void setULUnit(String ULUnit) {
        this.ULUnit = ULUnit;
    }
}
