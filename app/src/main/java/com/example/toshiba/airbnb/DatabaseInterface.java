package com.example.toshiba.airbnb;

/**
 * Created by TOSHIBA on 29/07/2017.
 */



import com.example.toshiba.airbnb.Profile.BecomeAHost.IdListing;
import com.example.toshiba.airbnb.Profile.BecomeAHost.ImageListingRequest;
import com.example.toshiba.airbnb.Profile.BecomeAHost.PublishListingDataRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Owner on 2017-06-19.
 */

public interface DatabaseInterface {

    @Headers("Content-Type: application/json")
    @GET("/findUser/{email}")
    Call<EmailResult> findUser(@Path("email") String email);

    @Headers("Content-Type: application/json")
    @POST("/register")
    Call<Void> insertUserRegistration(@Body UserRegistrationRequest body); //Alter to Call<Void> later

    @Headers("Content-Type: application/json")
    @POST("/login")
    Call<PasswordMatch> findLogInData(@Body LogInRequest body);

    @Headers("Content-Type: application/json")
    @POST("/insertListingData")
    Call<IdListing> insertListingData(@Body PublishListingDataRequest body);

    @Headers("Content-Type: application/json")
    @POST("/insertListingImages")
    Call<Void> insertListingImages(@Body ImageListingRequest body);

    @Headers("Content-Type: application/json")
    @GET("/hi")
    Call<Void> hi();

}
