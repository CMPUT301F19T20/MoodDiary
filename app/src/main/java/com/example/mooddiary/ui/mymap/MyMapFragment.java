package com.example.mooddiary.ui.mymap;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mooddiary.MapsActivity;
import com.example.mooddiary.R;
import com.example.mooddiary.ui.home.HomeViewModel;

public class MyMapFragment extends Fragment {

    private MyMapViewModel myMapViewModel;
    private HomeViewModel viewModelFromHome;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myMapViewModel =
                ViewModelProviders.of(this).get(MyMapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my_map, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);

        viewModelFromHome = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
        //viewModelFromHome.getMoodList();

        Intent intent = new Intent(getActivity(),MapsActivity.class);
        intent.putExtra("map","mymap");
        intent.putExtra("moodlist",viewModelFromHome.getMoodList().getAllMoodList());
        startActivity(intent);


        return root;
    }
}