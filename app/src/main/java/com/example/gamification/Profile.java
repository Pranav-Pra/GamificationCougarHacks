package com.example.gamification;

import java.util.ArrayList;

public class Profile {
    String name;
    int points;
    String level;
    String code;
    ArrayList<String> employees;
    String bossUid;

    public Profile(String n, int p, String l, String c){
        name = n;
        points = p;
        level = l;
        code = c;
    }

    public Profile(String n, String l, String c, ArrayList<String> e){
        name = n;
        level = l;
        code = c;
        employees = e;
    }

    public ArrayList<String> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<String> employees) {
        this.employees = employees;
    }

    public String getBossUid() {
        return bossUid;
    }

    public void setBossUid(String bossUid) {
        this.bossUid = bossUid;
    }

    public Profile(String n, String l, String c, String e){
        name = n;
        level = l;
        code = c;
        bossUid = e;
    }
    public Profile(String n, String l, String c){
        name = n;
        level = l;
        code = c;
    }

    public Profile(){
        name = "";
        points = 0;
        level = "l";
        code = "";
    }




    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
