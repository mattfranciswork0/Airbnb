package com.example.toshiba.airbnb.Profile.BecomeAHost;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.PropertyTypeFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.PhotoFragment;
import com.example.toshiba.airbnb.R;


public class ProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        if(getIntent().getExtras().getBoolean(BecomeAHostActivity.BASIC_BUTTON))
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.progressFragment, new PropertyTypeFragment()).commit();
        else if(getIntent().getExtras().getBoolean(BecomeAHostActivity.SCENE_BUTTON))
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.progressFragment, new PhotoFragment()).commit();
    }
}
