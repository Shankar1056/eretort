package com.apextechies.eretort.model;

import java.util.ArrayList;

/**
 * Created by Shankar on 4/1/2018.
 */

public class TopperListModel {

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public ArrayList<TopperListDateModel> getData() {
        return data;
    }

    private String status,msg;
    private ArrayList<TopperListDateModel> data;
}
