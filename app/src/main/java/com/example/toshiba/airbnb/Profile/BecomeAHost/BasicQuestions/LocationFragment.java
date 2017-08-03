package com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toshiba.airbnb.R;


/**
 * Created by Owner on 2017-07-07.
 */

public class LocationFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    public static String COUNTRY = "COUNTRY";
    private EditText etCountryInput;
    View view;
    public void checkSavedData(){

            etCountryInput.setText(sharedPreferences.getString(LocationFilterAdapter.COUNTRY_NAME,"NULL"));

            TextView tvStreetInput = (TextView) view.findViewById(R.id.tvStreetInput);
            tvStreetInput.setText(sharedPreferences.getString(LocationFilterAdapter.STREET_NAME,"NULL"));

            EditText etCityInput = (EditText) view.findViewById(R.id.etCityInput);
            etCityInput.setText(sharedPreferences.getString(LocationFilterAdapter.CITY_NAME,"NULL"));

            EditText etStateInput = (EditText) view.findViewById(R.id.etStateInput);
            etStateInput.setText(sharedPreferences.getString(LocationFilterAdapter.STATE_NAME,"NULL"));

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressBar basicProgressBar = (ProgressBar) getActivity().findViewById(R.id.basicProgressBar);
        basicProgressBar.setProgress(80);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        //check if shared preferences contains values

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences(LocationFilterAdapter.LOCATION_SP, Context.MODE_PRIVATE);
        etCountryInput = (EditText) view.findViewById(R.id.etCountryInput);
        this.view = view;
        checkSavedData();

        Toast.makeText(getActivity(), "onViewCreated", Toast.LENGTH_LONG).show();
        view.findViewById(R.id.layoutStreet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etCountryInput.getText().toString().length() > 0) {
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString(COUNTRY, etCountryInput.getText().toString());
                    edit.apply();
                }
                getFragmentManager().beginTransaction()
                        .replace(R.id.progressFragment, new LocationFilterFragment()).addToBackStack("layoutStreet").commit();
            }
        });

        view.findViewById(R.id.bContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapFragment mapFragment = new MapFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.progressFragment, mapFragment).addToBackStack(null).commit();
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
        Toast.makeText(getActivity(), "OnResume", Toast.LENGTH_LONG).show();
//        checkSavedData();
    }
}