package com.example.toshiba.airbnb.UserAuthentication.LogIn;

/**
 * Created by TOSHIBA on 29/07/2017.
 */


import android.app.ProgressDialog;
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
import com.example.toshiba.airbnb.LoadingMenuActivity;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.UserAuthentication.SessionManager;
import com.example.toshiba.airbnb.Util.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Owner on 2017-06-23.
 */

public class LogInFragment extends android.support.v4.app.Fragment {
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

        retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();


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
                    final ProgressDialog dialog = new ProgressDialog(getActivity());
                    dialog.setMessage("Logging you in...");
                    dialog.setCancelable(false);
                    dialog.show();
                    DTOLogInRequest DTOLogInRequest = new DTOLogInRequest(etEmail.getText().toString(), etPassword.getText().toString());
                    Call<POJOPasswordMatch> call = retrofit.findLogInData(DTOLogInRequest);
                    call.enqueue(new Callback<POJOPasswordMatch>() {
                        @Override
                        public void onResponse(Call<POJOPasswordMatch> call, Response<POJOPasswordMatch> response) {
                            if (response.body().getPasswordMatch()) {
                                dialog.dismiss();
                                Log.d("blue", "success login");
                                POJOPasswordMatch body = response.body();
                                SessionManager sessionManager = new SessionManager(getActivity());
                                sessionManager.createLoginSession(body.getUserId(), body.getEmail(), body.getFirstName(), body.getLastName(), body.getPhoneNum());
                                Intent intent = new Intent(getActivity(), LoadingMenuActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                dialog.dismiss();
                                Toast.makeText(getActivity(), "Invalid login creddientals", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<POJOPasswordMatch> call, Throwable t) {
                            Log.d("blue", "fail login:" + t.getMessage());
                            Toast.makeText(getActivity(),  t.getMessage(), Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    });
                }
            });
        }
    }
}

