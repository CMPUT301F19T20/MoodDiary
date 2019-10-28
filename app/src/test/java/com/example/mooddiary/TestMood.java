package com.example.mooddiary;
import org.junit.Test;


import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestMood {

    @Test
    public void testGetMood(){
        Mood mood = new Mood("happy");
        assertEquals("happy",mood.getMood());
        Mood mood2 = new Mood("angry");
        assertEquals("angry",mood2.getMood());
        Mood mood3 = new Mood("sad");
        assertEquals("sad",mood3.getMood());
        Mood mood4 = new Mood("content");
        assertEquals("content",mood4.getMood());
        Mood mood5 = new Mood("stressed");
        assertEquals("stressed",mood5.getMood());
        Mood mood6 = new Mood("meh");
        assertEquals("meh",mood6.getMood());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testMoodConstructor(){
        Mood mood = new Mood("good");
    }
    @Test
    public void testGetImage(){
        Mood mood = new Mood("happy");
        assertEquals(R.drawable.happy,mood.getMoodImage());
        Mood mood2 = new Mood("angry");
        assertEquals(R.drawable.angry,mood2.getMoodImage());
        Mood mood3 = new Mood("stressed");
        assertEquals(R.drawable.stressed,mood3.getMoodImage());
        Mood mood4 = new Mood("sad");
        assertEquals(R.drawable.sad,mood4.getMoodImage());
        Mood mood5 = new Mood("meh");
        assertEquals(R.drawable.meh,mood5.getMoodImage());
        Mood mood6 = new Mood("content");
        assertEquals(R.drawable.content,mood6.getMoodImage());
    }
    @Test
    public void testSetMood(){
        Mood mood = new Mood("happy");
        assertThrows(IllegalArgumentException.class,() -> {
            mood.setMood("good");
        });
        mood.setMood("sad");
        assertEquals("sad",mood.getMood());
        assertEquals(R.drawable.sad,mood.getMoodImage());
    }
@Test
    public void testGetColor(){
        Mood mood = new Mood("happy");
        assertEquals("#ff8080",mood.getColor());
        Mood mood2 = new Mood("angry");
        assertEquals( "#ba8dfd",mood2.getColor());
        Mood mood3 = new Mood("sad");
        assertEquals( "#accfff",mood3.getColor());
        Mood mood4 = new Mood("content");
        assertEquals( "#ffba92",mood4.getColor());
        Mood mood5 = new Mood("stressed");
        assertEquals( "#c6f1d6",mood5.getColor());
        Mood mood6 = new Mood("meh");
        assertEquals( "#d4f596",mood6.getColor());
}


}
