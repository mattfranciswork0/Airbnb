package com.example.toshiba.airbnb.Explore;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.toshiba.airbnb.LoadingMenuActivity;
import com.example.toshiba.airbnb.Profile.ProfileFragment;
import com.example.toshiba.airbnb.R;


public class MenuActivity extends AppCompatActivity {


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Log.d("hicutie", "onCreate");

        Bundle bundle = new Bundle();
        bundle.putInt(LoadingMenuActivity.TOTAL_LISTINGS, getIntent().getExtras().getInt(LoadingMenuActivity.TOTAL_LISTINGS));
        final HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);

        final ProfileFragment profileFragment = new ProfileFragment();

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.sectionFragmentReplace, homeFragment, "homeFragment").addToBackStack("homeFragment");
        fragmentTransaction.add(R.id.sectionFragmentReplace, profileFragment);
        fragmentTransaction.hide(profileFragment);
        fragmentTransaction.show(homeFragment);
        fragmentTransaction.commit();

        TabLayout sectionTab = (TabLayout) findViewById(R.id.sectionTab);
        sectionTab.addTab(sectionTab.newTab().setText("Explore"));
        sectionTab.addTab(sectionTab.newTab().setText("Profile"));
        sectionTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: //Explore
                        Log.d("hicutie", "blue");
                       fragmentManager.beginTransaction().hide(profileFragment)
                               .show(homeFragment).commit();

                        break;
                    case 1:
                        fragmentManager.beginTransaction().
                                hide(homeFragment)
                                .show(profileFragment)
                                .commit();
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });


    }
}
