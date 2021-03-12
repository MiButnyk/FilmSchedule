package com.example.kursovaya.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.kursovaya.dbclasses.Details;
import com.example.kursovaya.dbclasses.Film;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Repository {
    private Executor executor = Executors.newSingleThreadExecutor();
    private LocalDataSource localDataSource;
    private RemoteDataSource remoteDataSource;

    public Repository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public RemoteDataSource getRemoteDataSource() {
        return this.remoteDataSource;
    }

    public LiveData<List<Film>> getSearch(String query) {
        remoteDataSource.setQuery(query);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    localDataSource.putData(query, remoteDataSource.fetchData());
                } catch (IOException e) {
                    Log.d("Oshibka", "IOexception refreshData");
                }
            }
        });
        return localDataSource.getFilms(query);
    }

    public LiveData<Details> getDetails(int id) {
        remoteDataSource.setId(id);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    localDataSource.putData(remoteDataSource.fetchFilmDetails());
                } catch (IOException e) {
                    Log.d("Oshibka", "IOexception refreshCredits");
                }
            }
        });
        return localDataSource.getDetails(id);
    }

}
