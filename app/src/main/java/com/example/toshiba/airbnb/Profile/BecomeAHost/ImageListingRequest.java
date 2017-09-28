package com.example.toshiba.airbnb.Profile.BecomeAHost;

import java.util.ArrayList;

/**
 * Created by TOSHIBA on 01/09/2017.
 */

public class ImageListingRequest {
    public String[] image_path;
    public String[] caption;
    public int listing_id;
    public ImageListingRequest(String[] image_path, String[] caption, int listing_id){
        this.image_path = image_path;
        this.caption = caption;
        this.listing_id = listing_id;
    }
}
