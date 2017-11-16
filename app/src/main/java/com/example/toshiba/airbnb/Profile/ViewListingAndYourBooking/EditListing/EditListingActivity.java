package com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.GalleryFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.PhotoDescFragment;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.ViewListingAndYourBookingAdapter;
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
                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, editListingFragment)
                        .commit();
            }
        }

    }

    @Override
    public void onBackPressed() {
        Log.d("loveutodeath", "hi" + getSupportFragmentManager().getBackStackEntryCount());
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            Log.d("loveutodeath", "ho");
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setCancelable(true);
            dialog.setTitle("Want to save your changes?");
            dialog.setMessage("Any changes will be lost if you continue");
            dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            dialog.setNegativeButton("PROCEED", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    getSupportFragmentManager().popBackStack();
                }
            });
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.rootLayout);

            if (currentFragment instanceof GalleryFragment ||
                    currentFragment instanceof PhotoDescFragment) {
                super.onBackPressed();
            } else {
                dialog.show();
            }


        } else {
            Log.d("loveutodeath", "super.onbaack");
            super.onBackPressed();
        }

//
    }
}

