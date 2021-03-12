package com.example.kursovaya.dbclasses;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.kursovaya.pojo.FilmDetails;

import java.util.List;

@Dao
public interface DetailsDao {
    @Query("SELECT * FROM Details")
    LiveData<List<Details>> getAll();

    @Query("SELECT * FROM Details WHERE film_id = :filmId")
    LiveData<Details> getDetailsByFilmId(int filmId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Details... details);

    @Query("DELETE FROM Details")
    void deleteAll();
}
