package com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions;

/**
 * Created by TOSHIBA on 30/07/2017.
 */


import android.app.ProgressDialog;
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
import android.view.textservice.TextInfo;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.Explore.POJOListingData;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BottomSheetFragment;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListingFragment;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.ViewListingAndYourBookingAdapter;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.Util.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Owner on 2017-07-06.
 */

public class GuestFragment extends Fragment {
    public static final String TOTAL_GUEST_BOTTOM_SHEET = "TOTAL_GUEST_BOTTOM_SHEET";
    public static final String BED_ROOM_BOTTOM_SHEET = "BED_ROOM_BOTTOM_SHEET";
    public static final String BED_BOTTOM_SHEET = "BED_BOTTOM_SHEET";
    public static final String GUEST_SP = "GUEST_SP";

    public static final String TOTAL_GUEST = "TOTAL_GUEST";
    public static final String TOTAL_BED_ROOM = "TOTAL_BED_ROOM";
    public static final String TOTAL_BED = "TOTAL_BED";

    SharedPreferences guestSP;
    SharedPreferences.Editor edit;
    public static View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() == null) {
            ProgressBar basicProgressBar = (ProgressBar) getActivity().findViewById(R.id.basicProgressBar);
            basicProgressBar.setProgress(40);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guest, container, false);
        guestSP = getActivity().getSharedPreferences(GUEST_SP, Context.MODE_PRIVATE);
        edit = guestSP.edit();
        mView = view;
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextView tvTotalGuestInput = (TextView) view.findViewById(R.id.tvTotalGuestInput);
        final TextView tvTotalBedRoomInput = (TextView) view.findViewById(R.id.tvTotalBedRoomInput);
        final TextView tvTotalBedInput = (TextView) view.findViewById(R.id.tvTotalBedInput);
        Button bNext = (Button) view.findViewById(R.id.bNext);


        if(guestSP.contains(TOTAL_GUEST)) tvTotalGuestInput.setText(guestSP.getString(TOTAL_GUEST, "1 guest"));
        if(guestSP.contains(TOTAL_BED_ROOM)) tvTotalBedRoomInput.setText(guestSP.getString(TOTAL_BED_ROOM, "1 bed"));
        if(guestSP.contains(TOTAL_BED)) tvTotalBedInput.setText(guestSP.getString(TOTAL_BED, "1 bedroom"));


        this.mView = view;
        view.findViewById(R.id.layoutTotalGuest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean(TOTAL_GUEST_BOTTOM_SHEET, true);
                bottomSheetFragment.setArguments(bundle);
                bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        view.findViewById(R.id.layoutBedroom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean(BED_ROOM_BOTTOM_SHEET, true);
                bottomSheetFragment.setArguments(bundle);
                bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        view.findViewById(R.id.layoutbed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean(BED_BOTTOM_SHEET, true);
                bottomSheetFragment.setArguments(bundle);
                bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        tvTotalGuestInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                if(propertyTypeSP.contains(PROPERTY_TYPE)) edit.remove(PROPERTY_TYPE);
                edit.remove(TOTAL_GUEST);
                edit.putString(TOTAL_GUEST, tvTotalGuestInput.getText().toString());
                edit.apply();
            }
        });

        tvTotalBedRoomInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                if(propertyTypeSP.contains(PROPERTY_TYPE)) edit.remove(PROPERTY_TYPE);
                edit.remove(TOTAL_BED_ROOM);
                edit.putString(TOTAL_BED_ROOM, tvTotalBedRoomInput.getText().toString());
                edit.apply();
            }
        });

        tvTotalBedInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                if(propertyTypeSP.contains(PROPERTY_TYPE)) edit.remove(PROPERTY_TYPE);
                edit.remove(TOTAL_BED);
                edit.putString(TOTAL_BED, tvTotalBedInput.getText().toString());
                edit.apply();
            }
        });

        if(getArguments().containsKey(ViewListingAndYourBookingAdapter.LISTING_ID)){
            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.dismiss();
            DatabaseInterface retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();
            retrofit.getListingData(getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID)).enqueue(new Callback<POJOListingData>() {
                @Override
                public void onResponse(Call<POJOListingData> call, Response<POJOListingData> response) {
                    POJOListingData body = response.body();
                    tvTotalGuestInput.setText(body.getTotalGuest());
                    tvTotalBedRoomInput.setText(body.getTotalBedrooms());
                    tvTotalBedInput.setText(body.getTotalBeds());
                }

                @Override
                public void onFailure(Call<POJOListingData> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_LONG).show();
                    getActivity().onBackPressed();

                }
            });

            bNext.setText(getString(R.string.save));
            bNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } else {
            bNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!(guestSP.contains(TOTAL_GUEST)))
                        edit.putString(TOTAL_GUEST, tvTotalGuestInput.getText().toString()).apply();
                    if (!(guestSP.contains(TOTAL_BED_ROOM)))
                        edit.putString(TOTAL_BED_ROOM, tvTotalBedRoomInput.getText().toString()).apply();
                    if (!(guestSP.contains(TOTAL_BED)))
                        edit.putString(TOTAL_BED, tvTotalBedInput.getText().toString()).apply();
                    getFragmentManager().beginTransaction().replace(R.id.progressFragment, new BathroomFragment()).addToBackStack(null).commit();
                }
            });
        }

    }

}
