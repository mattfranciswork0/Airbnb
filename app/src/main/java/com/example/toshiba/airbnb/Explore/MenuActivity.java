package com.example.toshiba.airbnb.Explore;


import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toshiba.airbnb.LoadingMenuActivity;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.LocationFragment;
import com.example.toshiba.airbnb.Profile.ProfileFragment;
import com.example.toshiba.airbnb.R;


public class MenuActivity extends AppCompatActivity {
     HomeFragment homeFragment;

    private void changeTabsFont(TabLayout tabLayout) {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.sectionFragmentReplace);

        if(currentFragment instanceof LocationFragment || currentFragment instanceof SearchGuestFragment){

            super.onBackPressed();
            //section tab is invisible when one of the search-bar filters are clicked
            findViewById(R.id.sectionTab).setVisibility(View.VISIBLE);
        }
        else if(currentFragment instanceof HomeFragment || currentFragment instanceof ProfileFragment){
            finish();
        }
        else{
            super.onBackPressed();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Log.d("hicutie", "onCreate");

        Bundle bundle = new Bundle();
        bundle.putInt(LoadingMenuActivity.TOTAL_LISTINGS, getIntent().getExtras().getInt(LoadingMenuActivity.TOTAL_LISTINGS));
         homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);

        final ProfileFragment profileFragment = new ProfileFragment();

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.sectionFragmentReplace, homeFragment, "homeFragment");

        fragmentTransaction.hide(profileFragment);
        fragmentTransaction.show(homeFragment);
        fragmentTransaction.commit();

        TabLayout sectionTab = (TabLayout) findViewById(R.id.sectionTab);
        sectionTab.addTab(sectionTab.newTab().setText("Explore"));
        sectionTab.addTab(sectionTab.newTab().setText("Profile"));
        changeTabsFont(sectionTab);
        sectionTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: //Explore
                       fragmentManager.beginTransaction().hide(profileFragment)
                               .show(homeFragment).commit();

                        break;
                    case 1:
                        if(!profileFragment.isAdded()) {
                            fragmentManager.beginTransaction().add(R.id.sectionFragmentReplace, profileFragment).commit();
                        }
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
