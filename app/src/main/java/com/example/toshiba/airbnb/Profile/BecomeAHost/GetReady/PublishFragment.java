package com.example.toshiba.airbnb.Profile.BecomeAHost.GetReady;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.Explore.MenuActivity;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.AmenitiesItemFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.BathroomFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.GuestFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.LocationFilterAdapter;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.LocationFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.PropertyTypeFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.IdListing;
import com.example.toshiba.airbnb.Profile.BecomeAHost.PublishListingDataRequest;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by TOSHIBA on 11/08/2017.
 */

public class PublishFragment extends Fragment {
    public static final String PUBLISH_FRAGMENT_FINISHED = "PUBLISH_FRAGMENT_FINISHED";
    public static final String LISTING_ID_SP = "LISTING_ID_SP";
    public static final String LISTING_ID = "LISTING_ID";
    private SharedPreferences propertyTypeSP;
    private SharedPreferences guestFragmentSP;
    private SharedPreferences bathroomFragmentSP;
    private SharedPreferences locationSP;
    private SharedPreferences amenitiesSP;
    private SharedPreferences sessionSP;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressBar basicProgressBar = (ProgressBar) getActivity().findViewById(R.id.basicProgressBar);
        basicProgressBar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.lightRed));
        basicProgressBar.setProgress(100);
        propertyTypeSP = getActivity().getSharedPreferences(PropertyTypeFragment.PROPERTY_TYPE_SP, Context.MODE_PRIVATE);
        guestFragmentSP = getActivity().getSharedPreferences(GuestFragment.GUEST_SP, Context.MODE_PRIVATE);
        bathroomFragmentSP = getActivity().getSharedPreferences(BathroomFragment.BATHROOM_SP, Context.MODE_PRIVATE);
        locationSP = getActivity().getSharedPreferences(LocationFilterAdapter.LOCATION_SP, Context.MODE_PRIVATE);
        amenitiesSP = getActivity().getSharedPreferences(AmenitiesItemFragment.AMENITIES_SP, Context.MODE_PRIVATE);
        sessionSP = getActivity().getSharedPreferences(SessionManager.SESSION_SP, Context.MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publish, container, false);
        return view;
    }
    public String checkPropertyOwnership(){
        if (propertyTypeSP.contains(PropertyTypeFragment.ENTIRE_RADIO))
            return "Entire";
       if (propertyTypeSP.contains(PropertyTypeFragment.SHARED_RADIO))
           return "Shared";
        if (propertyTypeSP.contains(PropertyTypeFragment.PRIVATE_RADIO))
            return "Private";
       return "ERROR";
    }

    public String checkBathroomType(){
        if(bathroomFragmentSP.contains(BathroomFragment.PRIVATE_BATHROOM))
            return "Private";
        else if(bathroomFragmentSP.contains(BathroomFragment.SHARED_BATHROOM))
            return "Shared";
        return "ERROR";
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.bMakeChanges).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        view.findViewById(R.id.bPublish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Listing your place...");
                progressDialog.show();
                PublishListingDataRequest publishListingDataRequest = new PublishListingDataRequest(
                        sessionSP.getInt(SessionManager.USER_ID, 0),
                        checkPropertyOwnership(),
                        propertyTypeSP.getString(PropertyTypeFragment.PROPERTY_TYPE, "ERROR"),
                        guestFragmentSP.getString(GuestFragment.TOTAL_GUEST, "ERROR"),
                        guestFragmentSP.getString(GuestFragment.TOTAL_BED_ROOM, "ERROR"),
                        guestFragmentSP.getString(GuestFragment.TOTAL_BED, "ERROR"),
                        bathroomFragmentSP.getString(BathroomFragment.TOTAL_BATHROOM, "ERROR"),
                        checkBathroomType(),
                        locationSP.getString(LocationFilterAdapter.COUNTRY_NAME, "ERROR"),
                        locationSP.getString(LocationFilterAdapter.STREET_NAME, "ERROR"),
                        locationSP.getString(LocationFragment.EXTRA_DETAILS, "ERROR"),
                        locationSP.getString(LocationFilterAdapter.CITY_NAME, "ERROR"),
                        locationSP.getString(LocationFilterAdapter.STATE_NAME, "ERROR"),
                        locationSP.getString(LocationFilterAdapter.LNG, "ERROR"),
                        locationSP.getString(LocationFilterAdapter.LAT, "ERROR"),
                        //AmenitiesItem
                        amenitiesSP.contains(getResources().getString(R.string.rbEssentials)),
                        amenitiesSP.contains(getResources().getString(R.string.rbInternet)),
                        amenitiesSP.contains(getResources().getString(R.string.rbShampoo)),
                        amenitiesSP.contains(getResources().getString(R.string.rbHangers)),
                        amenitiesSP.contains(getResources().getString(R.string.rbTV)),
                        amenitiesSP.contains(getResources().getString(R.string.rbHeating)),
                        amenitiesSP.contains(getResources().getString(R.string.rbAirConditioning)),
                        amenitiesSP.contains(getResources().getString(R.string.rbBreakfast)),
                        //AmenitiesPlace
                        amenitiesSP.contains(getResources().getString(R.string.rbKitchen)),
                        amenitiesSP.contains(getResources().getString(R.string.rbLaundry)),
                        amenitiesSP.contains(getResources().getString(R.string.rbParking)),
                        amenitiesSP.contains(getResources().getString(R.string.rbElevator)),
                        amenitiesSP.contains(getResources().getString(R.string.rbPool)),
                        amenitiesSP.contains(getResources().getString(R.string.rbGym))
                );
                final DatabaseInterface retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.2.89:3000/")
                        .baseUrl("http://192.168.1.115:3000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build().create(DatabaseInterface.class);
                retrofit.insertListingData(publishListingDataRequest).enqueue(new Callback<IdListing>() {
                    @Override
                    public void onResponse(Call<IdListing> call, Response<IdListing> response) {
                        Log.d("heyBestie", "The listing id is " + response.body().getId());
                        SharedPreferences listingIdSP = getActivity().getSharedPreferences(LISTING_ID_SP, Context.MODE_PRIVATE);
                        listingIdSP.edit().putInt(LISTING_ID, response.body().getId());
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Place successfully listed", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<IdListing> call, Throwable t) {
                        Log.d("heyBestie", "fail " + t.getMessage() + " or " + t.toString() );
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Failed to list your place, try again", Toast.LENGTH_LONG).show();

                    }
                });

                Intent intent = new Intent(getActivity(), MenuActivity.class);
                intent.putExtra("BASIC_QUESTIONS_COMPLETED", true);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
    }
}
