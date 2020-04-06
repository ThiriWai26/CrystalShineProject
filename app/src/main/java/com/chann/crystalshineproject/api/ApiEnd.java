package com.chann.crystalshineproject.api;

import com.chann.crystalshineproject.data.Login;
import com.chann.crystalshineproject.data.Logout;
import com.chann.crystalshineproject.data.ProjectNameListResponse;
import com.chann.crystalshineproject.data.ShopCategoriesResponse;
import com.chann.crystalshineproject.data.ShopDetailResponse;
import com.chann.crystalshineproject.data.ShopListResponse;
import com.chann.crystalshineproject.data.ShopReportResponse;
import com.chann.crystalshineproject.data.ShopSearchResponse;
import com.chann.crystalshineproject.data.ShopStoreResponse;
import com.chann.crystalshineproject.data.TownsResponse;
import com.chann.crystalshineproject.data.TownshipResponse;
import com.chann.crystalshineproject.data.TownshipsResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiEnd {

    @FormUrlEncoded
    @POST("/api/login")
    Call<Login> userLogin(@Field("name_or_phone_number") String phoneNumber, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/projects")
    Call<ProjectNameListResponse> projectNameList(@Field("token") String token);

    @FormUrlEncoded
    @POST("api/project_townships/")
    Call<TownshipResponse> townshopList(@Field("token") String token, @Field("project_id") int projectId);

    @FormUrlEncoded
    @POST("api/shops")
    Call<ShopListResponse> shopList(@Field("token") String token, @Field("township_id") int townshipId);

    @FormUrlEncoded
    @POST("api/shop_details")
    Call<ShopDetailResponse> shopDetail(@Field("token") String token, @Field("shop_id") int shopId);

    @Multipart
    @POST("api/shop_report")
    Call<ShopReportResponse> shopReport(@Part("token") RequestBody token, @Part("shop_id") int shopId, @Part("project_id") int projectId, @Part MultipartBody.Part photo, @Part("latitude") double latitude, @Part("longitude") double longitude);

    @FormUrlEncoded
    @POST("api/shop_search")
    Call<ShopSearchResponse> shopSearch(@Field("token") String token, @Field("name") String name);

    @FormUrlEncoded
    @POST("api/towns")
    Call<TownsResponse> towns(@Field("token") String token);

    @FormUrlEncoded
    @POST("api/townships")
    Call<TownshipsResponse> townships(@Field("token") String token, @Field("town_id") int townId);

    @FormUrlEncoded
    @POST("api/shop_categories")
    Call<ShopCategoriesResponse> shopCategory(@Field("token") String token);


    @Headers("Accept: application/json")
    @Multipart
    @POST("/api/shop_store")
    Observable<ShopStoreResponse> shopStore1(@Part("token") String token,
                                      @Part("category_id") int categoryId,
                                      @Part("township_id") int  townshipId,
                                      @Part MultipartBody.Part photo,
                                      @Part("rate") int rate,
                                      @Part("grade") int grade,
                                            @Part("name") String name,
                                      @Part("address") String address);

    @Multipart
    @Headers("Accept: application/json")
    @POST("/api/shop_store")
    Call<ShopStoreResponse> shopStore33(@Part("token") RequestBody token,
                                      @Part("category_id") RequestBody categoryId,
                                      @Part("township_id") RequestBody  townshipId,
                                      //   @Part MultipartBody.Part photo,
                                      @Part("rate") RequestBody rate,
                                      @Part("name") RequestBody name,
                                      @Part("grade") RequestBody grade,
                                      @Part("address") RequestBody address);



    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("/api/shop_store")
    Call<ShopStoreResponse> shopStore(@Field("token") String token, @Field("category_id") int categoryId, @Field("township_id") int townshipId, @Field("rate") List<String> rate, @Field("grade") String grade, @Field("name") String name, @Field("address") String address);

    @Multipart
    @POST("api/shop_photo")
    Call<ShopReportResponse> shopPhoto(@Part("token") RequestBody token, @Part("shop_id") int shopId, @Part("project_id") int projectId, @Part MultipartBody.Part photo, @Part("latitude") double latitude, @Part("longitude") double longitude);


    @FormUrlEncoded
    @POST("api/shop_store/{id}")
    Call<ShopStoreResponse> shopStoreUpdate(@Field("token") String token, @Field("category_id") int categoryId, @Field("township_id") int townshipId,  @Part("rate") List<String> rate, @Part("grade") String grade, @Part("name") String name, @Part("address") String address);

//    @Multipart
//    @POST("api/shop_store/{id}")
//    Call<ShopStoreResponse> shopStoreUpdate(@Part("token") RequestBody token, @Part("category_id") int categoryId, @Part("township_id") int townshipId, @Part MultipartBody.Part photo, @Part("rate") List<String> rate, @Part("grade") String grade, @Part("name") String name, @Part("address") String address);

    @FormUrlEncoded
    @POST("api/logout")
    Call<Logout> userLogout(@Field("token") String token);

}
