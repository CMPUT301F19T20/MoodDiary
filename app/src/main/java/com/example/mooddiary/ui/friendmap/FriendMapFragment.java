package com.example.mooddiary.ui.friendmap;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mooddiary.Database;
import com.example.mooddiary.LoginActivity;
import com.example.mooddiary.MapsActivity;
import com.example.mooddiary.MoodEvent;
import com.example.mooddiary.MoodList;
import com.example.mooddiary.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * This is a FriendMapFragment which shows user's friends' mood event map
 */
public class FriendMapFragment extends Fragment {

    private FriendMapViewModel friendMapViewModel;
    //FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * This creates the view for the list of user's friend's mood event map.
     *
     * @param inflater           This is a LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          This can be null. If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the view for the fragment UI
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        friendMapViewModel =
                ViewModelProviders.of(this).get(FriendMapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_friend_map, container, false);
        final TextView textView = root.findViewById(R.id.friend_map);

        getFriends();
        getFriendsEvents();

        Intent intent = new Intent(getActivity(), MapsActivity.class);
        intent.putExtra("friendEvents",friendMapViewModel.getFriendsRecentEvent());
        intent.putExtra("map", "friendmap");
        startActivity(intent);

        return root;
    }

    public void getFriends() {
        DocumentReference docRef = Database.getUserFollowList(LoginActivity.userName);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ArrayList<String> friendsName = documentSnapshot.toObject(ArrayList.class);
                friendMapViewModel.getFriendsName().clear();
                for (String name : friendsName) {
                    friendMapViewModel.getFriendsName().add(name);
                }
            }
        });
    }

    public void getFriendsEvents(){
        friendMapViewModel.getFriendsRecentEvent().clear();
        for (String name:friendMapViewModel.getFriendsName()){
            DocumentReference docRef = Database.getUserMoodList(name);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    MoodList moodList = documentSnapshot.toObject(MoodList.class);
                    moodList.sortAllMoodList();
                    friendMapViewModel.getFriendsRecentEvent().put(name,moodList.getAllMoodList().get(0));
                }
            });
        }
    }
}