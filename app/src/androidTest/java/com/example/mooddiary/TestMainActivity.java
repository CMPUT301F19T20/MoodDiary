package com.example.mooddiary;

import android.view.View;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class TestMainActivity {
    private Solo solo;
    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, true, true);


    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }


    @Test
    public void ToAddActivity(){
        //Fragment fragment = solo.getCurrentActivity().getFragmentManager().findFragmentById()
       // solo.assertCurrentActivity("Wong Activity", MainActivity.class);
        View fab = rule.getActivity().findViewById(R.id.fab);
        solo.clickOnView(fab);
        solo.assertCurrentActivity("Wong Activity", AddMoodEventActivity.class);

    }

}
