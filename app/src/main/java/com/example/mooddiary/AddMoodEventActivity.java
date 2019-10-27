package com.example.mooddiary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.ArrayList;
import java.util.Calendar;

public class AddMoodEventActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener {
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
    private Context mContext;

    // record the social situation which is chosen from social situation spinner
    private String SocialSituationSpinnerResult;
    private String MoodSpinnerResult;

    private static final int TAKE_PHOTO = 1;
    private static final  int CHOOSE_PHOTO = 2;

    private Uri imageUri;




    private boolean two_selected = false;
    private ArrayList<MoodBean> mData = null;
    private MsAdapter mAdadpter = null;


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
                Log.d("lty", " open camera");
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
            }
        });

        PhotoFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddMoodEventActivity.this, "222", Toast.LENGTH_LONG).show();
                if(ContextCompat.checkSelfPermission(AddMoodEventActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(AddMoodEventActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else {
                    openAlbum();
                }
            }
        });



        SocialSituationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] socialsituation = getResources().getStringArray(R.array.socialSituation);
                Toast.makeText(AddMoodEventActivity.this, "your choice is :"+socialsituation[pos], Toast.LENGTH_SHORT).show();
                SocialSituationSpinnerResult = socialsituation[pos];
//                Location.setText(SocialSituationSpinnerResult);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });



        mContext = AddMoodEventActivity.this;
        mData = new ArrayList<MoodBean>();

        bindViews();


    }
    private void openAlbum(){
        Log.d("lty", " open album");
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }
    /**
     * get the result of intent
     * @param requestCode
     *      request code from intent
     * @param resultCode
     *      result code from intent
     * @param data
     *      data is image

     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("lty", " acquire intent result code");
        switch (requestCode){
            case TAKE_PHOTO:
                Log.d("lty", " acquire intent result code from camera");
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
            case CHOOSE_PHOTO:
                if(resultCode == RESULT_OK){
                    Log.d("lty", " acquire intent result code from choose photo intent");
                    if(Build.VERSION.SDK_INT >= 19){
                        Log.d("lty", " version > 19");
                        handleImageOnKitKat(data);

                    }else{
                        Log.d("lty", " version < 19");
                        handleImageBeforeKitKat(data);

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
    /**
     * dispaly the data from date picker
     * @param datePicker
     *      get the data from date picker
     * @param year
     *      get the year from date picker
     * @param month
     *      get the month from date picker
     * @param dayOfMonth
     *      get the day from date picker
     */
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        String desc=String.format("%d/%d/%d",year,month+1,dayOfMonth);
        Date.setText(desc);
    }
    /**
     * dispaly the data from date picker
     * @param timePicker
     *      get the date from time picker
     * @param hourOfDay
     *      get the hour from date picker
     * @param minute
     *      get the minute from date picker
     */
    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        String desc=String.format("%02d:%02d",hourOfDay,minute);
        Time.setText(desc);
    }
    /**
     * get require permission from system to deal with opening album
     * @param requestCode
     *
     * @param permissions
     *
     * @param grantResults
     *
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(AddMoodEventActivity.this,"9999",Toast.LENGTH_SHORT).show();
                    openAlbum();
                }else {
                    Toast.makeText(AddMoodEventActivity.this,"YOU DENIED THE PERMISSION",Toast.LENGTH_SHORT).show();
                }
                break;
            default:

        }
    }
    /**
     * to display the image
     * @param imagePath
     *
     * the image path from album
     *
     *
     */

    private void displayImage(String imagePath){
        if(imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            Image.setImageBitmap(bitmap);
        }else {
            Toast.makeText(this,"failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * handleImageOnKitKat
     * @param data
     *
     */

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("lty", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {

            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {

            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {

            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    /**
     * handleImageOnKitKat for version lower than 4.0
     * @param data
     *
     */
    private void handleImageBeforeKitKat(Intent data){
        Log.d("lty", " at beforekat");
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);

    }

    private String getImagePath(Uri uri, String selection){
        String path = null;
        // get image path throuth Uri and selection

        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if(cursor !=null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


    private void bindViews() {
        MoodSpinner  = findViewById(R.id.add_mood_spinner);

        mData.add(new MoodBean(R.drawable.angry,"angry"));
        mData.add(new MoodBean(R.drawable.content,"content"));
        mData.add(new MoodBean(R.drawable.meh,"meh"));
        mData.add(new MoodBean(R.drawable.happy,"happy"));
        mData.add(new MoodBean(R.drawable.sad,"sad"));
        mData.add(new MoodBean(R.drawable.stressed,"stressed"));

        mAdadpter = new MsAdapter<MoodBean>(mData,R.layout.spinner_item) {
            @Override
            public void bindView(MsAdapter.ViewHolder holder, MoodBean obj) {
                holder.setImageResource(R.id.icon,obj.getIcon());
                holder.setText(R.id.name, obj.getName());
            }
        };
        MoodSpinner.setAdapter(mAdadpter);
        MoodSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){

            case R.id.add_mood_spinner:
                if(two_selected){
                    TextView txt_name = view.findViewById(R.id.name);
                    Toast.makeText(mContext,"choose a mood：" + txt_name.getText().toString(),
                            Toast.LENGTH_SHORT).show();
                    MoodSpinnerResult = txt_name.getText().toString();

                }else
                    two_selected = true;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
