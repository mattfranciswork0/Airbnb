package com.example.toshiba.airbnb.Explore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.toshiba.airbnb.R;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;


public class SingleContentDescActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_content_desc);
        getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, new SingleContentDescFragment()).commit();

    }
}
