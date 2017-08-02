package com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.POJOMap.GMapsPlaceDetails;

import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.POJOMap.GMapsAutoComplete.POJOTerm;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Owner on 2017-07-08.
 */

public class POJOGeometry {
    @SerializedName("location")
    @Expose
    private POJOLocation location;
    @SerializedName("location_type")
    @Expose
    private String locationType;

    public POJOLocation getLocation() {
        return location;
    }

    public void setLocation(POJOLocation location) {
        this.location = location;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }
}
