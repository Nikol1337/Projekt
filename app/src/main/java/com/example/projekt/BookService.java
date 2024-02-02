package com.example.projekt;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookService {
    // ...
    @GET("dictionary")
    Call<List<ApiResponse>> findBooks(@Query("l") String language, @Query("q") String query);
    // ...
}

