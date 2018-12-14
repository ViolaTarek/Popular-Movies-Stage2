package com.example.viola.movies.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.viola.movies.Movie;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {
    private FavoriteRep FavRep;
    private LiveData<List<Movie>> allFavorites;

    public FavoriteViewModel( Application application) {
        super(application);
        FavRep = new FavoriteRep(application);
        allFavorites = FavRep.getAllFavorites();
    }
    public LiveData<List<Movie>> getAllFavorites() {
        return allFavorites;
    }

    public void insert(Movie MovRes) {
        FavRep.insert(MovRes);
    }

    public void deleteAllFavorites() {
        FavRep.deleteAllFavorites();
    }

}
