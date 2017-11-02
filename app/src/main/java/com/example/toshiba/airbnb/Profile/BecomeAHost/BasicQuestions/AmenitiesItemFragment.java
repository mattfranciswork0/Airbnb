package com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.Explore.POJOListingData;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.EditListingDTO.AmenitiesItemDTO;
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
        final Button bNext = (Button) view.findViewById(R.id.bNext);
        bNext.setOnClickListener(new View.OnClickListener() {
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


        DatabaseInterface retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();
        if (getArguments() != null) {
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
                        retrofit.updateAmenitiesItem(getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID),
                                new AmenitiesItemDTO(
                                        rbEssentials.isChecked(), rbInternet.isChecked(),
                                        rbShampoo.isChecked(), rbHangers.isChecked(),
                                        rbTV.isChecked(), rbHeating.isChecked(),
                                        rbAirConditioning.isChecked(), rbBreakfast.isChecked()
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
                retrofit.getListingData(
                        getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID)
                ).enqueue(new Callback<POJOListingData>() {
                    @Override
                    public void onResponse(Call<POJOListingData> call, final Response<POJOListingData> response) {
                        //Load saved amenities in database
                        final POJOListingData body = response.body();
                        if (body.getEssentials() == 1) {
                            rbEssentials.setChecked(true);
                            rbEssentialsCanUncheck = true;
                        }
                        if (body.getInternet() == 1) {
                            rbInternet.setChecked(true);
                            rbInternetCanUncheck = true;
                        }
                        if (body.getShampoo() == 1) {
                            rbShampoo.setChecked(true);
                            rbShampooCanUncheck = true;
                        }
                        if (body.getHangers() == 1) {
                            rbHangers.setChecked(true);
                            rbHangersCanUncheck = true;
                        }
                        if (body.getTv() == 1) {
                            rbTV.setChecked(true);
                            rbTVCanUncheck = true;
                        }
                        if (body.getHeating() == 1) {
                            rbHeating.setChecked(true);
                            rbHeatingCanUncheck = true;
                        }
                        if (body.getAirConditioning() == 1) {
                            rbAirConditioning.setChecked(true);
                            rbAirConditoningCanUncheck = true;
                        }
                        if (body.getBreakfast() == 1) {
                            rbBreakfast.setChecked(true);
                            rbBreakfastCanUncheck = true;
                        }


                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<POJOListingData> call, Throwable t) {
                        Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_LONG).show();
                        getActivity().onBackPressed();
                        dialog.dismiss();
                    }

                });
            }
        } else {
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
