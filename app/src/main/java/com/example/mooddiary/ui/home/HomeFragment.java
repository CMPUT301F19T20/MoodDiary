package com.example.mooddiary.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mooddiary.MainActivity;
import com.example.mooddiary.Mood;
import com.example.mooddiary.MoodAdapter;
import com.example.mooddiary.MoodEvent;
import com.example.mooddiary.MoodList;
import com.example.mooddiary.R;
import com.example.mooddiary.ViewActivity;

import java.io.ByteArrayOutputStream;


/**
 * This is Home fragment that shows a list of user's mood event
 */
public class HomeFragment extends Fragment {
    private final int VIEW_EDIT_REQUEST = 0;

    private HomeViewModel homeViewModel;
    private MoodList myMoodList = new MoodList();
    private ListView myMoodEventListView;
    private MoodAdapter moodAdapter;
    private int deleteMoodEventIndex;
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
                Intent i = new Intent(getActivity(), ViewActivity.class);
                i.putExtra("moodevent",(MoodEvent)myMoodEventListView.getItemAtPosition(position));
                startActivityForResult(i, VIEW_EDIT_REQUEST);
            }
        });

        myMoodEventListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                // deleteMoodEventIndex = i;
                final MoodEvent moodevent = (MoodEvent)myMoodEventListView.getItemAtPosition(i);
                Log.i("mood string",moodevent.getMood().getMood());
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Delete a mood");
                dialog.setMessage("Delete is unrecoverable. Are you sure?");
                dialog.setCancelable(false);
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myMoodList.delete(moodevent);
                        moodAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(),"Delete a mood",Toast.LENGTH_SHORT).show();;
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(),"Delete canceled",Toast.LENGTH_SHORT).show();;
                    }
                });
                dialog.show();
                return true;

            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

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
                new MoodEvent("happy", "Oct 24, 2019", "10:40", "alone", "", "", d);

        MoodEvent moodEvent2 =
                new MoodEvent("sad", "Oct 23, 2019", "11:40", "alone", "", "", d);

        MoodEvent moodEvent3 =
                new MoodEvent("meh", "Oct 25, 2019", "12:40", "alone", "", "", d);

        MoodEvent moodEvent4 =
                new MoodEvent("stressed", "Oct 22, 2019", "10:40", "alone", "", "", d);

        MoodEvent moodEvent5 =
                new MoodEvent("angry", "Oct 27, 2019", "10:40", "alone", "", "", d);

        MoodEvent moodEvent6 =
                new MoodEvent("content", "Oct 19, 2019", "10:40", "alone", "", "", d);

        myMoodList.add(moodEvent2);
        myMoodList.add(moodEvent1);
        myMoodList.add(moodEvent3);
        myMoodList.add(moodEvent4);
        myMoodList.add(moodEvent5);
        myMoodList.add(moodEvent6);
    }

}