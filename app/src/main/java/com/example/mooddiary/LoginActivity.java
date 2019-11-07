package com.example.mooddiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.SyncFailedException;
import java.util.regex.Pattern;

/**
 * This is an activity where the user login in
 */
public class LoginActivity extends AppCompatActivity {
    private Button login;
    private Button signUp;
    private EditText nameInput;
    static public String userName;
    private FirebaseFirestore db;
    public static final String TAG = LoginActivity.class.getSimpleName();

    /**
     * This creates the view of signup
     * @param savedInstanceState
     *      If the activity is being re-initialized after previously being shut down
     *      then this Bundle contains the data it most recently supplied in.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.login);
        signUp = findViewById(R.id.signup);
        nameInput = findViewById(R.id.username);
        db = FirebaseFirestore.getInstance();
        initButtons();
    }

    /**
     * This initializes Login and Sign up buttons
     */
    public void initButtons(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = nameInput.getText().toString();
                String usernamePat = "^([a-z0-9A-Z]{3,20})$";
                if (!Pattern.matches(usernamePat, nameInput.getText().toString())) {
                    nameInput.setError("Username should more 3 and less than 20 characters with only letters or numbers");
                    nameInput.setText("");
                }
                else{
                    db.collection("users").document("users").collection(userName).document("MoodList").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.exists()){
                                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                                intent.putExtra("EXTRA", "openFragment");
                                startActivity(intent);
                            }
                            else{
                                nameInput.setError("Username doesn't exist");
                                nameInput.setText("");
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
