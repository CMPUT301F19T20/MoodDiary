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
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddMoodEventActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener {
    private static final SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMddHHmmss");

    private Button addButton;
    private ImageButton cancelButton;
    private ImageButton photoFromCameraButton;
    private ImageButton photoFromAlbumButton;
    private TextView dateText;
    private TextView timeText;
    private Spinner moodSpinner;
    private Spinner socialSituationSpinner;
    private TextView locationText;
    private EditText reasonEdit;
    private ImageView photoImage;
    private Context mContext;
    private int moodNamePosition = -1 ; // if moodevent is null

    // record the social situation which is chosen from social situation spinner
    private String socialSituationSpinnerResult = "alone";
    private String moodSpinnerResult = "happy";
    private String dateResult = "";
    private String timeResult = "";
    private String locationResult = "";
    private String reasonResult = "";
    private String photoResult = "";

    private static final int TAKE_PHOTO = 1;
    private static final  int CHOOSE_PHOTO = 2;

    private Uri imageUri;



    private boolean two_selected = false;
    private ArrayList<MoodBean> mData = null;
    private MsAdapter mAdadpter = null;

    private boolean isFromView = false;
    private MoodEvent moodEventFromView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mood_event);

        // find all views
        addButton = findViewById(R.id.add_mood_event_button);
        cancelButton = findViewById(R.id.cancel_add_mood_event_button);
        dateText = findViewById(R.id.add_date_text);
        timeText = findViewById(R.id.add_time_text);
        moodSpinner = findViewById(R.id.add_mood_spinner);
        socialSituationSpinner = findViewById(R.id.add_social_situation_spinner);
        locationText = findViewById(R.id.add_location_edit);
        reasonEdit = findViewById(R.id.add_textual_reason_edit);
        photoImage = findViewById(R.id.add_image_reason);
        photoFromCameraButton = findViewById(R.id.add_photo_camera);
        photoFromAlbumButton = findViewById(R.id.add_photo_album);

        /*
          get intent from either Main Activity(Home Fragment) or View Activity
         */
        Intent intentFrom = getIntent();
        /*
          check where it comes from
         */
        isFromView = !intentFrom.getBooleanExtra("action_add", false);

        if (isFromView) {
            /*
              from View Activity
             */

            moodEventFromView = (MoodEvent) intentFrom.getExtras().getSerializable("mood_event_edit");

            // initialize return results in Add Activity
            dateResult = moodEventFromView.getDate();
            timeResult = moodEventFromView.getTime();
            moodSpinnerResult = moodEventFromView.getMood().getMood();
            socialSituationSpinnerResult = moodEventFromView.getSocialSituation();
            locationResult = moodEventFromView.getLocation();
            reasonResult = moodEventFromView.getReason();
            photoResult = moodEventFromView.getPhoto();

            // initialize views in Add Activity
            dateText.setText(moodEventFromView.getDate());
            timeText.setText(moodEventFromView.getTime());
            locationText.setText(moodEventFromView.getLocation());
            reasonEdit.setText(moodEventFromView.getReason());
            String socialSituation = moodEventFromView.getSocialSituation();
            Mood mood = moodEventFromView.getMood();
            if (!moodEventFromView.getPhoto().equals("")) {
                Bitmap bitmap = BitmapFactory.decodeFile(getExternalFilesDir("photo") + "/" + moodEventFromView.getPhoto());
                photoImage.setImageBitmap(bitmap);
            }

            String moodName = mood.getMood();
            switch (moodName) {
                case "happy":
                    moodNamePosition = 0;
                    break;
                case "angry":
                    moodNamePosition = 1;
                    break;
                case "content":
                    moodNamePosition = 2;
                    break;
                case "meh":
                    moodNamePosition = 3;
                    break;
                case "sad":
                    moodNamePosition = 4;
                    break;
                case "stressed":
                    moodNamePosition = 5;
                    break;
                default:
                    break;
            }
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.SocialSituation, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            socialSituationSpinner.setAdapter(adapter);
            if (socialSituation != null) {
                int spinnerPosition = adapter.getPosition(socialSituation);
                socialSituationSpinner.setSelection(spinnerPosition);
            }

        }


        // set Date and Time through time and date picker
        dateText.setOnClickListener(this);
        timeText.setOnClickListener(this);

        // choose photo from camera
        photoFromCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File outputImage = new File(getFilesDir(),"out_image.jpg");

