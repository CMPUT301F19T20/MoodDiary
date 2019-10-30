package com.example.mooddiary.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mooddiary.MoodAdapter;
import com.example.mooddiary.MoodEvent;
import com.example.mooddiary.MoodList;
import com.example.mooddiary.R;
import com.example.mooddiary.ViewActivity;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;


/**
 * This is Home fragment that shows a list of user's mood event
 */
public class HomeFragment extends Fragment {
    private static final int VIEW_EDIT_REQUEST = 0;
    private static final int HOME_TO_ADD_REQUEST = 10;

    private HomeViewModel homeViewModel;
    private MoodList myMoodList = new MoodList();
    private ListView myMoodEventListView;
    private MoodAdapter moodAdapter;
    private boolean actionAddReturn;

    /**
     * This creates the view for the list of user's mood events.
     * @param inflater
     *      This is a LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container
     *      This can be null. If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState
     *      If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return
     *      Return the view for the fragment UI
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root =
                inflater.inflate(R.layout.fragment_home, container, false);

        initMoodList();

        moodAdapter =
                new MoodAdapter(getActivity(), R.layout.mood_list_item, myMoodList.getAllMoodList());

        myMoodEventListView = root.findViewById(R.id.my_mood_event_list_view);

        myMoodEventListView.setAdapter(moodAdapter);

        myMoodEventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("add_test", String.valueOf(position));
                Intent i = new Intent(getActivity(), ViewActivity.class);
                i.putExtra("moodEvent_index", position);
                i.putExtra("moodEvent",(MoodEvent)myMoodEventListView.getItemAtPosition(position));
                startActivityForResult(i, VIEW_EDIT_REQUEST);
            }
        });

        myMoodEventListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                MoodEvent deleteMood = (MoodEvent)myMoodEventListView.getItemAtPosition(i);
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Delete a mood");
                dialog.setMessage("Delete is unrecovrable. Are you sure?");
                dialog.setCancelable(false);
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myMoodList.delete(deleteMood);
                        moodAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(),"Deleted a mood",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(),"Deleted canceled",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
                return true;
            }
        });

//        Intent intent = getActivity().getIntent();
//        actionAddReturn = intent.getBooleanExtra("action_add_return", false);
//        if (actionAddReturn) {
//            MoodEvent moodEventAdded = (MoodEvent) intent.getSerializableExtra("added_mood_event");
//            myMoodList.add(moodEventAdded);
//            Log.d("add_test", "added");
//            moodAdapter.notifyDataSetChanged();
//            Log.d("add_test", "length:");
//            Log.d("add_test", String.valueOf(myMoodList.getAllListLength()));
//        }

        return root;
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("add_test", "enter fragment activity result");
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case VIEW_EDIT_REQUEST:
                if (resultCode == RESULT_OK) {
                    boolean ifEdited = (boolean) data.getBooleanExtra("if_edited", false);
                    if (ifEdited) {
                        MoodEvent originalMoodEvent =
                                (MoodEvent) data.getSerializableExtra("original_mood_event");
                        MoodEvent editMoodEvent =
                                (MoodEvent) data.getSerializableExtra("edited_mood_event_return");
//                        int i = data.getIntExtra("mood_event_index_return", 0);
                        myMoodList.edit(editMoodEvent, originalMoodEvent);
//                        myMoodList.setMoodEventWithIndex(i, editMoodEvent);
                        moodAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case HOME_TO_ADD_REQUEST:
                if (resultCode == RESULT_OK) {
                    MoodEvent moodEventAdded = (MoodEvent) data.getSerializableExtra("added_mood_event");
                    myMoodList.add(moodEventAdded);
                    moodAdapter.notifyDataSetChanged();
                }
            default:
        }

    }

    /**
     * This creates a list of mood events for testing.
     * Not required for the project.
     * This may be deleted later.
     */
    private void initMoodList() {
        Drawable drawable = getResources().getDrawable(R.drawable.angry);
        Bitmap bitmap= ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] d = baos.toByteArray();
        MoodEvent moodEvent1 =
                new MoodEvent("happy", "2019/10/27", "10:40", "with a crowd", "", "l", d);

        MoodEvent moodEvent2 =
                new MoodEvent("sad", "2019/10/23", "11:40", "alone", "", "love", d);

        MoodEvent moodEvent3 =
                new MoodEvent("meh", "2019/10/25", "12:40", "alone", "", "", d);

        MoodEvent moodEvent4 =
                new MoodEvent("stressed", "2019/10/22", "10:40", "alone", "", "", d);

        MoodEvent moodEvent5 =
                new MoodEvent("angry", "2019/10/21", "10:40", "alone", "", "", d);

        MoodEvent moodEvent6 =
                new MoodEvent("content", "2019/10/19", "10:40", "alone", "", "", d);

        myMoodList.add(moodEvent2);
        myMoodList.add(moodEvent1);
        myMoodList.add(moodEvent3);
//        myMoodList.add(moodEvent4);
//        myMoodList.add(moodEvent5);
//        myMoodList.add(moodEvent6);
    }

}