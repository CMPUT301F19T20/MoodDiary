package com.example.mooddiary;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
/**
 * Test class for SignUpActivity. All the UI tests are written here.Robotium test framework is used
 */
public class TestSignupActivity {
    private Solo solo;
    @Rule
    public ActivityTestRule<SignUpActivity> rule = new ActivityTestRule<>(SignUpActivity.class, true, true);
    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        Utils.saveSharedSetting(solo.getCurrentActivity(),"isTutorial",true);
    }
    /**
     * Gets the Activity
     * @throws Exception
     */
    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    /**
     * Test a sign up without password
     */
    @Test
    public void testSignupNopassword(){
        solo.assertCurrentActivity("Wong Activity", SignUpActivity.class);
        solo.enterText((EditText) solo.getView(R.id.signup_new_username_edit),"tester");
        solo.clickOnButton("Confirm");
        solo.assertCurrentActivity("Wong Activity", SignUpActivity.class);
    }
    /**
     *Test nomal changing to LoginActivity
     * Click Confirm Button
     */
    @Test
    public void testToLogin2(){
        solo.assertCurrentActivity("Wong Activity", SignUpActivity.class);
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wong Activity", LoginActivity.class);

    }
    @After
    public void tearDown(){
        Utils.saveSharedSetting(solo.getCurrentActivity(),"isTutorial",false);
    }

}
