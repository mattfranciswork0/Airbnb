package com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.DTO;

/**
 * Created by TOSHIBA on 30/10/2017.
 */

public class DTOHouseRules {
    private final boolean suitable_for_children;
    private final boolean suitable_for_infants;
    private final boolean suitable_for_pets;
    private final boolean smoking_allowed;
    private final boolean parties_allowed;
    private final String additional_rules;

    public DTOHouseRules(boolean suitable_for_children, boolean suitable_for_infants, boolean suitable_for_pets,
                         boolean smoking_allowed, boolean parties_allowed, String additional_rules){
        this.suitable_for_children = suitable_for_children;
        this.suitable_for_infants = suitable_for_infants;
        this.suitable_for_pets = suitable_for_pets;
        this.smoking_allowed = smoking_allowed;
        this.parties_allowed = parties_allowed;
        this.additional_rules = additional_rules;



    }
}
