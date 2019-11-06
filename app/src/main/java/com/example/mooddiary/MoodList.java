package com.example.mooddiary;

import android.annotation.TargetApi;
import android.util.Log;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MoodList implements Serializable {

    public ArrayList<MoodEvent> allMoodList;
    public ArrayList<MoodEvent> happyList;
    public ArrayList<MoodEvent> angryList;
    public ArrayList<MoodEvent> sadList;
    public ArrayList<MoodEvent> contentList;
    public ArrayList<MoodEvent> stressedList;
    public ArrayList<MoodEvent> mehList;

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

    public void delete(MoodEvent mood) {
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

    public void edit(MoodEvent newMood, MoodEvent originMood) {
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

    public ArrayList<MoodEvent> getMehList() {
        return mehList;
    }

    public void setAllMoodList(ArrayList<MoodEvent> allMoodList){
        this.allMoodList = allMoodList;
    }

    public void setHappyList(ArrayList<MoodEvent> happyList){
        this.happyList = happyList;
    }

    public void setAngryList(ArrayList<MoodEvent> angryList){ this.angryList = angryList; }

    public void setSadList(ArrayList<MoodEvent> sadList){
        this.sadList = sadList;
    }

    public void setContentList(ArrayList<MoodEvent> contentList){
        this.contentList = contentList;
    }

    public void setStressedList(ArrayList<MoodEvent> stressedList){ this.stressedList = stressedList; }

    public void setMehList(ArrayList<MoodEvent> mehList){
        this.mehList = mehList;
    }

    public ArrayList<MoodEvent> getMoodList(String type) {
        ArrayList<MoodEvent> sortList;
        switch(type) {
            case "all" :
                sortList = allMoodList;
                break;
            case "happy" :
                sortList = happyList;
                break;
            case "angry" :
                sortList = angryList;
                break;
            case "sad" :
                sortList = sadList;
                break;
            case "content" :
                sortList = contentList;
                break;
            case "stressed" :
                sortList = stressedList;
                break;
            case "meh" :
                sortList = mehList;
                break;
            default :
                throw new IllegalArgumentException();
        }
        sortList.sort(new Comparator<MoodEvent>() {
            @Override
            public int compare(MoodEvent o1, MoodEvent o2) {
                if(o1.getNumericDate() > o2.getNumericDate()) {
                    return 1;
                } else if(o1.getNumericDate() < o2.getNumericDate()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        Collections.reverse(sortList);
        return sortList;
    }

    public void sortMoodList() {
        allMoodList.sort(new Comparator<MoodEvent>() {
            @Override
            public int compare(MoodEvent o1, MoodEvent o2) {
                if(o1.getNumericDate() > o2.getNumericDate()) {
                    return 1;
                } else if (o1.getNumericDate() < o2.getNumericDate()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        Collections.reverse(allMoodList);

    }

    public void clearMoodList() {
        this.allMoodList.clear();
        this.angryList.clear();
        this.happyList.clear();
        this.contentList.clear();
        this.sadList.clear();
        this.stressedList.clear();
        this.mehList.clear();
    }

}
