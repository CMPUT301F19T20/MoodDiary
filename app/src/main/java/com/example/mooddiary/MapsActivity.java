package com.example.mooddiary;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<MoodEvent> myMoods  = new ArrayList<>();
    private ArrayList<MoodEvent> friendMoods = new ArrayList<>();
    private ArrayList<MoodEvent> myMapMoods = new ArrayList<>();
    private ArrayList<MoodEvent> friendMapMoods = new ArrayList<>();
    String map = null;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        this.map = intent.getStringExtra("map");
        //myMoods = (ArrayList<MoodEvent>)intent.getSerializableExtra("usermoods");
        //friendMoods = (ArrayList<MoodEvent>)intent.getSerializableExtra("friendmoods");

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
        if (map.equals("mymap")){
            testMoodList();
            getMyMapMoods(myMoods);
            setMyMapMarker();
        }
        else if(map.equals("friendmap")){
            testMoodList();
            getFriendMapMoods(friendMoods);
            setFriendMapMarker();
        }
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
            if (!(address.isEmpty())){
                Address location = address.get(0);
                l1 = new LatLng(location.getLatitude(), location.getLongitude());
            }
            else{
                Toast.makeText(MapsActivity.this, locationName+" is not a valid address, please " +
                        "enter the correct address", Toast.LENGTH_SHORT).show();
                return null;
            }

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
        for (int i = 0; i< myMoodList.size();i++){
            if (myMoodList.get(i).getLocation()!=null)
                myMapMoods.add(myMoodList.get(i));
        }
    }

    /**
     * get a list of mood events with a string of location of the user's friends
     * @param friendMoodList
     */
    public void getFriendMapMoods(ArrayList<MoodEvent> friendMoodList){
        for (int i = 0; i< friendMoodList.size();i++){
            if (friendMoodList.get(i).getLocation()!=null)
                friendMapMoods.add(friendMoodList.get(i));
        }
    }

    /**
     * set the location point markers of the user on the map
     */
    public void setMyMapMarker(){
        for (MoodEvent moodEvent:myMapMoods) {
            String locationName = moodEvent.getLocation();
            LatLng markPoint = getLocationLatLng(getApplicationContext(), locationName);
            String moodType = moodEvent.getMood().getMood();
            if(markPoint!=null){
                switch (moodType){
                    case "happy":
                        mMap.addMarker(new MarkerOptions().position(markPoint).icon(
                                BitmapDescriptorFactory.fromResource(R.drawable.happy)));
                        break;
                    case "sad":
                        mMap.addMarker(new MarkerOptions().position(markPoint).icon(
                                BitmapDescriptorFactory.fromResource(R.drawable.sad)));
                        break;
                    case "content":
                        mMap.addMarker(new MarkerOptions().position(markPoint).icon(
                                BitmapDescriptorFactory.fromResource(R.drawable.content)));
                        break;
                    case "angry":
                        mMap.addMarker(new MarkerOptions().position(markPoint).icon(
                                BitmapDescriptorFactory.fromResource(R.drawable.angry)));
                        break;
                    case "stressed":
                        mMap.addMarker(new MarkerOptions().position(markPoint).icon(
                                BitmapDescriptorFactory.fromResource(R.drawable.stressed)));
                    case  "meh":
                        mMap.addMarker(new MarkerOptions().position(markPoint).icon(
                                BitmapDescriptorFactory.fromResource(R.drawable.meh)));
                }
            }
        }
    }

    public void setFriendMapMarker(){
        for (MoodEvent moodEvent:friendMapMoods) {
            String locationName = moodEvent.getLocation();
            LatLng markPoint = getLocationLatLng(getApplicationContext(), locationName);
            String moodType = moodEvent.getMood().getMood();
            if (markPoint != null) {
                switch (moodType) {
                    case "happy":
                        mMap.addMarker(new MarkerOptions().position(markPoint).icon(
                                BitmapDescriptorFactory.fromResource(R.drawable.happy)));
                        break;
                    case "sad":
                        mMap.addMarker(new MarkerOptions().position(markPoint).icon(
                                BitmapDescriptorFactory.fromResource(R.drawable.sad)));
                        break;
                    case "content":
                        mMap.addMarker(new MarkerOptions().position(markPoint).icon(
                                BitmapDescriptorFactory.fromResource(R.drawable.content)));
                        break;
                    case "angry":
                        mMap.addMarker(new MarkerOptions().position(markPoint).icon(
                                BitmapDescriptorFactory.fromResource(R.drawable.angry)));
                        break;
                    case "stressed":
                        mMap.addMarker(new MarkerOptions().position(markPoint).icon(
                                BitmapDescriptorFactory.fromResource(R.drawable.stressed)));
                    case "meh":
                        mMap.addMarker(new MarkerOptions().position(markPoint).icon(
                                BitmapDescriptorFactory.fromResource(R.drawable.meh)));
                }
            }
        }
    }

    public void testMoodList(){
        MoodEvent moodEvent = new MoodEvent("happy","2000-09-20","13:33","alone","sbsbsbsbs","ate well",null);
        MoodEvent moodEvent1 = new MoodEvent("happy","2000-09-20","13:33","alone","1124223431223 75Ave NorthWest","ate well",null);

        myMoods.add(moodEvent);
        myMoods.add(moodEvent1);



        friendMoods.add(moodEvent);

    }

}

