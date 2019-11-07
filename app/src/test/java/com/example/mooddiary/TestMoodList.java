package com.example.mooddiary;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMoodList {

    @Test
    public void testAdd(){
        MoodList list = new MoodList();
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/23", "10:40", "alone", "", "", "");
        list.add(moodEvent);
        assertThrows(IllegalArgumentException.class,()->{
            list.add(moodEvent);
        });
    }
    @Test
    public void testDelete(){
        MoodList list = new MoodList();

        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/23", "10:40", "alone", "", "", "");
        list.add(moodEvent);
        list.delete(moodEvent);
        assertEquals(0,list.getMoodList("all").size());
        list.add(moodEvent);
        MoodEvent moodEvent2 =new MoodEvent("stressed", "2019/10/23", "10:40", "alone", "", "", "");
        list.add(moodEvent2);
        list.delete(moodEvent2);
        assertTrue(list.getMoodList("all").contains(moodEvent));
    }
    @Test
    public void testEdit(){
        MoodList list = new MoodList();
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/23", "10:40", "alone", "", "", "");
        MoodEvent moodEvent2 =new MoodEvent("stressed", "2019/10/23", "10:40", "alone", "", "", "");
        list.add(moodEvent);
        list.edit(moodEvent2,moodEvent);
        assertTrue(list.getMoodList("all").contains(moodEvent2));
    }
    @Test
    public void testGetMoodList(){
        MoodList list = new MoodList();
        assertThrows(IllegalArgumentException.class,()->{
            list.getMoodList("a");
        });
    }
    @Test
    public void testGetMoodList2(){
        MoodList list = new MoodList();
        MoodEvent moodEvent2 =new MoodEvent("stressed", "2019/10/23", "10:40", "alone", "", "", "");

        list.add(moodEvent2);
        assertEquals(moodEvent2,list.getMoodList("stressed").get(0));
    }
    @Test
    public void testClear(){
        MoodList list = new MoodList();
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/23", "10:40", "alone", "", "", "");

        MoodEvent moodEvent2 =new MoodEvent("stressed", "2019/10/23", "10:40", "alone", "", "", "");
        list.add(moodEvent);
        list.add(moodEvent2);
        list.clearMoodList();
        assertEquals(0,list.getMoodList("all").size());
    }
}
