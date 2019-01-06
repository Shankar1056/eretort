package com.apextechies.eretort.model;

/**
 * Created by Shankar on 3/10/2018.
 */

public class TeacherDetailsModel {
    private String teacher_id,teacherRegistrationId, teacherName, teacherQualification, teacherSubject, teacherSchoolName, teacherStatus;

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getTeacherRegistrationId() {
        return teacherRegistrationId;
    }

    public void setTeacherRegistrationId(String teacherRegistrationId) {
        this.teacherRegistrationId = teacherRegistrationId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherQualification() {
        return teacherQualification;
    }

    public void setTeacherQualification(String teacherQualification) {
        this.teacherQualification = teacherQualification;
    }

    public String getTeacherSubject() {
        return teacherSubject;
    }

    public void setTeacherSubject(String teacherSubject) {
        this.teacherSubject = teacherSubject;
    }

    public String getTeacherSchoolName() {
        return teacherSchoolName;
    }

    public void setTeacherSchoolName(String teacherSchoolName) {
        this.teacherSchoolName = teacherSchoolName;
    }

    public String getTeacherStatus() {
        return teacherStatus;
    }

    public void setTeacherStatus(String teacherStatus) {
        this.teacherStatus = teacherStatus;
    }

    public TeacherDetailsModel(String teacher_id, String teacherRegistrationId, String teacherName, String teacherQualification,
                               String teacherSubject, String teacherSchoolName, String teacherStatus){

        this.teacher_id =  teacher_id;
        this.teacherRegistrationId =  teacherRegistrationId;
        this.teacherName =  teacherName;
        this.teacherQualification =  teacherQualification;
        this.teacherSubject =  teacherSubject;
        this.teacherSchoolName =  teacherSchoolName;
        this.teacherStatus =  teacherStatus;

    }
}
