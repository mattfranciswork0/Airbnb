package com.example.toshiba.airbnb.Profile.BecomeAHost.GetReady;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.Explore.POJO.POJOListingData;
import com.example.toshiba.airbnb.Profile.POJOUserData;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.DTO.DTOBooking;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.POJO.POJOBookingsToDelete;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.ViewListingAndYourBookingAdapter;
import com.example.toshiba.airbnb.Util.KeyboardUtil;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.Util.RetrofitUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by TOSHIBA on 09/08/2017.
 */

public class BookingFragment extends Fragment {
    public static final String BOOKING_SP = "BOOKING_SP";

    public static final String MAX_MONTH = "MAX_MONTH";
    public static final String ARRIVE_AFTER = "ARRIVE_AFTER";
    public static final String LEAVE_BEFORE = "LEAVE_BEFORE";
    public static final String MAX_STAY = "MAX_STAY";
    public static final String MIN_STAY = "MIN_STAY";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;

    public void failedToUpdate(ProgressDialog dialog) {
        Toast.makeText(getActivity(), "Failed to update listings, check your internet connection and try again", Toast.LENGTH_LONG).show();
        dialog.dismiss();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (getArguments() == null) {
            ProgressBar basicProgressBar = (ProgressBar) getActivity().findViewById(R.id.basicProgressBar);
            basicProgressBar.setProgress(75);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        ;
        sharedPreferences = getActivity().getSharedPreferences(BOOKING_SP, Context.MODE_PRIVATE);
        edit = sharedPreferences.edit();
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        final Button bNext = (Button) view.findViewById(R.id.bNext);
        final EditText etMaxMonth = (EditText) view.findViewById(R.id.etMaxMonth);
        final EditText etArriveAfter = (EditText) view.findViewById(R.id.etArriveAfter);
        final EditText etLeaveBefore = (EditText) view.findViewById(R.id.etLeaveBefore);
        final EditText etMinStay = (EditText) view.findViewById(R.id.etMinStay);
        final EditText etMaxStay = (EditText) view.findViewById(R.id.etMaxStay);
        //load from database
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
                        final int listingId = getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID);
                        KeyboardUtil.hideKeyboard(getActivity());
                        final DatabaseInterface retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();
                        final ProgressDialog dialog = new ProgressDialog(getActivity());
                        dialog.setMessage("Updating data...");
                        dialog.setCancelable(false);

                        dialog.show();

                        retrofit.deleteBookings(listingId).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                retrofit.getListingData(listingId).enqueue(new Callback<POJOListingData>() {
                                    @Override
                                    public void onResponse(Call<POJOListingData> call, Response<POJOListingData> response) {

                                        final String placeTitle = response.body().getPlaceTitle();
                                        final String country = response.body().getCountry();
                                        final String street = response.body().getStreet();
                                        retrofit.bookingsToDeleteData(listingId).enqueue(new Callback<POJOBookingsToDelete>() {
                                            @Override
                                            public void onResponse(Call<POJOBookingsToDelete> call, Response<POJOBookingsToDelete> response) {
                                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                                final List<String> checkInArrayList = new ArrayList<String>();
                                                final List<String> checkOutArrayList = new ArrayList<String>();
                                                final int size = response.body().getUserId().size();

                                                SimpleDateFormat mySqlFormat = new SimpleDateFormat("yyyy-MM-dd");

                                                for (int i = 0; i < size; i++) {

                                                    Date checkOutDate = null;
                                                    try {
                                                        Date checkInDate = mySqlFormat.parse(response.body().getCheckIn().get(i).getCheckIn());
                                                        String checkInDateAsString = sdf.format(checkInDate);
                                                        checkOutDate = mySqlFormat.parse(response.body().getCheckOut().get(i).getCheckOut());
                                                        String checkOutDateAsString = sdf.format(checkOutDate);
                                                        checkInArrayList.add(checkInDateAsString);
                                                        checkOutArrayList.add(checkOutDateAsString);
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }

                                                }


                                                //send sms messages to the users affected by the listing lenght change
                                                for (int i = 0; i < size; i++) {
                                                    final int finalI = i;
                                                    retrofit.getUserData(response.body().getUserId().get(i).getUserId()).enqueue(new Callback<POJOUserData>() {
                                                        @Override
                                                        public void onResponse(Call<POJOUserData> call, Response<POJOUserData> response) {
                                                            SmsManager sms = SmsManager.getDefault();
                                                            sms.sendTextMessage(response.body().getPhoneNum(), null,
                                                                    "Airbnb -  Hey, " + response.body().getFirstName() + " " + response.body().getLastName() +
                                                                            "Your booking has been canceled for " + placeTitle + " in " + street + " , " + country + " because the host changed their listing availability."
                                                                            + " You are no longer booked from" + checkInArrayList.get(finalI) + " to " + checkOutArrayList.get(finalI)
                                                                    , null, null);
                                                            if(finalI == size - 1 ) {
                                                                retrofit.updateBooking(listingId,
                                                                        new DTOBooking(etMaxMonth.getText().toString(), etArriveAfter.getText().toString(),
                                                                                etLeaveBefore.getText().toString(), etMaxStay.getText().toString(),
                                                                                etMinStay.getText().toString())).enqueue(new Callback<Void>() {
                                                                    @Override
                                                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                                                        dialog.dismiss();
                                                                        Toast.makeText(getActivity(), "Updated", Toast.LENGTH_LONG).show();
                                                                        getFragmentManager().popBackStack();
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<Void> call, Throwable t) {
                                                                        failedToUpdate(dialog);
                                                                    }
                                                                });
                                                            }

                                                        }

                                                        @Override
                                                        public void onFailure(Call<POJOUserData> call, Throwable t) {
                                                            failedToUpdate(dialog);
                                                        }
                                                    });
                                                }

                                            }

                                            @Override
                                            public void onFailure(Call<POJOBookingsToDelete> call, Throwable t) {
                                                failedToUpdate(dialog);
                                            }
                                        });


                                    }

                                    @Override
                                    public void onFailure(Call<POJOListingData> call, Throwable t) {
                                        failedToUpdate(dialog);
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                failedToUpdate(dialog);
                            }
                        });
                        //get listing data info to alert users whose' booking to canceled due to the chnage in listing length

                    }
                });
                DatabaseInterface retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();
                retrofit.getListingData(getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID)).enqueue(new Callback<POJOListingData>() {
                    @Override
                    public void onResponse(Call<POJOListingData> call, Response<POJOListingData> response) {
                        POJOListingData body = response.body();
                        etMaxMonth.setText(body.getListingLength());
                        etArriveAfter.setText(body.getArriveAfter());
                        etLeaveBefore.setText(body.getLeaveBefore());
                        etMinStay.setText(body.getMinStay());
                        etMaxStay.setText(body.getMaxStay());
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
        } else {
            etMaxMonth.setText(sharedPreferences.getString(MAX_MONTH, ""));
            etArriveAfter.setText(sharedPreferences.getString(ARRIVE_AFTER, ""));
            etLeaveBefore.setText(sharedPreferences.getString(LEAVE_BEFORE, ""));
            etMinStay.setText(sharedPreferences.getString(MIN_STAY, ""));
            etMaxStay.setText(sharedPreferences.getString(MAX_STAY, ""));

            bNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    KeyboardUtil.hideKeyboard(getActivity());
                    if (etMaxMonth.getText().length() > 0 &&
                            etArriveAfter.getText().length() > 0 &&
                            etLeaveBefore.getText().length() > 0 &&
                            etMinStay.getText().length() > 0) {


                        edit.putString(MAX_MONTH, etMaxMonth.getText().toString());
                        edit.putString(ARRIVE_AFTER, etArriveAfter.getText().toString());
                        edit.putString(LEAVE_BEFORE, etLeaveBefore.getText().toString());
                        edit.putString(MIN_STAY, etMinStay.getText().toString());

                        //etMaxStay is optional
                        if (etMaxStay.getText().length() > 0) {
                            if (Integer.parseInt(etMaxStay.getText().toString()) >
                                    Integer.parseInt(etMinStay.getText().toString())) {
                                edit.putString(MAX_STAY, etMaxStay.getText().toString());
                            } else {
                                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                                dialog.setMessage("Maximum stay must be greater than minimum stay");
                                dialog.setCancelable(false);
                                dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                                return;
                            }

                        }

                        edit.apply();

                    } else {
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
                }
            });

        }
        TextWatcher monthTextWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (etMaxMonth.getText().length() > 0) {
                    int val = Integer.parseInt(s.toString());
                    if (val > 12) {
                        s.replace(0, s.length(), "12", 0, 2);
                        Toast.makeText(getActivity(), "Months must be less or equal than to 12", Toast.LENGTH_LONG).show();
                    }
                    if (val == 0) {
                        s.replace(0, s.length(), "1", 0, 1);
                        Toast.makeText(getActivity(), "Cannot have an input of zero", Toast.LENGTH_LONG).show();
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        };
        etMaxMonth.addTextChangedListener(monthTextWatcher);

        TextWatcher stayTextWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (etMinStay.getText().length() > 0) {
                    int val = Integer.parseInt(s.toString());
                    if (val == 0) {
                        s.replace(0, s.length(), "1", 0, 1);
                        Toast.makeText(getActivity(), "Cannot have an input of zero", Toast.LENGTH_LONG).show();
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        };
//        etMaxMonth.addTextChangedListener(stayTextWatcher);
        etMinStay.addTextChangedListener(stayTextWatcher);

    }
}
