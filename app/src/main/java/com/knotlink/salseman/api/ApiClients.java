package com.knotlink.salseman.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClients {
    //    private static final String BASE_URL = "https://teocfish.com/knotlinkdemo/";
    private static final String BASE_URL = "https://teocfish.com/knotlink/";
    private static Retrofit retrofit;

    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    public static Retrofit getApiClients(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
