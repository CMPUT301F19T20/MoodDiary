package com.example.mooddiary;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MoodList {

    private ArrayList<MoodEvent> allMoodList;
    private ArrayList<MoodEvent> happyList;
    private ArrayList<MoodEvent> angryList;
    private ArrayList<MoodEvent> sadList;
    private ArrayList<MoodEvent> contentList;
    private ArrayList<MoodEvent> stressedList;
    private ArrayList<MoodEvent> worriedList;

    public MoodList() {
        allMoodList = new ArrayList<>();
        happyList = new ArrayList<>();
        angryList = new ArrayList<>();
        sadList = new ArrayList<>();
        contentList = new ArrayList<>();
        stressedList = new ArrayList<>();
        worriedList = new ArrayList<>();

    }

    public void add(MoodEvent mood){
        if(allMoodList.contains(mood)){

            throw new IllegalArgumentException();

        } else {

            allMoodList.add(mood);
            Collections.sort(allMoodList, new Comparator<MoodEvent>() {
                @Override
                public int compare(MoodEvent moodevent, MoodEvent t1) {
                    return t1.getDate().compareTo(moodevent.getDate());
                }
            });

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
                    worriedList.add(mood);
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
                case "worried" : worriedList.remove(mood); break;
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

    public ArrayList<MoodEvent> getAllMoodList() {return allMoodList;}

    public ArrayList<MoodEvent> getHappyList() {return happyList;}

    public ArrayList<MoodEvent> getAngryList() {
        return angryList;
    }

    public ArrayList<MoodEvent> getSadList() {
        return sadList;
    }

    public ArrayList<MoodEvent> getContentList() {
        return contentList;
    }

    public ArrayList<MoodEvent> getStressedList() {
        return stressedList;
    }

    public ArrayList<MoodEvent> getWorriedList() {
        return worriedList;
    }
}
