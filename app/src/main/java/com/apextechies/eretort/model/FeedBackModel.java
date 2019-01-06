package com.apextechies.eretort.model;

import com.google.gson.annotations.SerializedName;

public class FeedBackModel {
    @SerializedName("status")
    String status;
    @SerializedName("msg")
    String msg;
    @SerializedName("data")
    String data;

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public String getData() {
        return data;
    }

}
