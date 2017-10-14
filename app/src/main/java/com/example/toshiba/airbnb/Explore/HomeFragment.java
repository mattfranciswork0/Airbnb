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

import com.cloudinary.Cloudinary;
import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.LoadingMenuActivity;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.Util.RetrofitUtil;

import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Owner on 2017-06-28.
 */

public class HomeFragment extends android.support.v4.app.Fragment {
    int index;
    DatabaseInterface retrofit;
    int size;

    public void addData(HomeAdapter homeAdapter, Response<POJOListingData> response) {
        homeAdapter.addListingId(response.body().getId());
        homeAdapter.addImagePath(response.body().getImageData().get(0).getImagePath());
        homeAdapter.addPropertyOwnership(response.body().getPropertyOwnership());
        homeAdapter.addPropertyType(response.body().getPropertyType());
        homeAdapter.addTotalBed(response.body().getTotalBeds());
        homeAdapter.addPlaceTitle(response.body().getPlaceTitle());
        homeAdapter.addPrice(response.body().getPrice());

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        size = getArguments().getInt(LoadingMenuActivity.TOTAL_LISTINGS);
        retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Log.d("fragmentCheck", "HomeFragment");

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        if(size == 0){
            view.findViewById(R.id.tvEmptyListing).setVisibility(View.VISIBLE);
        }
        final CountDownLatch latch;
        final HomeAdapter homeAdapter = new HomeAdapter();
        index = 0;
        //get first four items
        final int visibleThreshold = 4;

        if (size < visibleThreshold) {
            latch = new CountDownLatch(size);
        } else {
            latch = new CountDownLatch(visibleThreshold);
        }

        final ProgressDialog dialog = new ProgressDialog(getActivity());

        final DatabaseInterface retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();
        Cloudinary cloudinary = new Cloudinary(getActivity().

                getResources().getString(R.string.cloudinaryEnviornmentVariable)); //configured using an environment variable
        int showInitialItems;
        if(size < visibleThreshold){
            showInitialItems = size;
        } else{
            showInitialItems = index + visibleThreshold;
        }
        for (int i = index; i < showInitialItems; i++) {

            homeAdapter.addSize();
            if (i == index) {
                Log.d("latchCount", "showDialog");
                dialog.setMessage("Loading...");
                dialog.setCancelable(false);
                dialog.show();
            }
            final int getListingId = i + 1;

            retrofit.getListingData(getListingId).enqueue(new Callback<POJOListingData>() {
                @Override
                public void onResponse(Call<POJOListingData> call, Response<POJOListingData> response) {
                    Log.d("HomeAdapter", "onResponse" + getListingId);

                    addData(homeAdapter, response);


                    latch.countDown();
                    Log.d("latchCount", latch.getCount() + "");
                    if (latch.getCount() == 0) {
                        Log.d("latchCount", "hide");
                        dialog.dismiss();
                        recyclerView.setAdapter(homeAdapter);
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

                if (index < size) {
                    if (firstVisibleItem == itemCount
                            && firstVisibleItem % visibleThreshold == 0) { //if scrolled down to the very bottom; show every fourth child


                        if (showItems > size) {
                            showItems = size; //show remaininning listing by forcing for loopp below to the database size
                        }
                        Log.d("latchCount", showItems + "showItems" + " and" + firstVisibleItem);
                        int remainingListings = showItems - firstVisibleItem;
                        final CountDownLatch latch = new CountDownLatch(remainingListings);
                        final ProgressDialog dialog = new ProgressDialog(getActivity());

                        for (int i = index; i < showItems; i++) {

                            if (i == index) {
                                Log.d("latchCount", "showDialog");
                                dialog.setMessage("Loading...");
                                dialog.setCancelable(false);
                                dialog.show();
                            }
                            int getListingId = i + 1;

                            retrofit.getListingData(getListingId).enqueue(new Callback<POJOListingData>() {
                                @Override
                                public void onResponse(Call<POJOListingData> call, Response<POJOListingData> response) {
                                    addData(homeAdapter, response);
                                    latch.countDown();
                                    Log.d("latchCount", latch.getCount() + "");
                                    if (latch.getCount() == 0) {
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

                            homeAdapter.notifyItemInserted(i);

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