package com.chann.crystalshineproject.data;

import com.google.gson.annotations.SerializedName;

public class ShopDetailResponse {

    @SerializedName("is_success")
    public boolean isSuccess;

    @SerializedName("shop")
    public ShopDetail shopDetail;

    @SerializedName("error_message")
    public String errorMessage;
}
