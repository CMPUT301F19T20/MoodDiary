package com.example.mooddiary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

public class AddMoodEventActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {
    private Button Add;
    private Button Cancel;
    private Button PhotoFromCamera;
    private Button PhotoFromAlbum;
    private TextView Date;
    private TextView Time;
    private Spinner MoodSpinner;
    private Spinner SocialSituationSpinner;
    private EditText Location;
    private EditText Reason;
    private ImageView Image;

    // Initial attributes for choosing photo;

    public static final int TAKE_PHOTO = 1;
    public static final  int CHOOSE_PHOTO = 2;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mood_event);
        // Initial attributes for choosing photo;




        // find all attributes
        Add = findViewById(R.id.add_mood_event_button);
        Cancel = findViewById(R.id.cancel_add_mood_event_button);
        Date = findViewById(R.id.add_date_text);
        Time = findViewById(R.id.add_time_text);
        MoodSpinner = findViewById(R.id.add_mood_spinner);
        SocialSituationSpinner = findViewById(R.id.add_social_situation_spinner);
        Location = findViewById(R.id.add_location_edit);
        Reason = findViewById(R.id.add_textual_reason_edit);
        Image = findViewById(R.id.add_image_reason);
        PhotoFromCamera = findViewById(R.id.add_photo_camera);
        PhotoFromAlbum = findViewById(R.id.add_photo_album);

        // set Date and Time through time and date picker
        Date.setOnClickListener(this);
        Time.setOnClickListener(this);
        // choose photo from camera
        PhotoFromCamera.setOnClickListener(new View.OnClickListener() {
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
                    imageUri = FileProvider.getUriForFile(AddMoodEventActivity.this,"com.example.mooddiary.fileprovider",outputImage);
                }else{
                    imageUri = Uri.fromFile(outputImage);
                }


                // start camera

                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
            }
        });

        PhotoFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(AddMoodEventActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(AddMoodEventActivity.this, new String[]{main});
                }
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
                        Image.setImageBitmap(bitmap);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.add_date_text){
            Toast.makeText(AddMoodEventActivity.this, "111", Toast.LENGTH_LONG).show();
            Calendar calendar=Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(this,this,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }
        if (view.getId()==R.id.add_time_text){
            Calendar calendar=Calendar.getInstance();
            TimePickerDialog dialog=new TimePickerDialog(this,this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            dialog.show();
        }


    }
    @Override

    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        String desc=String.format("%d/%d/%d",year,month+1,dayOfMonth);
        Date.setText(desc);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        String desc=String.format("%02d:%02d",hourOfDay,minute);
        Time.setText(desc);
    }
}
