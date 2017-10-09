package com.example.toshiba.airbnb.UserAuthentication;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.toshiba.airbnb.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by Owner on 2017-06-10.
 */

public class RegisterAgeFragment extends Fragment {
    EditText etAge;
    String date;
    Button bRegProceed;
    Calendar newDate;
    SimpleDateFormat simpleDateFormat;
    public static int sAge;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_age, container, false);
        etAge = (EditText) view.findViewById(R.id.etAge);
        bRegProceed = (Button) view.findViewById(R.id.bRegProceed);

        //DatePickerDialog
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -16);
        etAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        date = simpleDateFormat.format(newDate.getTime());
                        etAge.setText(date);
                    }

                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {
                registrationProceed();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        };
        etAge.addTextChangedListener(textWatcher);
        return view;
    }

    public void registrationProceed() {
        if (!(etAge.getText().toString().isEmpty())) {
            bRegProceed.setEnabled(true);
            bRegProceed.setBackgroundResource(R.drawable.reg_proceed_button);
            bRegProceed.setTextColor(Color.parseColor("#ff6666"));
            bRegProceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar today = Calendar.getInstance();

                    int diff = today.get(Calendar.YEAR) - newDate.get(Calendar.YEAR);
                    ;
                    //Use Jodatime for specific age difference
                    sAge = diff;
                    Log.d("blue", Integer.toString(sAge));
                    Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        } else {
            bRegProceed.setEnabled(false);
            bRegProceed.setBackgroundResource(R.drawable.reg_proceed_button_fail);
            bRegProceed.setTextColor(Color.parseColor("#ff6666"));
        }
    }

}
