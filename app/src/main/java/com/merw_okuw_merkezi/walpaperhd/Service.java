package com.merw_okuw_merkezi.walpaperhd;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface Service {
    @GET("/v1/curated/")
    Call<ResponseGetPhotos> getPhotos(@Header("Authorization") String auth, @Query("page") int page, @Query("per_page") int perPage);

    @GET("/v1/search/")
    Call<ResponseGetPhotos> searchPhoto(@Header("Authorization") String auth, @Query("page") int page, @Query("per_page") int perPage, @Query("query") String search);

}
