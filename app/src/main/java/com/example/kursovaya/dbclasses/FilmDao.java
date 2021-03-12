package com.example.kursovaya.dbclasses;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FilmDao {
    @Query("SELECT * FROM Film")
    LiveData<List<Film>> getAll();

    @Query("SELECT * FROM Film WHERE search_key LIKE (:searchKey)")
    LiveData<List<Film>> getFilmBySearchKey(String searchKey);

    @Query("SELECT * FROM Film WHERE release_date LIKE :releaseDate")
    LiveData<List<Film>> findByReleaseDate(String releaseDate);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Film> filmList);

    @Query("DELETE FROM Film")
    void deleteAll();
}
