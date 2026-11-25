package com.ironfit.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ironfit.app.R;
import com.ironfit.app.models.Exercise;
import com.ironfit.app.utils.AnimationUtilsHelper;

import java.util.ArrayList;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    private final Context context;
    private List<Exercise> exercises;
    private List<Exercise> fullList; // backup for filtering

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Exercise exercise);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ExerciseAdapter(Context context, List<Exercise> exercises) {
        this.context = context;
        this.exercises = new ArrayList<>(exercises);
        this.fullList = new ArrayList<>(exercises);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_exercise_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);

        holder.tvName.setText(exercise.getName());
        holder.tvCategory.setText(exercise.getCategory());
        holder.tvTargetMuscles.setText(exercise.getTargetMuscles());
        holder.tvDifficulty.setText(exercise.getDifficulty());

        // Difficulty chip color
        int color;
        switch (exercise.getDifficulty()) {
            case "Beginner":
                color = ContextCompat.getColor(context, R.color.color_success);
                break;
            case "Advanced":
                color = ContextCompat.getColor(context, R.color.color_accent);
                break;
            default: // Intermediate
                color = ContextCompat.getColor(context, R.color.color_warning);
                break;
        }
        holder.tvDifficulty.setTextColor(color);
        if (holder.tvDifficulty.getBackground() != null) {
            holder.tvDifficulty.getBackground().setTint(color & 0x33FFFFFF);
        }

        if (holder.ivThumb != null) {
            int resId = 0;
            if (exercise.getImageUrl() != null && !exercise.getImageUrl().isEmpty()) {
                resId = context.getResources().getIdentifier(exercise.getImageUrl(), "drawable", context.getPackageName());
            }
            if (resId != 0) {
                holder.ivThumb.setImageResource(resId);
                holder.ivThumb.setScaleType(ImageView.ScaleType.CENTER_CROP);
                holder.ivThumb.clearColorFilter();
            } else {
                holder.ivThumb.setImageResource(R.drawable.ic_exercise);
                holder.ivThumb.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                holder.ivThumb.setColorFilter(ContextCompat.getColor(context, R.color.color_accent));
            }
        }

        AnimationUtilsHelper.setupTouchBounce(holder.itemView);
        AnimationUtilsHelper.animateCardEntrance(holder.itemView, position);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(exercise);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public void filterByCategory(String category) {
        exercises = new ArrayList<>();
        if (category == null || category.equalsIgnoreCase("All")) {
            exercises.addAll(fullList);
        } else {
            for (Exercise e : fullList) {
                if (e.getCategory() != null && e.getCategory().equalsIgnoreCase(category)) {
                    exercises.add(e);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void filterBySearch(String query) {
        exercises = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) {
            exercises.addAll(fullList);
        } else {
            String lower = query.toLowerCase().trim();
            for (Exercise e : fullList) {
                boolean nameMatch = e.getName() != null && e.getName().toLowerCase().contains(lower);
                boolean muscleMatch = e.getTargetMuscles() != null && e.getTargetMuscles().toLowerCase().contains(lower);
                boolean secMuscleMatch = e.getSecondaryMuscles() != null && e.getSecondaryMuscles().toLowerCase().contains(lower);
                if (nameMatch || muscleMatch || secMuscleMatch) {
                    exercises.add(e);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void updateList(List<Exercise> newList) {
        this.exercises = new ArrayList<>(newList);
        this.fullList = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvCategory;
        TextView tvTargetMuscles;
        TextView tvDifficulty;
        ImageView ivThumb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_exercise_name);
            tvCategory = itemView.findViewById(R.id.tv_exercise_category);
            tvTargetMuscles = itemView.findViewById(R.id.tv_target_muscles);
            tvDifficulty = itemView.findViewById(R.id.tv_difficulty);
            ivThumb = itemView.findViewById(R.id.iv_exercise_thumb);
        }
    }
}
