package com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.toshiba.airbnb.R;


/**
 * Created by Owner on 2017-07-21.
 */


public class AmenitiesItemFragment extends Fragment {
    public static final String AMENITIES_SP = "AMENITIES_SP";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private boolean rbEssentialsCanUncheck = false;
    private boolean rbInternetCanUncheck = false;
    private boolean rbShampooCanUncheck = false;
    private boolean rbHangersCanUncheck = false;
    private boolean rbTVCanUncheck = false;
    private boolean rbHeatingCanUncheck = false;
    private boolean rbAirConditoningCanUncheck = false;
    private boolean rbBreakfastCanUncheck = false;

    public boolean radioButtonFunctionality(RadioButton radioButton, boolean radioCanUnCheck) {
        if (radioButton instanceof RadioButton) {
            String tag = (String) radioButton.getTag();
            if (radioButton.isChecked() && !radioCanUnCheck) {
                editor.remove(tag);
                editor.putBoolean(tag, true);
                editor.apply();
                radioCanUnCheck = true;
            } else if (radioCanUnCheck) {
                editor.remove(tag);
                editor.apply();
                radioButton.setChecked(false);
                radioCanUnCheck = false;
            }
        }
        return radioCanUnCheck;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_amenities_item, container, false);
        //Essentials
        sharedPreferences = getActivity().getSharedPreferences(AMENITIES_SP, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.bNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.progressFragment, new AmenitiesSpaceFragment()).addToBackStack(null).commit();
            }
        });

        final RadioButton rbEssentials = (RadioButton) view.findViewById(R.id.rbEssentials);
        final RadioButton rbInternet = (RadioButton) view.findViewById(R.id.rbInternet);
        final RadioButton rbShampoo = (RadioButton) view.findViewById(R.id.rbShampoo);
        final RadioButton rbHangers = (RadioButton) view.findViewById(R.id.rbHangers);
        final RadioButton rbTV = (RadioButton) view.findViewById(R.id.rbTV);
        final RadioButton rbHeating = (RadioButton) view.findViewById(R.id.rbHeating);
        final RadioButton rbAirConditioning = (RadioButton) view.findViewById(R.id.rbAirConditioning);
        final RadioButton rbBreakfast = (RadioButton) view.findViewById(R.id.rbBreakfast);

        //Load saved amenities in shared preferences
        if (sharedPreferences.contains(rbEssentials.getTag().toString())) {
            rbEssentials.setChecked(true);
            rbEssentialsCanUncheck = true;
        }
        if (sharedPreferences.contains(rbInternet.getTag().toString())) {
            rbInternet.setChecked(true);
            rbInternetCanUncheck = true;
        }
        if (sharedPreferences.contains(rbShampoo.getTag().toString())) {
            rbShampoo.setChecked(true);
            rbShampooCanUncheck = true;
        }
        if (sharedPreferences.contains(rbHangers.getTag().toString())) {
            rbHangers.setChecked(true);
            rbHangersCanUncheck = true;
        }
        if (sharedPreferences.contains(rbTV.getTag().toString())) {
            rbTV.setChecked(true);
            rbTVCanUncheck = true;
        }
        if (sharedPreferences.contains(rbHeating.getTag().toString())) {
            rbHeating.setChecked(true);
            rbHeatingCanUncheck = true;
        }
        if (sharedPreferences.contains(rbAirConditioning.getTag().toString())) {
            rbAirConditioning.setChecked(true);
            rbAirConditoningCanUncheck = true;
        }
        if (sharedPreferences.contains(rbBreakfast.getTag().toString())) {
            rbBreakfast.setChecked(true);
            rbBreakfastCanUncheck = true;
        }
        //Handle click
        rbEssentials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbEssentialsCanUncheck = radioButtonFunctionality(rbEssentials, rbEssentialsCanUncheck);
            }
        });

        rbInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbInternetCanUncheck = radioButtonFunctionality(rbInternet, rbInternetCanUncheck);
            }
        });

        rbShampoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbShampooCanUncheck = radioButtonFunctionality(rbShampoo, rbShampooCanUncheck);
            }
        });

        rbHangers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbHangersCanUncheck = radioButtonFunctionality(rbHangers, rbHangersCanUncheck);
            }
        });

        rbTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbTVCanUncheck = radioButtonFunctionality(rbTV, rbTVCanUncheck);
            }
        });

        rbHeating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbHeatingCanUncheck = radioButtonFunctionality(rbHeating, rbHeatingCanUncheck);
            }
        });

        rbAirConditioning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbAirConditoningCanUncheck = radioButtonFunctionality(rbAirConditioning, rbAirConditoningCanUncheck);
            }
        });


        rbBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbBreakfastCanUncheck = radioButtonFunctionality(rbBreakfast, rbBreakfastCanUncheck);
            }
        });


    }


}
