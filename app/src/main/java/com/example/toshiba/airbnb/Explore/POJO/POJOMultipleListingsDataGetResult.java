package com.example.toshiba.airbnb.Explore.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by TOSHIBA on 15/10/2017.
 */

public class POJOMultipleListingsDataGetResult {
    @SerializedName("result")
    @Expose
    private List<POJOMultipleListingsData> result = null;

    public List<POJOMultipleListingsData> getResult() {
        return result;
    }

    public void setResult(List<POJOMultipleListingsData> result) {
        this.result = result;
    }
}
