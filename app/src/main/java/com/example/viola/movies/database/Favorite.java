package com.example.viola.movies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.viola.movies.Movie;

import java.util.List;

@Dao
public interface Favorite {
    @Insert
    void insert(Movie results);

    @Query("DELETE FROM favorites_table")
    void deleteAllFavorites();

    @Query("SELECT * FROM favorites_table ORDER BY movie_id DESC")
    LiveData<List<Movie>> getAllFavorites();

    @Query("DELETE FROM favorites_table WHERE movie_id = :id")
    void deleteThisMovie(int id);

    @Query("SELECT COUNT(movie_id) FROM favorites_table WHERE movie_id = :id")
    Integer ifExists(int id);


}
