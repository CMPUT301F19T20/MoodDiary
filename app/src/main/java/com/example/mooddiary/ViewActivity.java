package com.example.mooddiary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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
    private Button save;
    private Button camera;
    private Button album;
    private Uri imageUri;

    private static final int TAKE_PHOTO = 1;

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
        save = findViewById(R.id.save);
        camera = findViewById(R.id.camera);
        album = findViewById(R.id.album);

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
        photo.setImageBitmap(BitmapFactory.decodeByteArray(m.getPhoto(),0,m.getPhoto().length));

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File outputImage = new File(getExternalCacheDir(),"out_image.jpg");

                try{
                    if (outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e){
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24){
                    imageUri = FileProvider.getUriForFile(ViewActivity.this,"com.example.mooddiary.fileprovider",outputImage);
                }else{
                    imageUri = Uri.fromFile(outputImage);
                }


                // start camera

                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
            }
        });

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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editDate = date.getText().toString();
                String editTime = time.getText().toString();
                Mood editMood = new Mood(mood.getSelectedItem().toString());
                String editSocialSituation = socialSituation.getSelectedItem().toString();
                String editLocation = location.getText().toString();
                String editReason = reason.getText().toString();

                Bitmap bitmap = ((BitmapDrawable)photo.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] editPhoto = baos.toByteArray();




            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    try{
                        // show photo from camera
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        photo.setImageBitmap(bitmap);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
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
