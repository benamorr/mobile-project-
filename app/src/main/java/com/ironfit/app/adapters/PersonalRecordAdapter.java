package com.ironfit.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ironfit.app.R;
import com.ironfit.app.models.PersonalRecord;
import com.ironfit.app.utils.DateUtils;

import java.util.List;
import java.util.Locale;

public class PersonalRecordAdapter extends RecyclerView.Adapter<PersonalRecordAdapter.ViewHolder> {

    private final Context context;
    private final List<PersonalRecord> records;

    public PersonalRecordAdapter(Context context, List<PersonalRecord> records) {
        this.context = context;
        this.records = records;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_personal_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PersonalRecord record = records.get(position);
        holder.tvExercise.setText(record.getExerciseName());
        holder.tvDate.setText(DateUtils.formatDate(record.getTimestamp()));
        holder.tvWeight.setText(String.format(Locale.getDefault(), "%.1f kg", record.getMaxWeightKg()));
        holder.tvReps.setText(String.format(Locale.getDefault(), "%d reps", record.getMaxReps()));
    }

    @Override
    public int getItemCount() {
        return records != null ? records.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvExercise;
        TextView tvDate;
        TextView tvWeight;
        TextView tvReps;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvExercise = itemView.findViewById(R.id.tv_pr_exercise);
            tvDate = itemView.findViewById(R.id.tv_pr_date);
            tvWeight = itemView.findViewById(R.id.tv_pr_weight);
            tvReps = itemView.findViewById(R.id.tv_pr_reps);
        }
    }
}
