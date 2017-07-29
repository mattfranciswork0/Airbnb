package com.example.toshiba.airbnb.Explore;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toshiba.airbnb.R;


/**
 * Created by Owner on 2017-06-24.
 */

public class ExploreFragment extends android.support.v4.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        Log.d("fragmentCheck", "ExploreFragment");

        //Launch foryouFragment when view is created
        final ForYouFragment forYouFragment = new ForYouFragment();
        android.support.v4.app.FragmentManager fragmentManager = getChildFragmentManager();
        final android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.exploreSectionFragment, forYouFragment);
        fragmentTransaction.commit();

        TabLayout exploreSectionTab = (TabLayout) view.findViewById(R.id.exploreSectionTab);
        exploreSectionTab.addTab(exploreSectionTab.newTab().setText("For You"));
        exploreSectionTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Log.d("fragmentCheck", "For You tab is clicked");
                        fragmentTransaction.replace(R.id.exploreSectionFragment, forYouFragment);
                        fragmentTransaction.commit();

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Log.d("fragmentCheck", "For You tab is clicked");
                        fragmentTransaction.replace(R.id.exploreSectionFragment, forYouFragment);
                        fragmentTransaction.commit();

                }
            }
        });


        return view;
    }
}
