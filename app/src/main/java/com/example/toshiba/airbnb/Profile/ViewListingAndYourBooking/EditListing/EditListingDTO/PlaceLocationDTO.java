package com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.EditListingDTO;

/**
 * Created by TOSHIBA on 30/10/2017.
 */

public class PlaceLocationDTO {
    private final String country;
    private final String street;
    private final String extra_place_details;
    private final String city;
    private final String state;

    public PlaceLocationDTO(String country, String street, String extra_place_details, String city, String state){
        this.country = country;
        this.street = street;
        this.extra_place_details = extra_place_details;
        this.city = city;
        this.state = state;
    }
}
