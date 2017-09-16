package com.example.toshiba.airbnb.Profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by TOSHIBA on 15/09/2017.
 */

public class POJOImageAndListingGetResult {
    @SerializedName("result")
    @Expose
    private List<POJOImageAndListingResult> result = null;

    public List<POJOImageAndListingResult> getResult() {
        return result;
    }

    public void setResult(List<POJOImageAndListingResult> result) {
        this.result = result;
    }
}
