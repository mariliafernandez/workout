package com.example.marilia.workout;

import android.content.Context;
import android.content.res.Resources;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class Exercise implements Serializable {

    private String name;
    private int category;
    private int duration;
    private int sets;
    private int repetitions;

    public Exercise() {

    }

    public Exercise(String name) {
        this.name = name;

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getCategory() {
        return category;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getSets() {
        return sets;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public int getRepetitions() {
        return repetitions;
    }

    @Override
    public String toString() {
        String print = "";
        switch (category) {
            case 0:
//                print += name + "\n" + duration + " " + R.string.minutes;
                print += name + "\n" + duration + " min";
                break;
            case 1:
//                print = name + "\n" + sets + " " + R.string.sets + " " + R.string.of + " " + repetitions + " " + R.string.repetitions;
                print = name + "\n" + sets + " x " + repetitions;
                break;
        }
        return print;
    }
}
