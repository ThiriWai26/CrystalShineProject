package com.chann.crystalshineproject.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TownsResponse {

    @SerializedName("is_success")
    public boolean isSuccess;

    @SerializedName("towns")
    public List<Towns> towns;
}
