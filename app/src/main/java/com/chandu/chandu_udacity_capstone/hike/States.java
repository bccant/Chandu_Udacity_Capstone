package com.chandu.chandu_udacity_capstone.hike;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class States implements Serializable {
    @SerializedName("state")
    private String stateName;

    @SerializedName("capital_city")
    private String capName;

    @SerializedName("landscape_background_url")
    private String landscapeImage;

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCapName() {
        return capName;
    }

    public void setCapName(String capName) {
        this.capName = capName;
    }

    public String getLandscapeImage() {
        return landscapeImage;
    }

    public void setLandscapeImage(String landscapeImage) {
        this.landscapeImage = landscapeImage;
    }

}
