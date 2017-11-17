package com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by TOSHIBA on 15/09/2017.
 */

public class POJOListingImageAndTitleResult {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("place_title")
    @Expose
    private String placeTitle;
    @SerializedName("image_path")
    @Expose
    private String imagePath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlaceTitle() {
        return placeTitle;
    }

    public void setPlaceTitle(String placeTitle) {
        this.placeTitle = placeTitle;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
