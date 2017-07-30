package com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.toshiba.airbnb.Profile.BecomeAHost.BottomSheetFragment;
import com.example.toshiba.airbnb.R;


/**
 * Created by Owner on 2017-07-07.
 */

public class BathroomFragment extends Fragment {
    public static final String BATHROOM_BOTTOM_SHEET = "BATHROOM_BOTTOM_SHEET";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressBar basicProgressBar = (ProgressBar) getActivity().findViewById(R.id.basicProgressBar);
        basicProgressBar.setProgress(10);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bathroom, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.layoutBathroom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean(BATHROOM_BOTTOM_SHEET, true);
                bottomSheetFragment.setArguments(bundle);
                bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        view.findViewById(R.id.bNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.progressFragment, new LocationFragment()).addToBackStack(null).commit();
            }
        });
    }
}