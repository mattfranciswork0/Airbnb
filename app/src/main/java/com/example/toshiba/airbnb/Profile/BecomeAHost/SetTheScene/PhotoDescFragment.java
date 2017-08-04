package com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene;

/**
 * Created by TOSHIBA on 03/08/2017.
 */


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.toshiba.airbnb.R;


/**
 * Created by Owner on 2017-07-17.
 */

public class PhotoDescFragment extends Fragment {
    private static final String PROGRESS_SP = "PROGRESS_SP";
    private SharedPreferences.Editor editor;
    private static final String KEY_EDITTEXT = "KEY_EDITTEXT";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_desc, container, false);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        Uri imageUri = Uri.parse(getArguments().getString(GalleryAdapter.CLICKED_IMAGE_URI));
        ImageView ivClickedPhoto = (ImageView) view.findViewById(R.id.ivClickedPhoto);
        Glide.with(getActivity()).load(imageUri).into(ivClickedPhoto);

        view.findViewById(R.id.bSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etCaption = (EditText) view.findViewById(R.id.etCaption);
                etCaption.getText();


                //Save EditText to sharedpreferences for HomeDescFragment (initialzied by the preview button)
                //get access to sharedpreferences to save to internal storage, only applicaitons can access it
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PROGRESS_SP, Context.MODE_PRIVATE);
                //GET EDITOR
                editor = sharedPreferences.edit();
                editor.putString(KEY_EDITTEXT, etCaption.getText().toString());
                editor.apply();
            }
        });

    }
}
