package com.example.mooddiary.ui.home;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.mooddiary.LoginActivity;
import com.example.mooddiary.MoodEvent;
import com.example.mooddiary.MoodList;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * This is a ViewModel holding data for HomeFragment
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