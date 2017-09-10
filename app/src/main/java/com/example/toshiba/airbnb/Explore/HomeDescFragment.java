package com.example.toshiba.airbnb.Explore;

/**
 * Created by TOSHIBA on 02/08/2017.
 */


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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.AmenitiesIconMoreFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.AmenitiesItemFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.BathroomFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.GuestFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.LocationFilterAdapter;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.PropertyTypeFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.DescribePlaceFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.GalleryAdapter;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.TitleFragment;
import com.example.toshiba.airbnb.Profile.HostProfileViewFragment;
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


/**
 * Created by Owner on 2017-07-14.
 */

public class HomeDescFragment extends Fragment implements OnMapReadyCallback {
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
        if (savedAmenities != null && layoutIconAmenities != null && params != null) {
            getView().findViewById(R.id.tvAmenitiesNone).setVisibility(View.GONE);
            if (showOtherIconsInt > 0) {
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_desc, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        showOthersIconAddded = false;
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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

        TextView tvDesc = (TextView) view.findViewById(R.id.tvDesc);
        TextView tvPlaceTitle = (TextView) view.findViewById(R.id.tvPlaceTitle);

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
        TextView tvPropertyType = (TextView) view.findViewById(R.id.tvPropertyType);
        tvPropertyType.setText(propertyTypeSP.getString(PropertyTypeFragment.PROPERTY_TYPE, ""));

        //Load PropertyTypeFragment from shared preferences
        SharedPreferences guestSP = getActivity().getSharedPreferences(GuestFragment.GUEST_SP, Context.MODE_PRIVATE);
        TextView tvGuest = (TextView) view.findViewById(R.id.tvGuest);
        tvGuest.setText(guestSP.getString(GuestFragment.TOTAL_GUEST, "1 guest"));
        TextView tvRoom = (TextView) view.findViewById(R.id.tvRoom);
        tvRoom.setText(guestSP.getString(GuestFragment.TOTAL_BED_ROOM, "1 bedroom"));
        TextView tvBed = (TextView) view.findViewById(R.id.tvBed);
        tvBed.setText(guestSP.getString(GuestFragment.TOTAL_BED, "1 bed"));

        //Load BathroomFragment
        SharedPreferences bathroomSP = getActivity().getSharedPreferences(BathroomFragment.BATHROOM_SP, Context.MODE_PRIVATE);
        TextView tvBathroom = (TextView) view.findViewById(R.id.tvBaths);
        tvBathroom.setText(bathroomSP.getString(BathroomFragment.TOTAL_BATHROOM, "1 bathroom"));



        //Show preview that is not saved to shared preferences yet
        if (bundle != null) {
            //Show preview that is not saved to shared preferences yet
            if (bundle.containsKey(DescribePlaceFragment.DESCRIBE_PREVIEW)) {
                tvDesc.setText(getArguments().getString(DescribePlaceFragment.DESCRIBE_PREVIEW));
            } else if (bundle.containsKey(TitleFragment.TITLE_PREVIEW)) {
                tvPlaceTitle.setText(getArguments().getString(TitleFragment.TITLE_PREVIEW));
            }
        }


        //Load amenities icon from sharedpreferences
        amenitiesSP = getActivity().getSharedPreferences(AmenitiesItemFragment.AMENITIES_SP, Context.MODE_PRIVATE);
        savedAmenities = amenitiesSP.getAll();
        Map<String, ?> keys = amenitiesSP.getAll();

        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            Log.d("map values", entry.getKey() + ": " +
                    entry.getValue().toString());
        }

        layoutIconAmenities = (LinearLayout) view.findViewById(R.id.layoutIconAmenities);

        //Layout params for amenities icons
        float widthAndHeight = getResources().getDimension(R.dimen.amenities_icon_height_and_width);
        params = new LinearLayout.LayoutParams((int) widthAndHeight,
                (int) widthAndHeight);
        params.weight = 1;


        //-1 because loadAmenititesIcon() will create an additional + icon.
        //if countdown reaches 0, it will show "Others icon int"
        int showOthersIconInt = 6 - 1;
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


        view.findViewById(R.id.layoutHouseRule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.homeDescLayout, new HouseRuleMoreFragment()).
                        addToBackStack(null).commit();
            }
        });

        view.findViewById(R.id.layoutAvailability).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.homeDescLayout, new AvailabilityFragment()).
                        addToBackStack(null).commit();
            }
        });

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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //need to remove child fragment when destroyed or else "error loading xml file would occur"
        if (mapFragment != null)
        {
            //get rid of child when activity is already saved
            getChildFragmentManager().beginTransaction().remove(mapFragment).commitAllowingStateLoss();
        }

        mapFragment = null;
    }
}

