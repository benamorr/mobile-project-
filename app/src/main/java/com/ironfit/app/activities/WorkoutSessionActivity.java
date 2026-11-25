package com.ironfit.app.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.ironfit.app.R;
import com.ironfit.app.adapters.SessionExerciseAdapter;
import com.ironfit.app.database.ExerciseDao;
import com.ironfit.app.database.PersonalRecordDao;
import com.ironfit.app.database.WorkoutSessionDao;
import com.ironfit.app.models.Exercise;
import com.ironfit.app.models.ExerciseSet;
import com.ironfit.app.models.WorkoutSession;
import com.ironfit.app.utils.AnimationUtilsHelper;
import com.ironfit.app.utils.DateUtils;
import com.ironfit.app.utils.PreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class WorkoutSessionActivity extends AppCompatActivity {

    public static final String EXTRA_SPECIFIC_EXERCISE_ID = "extra_specific_exercise_id";

    private TextView tvSessionName, tvSessionTimer, tvRestCountdown;
    private CardView cardRestTimer;
    private MaterialButton btnSkipRest, btnFinishWorkout;
    private ImageButton btnClose;
    private RecyclerView recyclerExercises;

    private SessionExerciseAdapter adapter;
    private List<SessionExerciseAdapter.ExerciseEntry> exerciseEntries;

    private long startTimeMillis = 0L;
    private Handler timerHandler = new Handler(Looper.getMainLooper());
    private Runnable timerRunnable;
    private CountDownTimer restCountdownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_session);

        initViews();
        setupWorkoutData();
        startStopwatch();
    }

    private void initViews() {
        tvSessionName = findViewById(R.id.tv_session_name);
        tvSessionTimer = findViewById(R.id.tv_session_timer);
        tvRestCountdown = findViewById(R.id.tv_rest_countdown);
        cardRestTimer = findViewById(R.id.card_rest_timer);
        btnSkipRest = findViewById(R.id.btn_skip_rest);
        btnFinishWorkout = findViewById(R.id.btn_finish_workout);
        btnClose = findViewById(R.id.btn_close_session);
        recyclerExercises = findViewById(R.id.recycler_session_exercises);

        AnimationUtilsHelper.setupTouchBounce(btnFinishWorkout);
        AnimationUtilsHelper.setupTouchBounce(btnClose);
        AnimationUtilsHelper.setupTouchBounce(btnSkipRest);

        recyclerExercises.setLayoutManager(new LinearLayoutManager(this));

        btnClose.setOnClickListener(v -> confirmCancelWorkout());
        btnSkipRest.setOnClickListener(v -> cancelRestTimer());
        btnFinishWorkout.setOnClickListener(v -> finishWorkout());
    }

    private void setupWorkoutData() {
        PreferencesManager prefs = new PreferencesManager(this);
        String planName = prefs.getCurrentPlanName();
        tvSessionName.setText(planName + " Workout");

        ExerciseDao exerciseDao = new ExerciseDao(this);
        exerciseEntries = new ArrayList<>();

        int specificId = getIntent().getIntExtra(EXTRA_SPECIFIC_EXERCISE_ID, -1);
        if (specificId != -1) {
            Exercise ex = exerciseDao.getExerciseById(specificId);
            if (ex != null) {
                exerciseEntries.add(new SessionExerciseAdapter.ExerciseEntry(ex));
            }
        }

        // If no specific or only 1, fill with sample exercises
        if (exerciseEntries.isEmpty()) {
            List<Exercise> all = exerciseDao.getAllExercises();
            int count = Math.min(all.size(), 4);
            for (int i = 0; i < count; i++) {
                exerciseEntries.add(new SessionExerciseAdapter.ExerciseEntry(all.get(i)));
            }
        }

        adapter = new SessionExerciseAdapter(this, exerciseEntries);
        adapter.setOnSetActionListener((exercise, set) -> {
            startRestTimer(exercise.getRestTimeSeconds() > 0 ? exercise.getRestTimeSeconds() : 60);
        });
        recyclerExercises.setAdapter(adapter);
    }

    private void startStopwatch() {
        startTimeMillis = SystemClock.uptimeMillis();
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                long elapsedSeconds = (SystemClock.uptimeMillis() - startTimeMillis) / 1000;
                tvSessionTimer.setText(DateUtils.formatDuration(elapsedSeconds));
                timerHandler.postDelayed(this, 1000);
            }
        };
        timerHandler.post(timerRunnable);
    }

    private void startRestTimer(int seconds) {
        cancelRestTimer();
        cardRestTimer.setVisibility(View.VISIBLE);

        restCountdownTimer = new CountDownTimer(seconds * 1000L, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long sec = millisUntilFinished / 1000;
                tvRestCountdown.setText(sec + "s remaining");
            }

            @Override
            public void onFinish() {
                cardRestTimer.setVisibility(View.GONE);
                Toast.makeText(WorkoutSessionActivity.this, "Rest complete! Time for the next set!", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    private void cancelRestTimer() {
        if (restCountdownTimer != null) {
            restCountdownTimer.cancel();
            restCountdownTimer = null;
        }
        cardRestTimer.setVisibility(View.GONE);
    }

    private void finishWorkout() {
        List<ExerciseSet> completedSets = adapter.getAllCompletedSets();

        // If user didn't manually check boxes, mark current filled sets
        if (completedSets.isEmpty()) {
            for (SessionExerciseAdapter.ExerciseEntry entry : exerciseEntries) {
                for (ExerciseSet s : entry.sets) {
                    s.setCompleted(true);
                    completedSets.add(s);
                }
            }
        }

        long durationSec = (SystemClock.uptimeMillis() - startTimeMillis) / 1000;
        long now = System.currentTimeMillis();

        WorkoutSession session = new WorkoutSession();
        session.setName(tvSessionName.getText().toString());
        session.setStartTime(now - (durationSec * 1000));
        session.setEndTime(now);
        session.setDurationSeconds(durationSec);
        session.setSets(completedSets);
        session.setNotes("Completed via IronFit Workout Tracker");

        WorkoutSessionDao sessionDao = new WorkoutSessionDao(this);
        sessionDao.saveSession(session);

        // Update Personal Records
        PersonalRecordDao prDao = new PersonalRecordDao(this);
        for (ExerciseSet s : completedSets) {
            if (s.getWeightKg() > 0) {
                prDao.upsertRecord(s.getExerciseId(), s.getExerciseName(), s.getWeightKg(), s.getReps());
            }
        }

        // Show celebratory dialog
        new AlertDialog.Builder(this)
                .setTitle("Workout Completed! 🔥")
                .setMessage("Great job crushing your session!\n\nDuration: " + DateUtils.formatDuration(durationSec) + "\nCompleted Sets: " + completedSets.size())
                .setPositiveButton("AWESOME", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }

    private void confirmCancelWorkout() {
        new AlertDialog.Builder(this)
                .setTitle("Quit Workout?")
                .setMessage("Are you sure you want to discard this workout session?")
                .setPositiveButton("QUIT", (dialog, which) -> finish())
                .setNegativeButton("RESUME", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timerHandler != null && timerRunnable != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }
        cancelRestTimer();
    }
}
