package com.example.mooddiary;

import java.util.ArrayList;

/**
 * user class can keep track of user's information
 *
 */


public class User {
    private String username;
    private MoodList moodList = new MoodList();
    private ArrayList<String> friends;
    public User(){}
    public User(String username){
        this.username = username;
        friends = new ArrayList<String>();
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setMoodList(MoodList moodList){this.moodList= moodList;}

    public ArrayList<String> getFriends(){
        return this.friends;
    }

    public void setFriends(ArrayList<String> friends){
        this.friends = friends;
    }

    public void addFriends(String username){
        if(!this.friends.contains(username)){
            this.friends.add(username);
        }
    }

    public void removeFriends(String username){
        this.friends.remove(username);
    }

<<<<<<< Updated upstream
    public MoodList getMoodList(){
        return this.moodList;
    }
=======
    public MoodList getMoodList(){return this.moodList;}
>>>>>>> Stashed changes

}

