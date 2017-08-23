package com.example.toshiba.airbnb;

/**
 * Created by TOSHIBA on 29/07/2017.
 */


public class UserRegistrationRequest {
    final String first_name;
    final String last_name;
    final String email;
    final String password;
    final String phoneNum;

    UserRegistrationRequest(String first_name, String last_name, String email, String password, String phoneNum ){
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
    }

}
