package com.example.toshiba.airbnb.Profile.BecomeAHost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by TOSHIBA on 30/08/2017.
 */

public class POJOIdListing {
    @SerializedName("id")
    @Expose
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
