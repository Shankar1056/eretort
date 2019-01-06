package com.apextechies.eretort.model;

/**
 * Created by Shankar on 1/14/2018.
 */

public class YearHolidayModel {

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    private String id;
    private String name;
    private String date;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;
    private String schoolName;

    public YearHolidayModel(String id, String name, String date, String image, String schoolName)
    {
        this.id = id;
        this.name = name;
        this.date = date;
        this.image = image;
        this.schoolName = schoolName;
    }
}
