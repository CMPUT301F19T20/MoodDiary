package com.example.mooddiary.ui.home;

import androidx.lifecycle.ViewModel;
import com.example.mooddiary.MoodEvent;
import com.example.mooddiary.MoodList;

/**
 * This is a ViewModel holding data for HomeFragment
 * easier to sharing data between fragments
 */
public class HomeViewModel extends ViewModel {


    private MoodList moodListHome;

    public HomeViewModel() {
        moodListHome = new MoodList();
    }

    public MoodList getMoodList() {
        return moodListHome;
    }

}