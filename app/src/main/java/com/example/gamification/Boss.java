package com.example.gamification;

import java.util.ArrayList;

public class Boss {
    String name;
    String code;
    int points;
    String level;
    ArrayList<String> employees;

    public Boss(String n, String c, int p, String l, ArrayList<String> e){
        name = n;
        code = c;
        points = p;
        level = l;
        employees = e;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public ArrayList<String> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<String> employees) {
        this.employees = employees;
    }
}
