package com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions;

import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.POJOMap.GMapsAutoComplete.POJOPredictions;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.POJOMap.GMapsPlaceDetails.POJOGeometry;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Owner on 2017-07-08.
 */

public class ResultsPOJO {

    @SerializedName("address_components")
    @Expose
    private List<POJOPredictions> addressComponents = null;
    @SerializedName("formatted_address")
    @Expose
    private String formattedAddress;
    @SerializedName("geometry")
    @Expose
    private POJOGeometry geometry;
    @SerializedName("place_id")

    public List<POJOPredictions> getAddressComponents() {
        return addressComponents;
    }

    public void setAddressComponents(List<POJOPredictions> addressComponents) {
        this.addressComponents = addressComponents;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public POJOGeometry getGeometry() {
        return geometry;
    }

    public void setGeometry(POJOGeometry geometry) {
        this.geometry = geometry;
    }

}
