package com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.toshiba.airbnb.Profile.BecomeAHost.BecomeAHostActivity;
import com.example.toshiba.airbnb.R;

import java.util.Map;


/**
 * Created by Owner on 2017-07-21.
 */

public class AmenitiesSpaceFragment extends Fragment {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private boolean rbKitchenCanUncheck = false;
    private boolean rbLaundryCanUncheck = false;
    private boolean rbParkingCanUncheck = false;
    private boolean rbElevatorCanUncheck = false;
    private boolean rbPoolUncheck = false;
    private boolean rbGymUncheck = false;

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
        View view = inflater.inflate(R.layout.fragment_amenities_space, container, false);
        sharedPreferences = getActivity().getSharedPreferences(AmenitiesItemFragment.AMENITIES_SP, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.bNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BecomeAHostActivity.class);
                intent.putExtra("BASIC_QUESTIONS_COMPLETED", true);
                startActivity(intent);
            }
        });

        final RadioButton rbKitchen = (RadioButton) view.findViewById(R.id.rbKitchen);
        final RadioButton rbLaundry = (RadioButton) view.findViewById(R.id.rbLaundry);
        final RadioButton rbParking = (RadioButton) view.findViewById(R.id.rbParking);
        final RadioButton rbElevator = (RadioButton) view.findViewById(R.id.rbElevator);
        final RadioButton rbPool = (RadioButton) view.findViewById(R.id.rbPool);
        final RadioButton rbGym = (RadioButton) view.findViewById(R.id.rbGym);
        //Load saved spaces in shared preferences
        if (sharedPreferences.contains(rbKitchen.getTag().toString())) {
            rbKitchen.setChecked(true);
            rbKitchenCanUncheck = true;
        }
        if (sharedPreferences.contains(rbLaundry.getTag().toString())) {
            rbLaundry.setChecked(true);
            rbLaundryCanUncheck = true;
        }
        if (sharedPreferences.contains(rbParking.getTag().toString())) {
            rbParking.setChecked(true);
            rbParkingCanUncheck = true;
        }
        if (sharedPreferences.contains(rbElevator.getTag().toString())) {
            rbElevator.setChecked(true);
            rbElevatorCanUncheck = true;
        }
        if (sharedPreferences.contains(rbPool.getTag().toString())) {
            rbPool.setChecked(true);
            rbPoolUncheck = true;
        }
        if (sharedPreferences.contains(rbGym.getTag().toString())) {
            rbGym.setChecked(true);
            rbGymUncheck = true;
        }


        //Handle click
        rbKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbKitchenCanUncheck = radioButtonFunctionality(rbKitchen, rbKitchenCanUncheck);
            }
        });

        rbLaundry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbLaundryCanUncheck = radioButtonFunctionality(rbLaundry, rbLaundryCanUncheck);
            }
        });

        rbParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbParkingCanUncheck = radioButtonFunctionality(rbParking, rbParkingCanUncheck);
            }
        });

        rbElevator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbElevatorCanUncheck = radioButtonFunctionality(rbElevator, rbElevatorCanUncheck);
            }
        });

        rbPool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbPoolUncheck = radioButtonFunctionality(rbPool, rbPoolUncheck);
            }
        });

        rbGym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbGymUncheck = radioButtonFunctionality(rbGym, rbGymUncheck);
            }
        });


    }
}
