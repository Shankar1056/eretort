package com.apextechies.eretort.model;

/**
 * Created by Shankar on 4/1/2018.
 */

public class TopperListDateModel {

    public String getToper_id() {
        return toper_id;
    }

    public void setToper_id(String toper_id) {
        this.toper_id = toper_id;
    }

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassing_year() {
        return passing_year;
    }

    public void setPassing_year(String passing_year) {
        this.passing_year = passing_year;
    }

    public String getPassing_number() {
        return passing_number;
    }

    public void setPassing_number(String passing_number) {
        this.passing_number = passing_number;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    private String toper_id, school_id, name, passing_year, passing_number, user_image;

}
