package com.apextechies.eretort.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StudentListByClassModel {

    @SerializedName("status")
    String status;
    @SerializedName("msg")
    String msg;
    @SerializedName("data")
    ArrayList<StudentListDataModel> data;

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public ArrayList<StudentListDataModel> getData() {
        return data;
    }
}
