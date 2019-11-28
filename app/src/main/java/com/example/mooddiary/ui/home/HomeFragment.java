package com.example.mooddiary.ui.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.core.Controller;
import com.app.hubert.guide.listener.OnGuideChangedListener;
import com.app.hubert.guide.listener.OnLayoutInflatedListener;
import com.app.hubert.guide.listener.OnPageChangedListener;
import com.app.hubert.guide.model.GuidePage;
import com.example.mooddiary.AddMoodEventActivity;
import com.example.mooddiary.Database;
import com.example.mooddiary.FilterAdapter;
import com.example.mooddiary.LoginActivity;
import com.example.mooddiary.MoodAdapter;
import com.example.mooddiary.MoodBean;
import com.example.mooddiary.MoodEvent;
import com.example.mooddiary.MoodList;
import com.example.mooddiary.R;
import com.example.mooddiary.ViewActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


/**
 * This is Home fragment that shows a list of user's mood event
 */
public class HomeFragment extends Fragment {
    private static final int VIEW_EDIT_REQUEST = 0;
    private static final int HOME_TO_ADD_REQUEST = 10;
    private HomeViewModel homeViewModel;

    private ListView myMoodEventListView;
    private ListView homeFilterListView;
    private Button homeFilterClearAllButton;
    private Button homeFilterSelectAllButton;
    private ImageButton homeFilterButton;
    private MoodAdapter moodAdapter;
    private FilterAdapter filterAdapter;
    private ArrayList<String> selectedMoodType = new ArrayList<String>();

    public static final String TAG = HomeFragment.class.getSimpleName();



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

        homeViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        RectF rectF = new RectF(35,70,200,235);
        myMoodEventListView = root.findViewById(R.id.my_mood_event_list_view);
        myMoodEventListView.setEmptyView(root.findViewById(R.id.empty_view));

        homeFilterButton = root.findViewById(R.id.home_filter_button);
        homeFilterListView = root.findViewById(R.id.home_filter_list_view);
        myMoodEventListView = root.findViewById(R.id.my_mood_event_list_view);

