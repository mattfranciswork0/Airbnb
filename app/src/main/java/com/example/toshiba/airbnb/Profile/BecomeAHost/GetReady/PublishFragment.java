package com.example.toshiba.airbnb.Profile.BecomeAHost.GetReady;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.toshiba.airbnb.Explore.MenuActivity;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BecomeAHostActivity;
import com.example.toshiba.airbnb.R;

/**
 * Created by TOSHIBA on 11/08/2017.
 */

public class PublishFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressBar basicProgressBar = (ProgressBar) getActivity().findViewById(R.id.basicProgressBar);
        basicProgressBar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.lightRed));
        basicProgressBar.setProgress(100);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publish, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.bMakeChanges).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        view.findViewById(R.id.bPublish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MenuActivity.class);
                intent.putExtra("BASIC_QUESTIONS_COMPLETED", true);
                startActivity(intent);
            }
        });
    }
}
