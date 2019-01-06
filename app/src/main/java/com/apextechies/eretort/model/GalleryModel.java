package com.apextechies.eretort.model;

import java.util.ArrayList;

/**
 * Created by Shankar on 3/18/2018.
 */

public class GalleryModel {
    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public ArrayList<GalleryDataModel> getData() {
        return data;
    }

    private String status,msg;
    ArrayList<GalleryDataModel> data;

}
