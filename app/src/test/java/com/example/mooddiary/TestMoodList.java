package com.example.mooddiary;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMoodList {
    @Test
    public void testAdd(){
        MoodList list = new MoodList();
        MoodEvent moodEvent = new MoodEvent("happy", "Oct 24, 2019", "10:40", "alone", "", "", "");
        list.add(moodEvent);
        assertThrows(IllegalArgumentException.class,()->{
            list.add(moodEvent);
        });
    }
    @Test
    public void testDelete(){
        MoodList list = new MoodList();
        MoodEvent moodEvent = new MoodEvent("happy", "Oct 24, 2019", "10:40", "alone", "", "", "");
        list.add(moodEvent);
        list.delete(moodEvent);
        assertEquals(0,list.getAllMoodList().size());
        list.add(moodEvent);
        MoodEvent moodEvent2 =new MoodEvent("stressed", "Oct 22, 2019", "10:40", "alone", "", "", "");
        list.add(moodEvent2);
        list.delete(moodEvent2);
        assertTrue(list.getAllMoodList().contains(moodEvent));
    }
    @Test
    public void testEdit(){
        MoodList list = new MoodList();
        MoodEvent moodEvent = new MoodEvent("happy", "Oct 24, 2019", "10:40", "alone", "", "", "");
        MoodEvent moodEvent2 =new MoodEvent("stressed", "Oct 22, 2019", "10:40", "alone", "", "", "");
        list.add(moodEvent);
        list.edit(moodEvent,moodEvent2);
        assertTrue(list.getAllMoodList().contains(moodEvent2));
    }
}
