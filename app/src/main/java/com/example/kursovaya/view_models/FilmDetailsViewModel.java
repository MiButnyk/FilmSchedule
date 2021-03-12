package com.example.kursovaya.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.kursovaya.support_classes.App;
import com.example.kursovaya.dbclasses.Details;

public class FilmDetailsViewModel extends AndroidViewModel {

    private LiveData<Details> details;

    public FilmDetailsViewModel(@NonNull Application app){
        super(app);
    }

    public LiveData<Details> getDetails(int id){
        App app = getApplication();
        details = app.getRepository().getDetails(id);
        return details;
    }
}
