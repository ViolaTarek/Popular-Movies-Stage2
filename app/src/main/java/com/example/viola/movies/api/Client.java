package com.example.viola.movies.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    static public String BASE_URL = "http://api.themoviedb.org/3/";
    static public Retrofit retrofit=null;


    public Retrofit getClient(){
        if (retrofit==null){
            retrofit =new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
            .build();

        }
        return retrofit;
    }

}
