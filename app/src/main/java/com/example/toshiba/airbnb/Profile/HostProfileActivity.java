package com.example.toshiba.airbnb.Profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.toshiba.airbnb.R;

/**
 * Created by TOSHIBA on 14/08/2017.
 */

public class HostProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_profile);
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.hostProfileLayout, new HostProfileViewFragment()).addToBackStack(null).commit();
    }
}
