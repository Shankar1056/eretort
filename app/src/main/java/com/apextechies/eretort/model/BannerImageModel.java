package com.apextechies.eretort.model;

/**
 * Created by Shankar on 1/10/2017.
 */

public class BannerImageModel {


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    private String id,banner;

    public BannerImageModel(String id, String banner)
    {
        this.id = id;
        this.banner = banner;
    }
}
