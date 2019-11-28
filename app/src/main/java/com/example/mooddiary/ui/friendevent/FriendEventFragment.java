package com.example.mooddiary.ui.friendevent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.mooddiary.Database;
import com.example.mooddiary.FriendMoodAdapter;
import com.example.mooddiary.LoginActivity;
import com.example.mooddiary.MoodEvent;
import com.example.mooddiary.MoodList;
import com.example.mooddiary.R;
import com.example.mooddiary.ViewFriendActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import java.util.ArrayList;

/**
 * This is FriendEventFragment which shows recent friends' mood event
 */
public class FriendEventFragment extends Fragment {

    private ListView friendMoodEventListView;
    private FriendMoodAdapter friendMoodAdapter;
    private ArrayList<MoodEvent> friendMoodList = new ArrayList<>();

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

        View root = inflater.inflate(R.layout.fragment_friend_event, container, false);

        friendMoodEventListView = root.findViewById(R.id.friend_mood_event_list_view);
        friendMoodEventListView.setEmptyView(root.findViewById(R.id.empty_view));
        friendMoodAdapter = new FriendMoodAdapter(getActivity(),
                R.layout.friend_mood_list_item, friendMoodList);
        friendMoodEventListView.setAdapter(friendMoodAdapter);

        DocumentReference docRef = Database.getUserFollowList(LoginActivity.userName);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.get("FollowList") != null) {
                    ArrayList<String> following = (ArrayList<String>)documentSnapshot.get("FollowList");
                    friendMoodList.clear();
                    for(String username: following) {
                        DocumentReference friendRef = Database.getUserMoodList(username);
                        friendRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                MoodList moodList = documentSnapshot.toObject(MoodList.class);
                                if(!moodList.getAllMoodList().isEmpty()) {
                                    friendMoodList.add(moodList.getAllMoodList().get(0));
                                    friendMoodAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                }
            }
        });

        friendMoodEventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ViewFriendActivity.class);
                intent.putExtra("moodEvent", (MoodEvent)friendMoodEventListView.getItemAtPosition(position));
                startActivity(intent);
            }
        });
        return root;
    }
}