package com.example.gamification;

public class Profile {
    String name;
    int points;

    public Profile(String n, int p){
        name = n;
        points = p;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
