package com.chandu.chandu_udacity_capstone.hike;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Hike implements Serializable {
    @SerializedName("id")
    private String hikeID;

    @SerializedName("name")
    private String hikeName;

    @SerializedName("type")
    private String hikeType;

    @SerializedName("summary")
    private String hikeSummary;

    @SerializedName("difficulty")
    private String hikeDifficulty;

    @SerializedName("stars")
    private String hikeStars;

    @SerializedName("location")
    private String hikeLocation;

    @SerializedName("url")
    private String hikeURL;

    @SerializedName("imgSmall")
    private String hikeImageSmall;

    @SerializedName("imgMedium")
    private String hikeImage;

    @SerializedName("length")
    private String hikeLength;

    @SerializedName("latitude")
    private String hikeLat;

    @SerializedName("longitude")
    private String hikeLong;

    @SerializedName("conditionStatus")
    private String hikeCond;

    @SerializedName("conditionDetails")
    private String hikeCondDetails;

    @SerializedName("homeLat")
    private String homeLat = "";

    @SerializedName("homeLong")
    private String homeLong = "";

    @SerializedName("fav")
    private Boolean isFav = false;


    public Hike() {
    }

    public String getHikeID() {
        return hikeID;
    }

    public void setHikeID(String hikeID) {
        this.hikeID = hikeID;
    }

    public String getHikeName() {
        return hikeName;
    }

    public void setHikeName(String hikeName) {
        this.hikeName = hikeName;
    }

    public String getHikeType() {
        return hikeType;
    }

    public void setHikeType(String hikeType) {
        this.hikeType = hikeType;
    }

    public String getHikeSummary() {
        return hikeSummary;
    }

    public void setHikeSummary(String hikeSummary) {
        this.hikeSummary = hikeSummary;
    }

    public String getHikeDifficulty() {
        return hikeDifficulty;
    }

    public void setHikeDifficulty(String hikeDifficulty) {
        this.hikeDifficulty = hikeDifficulty;
    }

    public String getHikeStars() {
        return hikeStars;
    }

    public void setHikeStars(String hikeStars) {
        this.hikeStars = hikeStars;
    }

    public String getHikeLocation() {
        return hikeLocation;
    }

    public void setHikeLocation(String hikeLocation) {
        this.hikeLocation = hikeLocation;
    }

    public String getHikeURL() {
        return hikeURL;
    }

    public void setHikeURL(String hikeURL) {
        this.hikeURL = hikeURL;
    }

    public String getHikeImageSmall() {
        return hikeImageSmall;
    }

    public void setHikeImageSmall(String hikeImageSmall) {
        this.hikeImageSmall = hikeImageSmall;
    }

    public String getHikeImage() {
        return hikeImage;
    }

    public void setHikeImage(String hikeImage) {
        this.hikeImage = hikeImage;
    }

    public String getHikeLength() {
        return hikeLength;
    }

    public void setHikeLength(String hikeLength) {
        this.hikeLength = hikeLength;
    }

    public String getHikeLat() {
        return hikeLat;
    }

    public void setHikeLat(String hikeLat) {
        this.hikeLat = hikeLat;
    }

    public String getHikeLong() {
        return hikeLong;
    }

    public void setHikeLong(String hikeLong) {
        this.hikeLong = hikeLong;
    }

    public String getHikeCond() {
        return hikeCond;
    }

    public void setHikeCond(String hikeCond) {
        this.hikeCond = hikeCond;
    }

    public String getHikeCondDetails() {
        return hikeCondDetails;
    }

    public void setHikeCondDetails(String hikeCondDetails) {
        this.hikeCondDetails = hikeCondDetails;
    }

    public String getHomeLat() {
        return homeLat;
    }

    public void setHomeLat(String homeLat) {
        this.homeLat = homeLat;
    }

    public String getHomeLong() {
        return homeLong;
    }

    public void setHomeLong(String homeLong) {
        this.homeLong = homeLong;
    }

    public Boolean getFav() {
        return isFav;
    }

    public void setFav(Boolean fav) {
        isFav = fav;
    }
}
