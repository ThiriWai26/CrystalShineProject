package com.chann.crystalshineproject.data;

import com.google.gson.annotations.SerializedName;

public class ProjectNameListResponse {

    @SerializedName("is_success")
    public boolean isSuccess;

    @SerializedName("projects")
    public ProjectNameList projectNameList;

    @SerializedName("error_message")
    public String errorMessage;

}
