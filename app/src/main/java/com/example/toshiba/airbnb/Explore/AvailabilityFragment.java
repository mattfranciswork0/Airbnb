package com.example.toshiba.airbnb.Explore;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toshiba.airbnb.Profile.BecomeAHost.BecomeAHostActivity;
import com.example.toshiba.airbnb.Profile.BecomeAHost.GetReady.BookingFragment;
import com.example.toshiba.airbnb.R;
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_availability, container, false);
        sharedPreferences = getActivity().getSharedPreferences(BookingFragment.BOOKING_SP, Context.MODE_PRIVATE);
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
        if (sharedPreferences.getString(BookingFragment.MAX_STAY, null) != null) {
            tvStay.setText("Requires a minimum stay of " + sharedPreferences.getString(BookingFragment.MIN_STAY, "") + " night(s) and " +
                    "a maximum stay of " + sharedPreferences.getString(BookingFragment.MAX_STAY, "") + " night(s).");
        } else {
            tvStay.setText("Requires a minimum stay of " + sharedPreferences.getString(BookingFragment.MIN_STAY, "") + " night(s).");
        }


        tvArriveAfter.setText("Arrive after " + sharedPreferences.getString(BookingFragment.ARRIVE_AFTER, ""));
        tvLeaveBefore.setText("Leave before " + sharedPreferences.getString(BookingFragment.LEAVE_BEFORE, ""));
        view.findViewById(R.id.bSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BecomeAHostActivity.PREVIEW_MODE) {//do nothing

                }
                getFragmentManager().popBackStack();
            }
        });

        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,
                Integer.parseInt(sharedPreferences.getString(BookingFragment.MAX_MONTH, "1")));
//        calendarView.setMaxDate(calendar.getTimeInMillis());
//        calendarView.setMinDate(System.currentTimeMillis());
        Date minDate = new Date(System.currentTimeMillis());
        Date maxDate = new Date(calendar.getTimeInMillis());

        final CaldroidFragment caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt("month", cal.get(Calendar.MONTH) + 1);
        args.putInt("year", cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);

        caldroidFragment.setMinDate(minDate);
        caldroidFragment.setMaxDate(maxDate);

        //Disable today's date
        disabledDates.add(minDate);
        caldroidFragment.setDisableDates(disabledDates);
        view.findViewById(R.id.bClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                caldroidFragment.clearSelectedDates();
                caldroidFragment.refreshView();
                tvCheckIn.setText("Check in");
                tvCheckOut.setText("Check out");
                firstDateClick = null;
                secondDateClick = null;
            }
        });
        caldroidFragment.setArguments(args);
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                if (firstDateClick == null) {
                    firstDateClick = date;
                    caldroidFragment.setSelectedDate(firstDateClick);
                    caldroidFragment.refreshView();
                    tvCheckIn.setText(simpleDateFormat.format(date));
                } else {

                    secondDateClick = date;
                    if (firstDateClick != null && secondDateClick != null) {
                        boolean maxAndMinProblem = false;
                        //if second date is before first date
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

                            Calendar maxCal = Calendar.getInstance();
                            maxCal.setTime(firstDateClick);
                            int maxStay = Integer.parseInt(sharedPreferences.getString(BookingFragment.MAX_STAY, "0"));
                            maxCal.add(Calendar.DATE, maxStay); //counting nights, not days
                            //if exceed max night
                            if (!(date.compareTo(maxCal.getTime()) < 0)) {
                                Toast.makeText(getActivity(), "Can only stay for " + sharedPreferences.getString(BookingFragment.MAX_STAY, "") + " nights"
                                        , Toast.LENGTH_LONG).show();
                                tvCheckOut.setText("Check out");
                                return;
                            }

//                            //if below min
                            Calendar minCal = Calendar.getInstance();
                            minCal.setTime(firstDateClick);
                            int minStay = Integer.parseInt(sharedPreferences.getString(BookingFragment.MIN_STAY, "0"));
                            minCal.add(Calendar.DATE, minStay - 1);
                            if (!(date.compareTo(minCal.getTime()) > 0)) {
                                Toast.makeText(getActivity(), "Requires a minimum stay of " + sharedPreferences.getString(BookingFragment.MIN_STAY, "3") + " nights"
                                        , Toast.LENGTH_LONG).show();
                                tvCheckOut.setText("Check out");
                                return;
                            }

                        }
                        caldroidFragment.clearSelectedDates();
                        caldroidFragment.setSelectedDates(firstDateClick, secondDateClick);
                        caldroidFragment.refreshView();
                        tvCheckOut.setText((simpleDateFormat.format(secondDateClick)));
                    }
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
}
