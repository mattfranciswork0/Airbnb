package com.example.toshiba.airbnb.Profile.BecomeAHost;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.PropertyTypeFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.GalleryFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.PhotoFragment;
import com.example.toshiba.airbnb.R;


public class ProgressActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        if(getIntent().getExtras().getBoolean(BecomeAHostActivity.BASIC_BUTTON))
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.progressFragment, new PropertyTypeFragment()).commit();

        else if(getIntent().getExtras().getBoolean(BecomeAHostActivity.SCENE_BUTTON)) {
            //check if user clicked "Add Photo button" in PhotoFragment, if so, do not show PhotoFragment anymore till user has finished registering
            SharedPreferences sharedPreferences = getSharedPreferences("PHOTOFRAGMENT", Context.MODE_PRIVATE);
            if (sharedPreferences.getBoolean(PhotoFragment.PHOTOFRAGMENT_REMOVE, false)) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.progressFragment, new GalleryFragment()).commit();

            }
            else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.progressFragment, new PhotoFragment()).commit();
            }
        }
    }
}
