package com.example.viola.movies.UI;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.viola.movies.BuildConfig;
import com.example.viola.movies.Movie;
import com.example.viola.movies.R;
import com.example.viola.movies.adapter.MovieAdapter;
import com.example.viola.movies.api.Client;
import com.example.viola.movies.api.server;
import com.example.viola.movies.database.Favorite;
import com.example.viola.movies.database.FavoriteViewModel;
import com.example.viola.movies.Response.fetchMovies;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private List<Movie> movies;
    private FavoriteViewModel favoriteViewModel;
    Bundle mBundleRecyclerViewState;

    SharedPreferences sharedPreferences;
    String pref_key;
    String popular_key;
    String top_rated_key;
    String Favorite;
    String RECYCLER_STATE_KEY;
    public static final String TAG = MovieAdapter.class.getName();
    int flag=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref_key = getString(R.string.pref_key);
        popular_key= getString(R.string.popular_key);
        top_rated_key = getString(R.string.top_rated_key);
        Favorite = getString(R.string.favorite_key);
        RECYCLER_STATE_KEY = getString(R.string.recycler_state);
        

        recyclerView = findViewById(R.id.recycler_view);
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String pref = sharedPreferences.getString(pref_key,popular_key);
        if(pref.equals(popular_key)){
            initViews();
            loadJSON();
            //checked
        }
        else if(pref.equals(top_rated_key)){
            initViews();
            loadJSON1();
        }
        else if(pref.equals(Favorite)){
            initViews();
            getFavorite();
        }

    }


    private Activity getActivity() {
        Context c = this;
        while (c instanceof ContextWrapper){
            if(c instanceof Activity){
                return (Activity) c;
            }
            c = ((ContextWrapper) c ).getBaseContext();
        }
        return null;

    }

    private void initViews() {

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        movies = new ArrayList<>();
        adapter = new MovieAdapter(this, movies);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    //popular
    private void loadJSON() {
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()){
                Toast.makeText(getApplicationContext(), "please obtain your API_KEY first", Toast.LENGTH_SHORT).show();
            return; }

            Client client = new Client();
            server apiservice = client.getClient().create(server.class);
            Call<fetchMovies> call = apiservice.getPopularMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<fetchMovies>() {
                @Override
                public void onResponse(Call<fetchMovies> call, Response<fetchMovies> response) {
                    List<Movie> movies =response.body().getResults();
                    recyclerView.setAdapter(new MovieAdapter(getApplicationContext(),movies));
                    recyclerView.smoothScrollToPosition(0);

                }
                @Override
                public void onFailure(Call<fetchMovies> call, Throwable t) {
                    Log.d("error",t.getMessage());
                    Toast.makeText(MainActivity.this,"error fetching the movies",Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e){
            Log.d("error",e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    //to get top rated movies
    private void loadJSON1() {
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()){
                Toast.makeText(getApplicationContext(), "please obtain your API_KEY first", Toast.LENGTH_SHORT).show();
                return; }

            Client client = new Client();
            server apiservice = client.getClient().create(server.class);
            Call<fetchMovies> call = apiservice.getTopRatedMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<fetchMovies>() {
                @Override
                public void onResponse(Call<fetchMovies> call, Response<fetchMovies> response) {
                    List<Movie> movies= response.body().getResults();
                    recyclerView.setAdapter(new MovieAdapter(getApplicationContext(),movies));
                    recyclerView.smoothScrollToPosition(0);

                }
                @Override
                public void onFailure(Call<fetchMovies> call, Throwable t) {
                    Log.d("error",t.getMessage());
                    Toast.makeText(MainActivity.this,"error fetching the movies",Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e){
            Log.d("error",e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }



    private void getFavorite() {
        favoriteViewModel.getAllFavorites().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movieResults) {
                adapter= new MovieAdapter(getApplicationContext(),movieResults);
                adapter.setFavs(movieResults);
                recyclerView.setAdapter(adapter);
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case (R.id.popular_sort):
            {
                Log.d(TAG, "sort by most popular");
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(pref_key,popular_key);
                editor.putInt(RECYCLER_STATE_KEY,recyclerView.getScrollState());
                editor.apply();
                loadJSON();
                item.setChecked(true);
                flag=1;
                return true;
            }
            case(R.id.rating_sort): {
                Log.d(TAG,"sort by top rated");
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(pref_key,top_rated_key);
                editor.putInt(RECYCLER_STATE_KEY,recyclerView.getScrollState());
                editor.apply();
                loadJSON1();
                item.setChecked(true);
                flag =2 ;
                return true;
            }
            case (R.id.favorites_sorting):{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(pref_key,Favorite);
                editor.putInt(RECYCLER_STATE_KEY,recyclerView.getScrollState());
                editor.apply();
                getFavorite();
                item.setChecked(true);
                flag=3;
                return true;
            }
            default: return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = recyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(RECYCLER_STATE_KEY, listState);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(RECYCLER_STATE_KEY);
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }

}







