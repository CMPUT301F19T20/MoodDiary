package com.example.mooddiary;

public class Mood {
    private int moodImage;
    private String mood;

    public Mood(String mood) {
        this.mood = mood;
        this.moodImage = imageOfMood(mood);
    }

    public String getMood() {
        return mood;
    }

    public int getMoodImage() {
        return moodImage;
    }

    public void setMood(String mood) {
        this.mood = mood;
        this.moodImage = imageOfMood(mood);
    }

    private static int imageOfMood(String mood) {
        switch(mood) {
            case "happy" : return R.drawable.happy;
            case "angry" : return R.drawable.angry;
            case "sad" : return R.drawable.sad;
            case "content" : return R.drawable.content;
            case "stressed" : return R.drawable.stressed;
            case "worried" : return R.drawable.worried;
            default : throw new IllegalArgumentException();
        }
    }
}
