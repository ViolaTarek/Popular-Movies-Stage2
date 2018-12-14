package com.example.viola.movies.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.viola.movies.Movie;

import java.util.List;

public class FavoriteRep {

    Favorite favoriteDao;
    private LiveData<List<Movie>> allFavorites;

    public FavoriteRep(Application application) {
        FavoriteDb database = FavoriteDb.getInstance(application);
        favoriteDao = database.favoritesDao();
        allFavorites = favoriteDao.getAllFavorites();

    }

    public LiveData<List<Movie>> getAllFavorites() {
        return allFavorites;
    }

    public void insert(Movie fav) {
        new InsertFavoritesAsyncTask(favoriteDao).execute(fav);

    }
    private static class InsertFavoritesAsyncTask extends AsyncTask<Movie, Void, Void> {
        private Favorite AsyncDao;


        private InsertFavoritesAsyncTask(Favorite Fav) {
           this.AsyncDao = Fav;
        }

        @Override
        protected Void doInBackground(final Movie... movieResults) {
            AsyncDao.insert(movieResults[0]);
            return null;
        }
    }


    public void deleteAllFavorites() {
        new DeleteAllFavoritesAsyncTask(favoriteDao).execute();
    }

    private static class DeleteAllFavoritesAsyncTask extends AsyncTask<Void, Void, Void> {
        private Favorite mAsyncDao;

        private DeleteAllFavoritesAsyncTask(Favorite favorite) {
            mAsyncDao = favorite;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            mAsyncDao.deleteAllFavorites();
            return null;
        }
    }


}
