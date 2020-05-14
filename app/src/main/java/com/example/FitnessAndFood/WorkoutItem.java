package com.example.FitnessAndFood;


public class WorkoutItem {

    private int text;
    private int title;
    private int bg;

    public WorkoutItem(int title, int text, int bg) {
        this.title = title;
        this.text = text;
        this.bg = bg;
    }

    public int getText() {
        return text;
    }

    public int getTitle() {
        return title;
    }

    public int getBg() {
        return bg;
    }
}
