package com.example.toshiba.airbnb.Profile.BecomeAHost;

/**
 * Created by TOSHIBA on 27/08/2017.
 */

public class PublishListingDataRequestDTO {
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

    String place_description;
    String place_title;

    boolean suitable_for_children;
    boolean suitable_for_infants;
    boolean suitable_for_pets;
    boolean smoking_allowed;
    boolean parties_allowed;
    String additional_rules;
    String listing_length;
    String arrive_after;
    String leave_before;
    String min_stay;
    String max_stay;
    String price;

    String date_listed;

    public PublishListingDataRequestDTO(int user_id, String property_ownership, String property_type,
                                        String total_guest, String total_bedrooms, String total_beds, String total_bathrooms,
                                        String bathroom_type, String country, String street, String extra_place_details,
                                        String city, String state, String lng, String lat,


                                        boolean essentials, boolean internet, boolean shampoo, boolean hangers, boolean tv, boolean heating, boolean air_conditioning,
                                        boolean breakfast, boolean kitchen, boolean laundry, boolean parking, boolean elevator,
                                        boolean pool, boolean gym,

                                        String place_description, String place_title,

                                        boolean suitable_for_children, boolean suitable_for_infants, boolean suitable_for_pets, boolean smoking_allowed,
                                        boolean parties_allowed, String additional_rules, String listing_length, String arrive_after, String leave_before,
                                        String min_stay, String max_stay, String price,

                                        String date_listed)

    {
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

        this.place_description = place_description;
        this.place_title = place_title;

        this.suitable_for_children = suitable_for_children;
        this.suitable_for_infants = suitable_for_infants;
        this.suitable_for_pets = suitable_for_pets;
        this.smoking_allowed = smoking_allowed;
        this.parties_allowed = parties_allowed;
        this.additional_rules = additional_rules;
        this.listing_length = listing_length;
        this.arrive_after = arrive_after;
        this.leave_before = leave_before;
        this.min_stay = min_stay;
        this.max_stay = max_stay;
        this.price = price;

        this.date_listed = date_listed;
    }
}
