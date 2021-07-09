package com.openu.sadna.booklibrary.network.pojo;

import com.google.gson.annotations.SerializedName;

public class Book {

    @SerializedName("authorFName")
    private final String authorFName;

    @SerializedName("authorLName")
    private final String authorLName;

    @SerializedName("name")
    private final String name;

    @SerializedName("category")
    private final String category;

    @SerializedName("description")
    private final String description;

    @SerializedName("id")
    private final int id;

    @SerializedName("isAvailable")
    private final boolean isAvailable;

    @SerializedName("lendDetails")
    private final BookLendDetails lendDetails;

    public Book(String authorFName, String authorLName, String name, String category, String description, int id, boolean isAvailable, BookLendDetails lendDetails) {
        this.authorFName = authorFName;
        this.authorLName = authorLName;
        this.name = name;
        this.category = category;
        this.description = description;
        this.id = id;
        this.isAvailable = isAvailable;
        this.lendDetails = lendDetails;
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

    public BookLendDetails getLendDetails() {
        return lendDetails;
    }
}
