package com.chann.crystalshineproject.data;

import com.google.gson.annotations.SerializedName;

public class Township {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("error_message")
    public String errorMessage;
}
