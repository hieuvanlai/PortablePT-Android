package com.example.hihihahahehe.portablept.models.JSONModel;

/**
 * Created by hieuit on 8/26/17.
 */

public class UserJSONModel {
    private String phoneNumber;
    private String password;
    private String username;
    private String id;
    private String imgAvata;
    private String name;
    private String email;
    private String gender;
    private String birthday;
    private String location;
    private String role;

    public UserJSONModel(String phoneNumber, String password, String username, String id, String imgAvata, String name, String email, String gender, String birthday, String location, String role) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.username = username;
        this.id = id;
        this.imgAvata = imgAvata;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.birthday = birthday;
        this.location = location;
        this.role = role;
    }

    public UserJSONModel() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgAvata() {
        return imgAvata;
    }

    public void setImgAvata(String imgAvata) {
        this.imgAvata = imgAvata;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
