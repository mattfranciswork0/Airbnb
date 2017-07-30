package com.example.toshiba.airbnb.Profile.BecomeAHost;

/**
 * Created by TOSHIBA on 30/07/2017.
 */


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toshiba.airbnb.R;


/**
 * Created by Owner on 2017-07-06.
 */

public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.BottomSheetViewHolder> {
    Context mContext;

    boolean propertyTypeBottomSheet;
    String[] propertyTypeArray;
    int propertyTypeSize;

    boolean totalGuestBottomSheet;
    int totalGuessSize;

    boolean bedRoomBottomSheet;
    int bedRoomSize;

    boolean bedBottomSheet;
    int bedSize;

    boolean kindOfBedBottomSheet;
    String[] kindOfBedArray;
    int kindOfBedSize;

    boolean bathroomBottomSheet;
    int bathroomSize = 16;

    //PropertyTypeFragment
    public void isPropertyType(String[] array) {
        propertyTypeArray = array;
        propertyTypeBottomSheet = true;
    }

    //GuestFragment
    public void isTotalGuest() {
        totalGuestBottomSheet = true;
    }

    public void isBedRoom() {
        bedRoomBottomSheet = true;
    }

    public void isBed() {
        bedBottomSheet = true;
    }

    public void isKindOfBed(String[] array) {
        kindOfBedArray = array;
        kindOfBedBottomSheet = true;
    }

    //BathroomFragment
    public void isBathroom() {
        bathroomBottomSheet = true;
    }

    @Override
    public BottomSheetAdapter.BottomSheetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bottom_sheet_adapter_item, parent, false);

        mContext = parent.getContext();
        return new BottomSheetAdapter.BottomSheetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BottomSheetAdapter.BottomSheetViewHolder holder, int position) {
        holder.bindView(position);
    }


    @Override
    public int getItemCount() {
        if (propertyTypeBottomSheet) {
            propertyTypeSize = propertyTypeArray.length;
            return propertyTypeArray.length;
        } else if (totalGuestBottomSheet) {
            totalGuessSize = 16;
            return totalGuessSize;
        } else if (bedRoomBottomSheet) {
            bedRoomSize = 11;
            return bedRoomSize;
        } else if (bedBottomSheet) {
            bedSize = 16;
            return bedSize;
        } else if (kindOfBedBottomSheet) {
            kindOfBedSize = kindOfBedArray.length;
            return kindOfBedArray.length;
        } else if (bathroomBottomSheet) {
            bathroomSize = 16;
            return bathroomSize;
        }
        return 0;
    }

    public class BottomSheetViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        int incrementByHalf;

        public BottomSheetViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }

        public void bindView(int position) {
            //PropertyTypeFragment
            if (propertyTypeBottomSheet) tv.setText(propertyTypeArray[position]);

                //GuestFragment
            else if (totalGuestBottomSheet) {
                if (position == 0) tv.setText(String.valueOf(position + 1) + " guest");
                else if (position == totalGuessSize - 1)
                    tv.setText(String.valueOf(position + 1) + "+ guests");
                else tv.setText(String.valueOf(position + 1) + " guests");


            } else if (bedRoomBottomSheet) {
                if (position == 0) tv.setText("Studio");
                else if (position == 1) tv.setText(String.valueOf(position) + " bedroom");
                else if (position == bedRoomSize - 1)
                    tv.setText(String.valueOf(position) + "+ bedrooms");
                else tv.setText(String.valueOf(position) + " bedrooms");

            } else if (bedBottomSheet) {
                if (position == 0) tv.setText(String.valueOf(position + 1) + " bed");
                else if (position == bedSize - 1)
                    tv.setText(String.valueOf(position + 1) + "+ beds");
                else tv.setText(String.valueOf(position + 1) + " beds");

            } else if (kindOfBedBottomSheet) {
                tv.setText(kindOfBedArray[position]);
            }

            //BathroomFragment
            else if (bathroomBottomSheet) {
                double val = position / 2.0;
                tv.setText(Double.toString(val) + " bathrooms");
            }

        }
    }

}
