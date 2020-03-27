package com.chann.crystalshineproject.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShopCategoriesResponse {

    @SerializedName("is_success")
    public boolean isSuccess;

    @SerializedName("shop_categories")
    public List<ShopCategory> shopCategory;
}
