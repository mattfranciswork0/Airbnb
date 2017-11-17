package com.example.toshiba.airbnb.UserAuthentication;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.toshiba.airbnb.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        SessionManager sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        getSupportFragmentManager().beginTransaction().replace(R.id.progressFragment, new WelcomeFragment()).commit();

//        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);
//        Typeface openSans = Typeface.createFromAsset(getAssets(), "fonts.opensans/OpenSans-Regular.ttf");
//        tvWelcome.setTypeface(openSans);

       
    }
}
