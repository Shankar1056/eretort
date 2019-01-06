package com.apextechies.eretort.model;

import java.util.ArrayList;

/**
 * Created by Shankar on 3/17/2018.
 */

public class LeaveAllRequestModel {

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public ArrayList<LeaveRequestModel> getData() {
        return data;
    }

    private String status,msg;
    ArrayList<LeaveRequestModel> data;

}
