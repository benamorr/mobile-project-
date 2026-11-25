package com.ironfit.app.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.ironfit.app.R;
import com.ironfit.app.models.Exercise;
import com.ironfit.app.models.ExerciseSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SessionExerciseAdapter extends RecyclerView.Adapter<SessionExerciseAdapter.ViewHolder> {

    public static class ExerciseEntry {
        public Exercise exercise;
        public List<ExerciseSet> sets;

        public ExerciseEntry(Exercise exercise) {
            this.exercise = exercise;
            this.sets = new ArrayList<>();
            // Start with 1 set
            this.sets.add(new ExerciseSet(0, exercise.getId(), exercise.getName(), 1, 10, 60.0f, false));
        }
    }

    public interface OnSetActionListener {
        void onSetCompleted(Exercise exercise, ExerciseSet set);
    }

    private final Context context;
    private final List<ExerciseEntry> exerciseEntries;
    private OnSetActionListener setActionListener;

    public SessionExerciseAdapter(Context context, List<ExerciseEntry> exerciseEntries) {
        this.context = context;
        this.exerciseEntries = exerciseEntries;
    }

    public void setOnSetActionListener(OnSetActionListener listener) {
        this.setActionListener = listener;
    }

    public List<ExerciseSet> getAllCompletedSets() {
        List<ExerciseSet> completedSets = new ArrayList<>();
        for (ExerciseEntry entry : exerciseEntries) {
            for (ExerciseSet s : entry.sets) {
                if (s.isCompleted()) {
                    completedSets.add(s);
                }
            }
        }
        return completedSets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_session_exercise, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExerciseEntry entry = exerciseEntries.get(position);
        holder.tvName.setText(entry.exercise.getName());
        holder.tvTarget.setText(entry.exercise.getCategory());

        holder.containerSets.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(context);

        for (int i = 0; i < entry.sets.size(); i++) {
            ExerciseSet set = entry.sets.get(i);
            View rowView = inflater.inflate(R.layout.item_set_row, holder.containerSets, false);

            TextView tvSetNumber = rowView.findViewById(R.id.tv_set_number);
            EditText etWeight = rowView.findViewById(R.id.et_weight);
            EditText etReps = rowView.findViewById(R.id.et_reps);
            ImageButton btnComplete = rowView.findViewById(R.id.btn_complete_set);
            View layoutRow = rowView.findViewById(R.id.layout_set_row);

            tvSetNumber.setText(String.valueOf(set.getSetNumber()));
            etWeight.setText(String.format(Locale.getDefault(), "%.1f", set.getWeightKg()));
            etReps.setText(String.valueOf(set.getReps()));

            updateSetRowUi(layoutRow, btnComplete, set.isCompleted());

            etWeight.addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        set.setWeightKg(Float.parseFloat(s.toString()));
                    } catch (Exception ignored) {}
                }
            });

            etReps.addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        set.setReps(Integer.parseInt(s.toString()));
                    } catch (Exception ignored) {}
                }
            });

            btnComplete.setOnClickListener(v -> {
                boolean newState = !set.isCompleted();
                set.setCompleted(newState);
                updateSetRowUi(layoutRow, btnComplete, newState);

                if (newState && setActionListener != null) {
                    setActionListener.onSetCompleted(entry.exercise, set);
                }
            });

            holder.containerSets.addView(rowView);
        }

        holder.btnAddSet.setOnClickListener(v -> {
            int nextSetNum = entry.sets.size() + 1;
            float prevWeight = entry.sets.isEmpty() ? 60.0f : entry.sets.get(entry.sets.size() - 1).getWeightKg();
            int prevReps = entry.sets.isEmpty() ? 10 : entry.sets.get(entry.sets.size() - 1).getReps();

            entry.sets.add(new ExerciseSet(0, entry.exercise.getId(), entry.exercise.getName(), nextSetNum, prevReps, prevWeight, false));
            notifyItemChanged(holder.getAdapterPosition());
        });
    }

    private void updateSetRowUi(View rowLayout, ImageButton btnComplete, boolean completed) {
        if (completed) {
            rowLayout.setBackgroundResource(R.drawable.bg_set_item_done);
            btnComplete.setColorFilter(ContextCompat.getColor(context, R.color.color_success));
            btnComplete.setBackgroundResource(R.drawable.bg_chip_selected);
        } else {
            rowLayout.setBackgroundResource(R.drawable.bg_set_item);
            btnComplete.setColorFilter(ContextCompat.getColor(context, R.color.color_text_secondary));
            btnComplete.setBackgroundResource(R.drawable.bg_chip);
        }
    }

    @Override
    public int getItemCount() {
        return exerciseEntries != null ? exerciseEntries.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvTarget;
        LinearLayout containerSets;
        MaterialButton btnAddSet;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_session_ex_name);
            tvTarget = itemView.findViewById(R.id.tv_session_ex_target);
            containerSets = itemView.findViewById(R.id.container_sets);
            btnAddSet = itemView.findViewById(R.id.btn_add_set);
        }
    }
}
