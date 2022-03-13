package com.example.gamification;

public class Task {
    private int pointValue;
    private String instructions;

    public Task() {}

    public Task(int p, String i){
        this.pointValue = p;
        this.instructions = i;
    }

    public int getPointValue() {
        return pointValue;
    }

    public void setPointValue(int pointValue) {
        this.pointValue = pointValue;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return (instructions + "\n" + pointValue + " points");
    }
}
