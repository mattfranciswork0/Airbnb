package com.example.toshiba.airbnb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.toshiba.airbnb.Explore.MenuActivity;
import com.example.toshiba.airbnb.Explore.POJOTotalListings;
import com.example.toshiba.airbnb.Util.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by TOSHIBA on 14/10/2017.
 */

public class LoadingMenuActivity extends Activity {
    public static String TOTAL_LISTINGS = "TOTAL_LISTINGS";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_loading);
        final DatabaseInterface retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();
        //search for results for total_guests that are greater than 1
        retrofit.getTotalListings(" ", " ", " ", " ", "1", " ", " ", " ").enqueue(new Callback<POJOTotalListings>() {
            @Override
            public void onResponse(Call<POJOTotalListings> call, Response<POJOTotalListings> response) {
                int size = Integer.parseInt(response.body().getTotalListings());
                Intent intent = new Intent(LoadingMenuActivity.this, MenuActivity.class);
                intent.putExtra(TOTAL_LISTINGS, size);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<POJOTotalListings> call, Throwable t) {
                Log.d("loadingFail", "loadingFail" + t.toString());
                onBackPressed();
            }
        });
    }


}
