package com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toshiba.airbnb.Util.KeyboardUtil;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.POJOMap.GMapsAutoComplete.POJOPrediction;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.POJOMap.GMapsAutoComplete.POJOPredictions;
import com.example.toshiba.airbnb.R;


/**
 * Created by Owner on 2017-07-07.
 */

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Owner on 2017-07-07.
 */

public class LocationFilterFragment extends Fragment {
    EditText etStreet;
    String streetName;
    MapInterface retrofit;
    public static View mView;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;


    public void refreshLocations() {
            streetName = etStreet.getText().toString() + ", " + sharedPreferences.getString(LocationFragment.COUNTRY, "");

        Call<POJOPredictions> call = retrofit.getAutoCompleteInfo(streetName, "address", getResources().getString(R.string.google_maps_key));
        if(getArguments() == null) {
            ProgressBar basicProgressBar = (ProgressBar) getActivity().findViewById(R.id.basicProgressBar);
            basicProgressBar.setProgress(80);


            progressBar.setVisibility(View.VISIBLE);
        }
        recyclerView.setVisibility(View.INVISIBLE);
        call.enqueue(new Callback<POJOPredictions>() {
            @Override
            public void onResponse(Call<POJOPredictions> call, Response<POJOPredictions> response) {
                if(getArguments() == null){
                    progressBar.setVisibility(View.INVISIBLE);
                }
                recyclerView.setVisibility(View.VISIBLE);
                List<POJOPrediction> predictions = response.body().getPredictions();
                ArrayList<String> fullNameArrayList = new ArrayList<>();
                ArrayList<String> placeIdArrayList = new ArrayList<>();

                for(int i=0;i < predictions.size(); i++){
                    fullNameArrayList.add(predictions.get(i).getDescription());
                    placeIdArrayList.add(predictions.get(i).getPlaceId());
                }
//                List<String> addressComponentType = response.body().getResults().get(0).getAddressComponents().get(i).getTypes();
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(new LocationFilterAdapter(fullNameArrayList, placeIdArrayList)); // or pass fragment

            }

            @Override
            public void onFailure(Call<POJOPredictions> call, Throwable t) {
                Log.d("retrofit", "fail");
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_filter, container, false);
        mView = view;
        if(getArguments() == null) {
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        }
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        sharedPreferences = getActivity().getSharedPreferences(LocationFilterAdapter.LOCATION_SP, Context.MODE_PRIVATE);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.closeIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.hideKeyboard(getActivity());
                getActivity().getSupportFragmentManager().popBackStack("layoutStreet", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place/autocomplete/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(MapInterface.class);


        etStreet = (EditText) view.findViewById(R.id.etStreetFilter);


        TextWatcher textWatcher = new TextWatcher() {

            public void afterTextChanged(Editable s) {
                refreshLocations();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        };
        etStreet.addTextChangedListener(textWatcher);

    }


}
