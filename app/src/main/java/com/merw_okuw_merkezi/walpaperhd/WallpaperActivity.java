package com.merw_okuw_merkezi.walpaperhd;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.IOException;

public class WallpaperActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button btnSetWallpaper;
    private Button btnDownload;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);
        getArgs();
        initComponents();
        setImage();
        initListeners();
    }

    private void initListeners() {
    btnDownload.setOnClickListener(this::downloadWallpaperEvent);
    btnSetWallpaper.setOnClickListener(this::setWallpaperEvent);
    }

    private void setImage() {
        Glide.with(this)
                .load(url)
                .placeholder(R.color.placeholder)
                .into(imageView);
    }

    private void initComponents() {
        imageView = findViewById(R.id.image);
        btnSetWallpaper = findViewById(R.id.set_wallpaper);
        btnDownload = findViewById(R.id.download);
    }

    private void getArgs() {
        if (getIntent() == null) return;
        url = getIntent().getStringExtra("image_url");
    }

    public void setWallpaperEvent(View view) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        Bitmap bitmap  = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        try {
            wallpaperManager.setBitmap(bitmap);
            Toast.makeText(this, "Wallpaper Set", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void downloadWallpaperEvent(View view) {
        DownloadManager downloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadManager.enqueue(request);
        Toast.makeText(this, "Downloading Start", Toast.LENGTH_SHORT).show();
    }
}