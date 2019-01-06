package com.apextechies.eretort.model;

/**
 * Created by Shankar on 2/10/2018.
 */

public class TimeTableModel {
    public String getTime_table_id() {
        return time_table_id;
    }

    public void setTime_table_id(String time_table_id) {
        this.time_table_id = time_table_id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrom_time() {
        return from_time;
    }

    public void setFrom_time(String from_time) {
        this.from_time = from_time;
    }

    public String getTo_time() {
        return to_time;
    }

    public void setTo_time(String to_time) {
        this.to_time = to_time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    private String time_table_id,schoolName,clas,sec,date,from_time,to_time,subject,teacher_name;

    public TimeTableModel(String time_table_id, String schoolName, String clas, String sec, String date, String from_time,
                          String to_time, String subject, String teacher_name){

        this.time_table_id = time_table_id;
        this.schoolName = schoolName;
        this.clas = clas;
        this.sec = sec;
        this.date = date;
        this.from_time = from_time;
        this.to_time = to_time;
        this.subject = subject;
        this.teacher_name = teacher_name;

    }

}
