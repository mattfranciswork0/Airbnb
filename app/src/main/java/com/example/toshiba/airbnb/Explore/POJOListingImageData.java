package com.example.toshiba.airbnb.Explore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by TOSHIBA on 26/09/2017.
 */

public class POJOListingImageData {
    @SerializedName("image_path")
    @Expose
    private String imagePath;
    @SerializedName("caption")
    @Expose
    private Object caption;
    @SerializedName("listing_id")
    @Expose
    private Integer listingId;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Object getCaption() {
        return caption;
    }

    public void setCaption(Object caption) {
        this.caption = caption;
    }

    public Integer getListingId() {
        return listingId;
    }

    public void setListingId(Integer listingId) {
        this.listingId = listingId;
    }
}
