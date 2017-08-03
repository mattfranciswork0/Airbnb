package com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toshiba.airbnb.R;

import java.io.FileNotFoundException;
import java.io.InputStream;


import static android.app.Activity.RESULT_OK;

/**
 * Created by Owner on 2017-07-13.
 */

public class GalleryFragment extends Fragment {
    private static final int SELECT_PICTURE = 1;
    RecyclerView recyclerView;
    GalleryAdapter galleryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bitmap image = getArguments().getParcelable(PhotoFragment.BIT_MAP_IMAGE);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        galleryAdapter = new GalleryAdapter();
        galleryAdapter.addImage(image);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(
                (int) getResources().getDimension(R.dimen.item_decoration_margin)));
        recyclerView.setAdapter(galleryAdapter);

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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri imageUri = data.getData();
                //Read IMAGE URI FORM SD CARD with InputStream
                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(imageUri);

                    Bitmap image = BitmapFactory.decodeStream(inputStream);

//                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
//                    recyclerView.setLayoutManager(gridLayoutManager);
                    galleryAdapter.addImage(image);
                    recyclerView.setAdapter(galleryAdapter);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}