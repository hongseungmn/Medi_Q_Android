package com.kosmo.kosmo.model.dto.request;

public class PredictCardiovascularRequest {
    String gender;
    String height;
    String weight;
    String bloodpress_high;
    String bloodpress_low;
    String total_cholesterol;
    String glucose;
    String smoke;
    String alco;
    String age;

    public PredictCardiovascularRequest(String gender, String height, String weight, String bloodpress_high, String bloodpress_low, String total_cholesterol, String glucose, String smoke, String alco, String age) {
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.bloodpress_high = bloodpress_high;
        this.bloodpress_low = bloodpress_low;
        this.total_cholesterol = total_cholesterol;
        this.glucose = glucose;
        this.smoke = smoke;
        this.alco = alco;
        this.age = age;
    }
}
