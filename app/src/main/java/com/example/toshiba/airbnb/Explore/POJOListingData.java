package com.example.toshiba.airbnb.Explore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by TOSHIBA on 18/09/2017.
 */

public class POJOListingData {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("property_ownership")
    @Expose
    private String propertyOwnership;
    @SerializedName("property_type")
    @Expose
    private String propertyType;
    @SerializedName("total_guest")
    @Expose
    private String totalGuest;
    @SerializedName("total_bedrooms")
    @Expose
    private String totalBedrooms;
    @SerializedName("total_beds")
    @Expose
    private String totalBeds;
    @SerializedName("total_bathrooms")
    @Expose
    private String totalBathrooms;
    @SerializedName("bathroom_type")
    @Expose
    private String bathroomType;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("extra_place_details")
    @Expose
    private String extraPlaceDetails;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("essentials")
    @Expose
    private Integer essentials;
    @SerializedName("internet")
    @Expose
    private Integer internet;
    @SerializedName("shampoo")
    @Expose
    private Integer shampoo;
    @SerializedName("hangers")
    @Expose
    private Integer hangers;
    @SerializedName("tv")
    @Expose
    private Integer tv;
    @SerializedName("heating")
    @Expose
    private Integer heating;
    @SerializedName("air_conditioning")
    @Expose
    private Integer airConditioning;
    @SerializedName("breakfast")
    @Expose
    private Integer breakfast;
    @SerializedName("kitchen")
    @Expose
    private Integer kitchen;
    @SerializedName("laundry")
    @Expose
    private Integer laundry;
    @SerializedName("parking")
    @Expose
    private Integer parking;
    @SerializedName("elevator")
    @Expose
    private Integer elevator;
    @SerializedName("pool")
    @Expose
    private Integer pool;
    @SerializedName("gym")
    @Expose
    private Integer gym;
    @SerializedName("place_description")
    @Expose
    private String placeDescription;
    @SerializedName("place_title")
    @Expose
    private String placeTitle;
    @SerializedName("suitable_for_children")
    @Expose
    private Integer suitableForChildren;
    @SerializedName("suitable_for_infants")
    @Expose
    private Integer suitableForInfants;
    @SerializedName("suitable_for_pets")
    @Expose
    private Integer suitableForPets;
    @SerializedName("smoking_allowed")
    @Expose
    private Integer smokingAllowed;
    @SerializedName("parties_allowed")
    @Expose
    private Integer partiesAllowed;
    @SerializedName("additional_rules")
    @Expose
    private String additionalRules;
    @SerializedName("listing_length")
    @Expose
    private String listingLength;
    @SerializedName("arrive_after")
    @Expose
    private String arriveAfter;
    @SerializedName("leave_before")
    @Expose
    private String leaveBefore;
    @SerializedName("min_stay")
    @Expose
    private String minStay;
    @SerializedName("max_stay")
    @Expose
    private String maxStay;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("date_listed")
    @Expose
    private String dateListed;
    @SerializedName("image_data")
    @Expose
    private List<POJOListingImageData> imageData = null;

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

    public String getTotalGuest() {
        return totalGuest;
    }

    public void setTotalGuest(String totalGuest) {
        this.totalGuest = totalGuest;
    }

    public String getTotalBedrooms() {
        return totalBedrooms;
    }

    public void setTotalBedrooms(String totalBedrooms) {
        this.totalBedrooms = totalBedrooms;
    }

    public String getTotalBeds() {
        return totalBeds;
    }

    public void setTotalBeds(String totalBeds) {
        this.totalBeds = totalBeds;
    }

    public String getTotalBathrooms() {
        return totalBathrooms;
    }

    public void setTotalBathrooms(String totalBathrooms) {
        this.totalBathrooms = totalBathrooms;
    }

    public String getBathroomType() {
        return bathroomType;
    }

