package com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.POJOMap.GMapsPlaceDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Owner on 2017-07-08.
 */

public class POJOResult {

    @SerializedName("address_components")
    @Expose
    private List<POJOAddressComponent> addressComponents = null;
    @SerializedName("formatted_address")
    @Expose
    private String formattedAddress;
    @SerializedName("geometry")
    @Expose
    private POJOGeometry geometry;
    @SerializedName("place_id")

    public List<POJOAddressComponent> getAddressComponents() {
        return addressComponents;
    }

    public void setAddressComponents(List<POJOAddressComponent> addressComponents) {
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
