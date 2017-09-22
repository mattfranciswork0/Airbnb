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
import com.example.toshiba.airbnb.Profile.BecomeAHost.GetReady.HouseRuleFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.DescribePlaceFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.GalleryAdapter;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.TitleFragment;
import com.example.toshiba.airbnb.Profile.HostProfileViewFragment;
import com.example.toshiba.airbnb.Profile.ViewListing.ViewListingAdapter;
import com.example.toshiba.airbnb.R;
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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Owner on 2017-07-14.
 */

public class HomeDescFragment extends Fragment implements OnMapReadyCallback {
    public static String LAT = "LAT";
    public static String LNG = "LNG";
    private ArrayList<Uri> imageUriArrayList = new ArrayList<>();
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
                    imageUriArrayList.add(Uri.fromFile(new File(listFile[i].getAbsolutePath())));
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
        loadSDCard();
        retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.2.89:3000/")
                .baseUrl("http://192.168.0.34:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(DatabaseInterface.class);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        showOthersIconAddded = false;


        //TODO: send img to imagesliderpager
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        final TextView tvSize = (TextView) view.findViewById(R.id.tvSize);
        imageSliderPager = new ImageSliderPager(getActivity(), imageUriArrayList, HomeDescFragment.this, viewPager);
        viewPager.setAdapter(imageSliderPager);

        tvSize.setText(1 + " of " + imageUriArrayList.size() + " - " + imageSliderPager.getFirstCaption());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tvSize.setText((position + 1) + " of " + imageUriArrayList.size() + " - " + imageSliderPager.getOtherCaptions(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

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
        Glide.with(getContext()).load("http://classicbathtubrefinishing.com/communities/7/000/001/766/187//images/9604963_229x158.png").into(ivBathroom);

        final TextView tvDesc = (TextView) view.findViewById(R.id.tvDesc);
        final TextView tvPlaceTitle = (TextView) view.findViewById(R.id.tvPlaceTitle);
        TextView tvPropertyType = (TextView) view.findViewById(R.id.tvPropertyType);
        final TextView tvGuest = (TextView) view.findViewById(R.id.tvGuest);
        final TextView tvRoom = (TextView) view.findViewById(R.id.tvRoom);
        final TextView tvBed = (TextView) view.findViewById(R.id.tvBed);
        final TextView tvBathroom = (TextView) view.findViewById(R.id.tvBaths);
        layoutIconAmenities = (LinearLayout) view.findViewById(R.id.layoutIconAmenities);
        final RelativeLayout layoutHouseRule = (RelativeLayout) view.findViewById(R.id.layoutHouseRule);
        showOthersIconInt = 6 - 1;
        //Layout params for amenities icons
        float widthAndHeight = getResources().getDimension(R.dimen.amenities_icon_height_and_width);
        params = new LinearLayout.LayoutParams((int) widthAndHeight,
                (int) widthAndHeight);
        params.weight = 1;

        view.findViewById(R.id.layoutAmentities).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.homeDescLayout, new AmenitiesIconMoreFragment()).addToBackStack(null)
                .commit();
            }
        });


        layoutHouseRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.homeDescLayout, new HouseRuleMoreFragment()).
                        addToBackStack(null).commit();
            }
        });


        //Retrieve data from database since listing is ALREADY PUBLISHED
        if (getArguments() != null) {
            if (getArguments().containsKey(ViewListingAdapter.LISTING_ID)) {
                final ProgressDialog dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Getting data...");
                dialog.show();
                Log.d("HomeDescFragment", "getArgument scope");
                Toast.makeText(getActivity(), "Hi", Toast.LENGTH_LONG).show();
                retrofit.getListingData(getArguments().getInt(ViewListingAdapter.LISTING_ID)).enqueue(new Callback<POJOListingData>() {
                    @Override
                    public void onResponse(Call<POJOListingData> call, Response<POJOListingData> response) {
                        POJOListingData body = response.body();
                        //TODO: PRICE, add property_ownership and property_type in view listing
                        //TODO: programtically add map to layout for setArgument
                        //TODO: layoutIconMore ameniteis

                        tvGuest.setText(body.getTotalGuest());
                        tvRoom.setText(body.getTotalBedrooms());
                        tvBed.setText(body.getTotalBeds());
                        tvBathroom.setText(body.getTotalBathrooms());


                        //TODO:Handle MAP
                        Bundle mapBundle = new Bundle();
                        mapBundle.putString(LAT, body.getLat());
                        mapBundle.putString(LNG, body.getLng());
                        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//                        mapFragment.setArguments(mapBundle);
                        mapFragment.getMapAsync(HomeDescFragment.this);
                        Log.d("thisObj", HomeDescFragment.class + "");
                        Log.d("thisObj", HomeDescFragment.this + "");
                        Log.d("thisObj", getActivity() + "");


                        //Load AmenitiesIcon
                        //1 is true
                        //2 is false
                        retrieveAmenitiesFromData = true;
                        if (body.getEssentials() == 1)
                            Log.d("loveYa", "essential if block");
                            showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.EssentialsIcon), showOthersIconInt);
                        if (body.getInternet() == 1)
                            showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.InternetIcon), showOthersIconInt);
                        if (body.getShampoo() == 1)
                            showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.ShampooIcon), showOthersIconInt);
                        if (body.getHangers() == 1)
                            showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.HangersIcon), showOthersIconInt);
                        if (body.getTv() == 1)
                            showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.TVIcon), showOthersIconInt);
                        if (body.getHeating() == 1)
                            showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.HeatingIcon), showOthersIconInt);
                        if (body.getAirConditioning() == 1)
                            showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.AirConditioningIcon), showOthersIconInt);
                        if (body.getBreakfast() == 1)
                            showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.BreakfastIcon), showOthersIconInt);
                        if (body.getKitchen() == 1)
                            showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.KitchenIcon), showOthersIconInt);
                        if (body.getLaundry() == 1)
                            showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.LaundryIcon), showOthersIconInt);
                        if (body.getParking() == 1)
                            showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.ParkingIcon), showOthersIconInt);
                        if (body.getElevator() == 1)
                            showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.ElevatorIcon), showOthersIconInt);
                        if (body.getPool() == 1)
                            showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.PoolIcon), showOthersIconInt);
                        if (body.getGym() == 1)
                            showOthersIconInt = loadAmenitiesIcon(getResources().getString(R.string.GymIcon), showOthersIconInt);


                        tvDesc.setText(body.getPlaceDescription());
                        tvPlaceTitle.setText(body.getPlaceTitle());

                        layoutHouseRule.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                HouseRuleMoreFragment houseRuleMoreFragment = new HouseRuleMoreFragment();
                                Bundle houseRuleBundle = new Bundle();
                                houseRuleBundle.putInt(ViewListingAdapter.LISTING_ID,
                                        getArguments().getInt(ViewListingAdapter.LISTING_ID));
                                houseRuleMoreFragment.setArguments(houseRuleBundle);
                                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.homeDescLayout, houseRuleMoreFragment).commit();
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
            } else {
                //Initalize map fragment
                mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);

                //get data from sharedpreferences since listing HAS NOT BEEN PUBLISHED

                Log.d("HomeDescFragment", "out of get arguments scope");
                Bundle bundle = getArguments();

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


                //Show preview that is not saved to shared preferences yet
                //Show preview that is not saved to shared preferences yet
                if (bundle.containsKey(DescribePlaceFragment.DESCRIBE_PREVIEW)) {
                    tvDesc.setText(getArguments().getString(DescribePlaceFragment.DESCRIBE_PREVIEW));
                } else if (bundle.containsKey(TitleFragment.TITLE_PREVIEW)) {
                    tvPlaceTitle.setText(getArguments().getString(TitleFragment.TITLE_PREVIEW));
                }


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
                        getFragmentManager().beginTransaction().replace(R.id.homeDescLayout, new AmenitiesIconMoreFragment()).addToBackStack(null).commit();
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

            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(LocationFilterAdapter.LOCATION_SP, Context.MODE_PRIVATE);

        Double LAT = Double.parseDouble(sharedPreferences.getString(LocationFilterAdapter.LAT, "0.000000"));
        Double LNG = Double.parseDouble(sharedPreferences.getString(LocationFilterAdapter.LNG, "0.000000"));
        LatLng latLng = new LatLng(LAT, LNG);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
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

        //Load data saved in dataabase if it exist
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

