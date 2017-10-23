package com.example.toshiba.airbnb.Explore;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.Profile.POJOUserData;
import com.example.toshiba.airbnb.Profile.ViewListing.ViewListingAdapter;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.UserAuthentication.SessionManager;
import com.example.toshiba.airbnb.Util.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by TOSHIBA on 18/10/2017.
 */

public class BookingSendSMSFragment extends Fragment {
    DatabaseInterface retrofit;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_send_sms_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.show();


        SharedPreferences sessionSP = getActivity().getSharedPreferences(SessionManager.SESSION_SP, Context.MODE_PRIVATE);
        final int userId = sessionSP.getInt(SessionManager.USER_ID, 0);

        final TextView tvUser = (TextView) view.findViewById(R.id.tvUser);
        final EditText etMessage = (EditText) view.findViewById(R.id.etMessage);

        retrofit.getUserData(userId).enqueue(new Callback<POJOUserData>() {
            @Override
            public void onResponse(Call<POJOUserData> call, final Response<POJOUserData> response) {
                dialog.dismiss();
                tvUser.setText(response.body().getFirstName() + " " + response.body().getLastName());

                view.findViewById(R.id.bProceed).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.setMessage("Booking your place...");
                        dialog.show();

                        final String userPhoneNum = response.body().getPhoneNum();
                        retrofit.insertBookingSchedule(userId, getArguments().getInt(ViewListingAdapter.LISTING_ID),
                                new DTOBookSchedule(
                                        getArguments().getString(HomeDescFragment.CHECK_IN),
                                        getArguments().getString(HomeDescFragment.CHECK_OUT)
                                )).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                dialog.dismiss();
                                SmsManager sms = SmsManager.getDefault();
                                sms.sendTextMessage(userPhoneNum, null,
                                        "Hey, I'm" + tvUser.getText().toString() + ", " +
                                        etMessage.getText().toString(), null, null);
                                Toast.makeText(getActivity(), "Booked your place", Toast.LENGTH_LONG).show();
                                getFragmentManager().popBackStack(HomeDescFragment.AVAILABILITY_FRAGMENT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                dialog.dismiss();
                                Toast.makeText(getActivity(), "Failed to book your place, try again", Toast.LENGTH_LONG).show();
                            }
                        });


                    }
                });
            }

            @Override
            public void onFailure(Call<POJOUserData> call, Throwable t) {
                getFragmentManager().popBackStack(AvailabilityFragment.SMS_FRAGMENT, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });


    }
}
