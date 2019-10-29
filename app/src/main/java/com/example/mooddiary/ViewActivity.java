package com.example.mooddiary;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ViewActivity extends AppCompatActivity {

    private final int VIEW_TO_ADD_EDIT_REQUEST = 5;

    private TextView viewTimeText;

    private TextView viewDateText;

    private TextView viewReasonText;

    private TextView viewLocationText;

    private TextView viewMoodTypeText;

    private ImageView viewMoodImage;

    private ImageView viewPhotoImage;

    private Button viewEditButton;

    private MoodEvent moodEvent;

    private MoodEvent editedMoodEvent;

    private boolean ifEdited = false;

    /**
     * This creates the view of details of a mood event.
     * @param savedInstanceState
     *      If the activity is being re-initialized after previously being shut down
     *      then this Bundle contains the data it most recently supplied in.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        viewDateText = (TextView) findViewById(R.id.view_date_text);
        viewTimeText = (TextView) findViewById(R.id.view_time_text);
        viewLocationText = (TextView) findViewById(R.id.view_location_text);
        viewMoodTypeText = (TextView) findViewById(R.id.view_mood_type_text);
        viewReasonText = (TextView) findViewById(R.id.view_reason_text);
        viewMoodImage = (ImageView) findViewById(R.id.view_mood_image);
        viewPhotoImage = (ImageView) findViewById(R.id.view_photo_image);
        viewEditButton = (Button) findViewById(R.id.view_edit_button);

        Intent intent = getIntent();

        moodEvent = (MoodEvent) intent.getSerializableExtra("moodEvent");

        viewDateText.setText(moodEvent.getDate());
        viewTimeText.setText(moodEvent.getTime());
        viewReasonText.setText((moodEvent.getReason()));
        viewMoodTypeText.setText(moodEvent.getMood().getMood());
        viewLocationText.setText(moodEvent.getLocation());
        viewMoodImage.setImageResource(moodEvent.getMood().getMoodImage());

        viewEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_edit = new Intent(ViewActivity.this, AddMoodEventActivity.class);
                intent_edit.putExtra("mood_event_edit", moodEvent);
                startActivityForResult(intent_edit, VIEW_TO_ADD_EDIT_REQUEST);
            }
        });

    }

    /**
     * This deals with the data requested from other activities.
     * @param requestCode
     *      This is originally supplied to startActivityForResult(), allowing to identify who this result came from.
     * @param resultCode
     *      This is returned by the child activity through its setResult().
     * @param data
     *       This is an intent returning result data.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case VIEW_TO_ADD_EDIT_REQUEST:
                if (resultCode == RESULT_OK) {
                    ifEdited = true;
                    editedMoodEvent = (MoodEvent) data.getSerializableExtra("edited_mood_event");
                    viewDateText.setText(moodEvent.getDate());
                    viewTimeText.setText(moodEvent.getTime());
                    viewReasonText.setText((moodEvent.getReason()));
                    viewMoodTypeText.setText(moodEvent.getMood().getMood());
                    viewLocationText.setText(moodEvent.getLocation());
                    viewMoodImage.setImageResource(moodEvent.getMood().getMoodImage());
                }
                break;
            default:
        }
    }

    /**
     * This sends a boolean indicating if edited, original mood event and new mood event
     * back to Home Fragment.
     */
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("if_edited", ifEdited);
        intent.putExtra("original_mood_event", moodEvent);
        intent.putExtra("edited_mood_event_return", editedMoodEvent);
        setResult(RESULT_OK, intent);
        finish();
    }
}
