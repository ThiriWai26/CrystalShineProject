package com.chann.crystalshineproject.data;

import com.google.gson.annotations.SerializedName;

public class ShopCategoriesResponse {

    @SerializedName("is_success")
    public boolean isSuccess;

    @SerializedName("shop_categories")
    public ShopCategory shopCategory;
}
