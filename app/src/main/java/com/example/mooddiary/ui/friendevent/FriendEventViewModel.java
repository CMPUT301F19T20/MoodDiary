package com.example.mooddiary.ui.friendevent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FriendEventViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FriendEventViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is friend event fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}