package com.example.toshiba.airbnb.Explore;

/**
 * Created by TOSHIBA on 02/08/2017.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.toshiba.airbnb.R;


public class HomeDescActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_desc);
        getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, new HomeDescFragment()).commit();
    }
}
