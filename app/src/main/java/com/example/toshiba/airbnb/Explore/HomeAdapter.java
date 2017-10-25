package com.example.toshiba.airbnb.Explore;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cloudinary.Cloudinary;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.ViewListingAndYourBookingAdapter;
import com.example.toshiba.airbnb.R;

import java.util.ArrayList;


/**
 * Created by Owner on 2017-06-28.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    Context context;
    ArrayList<Integer> listingIdArrayList = new ArrayList<>();
    ArrayList<String> imagePathArrayList = new ArrayList<>();
    ArrayList<String> propertyOwnershipArrayList = new ArrayList<>();
    ArrayList<String> propertyTypeArrayList = new ArrayList<>();
    ArrayList<String> bedArrayList = new ArrayList<>();
    ArrayList<String> placeTitleArrayList = new ArrayList<>();
    ArrayList<String> priceArrayList = new ArrayList<>();
    int size = 0;


    public void addSize() {
        size++;
    }

    public void addListingId(int id) {
        listingIdArrayList.add(id);
    }

    public void addImagePath(String imagePath) {
        imagePathArrayList.add(imagePath);
    }

    public void addPropertyOwnership(String propertyOwnership) {
        propertyOwnershipArrayList.add(propertyOwnership);
    }

    public void addPropertyType(String propertyType) {
        propertyTypeArrayList.add(propertyType);
    }

    public void addTotalBed(String bed) {
        bedArrayList.add(bed);
    }

    public void addPlaceTitle(String placeTitle) {
        placeTitleArrayList.add(placeTitle);
    }

    public void addPrice(String price) {
        priceArrayList.add(price);
    }

    @Override
    public HomeAdapter.HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_adapter_item, parent, false);
        context = parent.getContext();
        HomeAdapter.HomeViewHolder homeViewHolder = new HomeAdapter.HomeViewHolder(view);
        return homeViewHolder;
    }

    @Override
    public void onBindViewHolder(HomeAdapter.HomeViewHolder holder, int position) {
        holder.bindView(position);
    }


    @Override
    public int getItemCount() {
        return size;
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutHome;
        ImageView ivHome;
        TextView tvPropertyTypeAndOwnership;
        TextView tvBed;
        TextView tvPlaceTitle;
        TextView tvPrice;
        Cloudinary cloudinary;

        public HomeViewHolder(View itemView) {
            super(itemView);
            layoutHome = (LinearLayout) itemView.findViewById(R.id.layoutHome);
            ivHome = (ImageView) itemView.findViewById(R.id.ivHome);
            tvPropertyTypeAndOwnership = (TextView) itemView.findViewById(R.id.tvPropertyTypeAndOwnership);
            tvBed = (TextView) itemView.findViewById(R.id.tvBed);
            tvPlaceTitle = (TextView) itemView.findViewById(R.id.tvPlaceTitle);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            cloudinary = new Cloudinary(context.
                    getResources().getString(R.string.cloudinaryEnviornmentVariable));

        }

        public void bindView(final int position) {

//            Glide.with(mContext)
//                    .load("")
//                    .placeholder(R.drawable.home)
//                    .centerCrop().into(ivHome);

            Glide.with(context)
                    .load(cloudinary.url().generate(imagePathArrayList.get(position)))
                    .placeholder(R.drawable.home)
                    .centerCrop().into(ivHome);

            layoutHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, HomeDescActivity.class);
                    intent.putExtra(ViewListingAndYourBookingAdapter.LISTING_ID, listingIdArrayList.get(position) );
                    context.startActivity(intent);
                }
            });
//
            tvPropertyTypeAndOwnership.setText(propertyOwnershipArrayList.get(position) + " " + propertyTypeArrayList.get(position));
            tvBed.setText(bedArrayList.get(position));
            tvPlaceTitle.setText(placeTitleArrayList.get(position));
            tvPrice.setText(priceArrayList.get(position) + " " + "per night");


        }
    }
}
