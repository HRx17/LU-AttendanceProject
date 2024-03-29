package com.example.attendance_v10.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private  static  String BASE_URL="http://aryan51k-55373.portmap.host:55373/";
    private static RetrofitClient retrofitClient;
    private static Retrofit retrofit;

    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if(retrofitClient==null){
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }
    public Api getApi(){
        return retrofit.create(Api.class);
    }
}
