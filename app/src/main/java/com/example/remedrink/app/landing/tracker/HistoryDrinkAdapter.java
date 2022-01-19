package com.example.remedrink.app.landing.tracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.remedrink.R;
import com.example.remedrink.datamodel.drink.MyDrinkItemResponse;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by Angelia Widjaja on 19-Jan-22 16:53.
 */
public class HistoryDrinkAdapter extends RecyclerView.Adapter<HistoryDrinkAdapter.ViewHolder> {
    private Context context;
    private List<MyDrinkItemResponse>data;

    public HistoryDrinkAdapter(Context context, List<MyDrinkItemResponse> data) {
        this.context = context;
        this.data = data;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<MyDrinkItemResponse>data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context)
        .inflate(R.layout.drink_history_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyDrinkItemResponse item = data.get(position);
        holder.imgDrinkSize.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_drink));
        holder.txtDrinkSize.setText(item.getDrinkSize().toString());
        holder.createdAt.setText(item.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgDrinkSize;
        private TextView txtDrinkSize;
        private TextView createdAt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDrinkSize = itemView.findViewById(R.id.iv_drink_size_type);
            txtDrinkSize = itemView.findViewById(R.id.tv_drink_size);
            createdAt = itemView.findViewById(R.id.tv_created_at);
        }
    }
}
