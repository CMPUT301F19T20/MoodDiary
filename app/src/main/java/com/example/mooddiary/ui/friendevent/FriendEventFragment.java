package com.example.mooddiary.ui.friendevent;

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

import com.example.mooddiary.R;

/**
 * This is FriendEventFragment which shows recent friends' mood event
 */
public class FriendEventFragment extends Fragment {

    private FriendEventViewModel friendEventViewModel;
    /**
     * This creates the view for the list of user's friends' friends mood events.
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
        friendEventViewModel =
                ViewModelProviders.of(this).get(FriendEventViewModel.class);
        View root = inflater.inflate(R.layout.fragment_friend_event, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        friendEventViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}