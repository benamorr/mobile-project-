package com.ironfit.app.models;

import java.util.List;

public class WorkoutDay {
    private int id;
    private int planId;
    private String dayName; // e.g. "Day 1 - Push"
    private List<Integer> exerciseIds;

    public WorkoutDay() {}

    public WorkoutDay(int id, int planId, String dayName) {
        this.id = id;
        this.planId = planId;
        this.dayName = dayName;
    }

    public WorkoutDay(int planId, String dayName) {
        this.planId = planId;
        this.dayName = dayName;
    }

    public WorkoutDay(int planId, String dayName, List<Integer> exerciseIds) {
        this.planId = planId;
        this.dayName = dayName;
        this.exerciseIds = exerciseIds;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPlanId() { return planId; }
    public void setPlanId(int planId) { this.planId = planId; }

    public String getDayName() { return dayName; }
    public void setDayName(String dayName) { this.dayName = dayName; }

    public List<Integer> getExerciseIds() { return exerciseIds; }
    public void setExerciseIds(List<Integer> exerciseIds) { this.exerciseIds = exerciseIds; }

    @Override
    public String toString() {
        return "WorkoutDay{id=" + id + ", planId=" + planId + ", dayName='" + dayName + "'}";
    }
}
