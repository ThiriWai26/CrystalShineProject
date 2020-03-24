package com.chann.crystalshineproject.data;

import com.google.gson.annotations.SerializedName;

public class TownsResponse {

    @SerializedName("is_success")
    public boolean isSuccess;

    @SerializedName("towns")
    public Towns towns;
}
