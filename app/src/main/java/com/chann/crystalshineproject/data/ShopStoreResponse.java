package com.chann.crystalshineproject.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShopStoreResponse {

    @SerializedName("is_success")
    @Expose
    public boolean isSuccess;

    @SerializedName("error_message")
    @Expose
    public ErrorMessage errorMessage;


}
