package com.example.mooddiary;

import java.io.Serializable;

public class MoodEvent implements Serializable {
    private String date;
    private String time;
    private String socialSituation;
    private String location;
    private String reason;
    private String photo;
    private Mood mood;

    public MoodEvent(String mood, String date, String time, String socialSituation, String location,
                        String reason, String photo) {
        this.date = date;
        this.time = time;
        this.socialSituation = socialSituation;
        this.location = location;
        this.reason = reason;
        this.photo = photo;
        this.mood = new Mood(mood);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
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
}
