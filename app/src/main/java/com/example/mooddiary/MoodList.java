package com.example.mooddiary;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MoodList implements Serializable {

    private ArrayList<MoodEvent> allMoodList;
    private ArrayList<MoodEvent> happyList;
    private ArrayList<MoodEvent> angryList;
    private ArrayList<MoodEvent> sadList;
    private ArrayList<MoodEvent> contentList;
    private ArrayList<MoodEvent> stressedList;
    private ArrayList<MoodEvent> mehList;

    public MoodList() {
        allMoodList = new ArrayList<>();
        happyList = new ArrayList<>();
        angryList = new ArrayList<>();
        sadList = new ArrayList<>();
        contentList = new ArrayList<>();
        stressedList = new ArrayList<>();
        mehList = new ArrayList<>();

    }

    public void add(MoodEvent mood){
        if(allMoodList.contains(mood)){
            throw new IllegalArgumentException();
        } else {
            allMoodList.add(mood);
            String moodString = mood.getMood().getMood();
            switch (moodString) {
                case "happy" :
                    happyList.add(mood);
                    break;
                case "angry" :
                    angryList.add(mood);
                    break;
                case "sad" :
                    sadList.add(mood);
                    break;
                case "content" :
                    contentList.add(mood);
                    break;
                case "stressed" :
                    stressedList.add(mood);
                    break;
                case "meh" :
                    mehList.add(mood);
                    break;
                default :
                    throw new IllegalArgumentException();
            }



        }

    }

    public void delete(MoodEvent mood){
        if(allMoodList.contains(mood)) {
            allMoodList.remove(mood);
            switch (mood.getMood().getMood()){
                case "happy" : happyList.remove(mood); break;
                case "angry" : angryList.remove(mood); break;
                case "sad" : sadList.remove(mood); break;
                case "content" : contentList.remove(mood); break;
                case "stressed" : stressedList.remove(mood); break;
                case "meh" : mehList.remove(mood); break;
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
        return allMoodList.get(position);
    }

    public ArrayList<MoodEvent> getMoodList(String type) {
        switch(type) {
            case "all" :
                return allMoodList;
            case "happy" :
                return happyList;
            case "angry" :
                return angryList;
            case "sad" :
                return sadList;
            case "content" :
                return contentList;
            case "stressed" :
                return stressedList;
            case "meh" :
                return mehList;
            default :
                throw new IllegalArgumentException();
        }
    }

}
