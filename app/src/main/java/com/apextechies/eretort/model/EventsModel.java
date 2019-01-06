package com.apextechies.eretort.model;

/**
 * Created by Shankar on 12/17/2017.
 */

public class EventsModel {
    public String getEvents_id() {
        return events_id;
    }

    public void setEvents_id(String events_id) {
        this.events_id = events_id;
    }

    public String getEventsTitle() {
        return eventsTitle;
    }

    public void setEventsTitle(String eventsTitle) {
        this.eventsTitle = eventsTitle;
    }

    public String getEventsShortDesc() {
        return eventsShortDesc;
    }

    public void setEventsShortDesc(String eventsShortDesc) {
        this.eventsShortDesc = eventsShortDesc;
    }

    public String getEventsLonDesc() {
        return eventsLonDesc;
    }

    public void setEventsLonDesc(String eventsLonDesc) {
        this.eventsLonDesc = eventsLonDesc;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage;
    }

    public String getEvents_date() {
        return events_date;
    }

    public void setEvents_date(String events_date) {
        this.events_date = events_date;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    private String events_id,eventsTitle,eventsShortDesc,eventsLonDesc,newsImage,events_date,schoolName;

    public EventsModel(String events_id, String eventsTitle, String eventsShortDesc, String eventsLonDesc, String newsImage,
                       String events_date, String schoolName)
    {
        this.events_id = events_id;
        this.eventsTitle = eventsTitle;
        this.eventsShortDesc = eventsShortDesc;
        this.eventsLonDesc = eventsLonDesc;
        this.newsImage = newsImage;
        this.events_date = events_date;
        this.schoolName = schoolName;
    }
}
