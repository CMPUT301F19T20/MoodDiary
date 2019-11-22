package com.example.mooddiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class ViewFriendActivity extends AppCompatActivity {

    private TextView viewfriendDateText;
    private TextView viewfriendTimeText;
    private TextView viewfriendReasonText;
    private TextView viewfriendLocationText;
    private TextView viewfriendMoodTypeText;
    private ImageView viewfriendMoodTypeImage;
    private TextView viewfriendSocialSituationText;
    private ImageView viewfriendPhotoImage;
    private MoodEvent moodEvent;
    private ProgressBar viewfriendDownloadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_friend);

        viewfriendDateText = findViewById(R.id.view_friend_date_text);
        viewfriendTimeText = findViewById(R.id.view_friend_time_text);
        viewfriendReasonText = findViewById(R.id.view_friend_reason_text);
        viewfriendLocationText = findViewById(R.id.view_friend_location_text);
        viewfriendMoodTypeText = findViewById(R.id.view_friend_mood_type_text);
        viewfriendMoodTypeImage = findViewById(R.id.view_friend_mood_type_image);
        viewfriendSocialSituationText = findViewById(R.id.view_friend_social_situation_text);
        viewfriendPhotoImage = findViewById(R.id.view_friend_photo_image);
        viewfriendDownloadingProgress = findViewById(R.id.view_friend_downloading_progress);

        Intent intent = getIntent();
        moodEvent = (MoodEvent) intent.getSerializableExtra("moodEvent");

        viewfriendDateText.setText(moodEvent.getDate());
        viewfriendTimeText.setText(moodEvent.getTime());
        viewfriendReasonText.setText((moodEvent.getReason()));
        viewfriendMoodTypeText.setText(moodEvent.getMood().getMood());
        viewfriendMoodTypeImage.setImageResource(moodEvent.getMood().getMoodImage());
        viewfriendMoodTypeText.setTextColor(Color.parseColor(moodEvent.getMood().getColor()));
        viewfriendLocationText.setText(moodEvent.getLocation());
        viewfriendSocialSituationText.setText(moodEvent.getSocialSituation());


        if (!moodEvent.getPhoto().equals("")) {
            StorageReference imageRef = Database.storageRef.child(moodEvent.getPhoto());
            try{
                final File tempFile = File.createTempFile("tempPhoto","png");
                imageRef.getFile(tempFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        viewfriendDownloadingProgress.setVisibility(View.INVISIBLE);
                        Bitmap bitmap = BitmapFactory.decodeFile(tempFile.getAbsolutePath());
                        viewfriendPhotoImage.setImageBitmap(bitmap);
                    }
                });
            } catch (Exception e) {}
        } else {
            viewfriendDownloadingProgress.setVisibility(View.INVISIBLE);
        }

    }
}
