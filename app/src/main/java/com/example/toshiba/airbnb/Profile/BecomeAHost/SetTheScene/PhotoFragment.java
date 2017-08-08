package com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.example.toshiba.airbnb.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;



import static android.app.Activity.RESULT_OK;

/**
 * Created by Owner on 2017-07-13.
 */

public class PhotoFragment extends Fragment {
    private static final int SELECT_PICTURE = 1;
    public static final String IMAGE_URI = "IMAGE_URI ";
    public static final String PHOTOFRAGMENT_REMOVE = "PHOTOFRAGMENT_REMOVE";
    public static final String PHOTOFRAGMENT = "PHOTOFRAGMENT";
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        ProgressBar basicProgressBar = (ProgressBar) getActivity().findViewById(R.id.basicProgressBar);
        basicProgressBar.setProgress(25);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bundle = new Bundle();
        view.findViewById(R.id.bAddPhoto).setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri imageUri = data.getData();
                //Read IMAGE URI FORM SD CARD with InputStream
                GalleryFragment galleryFragment = new GalleryFragment();
                bundle.putString(IMAGE_URI, imageUri.toString());
                galleryFragment.setArguments(bundle);

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PHOTOFRAGMENT, Context.MODE_PRIVATE);
                //GET EDITOR
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(PHOTOFRAGMENT_REMOVE, true);
                editor.apply();

                getFragmentManager().beginTransaction()
                        .replace(R.id.progressFragment, galleryFragment).commit();


            }
        }

    }

}
