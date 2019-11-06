package com.example.mooddiary;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

    private ImageView viewMoodTypeImage;

    private TextView viewSocialSituationText;

    private ImageView viewPhotoImage;

    private Button viewEditButton;

    private ImageButton viewReturnButton;

    private MoodEvent moodEvent;

    private MoodEvent editedMoodEvent;

    private int position;

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
        viewSocialSituationText = (TextView) findViewById(R.id.view_social_situation_text);
        viewLocationText = (TextView) findViewById(R.id.view_location_text);
        viewMoodTypeText = (TextView) findViewById(R.id.view_mood_type_text);
        viewMoodTypeImage = (ImageView) findViewById(R.id.view_mood_type_image);
        viewReasonText = (TextView) findViewById(R.id.view_reason_text);
        viewPhotoImage = (ImageView) findViewById(R.id.view_photo_image);
        viewEditButton = (Button) findViewById(R.id.view_edit_button);
        viewReturnButton = (ImageButton) findViewById(R.id.view_return_button);

        Intent intent = getIntent();

        moodEvent = (MoodEvent) intent.getSerializableExtra("moodEvent");
        editedMoodEvent = (MoodEvent) intent.getSerializableExtra("moodEvent");
        position = intent.getIntExtra("moodEvent_index", 0);

        viewDateText.setText(editedMoodEvent.getDate());
        viewTimeText.setText(editedMoodEvent.getTime());
        viewReasonText.setText((editedMoodEvent.getReason()));
        viewMoodTypeText.setText(editedMoodEvent.getMood().getMood());
        viewMoodTypeImage.setImageResource(editedMoodEvent.getMood().getMoodImage());
        viewLocationText.setText(editedMoodEvent.getLocation());
        viewSocialSituationText.setText(editedMoodEvent.getSocialSituation());


        if (!editedMoodEvent.getPhoto().equals("")) {
            Bitmap bitmap = BitmapFactory.decodeFile(getExternalFilesDir("photo") + "/" + editedMoodEvent.getPhoto());
            viewPhotoImage.setImageBitmap(bitmap);
        }

        viewEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("intent", "click edit");
                Intent intent_edit = new Intent(ViewActivity.this, AddMoodEventActivity.class);
                intent_edit.putExtra("mood_event_edit", editedMoodEvent);
                //Log.d("intent", "put extra edit mood event");
                startActivityForResult(intent_edit, VIEW_TO_ADD_EDIT_REQUEST);
                //Log.d("intent", "start add success");
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
        Log.d("intent","enter view 1");
        switch (requestCode) {
            case VIEW_TO_ADD_EDIT_REQUEST:
                if (resultCode == RESULT_OK) {
                    ifEdited = true;
                    editedMoodEvent = (MoodEvent) data.getSerializableExtra("edited_mood_event");
                    viewDateText.setText(editedMoodEvent.getDate());
                    viewTimeText.setText(editedMoodEvent.getTime());
                    viewReasonText.setText((editedMoodEvent.getReason()));
                    viewMoodTypeText.setText(editedMoodEvent.getMood().getMood());
                    viewMoodTypeImage = (ImageView) findViewById(R.id.view_mood_type_image);
                    viewLocationText.setText(editedMoodEvent.getLocation());
                    viewSocialSituationText.setText(editedMoodEvent.getSocialSituation());
                    if (!editedMoodEvent.getPhoto().equals("")) {
                        Bitmap bitmap = BitmapFactory.decodeFile(getExternalFilesDir("photo") + "/" + editedMoodEvent.getPhoto());
                        viewPhotoImage.setImageBitmap(bitmap);
                    }
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
