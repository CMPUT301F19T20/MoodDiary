package com.example.mooddiary;

import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

/**
 * This is a class to store data for MoodEvent
 */
public class MoodEvent implements Serializable {
    private String date;
    private String time;
    private String socialSituation;
    private String location;
    private String reason;
    private String photo;
    private Mood mood;

    /**
     * This converts the format of Date
     */
    private static final SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    public MoodEvent() {
    }


    public MoodEvent(String mood, String date, String time, String socialSituation, String location,
                     String reason, String photo) {
//        try {
//            this.date = fmt.parse(date + " " + time);
//        } catch(Exception e) {}
        this.date = date;
        this.time = time;
        this.socialSituation = socialSituation;
        this.location = location;
        this.reason = reason;
        this.photo = photo;
        this.mood = new Mood(mood);
    }

    /**
     * This converts the format of time
     * @return
     *       Return the number of milliseconds since January 1, 1970, 00:00:00 GTM
     *
     */
    public long getNumericDate() {
        Date dateInDateFormat;
        try {
            dateInDateFormat = fmt.parse(this.date + " " + this.time);
            return dateInDateFormat.getTime();
        } catch(Exception e) {
            return -1;
        }
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSocialSituation() {
        return socialSituation;
    }

    public void setSocialSituation(String socialSituation) {
        this.socialSituation = socialSituation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    /**
     * This is to check whether two MoodEvents are the same
     * @param e
     *      This is a MoodEvent to compare
     * @return
     *      Return true if same, false otherwise
     */
    @Override
    public boolean equals(Object e) {
        MoodEvent compare = (MoodEvent) e;
        if(this.date.equals(compare.getDate()) &&
                this.time.equals(compare.getTime()) &&
                this.socialSituation.equals(compare.getSocialSituation()) &&
                this.location.equals(compare.getLocation()) &&
                this.reason.equals(compare.getReason()) &&
                this.photo.equals(compare.getPhoto()) &&
                this.mood.equals(compare.getMood())) {
            return true;
        } else {
            return false;
        }
    }

}
