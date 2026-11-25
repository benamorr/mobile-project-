package com.ironfit.app.models;

public class ExerciseSet {
    private int id;
    private int sessionId;
    private int exerciseId;
    private String exerciseName;
    private int setNumber;
    private int reps;
    private float weightKg;
    private boolean completed;

    public ExerciseSet() {}

    public ExerciseSet(int id, int sessionId, int exerciseId, String exerciseName,
                       int setNumber, int reps, float weightKg, boolean completed) {
        this.id = id;
        this.sessionId = sessionId;
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.setNumber = setNumber;
        this.reps = reps;
        this.weightKg = weightKg;
        this.completed = completed;
    }

    public ExerciseSet(int sessionId, int exerciseId, String exerciseName,
                       int setNumber, int reps, float weightKg, boolean completed) {
        this.sessionId = sessionId;
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.setNumber = setNumber;
        this.reps = reps;
        this.weightKg = weightKg;
        this.completed = completed;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSessionId() { return sessionId; }
    public void setSessionId(int sessionId) { this.sessionId = sessionId; }

    public int getExerciseId() { return exerciseId; }
    public void setExerciseId(int exerciseId) { this.exerciseId = exerciseId; }

    public String getExerciseName() { return exerciseName; }
    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }

    public int getSetNumber() { return setNumber; }
    public void setSetNumber(int setNumber) { this.setNumber = setNumber; }

    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }

    public float getWeightKg() { return weightKg; }
    public void setWeightKg(float weightKg) { this.weightKg = weightKg; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    @Override
    public String toString() {
        return "ExerciseSet{id=" + id + ", sessionId=" + sessionId +
               ", exerciseName='" + exerciseName + "', set=" + setNumber +
               ", reps=" + reps + ", weight=" + weightKg + "}";
    }
}
