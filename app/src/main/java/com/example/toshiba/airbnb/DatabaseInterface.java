package com.example.toshiba.airbnb;

/**
 * Created by TOSHIBA on 29/07/2017.
 */


import com.example.toshiba.airbnb.Explore.Homes.DTOBookSchedule;
import com.example.toshiba.airbnb.Explore.POJO.POJOBookingDataGetResult;
import com.example.toshiba.airbnb.Explore.POJO.POJOListingData;
import com.example.toshiba.airbnb.Explore.POJO.POJOMultipleListingsDataGetResult;
import com.example.toshiba.airbnb.Explore.POJO.POJOTotalListings;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.DTO.DTOBooking;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.DTO.DTOCaption;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.DTO.DTODescription;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.DTO.DTOHouseRules;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.DTO.DTOAmenitiesItem;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.DTO.DTOListingImages;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.DTO.DTOPlaceLocation;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.DTO.DTOPrice;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.DTO.DTORoomsAndGuest;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.DTO.DTOAmenitiesSpace;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.DTO.DTOTitle;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.POJO.POJOBookingsToDelete;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.POJO.POJOYourBookingGetResult;
import com.example.toshiba.airbnb.Profile.BecomeAHost.POJOIdListing;
import com.example.toshiba.airbnb.Profile.BecomeAHost.DTOImageListing;
import com.example.toshiba.airbnb.Profile.BecomeAHost.DTOPublishListingDataRequest;
import com.example.toshiba.airbnb.Profile.UserProfile.DTO.DTOAboutMe;
import com.example.toshiba.airbnb.Profile.UserProfile.DTO.DTOEmailDetailEdit;
import com.example.toshiba.airbnb.Profile.UserProfile.DTO.DTOLanguagesDetailEdit;
import com.example.toshiba.airbnb.Profile.UserProfile.DTO.DTOLocationDetailEdit;
import com.example.toshiba.airbnb.Profile.UserProfile.DTO.DTOPhoneNumDetailEdit;
import com.example.toshiba.airbnb.Profile.UserProfile.DTO.DTOWorkDetailEdit;
import com.example.toshiba.airbnb.Profile.POJOUserData;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.POJO.POJOListingImageAndTitleGetResult;
import com.example.toshiba.airbnb.UserAuthentication.Registration.POJOEmailResult;
import com.example.toshiba.airbnb.UserAuthentication.LogIn.DTOLogInRequest;
import com.example.toshiba.airbnb.UserAuthentication.LogIn.POJOPasswordMatch;
import com.example.toshiba.airbnb.UserAuthentication.DTOUserRegistrationRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Owner on 2017-06-19.
 */

public interface DatabaseInterface {

    @GET("/findUser/{email}")
    Call<POJOEmailResult> findUser(@Path("email") String email);

    @Headers("Content-Type: application/json")
    @POST("/register")
    Call<Void> insertUserRegistration(@Body DTOUserRegistrationRequest body); //Alter to Call<Void> later

    @Headers("Content-Type: application/json")
    @POST("/login")
    Call<POJOPasswordMatch> findLogInData(@Body DTOLogInRequest body);

    @Headers("Content-Type: application/json")
    @POST("/insertListingData")
    Call<POJOIdListing> insertListingData(@Body DTOPublishListingDataRequest body);

    @Headers("Content-Type: application/json")
    @POST("/insertListingImages")
    Call<Void> insertListingImages(@Body DTOImageListing body);

    @GET("/listingImageAndTitle/{user_id}")
    Call<POJOListingImageAndTitleGetResult> getListingImageAndTitle(@Path("user_id") int user_id);

    @GET("/listingData/{id}")
    Call<POJOListingData> getListingData(@Path("id") int user_id);

    @Headers("Content-Type: application/json")
    @POST("/bookSchedule/{id}/{listing_id}")
    Call<Void> insertBookingSchedule(@Path("id") int user_id, @Path("listing_id") int listing_id,
                                     @Body DTOBookSchedule body);

    @GET("/getBookingSchedules/{id}/{listing_id}")
    Call<POJOBookingDataGetResult> getBookingSchedules(@Path("id") int user_id, @Path("listing_id") int listing_id);

    @GET("/multipleListingsData/{showRowsAfter}/{showAmountOfRows}/{country}/{street}/{city}/{state}/{total_guest}/{suitable_for_infants}/{suitable_for_children}/{suitable_for_pets}")
    Call<POJOMultipleListingsDataGetResult> getMultipleListingsData(@Path("showRowsAfter") int showRowsAfter,
                                                                    @Path("showAmountOfRows") int showAmountOfRow,
                                                                    @Path("country") String country,
                                                                    @Path("street") String street,
                                                                    @Path("city") String city,
                                                                    @Path("state") String state,
                                                                    @Path("total_guest") String total_guest,
                                                                    @Path("suitable_for_infants") String suitable_for_infants,
                                                                    @Path("suitable_for_children") String suitable_for_children,
                                                                    @Path("suitable_for_pets") String suitable_for_pets);

    @GET("/totalListingsData/{country}/{street}/{city}/{state}/{total_guest}/{suitable_for_infants}/{suitable_for_children}/{suitable_for_pets}")
    Call<POJOTotalListings> getTotalListings (@Path("country") String country,
    @Path("street") String street,
    @Path("city") String city,
    @Path("state") String state,
    @Path("total_guest") String total_guest,
    @Path("suitable_for_infants") String suitable_for_infants,
    @Path("suitable_for_children") String suitable_for_children,
    @Path("suitable_for_pets") String suitable_for_pets);

