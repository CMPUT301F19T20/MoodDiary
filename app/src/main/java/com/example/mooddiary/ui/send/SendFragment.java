package com.example.mooddiary.ui.send;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.mooddiary.Database;
import com.example.mooddiary.LoginActivity;
import com.example.mooddiary.R;
import com.example.mooddiary.Request;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * This is SendFragment where the user sending friend request
 */
public class SendFragment extends Fragment {

    private EditText sendRequestUsernameEdit;
    private Button sendSendRequestButton;
    /**
     * This creates the view for sending friend request.
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
        View root = inflater.inflate(R.layout.fragment_send, container, false);

        sendRequestUsernameEdit = root.findViewById(R.id.send_request_username_edit);
        sendSendRequestButton = root.findViewById(R.id.send_send_request_button);

        sendSendRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String friendUsername = sendRequestUsernameEdit.getText().toString();
                String usernamePat = "^([a-z0-9A-Z]{3,20})$";
                if (!Pattern.matches(usernamePat, friendUsername)) {
                    sendRequestUsernameEdit.setError("Invalid Friend's Username");
                    sendRequestUsernameEdit.setText("");
                } else if(friendUsername.equals(LoginActivity.userName)) {
                    sendRequestUsernameEdit.setError("You cannot follow yourself");
                    sendRequestUsernameEdit.setText("");
                } else {
                    Database.db.collection("users")
                            .document("users")
                            .collection(friendUsername).get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.getResult().isEmpty()) {
                                        sendRequestUsernameEdit.setError("Username Doesn't Exist");
                                        sendRequestUsernameEdit.setText("");
                                    } else {
                                        Database.getUserFollowList(LoginActivity.userName).get()
                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        ArrayList<String> friends = (ArrayList<String>)documentSnapshot.get("FollowList");
                                                        if(!friends.contains(friendUsername)) {
                                                            Request request = new Request(LoginActivity.userName, friendUsername);
                                                            Request.send(request);
                                                            sendRequestUsernameEdit.setText("");
                                                            Toast.makeText(getActivity(), "request sent", Toast.LENGTH_SHORT).show();
                                                        }
                                                        else {
                                                            Toast.makeText(getActivity(), "You are already friends", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });
                }
            }
        });

        return root;
    }
}