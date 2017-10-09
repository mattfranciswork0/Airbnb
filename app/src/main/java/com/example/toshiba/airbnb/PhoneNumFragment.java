package com.example.toshiba.airbnb;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.toshiba.airbnb.Profile.HostProfileEditDetailFragment;

import java.util.Random;


/**
 * Created by Owner on 2017-07-21.
 */

public class PhoneNumFragment extends Fragment {
    EditText etPhone;
    String userPhoneNum;
    Button bRegProceed;
    public static final String USER_PHONE_NUM = "USER_PHONE_NUM";
    public static final String VERIFICATION_CODE = "VERIFICATION_CODE";


    public void proceed() {
        if (etPhone.getText().length() > 10) {
            if (etPhone.getText().toString().contains("+")
                    && PhoneNumberUtils.isGlobalPhoneNumber(etPhone.getText().toString())) {
                userPhoneNum = etPhone.getText().toString();
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
        ProgressBar basicProgressBar = (ProgressBar) getActivity().findViewById(R.id.basicProgressBar);
        if (basicProgressBar != null) {
            basicProgressBar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.lightRed));
            basicProgressBar.setProgress(100);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone_num, container, false);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        bRegProceed = (Button) view.findViewById(R.id.bRegProceed);

        bRegProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Generate random num
                Random random = new Random();
                int min = 100000;
                int max = 999999;
                String randomNum = String.valueOf(random.nextInt((max - min) + 1) + min);
                SmsManager sm = SmsManager.getDefault();
                sm.sendTextMessage(userPhoneNum, null, "Airbnb security code: " + randomNum + ". Use this to finish verification", null, null);

                PhoneNumVerifyFragment phoneNumVerifyFragment = new PhoneNumVerifyFragment();
                Bundle bundle = new Bundle();
                bundle.putString(USER_PHONE_NUM, userPhoneNum);
                bundle.putString(VERIFICATION_CODE, randomNum);
                if (getArguments() != null) {
                    if (getArguments().containsKey(HostProfileEditDetailFragment.PHONE_NUM_EDIT)) {
                        bundle.putBoolean(HostProfileEditDetailFragment.PHONE_NUM_EDIT,
                                getArguments().getBoolean(HostProfileEditDetailFragment.PHONE_NUM_EDIT));
                    }
                }
                phoneNumVerifyFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.progressFragment, phoneNumVerifyFragment).addToBackStack(null).commit();
            }
        });

        etPhone = (EditText) view.findViewById(R.id.etPhone);

        TextWatcher textWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {
                proceed();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        };
        etPhone.addTextChangedListener(textWatcher);
    }


}