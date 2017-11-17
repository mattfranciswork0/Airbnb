package com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.Explore.Homes.HomeDescActivity;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.DTO.DTODescription;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.EditListingFragment;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.ViewListingAndYourBookingAdapter;
import com.example.toshiba.airbnb.Util.KeyboardUtil;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.Util.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        if (getArguments() == null) {
            ProgressBar basicProgressBar = (ProgressBar) getActivity().findViewById(R.id.basicProgressBar);
            basicProgressBar.setProgress(75);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etDescribePlace = (EditText) view.findViewById(R.id.etDescribePlace);
        bPreview = (Button) view.findViewById(R.id.bPreview);
        bNext = (Button) view.findViewById(R.id.bNext);

        final TextView tvWordCount = (TextView) view.findViewById(R.id.tvWordCount);

        //get description stored in internal storage through sharedpreferences
        describePlaceSP = getActivity().getSharedPreferences(DESCRIBE_SP, Context.MODE_PRIVATE);
        String savedEtDescribePlace = describePlaceSP.getString(DESCRIBE_PLACE_KEY, "");
        etDescribePlace.setText(savedEtDescribePlace);
        registrationProceed();


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
        if(getArguments() != null) {
            if (getArguments().containsKey(EditListingFragment.DESCRIPTION_PLACE_FRAGMENT_INFO_FROM_DATABASE)
                    && getArguments().containsKey(ViewListingAndYourBookingAdapter.LISTING_ID)) {
                final String descriptionFromDatabase = getArguments().getString(EditListingFragment.DESCRIPTION_PLACE_FRAGMENT_INFO_FROM_DATABASE);
                etDescribePlace.setText(descriptionFromDatabase);
                bPreview.setVisibility(View.GONE);
                bNext.setText(getString(R.string.save));
                bNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        KeyboardUtil.hideKeyboard(getActivity());
                        DatabaseInterface retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();
                        final ProgressDialog dialog = new ProgressDialog(getActivity());
                        dialog.setMessage("Updating data...");
                        dialog.setCancelable(false);
                        dialog.show();
                        retrofit.updateDescription(getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID),
                                new DTODescription(etDescribePlace.getText().toString())).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                dialog.dismiss();
                                Toast.makeText(getActivity(), "Updated", Toast.LENGTH_LONG).show();
                                getFragmentManager().popBackStack();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                dialog.dismiss();
                                Toast.makeText(getActivity(), getString(R.string.failedToUpdate), Toast.LENGTH_LONG);

                            }
                        });
                    }
                });
            }
        }else{
            bNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    KeyboardUtil.hideKeyboard(getActivity());
                    getFragmentManager().beginTransaction().replace(R.id.progressFragment, new TitleFragment()).addToBackStack(null).commit();
                    //Save description
                    SharedPreferences.Editor editor = describePlaceSP.edit();
                    editor.remove(DESCRIBE_PLACE_KEY);
                    editor.putString(DESCRIBE_PLACE_KEY, etDescribePlace.getText().toString());
                    editor.apply();
                }
            });

            bPreview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    KeyboardUtil.hideKeyboard(getActivity());
                    Intent intent = new Intent(getContext(), HomeDescActivity.class);
                    intent.putExtra(DESCRIBE_PREVIEW, etDescribePlace.getText().toString());
                    Bundle bundle = new Bundle();
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

    }

    public void registrationProceed() {
        if (etDescribePlace.getText().length() == 0) {
            bPreview.setEnabled(false);
            bNext.setEnabled(false);
            bPreview.setBackgroundResource(R.drawable.reg_host_proceed_button_fail);
            bNext.setBackgroundResource(R.drawable.reg_host_proceed_button_fail);


        } else {
            bPreview.setEnabled(true);
            bPreview.setBackgroundResource(R.drawable.reg_host_proceed_button);
            bNext.setEnabled(true);
            bNext.setBackgroundResource(R.drawable.reg_host_proceed_button);

        }
    }
}
