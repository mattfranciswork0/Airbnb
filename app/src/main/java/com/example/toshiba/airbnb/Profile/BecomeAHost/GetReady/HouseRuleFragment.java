package com.example.toshiba.airbnb.Profile.BecomeAHost.GetReady;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import com.example.toshiba.airbnb.Util.KeyboardUtil;
import com.example.toshiba.airbnb.R;

/**
 * Created by TOSHIBA on 09/08/2017.
 */

public class HouseRuleFragment extends Fragment {
    public static final String HOUSE_RULE_SP = "HOUSE_RULE_SP";
    public static final String ADDITIONAL_RULES = "ADDITIONAL_RULES";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    EditText etAdditionalRules;

    private boolean rbChildrenCanUncheck = false;
    private boolean rbInfantsCanUncheck = false;
    private boolean rbPetsCanUncheck = false;
    private boolean rbSmokingCanUncheck = false;
    private boolean rbPartiesCanUncheck = false;

    public boolean radioButtonFunctionality(RadioButton radioButton, boolean radioCanUnCheck) {
        if (radioButton instanceof RadioButton) {
            String tag = (String) radioButton.getTag();
            if (radioButton.isChecked() && !radioCanUnCheck) {
                editor.remove(tag);
                editor.putBoolean(tag, true);
                editor.apply();
                radioCanUnCheck = true;
            } else if (radioCanUnCheck) {
                editor.remove(tag);
                editor.apply();
                radioButton.setChecked(false);
                radioCanUnCheck = false;
            }
        }
        return radioCanUnCheck;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressBar basicProgressBar = (ProgressBar) getActivity().findViewById(R.id.basicProgressBar);
        basicProgressBar.setProgress(25);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_house_rule, container, false);
        sharedPreferences = getActivity().getSharedPreferences(HOUSE_RULE_SP, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.bNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.hideKeyboard(getActivity());
                editor.putString(ADDITIONAL_RULES, etAdditionalRules.getText().toString());
                editor.apply();
                getFragmentManager().beginTransaction()
                        .replace(R.id.progressFragment, new HowGuestBookFragment()).addToBackStack(null).commit();
            }
        });
        final RadioButton rbChildren = (RadioButton) view.findViewById(R.id.rbChildren);
        final RadioButton rbInfants = (RadioButton) view.findViewById(R.id.rbInfants);
        final RadioButton rbPets = (RadioButton) view.findViewById(R.id.rbPets);
        final RadioButton rbSmoking = (RadioButton) view.findViewById(R.id.rbSmoking);
        final RadioButton rbParties = (RadioButton) view.findViewById(R.id.rbParties);
        etAdditionalRules= (EditText) view.findViewById(R.id.etAdditionalRules);
        etAdditionalRules.setText(sharedPreferences.getString(ADDITIONAL_RULES, ""));
        //Load saved amenities in shared preferences
        if (sharedPreferences.contains(rbChildren.getTag().toString())) {
            rbChildren.setChecked(true);
            rbChildrenCanUncheck = true;
        }
        if (sharedPreferences.contains(rbInfants.getTag().toString())) {
            rbInfants.setChecked(true);
            rbInfantsCanUncheck = true;
        }
        if (sharedPreferences.contains(rbPets.getTag().toString())) {
            rbPets.setChecked(true);
            rbPetsCanUncheck = true;
        }
        if (sharedPreferences.contains(rbSmoking.getTag().toString())) {
            rbSmoking.setChecked(true);
            rbSmokingCanUncheck = true;
        }
        if (sharedPreferences.contains(rbParties.getTag().toString())) {
            rbParties.setChecked(true);
            rbPartiesCanUncheck = true;
        }
        //Handle click
        rbChildren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbChildrenCanUncheck = radioButtonFunctionality(rbChildren, rbChildrenCanUncheck);
            }
        });

        rbInfants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbInfantsCanUncheck = radioButtonFunctionality(rbInfants, rbInfantsCanUncheck);
            }
        });

        rbPets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbPetsCanUncheck = radioButtonFunctionality(rbPets, rbPetsCanUncheck);
            }
        });

        rbSmoking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbSmokingCanUncheck = radioButtonFunctionality(rbSmoking, rbSmokingCanUncheck);
            }
        });

        rbParties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbPartiesCanUncheck = radioButtonFunctionality(rbParties, rbPartiesCanUncheck);
            }
        });


    }
}
