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
import com.example.toshiba.airbnb.Explore.HomeDescFragment;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.Util.RetrofitUtil;
import com.example.toshiba.airbnb.UserAuthentication.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HostProfileViewFragment extends Fragment {
    DatabaseInterface retrofit;
    int userId;
    Call<POJOUserData> getUserDataCall;

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
        View view = inflater.inflate(R.layout.fragment_host_profile_view, container, false);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SessionManager sessionManager = new SessionManager(getActivity());
        if (sessionManager.isLoggedIn()) {
            SharedPreferences sessionSP = getActivity().getSharedPreferences(SessionManager.SESSION_SP, Context.MODE_PRIVATE);
            TextView tvName = (TextView) view.findViewById(R.id.tvName);
            tvName.setText(sessionSP.getString(sessionManager.FIRST_NAME, "") + " " + sessionSP.getString(sessionManager.LAST_NAME, ""));
        }
        ImageView ivEdit = (ImageView) view.findViewById(R.id.ivEdit);
        Glide.with(getActivity()).load("").placeholder(R.drawable.pencil).into(ivEdit);
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.hostProfileLayout, new HostProfileEditFragment()).addToBackStack(null).commit();
            }
        });

        final ImageView ivProfilePic = (ImageView) view.findViewById(R.id.ivProfilePic);
        final Cloudinary cloudinary = new Cloudinary(getActivity().getResources().getString(R.string.cloudinaryEnviornmentVariable)); //configured using an environment variable

        if(getArguments() != null){
            if(getArguments().containsKey(HomeDescFragment.HOST_ID)){
                getUserDataCall = retrofit.getUserData(getArguments().getInt(HomeDescFragment.HOST_ID));
                ivEdit.setVisibility(View.GONE);
            }
        }else {
            getUserDataCall = retrofit.getUserData(userId);
        }

        getUserDataCall.enqueue(new Callback<POJOUserData>() {
            @Override
            public void onResponse(Call<POJOUserData> call, Response<POJOUserData> response) {
                Log.d("HostProfileView", "HostProfileView pic");
                if (response.body().getProfileImagePath() != null) {
                    TextView tvName = (TextView) view.findViewById(R.id.tvName);
                    tvName.setText(response.body().getFirstName() + " " + response.body().getLastName());

                    TextView tvLocation = (TextView) view.findViewById(R.id.tvLocation);
                    tvLocation.setText(response.body().getLocation());


                    if (response.body().getAboutMe() != null) {
                        view.findViewById(R.id.layoutAboutMe).setVisibility(View.GONE);
                        TextView tvAboutMe = (TextView) view.findViewById(R.id.tvAboutMe);
                        tvAboutMe.setText(response.body().getAboutMe());
                    }
                    if (response.body().getWork() != null) {
                        view.findViewById(R.id.layoutWork).setVisibility(View.VISIBLE);
                        TextView tvWorkDesc = (TextView) view.findViewById(R.id.tvWorkDesc);
                        tvWorkDesc.setText(response.body().getWork());
                    }


                    if (response.body().getLanguages() != null) {
                        view.findViewById(R.id.layoutLanguage).setVisibility(View.VISIBLE);
                        TextView tvLanguagesDesc = (TextView) view.findViewById(R.id.tvLanguagseDesc);
                        tvLanguagesDesc.setText(response.body().getLanguages());
                    }


                    Glide.with(getActivity()).
                            load(cloudinary.url().generate(response.body().getProfileImagePath())).into(ivProfilePic);
                }
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