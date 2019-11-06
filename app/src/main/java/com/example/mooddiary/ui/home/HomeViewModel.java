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
    final FirebaseFirestore db = FirebaseFirestore.getInstance();


    public HomeViewModel() {
        moodListHome = new MoodList();
    }
    // Attach a listener to read the data at our posts reference

//    public HomeViewModel() {
//        moodListHome = new MoodList();

       /* DatabaseReference ref = db.collection("users").document("chenge");

        // Attach a listener to read the data at our posts reference
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                System.out.println(documentSnapshot.toObject(User.class));
                //moodAdapter =
                //new MoodAdapter(getActivity(), R.layout.mood_list_item, user1.getMoodList().getAllMoodList());
            }
        });*/

//    }


    public MoodList getMoodList() {
//        if (moodListHome == null) {
//            moodListHome = new MoodList();
//
//        }
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
        Log.d("tag","111");

    }
    public void setMoodListHome(MoodList moodListHome){this.moodListHome = moodListHome;}

    private void loadMoodListHome() {
        DocumentReference docRef = db.collection("users").document("users").collection(LoginActivity.userName).document("MoodList");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
              //  MoodList moodlist = documentSnapshot.toObject(MoodList.class);
                MoodList mood = new MoodList();
                Log.d("tag_db","111");
                MoodEvent moodEvent6 =
                        new MoodEvent("content", "2019/10/19", "10:40", "alone", "", "", "");
                Log.d("tag_db","111");
                mood.add(moodEvent6);
                Log.d("tag_db","111");
                moodListHome = mood;
                Log.d("tag_db","111");

                //setList(moodlist);

            }
        });

    }



}