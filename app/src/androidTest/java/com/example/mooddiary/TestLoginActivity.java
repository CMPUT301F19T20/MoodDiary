package com.example.mooddiary;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
/*
 * Test class for LoginActivity. All the UI tests are written here.
 */

public class TestLoginActivity {
    private Solo solo;
    @Rule
    public ActivityTestRule<LoginActivity> rule = new ActivityTestRule<>(LoginActivity.class, true, true);


    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    @Test
    public void checkToHome() {
        solo.assertCurrentActivity("Wong Activity", LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.username));
        solo.enterText((EditText) solo.getView(R.id.username),"xinman");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wong Activity", MainActivity.class);


    }

    @Test
    public void checkToSignup() {
        solo.assertCurrentActivity("Wong Activity", LoginActivity.class);
        solo.clickOnButton("Sign up");
        solo.assertCurrentActivity("Wong Activity", SignUpActivity.class);
    }

    @Test
    public void checkRejectLogin(){
        solo.assertCurrentActivity("Wong Activity", LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.username));
        solo.enterText((EditText) solo.getView(R.id.username),"notUser");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wong Activity", LoginActivity.class);
    }
    @Test
    public void checkToMianToAdd() {
        solo.assertCurrentActivity("Wong Activity", LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.username));
        solo.enterText((EditText) solo.getView(R.id.username),"xinman");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.fab));
        solo.assertCurrentActivity("Wong Activity", AddMoodEventActivity.class);

    }
}


