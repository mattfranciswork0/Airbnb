package com.example.toshiba.airbnb.Profile.BecomeAHost;

/**
 * Created by TOSHIBA on 30/07/2017.
 */


import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.BathroomFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.GuestFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.PropertyTypeFragment;
import com.example.toshiba.airbnb.R;


/**
 * Created by Owner on 2017-07-05.
 */
public class BottomSheetFragment extends BottomSheetDialogFragment {
    public String[] propertyTypes = new String[]{"Flat", "House", "Bed & Breakfast", "Loft", "Cottage", "Villa", "Castle","Dorm", "Treehouse", "Boat", "Plane", "Camper/RV",
            "Igloo", "Lighthouse", "Yurt", "Tipi", "Cave", "Island", "Chalet", "Earthhouse", "Hut", "Train", "Tent", "Other"};

    public String[] kindOfBeds = new String[]{"Real bed", "Pull-out Sofa", "Airbed", "Futon", "Sofa"};

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View view =  View.inflate(getContext(), R.layout.fragment_bottom_sheet, null);
        view.findViewById(R.id.recyclerView);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //PropertyTypeFragment
        if(getArguments().getBoolean(PropertyTypeFragment.PROPERTY_TYPE_BOTTOM_SHEET)) {
            BottomSheetAdapter bottomSheetAdapter = new BottomSheetAdapter(BottomSheetFragment.this);
            bottomSheetAdapter.isPropertyType(propertyTypes);
            recyclerView.setAdapter(bottomSheetAdapter);
            dialog.setContentView(view);
        }

//        GuessFragment
        if(getArguments().getBoolean(GuestFragment.TOTAL_GUEST_BOTTOM_SHEET)){
            BottomSheetAdapter bottomSheetAdapter = new BottomSheetAdapter(BottomSheetFragment.this);
            bottomSheetAdapter.isTotalGuest();
            recyclerView.setAdapter(bottomSheetAdapter);
            dialog.setContentView(view);
        }

        if(getArguments().getBoolean(GuestFragment.BED_ROOM_BOTTOM_SHEET)){
            BottomSheetAdapter bottomSheetAdapter = new BottomSheetAdapter(BottomSheetFragment.this);
            bottomSheetAdapter.isBedRoom();
            recyclerView.setAdapter(bottomSheetAdapter);
            dialog.setContentView(view);
        }

        if(getArguments().getBoolean(GuestFragment.BED_BOTTOM_SHEET)){
            BottomSheetAdapter bottomSheetAdapter = new BottomSheetAdapter(BottomSheetFragment.this);
            bottomSheetAdapter.isBed();
            recyclerView.setAdapter(bottomSheetAdapter);
            dialog.setContentView(view);
        }


        //Bathroom Fragment
        if(getArguments().getBoolean(BathroomFragment.BATHROOM_BOTTOM_SHEET)){
            BottomSheetAdapter bottomSheetAdapter = new BottomSheetAdapter(BottomSheetFragment.this);
            bottomSheetAdapter.isBathroom();
            recyclerView.setAdapter(bottomSheetAdapter);
            dialog.setContentView(view);
        }




    }
}
