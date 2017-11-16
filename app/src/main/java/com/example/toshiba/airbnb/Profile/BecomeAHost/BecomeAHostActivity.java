package com.example.toshiba.airbnb.Profile.BecomeAHost;

/**
 * Created by TOSHIBA on 30/07/2017.
 */


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.toshiba.airbnb.Explore.Homes.HomeDescActivity;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.AmenitiesSpaceFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.GetReady.PublishFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.TitleFragment;
import com.example.toshiba.airbnb.R;


public class BecomeAHostActivity extends AppCompatActivity {
    public final static String BASIC_BUTTON = "BASIC_BUTTON";
    public final static String SCENE_BUTTON = "SCENE_BUTTON";
    public final static String GET_READY_BUTTON = "GET_READY_BUTTON";
    public static boolean PREVIEW_MODE = true;//turn this to false after user has done listing their place

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_a_host);


        //TODO: TRACK PROGRESS WITH SHAREDPREFERENCES
        SharedPreferences progressSP = getSharedPreferences(ProgressActivity.PROGRESS_SP, Context.MODE_PRIVATE);
        if (progressSP.contains(AmenitiesSpaceFragment.AMENITIES_SPACE_FRAGMENT_FINISHED)) {
            findViewById(R.id.bScene).setVisibility(View.VISIBLE);;
            TextView tvSceneTitle = (TextView) findViewById(R.id.tvSceneTitle);
            tvSceneTitle.setTextColor(ContextCompat.getColor(this, android.R.color.tab_indicator_text));
            TextView tvSceneDesc = (TextView) findViewById(R.id.tvSceneDesc);
            tvSceneDesc.setTextColor(ContextCompat.getColor(this, android.R.color.tab_indicator_text));
        }


        if(progressSP.contains(TitleFragment.TITLE_FRAGMENT_FINISHED)){
            findViewById(R.id.bGetReady).setVisibility(View.VISIBLE);;
            TextView tvReadyTitle = (TextView) findViewById(R.id.tvReadyTitle);
            tvReadyTitle.setTextColor(ContextCompat.getColor(this, android.R.color.tab_indicator_text));
            TextView tvReadyDesc = (TextView) findViewById(R.id.tvReadyDesc);
            tvReadyDesc.setTextColor(ContextCompat.getColor(this, android.R.color.tab_indicator_text));
        }
        if(progressSP.contains(PublishFragment.PUBLISH_FRAGMENT_FINISHED)){
            BecomeAHostActivity.PREVIEW_MODE = false;
        }


        Button basicButton = (Button) findViewById(R.id.bBasic);

        basicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BecomeAHostActivity.this, ProgressActivity.class);
                intent.putExtra(BASIC_BUTTON, true);
                startActivity(intent);

            }
        });

        findViewById(R.id.bScene).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BecomeAHostActivity.this, ProgressActivity.class);
                intent.putExtra(SCENE_BUTTON, true);
                startActivity(intent);
            }
        });

        findViewById(R.id.bPreview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BecomeAHostActivity.this, HomeDescActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.bGetReady).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BecomeAHostActivity.this, ProgressActivity.class);
                intent.putExtra(GET_READY_BUTTON, true);
                startActivity(intent);
            }
        });

    }
}
