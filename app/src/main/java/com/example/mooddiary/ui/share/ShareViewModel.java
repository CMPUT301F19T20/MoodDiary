package com.example.mooddiary.ui.share;


import android.widget.ArrayAdapter;

import androidx.lifecycle.ViewModel;
import com.example.mooddiary.Request;
import java.util.ArrayList;

/**
 * This is ViewModel holding data for ShareFragment
 */
public class ShareViewModel extends ViewModel {

    private ArrayList<String> followerList; // people follows user
    private ArrayList<String> followList; // user follows these people
    private ArrayList<String> requestList; // new request received by user

    public ShareViewModel() {
        followList = new ArrayList<>();
        requestList = new ArrayList<>();
        followerList = new ArrayList<>();
        initRequestList();
        initFollowerList();
        initFollowList();
    }

    public ArrayList<String> getFollowerList() {
        return followerList;
    }

    public ArrayList<String> getFollowList() {
        return followList;
    }

    public ArrayList<String> getRequestList() {
        return requestList;
    }


    private void initFollowList() {
        followList.add("xinman");
        followList.add("chenge");
        followList.add("shanchu");
        followList.add("xiaoyun");
    }

    private void initFollowerList() {
        followerList.add("chrids");
        followerList.add("mary");
        followerList.add("changxin");
        followerList.add("tala");
        followerList.add("jack");
    }

    private void initRequestList() {
        requestList.add("chenge");
        requestList.add("yanquan");
        requestList.add("shanchu");

    }
}