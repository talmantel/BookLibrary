package com.openu.sadna.booklibrary.network.pojo;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("name")
    private final String firstName;

    @SerializedName("website")
    private final String lastName;

    @SerializedName("username")
    private final String token;

    @SerializedName("isAdmin")
    private final Boolean isAdmin;

    public User(String firstName, String lastName, String token, Boolean isAdmin) {
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

    public boolean isAdmin() {
        if(isAdmin == null)
            return false;
        return isAdmin;
    }
}
