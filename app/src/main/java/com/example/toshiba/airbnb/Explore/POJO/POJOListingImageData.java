package com.example.toshiba.airbnb.Explore.POJO;

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
    private String caption;
    @SerializedName("listing_id")
    @Expose
    private Integer listingId;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Integer getListingId() {
        return listingId;
    }

    public void setListingId(Integer listingId) {
        this.listingId = listingId;
    }
}
