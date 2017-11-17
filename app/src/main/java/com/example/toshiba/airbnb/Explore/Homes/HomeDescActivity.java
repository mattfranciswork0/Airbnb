package com.example.toshiba.airbnb.Explore.Homes;

/**
 * Created by TOSHIBA on 02/08/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.ViewListingAndYourBookingAdapter;
import com.example.toshiba.airbnb.R;


public class HomeDescActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {

        final Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.homeDescLayout);
        if (currentFragment instanceof HomeDescFragment) {
            if (ImageSliderPager.FULL_SCREEN_MODE) {
                ImageSliderPager imageSliderPager = ((HomeDescFragment) currentFragment).getImageSliderPager();
                imageSliderPager.getOutOfFullScreen();
            }
            else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStackImmediate();

            }
            else{
                super.onBackPressed();
            }


        } else {
            super.onBackPressed();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_desc);
        HomeDescFragment homeDescFragment = new HomeDescFragment();
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey(ViewListingAndYourBookingAdapter.LISTING_ID)) {
                Bundle bundle = new Bundle();
                bundle.putInt(ViewListingAndYourBookingAdapter.LISTING_ID,
                        getIntent().getExtras().getInt(ViewListingAndYourBookingAdapter.LISTING_ID));
                homeDescFragment.setArguments(bundle);
//                Toast.makeText(HomeDescActivity.this, getIntent().getExtras().getInt(ViewListingAndYourBookingAdapter.LISTING_ID) +"",
//                        Toast.LENGTH_LONG).show();
            }
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.homeDescLayout, homeDescFragment).commit();
    }
}
