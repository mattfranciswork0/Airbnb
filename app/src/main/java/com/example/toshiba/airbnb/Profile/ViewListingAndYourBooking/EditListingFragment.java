package com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cloudinary.Cloudinary;
import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.Explore.HomeDescActivity;
import com.example.toshiba.airbnb.Explore.HomeDescFragment;
import com.example.toshiba.airbnb.Explore.POJOListingData;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.AmenitiesItemFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.AmenitiesSpaceFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.GuestFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.LocationFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.GetReady.BookingFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.GetReady.HouseRuleFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.GetReady.PriceFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.DescribePlaceFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.GalleryFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.PhotoFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.TitleFragment;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.Util.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by TOSHIBA on 27/10/2017.
 */

public class EditListingFragment extends Fragment {
    public static String EDIT_LISTING = "EDIT_LISTING";
    public static String TITLE_FRAGMENT_INFO_FROM_DATABASE = "TITLE_FRAGMENT_FROM_DATABASE";
    public static String DESCRIPTION_PLACE_FRAGMENT_INFO_FROM_DATABASE = "DESCRIPTION_PLACE_FRAGMENT_INFO_FROM_DATABASE";
    public static String PRICE_FRAGMENT_INFO_FROM_DATABASE = "LOCATION_FRAGMENT_INFO_FROM_DATABASE";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_listing, container, false);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ImageView ivListingPic = (ImageView) view.findViewById(R.id.ivListingPic);
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
        DatabaseInterface retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();
        retrofit.getListingData(getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID))
                .enqueue(new Callback<POJOListingData>() {
                    @Override
                    public void onResponse(Call<POJOListingData> call, final Response<POJOListingData> response) {

                        Cloudinary cloudinary = new Cloudinary(getActivity().getResources().getString(R.string.cloudinaryEnviornmentVariable));
                        Glide.with(getActivity()).load(cloudinary.url().generate(response.body().getImageData().get(0).getImagePath()))
                                .into(ivListingPic);
                        Log.d("imSorry", "im sad");
                        view.findViewById(R.id.tvPreview).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), HomeDescActivity.class);
                                intent.putExtra(ViewListingAndYourBookingAdapter.LISTING_ID, getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID) );
                                getActivity().startActivity(intent);

                            }
                        });
                        view.findViewById(R.id.ivListingPic).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                GalleryFragment galleryFragment = new GalleryFragment();
                                Bundle bundle = new Bundle();
                                bundle.putInt(ViewListingAndYourBookingAdapter.LISTING_ID, getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID));
                                galleryFragment.setArguments(bundle);
                                fragmentTransaction.hide(EditListingFragment.this);
                                fragmentTransaction.add(R.id.rootLayout, galleryFragment).addToBackStack(EDIT_LISTING).commit();
                                fragmentTransaction.show(galleryFragment);
                            }
                        });

                        view.findViewById(R.id.titleLayout).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                TitleFragment titleFragment = new TitleFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString(TITLE_FRAGMENT_INFO_FROM_DATABASE, response.body().getPlaceTitle());
                                bundle.putInt(ViewListingAndYourBookingAdapter.LISTING_ID, getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID));
                                titleFragment.setArguments(bundle);
//                                fragmentTransaction.hide(EditListingFragment.this);
                                fragmentTransaction.replace(R.id.rootLayout, titleFragment).addToBackStack(null).commit();
