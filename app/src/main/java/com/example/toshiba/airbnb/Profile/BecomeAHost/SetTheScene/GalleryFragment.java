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

import com.example.toshiba.airbnb.Explore.HomeDescActivity;
import com.example.toshiba.airbnb.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


import static android.app.Activity.RESULT_OK;

/**
 * Created by Owner on 2017-07-13.
 */


public class GalleryFragment extends Fragment {
    private static final int SELECT_PICTURE = 1;
    private RecyclerView recyclerView;
    private GalleryAdapter galleryAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
//        dialog = new ProgressDialog(getActivity());
//        dialog.setMessage("Your message..");
//        dialog.show();
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        galleryAdapter = new GalleryAdapter(GalleryFragment.this);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(
                (int) getResources().getDimension(R.dimen.item_decoration_margin)));
        recyclerView.setAdapter(galleryAdapter);
        //Add image must be below setAdapter() becuase views in GalleryAdpater must be inflated first for addImage() to work.
        if (getArguments() != null) {
            if (getArguments().containsKey(PhotoFragment.IMAGE_URI)) {
                final Uri imageUri = Uri.parse(getArguments().getString(PhotoFragment.IMAGE_URI));
                galleryAdapter.addImage(imageUri);
                getArguments().remove(PhotoFragment.IMAGE_URI);
            }
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

        view.findViewById(R.id.bPreview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HomeDescActivity.class);
                startActivity(intent);
            }
        });

//        view.findViewById(R.id.bNext).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getFragmentManager().beginTransaction().replace(R.id.progressFragment, new DescribePlaceFragment()).addToBackStack(null).commit();
//            }
//        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri imageUri = data.getData();

                galleryAdapter.addImage(imageUri);


            }
        }
    }

}