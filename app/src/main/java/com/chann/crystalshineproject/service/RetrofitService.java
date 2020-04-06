package com.chann.crystalshineproject.service;

import com.chann.crystalshineproject.api.ApiEnd;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

        public static final String BASE_URL="http://167.71.212.65/";
        private static ApiEnd apiEnd;
        private static RetrofitService retrofitService;

        private RetrofitService(){ }

        public static RetrofitService getInstance(){
            if(retrofitService == null){
                retrofitService = new RetrofitService();
            }


            return retrofitService;
        }

        public static ApiEnd getApiEnd(){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(200, TimeUnit.SECONDS)
                    .readTimeout(200,TimeUnit.SECONDS)
                    .build();


            Gson gson = new GsonBuilder().setLenient().create();
            Retrofit service = new Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();



            if(apiEnd == null) {

                apiEnd = service.create(ApiEnd.class);
            }

            return apiEnd;
        }

}
