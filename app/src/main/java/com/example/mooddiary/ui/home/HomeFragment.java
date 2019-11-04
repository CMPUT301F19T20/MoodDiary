package com.example.mooddiary.ui.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mooddiary.FilterAdapter;
import com.example.mooddiary.LoginActivity;
import com.example.mooddiary.MoodAdapter;
import com.example.mooddiary.MoodBean;
import com.example.mooddiary.MoodEvent;
import com.example.mooddiary.MoodList;
import com.example.mooddiary.R;
import com.example.mooddiary.User;
import com.example.mooddiary.ViewActivity;
import com.google.firebase.firestore.FieldValue;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


/**
 * This is Home fragment that shows a list of user's mood event
 */
public class HomeFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final int VIEW_EDIT_REQUEST = 0;
    private static final int HOME_TO_ADD_REQUEST = 10;
    public User user = new User(LoginActivity.userName);
    private HomeViewModel homeViewModel;

    public MoodList myMoodList = new MoodList();

    private ListView myMoodEventListView;
    private ListView homeFilterListView;
    private ImageButton homeFilterButton;
    private MoodAdapter moodAdapter;
    private FilterAdapter filterAdapter;
    private MoodBean moodBeanFiltered;
  
    private int waitFilterIndex = 0;
    private int currentFilterIndex = 0;
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
                ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
        View root =
                inflater.inflate(R.layout.fragment_home, container, false);
        myMoodEventListView = root.findViewById(R.id.my_mood_event_list_view);


        //initMoodList();
        /**final DocumentReference docRef1 = db.collection("users").document("chenge");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                System.out.println(documentSnapshot.toObject(User.class));
                //moodAdapter =
                        //new MoodAdapter(getActivity(), R.layout.mood_list_item, user1.getMoodList().getAllMoodList());
            }
        });

       /** DocumentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                user1 = snapshot.toObject(User.class);


                //moodAdapter = new MoodAdapter(getActivity(), R.layout.mood_list_item, user1.getMoodList().getAllMoodList());
            }



        });**/


        homeFilterButton = root.findViewById(R.id.home_filter_button);
        homeFilterListView = root.findViewById(R.id.home_filter_list_view);
        myMoodEventListView = root.findViewById(R.id.my_mood_event_list_view);
        moodAdapter =
                new MoodAdapter(getActivity(), R.layout.mood_list_item, homeViewModel.getMoodList().getMoodList("all"));

        myMoodEventListView.setAdapter(moodAdapter);

        myMoodEventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), ViewActivity.class);
                i.putExtra("moodEvent_index", position);
                i.putExtra("moodEvent",(MoodEvent)myMoodEventListView.getItemAtPosition(position));
                startActivityForResult(i, VIEW_EDIT_REQUEST);
            }
        });

        myMoodEventListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final MoodEvent deleteMood = (MoodEvent)myMoodEventListView.getItemAtPosition(i);
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Delete a mood");
                dialog.setMessage("Delete is unrecovrable. Are you sure?");
                dialog.setCancelable(false);
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        myMoodList.delete(deleteMood);
                        user.setMoodList(myMoodList);
                        db.collection("users").document(LoginActivity.userName).set(user);

                        homeViewModel.getMoodList().delete(deleteMood);

                        moodAdapter.notifyDataSetChanged();
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialog.show();
                return true;
            }
        });

        homeFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilter(requireActivity());
            }
        });




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
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case VIEW_EDIT_REQUEST:
                if (resultCode == RESULT_OK) {
                    boolean ifEdited = (boolean) data.getBooleanExtra("if_edited", false);
                    if (ifEdited) {

                        DocumentReference docRef = db.collection("users").document("users").collection(LoginActivity.userName).document("MoodList");
                        MoodEvent originalMoodEvent =
                                (MoodEvent) data.getSerializableExtra("original_mood_event");
                        MoodEvent editMoodEvent =
                                (MoodEvent) data.getSerializableExtra("edited_mood_event_return");
//                        int i = data.getIntExtra("mood_event_index_return", 0);
                        homeViewModel.getMoodList().edit(editMoodEvent, originalMoodEvent);
                        MoodList moodList = homeViewModel.getMoodList();
                        docRef.set(moodList);
                        Log.d("view", String.valueOf(homeViewModel.getMoodList().getMoodList("all").size()));
                        moodAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case HOME_TO_ADD_REQUEST:
                if (resultCode == RESULT_OK) {
                    DocumentReference docRef = db.collection("users").document("users").collection(LoginActivity.userName).document("MoodList");
                    MoodEvent moodEventAdded = (MoodEvent) data.getSerializableExtra("added_mood_event");
                    homeViewModel.getMoodList().add(moodEventAdded);
                    MoodList moodList = homeViewModel.getMoodList();
                    docRef.set(moodList);
                    Log.d("view", String.valueOf(homeViewModel.getMoodList().getMoodList("all").size()));
                    moodAdapter.notifyDataSetChanged();
                }
            default:
        }

    }

    /**
     * This creates a dialog to prompt for a mood to filter
     * @param activity
     *      This is the activity the dialog is attached to
     */

    private void showFilter(Activity activity) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setCancelable(false);
        dialog.setTitle("Choose a Mood");

        View view = LayoutInflater.from(activity).inflate(R.layout.fragment_filter, null);
        homeFilterListView = view.findViewById(R.id.home_filter_list_view);
        ArrayList<MoodBean> mData = new ArrayList<>();
        mData.add(new MoodBean(R.drawable.mood,"all"));
        mData.add(new MoodBean(R.drawable.happy,"happy"));
        mData.add(new MoodBean(R.drawable.angry,"angry"));
        mData.add(new MoodBean(R.drawable.content,"content"));
        mData.add(new MoodBean(R.drawable.meh,"meh"));
        mData.add(new MoodBean(R.drawable.sad,"sad"));
        mData.add(new MoodBean(R.drawable.stressed,"stressed"));
        filterAdapter= new FilterAdapter(activity, R.layout.filter_item, mData);
        homeFilterListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        homeFilterListView.setAdapter(filterAdapter);
        moodBeanFiltered = (MoodBean) homeFilterListView.getItemAtPosition(currentFilterIndex);
        filterAdapter.setSelectedItem(currentFilterIndex);
        homeFilterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                filterAdapter.setSelectedItem(i);
                moodBeanFiltered = (MoodBean) adapterView.getItemAtPosition(i);
                waitFilterIndex = i;
                filterAdapter.notifyDataSetChanged();

            }
        });
        dialog.setView(view)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        currentFilterIndex = waitFilterIndex;
                        onMoodSelected(moodBeanFiltered.getName());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

        dialog.show();
    }

    /**
     * This filters the mood history to show only one specific mood
     * @param mood
     *      This is the mood selected to show
     */
    private void onMoodSelected(String mood) {
        moodAdapter = new MoodAdapter(getActivity(), R.layout.mood_list_item, homeViewModel.getMoodList().getMoodList(mood));
        myMoodEventListView.setAdapter(moodAdapter);
//        switch (mood) {
//            case "no filter":
//                moodAdapter =
//                        new MoodAdapter(getActivity(), R.layout.mood_list_item, homeViewModel.getMoodList().getMoodList("all"));
//                myMoodEventListView.setAdapter(moodAdapter);
//                break;
//            case "happy" :
//                moodAdapter =
//                        new MoodAdapter(getActivity(), R.layout.mood_list_item, homeViewModel.getMoodList().getMoodList("happy"));
//                myMoodEventListView.setAdapter(moodAdapter);
//                break;
//            case "angry" :
//                moodAdapter =
//                        new MoodAdapter(getActivity(), R.layout.mood_list_item, homeViewModel.getMoodList().getMoodList("angry"));
//                myMoodEventListView.setAdapter(moodAdapter);
//                break;
//            case "sad" :
//                moodAdapter =
//                        new MoodAdapter(getActivity(), R.layout.mood_list_item, homeViewModel.getMoodList().getMoodList("sad"));
//                myMoodEventListView.setAdapter(moodAdapter);
//                break;
//            case "content" :
//                moodAdapter =
//                        new MoodAdapter(getActivity(), R.layout.mood_list_item, homeViewModel.getMoodList().getMoodList("content"));
//                myMoodEventListView.setAdapter(moodAdapter);
//                break;
//            case "stressed" :
//                moodAdapter =
//                        new MoodAdapter(getActivity(), R.layout.mood_list_item, homeViewModel.getMoodList().getMoodList("stress"));
//                myMoodEventListView.setAdapter(moodAdapter);
//                break;
//            case "meh" :
//                moodAdapter =
//                        new MoodAdapter(getActivity(), R.layout.mood_list_item, homeViewModel.getMoodList().getMoodList("meh"));
//                myMoodEventListView.setAdapter(moodAdapter);
//                break;
//            default :
//                throw new IllegalArgumentException();
//        }


    }

}