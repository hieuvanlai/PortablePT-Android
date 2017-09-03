package com.example.hihihahahehe.portablept.models.JSONModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hieuit on 9/2/17.
 */

public class GetPackJSONModel {
    @SerializedName("_id")
    private String id;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    @SerializedName("purpose")
    private String purpose;

    @SerializedName("coach")
    private DataLoginJSON coach;

    @SerializedName("price")
    private String price;

    @SerializedName("duration")
    private String duration;

    @SerializedName("coutStar")
    private Integer totalStars;

    @SerializedName("voteStar")
    private Integer votedStars;

    private String packName;

    @SerializedName("packImgUrl")
    private String packImgUrl;

    private String address;
    private String calendar;
    private String content;

    public String getContent() {
        return content;
    }

    public Integer getTotalStars() {
        return totalStars;
    }

    public void setTotalStars(Integer totalStars) {
        this.totalStars = totalStars;
    }

    public Integer getVotedStars() {
        return votedStars;
    }

    public void setVotedStars(Integer votedStars) {
        this.votedStars = votedStars;
    }

    public DataLoginJSON getCoach() {
        return coach;
    }

    public void setCoach(DataLoginJSON coach) {
        this.coach = coach;
    }

    public GetPackJSONModel() {
    }

    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
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
