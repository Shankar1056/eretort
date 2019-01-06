package com.apextechies.eretort.model;

/**
 * Created by Shankar on 12/17/2017.
 */

public class NewsModel {

    public String getNews_id() {
        return news_id;
    }

    public void setNews_id(String news_id) {
        this.news_id = news_id;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsShortDesc() {
        return newsShortDesc;
    }

    public void setNewsShortDesc(String newsShortDesc) {
        this.newsShortDesc = newsShortDesc;
    }

    public String getNewsLonDesc() {
        return newsLonDesc;
    }

    public void setNewsLonDesc(String newsLonDesc) {
        this.newsLonDesc = newsLonDesc;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage;
    }

    public String getNews_date() {
        return news_date;
    }

    public void setNews_date(String news_date) {
        this.news_date = news_date;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    private String news_id, newsTitle, newsShortDesc, newsLonDesc, newsImage, news_date, schoolName;
    public NewsModel(String news_id, String newsTitle, String newsShortDesc, String newsLonDesc, String newsImage, String news_date,
                     String schoolName)
    {
        this.news_id = news_id;
        this.newsTitle = newsTitle;
        this.newsShortDesc = newsShortDesc;
        this.newsLonDesc = newsLonDesc;
        this.newsImage = newsImage;
        this.news_date = news_date;
        this.schoolName = schoolName;
    }
}
