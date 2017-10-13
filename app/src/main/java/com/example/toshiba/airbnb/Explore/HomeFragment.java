package com.example.toshiba.airbnb.Explore;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.Util.RetrofitUtil;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Owner on 2017-06-28.
 */

public class HomeFragment extends android.support.v4.app.Fragment {
    int index;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Log.d("fragmentCheck", "HomeFragment");

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        HomeAdapter homeAdapter = new HomeAdapter();
        recyclerView.setAdapter(homeAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        final DatabaseInterface retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();

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

        final HomeAdapter homeAdapter = new HomeAdapter();

        index = 0;
        //get first four items
        final int visibleThreshold = 4;
        final CountDownLatch latch = new CountDownLatch(visibleThreshold);
        final ProgressDialog  dialog = new ProgressDialog(getActivity());
        for (int i = index; i < index + visibleThreshold; i++) {

            homeAdapter.addSize();
            if(i == index){
                Log.d("latchCount", "showDialog");
                dialog.setMessage("Loading...");
                dialog.setCancelable(false);
                dialog.show();
            }


            retrofit.getListingData(1).enqueue(new Callback<POJOListingData>() {
                @Override
                public void onResponse(Call<POJOListingData> call, Response<POJOListingData> response) {
                    latch.countDown();
                    Log.d("latchCount", latch.getCount() + "");
                    if(latch.getCount() == 0){
                        Log.d("latchCount", "hide");
                        dialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<POJOListingData> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Failed to retrieve data, check your internet conncection", Toast.LENGTH_LONG)
                            .show();
                }
            });
        }

        index += visibleThreshold; //get fifth item in next for loop

        recyclerView.setAdapter(homeAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int itemCount = homeAdapter.getItemCount();
                int firstVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition() + 1; //+1 because starts at 0

                int showItems = (index + visibleThreshold); //because index starts at 0, - 1

                //TODO: if recyclerview is scrolled, disable/enable scrollView for LinearLayout in explore_tab_fragment_explore
                super.onScrolled(recyclerView, dx, dy);
                Log.d("scroll", String.valueOf(itemCount));
                Log.d("numbers", "NOT Loading: " + "firstVisible is " + String.valueOf(firstVisibleItem) + " itemCount is " + String.valueOf(itemCount));

                if (index < numbers.size()) {
                    if (firstVisibleItem == itemCount
                            && firstVisibleItem % visibleThreshold == 0) { //if scrolled down to the very bottom; show every fourth child


                        if (showItems > numbers.size()) {
                            showItems = numbers.size(); //show remaininning listing by forcing for loopp below to the database size
                        }
                        Log.d("latchCount", showItems + "showItems" + " and" + firstVisibleItem );
                        int remainingListings = showItems - firstVisibleItem;
                        final CountDownLatch latch = new CountDownLatch(remainingListings);
                        final ProgressDialog  dialog = new ProgressDialog(getActivity());

                        for (int i = index; i < showItems; i++) {

                            if(i == index){
                                Log.d("latchCount", "showDialog");
                                dialog.setMessage("Loading...");
                                dialog.setCancelable(false);
                                dialog.show();
                            }

                            retrofit.getListingData(1).enqueue(new Callback<POJOListingData>() {
                                @Override
                                public void onResponse(Call<POJOListingData> call, Response<POJOListingData> response) {
                                    latch.countDown();
                                    Log.d("latchCount", latch.getCount() + "");
                                    if(latch.getCount() == 0){
                                        Log.d("latchCount", "hide");
                                        dialog.dismiss();
                                    }
                                }

                                @Override
                                public void onFailure(Call<POJOListingData> call, Throwable t) {
                                    dialog.dismiss();
                                    Toast.makeText(getActivity(), "Failed to retrieve data, check your internet conncection", Toast.LENGTH_LONG)
                                            .show();
                                }
                            });

                            homeAdapter.notifyItemInserted(numbers.get(i));

                            //sort by id (earliest listing)
                            homeAdapter.addSize();
                        }
                        index += visibleThreshold;
                        Toast.makeText(getActivity(), "Loading", Toast.LENGTH_LONG).show();

                    }
                }
            }


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }
}