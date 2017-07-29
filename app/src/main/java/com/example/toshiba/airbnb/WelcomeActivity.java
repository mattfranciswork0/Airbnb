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

//        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);
//        Typeface openSans = Typeface.createFromAsset(getAssets(), "fonts.opensans/OpenSans-Regular.ttf");
//        tvWelcome.setTypeface(openSans);
        TextView tvLogIn = (TextView) findViewById(R.id.tvLogIn);
        tvLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                LogInFragment logInFragment = new LogInFragment();
                fragmentTransaction.replace(R.id.activity_welcome, logInFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
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

        final DatabaseInterface retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.2.89:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(DatabaseInterface.class);
        Button bPost = (Button) findViewById(R.id.bPost);
        bPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest(
                        RegisterNameFragment.sFirstName, RegisterNameFragment.sLastName, RegisterEmailFragment.sEmail,
                        RegisterPasswordFragment.sPassword, RegisterAgeFragment.sAge);

                Call<Void> call = retrofit.insertUserRegistration(userRegistrationRequest);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d("blue", "Data is sent");
                        if (response.isSuccessful()) {
                        } else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                jObjError.getString("message");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("blue", "fail");
                    }
                });

            }
        });

    }
}
