package com.example.toshiba.airbnb;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        SessionManager sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        getFragmentManager().beginTransaction().replace(R.id.container, new WelcomeFragment()).commit();

//        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);
//        Typeface openSans = Typeface.createFromAsset(getAssets(), "fonts.opensans/OpenSans-Regular.ttf");
//        tvWelcome.setTypeface(openSans);

       
    }
}
