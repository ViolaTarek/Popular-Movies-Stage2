package com.example.viola.movies.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.viola.movies.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class fetchMovies implements Parcelable {

    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<Movie> results;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public List<Movie> getMovies() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public void setMovies(List<Movie> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeTypedList(this.results);
        dest.writeInt(this.totalResults);
        dest.writeInt(this.totalPages);
    }


    public fetchMovies() {
    }

    protected fetchMovies(Parcel in) {
        this.page = in.readInt();
        this.results = in.createTypedArrayList(Movie.CREATOR);
        this.totalResults = in.readInt();
        this.totalPages = in.readInt();
    }

    public static final Parcelable.Creator<fetchMovies> CREATOR = new Parcelable.Creator<fetchMovies>() {
        @Override
        public fetchMovies createFromParcel(Parcel source) {
            return new fetchMovies(source);
        }

        @Override
        public fetchMovies[] newArray(int size) {
            return new fetchMovies[size];
        }
    };
}
