package com.apextechies.eretort.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class InsertAttendanceModel {

    @SerializedName("date")
    String date;
    @SerializedName("class_id")
    String clas;
    @SerializedName("sec_id")
    String sec;
    @SerializedName("school_id")
    String schoolId;
    @SerializedName("sendertype")
    String senderType;
    @SerializedName("sendto")
    String sendTo;
    @SerializedName("is_sms_allowed")
    String smsAllowedStatus;
    @SerializedName("teacher_id")
    String teacher_id;
    @SerializedName("attendance")
    ArrayList<AttendanceStatusModel> attendance;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClas() {
        return clas;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }

    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public ArrayList<AttendanceStatusModel> getAttendance() {
        return attendance;
    }

    public void setAttendance(ArrayList<AttendanceStatusModel> attendance) {
        this.attendance = attendance;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getSenderType() {
        return senderType;
    }

    public void setSenderType(String senderType) {
        this.senderType = senderType;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getSmsAllowedStatus() {
        return smsAllowedStatus;
    }

    public void setSmsAllowedStatus(String smsAllowedStatus) {
        this.smsAllowedStatus = smsAllowedStatus;
    }



}
