package com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.DTO;

/**
 * Created by TOSHIBA on 01/11/2017.
 */

public class DTOCaption {
    private final String caption;
    private final String image_path;
    public DTOCaption(String image_path, String caption){
        this.image_path = image_path;
        this.caption = caption;
    }
}
