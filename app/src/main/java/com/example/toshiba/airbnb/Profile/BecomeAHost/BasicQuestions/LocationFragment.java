package com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.Explore.POJOListingData;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListingFragment;
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
    private EditText etCountryInput;
    TextView tvStreetInput;
    EditText etCityInput;
    EditText etStateInput;
    EditText etExtraDetailsInput;
    View view;
    SharedPreferences.Editor edit;


    public void checkSavedData(){

            etCountryInput.setText(sharedPreferences.getString(LocationFilterAdapter.COUNTRY_NAME,""));


            tvStreetInput.setText(sharedPreferences.getString(LocationFilterAdapter.STREET_NAME,""));

            if(sharedPreferences.contains(EXTRA_DETAILS)) {

                etExtraDetailsInput.setText(sharedPreferences.getString(EXTRA_DETAILS, ""));
            }

            etCityInput.setText(sharedPreferences.getString(LocationFilterAdapter.CITY_NAME,""));


            etStateInput.setText(sharedPreferences.getString(LocationFilterAdapter.STATE_NAME,""));

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() == null) {
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
        Button bNext = (Button) view.findViewById(R.id.bNext);

        if(getArguments().containsKey(ViewListingAndYourBookingAdapter.LISTING_ID)){
            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.show();
            bNext.setText(getString(R.string.save));
            bNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            DatabaseInterface retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();
            retrofit.getListingData(getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID)).enqueue(new Callback<POJOListingData>() {
                @Override
                public void onResponse(Call<POJOListingData> call, Response<POJOListingData> response) {
                    etCountryInput.setText(response.body().getCountry());
                    tvStreetInput.setText(response.body().getStreet());
                    etExtraDetailsInput.setText(response.body().getExtraPlaceDetails());
                    etCityInput.setText(response.body().getCity());
                    etStateInput.setText(response.body().getState());
                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<POJOListingData> call, Throwable t) {
                    Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    getActivity().onBackPressed();
                }
            });

        }else {
            checkSavedData();

            bNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    KeyboardUtil.hideKeyboard(getActivity());
                    edit.putString(EXTRA_DETAILS, "");
                    edit.apply();
                    if(etCountryInput.getText().length() > 0 && tvStreetInput.getText().length() > 0 ){
                        if(etCityInput.getText().length() > 0 || etStateInput.getText().length() > 0){
                            MapFragment mapFragment = new MapFragment();
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.progressFragment, mapFragment).addToBackStack(null).commit();
                        }
                        else{
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                            dialog.setMessage("Please fill in your state / city");
                            dialog.setCancelable(false);
                            dialog.setNegativeButton("OK",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }

                    }else{
                        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                        dialog.setMessage("Please fill in the required fields");
                        dialog.setCancelable(false);
                        dialog.setNegativeButton("OK",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }



                }
            });
        }

        view.findViewById(R.id.layoutStreet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               KeyboardUtil.hideKeyboard(getActivity());
                if(etCountryInput.getText().toString().length() > 0) {
                    edit = sharedPreferences.edit();
                    edit.putString(COUNTRY, etCountryInput.getText().toString());
                    edit.apply();
                }
                getFragmentManager().beginTransaction()
                        .replace(R.id.progressFragment, new LocationFilterFragment()).addToBackStack("layoutStreet").commit();
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