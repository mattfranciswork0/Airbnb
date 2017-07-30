package com.example.toshiba.airbnb.Profile.BecomeAHost;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.BathroomFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.GuestFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.PropertyTypeFragment;
import com.example.toshiba.airbnb.R;

import org.w3c.dom.Text;

/**
 * Created by Owner on 2017-07-06.
 */

public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.BottomSheetViewHolder> {
    Context mContext;
    BottomSheetFragment bottomSheetFragment;

    boolean propertyTypeBottomSheet;
    String[] propertyTypeArray;
    int propertyTypeSize;

    boolean totalGuestBottomSheet;
    int totalGuestSize;

    boolean bedRoomBottomSheet;
    int bedRoomSize;

    boolean bedBottomSheet;
    int bedSize;

    boolean kindOfBedBottomSheet;
    String[] kindOfBedArray;
    int kindOfBedSize;

    boolean bathroomBottomSheet;
    int bathroomSize = 16;

    public BottomSheetAdapter(BottomSheetFragment bottomSheetFragment) {
        this.bottomSheetFragment = bottomSheetFragment;
    }

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
            totalGuestSize = 16;
            return totalGuestSize;
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

        public BottomSheetViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }

        public void bindView(final int position) {
            //PropertyTypeFragment
            if (propertyTypeBottomSheet) {
                tv.setText(propertyTypeArray[position]);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView input = (TextView) PropertyTypeFragment.mView.findViewById(R.id.tvTypeInput);
                        input.setText(propertyTypeArray[position]);
                        bottomSheetFragment.dismiss();
                    }
                });
            }

            //GuestFragment
            else if (totalGuestBottomSheet) {
                final TextView input = (TextView) GuestFragment.mView.findViewById(R.id.tvtotalnput);
                if (position == 0) {
                    tv.setText(String.valueOf(position + 1) + " guest");
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            input.setText(String.valueOf(position + 1) + " guest");
                            bottomSheetFragment.dismiss();
                        }
                    });
                }
//                else if
//                    (position == totalGuestSize - 1){
//                    tv.setText(String.valueOf(position + 1) + "+ guests");
//                    tv.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            guessFragment.setText(String.valueOf(position + 1) + "+ guests");
//                        }
//                    });
//                }
                else {
                    tv.setText(String.valueOf(position + 1) + " guests");
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            input.setText(String.valueOf(position + 1) + "+ guests");
                        }
                    });
                }


            } else if (bedRoomBottomSheet) {
                final TextView input = (TextView) GuestFragment.mView.findViewById(R.id.tvBedRoomInput);
                if (position == 0) {
                    tv.setText("Studio");
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            input.setText("Studio");
                            bottomSheetFragment.dismiss();
                        }
                    });
                } else if (position == 1) {
                    tv.setText(String.valueOf(position) + " bedroom");
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            input.setText(String.valueOf(position) + " bedroom");
                            bottomSheetFragment.dismiss();
                        }
                    });
                } else if (position == bedRoomSize - 1) {
                    tv.setText(String.valueOf(position) + "+ bedrooms");
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            input.setText(String.valueOf(position) + "+ bedrooms");
                            bottomSheetFragment.dismiss();
                        }
                    });
                } else {
                    tv.setText(String.valueOf(position) + " bedrooms");
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            input.setText(String.valueOf(position) + " bedrooms");
                            bottomSheetFragment.dismiss();
                        }
                    });
                }

            } else if (bedBottomSheet) {
                final TextView input = (TextView) GuestFragment.mView.findViewById(R.id.tvBedInput);
                if (position == 0) {
                    tv.setText(String.valueOf(position + 1) + " bed");
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            input.setText(String.valueOf(position + 1) + " bed");
                            bottomSheetFragment.dismiss();
                        }
                    });
                } else if (position == bedSize - 1) {
                    tv.setText(String.valueOf(position + 1) + "+ beds");
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            input.setText(String.valueOf(position + 1) + "+ beds");
                            bottomSheetFragment.dismiss();
                        }
                    });
                } else {
                    tv.setText(String.valueOf(position + 1) + " beds");
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            input.setText(String.valueOf(position + 1) + " beds");
                            bottomSheetFragment.dismiss();
                        }
                    });
                }

            } else if (kindOfBedBottomSheet) {
                tv.setText(kindOfBedArray[position]);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView input = (TextView) GuestFragment.mView.findViewById(R.id.tvKindOfBedInput);
                        input.setText(kindOfBedArray[position]);
                        bottomSheetFragment.dismiss();
                    }
                });

            }

            //BathroomFragment
            else if (bathroomBottomSheet) {
                final double val = position / 2.0;
                tv.setText(Double.toString(val) + " bathrooms");
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView input = (TextView) BathroomFragment.mView.findViewById(R.id.tvBathroomInput);
                        input.setText(Double.toString(val) + " bathrooms");
                        bottomSheetFragment.dismiss();
                    }
                });
            }


        }
    }

}
