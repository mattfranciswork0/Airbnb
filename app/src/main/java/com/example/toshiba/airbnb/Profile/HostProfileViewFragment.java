package com.example.toshiba.airbnb.Profile;

/**
 * Created by TOSHIBA on 30/07/2017.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.toshiba.airbnb.R;

public class HostProfileViewFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_host_profile_view, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView ivEdit = (ImageView) view.findViewById(R.id.ivEdit);
        Glide.with(getActivity()).load(getResources().getString(R.string.PencilIcon)).into(ivEdit);
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.hostProfileLayout, new HostProfileEditFragment()).addToBackStack(null).commit();
            }
        });
    }
}