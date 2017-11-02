package com.example.toshiba.airbnb.Profile.BecomeAHost.GetReady;

import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.EditListingDTO.HouseRulesDTO;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.EditListingDTO.PriceDTO;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListingFragment;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.ViewListingAndYourBookingAdapter;
import com.example.toshiba.airbnb.Util.KeyboardUtil;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.Util.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by TOSHIBA on 11/08/2017.
 */

public class PriceFragment extends Fragment {
    SharedPreferences priceSP;
    SharedPreferences.Editor edit;
    public static String PRICE_SP = "PRICE_SP";
    public static String PRICE = "PRICE";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_price, container, false);
        priceSP = getActivity().getSharedPreferences(PRICE_SP, Context.MODE_PRIVATE);
        edit = priceSP.edit();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) {
            ProgressBar basicProgressBar = (ProgressBar) getActivity().findViewById(R.id.basicProgressBar);
            basicProgressBar.setBackgroundColor(0);
            basicProgressBar.setProgress(100);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText etPricePerNight = (EditText) view.findViewById(R.id.etPricePerNight);
        final Button bPublish = (Button) view.findViewById(R.id.bPublish);

        TextWatcher textWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (etPricePerNight.getText().length() == 0) {
                    bPublish.setBackgroundResource(R.drawable.reg_host_proceed_button_fail);
                } else {
                    bPublish.setBackgroundResource(R.drawable.reg_host_proceed_button);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        };
        etPricePerNight.addTextChangedListener(textWatcher);

        bPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.hideKeyboard(getActivity());
                edit.putString(PRICE, etPricePerNight.getText().toString());
                edit.apply();
                getFragmentManager().beginTransaction()
                        .replace(R.id.progressFragment, new PublishFragment()).addToBackStack(null).commit();
            }
        });

        //load from database
        if (getArguments() != null) {
            if (getArguments().containsKey(EditListingFragment.PRICE_FRAGMENT_INFO_FROM_DATABASE)
                    && getArguments().containsKey(ViewListingAndYourBookingAdapter.LISTING_ID)) {
                etPricePerNight.setText(getArguments().getString(EditListingFragment.PRICE_FRAGMENT_INFO_FROM_DATABASE));
                bPublish.setText(getString(R.string.save));
                bPublish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        KeyboardUtil.hideKeyboard(getActivity());
                        DatabaseInterface retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();
                        final ProgressDialog dialog = new ProgressDialog(getActivity());
                        dialog.setMessage("Updating data...");
                        dialog.setCancelable(false);
                        dialog.show();
                        retrofit.updatePrice(getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID),
                                new PriceDTO(
                                        etPricePerNight.getText().toString())
                        ).enqueue(new Callback<Void>() {
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
                etPricePerNight.setText(getArguments().getString(EditListingFragment.PRICE_FRAGMENT_INFO_FROM_DATABASE));
            }
        } else {
            //load from shared preferences

            bPublish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    KeyboardUtil.hideKeyboard(getActivity());
                    edit.putString(PRICE, etPricePerNight.getText().toString());
                    edit.apply();
                    getFragmentManager().beginTransaction()
                            .replace(R.id.progressFragment, new PublishFragment()).addToBackStack(null).commit();
                }
            });
            etPricePerNight.setText(priceSP.getString(PRICE, ""));
        }
    }
}
