package com.example.mupin_v2.models;

public class UserModel {

    private String userEmail, userName, tookenId;


    public UserModel(String userEmail, String userName, String tookenId) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.tookenId = tookenId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public String getTookenId() {
        return tookenId;
    }

    public UserModel() {
    }
}


