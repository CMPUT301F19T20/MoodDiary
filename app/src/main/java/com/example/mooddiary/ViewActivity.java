package com.example.mooddiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;

public class ViewActivity extends AppCompatActivity {
    private EditText date;
    private EditText time;
    private Spinner mood;
    private ImageView moodIcon;
    private Spinner socialSituation;
    private EditText location;
    private EditText reason;
    private ImageView photo;
    private Switch edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        mood = findViewById(R.id.mood);
        moodIcon = findViewById(R.id.moodicon);
        socialSituation = findViewById(R.id.social);
        location = findViewById(R.id.location);
        reason = findViewById(R.id.reason);
        photo = findViewById(R.id.photo);
        edit = findViewById(R.id.Edit);

        nonFocusable();

        Intent i = getIntent();
        MoodEvent m = (MoodEvent)i.getSerializableExtra("moodevent");
        date.setText(m.getDate());
        time.setText(m.getTime());
        setSpinner(mood, m.getMood().getMood());
        moodIcon.setImageResource(m.getMood().getMoodImage());
        setSpinner(socialSituation, m.getSocialSituation());
        location.setText(m.getLocation());
        reason.setText(m.getReason());

        edit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    allFocusable();
                } else {
                    nonFocusable();
                }
            }
        });
    }

    public void setSpinner(Spinner s, String text) {
        SpinnerAdapter adapter = s.getAdapter();
        for(int i = 0; i < adapter.getCount(); i++) {
            String tempString = (String)adapter.getItem(i);
            if(tempString.compareTo(text) == 0) {
                s.setSelection(i);
            }
        }
    }

    public void allFocusable() {
        date.setFocusableInTouchMode(true);
        time.setFocusableInTouchMode(true);
        mood.setFocusableInTouchMode(true);
        socialSituation.setFocusableInTouchMode(true);
        location.setFocusableInTouchMode(true);
        reason.setFocusableInTouchMode(true);
        photo.setFocusableInTouchMode(true);
    }

    public void nonFocusable() {
        date.setFocusable(false);
        time.setFocusable(false);
        mood.setFocusable(false);
        socialSituation.setFocusable(false);
        location.setFocusable(false);
        reason.setFocusable(false);
        photo.setFocusable(false);
    }
}
