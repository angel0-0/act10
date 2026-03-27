package com.angel.act10;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private final Context context;
    private final List<Integer> imageIds = new ArrayList<>();
    private final String[] titles;
    private final String[] descriptions;

    public ImageAdapter(Context context) {
        this.context = context;
        Resources res = context.getResources();

        // Load image resources
        for (int i = 1; i <= 12; i++) {
            int id = res.getIdentifier("k" + i, "drawable", context.getPackageName());
            if (id != 0) {
                imageIds.add(id);
            }
        }

        // Load string arrays for titles and descriptions
        titles = res.getStringArray(R.array.image_titles);
        descriptions = res.getStringArray(R.array.image_descriptions);
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.imageView.setImageResource(imageIds.get(position));

        // Set title and description from arrays if they exist for the current position
        if (titles != null && titles.length > position) {
            holder.titleTextView.setText(titles[position]);
        }
        if (descriptions != null && descriptions.length > position) {
            holder.descriptionTextView.setText(descriptions[position]);
        }
    }

    @Override
    public int getItemCount() {
        return imageIds.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView descriptionTextView;

        ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }
    }
}
