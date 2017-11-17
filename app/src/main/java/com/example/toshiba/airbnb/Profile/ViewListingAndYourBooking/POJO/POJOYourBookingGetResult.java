package com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by TOSHIBA on 25/10/2017.
 */

public class POJOYourBookingGetResult {

    @SerializedName("result")
    @Expose
    private List<POJOYourBooking> result = null;

    public List<POJOYourBooking> getResult() {
        return result;
    }

    public void setResult(List<POJOYourBooking> result) {
        this.result = result;
    }
}
