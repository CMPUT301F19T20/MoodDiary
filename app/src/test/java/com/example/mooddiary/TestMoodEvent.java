package com.example.mooddiary;

import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertEquals;

public class TestMoodEvent {

    @Test
    public void testGetDate() {
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/27", "10:40","10:40:01", "alone", "", "", "");
        assertEquals("2019/10/27", moodEvent.getDate());
    }

    @Test
    public void testSetDate() {
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/27", "10:40","10:40:01", "alone", "", "", "");
        moodEvent.setDate("2019/10/23");
        assertEquals("2019/10/23", moodEvent.getDate());
    }

    @Test
    public void testGetTime() {
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/23", "10:40","10:40:01", "alone", "", "", "");
        assertEquals("10:40", moodEvent.getTime());

    }

    @Test
    public void testSetTime() {
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/23", "10:40","10:40:01", "alone", "", "", "");
        moodEvent.setTime("11:40");
        assertEquals("11:40", moodEvent.getTime());
    }

    @Test
    public void testGetSituation() {
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/23", "10:40","10:40:01", "alone", "", "", "");
        assertEquals("alone", moodEvent.getSocialSituation());
    }

    @Test
    public void testSetSituation() {
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/23", "10:40","10:40:01", "alone", "", "", "");
        moodEvent.setSocialSituation("two");
        assertEquals("two", moodEvent.getSocialSituation());
    }

    @Test
    public void testGetLocation() {
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/23", "10:40","10:40:01", "alone", "", "", "");
        assertEquals("", moodEvent.getLocation());
    }

    @Test
    public void testSetLocation() {
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/23", "10:40","10:40:01", "alone", "", "", "");
        moodEvent.setLocation("1");
        assertEquals("1", moodEvent.getLocation());

    }

    @Test
    public void testGetReason() {
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/23", "10:40","10:40:01", "alone", "", "", "");
        assertEquals("", moodEvent.getReason());

    }

    @Test
    public void testSetReason() {
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/23", "10:40","10:40:01", "alone", "", "", "");
        moodEvent.setReason("2");
        assertEquals("2", moodEvent.getReason());

    }

    @Test
    public void testGetPhoto() {
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/23", "10:40","10:40:01", "alone", "", "", "");
        assertEquals("", moodEvent.getReason());
    }

    @Test
    public void testSetPhoto() {
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/23", "10:40","10:40:01", "alone", "", "", "");
        moodEvent.setPhoto("a");
        assertEquals("a", moodEvent.getPhoto());

    }


    @Test
    public void testSetMood() {
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/23", "10:40","10:40:01", "alone", "", "", "");
        Mood mood = new Mood("angry");

        moodEvent.setMood(mood);
        assertEquals(mood, moodEvent.getMood());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoodEventConstructor() {
        MoodEvent moodEvent = new MoodEvent("good", "2019/10/23", "10:40","10:40:01", "alone", "", "", "");
    }
}
