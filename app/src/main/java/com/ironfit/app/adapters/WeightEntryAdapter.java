package com.ironfit.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ironfit.app.R;
import com.ironfit.app.models.WeightEntry;
import com.ironfit.app.utils.DateUtils;

import java.util.List;
import java.util.Locale;

public class WeightEntryAdapter extends RecyclerView.Adapter<WeightEntryAdapter.ViewHolder> {

    private final Context context;
    private final List<WeightEntry> entries;
    private OnItemLongClickListener longClickListener;

    public interface OnItemLongClickListener {
        void onItemLongClick(WeightEntry entry, int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    public WeightEntryAdapter(Context context, List<WeightEntry> entries) {
        this.context = context;
        this.entries = entries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_weight_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeightEntry entry = entries.get(position);
        holder.tvDate.setText(DateUtils.formatDate(entry.getTimestamp()));
        holder.tvWeight.setText(String.format(Locale.getDefault(), "%.1f kg", entry.getWeight()));

        if (entry.getNotes() != null && !entry.getNotes().trim().isEmpty()) {
            holder.tvNotes.setText(entry.getNotes());
            holder.tvNotes.setVisibility(View.VISIBLE);
        } else {
            holder.tvNotes.setVisibility(View.GONE);
        }

        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onItemLongClick(entry, holder.getAdapterPosition());
                return true;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return entries != null ? entries.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvWeight;
        TextView tvNotes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_weight_date);
            tvWeight = itemView.findViewById(R.id.tv_weight_value);
            tvNotes = itemView.findViewById(R.id.tv_weight_notes);
        }
    }
}
