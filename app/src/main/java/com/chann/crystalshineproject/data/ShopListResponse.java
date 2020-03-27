package com.chann.crystalshineproject.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShopListResponse {

    @SerializedName("is_success")
    public boolean isSuccess;

    @SerializedName("shops")
    public List<ShopList> shopList;
}
