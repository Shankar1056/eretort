package com.apextechies.eretort.model;

public class GeneralParamsModel {
    String apiKeyStr;
            String tokenStr;
    String loginAsStr;

    public String getApiKey() {
        return apiKeyStr;
    }

    public String getToken() {
        return tokenStr;
    }

    public String getLoginAs() {
        return loginAsStr;
    }

    public String getSchoolId() {

        return schoolIdStr;
    }

    public GeneralParamsModel(String apiKeyStr, String tokenStr, String loginAsStr, String schoolIdStr) {
        this.apiKeyStr = apiKeyStr;
        this.tokenStr = tokenStr;
        this.loginAsStr = loginAsStr;
        this.schoolIdStr = schoolIdStr;
    }

    String schoolIdStr;
}
