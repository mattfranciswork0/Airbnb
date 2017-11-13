package com.example.toshiba.airbnb;

/**
 * Created by TOSHIBA on 29/07/2017.
 */


import com.example.toshiba.airbnb.Explore.DTOBookSchedule;
import com.example.toshiba.airbnb.Explore.POJOBookingData;
import com.example.toshiba.airbnb.Explore.POJOBookingDataGetResult;
import com.example.toshiba.airbnb.Explore.POJOListingData;
import com.example.toshiba.airbnb.Explore.POJOMultipleListingsData;
import com.example.toshiba.airbnb.Explore.POJOMultipleListingsDataGetResult;
import com.example.toshiba.airbnb.Explore.POJOTotalListings;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.EditListingDTO.BookingDTO;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.EditListingDTO.CaptionDTO;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.EditListingDTO.DescriptionDTO;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.EditListingDTO.HouseRulesDTO;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.EditListingDTO.AmenitiesItemDTO;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.EditListingDTO.ListingImagesDTO;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.EditListingDTO.PlaceLocationDTO;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.EditListingDTO.PriceDTO;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.EditListingDTO.RoomsAndGuestDTO;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.EditListingDTO.AmenitiesSpaceDTO;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.EditListingDTO.TitleDTO;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.POJOBookingsToDelete;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.POJOYourBookingGetResult;
import com.example.toshiba.airbnb.Profile.BecomeAHost.IdListing;
import com.example.toshiba.airbnb.Profile.BecomeAHost.ImageListingRequest;
import com.example.toshiba.airbnb.Profile.BecomeAHost.PublishListingDataRequestDTO;
import com.example.toshiba.airbnb.Profile.DTO.AboutMeDTO;
import com.example.toshiba.airbnb.Profile.DTO.EmailDetailEditDTO;
import com.example.toshiba.airbnb.Profile.DTO.LanguagesDetailEditDTO;
import com.example.toshiba.airbnb.Profile.DTO.LocationDetailEditDTO;
import com.example.toshiba.airbnb.Profile.DTO.PhoneNumDetailEditDTO;
import com.example.toshiba.airbnb.Profile.DTO.WorkDetailEditDTO;
import com.example.toshiba.airbnb.Profile.POJOUserData;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.POJOListingImageAndTitleGetResult;
import com.example.toshiba.airbnb.UserAuthentication.EmailResult;
import com.example.toshiba.airbnb.UserAuthentication.LogInRequest;
import com.example.toshiba.airbnb.UserAuthentication.PasswordMatch;
import com.example.toshiba.airbnb.UserAuthentication.UserRegistrationRequest;

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
    Call<EmailResult> findUser(@Path("email") String email);

    @Headers("Content-Type: application/json")
    @POST("/register")
    Call<Void> insertUserRegistration(@Body UserRegistrationRequest body); //Alter to Call<Void> later

    @Headers("Content-Type: application/json")
    @POST("/login")
    Call<PasswordMatch> findLogInData(@Body LogInRequest body);

    @Headers("Content-Type: application/json")
    @POST("/insertListingData")
    Call<IdListing> insertListingData(@Body PublishListingDataRequestDTO body);

    @Headers("Content-Type: application/json")
    @POST("/insertListingImages")
    Call<Void> insertListingImages(@Body ImageListingRequest body);

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
    Call<Void> insertAboutMe(@Path("id") int user_id, @Body AboutMeDTO body);

    @Headers("Content-Type: application/json")
    @POST("/insertEmailDetailEdit/{id}")
    Call<Void> insertEmailDetailEdit(@Path("id") int user_id, @Body EmailDetailEditDTO body);

    @Headers("Content-Type: application/json")
    @POST("/insertPhoneNumDetailEdit/{id}}")
    Call<Void> insertPhoneNumDetailEdit(@Path("id") int user_id, @Body PhoneNumDetailEditDTO body);

    @Headers("Content-Type: application/json")
    @POST("/insertLocationDetailEdit/{id}")
    Call<Void> insertLocationDetailEdit(@Path("id") int user_id, @Body LocationDetailEditDTO body);

    @Headers("Content-Type: application/json")
    @POST("/insertWorkDetailEdit/{id}")
    Call<Void> insertWorkDetailEdit(@Path("id") int user_id, @Body WorkDetailEditDTO body);

    @Headers("Content-Type: application/json")
    @POST("/insertLanguagesDetailEdit/{id}")
    Call<Void> insertLanguagesDetailEdit(@Path("id") int user_id, @Body LanguagesDetailEditDTO body);

    @GET("/bookingListingImageAndTitle/{user_id}")
    Call<POJOYourBookingGetResult> getBookingListingImageAndTitle(@Path("user_id") int user_id);

    @GET("/hi")
    Call<Void> hi();

    //edit listing
    @Headers("Content-Type: application/json")
    @POST("/updateListingImages/{listing_id}")
    Call<Void> updateListingImages(@Path("listing_id") int listing_id, @Body ListingImagesDTO body);

    @Headers("Content-Type: application/json")
    @POST("/updateCaption")
    Call<Void> updateCaption(@Body CaptionDTO body);

    @Headers("Content-Type: application/json")
    @POST("/updateTitle/{listing_id}")
    Call<Void> updateTitle(@Path("listing_id") int listing_id, @Body TitleDTO body);

    @Headers("Content-Type: application/json")
    @POST("/updateDescription/{listing_id}")
    Call<Void> updateDescription(@Path("listing_id") int listing_id, @Body DescriptionDTO body);

    @Headers("Content-Type: application/json")
    @POST("/updateRoomsAndGuests/{listing_id}")
    Call<Void> updateRoomsAndGuests(@Path("listing_id") int listing_id, @Body RoomsAndGuestDTO body);


    @Headers("Content-Type: application/json")
    @POST("/updateAmenitiesItem/{listing_id}")
    Call<Void> updateAmenitiesItem(@Path("listing_id") int listing_id, @Body AmenitiesItemDTO body);


    @Headers("Content-Type: application/json")
    @POST("/updateAmenitiesSpace/{listing_id}")
    Call<Void> updateAmenitiesSpace(@Path("listing_id") int listing_id, @Body AmenitiesSpaceDTO body);

    @Headers("Content-Type: application/json")
    @POST("/updateLocation/{listing_id}")
    Call<Void> updateLocation(@Path("listing_id") int listing_id, @Body PlaceLocationDTO body);

    @Headers("Content-Type: application/json")
    @POST("/updateHouseRules/{listing_id}")
    Call<Void> updateHouseRules(@Path("listing_id") int listing_id, @Body HouseRulesDTO body);

    @Headers("Content-Type: application/json")
    @POST("/updateBooking/{listing_id}")
    Call<Void> updateBooking(@Path("listing_id") int listing_id, @Body BookingDTO body);

    @Headers("Content-Type: application/json")
    @POST("/updatePrice/{listing_id}")
    Call<Void> updatePrice(@Path("listing_id") int listing_id, @Body PriceDTO body);


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
