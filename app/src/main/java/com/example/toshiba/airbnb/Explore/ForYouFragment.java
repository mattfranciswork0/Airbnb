package com.example.toshiba.airbnb.Explore;

/**
 * Created by TOSHIBA on 29/07/2017.
 */


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.toshiba.airbnb.R;

/**
 * Created by Owner on 2017-06-28.
 */

public class ForYouFragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_for_you, container, false);
        Log.d("fragmentCheck", "For You Fragment");
//        final LinearLayout searchLayout = (LinearLayout) getParentFragment().getView().findViewById(R.id.searchLayout);
//        final LinearLayout exploreSectionFragment = (LinearLayout) getParentFragment().getView().findViewById(R.id.exploreSectionFragment);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        ForYouSectionAdapter forYouSectionAdapter = new ForYouSectionAdapter();
        recyclerView.setAdapter(forYouSectionAdapter);
        return view;
    }
}
