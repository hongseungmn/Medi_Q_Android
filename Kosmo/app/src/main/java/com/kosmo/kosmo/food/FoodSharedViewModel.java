package com.kosmo.kosmo.food;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FoodSharedViewModel extends ViewModel {
    private MutableLiveData<String> sharedData = new MutableLiveData<>();

    public void setSharedData(String data) {
        sharedData.setValue(data);
    }

    public LiveData<String> getSharedData() {
        return sharedData;
    }
}
