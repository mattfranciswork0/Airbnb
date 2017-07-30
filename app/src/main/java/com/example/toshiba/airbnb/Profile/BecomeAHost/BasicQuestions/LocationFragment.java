package com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.toshiba.airbnb.R;


/**
 * Created by Owner on 2017-07-07.
 */

public class LocationFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.layoutStreet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.progressFragment, new LocationFilterFragment()).addToBackStack("locationFragment").commit();
            }
        });

        view.findViewById(R.id.bContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.progressFragment, new MapFragment()).addToBackStack(null).commit();
            }
        });
        view.findViewById(R.id.layoutTapInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.progressFragment, new LocationInfoFragment()).addToBackStack("locationFragment").commit();
            }
        });
    }


}
