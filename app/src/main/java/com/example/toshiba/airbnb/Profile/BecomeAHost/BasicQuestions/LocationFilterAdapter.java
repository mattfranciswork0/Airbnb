package com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.POJOMap.GMapsPlaceDetails.POJOResult;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.POJOMap.GMapsPlaceDetails.POJOResults;
import com.example.toshiba.airbnb.R;

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

public class LocationFilterAdapter extends RecyclerView.Adapter<LocationFilterAdapter.LocationFilterViewHolder> {
    public Context mActivity;
    private MapInterface retrofit;
    public static String LOCATION_SP = "LOCATION_SP";
    public static String LAT = "LAT";
    public static String LNG = "LNG";
    public static String STREET_NAME = "STREET_NAME";
    public static String STATE_NAME = "STATE_NAME";
    public static String CITY_NAME = "CITY_NAME";
    public static String COUNTRY_NAME = "COUNTRY_NAME";
    ArrayList<String> fullNameArrayList = new ArrayList<>();
    ArrayList<String> placeIdArrayList = new ArrayList<>();

    public LocationFilterAdapter(ArrayList fullNameArrayList, ArrayList placeIdArrayList) {
        this.fullNameArrayList = fullNameArrayList;
        this.placeIdArrayList = placeIdArrayList;
    }

    @Override
    public LocationFilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_filter_adapter_item, parent, false);
        mActivity = parent.getContext();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place/details/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(MapInterface.class);

        return new LocationFilterAdapter.LocationFilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocationFilterViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return fullNameArrayList.size();
    }

    public class LocationFilterViewHolder extends RecyclerView.ViewHolder {
        TextView tvLocations;

        public LocationFilterViewHolder(View itemView) {
            super(itemView);
            tvLocations = (TextView) itemView.findViewById(R.id.tvFilterLocations);
        }

        public void bindView(final int position) {
//            final String output = LocationFilterFragment.STREET_NAME_VALUE + ", " + LocationFilterFragment.CITY_NAME_VALUE
//                    + ", " + LocationFilterFragment.STATE_NAME_VALUE + ", " + LocationFilterFragment.COUNTRY_NAME_VALUE;
            final String output = fullNameArrayList.get(position);
            tvLocations.setText(output);

            tvLocations.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText etStreet = (EditText) LocationFilterFragment.mView.findViewById(R.id.etStreetFilter);
                    etStreet.setText(output);
                    SharedPreferences sharedPreferences = mActivity.getSharedPreferences(LOCATION_SP, Context.MODE_PRIVATE);
                    final SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.clear();


                    Call<POJOResults> call = retrofit.getPlaceDetailsInfo(placeIdArrayList.get(position), mActivity.getResources().getString(R.string.google_maps_key));
                    call.enqueue(new Callback<POJOResults>() {
                        @Override
                        public void onResponse(Call<POJOResults> call, Response<POJOResults> response) {
                            POJOResult result = response.body().getResult();
                            SharedPreferences sharedPreferences = mActivity.getSharedPreferences(LOCATION_SP, Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.clear();
                            for (int i = 0; i < result.getAddressComponents().size(); i++) {

                                if (result.getAddressComponents().get(i).getTypes().get(0).equals("route"))
                                    edit.putString(STREET_NAME, result.getAddressComponents().get(i).getLongName());

                                else if (result.getAddressComponents().get(i).getTypes().get(0).equals("locality"))
                                    edit.putString(CITY_NAME, result.getAddressComponents().get(i).getLongName());

                                else if (result.getAddressComponents().get(i).getTypes().get(0).equals("administrative_area_level_1"))
                                    edit.putString(STATE_NAME, result.getAddressComponents().get(i).getShortName());

                                else if (result.getAddressComponents().get(i).getTypes().get(0).equals("country"))
                                    edit.putString(COUNTRY_NAME, result.getAddressComponents().get(i).getLongName());
                            }
                            edit.putString(LNG,result.getGeometry().getLocation().getLng().toString());
                            edit.putString(LAT,result.getGeometry().getLocation().getLat().toString());
                            edit.apply();
                            ((AppCompatActivity) mActivity).getSupportFragmentManager().popBackStack("layoutStreet", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        }

                        @Override
                        public void onFailure(Call<POJOResults> call, Throwable t) {
                            Toast.makeText(mActivity, "INTERNET CONNECTION NEEDED", Toast.LENGTH_LONG).show();
                        }
                    });


                }
            });
        }

    }
}