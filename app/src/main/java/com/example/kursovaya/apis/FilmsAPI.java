package com.example.kursovaya.apis;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import com.example.kursovaya.pojo.*;

public interface FilmsAPI {
    @GET("/3/search/movie")
    Call<DataModel> getData(@Query("api_key") String apikey, @Query("query") String query, @Query("language") String language);
    @GET("/3/movie/{movie_id}")
    Call<FilmDetails> getFilmCast(@Path("movie_id") Integer id, @Query("api_key") String apikey, @Query("append_to_response") String appendToResponse);
}
