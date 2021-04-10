package com.openu.sadna.booklibrary.network.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Categories {
    @SerializedName("categories")
    private List<String> categories;

    public Categories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getCategories() {
        return categories;
    }
}
