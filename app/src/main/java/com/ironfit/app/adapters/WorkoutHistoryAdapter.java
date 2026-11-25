package com.ironfit.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ironfit.app.R;
import com.ironfit.app.models.WorkoutSession;
import com.ironfit.app.utils.DateUtils;

import java.util.List;
import java.util.Locale;

public class WorkoutHistoryAdapter extends RecyclerView.Adapter<WorkoutHistoryAdapter.ViewHolder> {

    private final Context context;
    private final List<WorkoutSession> sessions;

    public WorkoutHistoryAdapter(Context context, List<WorkoutSession> sessions) {
        this.context = context;
        this.sessions = sessions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_workout_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WorkoutSession session = sessions.get(position);
        holder.tvName.setText(session.getName());
        holder.tvDate.setText(DateUtils.formatDate(session.getStartTime()));
        holder.tvDuration.setText(DateUtils.formatDuration(session.getDurationSeconds()));

        int setCount = (session.getSets() != null) ? session.getSets().size() : 0;
        holder.tvSets.setText(String.format(Locale.getDefault(), "%d sets", setCount));
    }

    @Override
    public int getItemCount() {
        return sessions != null ? sessions.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvDate;
        TextView tvDuration;
        TextView tvSets;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_history_name);
            tvDate = itemView.findViewById(R.id.tv_history_date);
            tvDuration = itemView.findViewById(R.id.tv_history_duration);
            tvSets = itemView.findViewById(R.id.tv_history_sets);
        }
    }
}
