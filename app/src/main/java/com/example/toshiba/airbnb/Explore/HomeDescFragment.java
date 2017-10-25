package com.example.toshiba.airbnb.Explore;

/**
 * Created by TOSHIBA on 02/08/2017.
 */


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.AmenitiesIconMoreFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.AmenitiesItemFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.BathroomFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.GuestFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.LocationFilterAdapter;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.PropertyTypeFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.GetReady.BookingFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.DescribePlaceFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.GalleryAdapter;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.PhotoDescFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.TitleFragment;
import com.example.toshiba.airbnb.Profile.HostProfileViewFragment;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.ViewListingAndYourBookingAdapter;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.UserAuthentication.SessionManager;
import com.example.toshiba.airbnb.Util.RetrofitUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Owner on 2017-07-14.
 */

public class HomeDescFragment extends Fragment implements OnMapReadyCallback {
    Double LAT;
    Double LNG;
    public static final String HOUSE_RULE_FRAGMENT_TAG = "HOUSE_RULE_FRAGMENT_TAG";
    public static final String AMENITIES_FRAGMENT_TAG = "AMENITIES_FRAGMENT_TAG";
    public static final String AVAILABILITY_FRAGMENT_TAG = "AVAILABILITY_FRAGMENT_TAG";
    public static String AMENITIES_FROM_DATABASE = "AMENITIES_FROM_DATABASE";
    public static String AVAILABILITY_FROM_DATABASE = "AVAILABILITY_FROM_DATABASE";
    public static String CHECK_IN = "CHECK_IN";
    public static String CHECK_OUT = "CHECK_OUT";
    private ArrayList<String> imageArrayList = new ArrayList<>();
    private ArrayList<String> captionArrayList = new ArrayList<>();
    private ImageSliderPager imageSliderPager;
    //Amenities
    SharedPreferences amenitiesSP;
    Map<String, ?> savedAmenities;
    LinearLayout layoutIconAmenities;
    //Layout params for amenities icons
    LinearLayout.LayoutParams params;
    ImageView showOthersIcon;
    boolean showOthersIconAddded;
    SupportMapFragment mapFragment;
    DatabaseInterface retrofit;
    int showOthersIconInt;
    boolean retrieveAmenitiesFromData;

