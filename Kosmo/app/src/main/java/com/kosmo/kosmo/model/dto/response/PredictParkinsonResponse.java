package com.kosmo.kosmo.model.dto.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PredictParkinsonResponse {
    @SerializedName("image_base64")
    private String imageBase64;

//    @SerializedName("score")
//    private List score;

    public String getImageBase64() {
        return imageBase64;
    }

//    public List getScore() {
//        return score;
//    }
}
