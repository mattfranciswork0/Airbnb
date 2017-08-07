package com.example.toshiba.airbnb.Explore;

/**
 * Created by TOSHIBA on 02/08/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.toshiba.airbnb.R;


public class HomeDescActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {

        final Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.homeDescLayout);
        if(currentFragment instanceof HomeDescFragment){
            Log.d("fullscreen", String.valueOf(ImageSliderPager.FULL_SCREEN_MODE));
            if(ImageSliderPager.FULL_SCREEN_MODE){
                ImageSliderPager imageSliderPager = ((HomeDescFragment) currentFragment).getImageSliderPager();
                imageSliderPager.getOutOfFullScreen();
            }
            else{
                super.onBackPressed();
            }
        }
        else{
            super.onBackPressed();
        }


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_desc);
        getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, new HomeDescFragment()).commit();
    }
}
