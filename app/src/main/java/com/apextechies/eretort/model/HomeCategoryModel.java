package com.apextechies.eretort.model;

/**
 * Created by Shankar on 11/7/2017.
 */

public class HomeCategoryModel {
    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatColor() {
        return catColor;
    }

    public void setCatColor(String catColor) {
        this.catColor = catColor;
    }

    public String getCatIcon() {
        return catIcon;
    }

    public void setCatIcon(String catIcon) {
        this.catIcon = catIcon;
    }

    public String getScholName() {
        return scholName;
    }

    public void setScholName(String scholName) {
        this.scholName = scholName;
    }

    private String cat_id,catName,catColor,catIcon,scholName;
    public HomeCategoryModel(String cat_id, String catName, String catColor, String catIcon, String scholName) {
        this.cat_id = cat_id;
        this.catName = catName;
        this.catColor = catColor;
        this.catIcon = catIcon;
        this.scholName = scholName;

    }
}
