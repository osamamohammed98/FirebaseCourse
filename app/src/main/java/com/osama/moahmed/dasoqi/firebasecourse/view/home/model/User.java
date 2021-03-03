package com.osama.moahmed.dasoqi.firebasecourse.view.home.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    String email;
    String fullName;
    String imageUrl;
    String password;
    String phone;
    String userID;

    public User() {
    }

    public User(String email, String fullName, String imageUrl, String password, String phone, String userID) {
        this.email = email;
        this.fullName = fullName;
        this.imageUrl = imageUrl;
        this.password = password;
        this.phone = phone;
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
