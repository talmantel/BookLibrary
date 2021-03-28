package com.openu.sadna.booklibrary.data.model;

public class User {

    private String firstName, lastName, token;
    private boolean isAdmin;

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

    public boolean isAdmin() {
        return isAdmin;
    }
}
