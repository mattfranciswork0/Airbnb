package com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene;

/**
 * Created by TOSHIBA on 03/08/2017.
 */


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.toshiba.airbnb.R;

import java.io.File;


/**
 * Created by Owner on 2017-07-17.
 */

public class PhotoDescFragment extends Fragment {
    public static final String CAPTION_SP = "CAPTION_SP";
    private static String IMAGE_URI;
    private SharedPreferences.Editor editor;
    private SharedPreferences captionSP;
    String stringImageUri;
    Uri imageUri;
    String savedCaption;
    EditText etCaption;

    public String getStringImageUri(){
        return stringImageUri;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_desc, container, false);
        if(getArguments().containsKey(GalleryAdapter.CLICKED_IMAGE_URI)) {
            ProgressBar basicProgressBar = (ProgressBar) getActivity().findViewById(R.id.basicProgressBar);
            basicProgressBar.setProgress(50);

            stringImageUri = getArguments().getString(GalleryAdapter.CLICKED_IMAGE_URI);
            getStringImageUri();
            imageUri = Uri.parse(stringImageUri);

            captionSP = getActivity().getSharedPreferences(CAPTION_SP, Context.MODE_PRIVATE);
            editor = captionSP.edit();
            savedCaption = captionSP.getString(stringImageUri, "");
        }
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etCaption = (EditText) view.findViewById(R.id.etCaption);
        Button bSave = (Button) view.findViewById(R.id.bSave);
        Button bDelete = (Button) view.findViewById(R.id.bDelete);

        ImageView ivClickedPhoto = (ImageView) view.findViewById(R.id.ivClickedPhoto);

        if(getArguments().containsKey(GalleryAdapter.CLICKED_IMAGE_URL)
                && getArguments().containsKey(GalleryAdapter.CLICKED_IMAGE_CAPTION)){
            Glide.with(getActivity()).load(getArguments().getString(GalleryAdapter.CLICKED_IMAGE_URL)).into(ivClickedPhoto);
            etCaption.setText(getArguments().getString(GalleryAdapter.CLICKED_IMAGE_CAPTION));

            bSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            bDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        if(getArguments().containsKey(GalleryAdapter.CLICKED_IMAGE_URI)) {
            etCaption.setText(captionSP.getString(stringImageUri, ""));
            Glide.with(getActivity()).load(imageUri).into(ivClickedPhoto);

            bSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //write captions to sharedpreferences
                    if (captionSP.contains(stringImageUri)) {
                        editor.remove(stringImageUri);
                        editor.putString(stringImageUri, etCaption.getText().toString());

                        Toast.makeText(getActivity(), "Updated", Toast.LENGTH_LONG).show();
                    } else {
                        editor.putString(stringImageUri, etCaption.getText().toString());
                        Toast.makeText(getActivity(), "Saved", Toast.LENGTH_LONG).show();
                    }
                    editor.apply();
                    getFragmentManager().popBackStack();
                }
            });

            bDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                            "Image is being processed, please wait....", true);

                    File file = new File(imageUri.getPath());
                    if (file.exists()) {

                        //Delete picture
                        file.delete();
                        if (captionSP.contains(stringImageUri)) {
                            editor.remove(stringImageUri);
                            editor.apply();
                        }

                        dialog.dismiss();

                        getFragmentManager().popBackStack();
                        Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_LONG).show();
                        //Delete caption


                    }

                }
            });
        }

    }

}

