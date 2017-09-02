package com.example.hihihahahehe.portablept.models.JSONModel;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by hieuit on 8/26/17.
 */

public class DataLoginJSON extends RealmObject {
    private String phoneNumber;
    @SerializedName("_id")
    private String id;
    @SerializedName("username")
    private String username;
    @SerializedName("imgAvata")
    private String imgAvata;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("role")
    private String role;

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setImgAvata(String imgAvata) {
        this.imgAvata = imgAvata;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getImgAvata() {
        return imgAvata;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getRole() {
        return role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getId() {
        return id;
    }
}
