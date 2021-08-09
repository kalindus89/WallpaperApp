package com.wllpaperapp.api;


import com.wllpaperapp.models.ImageModel;
import com.wllpaperapp.models.SearchModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {


     String API="563492ad6f9170000100000108fe32d7d67e4ea9b75a04d2fee03110";

    @Headers("Authorization: "+API)
    @GET("curated")
    Call<SearchModel> getImages(
            @Query("page") int page,
            @Query("per_page") int per_page
    );

    @Headers("Authorization: "+API)
    @GET("search")
    Call<SearchModel> getSearchImages(
            @Query("query") String query,
            @Query("page") int page,
            @Query("per_page") int per_page
    );

}
