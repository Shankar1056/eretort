package com.apextechies.eretort.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Shankar on 3/18/2018.
 */

public class GalleryDataModel implements Parcelable{

    private GalleryDataModel(Parcel in) {
        id = in.readString();
        heading = in.readString();
        status = in.readString();
        precedence = in.readString();
        images = in.createTypedArrayList(GalleryImages.CREATOR);
    }

    public static final Creator<GalleryDataModel> CREATOR = new Creator<GalleryDataModel>() {
        @Override
        public GalleryDataModel createFromParcel(Parcel in) {
            return new GalleryDataModel(in);
        }

        @Override
        public GalleryDataModel[] newArray(int size) {
            return new GalleryDataModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
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

    public ArrayList<GalleryImages> getImages() {
        return images;
    }

    public void setImages(ArrayList<GalleryImages> images) {
        this.images = images;
    }

    private String id,heading,status,precedence;
    private ArrayList<GalleryImages> images;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(heading);
        dest.writeString(status);
        dest.writeString(precedence);
        dest.writeTypedList(images);
    }
   /* public GalleryDataModel(String id, String heading, String status, String precedence){
        this.id = id;
        this.heading = heading;
        this.status = status;
        this.precedence = precedence;
    }*/
}
