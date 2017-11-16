package com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.DTO;

/**
 * Created by TOSHIBA on 31/10/2017.
 */

public class DTOListingImages {
    private final String image_path;
    private final String caption;

    public DTOListingImages(String image_path, String caption){
        this.image_path = image_path;
        this.caption = caption;
    }
}
