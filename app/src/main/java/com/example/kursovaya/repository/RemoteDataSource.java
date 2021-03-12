package com.example.kursovaya.repository;

import com.example.kursovaya.support_classes.App;
import com.example.kursovaya.pojo.DataModel;
import com.example.kursovaya.pojo.FilmDetails;

import java.io.IOException;

public class RemoteDataSource {

    private String query = "Брат+2";
    private Integer filmId = 1;

    public DataModel fetchData() throws IOException {
        retrofit2.Response response = App.getApi().getData("26e6765f27ff56b41d21101ca9ab1603", query, "ru").execute();
        return (DataModel)response.body();
    }

    public FilmDetails fetchFilmDetails() throws IOException{
        retrofit2.Response response = App.getApi().getFilmCast(filmId, "26e6765f27ff56b41d21101ca9ab1603", "credits").execute();
        return (FilmDetails)response.body();
    }

    public void setQuery(String query){
        this.query = query;
    }
    public void setId(Integer filmId){
        this.filmId = filmId;
    }
}
