package com.example.mooddiary;

import android.widget.EditText;
import android.widget.Spinner;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class TestMainActivity {
    private Solo solo;
    @Rule
    public ActivityTestRule<LoginActivity> rule = new ActivityTestRule<>(LoginActivity.class, true, true);


    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
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
        solo.waitForText("no thing", 1, 2000);
        solo.assertCurrentActivity("Wong Activity", MainActivity.class);




    }
    @Test
    public void checkDelete() {
        
    }

}
