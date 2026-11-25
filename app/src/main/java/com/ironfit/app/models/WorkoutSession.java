package com.ironfit.app.models;

import java.util.List;

public class WorkoutSession {
    private int id;
    private String name;
    private long startTime; // millis
    private long endTime;   // millis
    private long durationSeconds;
    private List<ExerciseSet> sets;
    private String notes;

    public WorkoutSession() {}

    public WorkoutSession(int id, String name, long startTime, long endTime,
                          long durationSeconds, String notes) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.durationSeconds = durationSeconds;
        this.notes = notes;
    }

    public WorkoutSession(String name, long startTime, long endTime,
                          long durationSeconds, String notes) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.durationSeconds = durationSeconds;
        this.notes = notes;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public long getStartTime() { return startTime; }
    public void setStartTime(long startTime) { this.startTime = startTime; }

    public long getEndTime() { return endTime; }
    public void setEndTime(long endTime) { this.endTime = endTime; }

    public long getDurationSeconds() { return durationSeconds; }
    public void setDurationSeconds(long durationSeconds) { this.durationSeconds = durationSeconds; }

    public List<ExerciseSet> getSets() { return sets; }
    public void setSets(List<ExerciseSet> sets) { this.sets = sets; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public String toString() {
        return "WorkoutSession{id=" + id + ", name='" + name + "', startTime=" + startTime +
               ", durationSeconds=" + durationSeconds + "}";
    }
}
