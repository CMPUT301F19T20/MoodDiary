package com.example.mooddiary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MoodList {

    private ArrayList<MoodEvent> AllMoodList = new ArrayList<MoodEvent>();
    private ArrayList<MoodEvent> HappyList = new ArrayList<MoodEvent>();
    private ArrayList<MoodEvent> AngryList = new ArrayList<MoodEvent>();
    private ArrayList<MoodEvent> SadList = new ArrayList<MoodEvent>();
    private ArrayList<MoodEvent> ContentList = new ArrayList<MoodEvent>();
    private ArrayList<MoodEvent> StressedList = new ArrayList<MoodEvent>();
    private ArrayList<MoodEvent> WorriedList = new ArrayList<MoodEvent>();

    public void add(MoodEvent mood){
        if(AllMoodList.contains(mood)){
            throw new IllegalArgumentException();
        }
        else{
            AllMoodList.add(mood);
            Collections.sort(AllMoodList, new Comparator<MoodEvent>() {
                @Override
                public int compare(MoodEvent moodevent, MoodEvent t1) {
                    return t1.getDate().compareTo(moodevent.getDate());
                }
            });
            switch (mood.getMood().getMood()){
                case "happy" : HappyList.add(mood);
                case "angry" : AngryList.add(mood);
                case "sad" : SadList.add(mood);
                case "content" : ContentList.add(mood);
                case "stressed" : StressedList.add(mood);
                case "worried" : WorriedList.add(mood);
                default : throw new IllegalArgumentException();
            }

        }
    }

    public void delete(MoodEvent mood){
        if(AllMoodList.contains(mood)) {
            AllMoodList.remove(mood);
            switch (mood.getMood().getMood()){
                case "happy" : HappyList.remove(mood);
                case "angry" : AngryList.remove(mood);
                case "sad" : SadList.remove(mood);
                case "content" : ContentList.remove(mood);
                case "stressed" : StressedList.remove(mood);
                case "worried" : WorriedList.remove(mood);
                default : throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void edit(MoodEvent newMood, MoodEvent originMood){
        delete(originMood);
        add(newMood);
    }

    public MoodEvent ViewDetail(int position){
        return AllMoodList.get(position);
    }

}
