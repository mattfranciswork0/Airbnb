package com.example.toshiba.airbnb.Explore;

/**
 * Created by TOSHIBA on 30/07/2017.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toshiba.airbnb.R;

import java.util.Calendar;


/**
 * Created by Owner on 2017-07-05.
 */

public class SingleContentDescFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_content_desc, container, false);
        view.findViewById(R.id.ivHost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.rootLayout, new HostProfileFragment()).commit();
            }
        });

        view.findViewById(R.id.tvHostName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.rootLayout, new HostProfileFragment()).commit();
            }
        });


        CalendarView calendarItem = (CalendarView) view.findViewById(R.id.calendarItem);
        Calendar calendarMax = Calendar.getInstance();
        calendarMax.add(Calendar.MONTH, 4);
        calendarItem.setMaxDate(calendarMax.getTimeInMillis());
        Calendar calendarMin = Calendar.getInstance();
        calendarItem.setMinDate(calendarMin.getTimeInMillis());

        return view;
    }
}
