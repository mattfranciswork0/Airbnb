package com.example.toshiba.airbnb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toshiba.airbnb.Profile.BecomeAHost.BecomeAHostActivity;
import com.example.toshiba.airbnb.Profile.DTO.PhoneNumDetailEditDTO;
import com.example.toshiba.airbnb.Profile.HostProfileEditDetailFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Owner on 2017-07-21.
 */

public class PhoneNumVerifyFragment extends Fragment {
    public static String phoneNum;
    DatabaseInterface retrofit;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.2.89:3000/")
                .baseUrl("http://192.168.0.34:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(DatabaseInterface.class);



        ProgressBar basicProgressBar = (ProgressBar) getActivity().findViewById(R.id.basicProgressBar);
        if(basicProgressBar!= null) basicProgressBar.setProgress(100);
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

        final EditText etCode = (EditText) view.findViewById(R.id.etCode);
        view.findViewById(R.id.bRegProceed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etCode.getText().toString().equals(getArguments().getString(PhoneNumFragment.VERIFICATION_CODE))) {
                    Toast.makeText(getActivity(), "Verification successfull", Toast.LENGTH_LONG).show();
                    if (getArguments() != null) {
                        phoneNum = getArguments().getString(PhoneNumFragment.USER_PHONE_NUM);
                        SharedPreferences sessionSP = getActivity().getSharedPreferences(SessionManager.SESSION_SP, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sessionSP.edit();
                        editor.putString(SessionManager.PHONE_NUM, phoneNum);
                        editor.apply();
                        if (getArguments().containsKey(HostProfileEditDetailFragment.PHONE_NUM_EDIT)) {
                            final int USER_ID = getActivity().getSharedPreferences(SessionManager.SESSION_SP, Context.MODE_PRIVATE)
                                    .getInt(SessionManager.USER_ID, 0);
                            retrofit.insertPhoneNumDetailEdit(USER_ID, new PhoneNumDetailEditDTO(phoneNum)).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    Toast.makeText(getActivity(), "Sucessfully updated phone number", Toast.LENGTH_LONG).show();
                                    getFragmentManager().popBackStack(getActivity().getResources().getString(R.string.hostProfileEditFragment),
                                            getFragmentManager().POP_BACK_STACK_INCLUSIVE);
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(getActivity(), "Failed to update phone number, try again", Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                    } else {
                        Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
            }
        });


    }
}
