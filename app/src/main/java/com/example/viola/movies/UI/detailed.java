package com.example.viola.movies.UI;

        import android.annotation.SuppressLint;
        import android.arch.lifecycle.ViewModelProviders;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.support.annotation.NonNull;
        import android.support.design.widget.FloatingActionButton;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.RatingBar;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.viola.movies.ApplicationExecuter;
        import com.example.viola.movies.BuildConfig;
        import com.example.viola.movies.Movie;
        import com.example.viola.movies.R;
        import com.example.viola.movies.Reviews;
        import com.example.viola.movies.Trailer;
        import com.example.viola.movies.adapter.ReviewAdapter;
        import com.example.viola.movies.adapter.TrailerAdapter;
        import com.example.viola.movies.api.Client;
        import com.example.viola.movies.api.server;
        import com.example.viola.movies.database.FavoriteDb;
        import com.example.viola.movies.database.FavoriteViewModel;
        import com.example.viola.movies.Response.fetchReviews;
        import com.example.viola.movies.Response.fetchTrailer;
        import com.squareup.picasso.Picasso;

        import java.util.ArrayList;
        import java.util.List;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

public class detailed extends AppCompatActivity {
    public TextView movieName , overview,releaseDate;
    public ImageView imageView;
    public RatingBar ratingText;
    FloatingActionButton favorite;
    boolean checkMovieInFavorites;
    int movie_id;
    Double rate;
    String posterPath,overView,originalTitle,DateOfRelease;
    String backdrop_path;

    private List<Trailer> mTrailerResults;
    private TrailerAdapter mTrailAdapter;
    private RecyclerView mTrailerRecyclerView;
    private FavoriteDb mDatabase;
    private FavoriteViewModel mFavVMod;
    private Movie CurrentMovie;
    private final AppCompatActivity activity = detailed.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        imageView = findViewById(R.id.backdrop_poster);
        movieName = findViewById(R.id.movie_title);
        overview = findViewById(R.id.overView_tv);
        releaseDate = findViewById(R.id.release_date_tv);
        ratingText = findViewById(R.id.user_rating_tv);
        favorite=findViewById(R.id.fab);

        Intent intent = getIntent();
        originalTitle=getIntent().getExtras().getString("original_title");
        posterPath=getIntent().getExtras().getString("poster_path");
        overView=getIntent().getExtras().getString("over_view");
        rate= Double.valueOf(getIntent().getExtras().getString("vote_average"));
        DateOfRelease=getIntent().getExtras().getString("release_date");
        backdrop_path=getIntent().getExtras().getString("backdrop_path");
        movie_id=getIntent().getExtras().getInt("id");

        CurrentMovie= new Movie(posterPath,movie_id,overView,DateOfRelease,originalTitle,rate,backdrop_path);

            movieName.setText(originalTitle);
            overview.setText(overView);
            releaseDate.setText(DateOfRelease);
            ratingText.setRating((rate.byteValue()) / 2);
            Picasso.with(this).load("http://image.tmdb.org/t/p/w200"+ backdrop_path).into(imageView);


        mDatabase = FavoriteDb.getInstance(getApplicationContext());
        mFavVMod = ViewModelProviders.of(this).get(FavoriteViewModel.class);

      checkIfExists flag = new checkIfExists();
        flag.execute();
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkMovieInFavorites){
                    ApplicationExecuter.getsInstance().getDiskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mDatabase.favoritesDao().deleteThisMovie(movie_id);

                        }
                    });
                    favorite.setImageResource(R.drawable.ic_favorite);
                    checkMovieInFavorites = false;
                    Toast.makeText(getApplicationContext(), "Deleted " + originalTitle + " from Favorites", Toast.LENGTH_SHORT).show();
                } else {
                    mFavVMod.insert(CurrentMovie);
                    favorite.setImageResource(R.drawable.ic_favorite_filled);
                    checkMovieInFavorites = true;
                    Toast.makeText(getApplicationContext(), "Added " + movieName.getText().toString() + " to Favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });
        initView();
    }



    private void initView(){

        mTrailerResults = new ArrayList<>();
        mTrailAdapter = new TrailerAdapter(mTrailerResults,this);

        mTrailerRecyclerView = findViewById(R.id.trailer_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mTrailerRecyclerView.setLayoutManager(layoutManager);
        mTrailerRecyclerView.setAdapter(mTrailAdapter);
        mTrailAdapter.notifyDataSetChanged();

        LoadJson();
        loadReview();
    }

    private void LoadJson() {
        int movie_id = getIntent().getExtras().getInt("id");
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(getApplicationContext(), "please obtain your API_KEY first", Toast.LENGTH_SHORT).show();
                return;
            }
            Client client = new Client();
            server apiService = client.getClient().create(server.class);
            Call<fetchTrailer> call = apiService.getMovieTrailer(movie_id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<fetchTrailer>() {
                @Override
                public void onResponse(Call<fetchTrailer> call, Response<fetchTrailer> response) {
                    List<Trailer> trailers = response.body().getResults();
                    mTrailerRecyclerView.setAdapter(new TrailerAdapter(trailers, getApplicationContext()));
                    mTrailerRecyclerView.smoothScrollToPosition(0);
                }

                @Override
                public void onFailure(Call<fetchTrailer> call, Throwable t) {
                    Log.d("error", t.getMessage());
                    Toast.makeText(detailed.this, "error fetching the trailers", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e){
            Log.d("error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private void loadReview(){
        int movie_id = getIntent().getExtras().getInt("id");

        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please get your API Key", Toast.LENGTH_SHORT).show();
                return;
            }

            else {
                Client Client = new Client();
                server apiService = Client.getClient().create(server.class);
                Call<Reviews> call = apiService.getReview(movie_id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
                call.enqueue(new Callback<Reviews>() {
                    @Override
                    public void onResponse(@NonNull Call<Reviews> call, Response<Reviews> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null){
                                List<fetchReviews> reviewResults = response.body().getResults();
                                RecyclerView recyclerView2 =  findViewById(R.id.review_Recycler_view);
                                LinearLayoutManager firstManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                                recyclerView2.setLayoutManager(firstManager);
                                recyclerView2.setAdapter(new ReviewAdapter(getApplicationContext(), reviewResults));
                                recyclerView2.smoothScrollToPosition(0);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Reviews> call, Throwable t) {

                    }
                });
            }

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(this, "unable to fetch data"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("StaticFieldLeak")
    protected class checkIfExists extends AsyncTask<Integer, Integer, Boolean> {

      @Override
        protected Boolean doInBackground(Integer... integers) {
            Boolean checkResult;
            Integer checkMovieId = mDatabase.favoritesDao().ifExists(CurrentMovie.getMovie_id());
           checkResult = checkMovieId > 0;
            return checkResult;
        }


        @Override
        protected void onPostExecute(Boolean checkResult) {
           checkMovieInFavorites = checkResult;
            if (checkMovieInFavorites) {
                favorite.setImageResource(R.drawable.ic_favorite_filled);
            } else {
                favorite.setImageResource(R.drawable.ic_favorite);
           }
        }
    }
}



