package com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by TOSHIBA on 15/09/2017.
 */

public class POJOListingImageAndTitleGetResult {
    @SerializedName("result")
    @Expose
    private List<POJOListingImageAndTitleResult> result = null;

    public List<POJOListingImageAndTitleResult> getResult() {
        return result;
    }

    public void setResult(List<POJOListingImageAndTitleResult> result) {
        this.result = result;
    }
}
