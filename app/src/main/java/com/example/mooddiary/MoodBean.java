package com.example.mooddiary;

/**
 * This is a class holding data for spinner and filter custom layout
 * This is essential for building an adapter with custom layout
 */
public class MoodBean {

    private int icon;
    private String name;
    private int index = 0;

    public MoodBean() {
    }

    public MoodBean(int icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public MoodBean(int icon, String name, int index) {
        this.icon = icon;
        this.name = name;
        this.index = index;
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

    public int getIndex() {
        return index;
    }
}
