package com.example.toshiba.airbnb.Profile.BecomeAHost;

/**
 * Created by TOSHIBA on 01/09/2017.
 */

public class ImageListingRequest {
    public String image_path;
    public int listing_id;
    public ImageListingRequest(String image_path, int listing_id){
        this.image_path = image_path;
        this.listing_id = listing_id;
    }
}
