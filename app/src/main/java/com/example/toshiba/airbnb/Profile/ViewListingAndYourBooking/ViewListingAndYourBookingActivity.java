package com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.toshiba.airbnb.Profile.ProfileFragment;
import com.example.toshiba.airbnb.R;

/**
 * Created by TOSHIBA on 15/09/2017.
 */

public class ViewListingAndYourBookingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listing);
        if(getIntent().getExtras() != null){
            if(getIntent().getExtras().containsKey(ProfileFragment.YOUR_BOOKING)){
                ViewListingAndYourBookingFragment viewListingAndYourBookingFragment = new ViewListingAndYourBookingFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean(ProfileFragment.YOUR_BOOKING, true);
                viewListingAndYourBookingFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, viewListingAndYourBookingFragment).commit();
            }
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new ViewListingAndYourBookingFragment()).commit();
        }
    }
}
