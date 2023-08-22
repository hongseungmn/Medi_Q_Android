package com.kosmo.kosmo.model.dto.response;

import com.google.gson.annotations.SerializedName;

public class GoogleResponse {
    @SerializedName("base64")
    private String base64;

    @SerializedName("titleText")
    private String titleText;

    public String getBase64() {
        return base64;
    }

    public String getTitleText() {
        return titleText;
    }
}
