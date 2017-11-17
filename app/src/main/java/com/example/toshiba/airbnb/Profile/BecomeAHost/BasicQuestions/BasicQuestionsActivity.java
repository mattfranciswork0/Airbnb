package com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.toshiba.airbnb.R;


public class BasicQuestionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_questions);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.progressFragment, new PropertyTypeFragment()).commit();

    }
}
