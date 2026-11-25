package com.ironfit.app.models;

import java.util.List;

public class WorkoutPlan {
    private int id;
    private String name; // e.g. "Push Pull Legs"
    private String description;
    private String level; // Beginner, Intermediate, Advanced
    private int daysPerWeek;
    private List<WorkoutDay> days;

    public WorkoutPlan() {}

    public WorkoutPlan(int id, String name, String description, String level, int daysPerWeek) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.level = level;
        this.daysPerWeek = daysPerWeek;
    }

    public WorkoutPlan(String name, String description, String level, int daysPerWeek) {
        this.name = name;
        this.description = description;
        this.level = level;
        this.daysPerWeek = daysPerWeek;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public int getDaysPerWeek() { return daysPerWeek; }
    public void setDaysPerWeek(int daysPerWeek) { this.daysPerWeek = daysPerWeek; }

    public List<WorkoutDay> getDays() { return days; }
    public void setDays(List<WorkoutDay> days) { this.days = days; }

    @Override
    public String toString() {
        return "WorkoutPlan{id=" + id + ", name='" + name + "', level='" + level +
               "', daysPerWeek=" + daysPerWeek + "}";
    }
}
