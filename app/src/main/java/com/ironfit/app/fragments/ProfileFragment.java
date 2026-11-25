package com.ironfit.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.ironfit.app.R;
import com.ironfit.app.activities.OnboardingActivity;
import com.ironfit.app.database.DatabaseHelper;
import com.ironfit.app.database.UserDao;
import com.ironfit.app.models.User;
import com.ironfit.app.utils.PreferencesManager;

import java.util.Locale;

public class ProfileFragment extends Fragment {

    private TextView tvAvatarInitial, tvProfileName, tvProfileExperience;
    private TextView tvProfileWeight, tvProfileHeight, tvProfileAge, tvProfileGoal;
    private MaterialButton btnResetData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        loadUserProfile();
        setupResetButton();
    }

    private void initViews(View view) {
        tvAvatarInitial = view.findViewById(R.id.tv_avatar_initial);
        tvProfileName = view.findViewById(R.id.tv_profile_name);
        tvProfileExperience = view.findViewById(R.id.tv_profile_experience);
        tvProfileWeight = view.findViewById(R.id.tv_profile_weight);
        tvProfileHeight = view.findViewById(R.id.tv_profile_height);
        tvProfileAge = view.findViewById(R.id.tv_profile_age);
        tvProfileGoal = view.findViewById(R.id.tv_profile_goal);
        btnResetData = view.findViewById(R.id.btn_reset_data);
    }

    private void loadUserProfile() {
        if (getContext() == null) return;

        UserDao userDao = new UserDao(requireContext());
        User user = userDao.getUser();

        if (user != null) {
            String name = user.getName();
            tvProfileName.setText(name);
            if (!name.isEmpty()) {
                tvAvatarInitial.setText(String.valueOf(name.charAt(0)).toUpperCase(Locale.getDefault()));
            }

            tvProfileExperience.setText(user.getExperienceLevel() + " Athlete");
            tvProfileWeight.setText(String.format(Locale.getDefault(), "%.1f kg", user.getWeight()));
            tvProfileHeight.setText(String.format(Locale.getDefault(), "%.0f cm", user.getHeight()));
            tvProfileAge.setText(user.getAge() + " yrs");
            tvProfileGoal.setText(user.getGoal());
        }
    }

    private void setupResetButton() {
        btnResetData.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Reset All Data?")
                    .setMessage("This will erase all your workout history, weight logs, and profile info permanently. Are you sure?")
                    .setPositiveButton("RESET", (dialog, which) -> {
                        DatabaseHelper db = new DatabaseHelper(requireContext());
                        requireContext().deleteDatabase(DatabaseHelper.DATABASE_NAME);

                        PreferencesManager prefs = new PreferencesManager(requireContext());
                        prefs.clearAll();

                        Toast.makeText(getContext(), "All data has been reset", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getActivity(), OnboardingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        if (getActivity() != null) {
                            getActivity().finish();
                        }
                    })
                    .setNegativeButton("CANCEL", null)
                    .show();
        });
    }
}
