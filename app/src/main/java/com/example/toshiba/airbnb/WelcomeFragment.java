package com.example.toshiba.airbnb;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by TOSHIBA on 10/09/2017.
 */

public class WelcomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvLogIn = (TextView) view.findViewById(R.id.tvLogIn);
        tvLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                LogInFragment logInFragment = new LogInFragment();
                fragmentTransaction.replace(R.id.container, logInFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        //Create Account Button
        Button bRegister = (Button) view.findViewById(R.id.bRegister);
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                RegisterNameFragment registerNameFragment = new RegisterNameFragment();
                fragmentTransaction.replace(R.id.container, registerNameFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        final DatabaseInterface retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.2.89:3000/")
                .baseUrl("http://192.168.0.34:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(DatabaseInterface.class);
        Button bPost = (Button) view.findViewById(R.id.bPost);
        bPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest(
//                        RegisterNameFragment.sFirstName, RegisterNameFragment.sLastName, RegisterEmailFragment.sEmail,
//                        RegisterPasswordFragment.sPassword, PhoneNumVerifyFragment.phoneNum);

                UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest(
                        "Matt", "Francis", "q@gmail.com",
                        "heyBestie123", "2897727436");

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
                        Log.d("blue", "fail:" +t.getMessage());
                    }
                });

            }
        });

    }
}
