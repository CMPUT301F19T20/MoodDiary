package com.example.mooddiary.ui.friendmap;

import android.content.Context;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.mooddiary.Database;
import com.example.mooddiary.LoginActivity;
import com.example.mooddiary.MoodEvent;
import com.example.mooddiary.MoodList;
import com.example.mooddiary.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import java.util.ArrayList;

/**
 * This is a FriendMapFragment which shows user's friends' mood event map
 */
public class FriendMapFragment extends Fragment {

    private MapView friendMapMoodMapMap;
    private GoogleMap friendMap;
    private ProgressBar friendMapLoadingProgress;
    

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

        View root = inflater.inflate(R.layout.fragment_friend_map, container, false);


        friendMapMoodMapMap = root.findViewById(R.id.friendmap_map_map);
        friendMapMoodMapMap.onCreate(savedInstanceState);
        friendMapLoadingProgress = root.findViewById(R.id.friendMap_loading_progress);

        friendMapLoadingProgress.setVisibility(View.VISIBLE);


        friendMapMoodMapMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                friendMap = googleMap;
                int currentMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                friendMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(),
                        currentMode == Configuration.UI_MODE_NIGHT_YES? R.raw.night_map:R.raw.day_map));
                friendMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(60, -100)));
                DocumentReference docRef = Database.getUserFollowList(LoginActivity.userName);
                docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if(documentSnapshot == null) { return; }
                        ArrayList<String> following = (ArrayList<String>) documentSnapshot.get("FollowList");
                        friendMapLoadingProgress.setVisibility(View.INVISIBLE);
                        friendMap.clear();
                        if(!following.isEmpty()) {
                            final LatLngBounds.Builder boundBuilder = new LatLngBounds.Builder();
                            for (String username : following) {
                                DocumentReference friendRef = Database.getUserMoodList(username);
                                    friendRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            ArrayList<MoodEvent> m = documentSnapshot.toObject(MoodList.class).getAllMoodList();
                                            if (!m.isEmpty() && m.get(0).getLocation() != "") {
                                                LatLng markPoint = new LatLng(m.get(0).getLatitude(),m.get(0).getLongitude());
                                                if (markPoint.latitude != 100 && markPoint.longitude != 200) {
                                                    friendMap.addMarker(new MarkerOptions().position(markPoint)
                                                            .title(m.get(0).getUsername() + ": " + m.get(0).getMood().getMood()).icon(
                                                            BitmapDescriptorFactory.fromResource(m.get(0).getMood().getMarker())));
                                                    boundBuilder.include(markPoint);
                                                    if(username.equals(following.get(following.size()-1))) {
                                                        friendMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundBuilder.build(), 250));
                                                    }
                                                }
                                            }
                                        }
                                    });
                            }
                        }
                    }
                });
            }
        });




        return root;
    }

    /**
     * Called when the activity will start interacting with the user.
     * At this point your activity is at the top of its activity stack
     * with user input going to it.
     */
    @Override
    public void onResume() {
        super.onResume();
        friendMapMoodMapMap.onResume();
    }

    /**
     * Called when the activity loses foreground state,
     * is no longer focusable or before transition to stopped/hidden or destroyed state.
     * The activity is still visible to user, so it's recommended to keep it visually active and continue updating the UI.
     * Implementations of this method must be very quick because the next activity will not be resumed until this method returns.
     */
    @Override
    public void onPause() {
        super.onPause();
        friendMapMoodMapMap.onPause();
    }

    /**
     * The final call you receive before your activity is destroyed.
     * This can happen either because the activity is finishing
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        friendMapMoodMapMap.onDestroy();
    }

    /**
     * create views, bind data to lists, etc.
     * This method also provides you with a Bundle containing the activity's previously frozen state,
     * if there was one.
     * @param outState
     *  This is activities created with the attribute
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        friendMapMoodMapMap.onSaveInstanceState(outState);
    }

    /**
     * This is called when the overall system is running low on memory,
     * and actively running processes should trim their memory usage.
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        friendMapMoodMapMap.onLowMemory();
    }
}
