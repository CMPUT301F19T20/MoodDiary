package com.example.mooddiary;

import android.app.Activity;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.mooddiary.ui.home.HomeViewModel;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
/**
 * Test class for SignUpActivity. All the UI tests are written here.Robotium test framework is used
 */
public class TestMainActivity {
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
     * Check add in MainActivity
     * Click add button then asserting the AddMoodEventActivity
     * Set date and time then  enter information for new mood
     * click Finish button
     * waiting the new mood in MainActivity
     */

    public void checkToMianToAdd() {
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
        solo.enterText((EditText) solo.getView(R.id.add_textual_reason_edit),"no thing");
        solo.clickOnButton(("Finish your diary"));
        solo.assertCurrentActivity("Wong Activity", MainActivity.class);
        solo.waitForText("2012/03/06", 1, 2000);





    }

    /**
     * Check Edit in MainActivity
     * Click one Mood in MainActivity
     * Change some information in this mood
     * Wait for new information in MainActivity
     */

    public void checkEdit(){

        solo.assertCurrentActivity("Wong Activity", MainActivity.class);
        solo.clickInList(1);
        solo.assertCurrentActivity("Wong Activity", ViewActivity.class);
        solo.clickOnButton("Click to edit");
        solo.assertCurrentActivity("Wong Activity", AddMoodEventActivity.class);
        Spinner spinner = (Spinner) solo.getView(R.id.add_mood_spinner);
        spinner.setSelection(3, true);
        //solo.clickOnButton("Choose the data");
        solo.clickOnText("2012/03/16");
        solo.setDatePicker(0, 2012, 3, 16);
        solo.clickOnText("OK");
        //solo.clickOnButton("Choose the time");
        solo.clickOnText("10:00");
        solo.setTimePicker(0, 11, 0);
        solo.clickOnText("OK");
        //solo.clickOnButton("OK");
        Spinner spinner2 = (Spinner) solo.getView(R.id.add_social_situation_spinner);
        spinner2.setSelection(2, true);
        //solo.enterText((EditText) solo.getView(R.id.add_textual_reason_edit),"no thing");
        solo.clickOnButton(("Finish your diary"));
        solo.assertCurrentActivity("Wong Activity", ViewActivity.class);
        solo.goBack();
        solo.assertCurrentActivity("Wong Activity", MainActivity.class);
        solo.waitForText("2012/04/06", 1, 2000);


    }

    /**
     * Check Delete in MainActivity
     * Long click in one mood in MainActivity
     * Search for the no exit text
     */

    public void checkDelete() {

        solo.assertCurrentActivity("Wong Activity", MainActivity.class);

        solo.clickLongInList(1);
        solo.clickOnButton("Yes");
        assertFalse(solo.searchText("2012/04/06"));


    }

    /**
     * This is a test in order to run test in a order
     */
    @Test
    public void test(){
        checkToMianToAdd();
        checkEdit();
        checkDelete();
}}
