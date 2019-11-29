package com.example.mooddiary.ui.mymap;

import android.content.Context;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * This is MyMapFragment which shows user's mood event map
 */
public class MyMapFragment extends Fragment {

    private MapView myMapMoodMapMap;
    private GoogleMap myMap;
    private ArrayList<MoodEvent> myMoodList = new ArrayList<>();
    private ProgressBar myMapLoadingProgress;
  
    /**
     * This creates the view for the user's mood event map.
     * @param inflater
     *      This is a LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container
     *      This can be null. If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState
     *      If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return
     *      Return the view for the fragment UI
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_map, container, false);

        myMapMoodMapMap = root.findViewById(R.id.myMap_map_map);
        myMapMoodMapMap.onCreate(savedInstanceState);
        myMapLoadingProgress = root.findViewById(R.id.myMap_loading_progress);

        myMapLoadingProgress.setVisibility(View.VISIBLE);

        myMapMoodMapMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                myMap = googleMap;
                int currentMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                myMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(),
                        currentMode == Configuration.UI_MODE_NIGHT_YES? R.raw.night_map:R.raw.day_map));
                myMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(60,-100)));
                DocumentReference docRef = Database.getUserMoodList(LoginActivity.userName);
                docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if(documentSnapshot == null) { return; }
                        ArrayList<MoodEvent> myAllMoodEvents = documentSnapshot.toObject(MoodList.class).getAllMoodList();
                        myMapLoadingProgress.setVisibility(View.INVISIBLE);
                        myMap.clear();
                        if(!myAllMoodEvents.isEmpty()) {
                            LatLngBounds.Builder boundBuilder = new LatLngBounds.Builder();
                            boolean hasPoint = false;
                            for(MoodEvent m: myAllMoodEvents) {
                                if(m.getLocation() != "") {
                                    LatLng markPoint = new LatLng(m.getLatitude(), m.getLongitude());
                                    if(markPoint.latitude != 100 && markPoint.longitude != 200) {
                                        myMap.addMarker(new MarkerOptions().position(markPoint).title(m.getMood().getMood()).icon(
                                                BitmapDescriptorFactory.fromResource(m.getMood().getMarker())));
                                        boundBuilder.include(markPoint);
                                        hasPoint = true;
                                    }
                                }
                            }
                            if(hasPoint) {
                                myMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundBuilder.build(), 250));
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
        myMapMoodMapMap.onResume();
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
        myMapMoodMapMap.onPause();
    }
    /**
     * The final call you receive before your activity is destroyed.
     * This can happen either because the activity is finishing
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        myMapMoodMapMap.onDestroy();
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
        myMapMoodMapMap.onSaveInstanceState(outState);
    }
    /**
     * This is called when the overall system is running low on memory,
     * and actively running processes should trim their memory usage.
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        myMapMoodMapMap.onLowMemory();
    }
}
