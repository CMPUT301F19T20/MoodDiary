package com.example.mooddiary;

import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertEquals;

public class TestMoodEvent {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    byte[] d = baos.toByteArray();
    @Test
    public void testGetDate() {
        MoodEvent moodEvent = new MoodEvent("happy", "Oct 24, 2019", "10:40", "alone", "", "", d);
        assertEquals("Oct 24, 2019", moodEvent.getDate());
    }

    @Test
    public void testSetDate() {
        MoodEvent moodEvent = new MoodEvent("happy", "Oct 24, 2019", "10:40", "alone", "", "", d);
        moodEvent.setDate("Oct 25, 2019");
        assertEquals("Oct 25, 2019", moodEvent.getDate());
    }

    @Test
    public void testGetTime() {
        MoodEvent moodEvent = new MoodEvent("happy", "Oct 24, 2019", "10:40", "alone", "", "", d);
        assertEquals("10:40", moodEvent.getTime());

    }

    @Test
    public void testSetTime() {
        MoodEvent moodEvent = new MoodEvent("happy", "Oct 24, 2019", "10:40", "alone", "", "", d);
        moodEvent.setTime("11:40");
        assertEquals("11:40", moodEvent.getTime());
    }

    @Test
    public void testGetSituation() {
        MoodEvent moodEvent = new MoodEvent("happy", "Oct 24, 2019", "10:40", "alone", "", "", d);
        assertEquals("alone", moodEvent.getSocialSituation());
    }

    @Test
    public void testSetSituation() {
        MoodEvent moodEvent = new MoodEvent("happy", "Oct 24, 2019", "10:40", "alone", "", "", d);
        moodEvent.setSocialSituation("two");
        assertEquals("two", moodEvent.getSocialSituation());
    }

    @Test
    public void testGetLocation() {
        MoodEvent moodEvent = new MoodEvent("happy", "Oct 24, 2019", "10:40", "alone", "", "", d);
        assertEquals("", moodEvent.getLocation());
    }

    @Test
    public void testSetLocation() {
        MoodEvent moodEvent = new MoodEvent("happy", "Oct 24, 2019", "10:40", "alone", "", "", d);
        moodEvent.setLocation("1");
        assertEquals("1", moodEvent.getLocation());

    }

    @Test
    public void testGetReason() {
        MoodEvent moodEvent = new MoodEvent("happy", "Oct 24, 2019", "10:40", "alone", "", "", d);
        assertEquals("", moodEvent.getReason());

    }

    @Test
    public void testSetReason() {
        MoodEvent moodEvent = new MoodEvent("happy", "Oct 24, 2019", "10:40", "alone", "", "", d);
        moodEvent.setReason("2");
        assertEquals("2", moodEvent.getReason());

    }

    @Test
    public void testGetPhoto() {
        MoodEvent moodEvent = new MoodEvent("happy", "Oct 24, 2019", "10:40", "alone", "", "", d);
        assertEquals(d, moodEvent.getReason());
    }

    @Test
    public void testSetPhoto() {
        MoodEvent moodEvent = new MoodEvent("happy", "Oct 24, 2019", "10:40", "alone", "", "", d);
        moodEvent.setPhoto(d);
        assertEquals(d, moodEvent.getPhoto());

    }


    @Test
    public void testSetMood() {
        MoodEvent moodEvent = new MoodEvent("happy", "Oct 24, 2019", "10:40", "alone", "", "", d);
        Mood mood = new Mood("angry");

        moodEvent.setMood(mood);
        assertEquals(mood, moodEvent.getMood());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoodEventConstructor() {
        MoodEvent moodEvent = new MoodEvent("good", "Oct 24, 2019", "10:40", "alone", "", "", d);
    }
}
