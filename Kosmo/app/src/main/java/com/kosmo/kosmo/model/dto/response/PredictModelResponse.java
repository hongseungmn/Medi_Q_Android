package com.kosmo.kosmo.model.dto.response;

import java.util.List;

public class PredictModelResponse {
    private List<List<Double>> prediction;

    public List<List<Double>> getPrediction() {
        return prediction;
    }
}
