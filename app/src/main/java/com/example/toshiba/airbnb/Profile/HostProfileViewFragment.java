package com.example.toshiba.airbnb.Profile;

/**
 * Created by TOSHIBA on 30/07/2017.
 */

import android.content.Context;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cloudinary.Cloudinary;
import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HostProfileViewFragment extends Fragment {
    DatabaseInterface retrofit;
    int userId;
    Call<POJOUserData> getUserDataCall;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.2.89:3000/")
                .baseUrl("http://192.168.0.34:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(DatabaseInterface.class);
        userId = getActivity().getSharedPreferences(SessionManager.SESSION_SP, Context.MODE_PRIVATE)
                .getInt(SessionManager.USER_ID, 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_host_profile_view, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SessionManager sessionManager = new SessionManager(getActivity());
        if (sessionManager.isLoggedIn()) {
            SharedPreferences sessionSP = getActivity().getSharedPreferences(SessionManager.SESSION_SP, Context.MODE_PRIVATE);
            TextView tvName = (TextView) view.findViewById(R.id.tvName);
            tvName.setText(sessionSP.getString(sessionManager.FIRST_NAME, "") + " " + sessionSP.getString(sessionManager.LAST_NAME, ""));
        }
        ImageView ivEdit = (ImageView) view.findViewById(R.id.ivEdit);
        Glide.with(getActivity()).load(getResources().getString(R.string.PencilIcon)).into(ivEdit);
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.hostProfileLayout, new HostProfileEditFragment()).addToBackStack(null).commit();
            }
        });

        final ImageView ivProfilePic = (ImageView) view.findViewById(R.id.ivProfilePic);
        final Cloudinary cloudinary = new Cloudinary(getActivity().getResources().getString(R.string.cloudinaryEnviornmentVariable)); //configured using an environment variable

        getUserDataCall = retrofit.getUserData(userId);

        getUserDataCall.enqueue(new Callback<POJOUserData>() {
            @Override
            public void onResponse(Call<POJOUserData> call, Response<POJOUserData> response) {
                Log.d("HostProfileView", "HostProfileView pic");
                Glide.with(getActivity()).
                        load(cloudinary.url().generate(response.body().getProfileImagePath().
                                toString())).into(ivProfilePic);
            }

            @Override
            public void onFailure(Call<POJOUserData> call, Throwable t) {
                Log.d("HostProfileView", t.toString());
                Toast.makeText(getActivity(), "Failed to get profile picture, try again", Toast.LENGTH_LONG).show();
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