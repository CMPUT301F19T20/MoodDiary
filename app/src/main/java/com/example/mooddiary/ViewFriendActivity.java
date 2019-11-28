package com.example.mooddiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;
import java.io.File;

public class ViewFriendActivity extends AppCompatActivity {

    private TextView viewFriendDateText;
    private TextView viewFriendTimeText;
    private TextView viewFriendReasonText;
    private TextView viewFriendLocationText;
    private TextView viewFriendMoodTypeText;
    private ImageView viewFriendMoodTypeImage;
    private TextView viewFriendSocialSituationText;
    private ImageView viewFriendPhotoImage;
    private MoodEvent moodEvent;
    private ProgressBar viewFriendDownloadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_friend);

        viewFriendDateText = findViewById(R.id.view_friend_date_text);
        viewFriendTimeText = findViewById(R.id.view_friend_time_text);
        viewFriendReasonText = findViewById(R.id.view_friend_reason_text);
        viewFriendLocationText = findViewById(R.id.view_friend_location_text);
        viewFriendMoodTypeText = findViewById(R.id.view_friend_mood_type_text);
        viewFriendMoodTypeImage = findViewById(R.id.view_friend_mood_type_image);
        viewFriendSocialSituationText = findViewById(R.id.view_friend_social_situation_text);
        viewFriendPhotoImage = findViewById(R.id.view_friend_photo_image);
        viewFriendDownloadingProgress = findViewById(R.id.view_friend_downloading_progress);

        Intent intent = getIntent();
        moodEvent = (MoodEvent) intent.getSerializableExtra("moodEvent");

        viewFriendDateText.setText(moodEvent.getDate());
        viewFriendTimeText.setText(moodEvent.getTime());
        viewFriendReasonText.setText((moodEvent.getReason()));
        viewFriendMoodTypeText.setText(moodEvent.getMood().getMood());
        viewFriendMoodTypeImage.setImageResource(moodEvent.getMood().getMoodImage());
        viewFriendMoodTypeText.setTextColor(Color.parseColor(moodEvent.getMood().getColor()));
        viewFriendLocationText.setText(moodEvent.getLocation());
        viewFriendSocialSituationText.setText(moodEvent.getSocialSituation());


        if (!moodEvent.getPhoto().equals("")) {
            StorageReference imageRef = Database.storageRef.child(moodEvent.getPhoto());
            try{
                final File tempFile = File.createTempFile("tempPhoto","png");
                imageRef.getFile(tempFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        viewFriendDownloadingProgress.setVisibility(View.INVISIBLE);
                        Bitmap bitmap = BitmapFactory.decodeFile(tempFile.getAbsolutePath());
                        viewFriendPhotoImage.setImageBitmap(bitmap);
                    }
                });
            } catch (Exception e) {}
        } else {
            viewFriendDownloadingProgress.setVisibility(View.INVISIBLE);
        }

    }
}
