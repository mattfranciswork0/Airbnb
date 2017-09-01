package com.example.toshiba.airbnb.Profile.BecomeAHost;

/**
 * Created by TOSHIBA on 27/08/2017.
 */

public class PublishListingDataRequest {
     int user_id;
     String property_ownership;
      String property_type;
      String total_guest;
      String total_bedrooms;
      String total_beds;
      String total_bathrooms;
      String bathroom_type;
      String country;
      String street;
      String extra_place_details;
      String city;
      String state;
      String lng;
      String lat;

      boolean essentials;
      boolean internet;
      boolean shampoo;
      boolean hangers;
      boolean tv;
      boolean heating;
      boolean air_conditioning;
      boolean breakfast;
      boolean kitchen;
      boolean laundry;
      boolean parking;
      boolean elevator;
      boolean pool;
      boolean gym;
    public PublishListingDataRequest(int user_id,String property_ownership, String property_type,
                                     String total_guest, String total_bedrooms, String total_beds, String total_bathrooms,
                                     String bathroom_type, String country, String street, String extra_place_details,
                                     String city, String state, String lng, String lat,


                                     boolean essentials, boolean internet, boolean shampoo, boolean hangers, boolean tv, boolean heating, boolean air_conditioning,
                                     boolean breakfast, boolean kitchen, boolean laundry, boolean parking, boolean elevator,
                                     boolean pool, boolean gym
    ){
        this.user_id = user_id;
        this.property_ownership = property_ownership;
        this.property_type = property_type;
        this.total_guest = total_guest;
        this.total_bedrooms = total_bedrooms;
        this.total_beds = total_beds;
        this.total_bathrooms = total_bathrooms;
        this.bathroom_type = bathroom_type;
        this.country = country;
        this.street = street;
        this.extra_place_details = extra_place_details;
        this.city = city;
        this.state = state;
        this.lng = lng;
        this.lat = lat;

        this.essentials = essentials;
        this.internet = internet;
        this.shampoo = shampoo;
        this.hangers = hangers;
        this.tv = tv;
        this.heating = heating;
        this.air_conditioning = air_conditioning;
        this.breakfast = breakfast;
        this.kitchen = kitchen;
        this.laundry = laundry;
        this.parking = parking;
        this.elevator = elevator;
        this.pool = pool;
        this.gym = gym;
    }
}
