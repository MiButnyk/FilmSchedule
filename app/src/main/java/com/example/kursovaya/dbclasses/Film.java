package com.example.kursovaya.dbclasses;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Film {

    public Film(int filmId, String title, String releaseDate, String overview, Double voteAverage, String searchKey, String posterPath){
        this.filmId = filmId;
        this.title = title;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.searchKey = searchKey;
        this.posterPath = posterPath;
    }

    @PrimaryKey
    public int filmId;

    @ColumnInfo(name="title")
    public String title;

    @ColumnInfo(name = "release_date")
    public String releaseDate;

    @ColumnInfo(name = "overview")
    public String overview;

    @ColumnInfo(name = "vote_average")
    public Double voteAverage;

    @ColumnInfo(name = "search_key")
    public String searchKey;

    @ColumnInfo(name = "poster_path")
    public String posterPath;

    @NonNull
    @Override
    public String toString() {
        return title + ": " + overview;
    }
}