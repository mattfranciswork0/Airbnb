package com.example.toshiba.airbnb.Profile.BecomeAHost;

/**
 * Created by TOSHIBA on 30/07/2017.
 */


import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.toshiba.airbnb.Explore.HomeDescActivity;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.MapFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.PropertyTypeFragment;
import com.example.toshiba.airbnb.R;


public class BecomeAHostActivity extends AppCompatActivity {
    public final static String BASIC_BUTTON = "BASIC_BUTTON";
    public final static String SCENE_BUTTON = "SCENE_BUTTON";
    public final static String GET_READY_BUTTON = "GET_READY_BUTTON";
    public final static boolean PREVIEW_MODE = true;//turn this to false after user has done listing their place

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_a_host);
        //TODO: TRACK PROGRESS WITH SHAREDPREFERENCES
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getBoolean(MapFragment.BASIC_QUESTIONS_COMPLETED)) {
                findViewById(R.id.sceneButton).setVisibility(View.VISIBLE);
                findViewById(R.id.sceneView).setVisibility(View.VISIBLE);
            }
        }


        final PropertyTypeFragment propertyTypeFragment = new PropertyTypeFragment();
        Button basicButton = (Button) findViewById(R.id.basicButton);

        basicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BecomeAHostActivity.this, ProgressActivity.class);
                intent.putExtra(BASIC_BUTTON, true);
                startActivity(intent);

            }
        });

        findViewById(R.id.sceneButton).setOnClickListener(new View.OnClickListener() {
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
