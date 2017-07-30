package com.example.toshiba.airbnb.Explore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CalendarView;

import com.example.toshiba.airbnb.R;

import java.util.Calendar;



public class SingleContentDescActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_content_desc);

        CalendarView calendarItem = (CalendarView) findViewById(R.id.calendarItem);

        Calendar calendarMax = Calendar.getInstance();
        calendarMax.add(Calendar.MONTH, 4);
        calendarItem.setMaxDate(calendarMax.getTimeInMillis());

        Calendar calendarMin = Calendar.getInstance();
        calendarItem.setMinDate(calendarMin.getTimeInMillis());
    }
}
