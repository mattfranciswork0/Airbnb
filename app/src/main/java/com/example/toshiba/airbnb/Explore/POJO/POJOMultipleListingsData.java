package com.example.toshiba.airbnb.Explore.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by TOSHIBA on 15/10/2017.
 */

public class POJOMultipleListingsData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("property_ownership")
    @Expose
    private String propertyOwnership;
    @SerializedName("property_type")
    @Expose
    private String propertyType;
    @SerializedName("total_beds")
    @Expose
    private String totalBeds;
    @SerializedName("place_title")
    @Expose
    private String placeTitle;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("image_path")
    @Expose
    private String imagePath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPropertyOwnership() {
        return propertyOwnership;
    }

    public void setPropertyOwnership(String propertyOwnership) {
        this.propertyOwnership = propertyOwnership;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getTotalBeds() {
        return totalBeds;
    }

    public void setTotalBeds(String totalBeds) {
        this.totalBeds = totalBeds;
    }

    public String getPlaceTitle() {
        return placeTitle;
    }

    public void setPlaceTitle(String placeTitle) {
        this.placeTitle = placeTitle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
