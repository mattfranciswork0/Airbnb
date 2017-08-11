package com.example.toshiba.airbnb.Profile.BecomeAHost;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.toshiba.airbnb.Explore.HomeDescFragment;
import com.example.toshiba.airbnb.Explore.ImageSliderPager;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.PropertyTypeFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.GetReady.BookingFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.GetReady.HouseRuleFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.GetReady.PriceFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.DescribePlaceFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.GalleryAdapter;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.GalleryFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.PhotoFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.TitleFragment;
import com.example.toshiba.airbnb.R;

import java.util.Map;


public class ProgressActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {

        final Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.progressFragment);
        //DescribePlace Fragment variables
        final EditText etDescribePlace = (EditText) currentFragment.getView().findViewById(R.id.etDescribePlace);
        final SharedPreferences describePlaceSP = getSharedPreferences(DescribePlaceFragment.DESCRIBE_SP, Context.MODE_PRIVATE);

        //TitleFragment
        final EditText etTitle = (EditText) currentFragment.getView().findViewById(R.id.etTitle);
        final SharedPreferences titleSP = getSharedPreferences(TitleFragment.TITLE_SP, Context.MODE_PRIVATE);

        //HouseRuleFragment
        final EditText etAdditionalRules = (EditText) currentFragment.getView().findViewById(R.id.etAdditionalRules);
        final SharedPreferences houseSP = getSharedPreferences(HouseRuleFragment.HOUSE_RULE_SP, Context.MODE_PRIVATE);

        //BookingFragment
        final SharedPreferences bookingSP = getSharedPreferences(BookingFragment.BOOKING_SP, Context.MODE_PRIVATE);
        final EditText etMaxMonth = (EditText) currentFragment.getView().findViewById(R.id.etMaxMonth);
        final EditText etArriveAfter = (EditText) currentFragment.getView().findViewById(R.id.etAriveAfter);
        final EditText etLeaveBefore = (EditText) currentFragment.getView().findViewById(R.id.etLeaveBefore);
        final EditText etMinStay = (EditText) currentFragment.getView().findViewById(R.id.etMinStay);
        final EditText etMaxStay = (EditText) currentFragment.getView().findViewById(R.id.etMaxStay);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(true);
        dialog.setTitle("Want to save your changes?");
        dialog.setMessage("You'll lose your changes if you continue without saving them.");

        //PriceFragment
        final SharedPreferences priceSP = getSharedPreferences(PriceFragment.PRICE_SP, Context.MODE_PRIVATE);
        final EditText etPricePerNight = (EditText) currentFragment.getView().findViewById(R.id.etPricePerNight);

        dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //Action for "Save".
                if (currentFragment instanceof DescribePlaceFragment) {
                    SharedPreferences.Editor edit = describePlaceSP.edit();
                    edit.remove(DescribePlaceFragment.DESCRIBE_PLACE_KEY);
                    edit.putString(DescribePlaceFragment.DESCRIBE_PLACE_KEY, etDescribePlace.getText().toString());
                    edit.apply();

                }

                else if (currentFragment instanceof TitleFragment) {
                    SharedPreferences.Editor edit = titleSP.edit();
                    edit.remove(TitleFragment.TITLE_KEY);
                    edit.putString(TitleFragment.TITLE_KEY, etTitle.getText().toString());
                    edit.apply();

                }

                else if(currentFragment instanceof  HouseRuleFragment){
                    SharedPreferences.Editor edit = houseSP.edit();
                    edit.remove(HouseRuleFragment.ADDITIONAL_RULES);
                    edit.putString(HouseRuleFragment.ADDITIONAL_RULES, etAdditionalRules.getText().toString());
                    edit.apply();
                }

                else if(currentFragment instanceof BookingFragment){
                    SharedPreferences.Editor edit = bookingSP.edit();
                    edit.remove(BookingFragment.MAX_MONTH);
                    edit.remove(BookingFragment.ARRIVE_AFTER);
                    edit.remove(BookingFragment.LEAVE_BEFORE);
                    edit.remove(BookingFragment.MAX_STAY);
                    edit.remove(BookingFragment.MIN_STAY);

                    edit.putString(BookingFragment.MAX_MONTH, etMaxMonth.getText().toString());
                    edit.putString(BookingFragment.ARRIVE_AFTER, etArriveAfter.getText().toString());
                    edit.putString(BookingFragment.LEAVE_BEFORE, etLeaveBefore.getText().toString());
                    edit.putString(BookingFragment.MAX_STAY, etMaxStay.getText().toString());
                    edit.putString(BookingFragment.MIN_STAY, etMinStay.getText().toString());

                    edit.apply();
                }
                else if(currentFragment instanceof PriceFragment){
                    SharedPreferences.Editor edit = priceSP.edit();
                    edit.remove(PriceFragment.PRICE);
                    edit.putString(PriceFragment.PRICE, etPricePerNight.getText().toString());
                    edit.apply();
                }

            }
        }).setNegativeButton("Discard ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Action for "Cancel".
                ProgressActivity.super.onBackPressed();
            }
        });

        if (currentFragment instanceof DescribePlaceFragment) {
            //If edit text value is equal to shared preferences' text value in DescribePlaceFragment
            if (!(etDescribePlace.getText().toString().equals(describePlaceSP.getString(DescribePlaceFragment.DESCRIBE_PLACE_KEY, "")))) {
                dialog.create().show();
            }else{
                //if already saved/no saved needed to be make, go back
                super.onBackPressed();
            }

        }else if (currentFragment instanceof TitleFragment) {
            //If edit text value is equal to shared preferences' text value in TitleFragment
            if (!(etTitle.getText().toString().equals(titleSP.getString(TitleFragment.TITLE_KEY, "")))) {
                dialog.create().show();
            } else {
                //if already saved/no saved needed to be make, go back
                super.onBackPressed();
            }
        }
        else if (currentFragment instanceof HouseRuleFragment){
            if (!(etAdditionalRules.getText().toString().equals(titleSP.getString(HouseRuleFragment.ADDITIONAL_RULES, "")))) {
                dialog.create().show();
            } else {
                //if already saved/no saved needed to be make, go back
                super.onBackPressed();
            }
        }
        else if(currentFragment instanceof BookingFragment){
            if (!(etMaxMonth.getText().toString().equals(bookingSP.getString(BookingFragment.MAX_MONTH, "")))) {
                dialog.create().show();
            }
            else if (!(etArriveAfter.getText().toString().equals(bookingSP.getString(BookingFragment.ARRIVE_AFTER, "")))) {
                dialog.create().show();
            }
            else if (!(etLeaveBefore.getText().toString().equals(bookingSP.getString(BookingFragment.LEAVE_BEFORE, "")))) {
                dialog.create().show();
            }
            else if (!(etMinStay.getText().toString().equals(bookingSP.getString(BookingFragment.MIN_STAY, "")))) {
                dialog.create().show();
            }
            else if (!(etMaxStay.getText().toString().equals(bookingSP.getString(BookingFragment.MAX_STAY, "")))) {
                dialog.create().show();
            }
            else {
                //if already saved/no saved needed to be make, go back
                super.onBackPressed();
            }
        }
        else if(currentFragment instanceof PriceFragment){
             if(!(etPricePerNight.getText().toString().equals(priceSP.getString(PriceFragment.PRICE, "")))){
                dialog.create().show();
            }else {
                 //if already saved/no saved needed to be make, go back
                 super.onBackPressed();
             }
        }
        else {
            super.onBackPressed();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        if (getIntent().getExtras().getBoolean(BecomeAHostActivity.BASIC_BUTTON))
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.progressFragment, new PropertyTypeFragment()).commit();

        else if (getIntent().getExtras().getBoolean(BecomeAHostActivity.SCENE_BUTTON)) {
            //check if user clicked "Add Photo button" in PhotoFragment, if so, do not show PhotoFragment anymore till user has finished registering
            SharedPreferences sharedPreferences = getSharedPreferences("PHOTOFRAGMENT", Context.MODE_PRIVATE);
            if (sharedPreferences.getBoolean(PhotoFragment.PHOTOFRAGMENT_REMOVE, false)) {
                getSupportFragmentManager().beginTransaction()
                        //TODO" MAKE SURE TO REMOVE PHOTOFRAGMENT_REMOVE AFTER LISTING IS DONE
                        .replace(R.id.progressFragment, new GalleryFragment()).commit();

            } else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.progressFragment, new PhotoFragment()).commit();
            }
        }

        else if(getIntent().getExtras().getBoolean(BecomeAHostActivity.GET_READY_BUTTON)){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.progressFragment, new HouseRuleFragment()).commit();
        }

    }
}
