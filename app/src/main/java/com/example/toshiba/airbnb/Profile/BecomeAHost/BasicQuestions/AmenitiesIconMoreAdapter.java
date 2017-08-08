package com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.toshiba.airbnb.R;

import java.util.ArrayList;
import java.util.Map;


/**
 * Created by Owner on 2017-07-23.
 */

public class AmenitiesIconMoreAdapter extends RecyclerView.Adapter<AmenitiesIconMoreAdapter.AmenitiesIconMoreViewHolder> {
    Activity activity;
    SharedPreferences amenitiesSP;
    private Map<String, ?> savedAmenities;
    private ArrayList<String> icons = new ArrayList<>();

    public AmenitiesIconMoreAdapter(Activity activity) {
        this.activity = activity;
        amenitiesSP = activity.getSharedPreferences(AmenitiesItemFragment.AMENITIES_SP, Context.MODE_PRIVATE);

    }

    @Override
    public AmenitiesIconMoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("recyclerAdapter", "onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_amenities_icon_more_item, parent, false);
        savedAmenities = amenitiesSP.getAll();
        for (String key : savedAmenities.keySet()) {
            icons.add(key);
        }


        return new AmenitiesIconMoreAdapter.AmenitiesIconMoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AmenitiesIconMoreViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        Log.d("recyclerAdapter", "itemCount");
        return amenitiesSP.getAll().size();
    }

    public class AmenitiesIconMoreViewHolder extends RecyclerView.ViewHolder {
        TextView tvIconName;
        ImageView ivIcon;

        public AmenitiesIconMoreViewHolder(View itemView) {
            super(itemView);
            tvIconName = (TextView) itemView.findViewById(R.id.tvIconName);
            ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);

        }

        public void bindView(int position) {
            if (icons.get(position).equals(activity.getResources().getString(R.string.rbEssentials))) {
                tvIconName.setText("Essentials");
                Glide.with(activity).load(activity.getResources().getString(R.string.EssentialsIcon)).into(ivIcon);
                return;
            }

            if (icons.get(position).equals(activity.getResources().getString(R.string.rbInternet))) {
                tvIconName.setText("Internet");
                Glide.with(activity).load(activity.getResources().getString(R.string.InternetIcon)).into(ivIcon);
                return;
            }

            if (icons.get(position).equals(activity.getResources().getString(R.string.rbShampoo))) {
                tvIconName.setText("Shampoo");
                Glide.with(activity).load(activity.getResources().getString(R.string.ShampooIcon)).into(ivIcon);
                return;
            }


            if (icons.get(position).equals(activity.getResources().getString(R.string.rbHangers))) {
                tvIconName.setText("Hangers");
                Glide.with(activity).load(activity.getResources().getString(R.string.HangersIcon)).into(ivIcon);
                return;
            }

            if (icons.get(position).equals(activity.getResources().getString(R.string.rbTV))) {
                tvIconName.setText("TV");
                Glide.with(activity).load(activity.getResources().getString(R.string.TVIcon)).into(ivIcon);
                return;
            }

            if (icons.get(position).equals(activity.getResources().getString(R.string.rbHeating))) {
                tvIconName.setText("Heating");
                Glide.with(activity).load(activity.getResources().getString(R.string.HeatingIcon)).into(ivIcon);
                return;
            }

            if (icons.get(position).equals(activity.getResources().getString(R.string.rbAirConditioning))) {
                tvIconName.setText("Air Conditoning");
                Glide.with(activity).load(activity.getResources().getString(R.string.AirConditioningIcon)).into(ivIcon);
                return;
            }

            if (icons.get(position).equals(activity.getResources().getString(R.string.rbBreakfast))) {
                tvIconName.setText("Breakfast");
                Glide.with(activity).load(activity.getResources().getString(R.string.BreakfastIcon)).into(ivIcon);
                return;
            }

            //Spaces Fragment
            if (icons.get(position).equals(activity.getResources().getString(R.string.rbKitchen))) {
                tvIconName.setText("Kitchen");
                Glide.with(activity).load(activity.getResources().getString(R.string.KitchenIcon)).into(ivIcon);
                return;
            }

            if (icons.get(position).equals(activity.getResources().getString(R.string.rbLaundry))) {
                tvIconName.setText("Laundry");
                Glide.with(activity).load(activity.getResources().getString(R.string.LaundryIcon)).into(ivIcon);
                return;
            }

            if (icons.get(position).equals(activity.getResources().getString(R.string.rbParking))) {
                tvIconName.setText("Parking");
                Glide.with(activity).load(activity.getResources().getString(R.string.ParkingIcon)).into(ivIcon);
                return;
            }
            if (icons.get(position).equals(activity.getResources().getString(R.string.rbElevator))) {
                tvIconName.setText("Elevator");
                Glide.with(activity).load(activity.getResources().getString(R.string.ElevatorIcon)).into(ivIcon);
                return;
            }
            if (icons.get(position).equals(activity.getResources().getString(R.string.rbPool))) {
                tvIconName.setText("Pool");
                Glide.with(activity).load(activity.getResources().getString(R.string.PoolIcon)).into(ivIcon);
                return;
            }
            if (icons.get(position).equals(activity.getResources().getString(R.string.rbGym))) {
                tvIconName.setText("Gym");
                Glide.with(activity).load(activity.getResources().getString(R.string.GymIcon)).into(ivIcon);
                return;
            }

            Log.e("MoreIconAdapter", "Should not be able to reach this staement; make sure to list all posibilitees of the amenities iconho");

        }
    }

}

