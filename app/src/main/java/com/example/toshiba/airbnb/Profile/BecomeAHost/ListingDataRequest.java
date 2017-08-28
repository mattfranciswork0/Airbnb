package com.example.toshiba.airbnb.Profile.BecomeAHost;

/**
 * Created by TOSHIBA on 27/08/2017.
 */

public class ListingDataRequest {
    final String first_name;
    final String last_name;
    final String email;
    final String password;
    final String phoneNum;

    ListingDataRequest(String first_name, String last_name, String email, String password, String phoneNum ){
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
    }
}
