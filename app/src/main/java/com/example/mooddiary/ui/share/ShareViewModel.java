package com.example.mooddiary.ui.share;


import android.widget.ArrayAdapter;

import androidx.lifecycle.ViewModel;
import com.example.mooddiary.Request;
import java.util.ArrayList;

/**
 * This is ViewModel holding data for ShareFragment
 * easier to sharing data between fragments
 */
public class ShareViewModel extends ViewModel {

    private ArrayList<String> followerList; // people follows user
    private ArrayList<String> followList; // user follows these people
    private ArrayList<Request> receivedRequestList; // new request received by user

    public ShareViewModel() {
        followList = new ArrayList<>();
        receivedRequestList = new ArrayList<>();
        followerList = new ArrayList<>();
    }

    public ArrayList<String> getFollowerList() {
        return followerList;
    }

    public ArrayList<String> getFollowList() {
        return followList;
    }

    public ArrayList<Request> getReceivedRequestList() {
        return receivedRequestList;
    }


}