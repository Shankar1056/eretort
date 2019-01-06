package com.apextechies.eretort.model;

/**
 * Created by Shankar on 3/7/2018.
 */

public class ExamScheduleModel {

    public String getExamtable_id() {
        return examtable_id;
    }

    public void setExamtable_id(String examtable_id) {
        this.examtable_id = examtable_id;
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

    public String getInvigilator() {
        return invigilator;
    }

    public void setInvigilator(String invigilator) {
        this.invigilator = invigilator;
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

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    private String examtable_id, school_id, clas, sec,date, from_time, to_time, invigilator, subject, status,created_date;

    public ExamScheduleModel(String examtable_id, String school_id, String clas, String sec, String date, String from_time, String to_time,
                             String invigilator, String subject, String status, String created_date){
        this.examtable_id = examtable_id;
        this.school_id = school_id;
        this.clas = clas;
        this.sec = sec;
        this.date = date;
        this.from_time = from_time;
        this.to_time = to_time;
        this.invigilator = invigilator;
        this.subject = subject;
        this.status = status;
        this.created_date = created_date;
    }
}
