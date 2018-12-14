package com.example.viola.movies.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.viola.movies.Movie;
import com.example.viola.movies.R;
import com.example.viola.movies.UI.MainActivity;
import com.example.viola.movies.UI.detailed;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private Context mcontext;
    private List<Movie> moviesList;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.mcontext = context;
        this.moviesList=movies;
    }
    public void setFavs(List<Movie> results) {
        this.moviesList = results;
    }

    @NonNull
    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item,parent,false);
    return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieAdapter.MyViewHolder holder, int position) {
        if(moviesList!=null) {
            Movie current = moviesList.get(position);
            holder.title.setText(current.getMovie_original_Title());
            holder.rate.setText(Double.toString(current.getVoteAverage()));
            Picasso.with(mcontext).load("http://image.tmdb.org/t/p/w500" +current.getPoster_path()).into(holder.poster);
        }
    }


    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public ImageView poster;
        public TextView rate;

        public MyViewHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.movie_title);
            poster= itemView.findViewById(R.id.moviePoster_iv);
            rate= itemView.findViewById(R.id.rate);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                    Intent intent = new Intent(mcontext,detailed.class);
                        intent.putExtra("original_title",moviesList.get(pos).getMovie_original_Title());
                        intent.putExtra("poster_path",moviesList.get(pos).getPoster_path());
                        intent.putExtra("over_view",moviesList.get(pos).getOverview());
                        intent.putExtra("vote_average",Double.toString(moviesList.get(pos).getVoteAverage()));
                        intent.putExtra("release_date",moviesList.get(pos).getDate_of_release());
                        intent.putExtra("backdrop_path",moviesList.get(pos).getBackdrop_path());
                        intent.putExtra("id",moviesList.get(pos).getMovie_id());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mcontext.startActivity(intent);

                }
            });
        }

    }

}
