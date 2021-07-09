package com.openu.sadna.booklibrary.network.pojo;

import com.google.gson.annotations.SerializedName;

public class BookLendDetails {

    @SerializedName("lentTime")
    private final long lentTime;

    @SerializedName("returnTime")
    private final Long returnTime;

    @SerializedName("lentTo")
    private final User lentTo;

    public BookLendDetails(Long lentTime, Long returnTime, User lentTo) {
        this.lentTime = lentTime;
        this.returnTime = returnTime;
        this.lentTo = lentTo;
    }

    public long getLentTime() {
        return lentTime;
    }

    public Long getReturnTime() {
        return returnTime;
    }

    public User getLentTo() {
        return lentTo;
    }
}
