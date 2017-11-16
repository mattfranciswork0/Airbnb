package com.example.toshiba.airbnb.UserAuthentication.LogIn;

/**
 * Created by TOSHIBA on 29/07/2017.
 */

public class DTOLogInRequest {
    String email;
    String password;

    DTOLogInRequest(String email, String password){
        this.email = email;
        this.password = password;
    }
}
