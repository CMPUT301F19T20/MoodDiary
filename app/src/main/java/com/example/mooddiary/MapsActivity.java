package com.example.mooddiary;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<MoodEvent> myMoods = new MoodList().getAllMoodList();
    private ArrayList<MoodEvent> friendMoods = new MoodList().getAllMoodList();
    private ArrayList<MoodEvent> myMapMoods = new ArrayList<>();
    private ArrayList<MoodEvent> friendMapMoods = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        String map = intent.getStringExtra("map");
        if (map.equals("mymap")){
            getMyMapMoods(myMoods);
            setFriendMapMarker();
        }
        else if(map.equals("friendmap")){
            getFriendMapMoods(friendMoods);
            setFriendMapMarker();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    /**
     * Getting the latitude and longitude of a location string by geocoder
     * May throw IOException if the locationName does not exist
     * @param context
     * @param locationName
     * @return
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

            Address location = address.get(0);
            l1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return l1;
    }

    /**
     * get a list of mood events with a string of location of the user
     * @param myMoodList
     */
    public void getMyMapMoods(ArrayList<MoodEvent> myMoodList){
        for (MoodEvent moodEvent : myMoodList){
            if (!(moodEvent.getLocation()==null))
                myMoodList.add(moodEvent);
        }
    }

    /**
     * get a list of mood events with a string of location of the user's friends
     * @param friendMoodList
     */
    public void getFriendMapMoods(ArrayList<MoodEvent> friendMoodList){
        for (MoodEvent moodEvent:friendMoodList){
            if (!(moodEvent.getLocation()==null))
                friendMapMoods.add(moodEvent);
        }
    }

    /**
     * set the location point markers of the user on the map
     */
    public void setMyMapMarker(){
        for (MoodEvent moodEvent:myMapMoods){
            String locationName = moodEvent.getLocation();
            LatLng markPoint = getLocationLatLng(getApplicationContext(),locationName);
            mMap.addMarker(new MarkerOptions().position(markPoint).title("new mood added"));
        }
    }

    public void setFriendMapMarker(){
        for (MoodEvent moodEvent:friendMapMoods){
            String locationName = moodEvent.getLocation();
            LatLng markPoint = getLocationLatLng(getApplicationContext(),locationName);
            mMap.addMarker(new MarkerOptions().position(markPoint).title("new mood added"));
        }
    }
}

