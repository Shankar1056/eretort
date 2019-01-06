package com.apextechies.eretort.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shankar on 4/6/2018.
 */

public class GalleryImages implements Parcelable{

    private String id;

    private GalleryImages(Parcel in) {
        id = in.readString();
        cat_id = in.readString();
        image = in.readString();
        thumbnail = in.readString();
        status = in.readString();
        precedence = in.readString();
    }

    public static final Creator<GalleryImages> CREATOR = new Creator<GalleryImages>() {
        @Override
        public GalleryImages createFromParcel(Parcel in) {
            return new GalleryImages(in);
        }

        @Override
        public GalleryImages[] newArray(int size) {
            return new GalleryImages[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrecedence() {
        return precedence;
    }

    public void setPrecedence(String precedence) {
        this.precedence = precedence;
    }

    private String cat_id;
    private String image;
    private String thumbnail;
    private String status;
    private String precedence;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(cat_id);
        dest.writeString(image);
        dest.writeString(thumbnail);
        dest.writeString(status);
        dest.writeString(precedence);
    }
}
