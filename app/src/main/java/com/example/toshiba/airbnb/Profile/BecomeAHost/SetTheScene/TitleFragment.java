package com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
 * Created by TOSHIBA on 07/08/2017.
 */

public class TitleFragment extends Fragment {
    public static final String TITLE_SP = "TITLE_SP";
    public static final String TITLE_KEY = "TITLE_KEY";
    public static final String TITLE_PREVIEW = "TITLE_PREVIEW";
    private SharedPreferences titleSP;
    private SharedPreferences.Editor editor;
    Button bPreview;
    Button bNext;
    EditText etTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_title, container, false);
        //OnCreate does not get called if user backs out from PhoneNumFragment
        ProgressBar basicProgressBar = (ProgressBar) getActivity().findViewById(R.id.basicProgressBar);
        basicProgressBar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.background_holo_light));
        basicProgressBar.setProgress(100);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etTitle = (EditText) view.findViewById(R.id.etTitle);
        final TextView tvWordCount = (TextView) view.findViewById(R.id.tvWordCount);
        //get title stored in internal storage thorugh sharedpreferences
        titleSP = getActivity().getSharedPreferences(TITLE_SP, Context.MODE_PRIVATE);
        String savedEtTitle = titleSP.getString(TITLE_KEY, "");
        if (savedEtTitle.equals("")) {
            etTitle.requestFocus();
        }
        etTitle.setText(savedEtTitle);

        bPreview = (Button) view.findViewById(R.id.bPreview);
        bNext = (Button) view.findViewById(R.id.bNext);

        bPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HomeDescActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(TITLE_PREVIEW, etTitle.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.remove(TITLE_KEY);
                editor.putString(TITLE_KEY, etTitle.getText().toString());
                editor.apply();
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {
                tvWordCount.setText(String.valueOf(50 - etTitle.getText().length()));
                registrationProceed();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        };
        etTitle.addTextChangedListener(textWatcher);

    }

    public void registrationProceed() {
        if (etTitle.getText().length() > 0) {
            bPreview.setEnabled(false);
            bNext.setEnabled(false);
            bPreview.setBackgroundResource(R.drawable.reg_host_proceed_button_fail);
            bNext.setBackgroundResource(R.drawable.reg_host_proceed_button_fail);;


        } else {
            bPreview.setEnabled(true);
            bPreview.setBackgroundResource(R.drawable.reg_host_proceed_button);
            bNext.setEnabled(true);
            bNext.setBackgroundResource(R.drawable.reg_host_proceed_button);
        }
    }
}
