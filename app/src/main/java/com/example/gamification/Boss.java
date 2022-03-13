package com.example.gamification;

import java.util.ArrayList;

public class Boss {
    String name;
    String code;
    String level;
    ArrayList<String> employees;
    ArrayList<String> employeeNames;

    public Boss(String n, String c, String l, ArrayList<String> e, ArrayList<String> eN){
        name = n;
        code = c;
        level = l;
        employees = e;
        employeeNames = eN;
    }
    public Boss(){


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

    public ArrayList<String> getEmployeeNames() {
        return employeeNames;
    }

    public void setEmployeeNames(ArrayList<String> employeeNames) {
        this.employeeNames = employeeNames;
    }

    public String printEmployees() {
        String str = "";
        for(String s : employees) {
            str += s + "\n";
        }
        return str;
    }
}
