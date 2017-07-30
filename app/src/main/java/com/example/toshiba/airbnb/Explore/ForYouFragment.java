package com.example.toshiba.airbnb.Explore;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.toshiba.airbnb.R;

import java.util.ArrayList;


/**
 * Created by Owner on 2017-06-28.
 */

public class ForYouFragment extends android.support.v4.app.Fragment {
    int index;

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

        final ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        numbers.add(6);
        numbers.add(7);
        numbers.add(8);
        numbers.add(9);
        numbers.add(10);

        final ForYouSectionAdapter forYouSectionAdapter = new ForYouSectionAdapter();

        index = 0;
        //get first four items
        for (int i = index; i <= (index + 4) - 1; i++) { //-1 because number.get() starts at 0
            forYouSectionAdapter.addNumbers(numbers.get(i));
        }
        index += 4; //get fifth item in next for loop

        recyclerView.setAdapter(forYouSectionAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int itemCount = forYouSectionAdapter.getItemCount();
                int firstVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition() + 1; //+1 because getItemCount starts at 0
                int visibleThreshold = 4;
                int showItems = (index + visibleThreshold) - 1;

                //TODO: if recyclerview is scrolled, disable/enable scrollView for LinearLayout in explore_tab_fragment_explore
                super.onScrolled(recyclerView, dx, dy);
                Log.d("scroll", String.valueOf(itemCount));
                Log.d("numbers", "NOT Loading: " + "firstVisible is " + String.valueOf(firstVisibleItem) + " itemCount is " + String.valueOf(itemCount));

                if (index <= numbers.size() - 1) {
                    if (firstVisibleItem == itemCount
                            && firstVisibleItem % visibleThreshold == 0) { //if scrolled down to the very bottom; show every fourth child

                        if (showItems >= numbers.size() - 1) {
                            showItems = numbers.size() - 1; //show left overs
                        }
                        for (int i = index; i <= showItems; i++) {
                            forYouSectionAdapter.notifyItemInserted(numbers.get(i));
                            forYouSectionAdapter.addNumbers(numbers.get(i));
                        }
                        index += 4;
                        Toast.makeText(getActivity(), "Loading", Toast.LENGTH_LONG).show();

                    }
                }
            }


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        return view;
    }
}
