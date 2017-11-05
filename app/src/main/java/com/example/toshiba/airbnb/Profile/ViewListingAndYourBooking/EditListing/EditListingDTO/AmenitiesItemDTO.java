package com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.EditListingDTO;

/**
 * Created by TOSHIBA on 30/10/2017.
 */

public class AmenitiesItemDTO {
    private final boolean essentials;
    private final boolean internet;
    private final boolean shampoo;
    private final boolean hangers;
    private final boolean tv;
    private final boolean heating;
    private final boolean air_conditioning;
    private final boolean breakfast;

    public AmenitiesItemDTO(boolean essentials, boolean internet, boolean shampoo, boolean hangers,
                            boolean tv, boolean heating, boolean air_conditioning, boolean breakfast){
        this.essentials = essentials;
        this.internet = internet;
        this.shampoo = shampoo;
        this.hangers = hangers;
        this.tv = tv;
        this.heating = heating;
        this.air_conditioning = air_conditioning;
        this.breakfast = breakfast;
    }
}
