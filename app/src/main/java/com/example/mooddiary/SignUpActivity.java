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
    private EditText signUpNewUsernameEdit;
    private EditText signUpNewPasswordEdit;
    private EditText signUpNewPasswordCheckEdit;
    private Button confirmButton;
    private Button loginButton;
    private String userName;
    private String password;
    public DocumentReference MoodRef;
    public DocumentReference followRef;
    public DocumentReference followerRef;
    public DocumentReference passwordRef;

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
        signUpNewUsernameEdit = findViewById(R.id.signup_new_username_edit);
        signUpNewPasswordEdit = findViewById(R.id.signup_new_password_edit);
        signUpNewPasswordCheckEdit = findViewById(R.id.signup_new_password_check_edit);
        loginButton = findViewById(R.id.signup_login_button);
        initButton();
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
                String passwordPat = "^([a-z0-9A-Z]{6,20})$";
                if (!Pattern.matches(usernamePat, signUpNewUsernameEdit.getText().toString())) {
                    signUpNewUsernameEdit.setError("Username should more than 3 and less than 20 characters with only letters or numbers");
                    signUpNewUsernameEdit.setText("");
                    valid = false;
                }
                if (valid && !(Pattern.matches(passwordPat, signUpNewPasswordEdit.getText().toString()))) {
                    signUpNewPasswordEdit.setError("Password must be more than 6 and less than 20 characters with only letters or numbers");
                    signUpNewPasswordEdit.setText("");
                    signUpNewPasswordCheckEdit.setText("");
                    valid = false;
                }
                if (valid && !(signUpNewPasswordCheckEdit.getText().toString().equals(signUpNewPasswordEdit.getText().toString()))) {
                    signUpNewPasswordCheckEdit.setError("Password entered here is not same as before");
                    signUpNewPasswordCheckEdit.setText("");
                    valid = false;
                }
                if (valid) {
                    userName = signUpNewUsernameEdit.getText().toString();
                    password = signUpNewPasswordEdit.getText().toString();
                    Database.getUserMoodList(userName).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                signUpNewUsernameEdit.setError("Username already exist");
                                signUpNewUsernameEdit.setText("");
                            } else {
                                // add password
                                Map<String, String> passwordData = new HashMap<>();
                                passwordData.put("Password", password);
                                passwordRef = Database.getUserPassword(userName);
                                passwordRef.set(passwordData);
                                // add follow list
                                ArrayList<String> followList = new ArrayList<>();
                                Map<String, Object> followData = new HashMap<>();
                                followData.put("FollowList", followList);
                                followRef = Database.getUserFollowList(userName);
                                followRef.set(followData);
                                // add follower list
                                ArrayList<String> followerList = new ArrayList<>();
                                Map<String, Object> followerData = new HashMap<>();
                                followerData.put("FollowerList", followerList);
                                followerRef = Database.getUserFollowerList(userName);
                                followerRef.set(followerData);
                                // add mood list
                                MoodRef = Database.getUserMoodList(userName);
                                MoodList moodList = new MoodList();
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
