package com.example.toshiba.airbnb.Profile;


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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cloudinary.Cloudinary;
import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BecomeAHostActivity;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.ViewListingAndYourBookingActivity;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.Util.RetrofitUtil;
import com.example.toshiba.airbnb.UserAuthentication.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Owner on 2017-07-02.
 */

public class ProfileFragment extends Fragment {
    SessionManager sessionManager;
    DatabaseInterface retrofit;
    int userId;
    Call<POJOUserData> getUserDataCall;
    public static String YOUR_BOOKING = "YOUR_BOOKING";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();
        userId = getActivity().getSharedPreferences(SessionManager.SESSION_SP, Context.MODE_PRIVATE)
                .getInt(SessionManager.USER_ID, 0);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container , false);
        sessionManager = new SessionManager(getActivity());

        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(sessionManager.isLoggedIn()){
            SharedPreferences sessionSP = getActivity().getSharedPreferences(SessionManager.SESSION_SP, Context.MODE_PRIVATE);
            TextView tvName = (TextView) view.findViewById(R.id.tvName);
            tvName.setText(sessionSP.getString(sessionManager.FIRST_NAME, "") + " " + sessionSP.getString(sessionManager.LAST_NAME, ""));
        }

        view.findViewById(R.id.tvBecomeAHost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), TestActivity.class);
                Intent intent = new Intent(getActivity(), BecomeAHostActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.tvEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HostProfileActivity.class);
                startActivity(intent);
            }
        });

        final ImageView ivProfilePic = (ImageView) view.findViewById(R.id.ivProfilePic);
        Glide.with(getActivity()).
                load(getResources().getString(R.string.defaultProfilePicture)).into(ivProfilePic);
        final Cloudinary cloudinary = new Cloudinary(getActivity().getResources().getString(R.string.cloudinaryEnviornmentVariable)); //configured using an environment variable
        getUserDataCall = retrofit.getUserData(userId);

        getUserDataCall.enqueue(new Callback<POJOUserData>() {
            @Override
            public void onResponse(Call<POJOUserData> call, Response<POJOUserData> response) {
                Log.d("ProfileFragment", "ProfileFragment pic");
                if(response.body().getProfileImagePath() != null){
                    Glide.with(getActivity()).
                            load(cloudinary.url().generate(response.body().getProfileImagePath().
                                    toString())).into(ivProfilePic);
                }

            }

            @Override
            public void onFailure(Call<POJOUserData> call, Throwable t) {
                Log.d("ProfileFragment", t.toString());
            }
        });
        Glide.with(getActivity()).load(getResources().getString(R.string.defaultProfilePicture)).into(ivProfilePic);

        ivProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HostProfileActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.tvListings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewListingAndYourBookingActivity.class);
                Log.d("inLove", "tvListings");
                startActivity(intent);
            }
        });

        view.findViewById(R.id.tvYourBooking ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewListingAndYourBookingActivity.class);
                Log.d("inLove", "tvYourBooking");
                intent.putExtra(YOUR_BOOKING, true);
                startActivity(intent);

            }
        });
        view.findViewById(R.id.tvLogOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sessionManager.logoutUser();
            }
        });
    }
    @Override
    public void onPause() {
        super.onPause();
        getUserDataCall.cancel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getUserDataCall.cancel();

    }
}

