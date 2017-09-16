package com.example.toshiba.airbnb.Profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.toshiba.airbnb.R;

/**
 * Created by TOSHIBA on 15/09/2017.
 */

public class ViewListingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listing);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ViewListingFragment()).commit();
    }
}
