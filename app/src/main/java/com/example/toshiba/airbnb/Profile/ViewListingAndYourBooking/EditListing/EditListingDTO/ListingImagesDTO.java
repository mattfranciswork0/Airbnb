package com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.EditListingDTO;

/**
 * Created by TOSHIBA on 31/10/2017.
 */

public class ListingImagesDTO {
    private final String image_path;
    private final String caption;

    public ListingImagesDTO(String image_path, String caption){
        this.image_path = image_path;
        this.caption = caption;
    }
}
