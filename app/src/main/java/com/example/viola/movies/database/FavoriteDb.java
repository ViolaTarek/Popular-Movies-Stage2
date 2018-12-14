package com.example.viola.movies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.viola.movies.Movie;

@Database(entities = {Movie.class}, version = 2, exportSchema = false)

public abstract class FavoriteDb extends RoomDatabase {

    private static FavoriteDb instance;

    public static FavoriteDb getInstance(Context context) {

        if (instance == null) {
            synchronized (FavoriteDb.class) {

                instance = Room.databaseBuilder(context.getApplicationContext(),
                    FavoriteDb.class, "favorites_database.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }}
        return instance;
    }

    public abstract Favorite favoritesDao();
}


