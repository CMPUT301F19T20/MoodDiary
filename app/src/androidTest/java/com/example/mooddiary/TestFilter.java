package com.example.mooddiary;

import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class TestFilter {
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
    public void Add() {
        solo.assertCurrentActivity("Wong Activity", LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.login_username_edit));
        solo.enterText((EditText) solo.getView(R.id.login_username_edit),"tester");
        solo.enterText((EditText) solo.getView(R.id.login_password_edit),"060199");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.fab));
        solo.assertCurrentActivity("Wong Activity", AddMoodEventActivity.class);
        Spinner spinner = (Spinner) solo.getView(R.id.add_mood_spinner);
        spinner.setSelection(1, true);
        //solo.clickOnButton("Choose the data");
        solo.clickOnText("Choose the date");
        solo.setDatePicker(0, 2012, 2, 16);
        solo.clickOnText("OK");
        //solo.clickOnButton("Choose the time");
        solo.clickOnText("Choose the time");
        solo.setTimePicker(0, 10, 0);
        solo.clickOnText("OK");
        //solo.clickOnButton("OK");
        Spinner spinner2 = (Spinner) solo.getView(R.id.add_social_situation_spinner);
        spinner2.setSelection(1, true);
        solo.enterText((EditText) solo.getView(R.id.add_textual_reason_edit),"filter");
        solo.clickOnButton(("Finish your diary"));
        solo.assertCurrentActivity("Wong Activity", MainActivity.class);
        solo.waitForText("2012/03/06", 1, 2000);}

    public void testFilter(){
        solo.assertCurrentActivity("Wong Activity", MainActivity.class);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
        solo.sendKey(KeyEvent.KEYCODE_DPAD_CENTER);
        solo.clickInList(0);
        solo.clickOnText("Yes");
        solo.waitForText("filter", 1, 2000);
    }
    public void delete(){
        solo.assertCurrentActivity("Wong Activity", MainActivity.class);

        solo.clickLongInList(1);
        solo.clickOnButton("Yes");
    }

    /**
     * Test for happy filter
     */
    @Test
    public void test(){
        Add();
        testFilter();
        delete();
    }
}
