package com.apextechies.eretort.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shankar on 12/13/2017.
 */

public class AskQuestionModel implements Parcelable{



    private String forum_id;
    private String registratinId;
    private String questionText;
    private String questionImage;
    private String forumStatus;
    private String schoolName;
    private String date;
    private String time;

    private AskQuestionModel(Parcel in) {
        forum_id = in.readString();
        registratinId = in.readString();
        questionText = in.readString();
        questionImage = in.readString();
        forumStatus = in.readString();
        schoolName = in.readString();
        date = in.readString();
        time = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(forum_id);
        dest.writeString(registratinId);
        dest.writeString(questionText);
        dest.writeString(questionImage);
        dest.writeString(forumStatus);
        dest.writeString(schoolName);
        dest.writeString(date);
        dest.writeString(time);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AskQuestionModel> CREATOR = new Creator<AskQuestionModel>() {
        @Override
        public AskQuestionModel createFromParcel(Parcel in) {
            return new AskQuestionModel(in);
        }

        @Override
        public AskQuestionModel[] newArray(int size) {
            return new AskQuestionModel[size];
        }
    };

    public String getForum_id() {
        return forum_id;
    }

    public void setForum_id(String forum_id) {
        this.forum_id = forum_id;
    }

    public String getRegistratinId() {
        return registratinId;
    }

    public void setRegistratinId(String registratinId) {
        this.registratinId = registratinId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionImage() {
        return questionImage;
    }

    public void setQuestionImage(String questionImage) {
        this.questionImage = questionImage;
    }

    public String getForumStatus() {
        return forumStatus;
    }

    public void setForumStatus(String forumStatus) {
        this.forumStatus = forumStatus;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
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

    public AskQuestionModel(String forum_id, String registratinId, String questionText, String questionImage, String forumStatus, String schoolName,
                            String date, String time)
    {
        this.forum_id = forum_id;
        this.registratinId = registratinId;
        this.questionText = questionText;
        this.questionImage = questionImage;
        this.forumStatus = forumStatus;
        this.schoolName = schoolName;
        this.date = date;
        this.time = time;
    }


}
