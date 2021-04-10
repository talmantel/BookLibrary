package com.openu.sadna.booklibrary.network.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Books {
    @SerializedName("books")
    private List<Book> books;

    public Books(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }
}
