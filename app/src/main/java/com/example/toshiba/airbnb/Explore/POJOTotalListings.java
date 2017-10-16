package com.example.toshiba.airbnb.Explore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by TOSHIBA on 14/10/2017.
 */

public class POJOTotalListings {
    @SerializedName("total_listings")
    @Expose
    private String totalListings;

    public String getTotalListings() {
        return totalListings;
    }

    public void setTotalListings(String totalListings) {
        this.totalListings = totalListings;
    }
}
