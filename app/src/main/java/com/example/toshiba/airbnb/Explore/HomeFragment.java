package com.example.toshiba.airbnb.Explore;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.LoadingMenuActivity;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.LocationFragment;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.Util.RetrofitUtil;

import java.util.List;


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
    public static final String SEARCH_BAR_LOCATION = "SEARCH_BAR_LOCATION";
    public static String SEARCH_BAR_SIZE = "SEARCH_BAR_SIZE";


    
    public void addSearchBarRelatedFragment(Fragment fragment, String fragmentTag){
            getFragmentManager().beginTransaction()
                    .add(R.id.sectionFragmentReplace, fragment).addToBackStack(fragmentTag)
                    .hide(HomeFragment.this)
                    .commit();
    }
    public static void expand(final View v, int duration, int targetHeight) {

        int prevHeight  = v.getHeight();

        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public void addData(HomeAdapter homeAdapter, int index, Response<POJOMultipleListingsDataGetResult> response) {
        List<POJOMultipleListingsData> result = response.body().getResult();
        for (int i = 0; i < result.size(); i++) {
            homeAdapter.addListingId(result.get(i).getId());
            homeAdapter.addImagePath(result.get(i).getImagePath());
            homeAdapter.addPropertyOwnership(result.get(i).getPropertyOwnership());
            homeAdapter.addPropertyType(result.get(i).getPropertyType());
            homeAdapter.addTotalBed(result.get(i).getTotalBeds());
            homeAdapter.addPlaceTitle(result.get(i).getPlaceTitle());
            homeAdapter.addPrice(result.get(i).getPrice());
            Log.d("homeFragment", "for loop");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            if(getArguments().containsKey(LoadingMenuActivity.TOTAL_LISTINGS)){
                size =getArguments().getInt(LoadingMenuActivity.TOTAL_LISTINGS);
            }
            else if(getArguments().containsKey(SEARCH_BAR_SIZE)){
                size = getArguments().getInt(SEARCH_BAR_SIZE);
            }
        }else {
            //from loadingMenuActivity

        }
        retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //section tab is invisible when one of the search-bar filters are clicked
        getActivity().findViewById(R.id.sectionTab).setVisibility(View.VISIBLE);
        TextView tvSearchAnywhere = (TextView) view.findViewById(R.id.tvSearchAnywhere);
        TextView tvSearchGuest = (TextView) view.findViewById(R.id.tvSearchGuest);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        final LinearLayout searchLayout = (LinearLayout) view.findViewById(R.id.searchLayout);
        final LinearLayout whereLayout = (LinearLayout) view.findViewById(R.id.whereLayout);
        final LinearLayout guestLayout = (LinearLayout) view.findViewById(R.id.guestLayout);

         if(!LocationFragment.SEARCH_COUNTRY_PATH.equals(" ") &&
                !LocationFragment.SEARCH_STREET_PATH.equals(" ") && !LocationFragment.SEARCH_STATE_PATH.equals(" ")){
            tvSearchAnywhere.setText(LocationFragment.SEARCH_COUNTRY_PATH + ", " +
                    LocationFragment.SEARCH_CITY_PATH + ", " + LocationFragment.SEARCH_STREET_PATH) ;
        }
        else if(!LocationFragment.SEARCH_COUNTRY_PATH.equals(" ") &&
                !LocationFragment.SEARCH_CITY_PATH.equals(" ") ){
            tvSearchAnywhere.setText(LocationFragment.SEARCH_COUNTRY_PATH + ", " +
                    LocationFragment.SEARCH_CITY_PATH);
        }
         else if(!LocationFragment.SEARCH_COUNTRY_PATH.equals(" ")){
             tvSearchAnywhere.setText(LocationFragment.SEARCH_COUNTRY_PATH);
         }



        if(!SearchGuestFragment.SEARCH_TOTAL_GUEST_PATH.equals("1")){
            tvSearchGuest.setText("Guest(s) - " + SearchGuestFragment.SEARCH_TOTAL_GUEST_PATH );
        }


        whereLayout.setClickable(false);
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {;
                guestLayout.setVisibility(View.VISIBLE);
                expand(searchLayout, 250, 250 );
                whereLayout.setClickable(true);
                whereLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        LocationFragment locationFragment = new LocationFragment();
                        Bundle bundle = new Bundle();
                        bundle.putBoolean(SEARCH_BAR_LOCATION, true);
                        locationFragment.setArguments(bundle);
                        addSearchBarRelatedFragment(locationFragment, "love");
                    }
                });

                guestLayout.setClickable(true);
                guestLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addSearchBarRelatedFragment(new SearchGuestFragment(),"love");
                    }
                });

            }
        });


        recyclerView.setLayoutManager(layoutManager);

        if (size == 0) {
            view.findViewById(R.id.tvEmptyListing).setVisibility(View.VISIBLE);
        }
        final HomeAdapter homeAdapter = new HomeAdapter();
        index = 0;
        //get first four items
        final int visibleThreshold = 4;

        final ProgressDialog dialog = new ProgressDialog(getActivity());

        final DatabaseInterface retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();

        int showInitialItems;
        if (size < visibleThreshold) {
            showInitialItems = size;
        } else {
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
        }

        if(getArguments() != null){
            if(getArguments().containsKey(LoadingMenuActivity.TOTAL_LISTINGS)){
                retrofit.getMultipleListingsData(index, index + visibleThreshold, " " ," " ," " ," ", " ", " ", " ", " ").enqueue(new Callback<POJOMultipleListingsDataGetResult>() {
                    @Override
                    public void onResponse(Call<POJOMultipleListingsDataGetResult> call, Response<POJOMultipleListingsDataGetResult> response) {
                        addData(homeAdapter, index, response);
                        dialog.dismiss();
                        recyclerView.setAdapter(homeAdapter);
                        index += visibleThreshold; //get fifth item in next for loop

                    }

                    @Override
                    public void onFailure(Call<POJOMultipleListingsDataGetResult> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Failed to retrieve data, check your internet conncection", Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            else if(getArguments().containsKey(SEARCH_BAR_SIZE)){
                retrofit.getMultipleListingsData(index, index + visibleThreshold, LocationFragment.SEARCH_COUNTRY_PATH,
                        LocationFragment.SEARCH_STREET_PATH , LocationFragment.SEARCH_CITY_PATH ,
                        LocationFragment.SEARCH_STATE_PATH, " ", " ", " ", " ").enqueue(new Callback<POJOMultipleListingsDataGetResult>() {
                    @Override
                    public void onResponse(Call<POJOMultipleListingsDataGetResult> call, Response<POJOMultipleListingsDataGetResult> response) {
                        addData(homeAdapter, index, response);
                        dialog.dismiss();
                        recyclerView.setAdapter(homeAdapter);
                        index += visibleThreshold; //get fifth item in next for loop

                    }

                    @Override
                    public void onFailure(Call<POJOMultipleListingsDataGetResult> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Failed to retrieve data, check your internet conncection", Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
        }
        //this is based by oldest to newest listing



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                expand(searchLayout, 250, 175
                );
                whereLayout.setClickable(false);
                guestLayout.setClickable(false);


                int itemCount = homeAdapter.getItemCount();
                int firstVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition() + 1; //+1 because starts at 0

                int showItems = (index + visibleThreshold); //because index starts at 0, - 1

                super.onScrolled(recyclerView, dx, dy);
                Log.d("numbers", "NOT Loading: " + "firstVisible is " + String.valueOf(firstVisibleItem) + " itemCount is " + String.valueOf(itemCount));

                if (index < size) {
                    if (firstVisibleItem == itemCount
                            && firstVisibleItem % visibleThreshold == 0) { //if scrolled down to the very bottom; show every fourth child


                        if (showItems > size) {
                            showItems = size;
                            //show remaining listing by forcing for loopp below to the database size
                        }

                        final ProgressDialog dialog = new ProgressDialog(getActivity());

                        dialog.setMessage("Loading...");
                        dialog.setCancelable(false);
                        dialog.show();

                        final int finalShowItems = showItems;
                        retrofit.getMultipleListingsData(index, showItems, " " ," " ," " ," ", " ", " ", " ", " ").enqueue(new Callback<POJOMultipleListingsDataGetResult>() {
                            @Override
                            public void onResponse(Call<POJOMultipleListingsDataGetResult> call, Response<POJOMultipleListingsDataGetResult> response) {

                                addData(homeAdapter, index, response);
                                dialog.dismiss();
                                for (int i = index; i < finalShowItems; i++) {
                                    Log.d("MattCool", "I am for loop");
                                    homeAdapter.addSize();
                                    homeAdapter.notifyItemInserted(i);
                                }

                                index += visibleThreshold;

                            }

                            @Override
                            public void onFailure(Call<POJOMultipleListingsDataGetResult> call, Throwable t) {
//                                "Failed to retrieve data, check your internet conncection"
                                dialog.dismiss();
                                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_LONG)
                                        .show();
                            }
                        });


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