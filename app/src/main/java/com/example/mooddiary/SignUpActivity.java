package com.example.mooddiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * This is an activity where user signs up
 */
public class SignUpActivity extends AppCompatActivity {
    private EditText newUsernameEdit;
    private Button confirmButton;
    private Button loginButton;
    private String userName;
    private FirebaseFirestore db;
    public DocumentReference MoodRef;
    public DocumentReference FriendsRef;

    public static final String TAG = SignUpActivity.class.getSimpleName();


    /**
     * This creates the Sign Up activity and its layout
     * @param savedInstanceState
     *      If the activity is being re-initialized after previously being shut down
     *      then this Bundle contains the data it most recently supplied in.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);
        confirmButton = findViewById(R.id.signup_confirm_button);
        newUsernameEdit = findViewById(R.id.signup_new_username_edit);
        loginButton = findViewById(R.id.signup_login_button);
        db = FirebaseFirestore.getInstance();
        initButton();
        Intent intent = getIntent();
    }

    /**
     * This initializes Confirm button
     */
    public void initButton() {
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid = true;
                String usernamePat = "^([a-z0-9A-Z]{3,20})$";
                if (!Pattern.matches(usernamePat, newUsernameEdit.getText().toString())) {
                    newUsernameEdit.setError("Username should more 3 and less than 20 characters with only letters or numbers");
                    newUsernameEdit.setText("");
                    valid = false;
                }
                if (valid) {
                    userName = newUsernameEdit.getText().toString();
                    db.collection("users").document("users").collection(userName).document("MoodList").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                newUsernameEdit.setError("Username already exist");
                                newUsernameEdit.setText("");
                            } else {
                                MoodList moodList = new MoodList();
                                ArrayList<String> friendsList = new ArrayList<>();
                                Map<String, Object> friendsData = new HashMap<>();
                                friendsData.put("FriendsList", friendsList);
                                MoodRef = db.collection("users").document("users").collection(userName).document("MoodList");
                                FriendsRef = db.collection("users").document("users").collection(userName).document("FriendsList");
                                FriendsRef.set(friendsData);
                                MoodRef.set(moodList)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(SignUpActivity.this, "New User signed up", Toast.LENGTH_SHORT).show();
                                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(SignUpActivity.this, "Fail to signed up", Toast.LENGTH_SHORT).show();
                                                Log.w(TAG, "Error adding document", e);
                                            }
                                        });
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
