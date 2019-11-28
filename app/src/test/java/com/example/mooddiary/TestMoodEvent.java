package com.example.mooddiary;

import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertEquals;

public class TestMoodEvent {

    @Test
    public void testGetDate() {
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/27", "10:40","10:40:01", "alone", "",10,0, "", "","tester");
        assertEquals("2019/10/27", moodEvent.getDate());
    }

    @Test
    public void testSetDate() {
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/27", "10:40","10:40:01", "alone", "",10,0, "", "","tester");
        moodEvent.setDate("2019/10/23");
        assertEquals("2019/10/23", moodEvent.getDate());
    }

    @Test
    public void testGetTime() {
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/27", "10:40","10:40:01", "alone", "",10,0, "", "","tester");
        assertEquals("10:40", moodEvent.getTime());

    }

    @Test
    public void testSetTime() {
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/27", "10:40","10:40:01", "alone", "",10,0, "", "","tester");
        moodEvent.setTime("11:40");
        assertEquals("11:40", moodEvent.getTime());
    }

    @Test
    public void testGetSituation() {
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/27", "10:40","10:40:01", "alone", "",10,0, "", "","tester");
        assertEquals("alone", moodEvent.getSocialSituation());
    }

    @Test
    public void testSetSituation() {
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/27", "10:40","10:40:01", "alone", "",10,0, "", "","tester");
        moodEvent.setSocialSituation("two");
        assertEquals("two", moodEvent.getSocialSituation());
    }

    @Test
    public void testGetLocation() {
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/27", "10:40","10:40:01", "alone", "",10,0, "", "","tester");
        assertEquals("", moodEvent.getLocation());
    }

    @Test
    public void testSetLocation() {
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/27", "10:40","10:40:01", "alone", "",10,0, "", "","tester");
        moodEvent.setLocation("1");
        assertEquals("1", moodEvent.getLocation());

    }

    @Test
    public void testGetReason() {
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/27", "10:40","10:40:01", "alone", "",10,0, "", "","tester");
        assertEquals("", moodEvent.getReason());

    }

    @Test
    public void testSetReason() {
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/27", "10:40","10:40:01", "alone", "",10,0, "", "","tester");
        moodEvent.setReason("2");
        assertEquals("2", moodEvent.getReason());

    }

    @Test
    public void testGetPhoto() {
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/27", "10:40","10:40:01", "alone", "",10,0, "", "","tester");
        assertEquals("", moodEvent.getReason());
    }

    @Test
    public void testSetPhoto() {
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/27", "10:40","10:40:01", "alone", "",10,0, "", "","tester");
        moodEvent.setPhoto("a");
        assertEquals("a", moodEvent.getPhoto());

    }


    @Test
    public void testSetMood() {
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/27", "10:40","10:40:01", "alone", "",10,0, "", "","tester");
        Mood mood = new Mood("angry");

        moodEvent.setMood(mood);
        assertEquals(mood, moodEvent.getMood());

    }
    @Test
    public void testSetLatitude() {
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/27", "10:40","10:40:01", "alone", "",10,0, "", "","tester");
        moodEvent.setLatitude(20);
        assertEquals(20, moodEvent.getLatitude(),0);
    }
    @Test
    public void testSetLongitude() {
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/27", "10:40","10:40:01", "alone", "",10,0, "", "","tester");
        moodEvent.setLongitude(20);
        assertEquals(20, moodEvent.getLongitude(),0);
    }


}
