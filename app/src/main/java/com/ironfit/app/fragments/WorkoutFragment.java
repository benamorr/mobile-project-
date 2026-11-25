package com.ironfit.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ironfit.app.R;
import com.ironfit.app.adapters.WorkoutPlanAdapter;
import com.ironfit.app.models.WorkoutPlan;
import com.ironfit.app.utils.PlanRepository;
import com.ironfit.app.utils.PreferencesManager;

import java.util.List;

public class WorkoutFragment extends Fragment {

    private RecyclerView recyclerView;
    private WorkoutPlanAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_plans);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<WorkoutPlan> plans = PlanRepository.getDefaultPlans();
        adapter = new WorkoutPlanAdapter(requireContext(), plans);

        PreferencesManager prefs = new PreferencesManager(requireContext());
        int currentPlanId = prefs.getCurrentPlanId();

        for (int i = 0; i < plans.size(); i++) {
            if (plans.get(i).getId() == currentPlanId) {
                adapter.setSelectedPosition(i);
                break;
            }
        }

        adapter.setOnPlanSelectedListener((plan, position) -> {
            prefs.setCurrentPlanId(plan.getId());
            prefs.setCurrentPlanName(plan.getName());
            Toast.makeText(getContext(), "Active plan updated to: " + plan.getName(), Toast.LENGTH_SHORT).show();
        });

        recyclerView.setAdapter(adapter);
    }
}
