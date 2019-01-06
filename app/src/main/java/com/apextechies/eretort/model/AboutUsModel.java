package com.apextechies.eretort.model;

/**
 * Created by Shankar on 4/5/2018.
 */

public class AboutUsModel {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getContant() {
        return contant;
    }

    public void setContant(String contant) {
        this.contant = contant;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String id,name,heading,contant,status;

    public AboutUsModel(String id, String name, String heading, String contant, String status){
        this.id = id;
        this.name = name;
        this.heading = heading;
        this.contant = contant;
        this.status = status;
    }
}
