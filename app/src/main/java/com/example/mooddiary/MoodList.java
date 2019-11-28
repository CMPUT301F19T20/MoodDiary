package com.example.mooddiary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
/**
 * This is a class to manage a list of MoodEvent object
 */
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

    /**
     * This adds new a mood event to mood list
     *  add each new mood event to allMoodList and to the mood list which it belongs to
     * @param mood
     *      This is a MoodEvent
     */
    public void add(MoodEvent mood){
        if(allMoodList.contains(mood)){
            throw new IllegalArgumentException();
        } else {
            allMoodList.add(mood);
            sortAllMoodList();
            String moodString = mood.getMood().getMood();
            switch (moodString) {
                case "happy" :
                    happyList.add(mood);
                    sortHappyList();
                    break;
                case "angry" :
                    angryList.add(mood);
                    sortAngryList();
                    break;
                case "sad" :
                    sadList.add(mood);
                    sortSadList();
                    break;
                case "content" :
                    contentList.add(mood);
                    sortContentList();
                    break;
                case "stressed" :
                    stressedList.add(mood);
                    sortStressedList();
                    break;
                case "meh" :
                    mehList.add(mood);
                    sortMehList();
                    break;
                default :
                    throw new IllegalArgumentException();
            }



        }

    }

    /**
     * This deletes a mood event from mood list
     *  delete mood event from allMoodList and from the mood list which it belongs to
     * @param mood
     *      This is a MoodEvent
     */
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

    /**
     * This edits a mood event
     * @param newMood
     *      this is the mood event edited
     * @param originMood
     *      this is the original mood event to delete
     */
    public void edit(MoodEvent newMood, MoodEvent originMood) {
        delete(originMood);
        add(newMood);
    }

    /**
     * This returns the mood event of all mood list at a specific position
     * @param position
     *       this is the position of the MoodEvent
     * @return
     *       Return the MoodEvent at position
     */
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

    /**
     * This return a mood list that contains all mood events with a specific mood
     * @param type
     *        This is a kind of mood to filter
     * @return
     *        Return a mood list that contains all mood events with mood type "type"
     */
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
        return sortList;
    }



    /**
     * This sorts allMoodList by Time
     */
    public void sortAllMoodList() {
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

    /**
     * This sorts angryList by Time
     */
    public void sortAngryList() {
        angryList.sort(new Comparator<MoodEvent>() {
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
        Collections.reverse(angryList);
    }

    /**
     * This sorts happyList by Time
     */
    public void sortHappyList() {
        happyList.sort(new Comparator<MoodEvent>() {
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
        Collections.reverse(happyList);
    }

    /**
     * This sorts sadList by Time
     */
    public void sortSadList() {
        sadList.sort(new Comparator<MoodEvent>() {
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
        Collections.reverse(sadList);
    }

    /**
     * This sorts mehList by Time
     */
    public void sortMehList() {
        mehList.sort(new Comparator<MoodEvent>() {
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
        Collections.reverse(mehList);
    }

    /**
     * This sorts contentList by Time
     */
    public void sortContentList() {
        contentList.sort(new Comparator<MoodEvent>() {
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
        Collections.reverse(contentList);
    }

    /**
     * This sorts stressedList by Time
     */
    public void sortStressedList() {
        stressedList.sort(new Comparator<MoodEvent>() {
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
        Collections.reverse(stressedList);
    }

    /**
     * This clears MoodList
     */
    public void clearMoodList() {
        this.allMoodList.clear();
        this.angryList.clear();
        this.happyList.clear();
        this.contentList.clear();
        this.sadList.clear();
        this.stressedList.clear();
        this.mehList.clear();
    }

    public static ArrayList<MoodEvent> sortMoodList(ArrayList<MoodEvent> moodListToSort) {
        moodListToSort.sort(new Comparator<MoodEvent>() {
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
        Collections.reverse(moodListToSort);
        return moodListToSort;
    }

}
