package com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.Explore.HomeFragment;
import com.example.toshiba.airbnb.Explore.POJOListingData;
import com.example.toshiba.airbnb.Explore.POJOTotalListings;
import com.example.toshiba.airbnb.Explore.SearchGuestFragment;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.EditListingDTO.PlaceLocationDTO;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.ViewListingAndYourBookingAdapter;
import com.example.toshiba.airbnb.Util.KeyboardUtil;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.Util.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Owner on 2017-07-07.
 */

public class LocationFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    public static String COUNTRY = "COUNTRY";
    public static String EXTRA_DETAILS = "EXTRA_DETAILS";

    public static String SEARCH_COUNTRY_PATH = " ";
    public static String SEARCH_STREET_PATH = " ";
    public static String SEARCH_CITY_PATH = " ";
    public static String SEARCH_STATE_PATH = " ";
    private EditText etCountryInput;
    TextView tvStreetInput;
    EditText etCityInput;
    EditText etStateInput;
    EditText etExtraDetailsInput;
    View view;
    SharedPreferences.Editor edit;

    public String checkIfEditTextIsEmpty(String etInput){
        if(etInput == null || etInput.equals("")){
            //cannot use an empty string for retrofit parameters/paths, therefore a space is used
            return  " ";
        }
        return etInput;
    }
    public void fillInRequirementsAlertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setMessage("Please fill in the required fields");
        dialog.setCancelable(false);
        dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void checkSavedData() {

        etCountryInput.setText(sharedPreferences.getString(LocationFilterAdapter.COUNTRY_NAME, ""));


        tvStreetInput.setText(sharedPreferences.getString(LocationFilterAdapter.STREET_NAME, ""));

        if (sharedPreferences.contains(EXTRA_DETAILS)) {

            etExtraDetailsInput.setText(sharedPreferences.getString(EXTRA_DETAILS, ""));
        }

        etCityInput.setText(sharedPreferences.getString(LocationFilterAdapter.CITY_NAME, ""));


        etStateInput.setText(sharedPreferences.getString(LocationFilterAdapter.STATE_NAME, ""));

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) {
            ProgressBar basicProgressBar = (ProgressBar) getActivity().findViewById(R.id.basicProgressBar);
            basicProgressBar.setProgress(80);
        }
        sharedPreferences = getActivity().getSharedPreferences(LocationFilterAdapter.LOCATION_SP, Context.MODE_PRIVATE);
        edit = sharedPreferences.edit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        //check if shared preferences contains values

        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = view;
        //checkSavedData in OnViewCreated wont refresh the views when popBackStack is called in LocationFilterFragment
        //hence it's called on OnResume..
        etCountryInput = (EditText) view.findViewById(R.id.etCountryInput);
        tvStreetInput = (TextView) view.findViewById(R.id.tvStreetInput);
        etExtraDetailsInput = (EditText) view.findViewById(R.id.etExtraDetailsInput);
        etCityInput = (EditText) view.findViewById(R.id.etCityInput);
        etStateInput = (EditText) view.findViewById(R.id.etStateInput);
        final Button bNext = (Button) view.findViewById(R.id.bNext);
        if (getArguments() != null) {
            if (getArguments().containsKey(ViewListingAndYourBookingAdapter.LISTING_ID)) {
                final ProgressDialog dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Loading...");
                dialog.show();
                dialog.setCancelable(false);
                bNext.setText(getString(R.string.save));

                DatabaseInterface retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();
                retrofit.getListingData(getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID)).enqueue(new Callback<POJOListingData>() {
                    @Override
                    public void onResponse(Call<POJOListingData> call, Response<POJOListingData> response) {
                        POJOListingData body = response.body();
                        etCountryInput.setText(body.getCountry());
                        tvStreetInput.setText(body.getStreet());
                        etExtraDetailsInput.setText(body.getExtraPlaceDetails());
                        etCityInput.setText(body.getCity());
                        etStateInput.setText(body.getState());

                        bNext.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (etCountryInput.getText().length() > 0 && tvStreetInput.getText().length() > 0) {
                                    if (etCityInput.getText().length() > 0 || etStateInput.getText().length() > 0) {
                                        KeyboardUtil.hideKeyboard(getActivity());
                                        DatabaseInterface retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();
                                        final ProgressDialog dialog = new ProgressDialog(getActivity());
                                        dialog.setMessage("Updating data...");
                                        dialog.setCancelable(false);
                                        dialog.show();
                                        retrofit.updateLocation(getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID),
                                                new PlaceLocationDTO(
                                                        etCountryInput.getText().toString(),
                                                        tvStreetInput.getText().toString(), etExtraDetailsInput.getText().toString(),
                                                        etCityInput.getText().toString(), etStateInput.getText().toString()
                                                )).enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                                dialog.dismiss();
                                                Toast.makeText(getActivity(), "Updated", Toast.LENGTH_LONG).show();
                                                getFragmentManager().popBackStack();
                                            }

                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {
                                                dialog.dismiss();
                                                Toast.makeText(getActivity(), getString(R.string.failedToUpdate), Toast.LENGTH_LONG);

                                            }
                                        });
                                    }
                                } else {
                                    fillInRequirementsAlertDialog();
                                }
                            }
                        });

                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<POJOListingData> call, Throwable t) {
                        Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        getActivity().onBackPressed();
                    }
                });

            } else if (getArguments().containsKey(HomeFragment.SEARCH_BAR_LOCATION)){
                //get rid of section tab from activity
                getActivity().findViewById(R.id.sectionTab).setVisibility(View.GONE);
                LinearLayout layouTapInfo = (LinearLayout) view.findViewById(R.id.layoutTapInfo);
                RelativeLayout layoutExtraDetails = (RelativeLayout) view.findViewById(R.id.layoutExtraDetails);
                layoutExtraDetails.setVisibility(View.GONE);
                view.findViewById(R.id.tvExtraDetailsLine).setVisibility(View.GONE);

                layouTapInfo.setClickable(false);
                layouTapInfo.setVisibility(View.INVISIBLE);

                if(!SEARCH_COUNTRY_PATH.equals(" ")){
                    etCountryInput.setText(SEARCH_COUNTRY_PATH);
                }
                if(!SEARCH_STREET_PATH.equals(" ")){
                    tvStreetInput.setText(SEARCH_STREET_PATH);
                }
                if(!SEARCH_CITY_PATH.equals(" ")){
                    etCityInput.setText(SEARCH_CITY_PATH);
                }
                if(!SEARCH_STATE_PATH.equals(" ")){
                    etStateInput.setText(SEARCH_STATE_PATH);
                }
                bNext.setText("Search");
                bNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final ProgressDialog dialog = new ProgressDialog(getActivity());
                        dialog.setMessage("Fetching your results...");
                        dialog.show();

                        DatabaseInterface retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();
                        SEARCH_COUNTRY_PATH = checkIfEditTextIsEmpty(etCountryInput.getText().toString().trim());
                        SEARCH_STREET_PATH =  checkIfEditTextIsEmpty(tvStreetInput.getText().toString().trim());
                        SEARCH_CITY_PATH = checkIfEditTextIsEmpty(etCityInput.getText().toString().trim());
                        SEARCH_STATE_PATH  = checkIfEditTextIsEmpty(etStateInput.getText().toString().trim());


                        retrofit.getTotalListings(
                                LocationFragment.SEARCH_COUNTRY_PATH,
                                LocationFragment.SEARCH_STREET_PATH,
                                LocationFragment.SEARCH_CITY_PATH,
                                LocationFragment.SEARCH_STATE_PATH,
                                SearchGuestFragment.SEARCH_TOTAL_GUEST_PATH,
                                SearchGuestFragment.SEARCH_INFANTS_PATH,
                                SearchGuestFragment.SEARCH_CHILDREN_PATH,
                                String.valueOf(SearchGuestFragment.SEARCH_PETS_ALLOWED_PATH)).enqueue(new Callback<POJOTotalListings>() {
                            @Override
                            public void onResponse(Call<POJOTotalListings> call, Response<POJOTotalListings> response) {

                                Log.d("searchCountry", LocationFragment.SEARCH_COUNTRY_PATH);
                                Log.d("searchStreet", LocationFragment.SEARCH_STREET_PATH);
                                Log.d("searchCity", LocationFragment.SEARCH_CITY_PATH);
                                Log.d("searchState",  LocationFragment.SEARCH_STATE_PATH);
                                Log.d("searchGuest", SearchGuestFragment.SEARCH_TOTAL_GUEST_PATH);
                                Log.d("searchInfants", SearchGuestFragment.SEARCH_INFANTS_PATH);
                                Log.d("searchChildren", SearchGuestFragment.SEARCH_CHILDREN_PATH);
                                Log.d("searchPets", String.valueOf(SearchGuestFragment.SEARCH_PETS_ALLOWED_PATH));
                                Bundle bundle = new Bundle();
                                bundle.putInt(HomeFragment.SEARCH_BAR_SIZE, Integer.parseInt(response.body().getTotalListings()));
                                HomeFragment homeFragment = new HomeFragment();
                                homeFragment.setArguments(bundle);
                                dialog.dismiss();
                                getFragmentManager().beginTransaction().replace(R.id.sectionFragmentReplace, homeFragment).commit();


                            }

                            @Override
                            public void onFailure(Call<POJOTotalListings> call, Throwable t) {
                                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_LONG).show();
//                                Toast.makeText(getActivity(), "Failed to fetch data, check your internet connection and try again", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        });


                    }
                });
            }
        } else {
            checkSavedData();

            bNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    KeyboardUtil.hideKeyboard(getActivity());
                    edit.putString(EXTRA_DETAILS, "");
                    edit.apply();
                    if (etCountryInput.getText().length() > 0 && tvStreetInput.getText().length() > 0) {
                        if (etCityInput.getText().length() > 0 || etStateInput.getText().length() > 0) {
                            MapFragment mapFragment = new MapFragment();
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.progressFragment, mapFragment).addToBackStack(null).commit();
                        } else {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                            dialog.setMessage("Please fill in your state / city");
                            dialog.setCancelable(false);
                            dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }

                    } else {
                        fillInRequirementsAlertDialog();
                    }


                }
            });
        }

        view.findViewById(R.id.layoutStreet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.hideKeyboard(getActivity());
                if (etCountryInput.getText().toString().length() > 0) {
                    edit = sharedPreferences.edit();
                    edit.putString(COUNTRY, etCountryInput.getText().toString());
                    edit.apply();
                }
                Log.d("hiMatt", "click");
                if(getArguments() != null){
                    if (getArguments().containsKey(HomeFragment.SEARCH_BAR_LOCATION)) {
                        Log.d("hiMatt", "true");
                        LocationFilterFragment locationFilterFragment = new LocationFilterFragment();
                        Bundle bundle = new Bundle();
                        bundle.putBoolean(HomeFragment.SEARCH_BAR_LOCATION, getArguments().containsKey(HomeFragment.SEARCH_BAR_LOCATION));
                        locationFilterFragment.setArguments(bundle);
                        getFragmentManager().beginTransaction()
                                .add(R.id.sectionFragmentReplace, locationFilterFragment).hide(LocationFragment.this)
                                .addToBackStack(null).commit();
                    }
                } else {
                    getFragmentManager().beginTransaction()
                            .add(R.id.progressFragment, new LocationFilterFragment()).hide(LocationFragment.this).addToBackStack(null).commit();
                }
            }
        });

        view.findViewById(R.id.layoutTapInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.progressFragment, new LocationInfoFragment()).addToBackStack("locationFragment").commit();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        checkSavedData();
    }
}