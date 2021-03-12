package com.example.kursovaya.repository;

import androidx.lifecycle.LiveData;

import com.example.kursovaya.support_classes.App;
import com.example.kursovaya.dbclasses.AppDatabase;
import com.example.kursovaya.dbclasses.*;
import com.example.kursovaya.pojo.*;

import java.util.ArrayList;
import java.util.List;

public class LocalDataSource {
    private AppDatabase db = App.getInstance().getDatabase();
    private FilmDao filmDao = db.filmDao();
    private DetailsDao detailsDao = db.detailsDao();

    public LiveData<List<Film>> getFilms(String query){
        return filmDao.getFilmBySearchKey(query);
    }
    public LiveData<Details> getDetails(int id){ return detailsDao.getDetailsByFilmId(id);}

    public void putData(String search_key, DataModel data){
        List<Result> results = data.getResults();
        List<Film> insertList = new ArrayList<>();
        for(Result r: results){
            insertList.add(new Film(r.getId(), r.getTitle(), r.getReleaseDate(), r.getOverview(), r.getVoteAverage(), search_key, r.getPosterPath()));
        }
        filmDao.insertAll(insertList);
    }

    public void putData(FilmDetails data){
        List<String> genres = new ArrayList<>();
        for(Genre g: data.getGenres()){
            genres.add(g.getName());
        }
        List<String> companies = new ArrayList<>();
        for(ProductionCompany p: data.getProductionCompanies()){
            companies.add(p.getName());
        }
        detailsDao.insertAll(new Details(data.getImdbId(), data.getId(), data.getOriginalTitle(), data.getTitle(), data.getBudget(), genres, companies,
                data.getOverview(), data.getPopularity(), data.getReleaseDate(), data.getRevenue(), data.getVoteAverage(), data.getCredits().getCast(), data.getPosterPath()));
    }
}
