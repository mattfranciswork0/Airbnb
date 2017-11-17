package com.example.toshiba.airbnb.UserAuthentication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.UserAuthentication.LogIn.LogInFragment;
import com.example.toshiba.airbnb.UserAuthentication.Registration.PhoneNumFragment;
import com.example.toshiba.airbnb.UserAuthentication.Registration.PhoneNumVerifyFragment;
import com.example.toshiba.airbnb.UserAuthentication.Registration.RegisterAgeFragment;
import com.example.toshiba.airbnb.UserAuthentication.Registration.RegisterEmailFragment;
import com.example.toshiba.airbnb.UserAuthentication.Registration.RegisterNameFragment;
import com.example.toshiba.airbnb.UserAuthentication.Registration.RegisterPasswordFragment;
import com.example.toshiba.airbnb.Util.RetrofitUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                fragmentTransaction.replace(R.id.progressFragment, logInFragment);
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
                fragmentTransaction.replace(R.id.progressFragment, registerNameFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }
}
