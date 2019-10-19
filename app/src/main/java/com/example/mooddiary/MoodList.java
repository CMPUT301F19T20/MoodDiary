package com.example.mooddiary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MoodList {

    private ArrayList<moodevent> AllMoodList = new ArrayList<moodevent>();
    private ArrayList<moodevent> HappyList = new ArrayList<moodevent>();
    private ArrayList<moodevent> AngryList = new ArrayList<moodevent>();
    private ArrayList<moodevent> SadList = new ArrayList<moodevent>();
    private ArrayList<moodevent> ContentList = new ArrayList<moodevent>();
    private ArrayList<moodevent> StressedList = new ArrayList<moodevent>();
    private ArrayList<moodevent> WorriedList = new ArrayList<moodevent>();

    public void add(moodevent Amood){
        if(AllMoodList.contains(Amood)){
            throw new IllegalArgumentException();
        }
        else{
            AllMoodList.add(Amood);
            Collections.sort(AllMoodList, new Comparator<moodevent>() {
                @Override
                public int compare(moodevent moodevent, moodevent t1) {
                    return t1.getDate().compareTo(moodevent.getDate());
                }
            });
            switch (Amood.getMood().getMood()){
                case "happy" : HappyList.add(Amood);
                case "angry" : AngryList.add(Amood);
                case "sad" : SadList.add(Amood);
                case "content" : ContentList.add(Amood);
                case "stressed" : StressedList.add(Amood);
                case "worried" : WorriedList.add(Amood);
                default : throw new IllegalArgumentException();
            }

        }
    }

    public void delete(moodevent Amood){
        AllMoodList.remove(Amood);
    }

    public void edit(int position, moodevent Amood){
        AllMoodList.remove(position);
        add(Amood);
    }

    public moodevent ViewDetail(int position){
        return AllMoodList.get(position);
    }

}
