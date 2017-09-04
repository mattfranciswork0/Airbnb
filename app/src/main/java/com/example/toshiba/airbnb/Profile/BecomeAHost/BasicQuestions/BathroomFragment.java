package com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toshiba.airbnb.Profile.BecomeAHost.BottomSheetFragment;
import com.example.toshiba.airbnb.R;


/**
 * Created by Owner on 2017-07-07.
 */

public class BathroomFragment extends Fragment {
    public static final String BATHROOM_BOTTOM_SHEET = "BATHROOM_BOTTOM_SHEET";
    public static final String BATHROOM_SP = "BATHROOM_SP";
    public static final String TOTAL_BATHROOM = "TOTAL_BATHROOM";
    public static final String PRIVATE_BATHROOM = "PRIVATE_BATHROOM";
    public static final String SHARED_BATHROOM = "SHARED_BATHROOM";
    public static View mView;
    SharedPreferences bathroomSP;
    SharedPreferences.Editor edit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressBar basicProgressBar = (ProgressBar) getActivity().findViewById(R.id.basicProgressBar);
        basicProgressBar.setProgress(60);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bathroom, container, false);
        bathroomSP = getActivity().getSharedPreferences(BATHROOM_SP, Context.MODE_PRIVATE);
        edit = bathroomSP.edit();
        mView = view;
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.layoutBathroom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean(BATHROOM_BOTTOM_SHEET, true);
                bottomSheetFragment.setArguments(bundle);
                bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        final RadioButton privateRadio = (RadioButton) view.findViewById(R.id.privateRadio);
        if(bathroomSP.getAll().isEmpty()) privateRadio.setChecked(true);
        final RadioButton sharedRadio = (RadioButton) view.findViewById(R.id.sharedRadio);

        final TextView tvTotalBathroomInput = (TextView) view.findViewById(R.id.tvTotalBathroomInput);
        if (bathroomSP.contains(TOTAL_BATHROOM)) tvTotalBathroomInput.setText(bathroomSP.getString(TOTAL_BATHROOM, "1 bathroom"));
        if (bathroomSP.contains(SHARED_BATHROOM)) sharedRadio.setChecked(true);
        if (bathroomSP.contains(PRIVATE_BATHROOM)) privateRadio.setChecked(true);
        tvTotalBathroomInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edit.remove(TOTAL_BATHROOM);
                edit.putString(TOTAL_BATHROOM, tvTotalBathroomInput.getText().toString());
                edit.apply();
            }
        });


        privateRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedRadio.setChecked(false);
                edit.remove(SHARED_BATHROOM);
                edit.remove(PRIVATE_BATHROOM);
                edit.putBoolean(PRIVATE_BATHROOM, true);
                edit.apply();
            }
        });

        sharedRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                privateRadio.setChecked(false);
                edit.remove(SHARED_BATHROOM);
                edit.remove(PRIVATE_BATHROOM);
                edit.putBoolean(SHARED_BATHROOM, true);
                edit.apply();
            }
        });

        view.findViewById(R.id.bNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(bathroomSP.contains(TOTAL_BATHROOM))) edit.putString(TOTAL_BATHROOM, tvTotalBathroomInput.getText().toString()).apply();
                getFragmentManager().beginTransaction().replace(R.id.progressFragment, new LocationFragment()).addToBackStack(null).commit();
            }
        });


    }
}