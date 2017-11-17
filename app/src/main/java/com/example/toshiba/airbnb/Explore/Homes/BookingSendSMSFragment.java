package com.example.toshiba.airbnb.Explore.Homes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.Profile.POJOUserData;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.ViewListingAndYourBookingAdapter;
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
        View view = inflater.inflate(R.layout.fragment_booking_send_sms, container, false);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ProgressDialog dialog = new ProgressDialog(getActivity());

        final TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        final TextView tvDesc = (TextView) view.findViewById(R.id.tvDesc);
        final Button bProceed = (Button) view.findViewById(R.id.bProceed);


        SharedPreferences sessionSP = getActivity().getSharedPreferences(SessionManager.SESSION_SP, Context.MODE_PRIVATE);
        final int userId = sessionSP.getInt(SessionManager.USER_ID, 0);
        final String firstName = sessionSP.getString(SessionManager.FIRST_NAME, "First Name");
        final String lastName = sessionSP.getString(SessionManager.LAST_NAME, "Last Name");

        final TextView tvUser = (TextView) view.findViewById(R.id.tvUser);
        final EditText etMessage = (EditText) view.findViewById(R.id.etMessage);
        tvUser.setText("Hey," + "I'm " + firstName + " " + lastName);

        if(getArguments() != null) {
            if(getArguments().containsKey(HomeDescFragment.AVAILABILITY_FROM_DATABASE)) {

                dialog.setMessage("Loading...");
                dialog.show();
                retrofit.getUserData(getArguments().getInt(HomeDescFragment.HOST_ID)).enqueue(new Callback<POJOUserData>() {
                    @Override
                    public void onResponse(Call<POJOUserData> call, final Response<POJOUserData> response) {
                        dialog.dismiss();


                        bProceed.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.setMessage("Booking your place...");
                                dialog.show();

                                final String hostPhoneNum = response.body().getPhoneNum();
                                retrofit.insertBookingSchedule(userId, getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID),
                                        new DTOBookSchedule(
                                                getArguments().getString(HomeDescFragment.CHECK_IN),
                                                getArguments().getString(HomeDescFragment.CHECK_OUT)
                                        )).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {

                                        dialog.dismiss();
                                        SmsManager sms = SmsManager.getDefault();
                                        sms.sendTextMessage(hostPhoneNum, null,
                                                "Hey, I'm" + firstName + ", " + lastName + "; " +
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
                        Toast.makeText(getActivity(), "Failed to load, check your internet connection and try again", Toast.LENGTH_LONG).show();
                        getFragmentManager().popBackStack(AvailabilityFragment.SMS_FRAGMENT, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }
                });
            }
            else if(getArguments().containsKey(HomeDescFragment.CONTACT_HOST)){

                tvTitle.setText("What do you want to say?");
                tvDesc.setVisibility(View.GONE);
                bProceed.setText("Send");
                bProceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.setMessage("Sending your text...");
                        dialog.show();
                        retrofit.getUserData(getArguments().getInt(HomeDescFragment.HOST_ID)).enqueue(new Callback<POJOUserData>() {
                            @Override
                            public void onResponse(Call<POJOUserData> call, Response<POJOUserData> response) {
                                dialog.dismiss();
                                SmsManager sms = SmsManager.getDefault();
                                String hostPhoneNum = response.body().getPhoneNum();
                                sms.sendTextMessage(hostPhoneNum , null,
                                        "Hey, I'm  " + firstName + ", " + lastName + "; " +
                                                etMessage.getText().toString(), null, null);
                                getFragmentManager().popBackStack(HomeDescFragment.CONTACT_HOST, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            }

                            @Override
                            public void onFailure(Call<POJOUserData> call, Throwable t) {
                                dialog.dismiss();
                                Toast.makeText(getActivity(), "Failed to book your place, try again", Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                });

            }
        }
    }
}
