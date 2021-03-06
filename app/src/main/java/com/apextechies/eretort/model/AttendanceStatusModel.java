package com.apextechies.eretort.model;

import com.google.gson.annotations.SerializedName;

public class AttendanceStatusModel {
    @SerializedName("student_id")
    String student_id;
    @SerializedName("status")
    String status;
    @SerializedName("student_name")
    String student_name;

    public AttendanceStatusModel(String student_id, String student_name, String status) {
        this.student_id = student_id;
        this.status = status;
        this.student_name = student_name;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
