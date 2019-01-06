package com.apextechies.eretort.model;

/**
 * Created by shankar on 12/12/17.
 */

public class NoticeBoardModel {

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getLondesc() {
        return londesc;
    }

    public void setLondesc(String londesc) {
        this.londesc = londesc;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    private String notice_id,heading,title,londesc,postedDate,schoolname;
    public NoticeBoardModel(String notice_id, String heading, String title, String londesc, String postedDate, String schoolname)
    {
        this.notice_id = notice_id;
        this.heading = heading;
        this.title = title;
        this.londesc = londesc;
        this.postedDate = postedDate;
        this.schoolname = schoolname;
    }
}
