package com.chann.crystalshineproject.data;

import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("is_success")
    public boolean isSuccess;

    @SerializedName("error_message")
    public String errorMessage;

    @SerializedName("token")
    public String token;


}
