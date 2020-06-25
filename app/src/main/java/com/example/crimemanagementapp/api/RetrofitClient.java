package com.example.crimemanagementapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL="http://192.168.43.216:8000/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    public RetrofitClient(){
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized RetrofitClient getInstance(){
        if(mInstance==null){

            mInstance=new RetrofitClient();
        }
        return mInstance;
    }
    public Api getApi(){
        return retrofit.create(Api.class);
    }







}
