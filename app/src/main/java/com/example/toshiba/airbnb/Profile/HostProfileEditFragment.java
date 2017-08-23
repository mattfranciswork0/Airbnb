package com.example.toshiba.airbnb.Profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.SessionManager;

/**
 * Created by TOSHIBA on 14/08/2017.
 */

public class HostProfileEditFragment extends Fragment {
    SessionManager sessionManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_host_profile_edit, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManager = new SessionManager(getActivity());
        if(sessionManager.isLoggedIn()){
            SharedPreferences sessionSP = getActivity().getSharedPreferences(SessionManager.SESSION_SP, Context.MODE_PRIVATE);
            TextView tvName = (TextView) view.findViewById(R.id.tvName);
            tvName.setText(sessionSP.getString(sessionManager.FIRST_NAME, "") + " " + sessionSP.getString(sessionManager.LAST_NAME, ""));
            TextView tvEmail = (TextView) view.findViewById(R.id.tvEmail);
            tvEmail.setText(sessionSP.getString(sessionManager.EMAIL, ""));
            TextView tvPhoneNum = (TextView) view.findViewById(R.id.tvPhoneNum);
            tvPhoneNum.setText(sessionSP.getString(sessionManager.PHONE_NUM, ""));
        }
        ImageView ivGallery = (ImageView) view.findViewById(R.id.ivGallery);
        Glide.with(getActivity()).load(getResources().getString(R.string.GalleryIcon)).into(ivGallery);
    }
}
