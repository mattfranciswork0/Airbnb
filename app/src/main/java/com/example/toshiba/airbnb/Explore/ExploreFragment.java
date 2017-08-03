package com.example.toshiba.airbnb.Explore;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.toshiba.airbnb.R;


/**
 * Created by Owner on 2017-06-24.
 */

public class ExploreFragment extends android.support.v4.app.Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

//        Launch foryouFragment when view is created
        ForYouFragment forYouFragment = new ForYouFragment();
        final android.support.v4.app.FragmentManager fragmentManager = getChildFragmentManager();
        final android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.exploreSectionFragment, forYouFragment);
        fragmentTransaction.commit();

        TabLayout exploreSectionTab = (TabLayout) view.findViewById(R.id.exploreSectionTab);
        exploreSectionTab.addTab(exploreSectionTab.newTab().setText("For You"));
        exploreSectionTab.addTab(exploreSectionTab.newTab().setText("Homes"));
        exploreSectionTab.addTab(exploreSectionTab.newTab().setText("Experiences"));
        exploreSectionTab.setTabGravity(TabLayout.GRAVITY_FILL);
        exploreSectionTab.setTabMode(TabLayout.MODE_FIXED);

        exploreSectionTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Log.d("tabCheck", String.valueOf(tab.getPosition()));
                        final android.support.v4.app.FragmentManager fragmentManager = getChildFragmentManager();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.exploreSectionFragment, new ForYouFragment());
                        fragmentTransaction.commit();
                        break;
                    case 1:
                        Log.d("tabCheck", String.valueOf(tab.getPosition()));
                        FragmentManager fragmentManager1 = getChildFragmentManager();
                        android.support.v4.app.FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                        fragmentTransaction1.replace(R.id.exploreSectionFragment, new HomeFragment());
                        fragmentTransaction1.commit();
                        break;
                    case 2:
                        Log.d("tabCheck", String.valueOf(tab.getPosition()));
                        FragmentManager fragmentManager2 = getChildFragmentManager();
                        android.support.v4.app.FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                        fragmentTransaction2.replace(R.id.exploreSectionFragment, new ExperienceFragment());
                        fragmentTransaction2.commit();


                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        return view;
    }
}
