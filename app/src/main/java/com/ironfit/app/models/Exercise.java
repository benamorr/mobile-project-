package com.ironfit.app.models;

public class Exercise {
    private int id;
    private String name;
    private String category; // Chest, Back, Shoulders, Biceps, Triceps, Legs, Abs
    private String targetMuscles;
    private String secondaryMuscles;
    private String description;
    private String instructions; // numbered steps, newline-separated
    private String difficulty; // Beginner, Intermediate, Advanced
    private int recommendedSets;
    private String recommendedReps; // e.g. "8-12"
    private int restTimeSeconds;
    private String imageUrl; // drawable resource name or URL placeholder

    public Exercise() {}

    public Exercise(int id, String name, String category, String targetMuscles,
                    String secondaryMuscles, String description, String instructions,
                    String difficulty, int recommendedSets, String recommendedReps,
                    int restTimeSeconds, String imageUrl) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.targetMuscles = targetMuscles;
        this.secondaryMuscles = secondaryMuscles;
        this.description = description;
        this.instructions = instructions;
        this.difficulty = difficulty;
        this.recommendedSets = recommendedSets;
        this.recommendedReps = recommendedReps;
        this.restTimeSeconds = restTimeSeconds;
        this.imageUrl = imageUrl;
    }

    public Exercise(String name, String category, String targetMuscles,
                    String secondaryMuscles, String description, String instructions,
                    String difficulty, int recommendedSets, String recommendedReps,
                    int restTimeSeconds, String imageUrl) {
        this.name = name;
        this.category = category;
        this.targetMuscles = targetMuscles;
        this.secondaryMuscles = secondaryMuscles;
        this.description = description;
        this.instructions = instructions;
        this.difficulty = difficulty;
        this.recommendedSets = recommendedSets;
        this.recommendedReps = recommendedReps;
        this.restTimeSeconds = restTimeSeconds;
        this.imageUrl = imageUrl;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getTargetMuscles() { return targetMuscles; }
    public void setTargetMuscles(String targetMuscles) { this.targetMuscles = targetMuscles; }

    public String getSecondaryMuscles() { return secondaryMuscles; }
    public void setSecondaryMuscles(String secondaryMuscles) { this.secondaryMuscles = secondaryMuscles; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public int getRecommendedSets() { return recommendedSets; }
    public void setRecommendedSets(int recommendedSets) { this.recommendedSets = recommendedSets; }

    public String getRecommendedReps() { return recommendedReps; }
    public void setRecommendedReps(String recommendedReps) { this.recommendedReps = recommendedReps; }

    public int getRestTimeSeconds() { return restTimeSeconds; }
    public void setRestTimeSeconds(int restTimeSeconds) { this.restTimeSeconds = restTimeSeconds; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    @Override
    public String toString() {
        return "Exercise{id=" + id + ", name='" + name + "', category='" + category + "'}";
    }
}
