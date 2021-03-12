package com.example.kursovaya.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.kursovaya.support_classes.App;
import com.example.kursovaya.dbclasses.Film;

import java.util.List;

public class SearchViewModel extends AndroidViewModel {

    private LiveData<List<Film>> search;

    public SearchViewModel(@NonNull Application app){
        super(app);
    }

    public LiveData<List<Film>> getSearch(String query){
        App app = getApplication();
        search = app.getRepository().getSearch(query);
        return search;
    }

}