        moodAdapter = new MoodAdapter(getActivity(), R.layout.mood_list_item,
                homeViewModel.getMoodList().getMoodList("all"));
        myMoodEventListView.setAdapter(moodAdapter);

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddMoodEventActivity.class);
                intent.putExtra("action_add", true);
                startActivityForResult(intent, HOME_TO_ADD_REQUEST);
            }
        });

        DocumentReference docRef = Database.getUserMoodList(LoginActivity.userName);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                MoodList moodList = documentSnapshot.toObject(MoodList.class);
                homeViewModel.getMoodList().clearMoodList();
                for (MoodEvent moodEvent: moodList.getAllMoodList()) {
                    homeViewModel.getMoodList().add(moodEvent);
                }
                homeViewModel.getMoodList().sortAllMoodList();
                moodAdapter.notifyDataSetChanged();
            }

        });

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
                dialog.setMessage("Delete is unrecoverable. Are you sure?");
                dialog.setCancelable(false);
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DocumentReference docRef = Database.getUserMoodList(LoginActivity.userName);
                        homeViewModel.getMoodList().delete(deleteMood);
                        MoodList moodList = homeViewModel.getMoodList();
                        docRef.set(moodList);
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

        NewbieGuide.with(this)
                .setLabel("page")
                //.anchor(anchor)
                .setOnGuideChangedListener(new OnGuideChangedListener() {
                    @Override
                    public void onShowed(Controller controller) {
                        Log.e(TAG, "NewbieGuide onShowed: ");
                    }

                    @Override
                    public void onRemoved(Controller controller) {
                        Log.e(TAG, "NewbieGuide  onRemoved: ");
                    }
                })
                .setOnPageChangedListener(new OnPageChangedListener() {
                    @Override
                    public void onPageChanged(int page) {
                    }
                })
                //.alwaysShow(true)//Whether the boot layer is displayed every time, by default false is displayed only once
                .addGuidePage(
                        GuidePage.newInstance()
                                .addHighLight(fab)
                                .setLayoutRes(R.layout.view_guide_floatbutton, R.id.iv)//Set the guide page layout
                                .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {

                                    @Override
                                    public void onLayoutInflated(View view, final Controller controller) {
                                        view.findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                controller.showPreviewPage();
                                            }
                                        });
                                    }
                                })
                                .setEverywhereCancelable(false)
                )
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(myMoodEventListView)
                        .setLayoutRes(R.layout.view_guide_moodevent, R.id.iv)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view, final Controller controller) {
                                view.findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        controller.showPreviewPage();
                                    }
                                });
                            }
                        })
                        .setEverywhereCancelable(false)//Whether to click anywhere to jump to the next page or disappear the guide layer, the default is true
                )
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(homeFilterButton)
                        .setLayoutRes(R.layout.view_guide_filterbutton, R.id.iv)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view, final Controller controller) {
                                view.findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        controller.showPreviewPage();
                                    }
                                });
                            }
                        })
                        .setEverywhereCancelable(false)
                )
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(rectF)
                        .setLayoutRes(R.layout.view_guide_drawer, R.id.iv)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view, final Controller controller) {
                                view.findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        controller.showPreviewPage();
                                    }
                                });
                            }
                        })
                        .setEverywhereCancelable(false)
                )
                .show();//display
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

                        DocumentReference docRef = Database.getUserMoodList(LoginActivity.userName);
                        MoodEvent originalMoodEvent =
                                (MoodEvent) data.getSerializableExtra("original_mood_event");
                        MoodEvent editMoodEvent =
                                (MoodEvent) data.getSerializableExtra("edited_mood_event_return");
                        homeViewModel.getMoodList().edit(editMoodEvent, originalMoodEvent);
                        MoodList moodList = homeViewModel.getMoodList();
                        docRef.set(moodList);
                        moodAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case HOME_TO_ADD_REQUEST:
                if (resultCode == RESULT_OK) {
                    DocumentReference docRef = Database.getUserMoodList(LoginActivity.userName);
                    MoodEvent moodEventAdded = (MoodEvent) data.getSerializableExtra("added_mood_event");
                    homeViewModel.getMoodList().add(moodEventAdded);
                    MoodList moodList = homeViewModel.getMoodList();
                    docRef.set(moodList);
                    onMoodSelected(selectedMoodType);
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
        homeFilterClearAllButton = view.findViewById(R.id.home_filter_clear_all_button);
        homeFilterSelectAllButton = view.findViewById(R.id.home_filter_select_all_button);
        ArrayList<MoodBean> mData = new ArrayList<>();
        mData.add(new MoodBean(R.drawable.happy,"happy", 0));
        mData.add(new MoodBean(R.drawable.angry,"angry", 1));
        mData.add(new MoodBean(R.drawable.content,"content", 2));
        mData.add(new MoodBean(R.drawable.meh,"meh", 3));
        mData.add(new MoodBean(R.drawable.sad,"sad", 4));
        mData.add(new MoodBean(R.drawable.stressed,"stressed", 5));
        homeFilterListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        filterAdapter = new FilterAdapter(activity, R.layout.filter_item, mData);
        filterAdapter.setSelectedItems(selectedMoodType);
        homeFilterListView.setAdapter(filterAdapter);

        homeFilterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MoodBean moodBean = (MoodBean) adapterView.getItemAtPosition(i);
                if (selectedMoodType.contains(moodBean.getName())) {
                    selectedMoodType.remove(moodBean.getName());
                } else {
                    selectedMoodType.add(moodBean.getName());
                }
                filterAdapter.setSelectedItems(selectedMoodType);
                filterAdapter.notifyDataSetChanged();

            }
        });

        homeFilterSelectAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedMoodType = new ArrayList<String>() {
                    {
                        add("happy");
                        add("sad");
                        add("stressed");
                        add("meh");
                        add("content");
                        add("angry");
                    }
                };
                filterAdapter.setSelectedItems(selectedMoodType);
                filterAdapter.notifyDataSetChanged();
            }
        });

        homeFilterClearAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedMoodType = new ArrayList<>();
                filterAdapter.setSelectedItems(selectedMoodType);
                filterAdapter.notifyDataSetChanged();
            }
        });

        dialog.setView(view)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedMoodType = filterAdapter.getSelectedItems();
                        onMoodSelected(selectedMoodType);
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
     * @param selectedMoodTypes
     *      These are the mood types selected to show
     */
    private void onMoodSelected(ArrayList<String> selectedMoodTypes) {
        ArrayList<MoodEvent> filteredMoodList = new ArrayList<>();
        if ((selectedMoodTypes.size() == 0) || (selectedMoodType.size() == 6)) {
            moodAdapter = new MoodAdapter(getActivity(), R.layout.mood_list_item, homeViewModel.getMoodList().getAllMoodList());
            myMoodEventListView.setAdapter(moodAdapter);
            return;
        }
        for (String moodType: selectedMoodTypes) {
            filteredMoodList.addAll(homeViewModel.getMoodList().getMoodList(moodType));
        }
        filteredMoodList = MoodList.sortMoodList(filteredMoodList);
        moodAdapter = new MoodAdapter(getActivity(), R.layout.mood_list_item, filteredMoodList);
        myMoodEventListView.setAdapter(moodAdapter);
    }

    

}