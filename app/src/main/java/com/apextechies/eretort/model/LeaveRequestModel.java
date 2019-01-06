package com.apextechies.eretort.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shankar on 3/17/2018.
 */

public class LeaveRequestModel  implements Parcelable {

    private LeaveRequestModel(Parcel in) {
        application_id = in.readString();
        school_id = in.readString();
        name = in.readString();
        description = in.readString();
        fromtime = in.readString();
        totime = in.readString();
        approver = in.readString();
        status = in.readString();
    }

    public static final Creator<LeaveRequestModel> CREATOR = new Creator<LeaveRequestModel>() {
        @Override
        public LeaveRequestModel createFromParcel(Parcel in) {
            return new LeaveRequestModel(in);
        }

        @Override
        public LeaveRequestModel[] newArray(int size) {
            return new LeaveRequestModel[size];
        }
    };

    public String getApplication_id() {
        return application_id;
    }

    public void setApplication_id(String application_id) {
        this.application_id = application_id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFromtime() {
        return fromtime;
    }

    public void setFromtime(String fromtime) {
        this.fromtime = fromtime;
    }

    public String getTotime() {
        return totime;
    }

    public void setTotime(String totime) {
        this.totime = totime;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String application_id, school_id, name, description, fromtime, totime, approver, status;

    public LeaveRequestModel(String application_id, String school_id,String name,  String description, String fromtime, String totime,
                            String approver , String status){
        this.application_id = application_id;
        this.school_id  = school_id;
        this.name  = name;
        this.description  = description;
        this.fromtime  = fromtime;
        this.totime  = totime;
        this.approver  = approver;
        this.status  = status;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(application_id);
        dest.writeString(school_id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(fromtime);
        dest.writeString(totime);
        dest.writeString(approver);
        dest.writeString(status);
    }
}
