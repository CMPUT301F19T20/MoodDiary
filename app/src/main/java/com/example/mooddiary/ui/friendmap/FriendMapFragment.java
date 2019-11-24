package com.example.mooddiary.ui.friendmap;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mooddiary.Database;
import com.example.mooddiary.LoginActivity;
import com.example.mooddiary.MapsActivity;
import com.example.mooddiary.Mood;
import com.example.mooddiary.MoodEvent;
import com.example.mooddiary.MoodList;
import com.example.mooddiary.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a FriendMapFragment which shows user's friends' mood event map
 */
public class FriendMapFragment extends Fragment {

    private FriendMapViewModel friendMapViewModel;
    private MapView friendMapMoodMapMap;
    private GoogleMap friendMap;
    private ProgressBar friendMapLoadingProgress;
    private String TAG2 = "NUMBER";
    private String TAG1 = "location";
    ArrayList<MoodEvent> followsMoods = new ArrayList<>();

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


        friendMapMoodMapMap = root.findViewById(R.id.friendmap_map_map);
        friendMapMoodMapMap.onCreate(savedInstanceState);
        friendMapLoadingProgress = root.findViewById(R.id.friendMap_loading_progress);

        friendMapLoadingProgress.setVisibility(View.VISIBLE);

        //getFriends();
        //getFriendsEvents();
        friendMapMoodMapMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                friendMap = googleMap;
                friendMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(60, -100)));
                friendMapLoadingProgress.setVisibility(View.INVISIBLE);
            }
        });


        DocumentReference docRef = Database.getUserFollowList(LoginActivity.userName);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                ArrayList<String> following = (ArrayList<String>) documentSnapshot.get("FollowList");
                friendMapViewModel.getFriendsRecentEvent().clear();
                for (String username : following) {
                    DocumentReference friendRef = Database.getUserMoodList(username);
                    friendRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            MoodList moodList = documentSnapshot.toObject(MoodList.class);
                            MoodEvent m = moodList.getAllMoodList().get(0);
                            if (m.getLocation() != null) {
                                LatLng markPoint = getLocationLatLng(getContext(), m.getLocation());
                                if (markPoint != null) {
                                    friendMap.addMarker(new MarkerOptions().position(markPoint).title(m.getUsername()).icon(
                                            BitmapDescriptorFactory.fromResource(m.getMood().getMarker())));
                                }

                            }
                        }
                    });
                }
            }
        });

        return root;
    }

    /*
    public void getFriends() {
        DocumentReference docRef = Database.getUserFollowList(LoginActivity.userName);
        friendMapViewModel.getFriendsName().clear();
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        ArrayList<String> follows = (ArrayList<String>) document.get("FollowList");
                        for (String name : follows) {
                            friendMapViewModel.getFriendsName().add(name);
                            Log.d(TAG, "onComplete: " + name);
                        }
                    }
                }
            }
        });
    }

     */
    /*
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

     */


    public LatLng getLocationLatLng(Context context, String locationName) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng l1 = null;

        try {
            address = coder.getFromLocationName(locationName, 5);
            if (address == null) {
                return null;
            }
            if (!(address.isEmpty())){
                Address location = address.get(0);
                l1 = new LatLng(location.getLatitude(), location.getLongitude());
            }
            else{
                Toast.makeText(getActivity(), locationName+" is not a valid address, please " +
                        "enter the correct address", Toast.LENGTH_SHORT).show();
                return null;
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return l1;
    }



    @Override
    public void onResume() {
        super.onResume();
        friendMapMoodMapMap.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        friendMapMoodMapMap.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        friendMapMoodMapMap.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        friendMapMoodMapMap.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        friendMapMoodMapMap.onLowMemory();
    }
}