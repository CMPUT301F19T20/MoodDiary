package com.example.mooddiary;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUser {
<<<<<<< Updated upstream
    @Test
    public void testSetUsername(){
        User user = new User("aaa");
        user.setUsername("bbb");
        assertEquals("bbb",user.getUsername());
    }
    @Test
    public void testSetMoodList(){
        User user= new User();
        MoodList list = new MoodList();
        user.setMoodList(list);
        assertEquals(list,user.getMoodList());
    }
    @Test
    public void testSetFriends(){
        User user= new User();
        ArrayList friend = new ArrayList<String>();
        user.setFriends(friend);
        assertEquals(friend,user.getFriends());
    }
    @Test
    public void testAddFriends(){
        User user= new User();
        ArrayList friend = new ArrayList<String>();
        user.setFriends(friend);
        user.addFriends("aaa");
        assertEquals("aaa",user.getFriends().get(0));
    }
}
=======

        @Test
        public void testSetUsername(){
            User user = new User("aaa");
            user.setUsername("bbb");
            assertEquals("bbb",user.getUsername());
        }
        @Test
        public void testSetMoodList(){
            User user= new User();
            MoodList list = new MoodList();
            user.setMoodList(list);
            assertEquals(list,user.getMoodList());
        }
        @Test
        public void testSetFriends(){
            User user= new User();
            ArrayList friend = new ArrayList<String>();
            user.setFriends(friend);
            assertEquals(friend,user.getFriends());
        }
        @Test
        public void testAddFriends(){
            User user= new User();
            ArrayList friend = new ArrayList<String>();
            user.setFriends(friend);
            user.addFriends("aaa");
            assertEquals("aaa",user.getFriends().get(0));
        }
    }


>>>>>>> Stashed changes
