package com.example.mooddiary.ui.friendmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mooddiary.MoodEvent;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is ViewModel holding data for FriendMapFragment
 * easier to sharing data between fragments
 */
public class FriendMapViewModel extends ViewModel {

    private ArrayList<String> friendsName;
    private ArrayList<MoodEvent> friendsRecentEvent;

    public FriendMapViewModel(){
        friendsName = new ArrayList<>();
        friendsRecentEvent = new ArrayList<>();
    }

    public ArrayList<String> getFriendsName() {
        return friendsName;
    }

    public ArrayList<MoodEvent> getFriendsRecentEvent() {
        return friendsRecentEvent;
    }
}