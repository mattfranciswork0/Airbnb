package com.example.toshiba.airbnb;

import android.content.Intent;
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


/**
 * Created by Owner on 2017-07-21.
 */

public class PhoneNumVerifyFragment extends Fragment {
    public static String phoneNum;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressBar basicProgressBar = (ProgressBar) getActivity().findViewById(R.id.basicProgressBar);
        basicProgressBar.setProgress(100);
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
        tvDesc.setText("We just sent a code to " + getArguments().getString(PhoneNumFragment.USER_PHONE_NUM)+". Enter the code in that message.");

        final EditText etCode = (EditText) view.findViewById(R.id.etCode);
        view.findViewById(R.id.bRegProceed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etCode.getText().toString().equals(getArguments().getString(PhoneNumFragment.VERIFICATION_CODE))){
                    Toast.makeText(getActivity(), "Verification successfull", Toast.LENGTH_LONG).show();
                    phoneNum = getArguments().getString(PhoneNumFragment.USER_PHONE_NUM);
                    Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });


    }
}
