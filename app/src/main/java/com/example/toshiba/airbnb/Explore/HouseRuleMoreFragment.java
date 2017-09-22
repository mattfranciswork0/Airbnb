package com.example.toshiba.airbnb.Explore;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.Profile.BecomeAHost.GetReady.HouseRuleFragment;
import com.example.toshiba.airbnb.Profile.ViewListing.ViewListingAdapter;
import com.example.toshiba.airbnb.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by TOSHIBA on 09/08/2017.
 */

public class HouseRuleMoreFragment extends Fragment {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;
    DatabaseInterface retrofit;

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

        final TextView tvSuitableForChildren = (TextView) view.findViewById(R.id.tvSuitableForChildren);
        final TextView tvSuitableForInfants = (TextView) view.findViewById(R.id.tvSuitableForInfants);
        final TextView tvSuitableForPets = (TextView) view.findViewById(R.id.tvSuitableForPets);
        final TextView tvSmoking = (TextView) view.findViewById(R.id.tvSmoking);
        TextView tvParties = (TextView) view.findViewById(R.id.tvParties);

        final EditText etAdditionalRules = (EditText) view.findViewById(R.id.etAdditionalRules);

        if (getArguments().containsKey(ViewListingAdapter.LISTING_ID)) {
            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Getting data...");
            dialog.show();
            retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.2.89:3000/")
                    .baseUrl("http://192.168.0.34:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(DatabaseInterface.class);
            retrofit.getListingData(getArguments().getInt(ViewListingAdapter.LISTING_ID)).enqueue(new Callback<POJOListingData>() {
                @Override
                public void onResponse(Call<POJOListingData> call, Response<POJOListingData> response) {
                    POJOListingData body = response.body();
                    //1 == true
                    //2 == false
                    if(body.getSuitableForChildren() == 1){
                        tvSuitableForChildren.setVisibility(View.VISIBLE);
                    }
                    if(body.getSuitableForInfants() == 1){
                        tvSuitableForInfants.setVisibility(View.VISIBLE);
                    }
                    if(body.getSuitableForPets() == 1){
                        tvSuitableForPets.setVisibility(View.VISIBLE);
                    }
                    if(body.getSmokingAllowed() == 1){
                        tvSmoking.setVisibility(View.VISIBLE);
                    }
                    if(body.getPartiesAllowed() == 1){
                        tvSmoking.setVisibility(View.VISIBLE);
                    }

                    etAdditionalRules.setText(body.getAdditionalRules());

                    dialog.dismiss();

                }

                @Override
                public void onFailure(Call<POJOListingData> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(getActivity(),
                            "Data retrieval failed, make sure your internet connection is stable, try again",Toast.LENGTH_LONG).show();
                    getActivity().onBackPressed();
                }
            });
        } else {
            if (!(sharedPreferences.contains(getString(R.string.rbChildren)))) {
                tvSuitableForChildren.setVisibility(View.VISIBLE);
            }
            if (!(sharedPreferences.contains(getString(R.string.rbInfants)))) {
                tvSuitableForInfants.setVisibility(View.VISIBLE);
            }
            if (!(sharedPreferences.contains(getString(R.string.rbPets)))) {
                tvSuitableForPets.setVisibility(View.VISIBLE);
            }
            if (!(sharedPreferences.contains(getString(R.string.rbSmoking)))) {
                tvSmoking.setVisibility(View.VISIBLE);
            }
            if (!(sharedPreferences.contains(getString(R.string.rbParties)))) {
                tvParties.setVisibility(View.VISIBLE);
            }
            if(sharedPreferences.contains(getResources().getString(R.string.additionalRules))){
                etAdditionalRules.setText(sharedPreferences.getString(HouseRuleFragment.ADDITIONAL_RULES, ""));
            }
        }

    }
}
