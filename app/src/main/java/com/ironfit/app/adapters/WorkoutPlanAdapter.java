package com.ironfit.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ironfit.app.R;
import com.ironfit.app.models.WorkoutPlan;
import com.ironfit.app.utils.AnimationUtilsHelper;

import java.util.List;

public class WorkoutPlanAdapter extends RecyclerView.Adapter<WorkoutPlanAdapter.ViewHolder> {

    private final Context context;
    private final List<WorkoutPlan> plans;
    private int selectedPosition = -1;

    private OnPlanSelectedListener listener;

    public interface OnPlanSelectedListener {
        void onPlanSelected(WorkoutPlan plan, int position);
    }

    public void setOnPlanSelectedListener(OnPlanSelectedListener listener) {
        this.listener = listener;
    }

    public WorkoutPlanAdapter(Context context, List<WorkoutPlan> plans) {
        this.context = context;
        this.plans = plans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_workout_plan_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WorkoutPlan plan = plans.get(position);

        holder.tvPlanName.setText(plan.getName());
        holder.tvDescription.setText(plan.getDescription());
        holder.tvDaysPerWeek.setText(plan.getDaysPerWeek() + " days/week");
        holder.tvLevel.setText(plan.getLevel());

        // Level chip color
        int levelColor;
        switch (plan.getLevel()) {
            case "Advanced":
                levelColor = ContextCompat.getColor(context, R.color.color_accent);
                break;
            case "Intermediate":
                levelColor = ContextCompat.getColor(context, R.color.color_warning);
                break;
            default: // Beginner
                levelColor = ContextCompat.getColor(context, R.color.color_success);
                break;
        }
        holder.tvLevel.setTextColor(levelColor);

        boolean isSelected = (position == selectedPosition);
        if (isSelected) {
            holder.btnSelectPlan.setText("✓ SELECTED");
            holder.btnSelectPlan.setBackgroundColor(ContextCompat.getColor(context, R.color.color_success));
        } else {
            holder.btnSelectPlan.setText("SELECT PLAN");
            holder.btnSelectPlan.setBackgroundColor(ContextCompat.getColor(context, R.color.color_accent));
        }

        AnimationUtilsHelper.setupTouchBounce(holder.itemView);
        AnimationUtilsHelper.setupTouchBounce(holder.btnSelectPlan);
        AnimationUtilsHelper.animateCardEntrance(holder.itemView, position);

        holder.btnSelectPlan.setOnClickListener(v -> {
            int prev = selectedPosition;
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(prev);
            notifyItemChanged(selectedPosition);
            if (listener != null) {
                listener.onPlanSelected(plan, selectedPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return plans.size();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public WorkoutPlan getSelectedPlan() {
        if (selectedPosition >= 0 && selectedPosition < plans.size()) {
            return plans.get(selectedPosition);
        }
        return null;
    }

    public void setSelectedPosition(int position) {
        int prev = selectedPosition;
        selectedPosition = position;
        notifyItemChanged(prev);
        notifyItemChanged(selectedPosition);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlanName;
        TextView tvDescription;
        TextView tvLevel;
        TextView tvDaysPerWeek;
        Button btnSelectPlan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlanName = itemView.findViewById(R.id.tv_plan_name);
            tvDescription = itemView.findViewById(R.id.tv_plan_description);
            tvLevel = itemView.findViewById(R.id.tv_plan_level);
            tvDaysPerWeek = itemView.findViewById(R.id.tv_days_per_week);
            btnSelectPlan = itemView.findViewById(R.id.btn_select_plan);
        }
    }
}
