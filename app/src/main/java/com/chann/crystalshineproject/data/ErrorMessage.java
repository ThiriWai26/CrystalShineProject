package com.chann.crystalshineproject.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ErrorMessage {

    @SerializedName("address")
    public List<String> address;

    @SerializedName("name")
    public List<String> name;
}
