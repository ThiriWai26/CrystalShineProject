package com.chann.crystalshineproject.data;

import com.google.gson.annotations.SerializedName;

public class TownshipResponse {

    @SerializedName("is_success")
    public boolean isSuccess;

    @SerializedName("townships")
    public Township township;
}
