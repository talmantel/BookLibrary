package com.openu.sadna.booklibrary.network;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Rest API client configuration
public class RestClient {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/"; //TODO
    private static RestClient instance = null;
    private final APIInterface apiService;

    private RestClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(APIInterface.class);
    }

    public synchronized static RestClient getInstance(){
        if(instance == null)
            instance = new RestClient();
        return instance;
    }

    public APIInterface getApiService() {
        return apiService;
    }
}
