package com.example.toshiba.airbnb.Explore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by TOSHIBA on 19/10/2017.
 */

public class POJOBookingData {
    @SerializedName("user_id")
    @Expose
    private Object userId;
    @SerializedName("listing_id")
    @Expose
    private Object listingId;
    @SerializedName("check_in")
    @Expose
    private String checkIn;
    @SerializedName("check_out")
    @Expose
    private String checkOut;

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public Object getListingId() {
        return listingId;
    }

    public void setListingId(Object listingId) {
        this.listingId = listingId;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }
}
