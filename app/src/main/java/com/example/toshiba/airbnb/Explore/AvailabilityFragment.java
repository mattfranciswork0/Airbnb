package com.example.toshiba.airbnb.Explore;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BecomeAHostActivity;
import com.example.toshiba.airbnb.Profile.BecomeAHost.GetReady.BookingFragment;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.ViewListingAndYourBookingAdapter;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.UserAuthentication.SessionManager;
import com.example.toshiba.airbnb.Util.RetrofitUtil;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by TOSHIBA on 09/08/2017.
 */


public class AvailabilityFragment extends Fragment {
    Date firstDateClick;
    Date secondDateClick;
    ArrayList<Date> disabledDates = new ArrayList<>();
    SharedPreferences sharedPreferences;
    TextView tvCheckIn;
    TextView tvCheckOut;
    DatabaseInterface retrofit;
    View view;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static String SMS_FRAGMENT = "SMS_FRAGMENT";


    Button bSave;


    public void disableSaveButton(){
        bSave.setBackgroundResource(R.drawable.reg_host_proceed_button_fail);
        bSave.setOnClickListener(null);
    }

    public static Date dateStringToDateObject(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date parsedDate;
        try {
            parsedDate = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            parsedDate = null;
            e.printStackTrace();
        }
        return parsedDate;
    }

    public void getDaysBetweenDates(Date checkInParsed, Date checkOutParsed) {
        Calendar firstCal = Calendar.getInstance();
        Calendar secondCal = Calendar.getInstance();
        firstCal.setTime(checkInParsed);
        secondCal.setTime(checkOutParsed);

        while (!firstCal.after(secondCal)) {
            disabledDates.add(firstCal.getTime());
            firstCal.add(Calendar.DATE, 1);
        }

    }

