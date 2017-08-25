package com.example.toshiba.airbnb;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Owner on 2017-06-09.
 */

public class RegisterEmailFragment extends Fragment {
    EditText etEmail;
    Button bRegProceed;
    public static String sEmail;
    DatabaseInterface retrofit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_email, container, false);
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        bRegProceed = (Button) view.findViewById(R.id.bRegProceed);

        retrofit = new Retrofit.Builder()
                //                .baseUrl("http://192.168.2.89:3000/")
                .baseUrl("http://192.168.1.109:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(DatabaseInterface.class);

        TextWatcher textWatcher = new TextWatcher() {
            //Check if email is valid
            public void afterTextChanged(Editable s) {
                registrationProceed();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        };
        etEmail.addTextChangedListener(textWatcher);
        return view;
    }

    public static boolean isValidEmail(String email) {
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void registrationProceed() {
        if (isValidEmail(etEmail.getText().toString().trim())) {
            bRegProceed.setEnabled(true);
            bRegProceed.setBackgroundResource(R.drawable.reg_proceed_button);
            bRegProceed.setTextColor(Color.parseColor("#ff6666"));
            bRegProceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    sEmail = etEmail.getText().toString();
                    retrofit.findUser(sEmail).enqueue(new Callback<EmailResult>() {
                        @Override
                        public void onResponse(Call<EmailResult> call, Response<EmailResult> response) {
                            if(!(response.body().getEmail().equals("null"))) {
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                RegisterPasswordFragment registerPasswordFragment = new RegisterPasswordFragment();
                                fragmentTransaction.replace(R.id.activity_welcome, registerPasswordFragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            } else{
                                Toast.makeText(getActivity(), "Email already existed", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<EmailResult> call, Throwable t) {

                        }
                    });

                }
            });
        } else {
            bRegProceed.setEnabled(false);
            bRegProceed.setBackgroundResource(R.drawable.reg_proceed_button_fail);
            bRegProceed.setTextColor(Color.parseColor("#ff6666"));
        }
    }
}
