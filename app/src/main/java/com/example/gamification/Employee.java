package com.example.gamification;

public class Employee {
    String name;
    String level;
    int points;
    String code;

    public Employee(String name, String level, int points, String code) {
        this.name = name;
        this.level = level;
        this.points = points;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
