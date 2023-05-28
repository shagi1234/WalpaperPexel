package com.merw_okuw_merkezi.walpaperhd;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static String authKeyPexels = "pOLJJTO1Qg6ECPmq9oPr0P562xSoIvZAbLGgqf5b3Wr7kSJbZSaU3aB4";
    private RecyclerView recyclerView;
    private EditText edtSearch;
    private ArrayList<Wallpaper> wallpapers = new ArrayList<>();
    private int perPage = 20;
    private String search = "";
    private AdapterWallpapers adapterWallpapers;
    private boolean isLastPage;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        setRecycler();
        getImages();
        initListeners();

    }

    private void initListeners() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (lm == null) return;
                int visibleItemCount = lm.getChildCount();
                int totalItemCount = lm.getItemCount();
                int pastVisibleItems = lm.findFirstVisibleItemPosition();

                if ((visibleItemCount + pastVisibleItems) >= totalItemCount && !isLastPage) {
                    page++;
                    getImages();
                }
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search = edtSearch.getText().toString().trim().toLowerCase();
                getImages();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void getImages() {
        if (!Objects.equals(search, "")) {
            Service request = (Service) RetrofitClient.createRequest(Service.class);
            Call<ResponseGetPhotos> call = request.searchPhoto(authKeyPexels, page, perPage, search);
            call.enqueue(new Callback<ResponseGetPhotos>() {
                @Override
                public void onResponse(Call<ResponseGetPhotos> call, Response<ResponseGetPhotos> response) {
                    if (response.code() == 200 && response.body() != null) {
                        if (page == 1) {
                            wallpapers.clear();
                        }

                        wallpapers.addAll(response.body().getPhotos());
                        adapterWallpapers.setImageList(wallpapers);
                    }
                }

                @Override
                public void onFailure(Call<ResponseGetPhotos> call, Throwable t) {

                }
            });
            return;
        }
        Service request = (Service) RetrofitClient.createRequest(Service.class);
        Call<ResponseGetPhotos> call = request.getPhotos(authKeyPexels, page, perPage);
        call.enqueue(new Callback<ResponseGetPhotos>() {
            @Override
            public void onResponse(Call<ResponseGetPhotos> call, Response<ResponseGetPhotos> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (page == 1) {
                        wallpapers.clear();
                    }

                    wallpapers.addAll(response.body().getPhotos());
                    adapterWallpapers.setImageList(wallpapers);
                }
            }

            @Override
            public void onFailure(Call<ResponseGetPhotos> call, Throwable t) {

            }
        });
    }

    private void initComponents() {
        recyclerView = findViewById(R.id.rec_wallpapers);
        edtSearch = findViewById(R.id.edt_search);
    }

    private void setRecycler() {
        adapterWallpapers = new AdapterWallpapers(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapterWallpapers);
    }
}