package com.example.asus.subthreemvvm.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MovieDAO {

    @Insert
    void insertMovie(MovieModelDb movieModelDb);

    @Query("SELECT * FROM favorite_movie")
    List<MovieModelDb> getMovieDb();

    @Delete
    void deleteMovie(MovieModelDb movieModelDb);

    @Query("SELECT * FROM favorite_movie WHERE id = :idMovie LIMIT 1")
    List<MovieModelDb> getById(int idMovie);

}
