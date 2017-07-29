package com.example.toshiba.airbnb;

/**
 * Created by TOSHIBA on 29/07/2017.
 */



import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Owner on 2017-06-19.
 */

public interface DatabaseInterface {
    @Headers("Content-Type: application/json")
    @POST("/register")
    Call<POJOUserRegistration> insertUserRegistration(@Body UserRegistrationRequest body);
}
