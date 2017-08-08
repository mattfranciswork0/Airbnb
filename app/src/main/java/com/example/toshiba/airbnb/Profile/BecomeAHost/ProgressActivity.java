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

        //PlaceTitle
        final EditText etTitle = (EditText) currentFragment.getView().findViewById(R.id.etTitle);
        final SharedPreferences titleSP = getSharedPreferences(TitleFragment.TITLE_SP, Context.MODE_PRIVATE);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(true);
        dialog.setTitle("Want to save your changes?");
        dialog.setMessage("You'll lose your changes if you continue without saving them.");

        dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //Action for "Save".
                if (currentFragment instanceof DescribePlaceFragment) {
                    SharedPreferences.Editor editor = describePlaceSP.edit();
                    editor.remove(DescribePlaceFragment.DESCRIBE_PLACE_KEY);
                    editor.putString(DescribePlaceFragment.DESCRIBE_PLACE_KEY, etDescribePlace.getText().toString());
                    editor.apply();

                }

                if (currentFragment instanceof TitleFragment) {
                    SharedPreferences.Editor editor = titleSP.edit();
                    editor.remove(TitleFragment.TITLE_KEY);
                    editor.putString(TitleFragment.TITLE_KEY, etTitle.getText().toString());
                    editor.apply();

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

    }
}