    public void setUpCalendar(String maxMonth, final String maxStay, final String minStay,
                              ArrayList<String> checkInArrayList, ArrayList<String> checkOutArrayList) {


        final Calendar maxCal = Calendar.getInstance();
        maxCal.add(Calendar.MONTH,
                Integer.parseInt(maxMonth));


        final CaldroidFragment caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt("month", cal.get(Calendar.MONTH) + 1);
        args.putInt("year", cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);
        Date minDate = new Date(System.currentTimeMillis());
        final Date maxDate = new Date(maxCal.getTimeInMillis());
        caldroidFragment.setMinDate(minDate);
        caldroidFragment.setMaxDate(maxDate);

        //Disable today's date
        if (!checkInArrayList.isEmpty() && !checkOutArrayList.isEmpty()) {
            for(int i = 0; i < checkInArrayList.size(); i++){

                getDaysBetweenDates(dateStringToDateObject(checkInArrayList.get(i)),
                        dateStringToDateObject(checkOutArrayList.get(i)));
            }

        }

        disabledDates.add(minDate);

        caldroidFragment.setDisableDates(disabledDates);
        view.findViewById(R.id.bClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                caldroidFragment.clearSelectedDates();
                caldroidFragment.refreshView();
                tvCheckIn.setText("Check In");
                tvCheckOut.setText("Check Out");
                firstDateClick = null;
                secondDateClick = null;
                bSave.setBackgroundResource(R.drawable.reg_host_proceed_button_fail);
                bSave.setOnClickListener(null);
                caldroidFragment.setMaxDate(maxDate);
            }
        });


        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                if (firstDateClick == null) {
                    firstDateClick = date;

                    Calendar firstCalendar = Calendar.getInstance();
                    Calendar secondCalendar = Calendar.getInstance();
                    firstCalendar.setTime(firstDateClick);
                    for(Date disabledDate : disabledDates){
                        secondCalendar.setTime(disabledDate);
                        if(firstCalendar.getTime().before(secondCalendar.getTime())){
                            caldroidFragment.setMaxDate(secondCalendar.getTime());
                        }
                    }


                    caldroidFragment.setSelectedDate(firstDateClick);
                    caldroidFragment.refreshView();
                    tvCheckIn.setText(simpleDateFormat.format(date));
                } else {

                    secondDateClick = date;
                    if (firstDateClick != null && secondDateClick != null) {
                        if (secondDateClick.compareTo(firstDateClick) < 0) {
//                    Returns the value 0 if the argument Date is equal to this Date;
//                    a value less than 0 if this Date is before the Date argument;
//                    and a value greater than 0 if this Date is after the Date argument.
                            caldroidFragment.clearSelectedDates();
                            caldroidFragment.setSelectedDate(secondDateClick);
                            caldroidFragment.refreshView();

                            tvCheckIn.setText(simpleDateFormat.format(secondDateClick));

                            tvCheckOut.setText("Check Out");
                            firstDateClick = secondDateClick;
                            secondDateClick = null;
                            return;

                        } else {


                            //counting nights, not days
                            //if exceed max night

                            if (maxStay != null) {
                                Calendar maxCal = Calendar.getInstance();
                                maxCal.setTime(firstDateClick);
                                maxCal.add(Calendar.DATE, Integer.parseInt(maxStay));
                                if (!(date.compareTo(maxCal.getTime()) < 0)) {
                                    Toast.makeText(getActivity(), "Can only stay for " + maxStay + " night(s)"
                                            , Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }

//                            //if below min
                            if(minStay != null) {
                                Calendar minCal = Calendar.getInstance();
                                minCal.setTime(firstDateClick);
                                minCal.add(Calendar.DATE, Integer.parseInt(minStay) - 1);
                                if (!(date.compareTo(minCal.getTime()) >= 0)) {
                                    Toast.makeText(getActivity(), "Requires a minimum stay of " + minStay + " night(s)"
                                            , Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }

                        }
                        caldroidFragment.clearSelectedDates();
                        caldroidFragment.setSelectedDates(firstDateClick, secondDateClick);
                        caldroidFragment.refreshView();
                        tvCheckOut.setText((simpleDateFormat.format(secondDateClick)));
                    }
                }

                final int listingId = getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID, 1);
                SharedPreferences sessionSP = getActivity().getSharedPreferences(SessionManager.SESSION_SP, Context.MODE_PRIVATE);
                int listingUserId = getArguments().getInt(SessionManager.USER_ID, 1);
                final int userId = sessionSP.getInt(SessionManager.USER_ID, 0);

                //if user clicked his own listing
                //TODO: CHANGE THIS TO !=, but for testing purposes, leave it.
                if (listingUserId
                        == userId) {
                    if (!tvCheckIn.getText().toString().equals("Check In") && !tvCheckOut.getText()
                            .toString().equals("Check Out")) {

                        bSave.setBackgroundResource(R.drawable.reg_host_proceed_button);


                        bSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                Bundle bundle = new Bundle();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                                bundle.putString(HomeDescFragment.CHECK_IN, sdf.format(tvCheckIn.getText().toString()));
                                bundle.putString(HomeDescFragment.CHECK_OUT, sdf.format(tvCheckOut.getText().toString()));
                                bundle.putInt(ViewListingAndYourBookingAdapter.LISTING_ID, listingId);
                                BookingSendSMSFragment bookingSendSMSFragment = new BookingSendSMSFragment();
                                bookingSendSMSFragment.setArguments(bundle);
                                fragmentTransaction.add(R.id.homeDescLayout, bookingSendSMSFragment).commit();
                                fragmentTransaction.addToBackStack(SMS_FRAGMENT);
                                fragmentTransaction.hide(AvailabilityFragment.this);

                            }
                        });
                    } else {
                        disableSaveButton();
                    }
                } else{
//                    bSave.setVisibility(View.GONE);


                }
            }

            @Override
            public void onChangeMonth(int month, int year) {
            }

            @Override
            public void onLongClickDate(Date date, View view) {
            }

            @Override
            public void onCaldroidViewCreated() {
            }

        };

        caldroidFragment.setCaldroidListener(listener);

        getChildFragmentManager().beginTransaction().replace(R.id.calender_container, caldroidFragment).commit();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_availability, container, false);
        sharedPreferences = getActivity().getSharedPreferences(BookingFragment.BOOKING_SP, Context.MODE_PRIVATE);
        retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvCheckIn = (TextView) view.findViewById(R.id.tvCheckIn);
        tvCheckOut = (TextView) view.findViewById(R.id.tvCheckOut);
        TextView tvArriveAfter = (TextView) view.findViewById(R.id.tvArriveAfter);
        TextView tvLeaveBefore = (TextView) view.findViewById(R.id.tvLeaveBefore);
        TextView tvStay = (TextView) view.findViewById(R.id.tvStay);
        bSave = (Button) view.findViewById(R.id.bSave);


        if (getArguments() != null) {
            if (getArguments().containsKey(HomeDescFragment.AVAILABILITY_FROM_DATABASE)) {
                ArrayList<String> checkIn;
                ArrayList<String> checkOut;
                if (getArguments().containsKey(HomeDescFragment.CHECK_IN)
                        && getArguments().containsKey(HomeDescFragment.CHECK_OUT)) {
                    checkIn = getArguments().getStringArrayList(HomeDescFragment.CHECK_IN);
                    checkOut = getArguments().getStringArrayList(HomeDescFragment.CHECK_OUT);
                } else {
                    checkIn = null;
                    checkOut = null;
                }
                Log.d("hiMatt", checkIn + " " + checkOut);

                setUpCalendar(
                        getArguments().getString(BookingFragment.MAX_MONTH, "1"),
                        getArguments().getString(BookingFragment.MAX_STAY, null),
                        getArguments().getString(BookingFragment.MIN_STAY, null),
                        checkIn,
                        checkOut);


                if (getArguments().getString(BookingFragment.MAX_STAY, null) != null) {
                    tvStay.setText("Requires a minimum stay of " + getArguments().getString(BookingFragment.MIN_STAY, "") + " night(s) and " +
                            "a maximum stay of " + getArguments().getString(BookingFragment.MAX_STAY, null) + " night(s).");
                } else {
                    tvStay.setText("Requires a minimum stay of " + getArguments().getString(BookingFragment.MIN_STAY, "") + " night(s).");
                }

                tvArriveAfter.setText("Arrive after " + getArguments().getString(BookingFragment.ARRIVE_AFTER, ""));
                tvLeaveBefore.setText("Leave before " + getArguments().getString(BookingFragment.LEAVE_BEFORE, ""));




            }
        } else {
            Log.d("Love", "married");
            bSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (BecomeAHostActivity.PREVIEW_MODE) {//do nothing

                    }
                    getFragmentManager().beginTransaction().hide(AvailabilityFragment.this);
                }
            });

            Log.d("Love", "else statement");
            setUpCalendar(sharedPreferences.getString(BookingFragment.MAX_MONTH, "1"),
                    sharedPreferences.getString(BookingFragment.MAX_STAY, "0"),
                    sharedPreferences.getString(BookingFragment.MIN_STAY, "3"),
                    null,
                    null

            );


            if (sharedPreferences.getString(BookingFragment.MAX_STAY, null) != null) {
                tvStay.setText("Requires a minimum stay of " + sharedPreferences.getString(BookingFragment.MIN_STAY, "") + " night(s) and " +
                        "a maximum stay of " + sharedPreferences.getString(BookingFragment.MAX_STAY, "") + " night(s).");
            } else {
                tvStay.setText("Requires a minimum stay of " + sharedPreferences.getString(BookingFragment.MIN_STAY, "") + " night(s).");
            }
        }
    }
}
