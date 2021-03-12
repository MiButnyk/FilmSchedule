package com.example.kursovaya.dbclasses;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.kursovaya.pojo.Cast;

import java.util.List;

@Entity
public class Details {

    public Details(String imdbID, int filmId, String originalTitle, String title, Integer budget,
                   List<String> genres, List<String> companies, String overview,
                   Double popularity, String releaseDate, Integer revenue, Double voteAverage, List<Cast> cast, String posterPath) {
        this.imdbID = imdbID;
        this.filmId = filmId;
        this.originalTitle = originalTitle;
        this.title = title;
        this.budget = budget;
        this.genres = genres;
        this.companies = companies;
        this.overview = overview;
        this.popularity = popularity;
        this.releaseDate = releaseDate;
        this.revenue = revenue;
        this.voteAverage = voteAverage;
        this.cast = cast;
        this.posterPath = posterPath;
    }

    @PrimaryKey
    @NonNull
    public String imdbID;

    @ColumnInfo(name = "film_id")
    public int filmId;

    @ColumnInfo(name = "originalTitle")
    public String originalTitle;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name="budget")
    public Integer budget;

    @ColumnInfo(name = "genre")
    @TypeConverters({GenresConverter.class})
    public List<String> genres;

    @ColumnInfo(name = "productionCompanies")
    @TypeConverters({GenresConverter.class})
    public List<String> companies;

    @ColumnInfo(name = "overview")
    public String overview;

    @ColumnInfo(name = "popularity")
    public Double popularity;

    @ColumnInfo(name = "releaseDate")
    public String releaseDate;

    @ColumnInfo(name = "revenue")
    public Integer revenue;

    @ColumnInfo(name = "voteAverage")
    public Double voteAverage;

    @ColumnInfo(name = "cast")
    @TypeConverters({CastConverter.class})
    public List<Cast> cast;

    @ColumnInfo(name = "poster_path")
    public String posterPath;

    @NonNull
    @Override
    public String toString() {
        return title + " " + voteAverage;
    }
}