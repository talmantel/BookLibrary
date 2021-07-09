package com.openu.sadna.booklibrary.network.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Review {

    @SerializedName("content")
    private final String content;

    @SerializedName("time")
    private final long time;

    public Review(String content, long time) {
        this.content = content;
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public Date getTime() {
        return new Date(time);
    }
}
