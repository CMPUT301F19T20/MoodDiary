package com.example.mooddiary;

import android.util.Log;

import java.io.Serializable;

/**
 * This is a class that store a Mood object
 */
public class Mood implements Serializable {

    // color for each type of Mood
    private final static String HAPPY_COLOR = "#ff8080";
    private final static String ANGRY_COLOR = "#ba8dfd";
    private final static String SAD_COLOR = "#accfff";
    private final static String CONTENT_COLOR = "#ffba92";
    private final static String STRESSED_COLOR = "#51dacf";
    private final static String MEH_COLOR = "#a7d129";

    private String mood;

    public Mood() {
    }

    public Mood(String mood) {
        this.mood = mood;
    }

    /**
     * This returns the type of mood.
     * @return
     *      Return the type of mood.
     */
    public String getMood() {
        return mood;
    }

    /**
     * This sets the current mood to a new mood.
     * @param mood
     *      This is the type of the new mood.
     */
    public void setMood(String mood) {
        this.mood = mood;
    }

    /**
     * This returns the image of the mood according to its type.
     * @return
     *      Return the image of the mood.
     */
    public int getMoodImage() {
        switch(this.mood) {
            case "happy" : return R.drawable.happy;
            case "angry" : return R.drawable.angry;
            case "sad" : return R.drawable.sad;
            case "content" : return R.drawable.content;
            case "stressed" : return R.drawable.stressed;
            case "meh" : return R.drawable.meh;
            default : throw new IllegalArgumentException();
        }
    }

    /**
     * This returns the color string of the mood according to its type.
     * @return
     *      Return the color string of the mood.
     */
    public String getColor() {
        switch(this.mood) {
            case "happy" : return HAPPY_COLOR;
            case "angry" : return ANGRY_COLOR;
            case "sad" : return SAD_COLOR;
            case "content" : return CONTENT_COLOR;
            case "stressed" : return STRESSED_COLOR;
            case "meh" : return MEH_COLOR;
            default : throw new IllegalArgumentException();
        }
    }

    /**
     * This returns the map marker of the mood according to its type.
     * @return
     *      Return the map marker of the mood.
     */
    public int getMarker() {
        switch (this.mood) {
            case "happy":
                return R.drawable.happymarker;
            case "sad":
                return R.drawable.sadmarker;
            case "content":
                return R.drawable.contentmarker;
            case "angry":
                return R.drawable.angrymarker;
            case "stressed":
                return R.drawable.stressedmarker;
            case "meh":
                return R.drawable.mehmarker;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * This compares two Mood objects
     * @param o
     *      This is a Mood object to compare
     * @return
     *      Return true if two Mood objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        Mood m = (Mood) o;
        return this.mood.equals(m.getMood());
    }
}