    @GET("/getUserData/{id}")
    Call<POJOUserData> getUserData(@Path("id") int user_id);

    //HostProfileEdit Section
    @Headers("Content-Type: application/json")
    @POST("/insertProfileImagePath/{id}/{profile_image_path}")
    Call<Void> insertProfileImagePath(@Path("id") int user_id, @Path("profile_image_path") String profile_image_path);

    @Headers("Content-Type: application/json")
    @POST("/insertAboutMe/{id}")
    Call<Void> insertAboutMe(@Path("id") int user_id, @Body DTOAboutMe body);

    @Headers("Content-Type: application/json")
    @POST("/insertEmailDetailEdit/{id}")
    Call<Void> insertEmailDetailEdit(@Path("id") int user_id, @Body DTOEmailDetailEdit body);

    @Headers("Content-Type: application/json")
    @POST("/insertPhoneNumDetailEdit/{id}}")
    Call<Void> insertPhoneNumDetailEdit(@Path("id") int user_id, @Body DTOPhoneNumDetailEdit body);

    @Headers("Content-Type: application/json")
    @POST("/insertLocationDetailEdit/{id}")
    Call<Void> insertLocationDetailEdit(@Path("id") int user_id, @Body DTOLocationDetailEdit body);

    @Headers("Content-Type: application/json")
    @POST("/insertWorkDetailEdit/{id}")
    Call<Void> insertWorkDetailEdit(@Path("id") int user_id, @Body DTOWorkDetailEdit body);

    @Headers("Content-Type: application/json")
    @POST("/insertLanguagesDetailEdit/{id}")
    Call<Void> insertLanguagesDetailEdit(@Path("id") int user_id, @Body DTOLanguagesDetailEdit body);

    @GET("/bookingListingImageAndTitle/{user_id}")
    Call<POJOYourBookingGetResult> getBookingListingImageAndTitle(@Path("user_id") int user_id);

    @GET("/hi")
    Call<Void> hi();

    //edit listing
    @Headers("Content-Type: application/json")
    @POST("/updateListingImages/{listing_id}")
    Call<Void> updateListingImages(@Path("listing_id") int listing_id, @Body DTOListingImages body);

    @Headers("Content-Type: application/json")
    @POST("/updateCaption")
    Call<Void> updateCaption(@Body DTOCaption body);

    @Headers("Content-Type: application/json")
    @POST("/updateTitle/{listing_id}")
    Call<Void> updateTitle(@Path("listing_id") int listing_id, @Body DTOTitle body);

    @Headers("Content-Type: application/json")
    @POST("/updateDescription/{listing_id}")
    Call<Void> updateDescription(@Path("listing_id") int listing_id, @Body DTODescription body);

    @Headers("Content-Type: application/json")
    @POST("/updateRoomsAndGuests/{listing_id}")
    Call<Void> updateRoomsAndGuests(@Path("listing_id") int listing_id, @Body DTORoomsAndGuest body);


    @Headers("Content-Type: application/json")
    @POST("/updateAmenitiesItem/{listing_id}")
    Call<Void> updateAmenitiesItem(@Path("listing_id") int listing_id, @Body DTOAmenitiesItem body);


    @Headers("Content-Type: application/json")
    @POST("/updateAmenitiesSpace/{listing_id}")
    Call<Void> updateAmenitiesSpace(@Path("listing_id") int listing_id, @Body DTOAmenitiesSpace body);

    @Headers("Content-Type: application/json")
    @POST("/updateLocation/{listing_id}")
    Call<Void> updateLocation(@Path("listing_id") int listing_id, @Body DTOPlaceLocation body);

    @Headers("Content-Type: application/json")
    @POST("/updateHouseRules/{listing_id}")
    Call<Void> updateHouseRules(@Path("listing_id") int listing_id, @Body DTOHouseRules body);

    @Headers("Content-Type: application/json")
    @POST("/updateBooking/{listing_id}")
    Call<Void> updateBooking(@Path("listing_id") int listing_id, @Body DTOBooking body);

    @Headers("Content-Type: application/json")
    @POST("/updatePrice/{listing_id}")
    Call<Void> updatePrice(@Path("listing_id") int listing_id, @Body DTOPrice body);


    @GET("/bookingsToDeleteData/{listing_id}")
    Call<POJOBookingsToDelete> bookingsToDeleteData(@Path("listing_id") int id);


    @Headers("Content-Type: application/json")
    @DELETE("/deleteBookings/{listing_id}")
    Call<Void> deleteBookings(@Path("listing_id") int listing_id);

    //search bar
    @GET("/searchFilter/{country}/{street}/{city}/{state}/{total_guest}/{suitable_for_infants}/{suitable_for_children}/{suitable_for_pets}")
    Call<POJOMultipleListingsDataGetResult> searchFilter(@Path("country") String country,
                                                         @Path("street") String street,
                                                         @Path("city") String city,
                                                         @Path("state") String state,
                                                         @Path("total_guest") String total_guest,
                                                         @Path("suitable_for_infants") String suitable_for_infants,
                                                         @Path("suitable_for_children") String suitable_for_children,
                                                         @Path("suitable_for_pets") boolean suitable_for_pets
    );

}
