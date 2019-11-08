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
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/23", "10:40","10:40:02", "alone", "", "", "");
        list.add(moodEvent);
        assertThrows(IllegalArgumentException.class,()->{
            list.add(moodEvent);
        });
    }
    @Test
    public void testDelete(){
        MoodList list = new MoodList();

        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/23", "10:40","10:40:02", "alone", "", "", "");
        list.add(moodEvent);
        list.delete(moodEvent);
        assertEquals(0,list.getMoodList("all").size());
        list.add(moodEvent);
        MoodEvent moodEvent2 =new MoodEvent("stressed", "2019/10/23", "10:40","10:40:02", "alone", "", "", "");
        list.add(moodEvent2);
        list.delete(moodEvent2);
        assertTrue(list.getMoodList("all").contains(moodEvent));
    }
    @Test
    public void testEdit(){
        MoodList list = new MoodList();
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/23", "10:40","10:40:02", "alone", "", "", "");
        MoodEvent moodEvent2 =new MoodEvent("stressed", "2019/10/23", "10:40","10:40:02", "alone", "", "", "");
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
        MoodEvent moodEvent2 =new MoodEvent("stressed", "2019/10/23", "10:40","10:40:02", "alone", "", "", "");

        list.add(moodEvent2);
        assertEquals(moodEvent2,list.getMoodList("stressed").get(0));
    }
    @Test
    public void testClear(){
        MoodList list = new MoodList();
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/23", "10:40", "10:40:02","alone", "", "", "");

        MoodEvent moodEvent2 =new MoodEvent("stressed", "2019/10/23", "10:40", "10:40:02","alone", "", "", "");
        list.add(moodEvent);
        list.add(moodEvent2);
        list.clearMoodList();
        assertEquals(0,list.getMoodList("all").size());
    }
    @Test
    public void testsortAllMoodList(){
        MoodList list = new MoodList();
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/21", "10:40", "alone", "", "", "");

        MoodEvent moodEvent2 =new MoodEvent("stressed", "2019/10/23", "10:40", "alone", "", "", "");
        MoodEvent moodEvent3 =new MoodEvent("stressed", "2019/10/22", "10:40", "alone", "", "", "");

        list.add(moodEvent);
        list.add(moodEvent2);
        list.add(moodEvent3);
        list.sortAllMoodList();
        assertEquals(moodEvent,list.getMoodList("all").get(0));

    }
    @Test
    public void testsortHappyList(){
        MoodList list = new MoodList();
        MoodEvent moodEvent = new MoodEvent("happy", "2019/10/21", "10:40", "alone", "", "", "");

        MoodEvent moodEvent2 =new MoodEvent("happy", "2019/10/23", "10:40", "alone", "", "", "");
        MoodEvent moodEvent3 =new MoodEvent("happy", "2019/10/22", "10:40", "alone", "", "", "");

        list.add(moodEvent);
        list.add(moodEvent2);
        list.add(moodEvent3);
        list.sortHappyList();
        assertEquals(moodEvent2,list.getMoodList("happy").get(0));

    }
    @Test
    public void testsortAngryList(){
        MoodList list = new MoodList();
        MoodEvent moodEvent = new MoodEvent("angry", "2019/10/21", "10:40", "alone", "", "", "");

        MoodEvent moodEvent2 =new MoodEvent("angry", "2019/10/23", "10:40", "alone", "", "", "");
        MoodEvent moodEvent3 =new MoodEvent("angry", "2019/10/22", "10:40", "alone", "", "", "");

        list.add(moodEvent);
        list.add(moodEvent2);
        list.add(moodEvent3);
        list.sortAngryList();
        assertEquals(moodEvent2,list.getMoodList("angry").get(0));

    }
    @Test
    public void testsortStressedList(){
        MoodList list = new MoodList();
        MoodEvent moodEvent = new MoodEvent("stressed", "2019/10/21", "10:40", "alone", "", "", "");

        MoodEvent moodEvent2 =new MoodEvent("stressed", "2019/10/23", "10:40", "alone", "", "", "");
        MoodEvent moodEvent3 =new MoodEvent("stressed", "2019/10/22", "10:40", "alone", "", "", "");

        list.add(moodEvent);
        list.add(moodEvent2);
        list.add(moodEvent3);
        list.sortStressedList();
        assertEquals(moodEvent2,list.getMoodList("stressed").get(0));

    }
    @Test
    public void testsortMehList(){
        MoodList list = new MoodList();
        MoodEvent moodEvent = new MoodEvent("meh", "2019/10/21", "10:40", "alone", "", "", "");

        MoodEvent moodEvent2 =new MoodEvent("meh", "2019/10/23", "10:40", "alone", "", "", "");
        MoodEvent moodEvent3 =new MoodEvent("meh", "2019/10/22", "10:40", "alone", "", "", "");

        list.add(moodEvent);
        list.add(moodEvent2);
        list.add(moodEvent3);
        list.sortMehList();
        assertEquals(moodEvent2,list.getMoodList("meh").get(0));
    }
    @Test
    public void testsortContentList(){
        MoodList list = new MoodList();
        MoodEvent moodEvent = new MoodEvent("content", "2019/10/21", "10:40", "alone", "", "", "");

        MoodEvent moodEvent2 =new MoodEvent("content", "2019/10/23", "10:40", "alone", "", "", "");
        MoodEvent moodEvent3 =new MoodEvent("content", "2019/10/22", "10:40", "alone", "", "", "");

        list.add(moodEvent);
        list.add(moodEvent2);
        list.add(moodEvent3);
        list.sortContentList();
        assertEquals(moodEvent2,list.getMoodList("content").get(0));
    }
    @Test
    public void testsortSadList(){
        MoodList list = new MoodList();
        MoodEvent moodEvent = new MoodEvent("sad", "2019/10/21", "10:40", "alone", "", "", "");

        MoodEvent moodEvent2 =new MoodEvent("sad", "2019/10/23", "10:40", "alone", "", "", "");
        MoodEvent moodEvent3 =new MoodEvent("sad", "2019/10/22", "10:40", "alone", "", "", "");

        list.add(moodEvent);
        list.add(moodEvent2);
        list.add(moodEvent3);
        list.sortSadList();
        assertEquals(moodEvent2,list.getMoodList("sad").get(0));
    }
}