    public void addNewFragment(Bundle bundle, String fragmentTag, Fragment fragment) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(fragmentTag);
        fragmentTransaction.add(R.id.homeDescLayout, fragment).commit();
        fragmentTransaction.hide(HomeDescFragment.this);
        fragmentTransaction.show(fragment);

    }

    public ImageSliderPager getImageSliderPager() {
        return imageSliderPager;
    }

    public void loadSDCard() {
        File[] listFile;
        //load images stored in SD CARD (external storage)

        //get file location
        File sdCardDirectory = Environment.getExternalStorageDirectory();
        File file = new File(sdCardDirectory.getAbsolutePath() + GalleryAdapter.airBnbDirectory);

        if (file.isDirectory()) {
            listFile = file.listFiles();
            if (listFile != null) {
                for (int i = 0; i < listFile.length; i++) {
                    //Convert file path to Uri, then add to arrayList
                    imageArrayList.add(String.valueOf(Uri.fromFile(new File(listFile[i].getAbsolutePath()))));
                }
            }
        }
    }

    public int loadAmenitiesIcon(String glideIconLink, int showOtherIconsInt) {
        Log.d("loadIcon", showOtherIconsInt + "");
        if (savedAmenities != null || retrieveAmenitiesFromData
                && layoutIconAmenities != null && params != null) {
            getView().findViewById(R.id.tvAmenitiesNone).setVisibility(View.GONE);
            if (showOtherIconsInt > 0) {
                Log.d("loadIcon", showOtherIconsInt + " greater than 0");
                ImageView amenitiesIcon = new ImageView(getActivity());
                Glide.with(getContext()).load(glideIconLink).into(amenitiesIcon);
                amenitiesIcon.setLayoutParams(params);
                layoutIconAmenities.addView(amenitiesIcon);
                showOtherIconsInt--;
                return showOtherIconsInt;
            } else {
                if (!showOthersIconAddded) {
                    showOthersIcon = new ImageView(getActivity());
                    Glide.with(getContext()).load("http://www.freeiconspng.com/uploads/plus-icon-black-2.png").into(showOthersIcon);
                    showOthersIcon.setLayoutParams(params);
                    layoutIconAmenities.addView(showOthersIcon);
                    showOthersIconAddded = true;
                }

            }

        } else {
            Log.e("mattError", "Variable savedAmenities, layouticonAmentities, or params are null");
        }
        return showOtherIconsInt;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ;
        retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();
        Log.d("HomeDescFragment", "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_desc, container, false);
        Log.d("HomeDescFragment", "onCreateView");
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        showOthersIconAddded = false;

        ImageView ivHost = (ImageView) view.findViewById(R.id.ivHost);
        ivHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.homeDescLayout, new HostProfileViewFragment()).
                        addToBackStack(null).commit();
            }
        });

        Glide.with(this).load("https://cdn.pixabay.com/photo/2014/03/04/12/55/people-279457_960_720.jpg").into(ivHost);

        ImageView ivGuest = (ImageView) view.findViewById(R.id.ivGuest);
        ImageView ivRoom = (ImageView) view.findViewById(R.id.ivRoom);
        ImageView ivBed = (ImageView) view.findViewById(R.id.ivBed);
        ImageView ivBathroom = (ImageView) view.findViewById(R.id.ivBathroom);

        Glide.with(getContext()).load("http://www.freeiconspng.com/uploads/person-icon-person-icon-clipart-image-from-our-icon-clipart-category--9.png").into(ivGuest);
        Glide.with(getContext()).load("http://www.freeiconspng.com/uploads/door-icon-19.png").into(ivRoom);
        Glide.with(getContext()).load("https://www.materialui.co/materialIcons/maps/hotel_grey_192x192.png").into(ivBed);
        Glide.with(getContext()).load("http://classicbaHothtubrefinishing.com/communities/7/000/001/766/187//images/9604963_229x158.png").into(ivBathroom);

        final TextView tvDesc = (TextView) view.findViewById(R.id.tvDesc);
        final TextView tvPlaceTitle = (TextView) view.findViewById(R.id.tvPlaceTitle);
        TextView tvPropertyType = (TextView) view.findViewById(R.id.tvPropertyType);
        final TextView tvGuest = (TextView) view.findViewById(R.id.tvGuest);
        final TextView tvRoom = (TextView) view.findViewById(R.id.tvRoom);
        final TextView tvBed = (TextView) view.findViewById(R.id.tvBed);
        final TextView tvBathroom = (TextView) view.findViewById(R.id.tvBaths);
        layoutIconAmenities = (LinearLayout) view.findViewById(R.id.layoutIconAmenities);
        final RelativeLayout layoutHouseRule = (RelativeLayout) view.findViewById(R.id.layoutHouseRule);
        final LinearLayout layoutAmenities = (LinearLayout) view.findViewById(R.id.layoutAmenities);
        final RelativeLayout layoutAvailability = (RelativeLayout) view.findViewById(R.id.layoutAvailability);

        showOthersIconInt = 6 - 1;
        //Layout params for amenities icons

        float widthAndHeight = getResources().getDimension(R.dimen.amenities_icon_height_and_width);
        params = new LinearLayout.LayoutParams((int) widthAndHeight,
                (int) widthAndHeight);
        params.weight = 1;


        layoutHouseRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewFragment(null, HOUSE_RULE_FRAGMENT_TAG, new HouseRuleMoreFragment());
            }
        });


        //Retrieve data from database since listing is ALREADY PUBLISHED
        if (getArguments() != null) {
            if (getArguments().containsKey(ViewListingAndYourBookingAdapter.LISTING_ID)) {
                final ProgressDialog dialog = new ProgressDialog(getActivity());
                dialog.setCancelable(true);
                dialog.setMessage("Getting data...");
                dialog.show();
                Log.d("HomeDescFragment", "getArgument scope");
                Log.d("checkMe", getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID) + "");
                SharedPreferences sessionSP = getActivity().getSharedPreferences(SessionManager.SESSION_SP, Context.MODE_PRIVATE);
                final int userId = sessionSP.getInt(SessionManager.USER_ID, 0);
                final int listingId = getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID);
                retrofit.getListingData(listingId).enqueue(new Callback<POJOListingData>() {
                    @Override
                    public void onResponse(Call<POJOListingData> call, final Response<POJOListingData> response) {
                        final POJOListingData body = response.body();
                        //TODO: PRICE, add property_ownership and property_type in view listing


                        //Handle slide images
                        if (body.getImageData().size() == 0) {
                            viewPager.setVisibility(View.GONE);
                        } else {

                            Log.d("ILoveYou", String.valueOf(new Integer(getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID) + "")));
                            final TextView tvSize = (TextView) view.findViewById(R.id.tvSize);
                            for (int i = 0; i < body.getImageData().size(); i++) {
                                imageArrayList.add(body.getImageData().get(i).getImagePath());
                                captionArrayList.add(response.body().getImageData().get(i).getCaption() + "");
                                Log.d("HeyBestie", "Love ya" + imageArrayList.get(i));
                            }
                            imageSliderPager = new ImageSliderPager(getActivity(), HomeDescFragment.this,
                                    imageArrayList,
                                    body.getImageData().size(), true);
                            viewPager.setAdapter(imageSliderPager);
                            Log.d("LoveYou", String.valueOf(body.getImageData().size()));
                            tvSize.setText(1 + " of " + body.getImageData().size() + " - " + body.getImageData().get(0));
                            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                }

                                @Override
                                public void onPageSelected(int position) {
                                    tvSize.setText((position + 1) + " of " + imageArrayList.size() + " - " + body.getImageData().get(position).getCaption());
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {
                                }
                            });

                        }
                        tvGuest.setText(body.getTotalGuest());
                        tvRoom.setText(body.getTotalBedrooms());
                        tvBed.setText(body.getTotalBeds());
                        tvBathroom.setText(body.getTotalBathrooms());


                        LAT = Double.parseDouble(body.getLat());
                        LNG = Double.parseDouble(body.getLng());
                        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                        mapFragment.getMapAsync(HomeDescFragment.this);
                        //Load AmenitiesIcon
                        //1 is true
                        //2 is false
                        retrieveAmenitiesFromData = true;
                        final ArrayList<String> amenitiesFromDatabase = new ArrayList<String>();
                        if (body.getEssentials() == 1) {
                            showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.EssentialsIcon), showOthersIconInt);
                            amenitiesFromDatabase.add(getResources().getString(R.string.rbEssentials));
                        }
                        if (body.getInternet() == 1) {
                            showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.InternetIcon), showOthersIconInt);
                            amenitiesFromDatabase.add(getResources().getString(R.string.rbInternet));
                        }
                        if (body.getShampoo() == 1) {
                            showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.ShampooIcon), showOthersIconInt);
                            amenitiesFromDatabase.add(getResources().getString(R.string.rbShampoo));
                        }
                        if (body.getHangers() == 1) {
                            showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.HangersIcon), showOthersIconInt);
                            amenitiesFromDatabase.add(getResources().getString(R.string.rbHangers));
                        }
                        if (body.getTv() == 1) {
                            showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.TVIcon), showOthersIconInt);
                            amenitiesFromDatabase.add(getResources().getString(R.string.rbTV));
                        }
                        if (body.getHeating() == 1) {
                            showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.HeatingIcon), showOthersIconInt);
                            amenitiesFromDatabase.add(getResources().getString(R.string.rbHeating));
                        }
                        if (body.getAirConditioning() == 1) {
                            showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.AirConditioningIcon), showOthersIconInt);
                            amenitiesFromDatabase.add(getResources().getString(R.string.rbAirConditioning));

                        }
                        if (body.getBreakfast() == 1) {
                            showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.BreakfastIcon), showOthersIconInt);
                            amenitiesFromDatabase.add(getResources().getString(R.string.rbBreakfast));

                        }
                        if (body.getKitchen() == 1) {
                            showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.KitchenIcon), showOthersIconInt);
                            amenitiesFromDatabase.add(getResources().getString(R.string.rbKitchen));

                        }
                        if (body.getLaundry() == 1) {
                            showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.LaundryIcon), showOthersIconInt);
                            amenitiesFromDatabase.add(getResources().getString(R.string.rbLaundry));

                        }
                        if (body.getParking() == 1) {
                            showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.ParkingIcon), showOthersIconInt);
                            amenitiesFromDatabase.add(getResources().getString(R.string.rbParking));
                        }
                        if (body.getElevator() == 1) {
                            showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.ElevatorIcon), showOthersIconInt);
                            amenitiesFromDatabase.add(getResources().getString(R.string.rbAirConditioning));

                        }
                        if (body.getPool() == 1) {
                            showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.PoolIcon), showOthersIconInt);
                            amenitiesFromDatabase.add(getResources().getString(R.string.rbPool));

                        }
                        if (body.getGym() == 1) {
                            showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.GymIcon), showOthersIconInt);
                            amenitiesFromDatabase.add(getResources().getString(R.string.rbGym));

                        }

                        tvDesc.setText(body.getPlaceDescription());
                        tvPlaceTitle.setText(body.getPlaceTitle());

                        layoutHouseRule.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle = new Bundle();
                                bundle.putInt(ViewListingAndYourBookingAdapter.LISTING_ID,
                                        getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID));
                                addNewFragment(bundle, HOUSE_RULE_FRAGMENT_TAG, new HouseRuleMoreFragment());
                            }
                        });

                        layoutAmenities.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle = new Bundle();
                                bundle.putStringArrayList(AMENITIES_FROM_DATABASE,
                                        amenitiesFromDatabase);
                                addNewFragment(null, AMENITIES_FRAGMENT_TAG, new AmenitiesIconMoreFragment());
                            }
                        });
                        Log.d("LoveYa", "saved");
                        layoutAvailability.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final ProgressDialog dialog = new ProgressDialog(getActivity());
                                dialog.setCancelable(false);
                                dialog.setMessage("Getting data...");
                                dialog.show();
                                final AvailabilityFragment availabilityFragment = new AvailabilityFragment();
                                final Bundle bundle = new Bundle();
                                bundle.putBoolean(AVAILABILITY_FROM_DATABASE, true);
                                bundle.putInt(SessionManager.USER_ID, body.getId());
                                bundle.putInt(ViewListingAndYourBookingAdapter.LISTING_ID, getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID));
                                bundle.putString(BookingFragment.MAX_MONTH, body.getListingLength());
                                if (body.getMinStay() != "") {
                                    bundle.putString(BookingFragment.MIN_STAY, "3");
                                }
                                if (body.getMaxStay() != "") {
                                    bundle.putString(BookingFragment.MAX_STAY, body.getMaxStay());
                                }

                                retrofit.getBookingSchedules(userId, listingId).enqueue(new Callback<POJOBookingDataGetResult>() {
                                    @Override
                                    public void onResponse(Call<POJOBookingDataGetResult> call, Response<POJOBookingDataGetResult> response) {
                                        dialog.dismiss();
                                        ArrayList<String> checkInArrayList = new ArrayList<String>();
                                        ArrayList<String> checkOutArrayList = new ArrayList<String>();
                                        if (response.body() != null) {
                                            for (int i = 0; i < response.body().getResult().size(); i++) {

                                                Log.d("hiMatt", response.body().getResult().get(i).getCheckIn());
                                                checkInArrayList.add(String.valueOf(response.body().getResult().get(i).getCheckIn()));
                                                checkOutArrayList.add(String.valueOf(response.body().getResult().get(i).getCheckOut()));
                                            }

                                            bundle.putStringArrayList(CHECK_IN, checkInArrayList);
                                            bundle.putStringArrayList(CHECK_OUT, checkOutArrayList);
                                        }

                                        addNewFragment(bundle, AVAILABILITY_FRAGMENT_TAG, availabilityFragment);
                                    }

                                    @Override
                                    public void onFailure(Call<POJOBookingDataGetResult> call, Throwable t) {
                                        dialog.dismiss();
                                        Log.d("CoolMatt", t.toString());
                                    }
                                });

                                availabilityFragment.setArguments(bundle);
