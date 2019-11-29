package com.example.mooddiary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class GuideFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "guide_number";

    private ImageView mImageView;

    private int[] bgs = new int[]{
            R.drawable.diary,
            R.drawable.people,
            R.drawable.mapview};



    private String[] oneTitles = new String[]{
            "Hola!"+"\n"+"I'm MoodDiary",
            "With friends,"+"\n"+"You're not alone here",
            "Emotion map,\n"+"Bring you a new experience\n"};

    private String[] twoTitles = new String[]{
            "Here you can record daily mood,\n" +
                    "upload the photo,\n" +
                    "and write your feeling",
            "Check your friends' mood,\n" +
                    "See where they are,\n"+
                    "happy for their achievement",
            "Create a unique emotion map that \nbelongs to you, and your friends"
    };

    /**
     * This is to initialize a guide line
     * @param sectionNumber The number of section
     * @return
     */

    public static GuideFragment newInstance(int sectionNumber) {
        GuideFragment fragment = new GuideFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to. The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     *                 This value may be null.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous
     *                          saved state as given here.
     * @return         Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager, container, false);
        TextView textView = (TextView) view.findViewById(R.id.fragment_pager_text_label);
        textView.setText(oneTitles[getArguments().getInt(ARG_SECTION_NUMBER)-1]);
        TextView content = (TextView) view.findViewById(R.id.fragment_pager_text_content);
        content.setText(twoTitles[getArguments().getInt(ARG_SECTION_NUMBER)-1]);
        mImageView = (ImageView) view.findViewById(R.id.fragment_pager_img);
        mImageView.setBackgroundResource(bgs[getArguments().getInt(ARG_SECTION_NUMBER) - 1]);
        return view;
    }
}
