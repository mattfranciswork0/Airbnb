package com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions;

/**
 * Created by TOSHIBA on 30/07/2017.
 */


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import com.example.toshiba.airbnb.Profile.BecomeAHost.BottomSheetFragment;
import com.example.toshiba.airbnb.R;

/**
 * Created by Owner on 2017-07-02.
 */

public class PropertyTypeFragment extends Fragment {

    public static View mView;
    public static final String PROPERTY_TYPE_BOTTOM_SHEET = "PROPERTY_TYPE_BOTTOM_SHEET";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressBar basicProgressBar = (ProgressBar) getActivity().findViewById(R.id.basicProgressBar);
        basicProgressBar.setProgress(10);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_property_type, container, false);
        mView = view;
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.layoutTypeOfProperty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean(PROPERTY_TYPE_BOTTOM_SHEET, true);
                bottomSheetFragment.setArguments(bundle);
                bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        view.findViewById(R.id.layoutTypeOfProperty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean(PROPERTY_TYPE_BOTTOM_SHEET, true);
                bottomSheetFragment.setArguments(bundle);
                bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        view.findViewById(R.id.bContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.progressFragment, new GuestFragment()).addToBackStack(null).commit();
            }
        });

        view.findViewById(R.id.layoutTapInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.progressFragment, new PropertyTypeInfoFragment()).addToBackStack("registerPlaceType").commit();
            }
        });

        //Radio functionality
        final RadioButton entireRadio = (RadioButton) view.findViewById(R.id.entireRadio);
        final RadioButton privateRadio = (RadioButton) view.findViewById(R.id.privateRadio);
        final RadioButton sharedRadio = (RadioButton) view.findViewById(R.id.sharedRadio);
        entireRadio.setChecked(true);
        entireRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    privateRadio.setChecked(false);
                    sharedRadio.setChecked(false);
                }
            }
        });

        privateRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    entireRadio.setChecked(false);
                    sharedRadio.setChecked(false);
                }
            }
        });

        sharedRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    entireRadio.setChecked(false);
                    privateRadio.setChecked(false);
                }
            }
        });
    }

}
