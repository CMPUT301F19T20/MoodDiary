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

import com.example.mooddiary.MapsActivity;
import com.example.mooddiary.R;

/**
 * This is a FriendMapFragment which shows user's friends' mood event map
 */
public class FriendMapFragment extends Fragment {

    private FriendMapViewModel friendMapViewModel;

    /**
     * This creates the view for the list of user's friend's mood event map.
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
        friendMapViewModel =
                ViewModelProviders.of(this).get(FriendMapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_friend_map, container, false);
        final TextView textView = root.findViewById(R.id.friend_map);

        Intent intent = new Intent(getActivity(), MapsActivity.class);
        intent.putExtra("map","friendmap");
        startActivity(intent);

        return root;
    }
}