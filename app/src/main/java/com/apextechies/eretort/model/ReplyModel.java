package com.apextechies.eretort.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shankar on 1/1/2018.
 */

public class ReplyModel implements Parcelable{

    private ReplyModel(Parcel in) {
        reply_id = in.readString();
        registrationNumber = in.readString();
        replyText = in.readString();
        replyImage = in.readString();
        schoolName = in.readString();
        replyStatus = in.readString();
        forum_id = in.readString();
        date = in.readString();
        time = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(reply_id);
        dest.writeString(registrationNumber);
        dest.writeString(replyText);
        dest.writeString(replyImage);
        dest.writeString(schoolName);
        dest.writeString(replyStatus);
        dest.writeString(forum_id);
        dest.writeString(date);
        dest.writeString(time);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ReplyModel> CREATOR = new Creator<ReplyModel>() {
        @Override
        public ReplyModel createFromParcel(Parcel in) {
            return new ReplyModel(in);
        }

        @Override
        public ReplyModel[] newArray(int size) {
            return new ReplyModel[size];
        }
    };

    public String getReply_id() {
        return reply_id;
    }

    public void setReply_id(String reply_id) {
        this.reply_id = reply_id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getReplyText() {
        return replyText;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    public String getReplyImage() {
        return replyImage;
    }

    public void setReplyImage(String replyImage) {
        this.replyImage = replyImage;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getReplyStatus() {
        return replyStatus;
    }

    public void setReplyStatus(String replyStatus) {
        this.replyStatus = replyStatus;
    }

    public String getForum_id() {
        return forum_id;
    }

    public void setForum_id(String forum_id) {
        this.forum_id = forum_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String reply_id,registrationNumber,replyText,replyImage,schoolName,replyStatus,forum_id, date, time;

    public ReplyModel(String reply_id, String registrationNumber, String replyText, String replyImage, String schoolName, String replyStatus,
                      String forum_id, String date, String time)
    {
        this.reply_id = reply_id;
        this.registrationNumber = registrationNumber;
        this.replyText = replyText;
        this.replyImage = replyImage;
        this.schoolName = schoolName;
        this.replyStatus = replyStatus;
        this.forum_id = forum_id;
        this.date = date;
        this.time = time;
    }

}
