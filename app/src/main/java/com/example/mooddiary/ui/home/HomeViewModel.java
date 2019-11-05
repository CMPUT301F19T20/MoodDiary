package com.example.mooddiary.ui.home;

import androidx.lifecycle.ViewModel;
import com.example.mooddiary.MoodEvent;
import com.example.mooddiary.MoodList;

/**
 * This is a ViewModel holding data for HomeFragment
 */
public class HomeViewModel extends ViewModel {

    private MoodList moodListHome;

    public MoodList getMoodList() {
        if (moodListHome == null) {
            moodListHome = new MoodList();
            initMoodList();
        }
        return moodListHome;
    }

    /**
     * This creates a list of mood events for testing.
     * Not required for the project.
     * This may be deleted later.
     */
    private void initMoodList() {
        MoodEvent moodEvent1 =
                new MoodEvent("happy", "2019/10/27", "10:40", "with a crowd", "", "l", "");

        MoodEvent moodEvent2 =
                new MoodEvent("sad", "2019/10/23", "11:40", "alone", "", "love", "");

        MoodEvent moodEvent3 =
                new MoodEvent("meh", "2019/10/25", "12:40", "alone", "", "", "");

        MoodEvent moodEvent4 =
                new MoodEvent("stressed", "2019/10/22", "10:40", "alone", "", "", "");

        MoodEvent moodEvent5 =
                new MoodEvent("angry", "2019/10/21", "10:40", "alone", "", "", "");

        MoodEvent moodEvent6 =
                new MoodEvent("content", "2019/10/19", "10:40", "alone", "", "", "");

        moodListHome.add(moodEvent2);
        moodListHome.add(moodEvent1);
        moodListHome.add(moodEvent3);
//        moodListHome.add(moodEvent4);
//        moodListHome.add(moodEvent5);
//        moodListHome.add(moodEvent6);
    }



}