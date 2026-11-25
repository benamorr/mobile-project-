package com.ironfit.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.ironfit.app.R;
import com.ironfit.app.database.ExerciseDao;
import com.ironfit.app.models.Exercise;
import com.ironfit.app.utils.AnimationUtilsHelper;

public class ExerciseDetailActivity extends AppCompatActivity {

    public static final String EXTRA_EXERCISE_ID = "exercise_id";

    private Exercise exercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        int exerciseId = getIntent().getIntExtra(EXTRA_EXERCISE_ID, -1);
        if (exerciseId == -1) {
            Toast.makeText(this, "Exercise not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ExerciseDao dao = new ExerciseDao(this);
        exercise = dao.getExerciseById(exerciseId);

        if (exercise == null) {
            Toast.makeText(this, "Exercise data not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initViews();
    }

    private void initViews() {
        ImageButton btnBack = findViewById(R.id.btn_back);
        ImageView ivDetailImage = findViewById(R.id.iv_detail_image);
        TextView tvName = findViewById(R.id.tv_detail_name);
        TextView tvCategory = findViewById(R.id.tv_detail_category);
        TextView tvDifficulty = findViewById(R.id.tv_detail_difficulty);
        TextView tvDescription = findViewById(R.id.tv_detail_description);
        TextView tvSets = findViewById(R.id.tv_detail_sets);
        TextView tvReps = findViewById(R.id.tv_detail_reps);
        TextView tvRest = findViewById(R.id.tv_detail_rest);
        TextView tvTarget = findViewById(R.id.tv_detail_target_muscles);
        TextView tvSecondary = findViewById(R.id.tv_detail_secondary_muscles);
        TextView tvInstructions = findViewById(R.id.tv_detail_instructions);
        MaterialButton btnAddToWorkout = findViewById(R.id.btn_add_to_workout);

        btnBack.setOnClickListener(v -> finish());

        if (ivDetailImage != null && exercise.getImageUrl() != null && !exercise.getImageUrl().isEmpty()) {
            int resId = getResources().getIdentifier(exercise.getImageUrl(), "drawable", getPackageName());
            if (resId != 0) {
                ivDetailImage.setImageResource(resId);
                ivDetailImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ivDetailImage.clearColorFilter();
            }
        }

        tvName.setText(exercise.getName());
        tvCategory.setText(exercise.getCategory());
        tvDifficulty.setText(exercise.getDifficulty());

        int diffColor;
        switch (exercise.getDifficulty()) {
            case "Advanced":
                diffColor = ContextCompat.getColor(this, R.color.color_accent);
                break;
            case "Intermediate":
                diffColor = ContextCompat.getColor(this, R.color.color_warning);
                break;
            default:
                diffColor = ContextCompat.getColor(this, R.color.color_success);
                break;
        }
        tvDifficulty.setTextColor(diffColor);

        tvDescription.setText(exercise.getDescription());
        tvSets.setText(String.valueOf(exercise.getRecommendedSets()));
        tvReps.setText(exercise.getRecommendedReps());
        tvRest.setText(exercise.getRestTimeSeconds() + "s");

        tvTarget.setText(exercise.getTargetMuscles());
        tvSecondary.setText(exercise.getSecondaryMuscles());
        tvInstructions.setText(exercise.getInstructions());

        AnimationUtilsHelper.setupTouchBounce(btnBack);
        AnimationUtilsHelper.setupTouchBounce(btnAddToWorkout);

        btnAddToWorkout.setOnClickListener(v -> {
            Intent intent = new Intent(ExerciseDetailActivity.this, WorkoutSessionActivity.class);
            intent.putExtra(WorkoutSessionActivity.EXTRA_SPECIFIC_EXERCISE_ID, exercise.getId());
            startActivity(intent);
        });
    }
}
