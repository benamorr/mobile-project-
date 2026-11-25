package com.ironfit.app.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.ironfit.app.R;
import com.ironfit.app.adapters.PersonalRecordAdapter;
import com.ironfit.app.adapters.WeightEntryAdapter;
import com.ironfit.app.database.PersonalRecordDao;
import com.ironfit.app.database.UserDao;
import com.ironfit.app.database.WeightDao;
import com.ironfit.app.database.WorkoutSessionDao;
import com.ironfit.app.models.PersonalRecord;
import com.ironfit.app.models.User;
import com.ironfit.app.models.WeightEntry;
import com.ironfit.app.utils.AnimationUtilsHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ProgressFragment extends Fragment {

    private TextView tvTotalWorkouts, tvCurrentWeight, tvWeeklyWorkouts, tvChartEmpty;
    private LineChart chartWeight;
    private MaterialButton btnLogWeight;
    private RecyclerView recyclerWeights, recyclerRecords;

    private WeightDao weightDao;
    private WorkoutSessionDao sessionDao;
    private PersonalRecordDao prDao;
    private UserDao userDao;

    private WeightEntryAdapter weightAdapter;
    private PersonalRecordAdapter prAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        initDaos();
        loadProgressData();

        btnLogWeight.setOnClickListener(v -> showLogWeightDialog());
    }

    @Override
    public void onResume() {
        super.onResume();
        loadProgressData();
    }

    private void initViews(View view) {
        tvTotalWorkouts = view.findViewById(R.id.tv_total_workouts);
        tvCurrentWeight = view.findViewById(R.id.tv_current_weight);
        tvWeeklyWorkouts = view.findViewById(R.id.tv_weekly_workouts);
        tvChartEmpty = view.findViewById(R.id.tv_chart_empty);
        chartWeight = view.findViewById(R.id.chart_weight);
        btnLogWeight = view.findViewById(R.id.btn_log_weight);
        recyclerWeights = view.findViewById(R.id.recycler_weights);
        recyclerRecords = view.findViewById(R.id.recycler_records);

        recyclerWeights.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerRecords.setLayoutManager(new LinearLayoutManager(getContext()));

        AnimationUtilsHelper.setupTouchBounce(btnLogWeight);
    }

    private void initDaos() {
        weightDao = new WeightDao(requireContext());
        sessionDao = new WorkoutSessionDao(requireContext());
        prDao = new PersonalRecordDao(requireContext());
        userDao = new UserDao(requireContext());
    }

    private void loadProgressData() {
        // Workouts count
        int totalSessions = sessionDao.getTotalSessionCount();
        int weeklySessions = sessionDao.getWeeklySessionCount();
        tvTotalWorkouts.setText(String.valueOf(totalSessions));
        tvWeeklyWorkouts.setText(String.valueOf(weeklySessions));

        // Weight
        WeightEntry latestWeight = weightDao.getLatestWeight();
        if (latestWeight != null) {
            tvCurrentWeight.setText(String.format(Locale.getDefault(), "%.1f kg", latestWeight.getWeight()));
        } else {
            User u = userDao.getUser();
            if (u != null && u.getWeight() > 0) {
                tvCurrentWeight.setText(String.format(Locale.getDefault(), "%.1f kg", u.getWeight()));
            } else {
                tvCurrentWeight.setText("-- kg");
            }
        }

        // Weight entries list & chart
        List<WeightEntry> weightList = weightDao.getAllWeightEntries();
        weightAdapter = new WeightEntryAdapter(getContext(), weightList);
        weightAdapter.setOnItemLongClickListener((entry, position) -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Delete Entry")
                    .setMessage("Delete weight entry of " + entry.getWeight() + " kg?")
                    .setPositiveButton("DELETE", (dialog, which) -> {
                        weightDao.deleteWeightEntry(entry.getId());
                        loadProgressData();
                    })
                    .setNegativeButton("CANCEL", null)
                    .show();
        });
        recyclerWeights.setAdapter(weightAdapter);

        setupChart(weightList);

        // Personal Records
        List<PersonalRecord> prs = prDao.getAllRecords();
        prAdapter = new PersonalRecordAdapter(getContext(), prs);
        recyclerRecords.setAdapter(prAdapter);
    }

    private void setupChart(List<WeightEntry> weightList) {
        if (weightList == null || weightList.isEmpty()) {
            chartWeight.setVisibility(View.GONE);
            tvChartEmpty.setVisibility(View.VISIBLE);
            return;
        }

        chartWeight.setVisibility(View.VISIBLE);
        tvChartEmpty.setVisibility(View.GONE);

        // Sort chronologically for chart
        List<WeightEntry> sortedList = new ArrayList<>(weightList);
        Collections.reverse(sortedList);

        List<Entry> chartEntries = new ArrayList<>();
        for (int i = 0; i < sortedList.size(); i++) {
            chartEntries.add(new Entry(i, sortedList.get(i).getWeight()));
        }

        int accentColor = ContextCompat.getColor(requireContext(), R.color.color_accent);

        LineDataSet dataSet = new LineDataSet(chartEntries, "Body Weight (kg)");
        dataSet.setColor(accentColor);
        dataSet.setCircleColor(accentColor);
        dataSet.setLineWidth(2.5f);
        dataSet.setCircleRadius(4f);
        dataSet.setDrawCircleHole(false);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(9f);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        LineData lineData = new LineData(dataSet);
        chartWeight.setData(lineData);
        chartWeight.getDescription().setEnabled(false);
        chartWeight.getLegend().setTextColor(Color.WHITE);
        chartWeight.getAxisRight().setEnabled(false);
        chartWeight.getAxisLeft().setTextColor(Color.LTGRAY);
        chartWeight.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chartWeight.getXAxis().setTextColor(Color.LTGRAY);
        chartWeight.getXAxis().setDrawGridLines(false);
        chartWeight.animateX(800);
        chartWeight.invalidate();
    }

    private void showLogWeightDialog() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_log_weight, null);
        TextInputEditText etWeight = dialogView.findViewById(R.id.et_dialog_weight);
        TextInputEditText etNotes = dialogView.findViewById(R.id.et_dialog_notes);
        MaterialButton btnCancel = dialogView.findViewById(R.id.btn_dialog_cancel);
        MaterialButton btnSave = dialogView.findViewById(R.id.btn_dialog_save);

        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        btnSave.setOnClickListener(v -> {
            String wtStr = etWeight.getText() != null ? etWeight.getText().toString().trim() : "";
            if (wtStr.isEmpty()) {
                etWeight.setError("Please enter weight");
                return;
            }

            try {
                float weight = Float.parseFloat(wtStr);
                String notes = etNotes.getText() != null ? etNotes.getText().toString().trim() : "";
                weightDao.addWeightEntry(new WeightEntry(weight, System.currentTimeMillis(), notes));
                userDao.updateUserWeight(weight);
                Toast.makeText(getContext(), "Weight logged: " + weight + " kg", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                loadProgressData();
            } catch (NumberFormatException e) {
                etWeight.setError("Invalid number");
            }
        });

        dialog.show();
    }
}
