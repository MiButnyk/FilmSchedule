package com.example.kursovaya.dbclasses;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Film.class, Details.class}, version = 10)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FilmDao filmDao();
    public abstract DetailsDao detailsDao();
}
