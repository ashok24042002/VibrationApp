package com.example.vibrationapp;

public class Machine {
    private String name;
    private float threshold;
    private String condition;

    public Machine(String name, float threshold, String condition) {
        this.name = name;
        this.threshold = threshold;
        this.condition = condition;
    }

    public String getName() {
        return name;
    }

    public float getThreshold() {
        return threshold;
    }

    public String getCondition() {
        return condition;
    }
}