//                                getFragmentManager().beginTransaction().replace(R.id.homeDescLayout, availabilityFragment).addToBackStack(null)
//                                        .commit();

                            }
                        });


                        dialog.dismiss();


                    }

                    @Override
                    public void onFailure(Call<POJOListingData> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(),
                                "Data retrieval failed, make sure your internet connection is stable, try again", Toast.LENGTH_LONG).show();
                        getActivity().onBackPressed();
                    }
                });

                //Show preview that is not saved to shared preferences yet
                if (getArguments().containsKey(DescribePlaceFragment.DESCRIBE_PREVIEW)) {
                    tvDesc.setText(getArguments().getString(DescribePlaceFragment.DESCRIBE_PREVIEW));
                } else if (getArguments().containsKey(TitleFragment.TITLE_PREVIEW)) {
                    tvPlaceTitle.setText(getArguments().getString(TitleFragment.TITLE_PREVIEW));
                }

            }
        } else {
            loadSDCard();
            //Handle images
            SharedPreferences captionSP = getActivity().getSharedPreferences(PhotoDescFragment.CAPTION_SP, Context.MODE_PRIVATE);

            for (Map.Entry<String, ?> entry : captionSP.getAll().entrySet()) {
                captionArrayList.add(String.valueOf(entry.getValue()));
            }
            if (imageArrayList.isEmpty()) {
                viewPager.setVisibility(View.GONE);
            } else {
                Log.d("HomeDescFragment", imageArrayList.get(0));

                final TextView tvSize = (TextView) view.findViewById(R.id.tvSize);
                imageSliderPager = new ImageSliderPager(getActivity(), HomeDescFragment.this, imageArrayList,
                        imageArrayList.size(), false);
                viewPager.setAdapter(imageSliderPager);

                tvSize.setText(1 + " of " + imageArrayList.size() + " - " + imageSliderPager.getFirstCaption());
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageSelected(int position) {
                        tvSize.setText((position + 1) + " of " + imageArrayList.size() + " - " + imageSliderPager.getOtherCaptions(position));
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }
                });
            }


            SharedPreferences locationSP = getActivity().getSharedPreferences(LocationFilterAdapter.LOCATION_SP,
                    Context.MODE_PRIVATE);
            LAT = Double.parseDouble(locationSP.getString(LocationFilterAdapter.LAT, "0.000000"));
            LNG = Double.parseDouble(locationSP.getString(LocationFilterAdapter.LNG, "0.000000"));
            mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(HomeDescFragment.this);
            //get data from sharedpreferences since listing HAS NOT BEEN PUBLISHED

            Log.d("HomeDescFragment", "out of get arguments scope");

            //Load description from shared preferences if user did not make any changes
            SharedPreferences describeSP = getActivity().getSharedPreferences(DescribePlaceFragment.DESCRIBE_SP, Context.MODE_PRIVATE);
            String savedEtDescribePlace = describeSP.getString(DescribePlaceFragment.DESCRIBE_PLACE_KEY, "");
            tvDesc.setText(savedEtDescribePlace);

            //Load title from shared preferences
            SharedPreferences titleSP = getActivity().getSharedPreferences(TitleFragment.TITLE_SP, Context.MODE_PRIVATE);
            String savedEtTitle = titleSP.getString(TitleFragment.TITLE_KEY, "");
            tvPlaceTitle.setText(savedEtTitle);

            //Load PropertyTypeFragment from shared preferences
            SharedPreferences propertyTypeSP = getActivity().getSharedPreferences(PropertyTypeFragment.PROPERTY_TYPE_SP, Context.MODE_PRIVATE);
            tvPropertyType.setText(propertyTypeSP.getString(PropertyTypeFragment.PROPERTY_TYPE, ""));

            //Load PropertyTypeFragment from shared preferences
            SharedPreferences guestSP = getActivity().getSharedPreferences(GuestFragment.GUEST_SP, Context.MODE_PRIVATE);
            tvGuest.setText(guestSP.getString(GuestFragment.TOTAL_GUEST, "1 guest"));
            tvRoom.setText(guestSP.getString(GuestFragment.TOTAL_BED_ROOM, "1 bedroom"));
            tvBed.setText(guestSP.getString(GuestFragment.TOTAL_BED, "1 bed"));

            //Load BathroomFragment
            SharedPreferences bathroomSP = getActivity().getSharedPreferences(BathroomFragment.BATHROOM_SP, Context.MODE_PRIVATE);
            tvBathroom.setText(bathroomSP.getString(BathroomFragment.TOTAL_BATHROOM, "1 bathroom"));


            //Load amenities icon from sharedpreferences
            amenitiesSP = getActivity().getSharedPreferences(AmenitiesItemFragment.AMENITIES_SP, Context.MODE_PRIVATE);
            savedAmenities = amenitiesSP.getAll();
            Map<String, ?> keys = amenitiesSP.getAll();

            for (Map.Entry<String, ?> entry : keys.entrySet()) {
                Log.d("map values", entry.getKey() + ": " +
                        entry.getValue().toString());
            }


            //-1 because loadAmenititesIcon() will create an additional + icon.
            //if countdown reaches 0, it will show "Others icon int"
            if (savedAmenities.containsKey(getResources().getString(R.string.rbEssentials))) {
                showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.EssentialsIcon), showOthersIconInt);
            }

            if (savedAmenities.containsKey(getResources().getString(R.string.rbInternet))) {
                showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.InternetIcon), showOthersIconInt);
            }
            if (savedAmenities.containsKey(getResources().getString(R.string.rbShampoo))) {
                showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.ShampooIcon), showOthersIconInt);
            }

            if (savedAmenities.containsKey(getResources().getString(R.string.rbHangers))) {
                showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.HangersIcon), showOthersIconInt);
            }
            if (savedAmenities.containsKey(getResources().getString(R.string.rbTV))) {
                showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.TVIcon), showOthersIconInt);
            }
            if (savedAmenities.containsKey(getResources().getString(R.string.rbHeating))) {
                showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.HeatingIcon), showOthersIconInt);
            }

            if (savedAmenities.containsKey(getResources().getString(R.string.rbAirConditioning))) {
                showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.AirConditioningIcon), showOthersIconInt);
            }

            if (savedAmenities.containsKey(getResources().getString(R.string.rbBreakfast))) {
                showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.BreakfastIcon), showOthersIconInt);
            }

            layoutIconAmenities.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction().add(R.id.homeDescLayout, new AmenitiesIconMoreFragment()).addToBackStack(null).commit();
                }
            });

            //AmenitiesSpaceFragment
            if (savedAmenities.containsKey(getResources().getString(R.string.rbKitchen))) {
                showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.KitchenIcon), showOthersIconInt);
            }

            if (savedAmenities.containsKey(getResources().getString(R.string.rbLaundry))) {
                showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.LaundryIcon), showOthersIconInt);
            }
            if (savedAmenities.containsKey(getResources().getString(R.string.rbParking))) {
                showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.ParkingIcon), showOthersIconInt);
            }

            if (savedAmenities.containsKey(getResources().getString(R.string.rbElevator))) {
                showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.ElevatorIcon), showOthersIconInt);
            }
            if (savedAmenities.containsKey(getResources().getString(R.string.rbPool))) {
                showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.PoolIcon), showOthersIconInt);
            }
            if (savedAmenities.containsKey(getResources().getString(R.string.rbGym))) {
                showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.GymIcon), showOthersIconInt);
            }
            layoutAmenities.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addNewFragment(null, AMENITIES_FRAGMENT_TAG, new AmenitiesIconMoreFragment());
                }
            });

            Log.d("LoveYa", "unsaved");
            layoutAvailability.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addNewFragment(null, AVAILABILITY_FRAGMENT_TAG, new AvailabilityFragment());
                }
            });

        }
    }

    //this overrides the onMapReady in MapFragment
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(LAT, LNG);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(LAT, LNG));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(11)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        googleMap.addMarker(markerOptions);
        googleMap.addCircle(new CircleOptions()
                .center(latLng)
                .radius(300)
                .strokeWidth(0f)
                .fillColor(0x550000FF));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //need to remove childR fragment when destroyed or else "error loading xml file would occur"
        if (mapFragment != null) {
            //get rid of child when activity is already saved
            getChildFragmentManager().beginTransaction().remove(mapFragment).commitAllowingStateLoss();
        }

        mapFragment = null;
    }
}

