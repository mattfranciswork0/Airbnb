package com.example.toshiba.airbnb.Profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by TOSHIBA on 15/09/2017.
 */

public class ViewListingAdapter extends RecyclerView.Adapter<ViewListingAdapter.ViewListingViewHolder> {
    DatabaseInterface retrofit;
    Context context;
    ProgressDialog progressDialog;
    int size;
    public ViewListingAdapter(int size, Context context){
        retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.2.89:3000/")
                .baseUrl("http://192.168.0.34:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(DatabaseInterface.class);
        this.size = size;
        this.context = context;
    }

    @Override
    public ViewListingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_listing_adapter_item, parent, false);
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
        public ViewListingViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            ivListing = (ImageView) itemView.findViewById(R.id.ivListing);
        }
        public void bindView(final int position){
            retrofit.getListingImageAndTitle(context.getSharedPreferences(SessionManager.SESSION_SP, Context.MODE_PRIVATE)
                    .getInt(SessionManager.USER_ID, 0)).enqueue(new Callback<POJOImageAndListingGetResult>() {
                @Override
                public void onResponse(Call<POJOImageAndListingGetResult> call, Response<POJOImageAndListingGetResult> response) {
                    tvTitle.setText(response.body().getResult().get(position).getPlaceTitle());
                    Cloudinary cloudinary = new Cloudinary(context.getResources().getString(R.string.cloudinaryEnviornmentVariable)); //configured using an environment variable
                    Glide.with(context).
                            load(cloudinary.url().generate(response.body().getResult().get(position).getImagePath())).into(ivListing);
                }

                @Override
                public void onFailure(Call<POJOImageAndListingGetResult> call, Throwable t) {
                    Log.d("missYouAdapter", t.toString());

                }
            });
        }
    }
}
