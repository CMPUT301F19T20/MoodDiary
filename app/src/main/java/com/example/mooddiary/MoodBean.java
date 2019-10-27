package com.example.mooddiary;

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

    public void seName(String name) {
        this.name = name;
    }
}
