package com.example.mooddiary.ui.mymap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyMapViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyMapViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is my map fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}