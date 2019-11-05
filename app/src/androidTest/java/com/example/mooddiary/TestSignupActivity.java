package com.example.mooddiary;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class TestSignupActivity {
    private Solo solo;
    @Rule
    public ActivityTestRule<SignUpActivity> rule = new ActivityTestRule<>(SignUpActivity.class, true, true);


    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }
    @Test
    public void testToLogin(){
        solo.assertCurrentActivity("Wong Activity", SignUpActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.newUser));
        solo.enterText((EditText) solo.getView(R.id.newUser),"tester");
        solo.clickOnButton("Confirm");
        solo.assertCurrentActivity("Wong Activity", LoginActivity.class);
    }
    @Test
    public void testToLogin2(){
        solo.assertCurrentActivity("Wong Activity", SignUpActivity.class);
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wong Activity", LoginActivity.class);

    }

}
