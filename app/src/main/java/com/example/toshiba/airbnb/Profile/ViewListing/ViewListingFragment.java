package com.example.toshiba.airbnb.Profile.ViewListing;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class ViewListingFragment extends Fragment {
    DatabaseInterface retrofit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.2.89:3000/")
                .baseUrl("http://192.168.0.34:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(DatabaseInterface.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_listing, container, false);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        retrofit.getListingImageAndTitle(getActivity().getSharedPreferences(SessionManager.SESSION_SP, Context.MODE_PRIVATE)
                .getInt(SessionManager.USER_ID, 0)).enqueue(new Callback<POJOListingImageAndTitleGetResult>() {
            @Override
            public void onResponse(Call<POJOListingImageAndTitleGetResult> call, Response<POJOListingImageAndTitleGetResult> response) {
                RecyclerView rvListing = (RecyclerView) view.findViewById(R.id.rvListing);
                rvListing.setLayoutManager(new LinearLayoutManager(getActivity()));
                rvListing.setAdapter(new ViewListingAdapter(response.body().getResult().size(), getActivity()));
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<POJOListingImageAndTitleGetResult> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("missYou", t.toString());
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_LONG).show();
                getActivity().onBackPressed();
            }
        });
    }
}
