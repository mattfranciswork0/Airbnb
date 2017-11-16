package com.example.toshiba.airbnb.UserAuthentication.Registration;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.widget.LinearLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Owner on 2017-06-10.
 */

import com.example.toshiba.airbnb.R;

/**
 * Created by Owner on 2017-06-10.
 */

public class RegisterPasswordFragment extends Fragment {
    EditText etPassword;
    Button bRegProceed;
    LinearLayout errorLayout;
    public static String sPassword;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password, container, false);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        bRegProceed = (Button) view.findViewById(R.id.bRegProceed);
        errorLayout = (LinearLayout) view.findViewById(R.id.errorLayout);

        TextWatcher textWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {
                registrationProceed();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        };
        etPassword.addTextChangedListener(textWatcher);

        return view;

    }

    public static boolean isValidPassword(String password) {
        final String ALPHANUMERICAL_PATTERN = "[^\\w\\d]*(([0-9]+.*[A-Za-z]+.*)|[A-Za-z]+.*([0-9]+.*))";
        Pattern pattern = Pattern.compile(ALPHANUMERICAL_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public void registrationProceed() {
        //errorLayout uses elevation attribute (API 21 and above, check for optimizations later)
        errorLayout.setVisibility(View.INVISIBLE);
        if (etPassword.getText().toString().trim().length() >= 8) {
            if (isValidPassword(etPassword.getText().toString())) {
                bRegProceed.setEnabled(true);
                //if statement added here to prevent REGEX operation from executing every text change
                bRegProceed.setBackgroundResource(R.drawable.reg_proceed_button);
                bRegProceed.setTextColor(Color.parseColor("#ff6666"));
                bRegProceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sPassword = etPassword.getText().toString();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        RegisterAgeFragment registerAgeFragment = new RegisterAgeFragment();
                        fragmentTransaction.replace(R.id.container, registerAgeFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });
            }else{
                bRegProceed.setEnabled(false);
                errorLayout.setVisibility(View.VISIBLE);
            }
        } else {
            bRegProceed.setBackgroundResource(R.drawable.reg_proceed_button_fail);
            bRegProceed.setTextColor(Color.parseColor("#ff6666"));
        }
    }
}
