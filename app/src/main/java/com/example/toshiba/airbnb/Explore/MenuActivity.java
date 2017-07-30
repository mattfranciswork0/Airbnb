package com.example.toshiba.airbnb.Explore;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.toshiba.airbnb.Profile.ProfileFragment;
import com.example.toshiba.airbnb.R;


public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activvity_menu);

        final ExploreFragment exploreFragment = new ExploreFragment();
        final android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        final android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.sectionFragmentReplace, exploreFragment);
        fragmentTransaction.commit();

        TabLayout sectionTab = (TabLayout) findViewById(R.id.sectionTab);
        sectionTab.addTab(sectionTab.newTab().setText("Explore"));
        sectionTab.addTab(sectionTab.newTab().setText("Profile"));
        sectionTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()){
                    case 0: //Explore
                        final android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.sectionFragmentReplace, new ExploreFragment());
                        fragmentTransaction.commit();
                        break;
                    case 1:
                        final android.support.v4.app.FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
                        fragmentTransaction1.replace(R.id.sectionFragmentReplace, new ProfileFragment());
                        fragmentTransaction1.commit();
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
