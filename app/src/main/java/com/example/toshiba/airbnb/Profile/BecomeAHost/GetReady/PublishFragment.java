package com.example.toshiba.airbnb.Profile.BecomeAHost.GetReady;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.Explore.MenuActivity;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.AmenitiesItemFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.BathroomFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.GuestFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.LocationFilterAdapter;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.LocationFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.BasicQuestions.PropertyTypeFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.IdListing;
import com.example.toshiba.airbnb.Profile.BecomeAHost.ImageListingRequest;
import com.example.toshiba.airbnb.Profile.BecomeAHost.PublishListingDataRequest;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.DescribePlaceFragment;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.GalleryAdapter;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.TitleFragment;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.SessionManager;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by TOSHIBA on 11/08/2017.
 */

public class PublishFragment extends Fragment {
    public static final String PUBLISH_FRAGMENT_FINISHED = "PUBLISH_FRAGMENT_FINISHED";
    public static final String LISTING_ID_SP = "LISTING_ID_SP";
    public static final String LISTING_ID = "LISTING_ID";
    private SharedPreferences propertyTypeSP;
    private SharedPreferences guestFragmentSP;
    private SharedPreferences bathroomFragmentSP;
    private SharedPreferences locationSP;
    private SharedPreferences amenitiesSP;

    private SharedPreferences describePlaceSP;
    private SharedPreferences titleSP;
    private SharedPreferences houseRuleSP;
    private SharedPreferences bookingSP;
    private SharedPreferences priceSP;

    private SharedPreferences sessionSP;
    private SharedPreferences listingIdSP;
    private SharedPreferences latchPublishSP;
    private SharedPreferences.Editor latchEdit;
    private static final String LATCH_COUNTDOWN_SP = "LATCH_COUNTDOWN_SP";
    private static final String LATCH_COUNTDOWN_COUNT = "LATCH_COUNTDOWN_COUNT";
    private SharedPreferences insertImagesSP;
    private SharedPreferences.Editor insertImagesEdit;
    private SharedPreferences cloudinaryUploadSP;
    private SharedPreferences.Editor cloudinaryEdit;
    private static final String INSERT_IMAGES_SP = "INSERT_IMAGES_SP";
    private static final String INSERT_IMAGES_COUNT = "INSERT_IMAGES_COUNT";
    private static final String CLOUDINARY_UPLOAD_SP = "CLOUDINARY_UPLOAD_SP";
    private static final String CLOUDINARY_UPLOAD_COUNT = "CLOUDINARY_UPLOAD_COUNT";
    private DatabaseInterface retrofit;
    private CountDownLatch latch;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressBar basicProgressBar = (ProgressBar) getActivity().findViewById(R.id.basicProgressBar);
        basicProgressBar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.lightRed));
        basicProgressBar.setProgress(100);
        propertyTypeSP = getActivity().getSharedPreferences(PropertyTypeFragment.PROPERTY_TYPE_SP, Context.MODE_PRIVATE);
        guestFragmentSP = getActivity().getSharedPreferences(GuestFragment.GUEST_SP, Context.MODE_PRIVATE);
        bathroomFragmentSP = getActivity().getSharedPreferences(BathroomFragment.BATHROOM_SP, Context.MODE_PRIVATE);
        locationSP = getActivity().getSharedPreferences(LocationFilterAdapter.LOCATION_SP, Context.MODE_PRIVATE);
        amenitiesSP = getActivity().getSharedPreferences(AmenitiesItemFragment.AMENITIES_SP, Context.MODE_PRIVATE);

        describePlaceSP = getActivity().getSharedPreferences(DescribePlaceFragment.DESCRIBE_SP, Context.MODE_PRIVATE);
        titleSP = getActivity().getSharedPreferences(TitleFragment. TITLE_SP, Context.MODE_PRIVATE);

        houseRuleSP = getActivity().getSharedPreferences(HouseRuleFragment.HOUSE_RULE_SP, Context.MODE_PRIVATE);
        bookingSP = getActivity().getSharedPreferences(BookingFragment.BOOKING_SP, Context.MODE_PRIVATE);
        priceSP = getActivity().getSharedPreferences(PriceFragment.PRICE_SP, Context.MODE_PRIVATE);

        sessionSP = getActivity().getSharedPreferences(SessionManager.SESSION_SP, Context.MODE_PRIVATE);
        listingIdSP = getActivity().getSharedPreferences(LISTING_ID_SP, Context.MODE_PRIVATE);
        //CountDownLatch for publish button
        latchPublishSP = getActivity().getSharedPreferences(LATCH_COUNTDOWN_SP, Context.MODE_PRIVATE);
        latchEdit = latchPublishSP.edit();
