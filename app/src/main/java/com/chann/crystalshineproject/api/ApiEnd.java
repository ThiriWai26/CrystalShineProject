package com.chann.crystalshineproject.api;

import com.chann.crystalshineproject.data.Login;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiEnd {

    @FormUrlEncoded
    @POST("/api/login")
    Observable<Login> userLogin(@Field("phone_number") String phoneNumber, @Field("password") String password);
}
