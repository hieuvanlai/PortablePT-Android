package com.example.hihihahahehe.portablept.models.JSONModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hieuit on 9/2/17.
 */

public class GetPackJSONModel {
    @SerializedName("_id")
    private String id;

    @SerializedName("purpose")
    private String purpose;

    @SerializedName("coach")
    private DataLoginJSON coach;

    @SerializedName("price")
    private String price;

    @SerializedName("duration")
    private String duration;

    private String packName;
    private String packImgUrl;
    private String address;
    private String calender;

    public DataLoginJSON getCoach() {
        return coach;
    }

    public void setCoach(DataLoginJSON coach) {
        this.coach = coach;
    }

    public GetPackJSONModel() {
    }

    public String getCalender() {
        return calender;
    }

    public void setCalender(String calender) {
        this.calender = calender;
    }

    public String getPackImgUrl() {
        return packImgUrl;
    }

    public void setPackImgUrl(String packImgUrl) {
        this.packImgUrl = packImgUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }



    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
