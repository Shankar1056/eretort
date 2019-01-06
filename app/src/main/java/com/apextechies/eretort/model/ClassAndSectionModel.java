package com.apextechies.eretort.model;

import com.google.gson.annotations.SerializedName;

public class ClassAndSectionModel {
    @SerializedName("class_id")
    private String classId;
    @SerializedName("section_id")
    private String sectionId;
    @SerializedName("class_name")
    private String className;
    @SerializedName("section_name")
    private String sectionName;
}
