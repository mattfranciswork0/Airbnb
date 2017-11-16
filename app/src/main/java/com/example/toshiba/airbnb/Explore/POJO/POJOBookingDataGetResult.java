package com.example.toshiba.airbnb.Explore.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by TOSHIBA on 20/10/2017.
 */

public class POJOBookingDataGetResult {
    @SerializedName("result")
    @Expose
    private List<POJOBookingData> result = null;

    public List<POJOBookingData> getResult() {
        return result;
    }

    public void setResult(List<POJOBookingData> result) {
        this.result = result;
    }
}
