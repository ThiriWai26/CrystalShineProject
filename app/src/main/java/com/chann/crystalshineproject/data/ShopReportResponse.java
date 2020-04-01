package com.chann.crystalshineproject.data;

import com.google.gson.annotations.SerializedName;

public class ShopReportResponse {

    @SerializedName("is_success")
    public boolean isSuccess;

    @SerializedName("error_message")
    public ErrorMessage errorMessage;
}
