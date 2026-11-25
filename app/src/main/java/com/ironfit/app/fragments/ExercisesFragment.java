package com.ironfit.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.ironfit.app.R;
import com.ironfit.app.activities.ExerciseDetailActivity;
import com.ironfit.app.adapters.ExerciseAdapter;
import com.ironfit.app.database.ExerciseDao;
import com.ironfit.app.models.Exercise;

import java.util.List;

public class ExercisesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ExerciseAdapter adapter;
    private TextInputEditText etSearch;
    private ExerciseDao exerciseDao;
    private TextView[] filterChips;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exercises, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_exercises);
        etSearch = view.findViewById(R.id.et_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        exerciseDao = new ExerciseDao(requireContext());
        List<Exercise> exercises = exerciseDao.getAllExercises();

        adapter = new ExerciseAdapter(requireContext(), exercises);
        adapter.setOnItemClickListener(exercise -> {
            Intent intent = new Intent(getActivity(), ExerciseDetailActivity.class);
            intent.putExtra(ExerciseDetailActivity.EXTRA_EXERCISE_ID, exercise.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        setupSearch();
        setupFilters(view);
    }

    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (adapter != null) {
                    adapter.filterBySearch(s.toString());
                }
            }
        });
    }

    private void setupFilters(View view) {
        TextView filterAll = view.findViewById(R.id.filter_all);
        TextView filterChest = view.findViewById(R.id.filter_chest);
        TextView filterBack = view.findViewById(R.id.filter_back);
        TextView filterShoulders = view.findViewById(R.id.filter_shoulders);
        TextView filterLegs = view.findViewById(R.id.filter_legs);
        TextView filterArms = view.findViewById(R.id.filter_arms);

        filterChips = new TextView[]{filterAll, filterChest, filterBack, filterShoulders, filterLegs, filterArms};

        setFilterClick(filterAll, "All");
        setFilterClick(filterChest, "Chest");
        setFilterClick(filterBack, "Back");
        setFilterClick(filterShoulders, "Shoulders");
        setFilterClick(filterLegs, "Legs");
        setFilterClick(filterArms, "Biceps"); // Or arms
    }

    private void setFilterClick(TextView chip, String category) {
        if (chip == null) return;
        chip.setOnClickListener(v -> {
            for (TextView c : filterChips) {
                if (c != null) {
                    c.setBackgroundResource(R.drawable.bg_chip);
                }
            }
            chip.setBackgroundResource(R.drawable.bg_chip_selected);
            if (adapter != null) {
                adapter.filterByCategory(category);
            }
        });
    }
}
