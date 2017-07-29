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

        final LinearLayout searchLayout = (LinearLayout) getParentFragment().getView().findViewById(R.id.searchLayout);
        final LinearLayout exploreSectionFragment = (LinearLayout) getParentFragment().getView().findViewById(R.id.exploreSectionFragment);
        final RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        SectionAdapter sectionAdapter = new SectionAdapter();
        mRecyclerView.setAdapter(sectionAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                //TODO: if recyclerview is scrolled, disable/enable scrollView for LinearLayout in explore_tab_fragment_explore

                super.onScrolled(recyclerView, dx, dy);
                Log.d("scroll", "dx: " + String.valueOf(dx) + "and dy: " + String.valueOf(dy));
                Log.d("scroll", String.valueOf(layoutManager.findFirstCompletelyVisibleItemPosition()));

//                if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
//                    searchLayout.setVisibility(View.VISIBLE);
//                }else{
//                    searchLayout.setVisibility(View.GONE);
//                    ViewGroup.LayoutParams params = mRecyclerView.getLayoutParams();
//                }

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        return view;
    }
}

