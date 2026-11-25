package com.ironfit.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {

    private static final String PREF_NAME = "ironfit_preferences";
    private static final String KEY_ONBOARDING_COMPLETED = "key_onboarding_completed";
    private static final String KEY_CURRENT_PLAN_ID = "key_current_plan_id";
    private static final String KEY_CURRENT_PLAN_NAME = "key_current_plan_name";

    private final SharedPreferences prefs;

    public PreferencesManager(Context context) {
        this.prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean isOnboardingCompleted() {
        return prefs.getBoolean(KEY_ONBOARDING_COMPLETED, false);
    }

    public void setOnboardingCompleted(boolean completed) {
        prefs.edit().putBoolean(KEY_ONBOARDING_COMPLETED, completed).apply();
    }

    public int getCurrentPlanId() {
        return prefs.getInt(KEY_CURRENT_PLAN_ID, 1);
    }

    public void setCurrentPlanId(int planId) {
        prefs.edit().putInt(KEY_CURRENT_PLAN_ID, planId).apply();
    }

    public String getCurrentPlanName() {
        return prefs.getString(KEY_CURRENT_PLAN_NAME, "Push Pull Legs (PPL)");
    }

    public void setCurrentPlanName(String planName) {
        prefs.edit().putString(KEY_CURRENT_PLAN_NAME, planName).apply();
    }

    public void clearAll() {
        prefs.edit().clear().apply();
    }
}
