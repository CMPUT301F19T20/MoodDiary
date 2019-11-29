package com.example.mooddiary;

import android.app.Activity;
import android.view.KeyEvent;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.mooddiary.ui.friendevent.FriendEventFragment;
import com.example.mooddiary.ui.friendmap.FriendMapFragment;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class Testdrawer {
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
        Utils.saveSharedSetting(solo.getCurrentActivity().getApplicationContext(),"isGuide",true);
    }

    /**
     * Test the transform between activiyies in the slide menu and the right show of the user name
     */
    @Test
    public void testToFriendMood() throws InterruptedException {
        solo.sleep(2000);
        System.out.println(Utils.readSharedSetting(solo.getCurrentActivity().getApplicationContext(), "isGuide", false));
        solo.assertCurrentActivity("Wong Activity", LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.login_username_edit));
        solo.enterText((EditText) solo.getView(R.id.login_username_edit),"tester");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit),"060199");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wong Activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.waitForText("tester", 1, 2000);
        solo.clickOnScreen(0,700);
        solo.assertCurrentActivity("Wong Activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnScreen(0,800);
        solo.assertCurrentActivity("Wong Activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnScreen(0,950);
        solo.assertCurrentActivity("Wong Activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnScreen(0,1100);
        solo.assertCurrentActivity("Wong Activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnScreen(0,1200);
        solo.assertCurrentActivity("Wong Activity", MainActivity.class);

    }


}
