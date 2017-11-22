package com.inovaitsys.inozest.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageResponse {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private Url url;
    @SerializedName("branches")
    @Expose
    private String branches;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public String getBranches() {
        return branches;
    }

    public void setBranches(String branches) {
        this.branches = branches;
    }

}
