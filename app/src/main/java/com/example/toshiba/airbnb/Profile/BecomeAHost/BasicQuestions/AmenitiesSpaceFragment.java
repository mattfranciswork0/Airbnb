package com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.Explore.POJO.POJOListingData;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BecomeAHostActivity;
import com.example.toshiba.airbnb.Profile.BecomeAHost.ProgressActivity;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.DTO.DTOAmenitiesSpace;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.ViewListingAndYourBookingAdapter;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.Util.KeyboardUtil;
import com.example.toshiba.airbnb.Util.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Owner on 2017-07-21.
 */

public class AmenitiesSpaceFragment extends Fragment {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String AMENITIES_SPACE_FRAGMENT_FINISHED = "AMENITIES_SPACE_FRAGMENT_FINISHED";

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
        final Button bNext = (Button) view.findViewById(R.id.bNext);

        final RadioButton rbKitchen = (RadioButton) view.findViewById(R.id.rbKitchen);
        final RadioButton rbLaundry = (RadioButton) view.findViewById(R.id.rbLaundry);
        final RadioButton rbParking = (RadioButton) view.findViewById(R.id.rbParking);
        final RadioButton rbElevator = (RadioButton) view.findViewById(R.id.rbElevator);
        final RadioButton rbPool = (RadioButton) view.findViewById(R.id.rbPool);
        final RadioButton rbGym = (RadioButton) view.findViewById(R.id.rbGym);

        if(getArguments() != null) {
            if (getArguments().containsKey(ViewListingAndYourBookingAdapter.LISTING_ID)) {
                final ProgressDialog dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Loading...");
                dialog.setCancelable(false);
                dialog.show();
                bNext.setText(getString(R.string.save));
                bNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        KeyboardUtil.hideKeyboard(getActivity());
                        DatabaseInterface retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();
                        final ProgressDialog dialog = new ProgressDialog(getActivity());
                        dialog.setMessage("Updating data...");
                        dialog.setCancelable(false);
                        dialog.show();
                        retrofit.updateAmenitiesSpace(getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID),
                                new DTOAmenitiesSpace(
                                        rbKitchen.isChecked(), rbLaundry.isChecked(),
                                        rbParking.isChecked(), rbElevator.isChecked(),
                                        rbPool.isChecked(), rbGym.isChecked()
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
                });
                DatabaseInterface retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();
                retrofit.getListingData(getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID)).enqueue(new Callback<POJOListingData>() {
                    @Override
                    public void onResponse(Call<POJOListingData> call, Response<POJOListingData> response) {
                        //Load saved spaces from database
                        final POJOListingData body = response.body();
                        if (body.getKitchen() == 1) {
                            rbKitchen.setChecked(true);
                            rbKitchenCanUncheck = true;
                        }
                        if (body.getLaundry() == 1) {
                            rbLaundry.setChecked(true);
                            rbLaundryCanUncheck = true;
                        }
                        if (body.getParking() == 1) {
                            rbParking.setChecked(true);
                            rbParkingCanUncheck = true;
                        }
                        if (body.getElevator() == 1) {
                            rbElevator.setChecked(true);
                            rbElevatorCanUncheck = true;
                        }
                        if (body.getPool() == 1) {
                            rbPool.setChecked(true);
                            rbPoolUncheck = true;
                        }
                        if (body.getGym() == 1) {
                            rbGym.setChecked(true);
                            rbGymUncheck = true;
                        }


                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<POJOListingData> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_LONG).show();
                        getActivity().onBackPressed();
                    }
                });
            }
        }else {

            bNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences progressSP = getActivity().getSharedPreferences(ProgressActivity.PROGRESS_SP, Context.MODE_PRIVATE);
                    SharedPreferences.Editor progressEdit = progressSP.edit();
                    progressEdit.putBoolean(AMENITIES_SPACE_FRAGMENT_FINISHED, true);
                    progressEdit.apply();
                    Intent intent = new Intent(getActivity(), BecomeAHostActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });

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
