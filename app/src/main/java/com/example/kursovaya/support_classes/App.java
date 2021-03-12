package com.example.kursovaya.support_classes;

import android.app.Application;

import androidx.room.Room;

import com.example.kursovaya.apis.FilmsAPI;
import com.example.kursovaya.dbclasses.AppDatabase;
import com.example.kursovaya.repository.LocalDataSource;
import com.example.kursovaya.repository.RemoteDataSource;
import com.example.kursovaya.repository.Repository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    public static App instance;
    private AppDatabase database;

    private static FilmsAPI filmsAPI;
    private Retrofit retrofit;

    private Repository repository;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database").fallbackToDestructiveMigration().build();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        filmsAPI = retrofit.create(FilmsAPI.class);

        repository = new Repository(new LocalDataSource(), new RemoteDataSource());
    }

    public static App getInstance(){
        return instance;
    }

    public AppDatabase getDatabase(){
        return database;
    }

    public static FilmsAPI getApi() {
        return filmsAPI;
    }

    public Repository getRepository() {
        return repository;
    }

}