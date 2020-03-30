package com.chann.crystalshineproject.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TownshipsResponse {

    @SerializedName("is_success")
    public boolean isSuccess;

    @SerializedName("townships")
    public List<Township> township;
}
