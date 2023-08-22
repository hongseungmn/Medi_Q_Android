package com.kosmo.kosmo.model.dto.request;

public class PredictDiabetesRequest {
    String age;
    String bmi;
    String glucose;
    String bloodpress;

    public PredictDiabetesRequest(String age, String bmi, String glucose, String bloodpress) {
        this.age = age;
        this.bmi = bmi;
        this.glucose = glucose;
        this.bloodpress = bloodpress;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getGlucose() {
        return glucose;
    }

    public void setGlucose(String glucose) {
        this.glucose = glucose;
    }

    public String bloodpress() {
        return bloodpress;
    }

    public void setBloodPress(String bloodpress) {
        this.bloodpress = bloodpress;
    }
}
