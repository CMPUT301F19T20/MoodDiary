package com.example.mooddiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

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
    private Button loginButton;
    private Button signUpButton;
    private EditText loginUsernameEdit;
    private EditText loginPasswordEdit;
    public static String userName;
    private String password;
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
        boolean isNightMode = NightModeConfig.getInstance().getNightMode(getApplicationContext());
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.login_login_button);
        signUpButton = findViewById(R.id.login_signup_button);
        loginUsernameEdit = findViewById(R.id.login_username_edit);
        loginPasswordEdit = findViewById(R.id.login_password_edit);
        initButtons();
    }

    /**
     * This initializes Login and Sign up buttons
     */
    public void initButtons(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = loginUsernameEdit.getText().toString();
                password = loginPasswordEdit.getText().toString();
                String usernamePat = "^([a-z0-9A-Z]{3,20})$";
                String passwordPat = "^([a-z0-9A-Z]{6,20})$";
                if (!Pattern.matches(usernamePat, loginUsernameEdit.getText().toString())) {
                    loginUsernameEdit.setError("Username should more 3 and less than 20 characters with only letters or numbers");
                    loginUsernameEdit.setText("");
                    loginPasswordEdit.setText("");
                } else if (!Pattern.matches(passwordPat, loginPasswordEdit.getText().toString())) {
                    loginPasswordEdit.setError("Password should more 6 and less than 20 characters with only letters or numbers");
                    loginPasswordEdit.setText("");
                } else {
                    Database.getUserMoodList(LoginActivity.userName).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (!documentSnapshot.exists()) {
                                loginUsernameEdit.setError("Username doesn't exist");
                                loginUsernameEdit.setText("");
                                loginPasswordEdit.setText("");
                            } else {
                                Database.getUserPassword(LoginActivity.userName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document != null) {
                                                String truePassword = (String) document.get("Password");
                                                if (truePassword.equals(password)) {
                                                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                    intent.putExtra("EXTRA", "openFragment");
                                                    startActivity(intent);
                                                } else {
                                                    loginPasswordEdit.setError("Incorrect password");
                                                    loginPasswordEdit.setText("");
                                                }
                                            }
                                        }
                                    }
                                });
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
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
