package com.example.toshiba.airbnb.Explore;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toshiba.airbnb.Profile.BecomeAHost.GetReady.HouseRuleFragment;
import com.example.toshiba.airbnb.R;

/**
 * Created by TOSHIBA on 09/08/2017.
 */

public class HouseRuleMoreFragment extends Fragment {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_house_rule_more, container, false);
        sharedPreferences = getActivity().getSharedPreferences(HouseRuleFragment.HOUSE_RULE_SP, Context.MODE_PRIVATE);
        edit = sharedPreferences.edit();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.closeIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        if(!(sharedPreferences.contains(getString(R.string.rbChildren)))){
            view.findViewById(R.id.tvSuitableForChildren).setVisibility(View.VISIBLE);
        }
        if(!(sharedPreferences.contains(getString(R.string.rbInfants)))){
            view.findViewById(R.id.tvSuitableForInfants).setVisibility(View.VISIBLE);
        }
        if(!(sharedPreferences.contains(getString(R.string.rbPets)))){
            view.findViewById(R.id.tvSuitableForPets).setVisibility(View.VISIBLE);
        }
        if(!(sharedPreferences.contains(getString(R.string.rbSmoking)))){
            view.findViewById(R.id.tvSmoking).setVisibility(View.VISIBLE);
        }
        if(!(sharedPreferences.contains(getString(R.string.rbParties)))){
            view.findViewById(R.id.tvParties).setVisibility(View.VISIBLE);
        }

    }
}
