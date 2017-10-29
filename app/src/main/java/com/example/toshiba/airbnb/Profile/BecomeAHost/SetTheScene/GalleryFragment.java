package com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.toshiba.airbnb.DatabaseInterface;
import com.example.toshiba.airbnb.Explore.HomeDescActivity;
import com.example.toshiba.airbnb.Explore.POJOListingData;
import com.example.toshiba.airbnb.Profile.ViewListingAndYourBooking.ViewListingAndYourBookingAdapter;
import com.example.toshiba.airbnb.R;
import com.example.toshiba.airbnb.Util.RetrofitUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Owner on 2017-07-13.
 */


public class GalleryFragment extends Fragment {
    private static final int SELECT_PICTURE = 1;
    private RecyclerView recyclerView;
    private GalleryAdapter galleryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        if (getArguments() == null) {
            ProgressBar basicProgressBar = (ProgressBar) getActivity().findViewById(R.id.basicProgressBar);
            basicProgressBar.setProgress(50);
        }
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button bNext = (Button) view.findViewById(R.id.bNext);
        Button bPreview = (Button) view.findViewById(R.id.bPreview);


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(
                (int) getResources().getDimension(R.dimen.item_decoration_margin)));

        //Add image must be below setAdapter() becuase views in GalleryAdpater must be inflated first for addImage() to work.
        if (getArguments() != null) {
            if (getArguments().containsKey(PhotoFragment.IMAGE_URI)) {
                final Uri imageUri = Uri.parse(getArguments().getString(PhotoFragment.IMAGE_URI));
                galleryAdapter.addImageFromPhone(imageUri);
                getArguments().remove(PhotoFragment.IMAGE_URI);
            }

            //load from database
            if(getArguments().containsKey(ViewListingAndYourBookingAdapter.LISTING_ID)){
                final ProgressDialog dialog = new ProgressDialog(getActivity());
                bNext.setVisibility(View.GONE);
                bPreview.setVisibility(View.GONE);
                dialog.setMessage("Loading...");
                dialog.show();
                final DatabaseInterface retrofit = RetrofitUtil.retrofitBuilderForDatabaseInterface();
                retrofit.getListingData(getArguments().getInt(ViewListingAndYourBookingAdapter.LISTING_ID)).enqueue(new Callback<POJOListingData>() {
                    @Override
                    public void onResponse(Call<POJOListingData> call, Response<POJOListingData> response) {
                        POJOListingData body = response.body();
                        galleryAdapter = new GalleryAdapter(GalleryFragment.this, true);
                        for(int i = 0; i < body.getImageData().size(); i++){
//                            Log.d("imcool", body.getImageData().get(i).getCaption().toString());
                            galleryAdapter.addImageFromDatabase(body.getImageData().get(i).getImagePath());
                            galleryAdapter.addCaptionFromDatabase(body.getImageData().get(i).getCaption());
                        }

                        recyclerView.setAdapter(galleryAdapter);
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<POJOListingData> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_LONG).show();
                        getActivity().onBackPressed();
                    }
                });
            }
        } else{
            galleryAdapter = new GalleryAdapter(GalleryFragment.this, false);
            recyclerView.setAdapter(galleryAdapter);
        }

        view.findViewById(R.id.ivAddPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //MIME DATA TYPE
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);
            }
        });

        bPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HomeDescActivity.class);
                startActivity(intent);
            }
        });

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.progressFragment, new DescribePlaceFragment()).addToBackStack(null).commit();
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri imageUri = data.getData();

                galleryAdapter.addImageFromPhone(imageUri);


            }
        }
    }

}