//        latchEdit.clear();
        //Save latchCountdown to sp just in case if user clicks publish and app crashes/closes; there won't be any repeated actions.
        if (latchPublishSP.getAll().isEmpty()) {
            latch = new CountDownLatch(2);
            latchEdit.putInt(LATCH_COUNTDOWN_COUNT, (int) latch.getCount()).apply();
        } else {
            latch = new CountDownLatch(latchPublishSP.getInt(LATCH_COUNTDOWN_COUNT, 2));
        }


        retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.2.89:3000/")
                .baseUrl("http://192.168.0.34:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(DatabaseInterface.class);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publish, container, false);
        progressDialog = new ProgressDialog(getActivity());
        return view;
    }

    public String checkPropertyOwnership() {
        if (propertyTypeSP.contains(PropertyTypeFragment.ENTIRE_RADIO))
            return "Entire";
        if (propertyTypeSP.contains(PropertyTypeFragment.SHARED_RADIO))
            return "Shared";
        if (propertyTypeSP.contains(PropertyTypeFragment.PRIVATE_RADIO))
            return "Private";
        return "ERROR";
    }

    public String checkBathroomType() {
        if (bathroomFragmentSP.contains(BathroomFragment.PRIVATE_BATHROOM))
            return "Private";
        else if (bathroomFragmentSP.contains(BathroomFragment.SHARED_BATHROOM))
            return "Shared";
        return "ERROR";
    }

    public void insertImagesToDatabase() {
        File sdCardDirectory = Environment.getExternalStorageDirectory();
        File file = new File(sdCardDirectory.getAbsolutePath() + GalleryAdapter.airBnbDirectory);
        final File[] listFile = file.listFiles();

        insertImagesSP = getActivity().getSharedPreferences(INSERT_IMAGES_COUNT, Context.MODE_PRIVATE);
        insertImagesEdit = insertImagesSP.edit();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.setMessage("Inserting images...");
                progressDialog.show();
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressDialog.dismiss();
            }

            @Override
            protected Void doInBackground(Void... params) {
                int uploadLeftOff;
                if (!(insertImagesSP.contains(INSERT_IMAGES_COUNT))) {
                    uploadLeftOff = 0;
                } else {
                    uploadLeftOff = insertImagesSP.getInt(INSERT_IMAGES_COUNT, 0);
                }

                for (int i = uploadLeftOff; i < listFile.length; i++) {
                    ImageListingRequest imageListingRequest = new ImageListingRequest(listFile[i].getAbsolutePath(),
                            listingIdSP.getInt(LISTING_ID, 0));

                    retrofit.insertListingImages(imageListingRequest).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(getActivity(), "Hi :)", Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getActivity(), "Failed to insert image", Toast.LENGTH_LONG).show();
                            return;
                        }
                    });
                }

                latch.countDown();
                latchEdit.putInt(LATCH_COUNTDOWN_COUNT, (int) latch.getCount()).apply();
                Toast.makeText(getActivity(), latchPublishSP.getInt(LATCH_COUNTDOWN_COUNT, 0) + "", Toast.LENGTH_LONG).show();
                insertImagesEdit.clear();

                return null;
            }
        }.execute();
    }

    public void uploadImageToCloudinary() {
        cloudinaryUploadSP = getActivity().getSharedPreferences(CLOUDINARY_UPLOAD_SP, Context.MODE_PRIVATE);
        cloudinaryEdit = cloudinaryUploadSP.edit();
        progressDialog.setMessage("Uploading your images...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        File sdCardDirectory = Environment.getExternalStorageDirectory();
        File file = new File(sdCardDirectory.getAbsolutePath() + GalleryAdapter.airBnbDirectory);
        final File[] listFile = file.listFiles();

        new AsyncTask<Void, Void, Void>() {
            SharedPreferences finishedUploadingSP;
            SharedPreferences.Editor finishedUploadingEdit;
            public String FINISHED_UPLOADING_SP = "FINISHED_UPLOADING_SP";
            public String FINISHED_UPLOADING = "FINISHED_UPLOADING";

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.setMessage("Uploading your images...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                finishedUploadingSP = getActivity().getSharedPreferences(FINISHED_UPLOADING_SP, Context.MODE_PRIVATE);
                finishedUploadingEdit = finishedUploadingSP.edit();
                //Create uploadLeftOff just in case if an image can't be uploaded during the process (lost connection, etc)
                int uploadLeftOff;
                if (!(cloudinaryUploadSP.contains(CLOUDINARY_UPLOAD_COUNT))) {
                    uploadLeftOff = 0;
                } else {
                    uploadLeftOff = cloudinaryUploadSP.getInt(CLOUDINARY_UPLOAD_COUNT, 0);
                }

                final Cloudinary cloudinary =
                        new Cloudinary("cloudinary://847239287961448:P2LUOMMTrMRij1Ny5ay8lWZHHUk@du8n2aa4p");

                for (int i = uploadLeftOff; i < listFile.length; i++) {
                    try {
                        cloudinary.uploader().upload(listFile[i].getAbsolutePath(), ObjectUtils.emptyMap());

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Failed to upload image", Toast.LENGTH_LONG).show();
                        //to avoid uploaidng repeating images when publish is clicked again
                        cloudinaryEdit.putInt(CLOUDINARY_UPLOAD_COUNT, i).apply();
                        return null;
                    }
                }
                finishedUploadingEdit.putBoolean(FINISHED_UPLOADING, true).apply();
                cloudinaryEdit.clear();

                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                progressDialog.dismiss();
                if (finishedUploadingSP.getBoolean(FINISHED_UPLOADING, false)) {
                    finishedUploadingEdit.clear();
                    latchEdit.clear().apply();
                    Intent intent = new Intent(getActivity(), MenuActivity.class);
                    intent.putExtra("BASIC_QUESTIONS_COMPLETED", true);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

            }

        }.execute();
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

        view.findViewById(R.id.bPublish).

                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("publish", latchPublishSP.getInt(LATCH_COUNTDOWN_COUNT, 0) + " ");
                        if (latchPublishSP.getInt(LATCH_COUNTDOWN_COUNT, 0) == 2) {
                            progressDialog.setMessage("Listing your place...");
                            progressDialog.show();
                            PublishListingDataRequest publishListingDataRequest = new PublishListingDataRequest(
                                    sessionSP.getInt(SessionManager.USER_ID, 0),
                                    checkPropertyOwnership(),
                                    propertyTypeSP.getString(PropertyTypeFragment.PROPERTY_TYPE, "ERROR"),
                                    guestFragmentSP.getString(GuestFragment.TOTAL_GUEST, "ERROR"),
                                    guestFragmentSP.getString(GuestFragment.TOTAL_BED_ROOM, "ERROR"),
                                    guestFragmentSP.getString(GuestFragment.TOTAL_BED, "ERROR"),
                                    bathroomFragmentSP.getString(BathroomFragment.TOTAL_BATHROOM, "ERROR"),
                                    checkBathroomType(),
                                    locationSP.getString(LocationFilterAdapter.COUNTRY_NAME, "ERROR"),
                                    locationSP.getString(LocationFilterAdapter.STREET_NAME, "ERROR"),
                                    locationSP.getString(LocationFragment.EXTRA_DETAILS, "ERROR"),
                                    locationSP.getString(LocationFilterAdapter.CITY_NAME, "ERROR"),
                                    locationSP.getString(LocationFilterAdapter.STATE_NAME, "ERROR"),
                                    locationSP.getString(LocationFilterAdapter.LNG, "ERROR"),
                                    locationSP.getString(LocationFilterAdapter.LAT, "ERROR"),
                                    //AmenitiesItem
                                    amenitiesSP.contains(getResources().getString(R.string.rbEssentials)),
                                    amenitiesSP.contains(getResources().getString(R.string.rbInternet)),
                                    amenitiesSP.contains(getResources().getString(R.string.rbShampoo)),
                                    amenitiesSP.contains(getResources().getString(R.string.rbHangers)),
                                    amenitiesSP.contains(getResources().getString(R.string.rbTV)),
                                    amenitiesSP.contains(getResources().getString(R.string.rbHeating)),
                                    amenitiesSP.contains(getResources().getString(R.string.rbAirConditioning)),
                                    amenitiesSP.contains(getResources().getString(R.string.rbBreakfast)),
                                    //AmenitiesPlace
                                    amenitiesSP.contains(getResources().getString(R.string.rbKitchen)),
                                    amenitiesSP.contains(getResources().getString(R.string.rbLaundry)),
                                    amenitiesSP.contains(getResources().getString(R.string.rbParking)),
                                    amenitiesSP.contains(getResources().getString(R.string.rbElevator)),
                                    amenitiesSP.contains(getResources().getString(R.string.rbPool)),
                                    amenitiesSP.contains(getResources().getString(R.string.rbGym)),

                                    describePlaceSP.getString(DescribePlaceFragment.DESCRIBE_PLACE_KEY, ""),
                                    titleSP.getString(TitleFragment.TITLE_KEY, ""),

//                                    houseRuleSP.getBoolean(getResources().getString(R.string.rbChildren), false),
//                                    houseRuleSP.getBoolean(getResources().getString(R.string.rbInfants), false),
//                                    houseRuleSP.getBoolean(getResources().getString(R.string.rbPets), false),
//                                    houseRuleSP.getBoolean(getResources().getString(R.string.rbSmoking), false),
//                                    houseRuleSP.getBoolean(getResources().getString(R.string.rbParties), false),
                                    false,
                                    false,
                                    false,
                                    false,
                                    false,
                                    houseRuleSP.getString(HouseRuleFragment.ADDITIONAL_RULES, ""),

                                    bookingSP.getString(BookingFragment.MAX_MONTH, "ERROR"),
                                    bookingSP.getString(BookingFragment.ARRIVE_AFTER, "ERROR"),
                                    bookingSP.getString(BookingFragment.LEAVE_BEFORE, "ERROR"),
                                    bookingSP.getString(BookingFragment.MIN_STAY, "ERROR"),
                                    bookingSP.getString(BookingFragment.MAX_STAY, ""),

                                    priceSP.getString(PriceFragment.PRICE, "ERROR")

                            );

                            retrofit.insertListingData(publishListingDataRequest).enqueue(new Callback<IdListing>() {
                                @Override
                                public void onResponse(Call<IdListing> call, Response<IdListing> response) {
                                    Log.d("heyBestie", "The listing id is " + response.body().getId());
                                    progressDialog.dismiss();
                                    latch.countDown();
                                    latchEdit.putInt(LATCH_COUNTDOWN_COUNT, (int) latch.getCount()).apply();
                                    listingIdSP.edit().putInt(LISTING_ID, response.body().getId()).apply();
                                }

                                @Override
                                public void onFailure(Call<IdListing> call, Throwable t) {
                                    progressDialog.dismiss();
                                    Log.d("heyBestie", t.toString());
                                    Toast.makeText(getActivity(), "Failed to list your place, try again", Toast.LENGTH_LONG).show();

                                }
                            });

                        } else if (latchPublishSP.getInt(LATCH_COUNTDOWN_COUNT, 0) == 1) {
                            insertImagesToDatabase();
                        } else if (latchPublishSP.getInt(LATCH_COUNTDOWN_COUNT, 0) == 0) {
                            uploadImageToCloudinary();
                        }

                    }
                });
    }
}
