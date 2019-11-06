package com.example.mooddiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private EditText newUser;
    private Button confirm;
    private Button login;
    private String userName;
    private FirebaseFirestore db;
    public DocumentReference MoodRef;
    public DocumentReference FriendsRef;

    public static final String TAG = SignUpActivity.class.getSimpleName();

    /**
     * This creates the view of login
     * @param savedInstanceState
     *      If the activity is being re-initialized after previously being shut down
     *      then this Bundle contains the data it most recently supplied in.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);
        confirm = findViewById(R.id.confirm);
        newUser = findViewById(R.id.newUser);
        login = findViewById(R.id.login);
        db = FirebaseFirestore.getInstance();


        initButton();
        Intent intent = getIntent();
    }

    public void initButton() {
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = newUser.getText().toString();
                MoodList moodList = new MoodList();
                ArrayList<String> friendsList = new ArrayList<>();
                Map<String, Object> friendsData = new HashMap<>();
                friendsData.put("FriendsList",friendsList);
                MoodRef = db.collection("users").document("users").collection(userName).document("MoodList");
                FriendsRef = db.collection("users").document("users").collection(userName).document("FriendsList");
                FriendsRef.set(friendsData);
                MoodRef.set(moodList)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(SignUpActivity.this,"New User signed up",Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignUpActivity.this,"Fail to signed up",Toast.LENGTH_SHORT).show();
                                Log.w(TAG,"Error adding document",e);
                            }
                        });

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }


}