    public void setBathroomType(String bathroomType) {
        this.bathroomType = bathroomType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getExtraPlaceDetails() {
        return extraPlaceDetails;
    }

    public void setExtraPlaceDetails(String extraPlaceDetails) {
        this.extraPlaceDetails = extraPlaceDetails;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Integer getEssentials() {
        return essentials;
    }

    public void setEssentials(Integer essentials) {
        this.essentials = essentials;
    }

    public Integer getInternet() {
        return internet;
    }

    public void setInternet(Integer internet) {
        this.internet = internet;
    }

    public Integer getShampoo() {
        return shampoo;
    }

    public void setShampoo(Integer shampoo) {
        this.shampoo = shampoo;
    }

    public Integer getHangers() {
        return hangers;
    }

    public void setHangers(Integer hangers) {
        this.hangers = hangers;
    }

    public Integer getTv() {
        return tv;
    }

    public void setTv(Integer tv) {
        this.tv = tv;
    }

    public Integer getHeating() {
        return heating;
    }

    public void setHeating(Integer heating) {
        this.heating = heating;
    }

    public Integer getAirConditioning() {
        return airConditioning;
    }

    public void setAirConditioning(Integer airConditioning) {
        this.airConditioning = airConditioning;
    }

    public Integer getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Integer breakfast) {
        this.breakfast = breakfast;
    }

    public Integer getKitchen() {
        return kitchen;
    }

    public void setKitchen(Integer kitchen) {
        this.kitchen = kitchen;
    }

    public Integer getLaundry() {
        return laundry;
    }

    public void setLaundry(Integer laundry) {
        this.laundry = laundry;
    }

    public Integer getParking() {
        return parking;
    }

    public void setParking(Integer parking) {
        this.parking = parking;
    }

    public Integer getElevator() {
        return elevator;
    }

    public void setElevator(Integer elevator) {
        this.elevator = elevator;
    }

    public Integer getPool() {
        return pool;
    }

    public void setPool(Integer pool) {
        this.pool = pool;
    }

    public Integer getGym() {
        return gym;
    }

    public void setGym(Integer gym) {
        this.gym = gym;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public void setPlaceDescription(String placeDescription) {
        this.placeDescription = placeDescription;
    }

    public String getPlaceTitle() {
        return placeTitle;
    }

    public void setPlaceTitle(String placeTitle) {
        this.placeTitle = placeTitle;
    }

    public Integer getSuitableForChildren() {
        return suitableForChildren;
    }

    public void setSuitableForChildren(Integer suitableForChildren) {
        this.suitableForChildren = suitableForChildren;
    }

    public Integer getSuitableForInfants() {
        return suitableForInfants;
    }

    public void setSuitableForInfants(Integer suitableForInfants) {
        this.suitableForInfants = suitableForInfants;
    }

    public Integer getSuitableForPets() {
        return suitableForPets;
    }

    public void setSuitableForPets(Integer suitableForPets) {
        this.suitableForPets = suitableForPets;
    }

    public Integer getSmokingAllowed() {
        return smokingAllowed;
    }

    public void setSmokingAllowed(Integer smokingAllowed) {
        this.smokingAllowed = smokingAllowed;
    }

    public Integer getPartiesAllowed() {
        return partiesAllowed;
    }

    public void setPartiesAllowed(Integer partiesAllowed) {
        this.partiesAllowed = partiesAllowed;
    }

    public String getAdditionalRules() {
        return additionalRules;
    }

    public void setAdditionalRules(String additionalRules) {
        this.additionalRules = additionalRules;
    }

    public String getListingLength() {
        return listingLength;
    }

    public void setListingLength(String listingLength) {
        this.listingLength = listingLength;
    }

    public String getArriveAfter() {
        return arriveAfter;
    }

    public void setArriveAfter(String arriveAfter) {
        this.arriveAfter = arriveAfter;
    }

    public String getLeaveBefore() {
        return leaveBefore;
    }

    public void setLeaveBefore(String leaveBefore) {
        this.leaveBefore = leaveBefore;
    }

    public String getMinStay() {
        return minStay;
    }

    public void setMinStay(String minStay) {
        this.minStay = minStay;
    }

    public String getMaxStay() {
        return maxStay;
    }

    public void setMaxStay(String maxStay) {
        this.maxStay = maxStay;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDateListed() {
        return dateListed;
    }

    public void setDateListed(String dateListed) {
        this.dateListed = dateListed;
    }

    public List<POJOListingImageData> getImageData() {
        return imageData;
    }

    public void setImageData(List<POJOListingImageData> imageData) {
        this.imageData = imageData;
    }
}
