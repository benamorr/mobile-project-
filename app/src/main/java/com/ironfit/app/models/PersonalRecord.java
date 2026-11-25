package com.ironfit.app.models;

public class PersonalRecord {
    private int id;
    private int exerciseId;
    private String exerciseName;
    private float maxWeightKg;
    private int maxReps;
    private long timestamp;

    public PersonalRecord() {}

    public PersonalRecord(int id, int exerciseId, String exerciseName,
                          float maxWeightKg, int maxReps, long timestamp) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.maxWeightKg = maxWeightKg;
        this.maxReps = maxReps;
        this.timestamp = timestamp;
    }

    public PersonalRecord(int exerciseId, String exerciseName,
                          float maxWeightKg, int maxReps, long timestamp) {
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.maxWeightKg = maxWeightKg;
        this.maxReps = maxReps;
        this.timestamp = timestamp;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getExerciseId() { return exerciseId; }
    public void setExerciseId(int exerciseId) { this.exerciseId = exerciseId; }

    public String getExerciseName() { return exerciseName; }
    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }

    public float getMaxWeightKg() { return maxWeightKg; }
    public void setMaxWeightKg(float maxWeightKg) { this.maxWeightKg = maxWeightKg; }

    public int getMaxReps() { return maxReps; }
    public void setMaxReps(int maxReps) { this.maxReps = maxReps; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "PersonalRecord{id=" + id + ", exerciseName='" + exerciseName +
               "', maxWeight=" + maxWeightKg + ", maxReps=" + maxReps + "}";
    }
}
