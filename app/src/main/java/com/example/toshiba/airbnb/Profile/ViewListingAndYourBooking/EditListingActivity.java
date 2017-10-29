package com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.toshiba.airbnb.R;

/**
 * Created by TOSHIBA on 27/10/2017.
 */

public class EditListingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_listing);
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey(ViewListingAndYourBookingAdapter.LISTING_ID)) {
                Bundle bundle = new Bundle();
                bundle.putInt(ViewListingAndYourBookingAdapter.LISTING_ID, getIntent().getExtras().getInt(ViewListingAndYourBookingAdapter.LISTING_ID));
                EditListingFragment editListingFragment = new EditListingFragment();
                editListingFragment.setArguments(bundle);
                Log.d("imSorry", "" + getIntent().getExtras().getInt(ViewListingAndYourBookingAdapter.LISTING_ID));
                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, editListingFragment).commit();
            }
        }

    }

    @Override
    public void onBackPressed() {

        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStackImmediate();
        } else {
            super.onBackPressed();
        }
    }
}
