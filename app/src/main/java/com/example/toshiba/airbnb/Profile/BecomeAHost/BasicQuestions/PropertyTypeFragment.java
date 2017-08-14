package com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions;

/**
 * Created by TOSHIBA on 30/07/2017.
 */


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toshiba.airbnb.Profile.BecomeAHost.BottomSheetFragment;
import com.example.toshiba.airbnb.R;

/**
 * Created by Owner on 2017-07-02.
 */

public class PropertyTypeFragment extends Fragment {

    public static View mView;
    public static final String PROPERTY_TYPE_BOTTOM_SHEET = "PROPERTY_TYPE_BOTTOM_SHEET";
    public static final String PROPERTY_TYPE_SP = "PROPERTY_TYPE_SP";
    SharedPreferences propertyTypeSP;
    SharedPreferences.Editor edit;
    public static final String ENTIRE_RADIO = "ENITRE_RADIO";
    public static final String PRIVATE_RADIO = "PRIVATE_RADIO";
    public static final String SHARED_RADIO = "SHARED_RADIO";
    public static  final String PROPERTY_TYPE = "PROPERTY_TYPE";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressBar basicProgressBar = (ProgressBar) getActivity().findViewById(R.id.basicProgressBar);
        basicProgressBar.setProgress(20);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_property_type, container, false);
        propertyTypeSP = getActivity().getSharedPreferences(PROPERTY_TYPE_SP, Context.MODE_PRIVATE);
        edit = propertyTypeSP.edit();
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

        view.findViewById(R.id.bNext).setOnClickListener(new View.OnClickListener() {
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
        if(propertyTypeSP.getAll().isEmpty()) {
            entireRadio.setChecked(true);
        } else{
            if(propertyTypeSP.contains(ENTIRE_RADIO))  entireRadio.setChecked(true);
            if(propertyTypeSP.contains(PRIVATE_RADIO)) privateRadio.setChecked(true);
            if(propertyTypeSP.contains(SHARED_RADIO)) sharedRadio.setChecked(true);
        }
        entireRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    privateRadio.setChecked(false);
                    sharedRadio.setChecked(false);
                    edit.putBoolean(ENTIRE_RADIO, true);
                    if(propertyTypeSP.contains(PRIVATE_RADIO)) edit.remove(PRIVATE_RADIO);
                    if(propertyTypeSP.contains(SHARED_RADIO)) edit.remove(SHARED_RADIO);
                    edit.apply();
                }
            }
        });

        privateRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    entireRadio.setChecked(false);
                    sharedRadio.setChecked(false);
                    edit.putBoolean(PRIVATE_RADIO, true);
                    if(propertyTypeSP.contains(ENTIRE_RADIO)) edit.remove(ENTIRE_RADIO);
                    if(propertyTypeSP.contains(SHARED_RADIO)) edit.remove(SHARED_RADIO);
                    edit.apply();
                }
            }
        });

        sharedRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    entireRadio.setChecked(false);
                    privateRadio.setChecked(false);
                    edit.putBoolean(SHARED_RADIO, true);
                    if(propertyTypeSP.contains(ENTIRE_RADIO)) edit.remove(ENTIRE_RADIO);
                    if(propertyTypeSP.contains(PRIVATE_RADIO)) edit.remove(PRIVATE_RADIO);
                    edit.apply();
                }
            }
        });

        final TextView tvTypeInput = (TextView) view.findViewById(R.id.tvTypeInput);
        if(propertyTypeSP.contains(PROPERTY_TYPE)) tvTypeInput.setText(propertyTypeSP.getString(PROPERTY_TYPE, ""));

        tvTypeInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                if(propertyTypeSP.contains(PROPERTY_TYPE)) edit.remove(PROPERTY_TYPE);
                edit.remove(PROPERTY_TYPE);
                edit.putString(PROPERTY_TYPE, tvTypeInput.getText().toString());
                edit.apply();
            }
        });
    }

}
