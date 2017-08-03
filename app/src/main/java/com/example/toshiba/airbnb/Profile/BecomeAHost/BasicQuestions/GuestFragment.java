package com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions;

/**
 * Created by TOSHIBA on 30/07/2017.
 */


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.toshiba.airbnb.Profile.BecomeAHost.BottomSheetFragment;
import com.example.toshiba.airbnb.R;


/**
 * Created by Owner on 2017-07-06.
 */

public class GuestFragment extends Fragment {
    public static final String TOTAL_GUEST_BOTTOM_SHEET = "TOTAL_GUEST_BOTTOM_SHEET";
    public static final String BED_ROOM_BOTTOM_SHEET = "BED_ROOM_BOTTOM_SHEET";
    public static final String BED_BOTTOM_SHEET = "BED_BOTTOM_SHEET";
    public static final String KIND_OF_BED_BOTTOM_SHEET = "KIND_OF_BED_BOTTOM_SHEET";
    public static View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressBar basicProgressBar = (ProgressBar) getActivity().findViewById(R.id.basicProgressBar);
        basicProgressBar.setProgress(40);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guest, container, false);
        mView = view;
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.mView = view;
        view.findViewById(R.id.layoutTotalGuest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean(TOTAL_GUEST_BOTTOM_SHEET, true);
                bottomSheetFragment.setArguments(bundle);
                bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        view.findViewById(R.id.layoutBedroom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean(BED_ROOM_BOTTOM_SHEET, true);
                bottomSheetFragment.setArguments(bundle);
                bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        view.findViewById(R.id.layoutbed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean(BED_BOTTOM_SHEET, true);
                bottomSheetFragment.setArguments(bundle);
                bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        view.findViewById(R.id.layoutKindofBed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean(KIND_OF_BED_BOTTOM_SHEET, true);
                bottomSheetFragment.setArguments(bundle);
                bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        view.findViewById(R.id.bContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.progressFragment, new BathroomFragment()).addToBackStack(null).commit();
            }
        });

    }

}
