package com.ironfit.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.ironfit.app.R;
import com.ironfit.app.activities.MainActivity;
import com.ironfit.app.activities.WorkoutSessionActivity;
import com.ironfit.app.database.UserDao;
import com.ironfit.app.database.WorkoutSessionDao;
import com.ironfit.app.models.User;
import com.ironfit.app.utils.AnimationUtilsHelper;
import com.ironfit.app.utils.PreferencesManager;

import java.util.Locale;

public class HomeFragment extends Fragment {

    private TextView tvUserName, tvUserWeight, tvGoalChip, tvWorkoutName, tvExerciseCount;
    private MaterialButton btnStartWorkout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvUserName = view.findViewById(R.id.tv_user_name);
        tvUserWeight = view.findViewById(R.id.tv_user_weight);
        tvGoalChip = view.findViewById(R.id.tv_goal_chip);
        tvWorkoutName = view.findViewById(R.id.tv_workout_name);
        tvExerciseCount = view.findViewById(R.id.tv_exercise_count);
        btnStartWorkout = view.findViewById(R.id.btn_start_workout);

        loadUserData();
        setupCategoryClicks(view);

        AnimationUtilsHelper.setupTouchBounce(btnStartWorkout);
        AnimationUtilsHelper.startPulse(btnStartWorkout);

        View heroImage = view.findViewById(R.id.iv_workout_hero);
        if (heroImage != null) {
            AnimationUtilsHelper.setupTouchBounce(heroImage);
        }

        btnStartWorkout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), WorkoutSessionActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUserData();
    }

    private void loadUserData() {
        if (getContext() == null) return;

        UserDao userDao = new UserDao(getContext());
        User user = userDao.getUser();
        if (user != null) {
            tvUserName.setText(user.getName() != null && !user.getName().isEmpty() ? user.getName() : "Athlete");
            tvUserWeight.setText(String.format(Locale.getDefault(), "%.1f kg", user.getWeight()));
            tvGoalChip.setText(user.getGoal() != null ? user.getGoal() : "Muscle Gain");
        }

        PreferencesManager prefs = new PreferencesManager(getContext());
        String planName = prefs.getCurrentPlanName();
        if (tvWorkoutName != null && planName != null && !planName.isEmpty()) {
            tvWorkoutName.setText(planName);
        }

        WorkoutSessionDao sessionDao = new WorkoutSessionDao(getContext());
        int total = sessionDao.getTotalSessionCount();
        if (tvExerciseCount != null) {
            tvExerciseCount.setText(total > 0 ? total + " workouts logged" : "6 exercises");
        }
    }

    private void setupCategoryClicks(View root) {
        int[] chipIds = {
                R.id.chip_chest, R.id.chip_back, R.id.chip_shoulders,
                R.id.chip_biceps, R.id.chip_triceps, R.id.chip_legs, R.id.chip_abs
        };

        for (int id : chipIds) {
            View chip = root.findViewById(id);
            if (chip != null) {
                AnimationUtilsHelper.setupTouchBounce(chip);
                chip.setOnClickListener(v -> {
                    if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).navigateToTab(R.id.nav_exercises);
                    }
                });
            }
        }
    }
}
