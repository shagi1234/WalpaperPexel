package com.merw_okuw_merkezi.walpaperhd;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterWallpapers extends RecyclerView.Adapter<AdapterWallpapers.WallpaperHolder> {
    private Activity activity;
    private ArrayList<Wallpaper> imageList = new ArrayList<>();

    public AdapterWallpapers(Activity mainActivity) {
        this.activity = mainActivity;
    }

    @NonNull
    @Override
    public AdapterWallpapers.WallpaperHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_grid_wallpaper, parent, false);
        return new AdapterWallpapers.WallpaperHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterWallpapers.WallpaperHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class WallpaperHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public WallpaperHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }

        public void bind() {
            //suratyn hilini utgedip bolyar .getMedium() shuny uytgedip

            Glide.with(activity)
                    .load(imageList.get(getAdapterPosition()).getSrc().getMedium())
                    .placeholder(R.color.placeholder)
                    .into(imageView);

            itemView.setOnClickListener(v -> {
                Intent i = new Intent(activity, WallpaperActivity.class);
                i.putExtra("image_url", imageList.get(getAdapterPosition()).getSrc().getPortrait());
                activity.startActivity(i);
            });
        }
    }

    public void setImageList(ArrayList<Wallpaper> imageList) {
        this.imageList = imageList;
        notifyDataSetChanged();
    }
}
