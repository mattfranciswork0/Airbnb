package com.example.toshiba.airbnb;

/**
 * Created by TOSHIBA on 29/07/2017.
 */

public class LogInRequest {
    String email;
    String password;

    LogInRequest(String email, String password){
        this.email = email;
        this.password = password;
    }
}
