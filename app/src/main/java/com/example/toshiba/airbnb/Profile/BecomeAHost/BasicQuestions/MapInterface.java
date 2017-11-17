package com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions;

import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.POJOMap.GMapsAutoComplete.POJOPredictions;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.POJOMap.GMapsPlaceDetails.POJOResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Owner on 2017-07-08.
 */

public interface MapInterface {
    @GET("json")
    Call<POJOPredictions> getAutoCompleteInfo(@Query("input") String input, @Query("types") String types , @Query("key") String key);

    @GET("json")
    Call<POJOResults> getPlaceDetailsInfo(@Query("placeid") String placeid, @Query("key") String key);
}
