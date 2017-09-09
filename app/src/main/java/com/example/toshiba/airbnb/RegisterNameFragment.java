package com.example.toshiba.airbnb;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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


/**
 * Created by Owner on 2017-06-09.
 */

public class RegisterNameFragment extends Fragment {
    Button bRegProceed;
    EditText etFirstName;
    EditText etLastName;
    public static String sFirstName;
    public static String sLastName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_name, container, false);
        bRegProceed = (Button) view.findViewById(R.id.bRegProceed);
//        etFirstName = (EditText) view.findViewById(R.id.etFirstName);
//        etLastName = (EditText) view.findViewById(R.id.etLastName);

        //Check text changes in EditText
        TextWatcher textWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {
                registrationProceed();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        };
        etFirstName.addTextChangedListener(textWatcher);
        etLastName.addTextChangedListener(textWatcher);


        return view;

    }

    //Method to check if users can proceed based on the results of the EditTexts
    public void registrationProceed() {
        if (etFirstName.getText().toString().isEmpty() || etLastName.getText().toString().isEmpty()) {
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
                    sFirstName = etFirstName.getText().toString();
                    sLastName = etLastName.getText().toString();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    RegisterEmailFragment registerEmailFragment = new RegisterEmailFragment();
                    fragmentTransaction.replace(R.id.container, registerEmailFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        }
    }


}
