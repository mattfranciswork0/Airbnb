package com.example.toshiba.airbnb;

/**
 * Created by TOSHIBA on 29/07/2017.
 */



import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Owner on 2017-06-19.
 */

public interface DatabaseInterface {

    @Headers("Content-Type: application/json")
    @POST("/login")
    Call<EmailResult> findUser(@Path("email") String email);

    @Headers("Content-Type: application/json")
    @POST("/register")
    Call<Void> insertUserRegistration(@Body UserRegistrationRequest body); //Alter to Call<Void> later

    @Headers("Content-Type: application/json")
    @POST("/login")
    Call<PasswordMatch> findLogInData(@Body LogInRequest body);


}
