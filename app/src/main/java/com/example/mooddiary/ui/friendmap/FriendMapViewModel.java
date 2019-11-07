package com.example.mooddiary.ui.friendmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * This is ViewModel holding data for FriendMapFragment
 */
public class FriendMapViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FriendMapViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is friend map fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}