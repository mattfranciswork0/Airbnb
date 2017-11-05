package com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.Profile.ProfileFragment;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.Util.RetrofitUtil;
import com.example.toshiba.airbnb.UserAuthentication.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by TOSHIBA on 15/09/2017.
 */

public class ViewListingAndYourBookingFragment extends Fragment {
    DatabaseInterface retrofit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_listing_and_your_booking, container, false);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        if (getArguments() != null) {
            if (getArguments().containsKey(ProfileFragment.YOUR_BOOKING)) {
                tvTitle.setText("Your Bookings");
                final Call<POJOYourBookingGetResult> call = retrofit.getBookingListingImageAndTitle(
                        getActivity().getSharedPreferences(SessionManager.SESSION_SP, Context.MODE_PRIVATE)
                                .getInt(SessionManager.USER_ID, 0));

                call.enqueue(new Callback<POJOYourBookingGetResult>() {
                    @Override
                    public void onResponse(Call<POJOYourBookingGetResult> call, Response<POJOYourBookingGetResult> response) {
                        RecyclerView rvListing = (RecyclerView) view.findViewById(R.id.rvListing);
                        rvListing.setLayoutManager(new LinearLayoutManager(getActivity()));
                        Log.d("inLoveViewListingFrag", getArguments().getBoolean(ProfileFragment.YOUR_BOOKING) + "");
                        rvListing.setAdapter(new ViewListingAndYourBookingAdapter(response.body().getResult().size(), getActivity(),
                                getArguments().getBoolean(ProfileFragment.YOUR_BOOKING)));

                        if (response.body().getResult().size() == 0) {
                            view.findViewById(R.id.tvEmptyListing).setVisibility(View.VISIBLE);
                        }

                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<POJOYourBookingGetResult> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_LONG).show();
                        getActivity().onBackPressed();
                    }
                });


                progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        call.cancel();
                        getActivity().onBackPressed();
                        //close socket connection
                    }
                });

            }
        } else {
            final Call<POJOListingImageAndTitleGetResult> call = retrofit.getListingImageAndTitle(getActivity().getSharedPreferences(SessionManager.SESSION_SP, Context.MODE_PRIVATE)
                    .getInt(SessionManager.USER_ID, 0));

            call.enqueue(new Callback<POJOListingImageAndTitleGetResult>() {
                @Override
                public void onResponse(Call<POJOListingImageAndTitleGetResult> call, Response<POJOListingImageAndTitleGetResult> response) {
                    RecyclerView rvListing = (RecyclerView) view.findViewById(R.id.rvListing);
                    rvListing.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rvListing.setAdapter(new ViewListingAndYourBookingAdapter(response.body().getResult().size(), getActivity(), false));
                    if (response.body().getResult().size() == 0) {
                        view.findViewById(R.id.tvEmptyListing).setVisibility(View.VISIBLE);
                    }
                    progressDialog.dismiss();
                    Log.d("inLove", "false");
                }

                @Override
                public void onFailure(Call<POJOListingImageAndTitleGetResult> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_LONG).show();
                    getActivity().onBackPressed();
                }
            });

            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    call.cancel();
                    getActivity().onBackPressed();
                    //close socket connection
                }
            });

        }
    }
}
