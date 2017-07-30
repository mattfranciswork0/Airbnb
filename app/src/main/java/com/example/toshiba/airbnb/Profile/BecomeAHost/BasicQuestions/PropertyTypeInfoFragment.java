package com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions;

/**
 * Created by TOSHIBA on 30/07/2017.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toshiba.airbnb.R;


/**
 * Created by Owner on 2017-07-04.
 */

public class PropertyTypeInfoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_property_type_info, container, false);

        view.findViewById(R.id.closeIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack("registerPlaceType", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

        return view;
    }
}