package com.example.toshiba.airbnb.UserAuthentication.Registration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.Profile.UserProfile.DTO.DTOPhoneNumDetailEdit;
import com.example.toshiba.airbnb.Profile.UserProfile.UserProfileEditDetailFragment;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.UserAuthentication.DTOUserRegistrationRequest;
import com.example.toshiba.airbnb.UserAuthentication.SessionManager;
import com.example.toshiba.airbnb.UserAuthentication.WelcomeActivity;
import com.example.toshiba.airbnb.Util.RetrofitUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Owner on 2017-07-21.
 */

public class PhoneNumVerifyFragment extends Fragment {
    public static String PHONE_NUM;
    DatabaseInterface retrofit;
    EditText etCode;
    Button bRegProceed;

    public void proceed() {
        if (etCode.getText().length() == 4) {
            {
                bRegProceed.setEnabled(true);
                bRegProceed.setBackgroundResource(R.drawable.reg_proceed_button);
                bRegProceed.setTextColor(Color.parseColor("#ff6666"));
            }

        } else {
            bRegProceed.setEnabled(false);
            bRegProceed.setBackgroundResource(R.drawable.reg_proceed_button_fail);
            bRegProceed.setTextColor(Color.parseColor("#ff6666"));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();


        ProgressBar basicProgressBar = (ProgressBar) getActivity().findViewById(R.id.basicProgressBar);
        if (basicProgressBar != null) basicProgressBar.setProgress(100);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone_num_verify, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvDesc = (TextView) view.findViewById(R.id.tvDesc);
        tvDesc.setText("We just sent a code to " + getArguments().getString(PhoneNumFragment.USER_PHONE_NUM) + ". Enter the code in that message.");
        etCode = (EditText) view.findViewById(R.id.etCode);
        bRegProceed = (Button) view.findViewById(R.id.bRegProceed);
        TextWatcher textWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {
                proceed();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

        };
        etCode.addTextChangedListener(textWatcher);

        bRegProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getArguments() != null) {
                    if (etCode.getText().toString().equals(getArguments().getString(PhoneNumFragment.VERIFICATION_CODE))) {

                        Log.d("loveya", "block 0");
                        PHONE_NUM = getArguments().getString(PhoneNumFragment.USER_PHONE_NUM);
                        SharedPreferences sessionSP = getActivity().getSharedPreferences(SessionManager.SESSION_SP, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sessionSP.edit();
                        editor.putString(SessionManager.PHONE_NUM, PHONE_NUM);
                        editor.apply();
                        if (getArguments().containsKey(UserProfileEditDetailFragment.PHONE_NUM_EDIT)) {
                            Log.d("loveya", "block 1");
                            final int USER_ID = getActivity().getSharedPreferences(SessionManager.SESSION_SP, Context.MODE_PRIVATE)
                                    .getInt(SessionManager.USER_ID, 0);
                            retrofit.insertPhoneNumDetailEdit(USER_ID, new DTOPhoneNumDetailEdit(PHONE_NUM)).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    Toast.makeText(getActivity(), "Sucessfully updated phone number", Toast.LENGTH_LONG).show();
                                    getFragmentManager().popBackStack(getActivity().getResources().getString(R.string.hostProfileEditFragment),
                                            getFragmentManager().POP_BACK_STACK_INCLUSIVE);
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(getActivity(), "Failed to update phone number, check your internet connection and try again", Toast.LENGTH_LONG).show();
                                }
                            });

                        } else if (getArguments().containsKey(PhoneNumFragment.VERIFICATION_CODE)) {
                            Log.d("loveya", "block 2");
                            DTOUserRegistrationRequest DTOUserRegistrationRequest = new DTOUserRegistrationRequest(
                                    RegisterNameFragment.FIRST_NAME, RegisterNameFragment.LAST_NAME,
                                    RegisterEmailFragment.EMAIL,
                                    RegisterPasswordFragment.PASSWORD, PHONE_NUM);
//                            DTOUserRegistrationRequest DTOUserRegistrationRequest = new DTOUserRegistrationRequest(
//                                    "Matt", "Francis", "e@gmail.com",
//                                    "heyBestie123", "2897727436");

                            Call<Void> call = retrofit.insertUserRegistration(DTOUserRegistrationRequest);
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    Toast.makeText(getActivity(), "Registration success", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(getActivity(), "Failed to register your account, check your internet connection and try again", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }

                }
            }
        });


    }
}
