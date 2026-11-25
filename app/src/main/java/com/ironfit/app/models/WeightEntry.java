package com.ironfit.app.models;

public class WeightEntry {
    private int id;
    private float weight; // kg
    private long timestamp; // millis
    private String notes;

    public WeightEntry() {}

    public WeightEntry(int id, float weight, long timestamp, String notes) {
        this.id = id;
        this.weight = weight;
        this.timestamp = timestamp;
        this.notes = notes;
    }

    public WeightEntry(float weight, long timestamp, String notes) {
        this.weight = weight;
        this.timestamp = timestamp;
        this.notes = notes;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public float getWeight() { return weight; }
    public void setWeight(float weight) { this.weight = weight; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public String toString() {
        return "WeightEntry{id=" + id + ", weight=" + weight + ", timestamp=" + timestamp + "}";
    }
}
