package com.ironfit.app.utils;

import com.ironfit.app.models.WorkoutPlan;

import java.util.ArrayList;
import java.util.List;

public class PlanRepository {

    public static List<WorkoutPlan> getDefaultPlans() {
        List<WorkoutPlan> plans = new ArrayList<>();

        plans.add(new WorkoutPlan(
                1,
                "Push Pull Legs (PPL)",
                "The ultimate 6-day split separating pushing muscles (chest/shoulders/triceps), pulling muscles (back/biceps), and legs for maximum muscle hypertrophy.",
                "Intermediate",
                6
        ));

        plans.add(new WorkoutPlan(
                2,
                "Full Body Routine",
                "High-frequency 3-day training covering foundational compound movements each workout. Ideal for steady progress and balanced recovery.",
                "Beginner",
                3
        ));

        plans.add(new WorkoutPlan(
                3,
                "Upper / Lower Split",
                "Proven 4-day split alternating upper body and lower body days. Exceptional balance between volume, intensity, and recovery.",
                "Intermediate",
                4
        ));

        plans.add(new WorkoutPlan(
                4,
                "Hypertrophy Muscle Specialization",
                "High-volume 5-day bodybuilding routine targeting aesthetic muscle development with progressive overload and isolation burnout.",
                "Advanced",
                5
        ));

        plans.add(new WorkoutPlan(
                5,
                "Pure Strength Program",
                "Heavy compound focused 4-day program optimizing neuromuscular adaptation in the squat, bench press, and deadlift.",
                "Advanced",
                4
        ));

        plans.add(new WorkoutPlan(
                6,
                "General Fitness & Conditioning",
                "Balanced 3-day regimen focusing on functional strength, core stability, joint health, and athletic stamina.",
                "Beginner",
                3
        ));

        return plans;
    }
}
