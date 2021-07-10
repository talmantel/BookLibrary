package com.openu.sadna.booklibrary.network.pojo;

import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("content")
    private final String content;

    @SerializedName("time")
    private final long time;

    @SerializedName("reviewer")
    private final User reviewer;

    public Review(String content, long time, User reviewer) {
        this.content = content;
        this.time = time;
        this.reviewer = reviewer;
    }

    public String getContent() {
        return content;
    }

    public long getTime() {
        return time;
    }

    public User getReviewer() {
        return reviewer;
    }
}
