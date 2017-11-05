package com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.EditListingDTO;

/**
 * Created by TOSHIBA on 30/10/2017.
 */

public class BookingDTO {
    private final String listing_length;
    private final String arrive_after;
    private final String leave_before;
    private final String max_stay;
    private final String min_stay;

    public BookingDTO(String listing_length, String arrive_after, String leave_before,
                      String max_stay, String min_stay){
        this.listing_length = listing_length;
        this.arrive_after = arrive_after;
        this.leave_before = leave_before;
        this.max_stay = max_stay;
        this.min_stay = min_stay;
        

    }
}
