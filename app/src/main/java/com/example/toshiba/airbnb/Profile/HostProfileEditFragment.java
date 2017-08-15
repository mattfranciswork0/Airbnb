package com.example.toshiba.airbnb.Profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.toshiba.airbnb.R;

/**
 * Created by TOSHIBA on 14/08/2017.
 */

public class HostProfileEditFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_host_profile_edit, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView ivGallery = (ImageView) view.findViewById(R.id.ivGallery);
        Glide.with(getActivity()).load(getResources().getString(R.string.GalleryIcon)).into(ivGallery);
    }
}
