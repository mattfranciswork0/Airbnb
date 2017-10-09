package com.example.toshiba.airbnb.UserAuthentication;

/**
 * Created by TOSHIBA on 29/07/2017.
 */


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.Explore.MenuActivity;
import com.example.toshiba.airbnb.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Owner on 2017-06-23.
 */

public class LogInFragment extends Fragment {
    EditText etEmail;
    EditText etPassword;
    Button bRegProceed;
    DatabaseInterface retrofit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        bRegProceed = (Button) view.findViewById(R.id.bRegProceed);

        retrofit = new Retrofit.Builder()
                //                .baseUrl("http://192.168.2.89:3000/")
                .baseUrl("http://192.168.0.34:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(DatabaseInterface.class);


        TextWatcher textWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {
                logInProceed();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        };
        etEmail.addTextChangedListener(textWatcher);
        etPassword.addTextChangedListener(textWatcher);

        return view;

    }

    public void logInProceed() {
        if (etEmail.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()) {
            bRegProceed.setEnabled(false);
            bRegProceed.setBackgroundResource(R.drawable.reg_proceed_button_fail);
            bRegProceed.setTextColor(Color.parseColor("#ff6666"));
        } else {
            bRegProceed.setEnabled(true);
            bRegProceed.setBackgroundResource(R.drawable.reg_proceed_button);
            bRegProceed.setTextColor(Color.parseColor("#ff6666"));
            bRegProceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogInRequest logInRequest = new LogInRequest(etEmail.getText().toString(), etPassword.getText().toString());
                    Call<PasswordMatch> call = retrofit.findLogInData(logInRequest);
                    call.enqueue(new Callback<PasswordMatch>() {
                        @Override
                        public void onResponse(Call<PasswordMatch> call, Response<PasswordMatch> response) {
                            if (response.body().getPasswordMatch()) {
                                //TODO: PROGRESSDIALOG
                                Log.d("blue", "success login");
                                PasswordMatch body = response.body();
                                SessionManager sessionManager = new SessionManager(getActivity());
                                sessionManager.createLoginSession(body.getUserId(), body.getEmail(), body.getFirstName(), body.getLastName(), body.getPhoneNum());
                                Intent intent = new Intent(getActivity(), MenuActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getActivity(), "Invalid login creddientals", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PasswordMatch> call, Throwable t) {
                            Log.d("blue", "fail login:" + t.getMessage());
                        }
                    });
                }
            });
        }
    }
}

