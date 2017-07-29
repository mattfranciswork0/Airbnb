package com.example.toshiba.airbnb;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

//        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);
//        Typeface openSans = Typeface.createFromAsset(getAssets(), "fonts.opensans/OpenSans-Regular.ttf");
//        tvWelcome.setTypeface(openSans);

        //Create Account Button
        Button bRegister = (Button) findViewById(R.id.bRegister);
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                RegisterNameFragment registerNameFragment = new RegisterNameFragment();
                fragmentTransaction.replace(R.id.activity_welcome, registerNameFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });



    }
}
