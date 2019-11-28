package com.example.mooddiary;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
/**
 * Test class for LoginActivity. All the UI tests are written here.Robotium test framework is used
*/

public class TestLoginActivity {
    private Solo solo;
    @Rule
    public ActivityTestRule<LoginActivity> rule = new ActivityTestRule<>(LoginActivity.class, true, true);
    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
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
*Tset from login activity to main activity
* Enter username and click login button
 */
    @Test
    public void checkToHome() {
        solo.assertCurrentActivity("Wong Activity", LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.login_username_edit));
        solo.enterText((EditText) solo.getView(R.id.login_username_edit),"tester");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit),"060199");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wong Activity", MainActivity.class);
    }
/**
*Test for singup activity
* click sign up button
 */
    @Test
    public void checkToSignup() {
        solo.assertCurrentActivity("Wong Activity", LoginActivity.class);
        solo.clickOnButton("Sign up");
        solo.assertCurrentActivity("Wong Activity", SignUpActivity.class);
    }
/**
*check for rejecting invalid username
* enter an invalid username and click login button
 */
    @Test
    public void checkRejectLogin(){
        solo.assertCurrentActivity("Wong Activity", LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.login_username_edit));
        solo.enterText((EditText) solo.getView(R.id.login_username_edit),"notUser");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit),"060199");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wong Activity", LoginActivity.class);
    }
    @Test
    public void checkWrongPassword(){
        solo.assertCurrentActivity("Wong Activity", LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.login_username_edit));
        solo.enterText((EditText) solo.getView(R.id.login_username_edit),"tester");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit),"123456");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wong Activity", LoginActivity.class);
    }
}


