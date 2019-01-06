package com.apextechies.eretort.model;

/**
 * Created by Shankar on 3/5/2018.
 */

public class HomeworkModel {
    public String getHomework_id() {
        return homework_id;
    }

    public void setHomework_id(String homework_id) {
        this.homework_id = homework_id;
    }

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    private String homework_id, school_id, clas, sec, date, from_time, to_time, subject, status, teacher_id,teacher_name;

    public HomeworkModel(String homework_id, String school_id, String clas, String sec, String date, String from_time, String to_time,
                         String subject, String status, String teacher_id, String teacher_name){
        this.homework_id = homework_id;
        this.school_id = school_id;
        this.clas = clas;
        this.sec = sec;
        this.date = date;
        this.from_time = from_time;
        this.to_time = to_time;
        this.subject = subject;
        this.status = status;
        this.teacher_id = teacher_id;
        this.teacher_name = teacher_name;
    }
}
