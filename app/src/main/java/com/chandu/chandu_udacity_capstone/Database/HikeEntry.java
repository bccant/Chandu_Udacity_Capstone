package com.chandu.chandu_udacity_capstone.Database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "hikedetails")
public class HikeEntry {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String hikeID;
    private String hikeName;
    private String hikeType;
    private String hikeSummary;
    private String hikeDifficulty;
    private String hikeStars;
    private String hikeLocation;
    private String hikeURL;
    private String hikeImageSmall;
    private String hikeImage;
    private String hikeLength;
    private String hikeLat;
    private String hikeLong;
    private String hikeCond;
    private String hikeCondDetails;
    private String homeLat;
    private String homeLong;
    private Boolean hikeIsFav = false;

    public HikeEntry(@NonNull String hikeID, String hikeName, String hikeType, String hikeSummary,
                     String hikeDifficulty, String hikeStars, String hikeLocation, String hikeURL,
                     String hikeImageSmall, String hikeImage, String hikeLength, String hikeLat,
                     String hikeLong, String hikeCond, String hikeCondDetails, String homeLat,
                     String homeLong, Boolean hikeIsFav) {
        this.hikeID = hikeID;
        this.hikeName = hikeName;
        this.hikeType = hikeType;
        this.hikeSummary = hikeSummary;
        this.hikeDifficulty = hikeDifficulty;
        this.hikeStars = hikeStars;
        this.hikeLocation = hikeLocation;
        this.hikeURL = hikeURL;
        this.hikeImageSmall = hikeImageSmall;
        this.hikeImage = hikeImage;
        this.hikeLength = hikeLength;
        this.hikeLat = hikeLat;
        this.hikeLong = hikeLong;
        this.hikeCond = hikeCond;
        this.hikeCondDetails = hikeCondDetails;
        this.homeLat = homeLat;
        this.homeLong = homeLong;
        this.hikeIsFav = hikeIsFav;
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

    public Boolean getHikeIsFav() {
        return hikeIsFav;
    }

    public void setHikeIsFav(Boolean hikeIsFav) {
        this.hikeIsFav = hikeIsFav;
    }
}
