package com.example.mooddiary.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.mooddiary.MoodList;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<MoodList> moodListMutable;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is moodlist fragment");

    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<MoodList> getMoodList() {return moodListMutable;}


}