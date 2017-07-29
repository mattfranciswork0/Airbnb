package com.example.toshiba.airbnb;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Owner on 2017-06-19.
 */

public class POJOResponse {
    @SerializedName("result")
    @Expose
    private List<POJOUserRegistration> result = null;

    public List<POJOUserRegistration> getResult() {
        return result;
    }

    public void setResult(List<POJOUserRegistration> result) {
        this.result = result;
    }
}
