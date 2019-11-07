package com.example.mooddiary;

/**
 * This is a class holding data for spinner and filter custom layout
 */
public class MoodBean {

    private int icon;
    private String name;

    public MoodBean() {
    }

    public MoodBean(int icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }
}
