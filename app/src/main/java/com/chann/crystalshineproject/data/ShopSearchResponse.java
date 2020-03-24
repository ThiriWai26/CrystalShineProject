package com.chann.crystalshineproject.data;

import com.google.gson.annotations.SerializedName;

public class ShopSearchResponse {

    @SerializedName("is_success")
    public boolean isSuccess;

    @SerializedName("shops")
    public ShopList shopList;
}
