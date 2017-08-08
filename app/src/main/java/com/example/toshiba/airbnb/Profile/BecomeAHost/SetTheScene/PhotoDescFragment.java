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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.toshiba.airbnb.R;

import java.io.File;


/**
 * Created by Owner on 2017-07-17.
 */

public class PhotoDescFragment extends Fragment {
    private static final String PROGRESS_SP = "PROGRESS_SP";
    public static final String CAPTION_SP = "CAPTION_SP";
    private SharedPreferences.Editor editor;
    private static final String KEY_EDITTEXT = "KEY_EDITTEXT";
    private SharedPreferences captionSP;
    String stringImageUri;
    Uri imageUri;
    String savedCaption;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_desc, container, false);
        stringImageUri = getArguments().getString(GalleryAdapter.CLICKED_IMAGE_URI);
        imageUri = Uri.parse(stringImageUri);

        captionSP = getActivity().getSharedPreferences(CAPTION_SP, Context.MODE_PRIVATE);
        editor = captionSP.edit();
        savedCaption = captionSP.getString(stringImageUri, "");
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {


        ImageView ivClickedPhoto = (ImageView) view.findViewById(R.id.ivClickedPhoto);
        Glide.with(getActivity()).load(imageUri).into(ivClickedPhoto);

        view.findViewById(R.id.bSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etCaption = (EditText) view.findViewById(R.id.etCaption);
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

        view.findViewById(R.id.bDelete).setOnClickListener(new View.OnClickListener() {
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