//                try {
//                        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMddHHmmss");
//                        String filename = LoginActivity.userName + simpleDate.format(new Date()) + ".png";
//                        outputImage = new File(getFilesDir().toString() + "/" +filename);
//                        outputImage.createNewFile();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

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

        // choose photo from album
        photoFromAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(AddMoodEventActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(AddMoodEventActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else {
                    openAlbum();
                }
            }
        });



        socialSituationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] socialsituation = getResources().getStringArray(R.array.SocialSituation);
                Toast.makeText(AddMoodEventActivity.this, "your choice is :"+socialsituation[pos], Toast.LENGTH_SHORT).show();
                socialSituationSpinnerResult = socialsituation[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        // initialize mood spinner
        mContext = AddMoodEventActivity.this;
        mData = new ArrayList<MoodBean>();
        bindViews();
        if (moodNamePosition != -1) {
            moodSpinner.setSelection(moodNamePosition);
        }

        // finish edit/add mood event
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reasonResult = reasonEdit.getText().toString();
                locationResult = locationText.getText().toString();


                String [] checkNumberOfReasonWords = reasonResult.split(" ");
                if (checkNumberOfReasonWords.length > 3 || reasonResult.length() > 20) {
                    Toast.makeText(AddMoodEventActivity.this,"reason no more than 20 characters or 3 words",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (dateResult.equals("") || timeResult.equals("")) {
                    Toast.makeText(AddMoodEventActivity.this,"fields marked by * are required",Toast.LENGTH_SHORT).show();
                    return;
                }
                MoodEvent moodEventResult =
                        new MoodEvent(moodSpinnerResult, dateResult, timeResult, socialSituationSpinnerResult, locationResult, reasonResult, photoResult);
                if (isFromView) {
                    Intent intent = new Intent();
                    intent.putExtra("edited_mood_event", moodEventResult);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("added_mood_event", moodEventResult);
                    setResult(RESULT_OK, intent);
                    finish();
                }


            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /*
      functions deal with photo part
     */
    // function used in choose photo from album
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
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
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //Bitmap bitmap = data.getExtras().getParcelable("data");
                    //photoResult = imageUri.getPath();
                    photoImage.setImageURI(imageUri);
                    Bitmap bitmap = ((BitmapDrawable)photoImage.getDrawable()).getBitmap();

                    try {
                        photoResult = LoginActivity.userName + "_" + simpleDate.format(new Date()) + ".png";
                        File addPhoto = new File(getExternalFilesDir("photo").toString() + "/" + photoResult);
                        FileOutputStream out = new FileOutputStream(addPhoto);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                        out.flush();
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);

                    } else {
                        handleImageBeforeKitKat(data);

                    }
                }
                break;
            default:
                break;
        }
    }

    // This is used for date and time picker
    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.add_date_text){

            Calendar calendar=Calendar.getInstance();
            DatePickerDialog dialog =
                    new DatePickerDialog(this,this,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }
        if (view.getId()==R.id.add_time_text){
            Calendar calendar=Calendar.getInstance();
            TimePickerDialog dialog =
                    new TimePickerDialog(this,this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            dialog.show();
        }
    }


    @Override
    /**
     * This displays the data from date picker
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
        String desc=String.format("%04d/%02d/%02d",year,month+1,dayOfMonth);
        dateText.setText(desc);
        dateResult = desc;
    }
    /**
     * This displays the data from time picker
     * @param timePicker
     *      This is the date from time picker
     * @param hourOfDay
     *      This is the hour from time picker
     * @param minute
     *      This is the minute from time picker
     */
    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        String desc=String.format("%02d:%02d",hourOfDay,minute);
        timeText.setText(desc);
        timeResult = desc;
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
                    openAlbum();
                }else {
                    Toast.makeText(AddMoodEventActivity.this,"YOU DENIED THE PERMISSION",Toast.LENGTH_SHORT).show();
                }
                break;
            default:

        }
    }
    /**
     * This displays the image
     * @param imagePath
     *      This is the image path from album
     */

    private void displayImage(String imagePath){
        if(imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            photoImage.setImageBitmap(bitmap);
            photoResult = LoginActivity.userName + "_" + simpleDate.format(new Date()) + ".png";
            File editPhoto = new File(getExternalFilesDir("photo").toString() + "/" + photoResult);
            try {
                FileOutputStream out = new FileOutputStream(editPhoto);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
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

    /**s
     * handleImageOnKitKat for version lower than 4.0
     * @param data
     *
     */
    private void handleImageBeforeKitKat(Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);

    }

    private String getImagePath(Uri uri, String selection){
        String path = null;
        // get image path through Uri and selection

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
        moodSpinner  = findViewById(R.id.add_mood_spinner);

        mData.add(new MoodBean(R.drawable.happy,"happy"));
        mData.add(new MoodBean(R.drawable.angry,"angry"));
        mData.add(new MoodBean(R.drawable.content,"content"));
        mData.add(new MoodBean(R.drawable.meh,"meh"));
        mData.add(new MoodBean(R.drawable.sad,"sad"));
        mData.add(new MoodBean(R.drawable.stressed,"stressed"));

        mAdadpter = new MsAdapter<MoodBean>(mData,R.layout.spinner_item) {
            @Override
            public void bindView(MsAdapter.ViewHolder holder, MoodBean obj) {
                holder.setImageResource(R.id.icon,obj.getIcon());
                holder.setText(R.id.name, obj.getName());
            }
        };
        moodSpinner.setAdapter(mAdadpter);
        moodSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){

            case R.id.add_mood_spinner:
                if(two_selected){
                    TextView txt_name = view.findViewById(R.id.name);
                    moodSpinnerResult = txt_name.getText().toString();

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
