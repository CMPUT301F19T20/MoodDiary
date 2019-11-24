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
                    "upload the scenery that you take,\n" +
                    "write the feeling that you come up",
            "Check your friends' mood over the world,\n" +
                    "See where your friends are recently,\n"+
                    "and happy for their achievement\n",
            "Create a unique emotion map that belongs to you\n" +
                    "Of course, the emotion of friends can also be marked\n"
    };


    public static GuideFragment newInstance(int sectionNumber) {
        GuideFragment fragment = new GuideFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

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
