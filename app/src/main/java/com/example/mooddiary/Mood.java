package com.example.mooddiary;

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
    private final static String STRESSED_COLOR = "#c6f1d6";
    private final static String MEH_COLOR = "#d4f596";

    private int moodImage;
    private String mood;
    private String color;

    public Mood(String mood) {
        this.mood = mood;
        this.moodImage = imageOfMood(mood);
        this.color = colorOfMood(mood);
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
     * This returns the image for the mood.
     * @return
     *      Return the image for the mood.
     */
    public int getMoodImage() {
        return moodImage;
    }

    /**
     * This returns the color of the mood.
     * @return
     *      Return the color of the mood.
     */
    public String getColor() {return color;}

    /**
     * This sets the current mood to a new mood.
     * @param mood
     *      This is the type of the new mood.
     */
    public void setMood(String mood) {
        this.mood = mood;
        this.moodImage = imageOfMood(mood);
        this.color = colorOfMood(mood);
    }

    /**
     * This returns the image of the mood according to its type.
     * @param mood
     *      This is the type of the mood.
     * @return
     *      Return the image of the mood.
     */
    private static int imageOfMood(String mood) {
        switch(mood) {
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
     * @param mood
     *      This is the type of the mood.
     * @return
     *      Return the color string of the mood.
     */
    private static String colorOfMood(String mood) {
        switch(mood) {
            case "happy" : return HAPPY_COLOR;
            case "angry" : return ANGRY_COLOR;
            case "sad" : return SAD_COLOR;
            case "content" : return CONTENT_COLOR;
            case "stressed" : return STRESSED_COLOR;
            case "meh" : return MEH_COLOR;
            default : throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean equals(Object o) {
        Mood m = (Mood) o;
        return this.mood.equals(m.getMood());
    }
}
