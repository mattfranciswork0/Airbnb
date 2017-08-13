package com.example.toshiba.airbnb.Profile.BecomeAHost.GetReady;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.toshiba.airbnb.Keyboard;
import com.example.toshiba.airbnb.R;

/**
 * Created by TOSHIBA on 11/08/2017.
 */

public class PriceFragment extends Fragment {
    SharedPreferences priceSP;
    SharedPreferences.Editor edit;
    public static String PRICE_SP = "PRICE_SP";
    public static String PRICE = "PRICE";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressBar basicProgressBar = (ProgressBar) getActivity().findViewById(R.id.basicProgressBar);
        basicProgressBar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.background_holo_light));
        basicProgressBar.setProgress(100);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_price, container, false);
        priceSP = getActivity().getSharedPreferences(PRICE_SP, Context.MODE_PRIVATE);
        edit = priceSP.edit();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText etPricePerNight = (EditText) view.findViewById(R.id.etPricePerNight);
        etPricePerNight.setText(priceSP.getString(PRICE, ""));

        view.findViewById(R.id.bPublish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Keyboard.hideKeyboard(getActivity());
                edit.putString(PRICE, etPricePerNight.getText().toString());
                edit.apply();
                getFragmentManager().beginTransaction()
                        .replace(R.id.progressFragment, new PublishFragment()).addToBackStack(null).commit();
            }
        });
    }
}
