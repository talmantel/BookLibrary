package com.openu.sadna.booklibrary.network.pojo;

import com.google.gson.annotations.SerializedName;

public class Book {

    @SerializedName("authorFName")
    private String authorFName;

    @SerializedName("authorLName")
    private String authorLName;

    @SerializedName("name")
    private String name;

    @SerializedName("category")
    private String category;

    @SerializedName("description")
    private String description;

    @SerializedName("id")
    private int id;

    @SerializedName("isAvailable")
    private boolean isAvailable;

    public Book(String authorFName, String authorLName, String name, String category, String description, int id, boolean isAvailable) {
        this.authorFName = authorFName;
        this.authorLName = authorLName;
        this.name = name;
        this.category = category;
        this.description = description;
        this.id = id;
        this.isAvailable = isAvailable;
    }

    public String getAuthorFName() {
        return authorFName;
    }

    public String getAuthorLName() {
        return authorLName;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
