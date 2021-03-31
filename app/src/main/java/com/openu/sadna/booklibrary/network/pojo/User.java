package com.openu.sadna.booklibrary.network.pojo;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("name")
    private String firstName;

    @SerializedName("website")
    private String lastName;

    @SerializedName("username")
    private String token;

    @SerializedName("isAdmin")
    private Boolean isAdmin;

    public User(String firstName, String lastName, String token, boolean isAdmin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.token = token;
        this.isAdmin = isAdmin;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getToken() {
        return token;
    }

    public Boolean isAdmin() {
        return isAdmin;
    }
}
