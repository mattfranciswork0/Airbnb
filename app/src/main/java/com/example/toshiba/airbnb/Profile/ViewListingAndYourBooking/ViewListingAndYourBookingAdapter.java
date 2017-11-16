package com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cloudinary.Cloudinary;
import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.EditListing.EditListingActivity;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.POJO.POJOListingImageAndTitleGetResult;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.POJO.POJOYourBookingGetResult;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.Util.RetrofitUtil;
import com.example.toshiba.airbnb.UserAuthentication.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by TOSHIBA on 15/09/2017.
 */

public class ViewListingAndYourBookingAdapter extends RecyclerView.Adapter<ViewListingAndYourBookingAdapter.ViewListingViewHolder> {
    DatabaseInterface retrofit;
    Context context;
    int size;
    boolean isYourBooking;
    public static String LISTING_ID = "LISTING_ID";
    public ViewListingAndYourBookingAdapter(int size, Context context, boolean isYourBooking){
        retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();
        this.size = size;
        this.context = context;
        this.isYourBooking = isYourBooking;
    }

    @Override
    public ViewListingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_listing_and_your_booking_adapter_item, parent, false);
        ViewListingViewHolder viewListingViewHolder = new ViewListingViewHolder(view);
        return viewListingViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewListingViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {

        return size;
    }

    public class ViewListingViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        ImageView ivListing;
        TextView tvCheckIn;
        TextView tvCheckOut;

        public ViewListingViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            ivListing = (ImageView) itemView.findViewById(R.id.ivListing);
            tvCheckIn = (TextView) itemView.findViewById(R.id.tvCheckIn);
            tvCheckOut = (TextView) itemView.findViewById(R.id.tvCheckOut);


        }
        public void bindView(final int position) {
            if (isYourBooking) {
                retrofit.getBookingListingImageAndTitle(
                        context.getSharedPreferences(SessionManager.SESSION_SP, Context.MODE_PRIVATE)
                                .getInt(SessionManager.USER_ID, 0)).enqueue(new Callback<POJOYourBookingGetResult>() {
                    @Override
                    public void onResponse(Call<POJOYourBookingGetResult> call, final Response<POJOYourBookingGetResult> response) {
                        tvTitle.setText(response.body().getResult().get(position).getPlaceTitle());
                        Cloudinary cloudinary = new Cloudinary(context.getResources().getString(R.string.cloudinaryEnviornmentVariable)); //configured using an environment variable
                        Glide.with(context).
                                load(cloudinary.url().generate(response.body().getResult().get(position).getImagePath())).into(ivListing);
                        itemView.findViewById(R.id.listingLayout).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                Intent intent = new Intent(context, HomeDescActivity.class);
//                                intent.putExtra(LISTING_ID, response.body().getResult().get(position).getListingId());
//                                context.startActivity(intent);

                                Intent intent = new Intent(context, EditListingActivity.class);
                                intent.putExtra(LISTING_ID, response.body().getResult().get(position).getListingId());
                                context.startActivity(intent);

                            }
                        });

                        tvCheckIn.setText("Check in: " + response.body().getResult().get(0).getCheckIn());
                        tvCheckOut.setText("Check out: " + response.body().getResult().get(0).getCheckOut());

                    }

                    @Override
                    public void onFailure(Call<POJOYourBookingGetResult> call, Throwable t) {
                        Log.d("inLove", t.toString());
                    }
                });
            } else {
                retrofit.getListingImageAndTitle(context.getSharedPreferences(SessionManager.SESSION_SP, Context.MODE_PRIVATE)
                        .getInt(SessionManager.USER_ID, 0)).enqueue(new Callback<POJOListingImageAndTitleGetResult>() {
                    @Override
                    public void onResponse(Call<POJOListingImageAndTitleGetResult> call, final Response<POJOListingImageAndTitleGetResult> response) {
                        tvTitle.setText(response.body().getResult().get(position).getPlaceTitle());
                        Cloudinary cloudinary = new Cloudinary(context.getResources().getString(R.string.cloudinaryEnviornmentVariable)); //configured using an environment variable
                        Glide.with(context).
                                load(cloudinary.url().generate(response.body().getResult().get(position).getImagePath())).into(ivListing);
                        itemView.findViewById(R.id.listingLayout).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                Intent intent = new Intent(context, HomeDescActivity.class);
//                                intent.putExtra(LISTING_ID, response.body().getResult().get(position).getId());
//                                context.startActivity(intent);
                                Intent intent = new Intent(context, EditListingActivity.class);
                                intent.putExtra(LISTING_ID, response.body().getResult().get(position).getId());
                                context.startActivity(intent);


                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<POJOListingImageAndTitleGetResult> call, Throwable t) {
                        Log.d("missYouAdapter", t.toString());

                    }
                });
            }
        }
    }
}
