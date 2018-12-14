package com.example.viola.movies;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "favorites_table")

public class Movie implements Parcelable {


    @PrimaryKey
    @NonNull
    @SerializedName("id")
    private int movie_id;

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String poster_path;

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    private String overview;
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    private String date_of_release;
    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    private String movie_original_Title;

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private Double voteAverage;


    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    private String backdrop_path;


    public Movie(){

    }

    public Movie(String poster_path, Integer movie_id, String overview, String date_of_release, String movie_original_Title, Double voteAverage, String backdrop_path) {
        this.poster_path = poster_path;
        this.movie_id = movie_id;
        this.overview = overview;
        this.date_of_release = date_of_release;
        this.movie_original_Title = movie_original_Title;
        this.voteAverage = voteAverage;
        this.backdrop_path=backdrop_path;
    }


    public String getPoster_path() {
        return  poster_path;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public String getOverview() {
        return overview;
    }

    public String getDate_of_release() {
        return date_of_release;
    }

    public String getMovie_original_Title() {
        return movie_original_Title;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }


    public String getBackdrop_path() {
        return  backdrop_path;
    }


    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setMovie_id(@NonNull Integer movie_id) {
        this.movie_id = movie_id;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setDate_of_release(String date_of_release) {
        this.date_of_release = date_of_release;
    }

    public void setMovie_original_Title(String movie_original_Title) {
        this.movie_original_Title = movie_original_Title;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }


    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.movie_id);
        dest.writeString(this.poster_path);
        dest.writeString(this.overview);
        dest.writeString(this.date_of_release);
        dest.writeString(this.movie_original_Title);
        dest.writeValue(this.voteAverage);
        dest.writeString(this.backdrop_path);


    }
@Ignore
    protected Movie(Parcel in) {
        movie_id = in.readInt();
        poster_path = in.readString();
        overview = in.readString();
        date_of_release = in.readString();
        movie_original_Title = in.readString();
        voteAverage = in.readDouble();
        backdrop_path = in.readString();

    }
    public static final Parcelable.Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}