//                                fragmentTransaction.show(titleFragment);
                            }
                        });

                        TextView tvTitleDesc = (TextView) view.findViewById(R.id.tvTitleDesc);
                        tvTitleDesc.setText(response.body().getPlaceTitle());

                        view.findViewById(R.id.tvDescripton).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                DescribePlaceFragment describePlaceFragment = new DescribePlaceFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString(DESCRIPTION_PLACE_FRAGMENT_INFO_FROM_DATABASE, response.body().getPlaceDescription());
                                bundle.putInt(ViewListingAndYourBookingAdapter.LISTING_ID, getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID));
                                describePlaceFragment.setArguments(bundle);
                                fragmentTransaction.hide(EditListingFragment.this);
                                fragmentTransaction.add(R.id.rootLayout, describePlaceFragment).addToBackStack(null).commit();
                                fragmentTransaction.show(describePlaceFragment);

                            }
                        });

                        view.findViewById(R.id.tvRoomAndGuest).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                GuestFragment guestFragment = new GuestFragment();
                                Bundle bundle = new Bundle();
                                bundle.putInt(ViewListingAndYourBookingAdapter.LISTING_ID, getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID));
                                guestFragment.setArguments(bundle);
                                fragmentTransaction.hide(EditListingFragment.this);
                                fragmentTransaction.add(R.id.rootLayout, guestFragment).addToBackStack(null).commit();
                                fragmentTransaction.show(guestFragment);
                            }
                        });

                        view.findViewById(R.id.tvItemAmenities).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                AmenitiesItemFragment amenitiesItemFragment = new AmenitiesItemFragment();
                                Bundle bundle = new Bundle();
                                bundle.putInt(ViewListingAndYourBookingAdapter.LISTING_ID, getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID));
                                amenitiesItemFragment.setArguments(bundle);
                                fragmentTransaction.hide(EditListingFragment.this);
                                fragmentTransaction.add(R.id.rootLayout, amenitiesItemFragment).addToBackStack(null).commit();
                                fragmentTransaction.show(amenitiesItemFragment);
                            }
                        });

                        view.findViewById(R.id.tvSpaceAmenities).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                AmenitiesSpaceFragment amenitiesSpaceFragment = new AmenitiesSpaceFragment();
                                Bundle bundle = new Bundle();
                                bundle.putInt(ViewListingAndYourBookingAdapter.LISTING_ID, getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID));
                                amenitiesSpaceFragment.setArguments(bundle);
                                fragmentTransaction.hide(EditListingFragment.this);
                                fragmentTransaction.add(R.id.rootLayout, amenitiesSpaceFragment).addToBackStack(null).commit();
                                fragmentTransaction.show(amenitiesSpaceFragment);
                            }
                        });

                        view.findViewById(R.id.tvLocation).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                LocationFragment locationFragment = new LocationFragment();
                                Bundle bundle = new Bundle();
                                bundle.putInt(ViewListingAndYourBookingAdapter.LISTING_ID, getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID));
                                locationFragment.setArguments(bundle);
                                fragmentTransaction.hide(EditListingFragment.this);
                                fragmentTransaction.add(R.id.rootLayout, locationFragment).addToBackStack(null).commit();
                                fragmentTransaction.show(locationFragment);
                            }
                        });

                        view.findViewById(R.id.tvHouseRules).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                HouseRuleFragment houseRuleFragment = new HouseRuleFragment();
                                Bundle bundle = new Bundle();
                                bundle.putInt(ViewListingAndYourBookingAdapter.LISTING_ID, getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID));
                                houseRuleFragment.setArguments(bundle);
                                fragmentTransaction.hide(EditListingFragment.this);
                                fragmentTransaction.add(R.id.rootLayout, houseRuleFragment).addToBackStack(null).commit();
                                fragmentTransaction.show(houseRuleFragment);
                            }
                        });

                        view.findViewById(R.id.tvBooking).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                BookingFragment bookingFragment = new BookingFragment();
                                Bundle bundle = new Bundle();
                                bundle.putInt(ViewListingAndYourBookingAdapter.LISTING_ID,  getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID));
                                bookingFragment.setArguments(bundle);
                                fragmentTransaction.hide(EditListingFragment.this);
                                fragmentTransaction.add(R.id.rootLayout, bookingFragment).addToBackStack(null).commit();
                                fragmentTransaction.show(bookingFragment);
                            }
                        });

                        view.findViewById(R.id.tvPrice).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                PriceFragment priceFragment = new PriceFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString(PRICE_FRAGMENT_INFO_FROM_DATABASE,  response.body().getPrice());
                                bundle.putInt(ViewListingAndYourBookingAdapter.LISTING_ID, getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID));
                                priceFragment.setArguments(bundle);
                                fragmentTransaction.hide(EditListingFragment.this);
                                fragmentTransaction.add(R.id.rootLayout, priceFragment).addToBackStack(null).commit();
                                fragmentTransaction.show(priceFragment);
                            }
                        });


                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<POJOListingData> call, Throwable t) {
                        Log.d("imSorry", t.toString());
                    }
                });
    }
}
