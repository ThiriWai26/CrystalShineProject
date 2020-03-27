package com.chann.crystalshineproject.data;

import com.google.gson.annotations.SerializedName;

public class ShopDetail {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("photo")
    public String photo;

    @SerializedName("address")
    public String address;

    @SerializedName("grade")
    public int grade;

    @SerializedName("rate")
    public int rate;

    @SerializedName("category_id")
    public int categoryId;

    @SerializedName("township_id")
    public int townshipId;

    @SerializedName("category_name")
    public String categoryName;

    @SerializedName("township_name")
    public String townshipName;

    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("updated_at")
    public String updatedAt;


}
