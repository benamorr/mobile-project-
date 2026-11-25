package com.ironfit.app.models;

public class User {
    private int id;
    private String name;
    private int age;
    private float height; // cm
    private float weight; // kg
    private String experienceLevel; // Beginner, Intermediate, Advanced
    private String goal; // Muscle Gain, Fat Loss, Strength, General Fitness

    public User() {}

    public User(int id, String name, int age, float height, float weight,
                String experienceLevel, String goal) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.experienceLevel = experienceLevel;
        this.goal = goal;
    }

    public User(String name, int age, float height, float weight,
                String experienceLevel, String goal) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.experienceLevel = experienceLevel;
        this.goal = goal;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public float getHeight() { return height; }
    public void setHeight(float height) { this.height = height; }

    public float getWeight() { return weight; }
    public void setWeight(float weight) { this.weight = weight; }

    public String getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; }

    public String getGoal() { return goal; }
    public void setGoal(String goal) { this.goal = goal; }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', age=" + age +
               ", height=" + height + ", weight=" + weight +
               ", level='" + experienceLevel + "', goal='" + goal + "'}";
    }
}
