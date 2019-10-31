package com.example.mooddiary.ui.mymap;

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

import com.example.mooddiary.MapsActivity;
import com.example.mooddiary.R;

public class MyMapFragment extends Fragment {

    private MyMapViewModel myMapViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myMapViewModel =
                ViewModelProviders.of(this).get(MyMapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my_map, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        /*
        myMapViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
         */
        Intent intent  = new Intent(getActivity(), MapsActivity.class);
        startActivity(intent);
        return root;
    }
}