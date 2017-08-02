package com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.POJOMap.GMapsAutoComplete;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Owner on 2017-07-08.
 */

public class POJOPredictions {
    @SerializedName("predictions")
    @Expose
    private List<POJOPrediction> predictions = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<POJOPrediction> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<POJOPrediction> predictions) {
        this.predictions = predictions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
