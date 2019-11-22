package com.example.mooddiary.ui.friendevent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mooddiary.MoodEvent;
import com.example.mooddiary.MoodList;

import java.util.ArrayList;
import java.util.List;

/**
 * This is ViewModel holding data for FriendEventFragment
 * easier to sharing data between fragments
 */
public class FriendEventViewModel extends ViewModel {

    private List<MoodEvent> moodListFriend;

    public FriendEventViewModel() {
        moodListFriend = new ArrayList<>();
    }

    public List<MoodEvent> getMoodList() {
        return moodListFriend;
    }
}