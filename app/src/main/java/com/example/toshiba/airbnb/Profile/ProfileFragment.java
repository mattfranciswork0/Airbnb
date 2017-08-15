package com.example.toshiba.airbnb.Profile;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BecomeAHostActivity;
import com.example.toshiba.airbnb.R;


/**
 * Created by Owner on 2017-07-02.
 */

public class ProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container , false);
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

        ImageView ivHostProfilePic = (ImageView) view.findViewById(R.id.ivHostProfilePic);
        Glide.with(getActivity()).load("https://cdn.pixabay.com/photo/2014/03/04/12/55/people-279457_960_720.jpg").into(ivHostProfilePic);

        return view;
    }
}

