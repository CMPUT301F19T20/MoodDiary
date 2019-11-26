package com.example.mooddiary;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.google.android.gms.maps.model.LatLng;

/**
 * This is a class to store data for MoodEvent
 */
public class MoodEvent implements Serializable {
    private String username;
    private String date;
    private String time;
    private String preciseTime;
    private String socialSituation;
    private String location;
    private double latitude;
    private double longitude;
    private String reason;
    private String photo;
    private Mood mood;

    /**
     * This converts the format of Date
     */
    private static final SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public MoodEvent() {
    }


    public MoodEvent(String mood, String date, String time,String preciseTime,
                     String socialSituation, String location, double latitude, double longitude,
                     String reason, String photo, String username) {
        this.date = date;
        this.time = time;
        this.preciseTime = preciseTime;
        this.socialSituation = socialSituation;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.reason = reason;
        this.photo = photo;
        this.mood = new Mood(mood);
        this.username = username;
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
            dateInDateFormat = fmt.parse(this.date + " " + this.preciseTime);
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

    public String getPreciseTime(){return this.preciseTime;}

    public void setPreciseTime(String preciseTime){this.preciseTime = preciseTime;}

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

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
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
                this.preciseTime.equals(compare.getPreciseTime())&&
                this.socialSituation.equals(compare.getSocialSituation()) &&
                this.location.equals(compare.getLocation()) &&
                this.reason.equals(compare.getReason()) &&
                this.photo.equals(compare.getPhoto()) &&
                this.mood.equals(compare.getMood()) &&
                this.username.equals(compare.getUsername())) {
            return true;
        } else {
            return false;
        }
    }

}
