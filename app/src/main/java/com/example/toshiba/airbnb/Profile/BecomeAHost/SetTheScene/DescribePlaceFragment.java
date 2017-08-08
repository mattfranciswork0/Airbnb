package com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.toshiba.airbnb.Explore.HomeDescActivity;
import com.example.toshiba.airbnb.R;

/**
 * Created by Owner on 2017-07-19.
 */


public class DescribePlaceFragment extends Fragment {
    public static final String DESCRIBE_SP = "DESCRIBE_SP";
    public static final String DESCRIBE_PREVIEW = "DESCRIBE_PREVIEW";
    public static final String DESCRIBE_PLACE_KEY = "DESCRIBE_PLACE_KEY";
    private SharedPreferences describePlaceSP;
    Button bPreview;
    Button bNext;
    EditText etDescribePlace;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_describe_place, container, false);
        ProgressBar basicProgressBar = (ProgressBar) getActivity().findViewById(R.id.basicProgressBar);
        basicProgressBar.setProgress(51);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etDescribePlace = (EditText) view.findViewById(R.id.etDescribePlace);
        final TextView tvWordCount = (TextView) view.findViewById(R.id.tvWordCount);

        //get description stored in internal storage through sharedpreferences
        describePlaceSP = getActivity().getSharedPreferences(DESCRIBE_SP, Context.MODE_PRIVATE);
        String savedEtDescribePlace = describePlaceSP.getString(DESCRIBE_PLACE_KEY, "");
        etDescribePlace.setText(savedEtDescribePlace);

        TextWatcher textWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {
                tvWordCount.setText(String.valueOf(500 - etDescribePlace.getText().length()));
                registrationProceed();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        };
        etDescribePlace.addTextChangedListener(textWatcher);
        bPreview = (Button) view.findViewById(R.id.bPreview);
        bNext = (Button) view.findViewById(R.id.bNext);

    }

    public void registrationProceed() {
        if (etDescribePlace.getText().length() == 0) {
            bPreview.setEnabled(false);
            bNext.setEnabled(false);
            bPreview.setBackgroundResource(R.drawable.reg_host_proceed_fail);
            bNext.setBackgroundResource(R.drawable.reg_host_proceed_fail);


        } else {
            bPreview.setEnabled(true);
            bPreview.setBackgroundResource(R.drawable.reg_host_proceed);
            bPreview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), HomeDescActivity.class);
                    intent.putExtra(DESCRIBE_PREVIEW, etDescribePlace.getText().toString());
                    Bundle bundle = new Bundle();
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            bNext.setEnabled(true);
            bNext.setBackgroundResource(R.drawable.reg_host_proceed);
            bNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction().replace(R.id.progressFragment, new TitleFragment()).addToBackStack(null).commit();
                    //Save description
                    SharedPreferences.Editor editor = describePlaceSP.edit();
                    editor.remove(DESCRIBE_PLACE_KEY);
                    editor.putString(DESCRIBE_PLACE_KEY, etDescribePlace.getText().toString());
                    editor.apply();
                }
            });
        }
    }
}
