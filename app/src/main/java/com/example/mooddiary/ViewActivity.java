package com.example.mooddiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

        Intent i = getIntent();
        MoodEvent m = (MoodEvent)i.getSerializableExtra("moodevent");
        date.setText(m.getDate());
        time.setText(m.getTime());
        setSpinner(mood, m.getMood().getMood());
        moodIcon.setImageResource(m.getMood().getMoodImage());
        setSpinner(socialSituation, m.getSocialSituation());
        location.setText(m.getLocation());
        reason.setText(m.getReason());

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
}
