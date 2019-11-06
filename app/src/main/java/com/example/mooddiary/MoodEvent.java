package com.example.mooddiary;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MoodEvent implements Serializable {
//    private Date date;
    private String date;
    private String time;
    private String socialSituation;
    private String location;
    private String reason;
    private String photo;
    private Mood mood;

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

    public long getNumericDate() {
        Date dateInFormatDate;
        try {
            dateInFormatDate = fmt.parse(date + " " + time);
            return dateInFormatDate.getTime();
        } catch(Exception e) {
            return -1;
        }
//        return date.getTime();
    }

//    public String getDateAndTime() {
//        return fmt.format(date);
//    }

    public String getDate() {
//        return fmt.format(date).substring(0,10);
        return this.date;
    }

    public void setDate(String date) {
//        try {
//            this.date = fmt.parse(date + " " + getTime());
//        } catch(Exception e) {};
        this.date = date;
    }

    public String getTime() {
//        return fmt.format(date).substring(10);
        return time;
    }

    public void setTime(String time) {
//        try {
//            this.date = fmt.parse(getDate() + " " + time);
//        } catch(Exception e) {}
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

    @Override
    public boolean equals(Object e) {
        MoodEvent compare = (MoodEvent)e;
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
