package com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cloudinary.Cloudinary;
import com.example.toshiba.airbnb.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


import static android.R.attr.bitmap;
import static android.R.attr.imeActionId;


/**
 * Created by Owner on 2017-07-13.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    public static final String CLICKED_IMAGE_URI = "CLICKED_IMAGE_URI";
    public static final String CLICKED_IMAGE_URL = "CLICKED_IMAGE_URL";
    public static final String CLICKED_IMAGE_CAPTION = "CLICKED_IMAGE_CAPTION";
    ArrayList<Uri> imageUriArrayList = new ArrayList<>();
    //from database
    ArrayList<String> imageUrlArrayList = new ArrayList<>();
    ArrayList<String> databaseCaptionArrayList = new ArrayList<>();
    File[] listFile;
    ProgressBar progressBar;
    private GalleryFragment mFragment;
    public static final String airBnbDirectory = "/Airbnb";
    ProgressDialog dialog;
    int position;
    boolean fromDatabase;

    public GalleryAdapter(GalleryFragment fragment, boolean fromDatabase) {
        mFragment = fragment;
        this.fromDatabase = fromDatabase;
        //When RecyclerView is first initalized, load the images in SD card
        if (!fromDatabase) loadSDCard();
    }

    public void addImageFromPhone(final Uri imageUri) {
        imageUriArrayList.add(imageUri);

        notifyItemChanged(position);
        new AsyncTask<Void, Void, Void>() {
            //
            String imageUriForLoadingSP;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = ProgressDialog.show(mFragment.getActivity(), "",
                        "Image is being saved, please wait....", true);
            }

            @Override
            protected Void doInBackground(Void... params) {
                imageUriForLoadingSP = saveInSDCard(imageUri);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                dialog.dismiss();

            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);


    }


    public void addImageFromDatabase(String imageUrl) {
        imageUrlArrayList.add(imageUrl);
    }

    public void addCaptionFromDatabase(String caption){
        databaseCaptionArrayList.add(caption);
    }

    public String saveInSDCard(Uri imageUri) {

        File sdCardDirectory = Environment.getExternalStorageDirectory();
        File file = new File(sdCardDirectory.getAbsolutePath() + airBnbDirectory);
        if (!file.exists()) {
            //Create directory
            file.mkdirs();
        }
        String fileNameAsTime = "" + System.currentTimeMillis() + ".png";
        File image = new File(sdCardDirectory.getAbsolutePath() + airBnbDirectory, fileNameAsTime);

        FileOutputStream outStream;
        Bitmap bitmap;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(mFragment.getActivity().getContentResolver(), imageUri);
            outStream = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
        /* 100 to keep full quality of the image */

            outStream.flush();
            outStream.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageUri.toString();
    }

    public void loadSDCard() {
        //load images stored in SD CARD (external storage)

        //get file location
        File sdCardDirectory = Environment.getExternalStorageDirectory();
        File file = new File(sdCardDirectory.getAbsolutePath() + airBnbDirectory);

        if (file.isDirectory()) {
            listFile = file.listFiles();
            if (listFile != null) {
                for (int i = 0; i < listFile.length; i++) {
                    //Convert file path to Uri, then add to arrayList
                    imageUriArrayList.add(Uri.fromFile(new File(listFile[i].getAbsolutePath())));
//                    imageLoadingArrayList.add(false);
                }

                Log.d("mattList", "SD CARD LOADED with size of " + listFile.length);
            }
        }


    }

    @Override
    public GalleryAdapter.GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_adapter_item, parent, false);
        return new GalleryAdapter.GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryAdapter.GalleryViewHolder holder, int position) {
        holder.bindView(position);
        this.position = position;
    }

    @Override
    public int getItemCount() {
        if(!fromDatabase) {
            return imageUriArrayList.size();
        }
        else{
            return imageUrlArrayList.size();
        }
    }


    public class GalleryViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            Log.d("adapter", "ViewHolder");
            ivPhoto = (ImageView) itemView.findViewById(R.id.ivPhoto);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);

        }

        public void bindView(final int position) {
            //load images called by addImage()
            if (fromDatabase) {
                Cloudinary cloudinary = new Cloudinary(mFragment.getActivity().getString(R.string.cloudinaryEnviornmentVariable));
                Glide.with(mFragment)
                        .load(cloudinary.url().generate(imageUrlArrayList.get(position))) // Uri of the picture
                        .into(ivPhoto);

                ivPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhotoDescFragment photoDescFragment = new PhotoDescFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(CLICKED_IMAGE_URL, imageUrlArrayList.get(position));
                        bundle.putString(CLICKED_IMAGE_CAPTION, databaseCaptionArrayList.get(position));
                        photoDescFragment.setArguments(bundle);
                        mFragment.getFragmentManager().beginTransaction().replace(R.id.rootLayout, photoDescFragment)
                                .addToBackStack(null).commit();
                    }
                });

            } else {
                Glide.with(mFragment)
                        .load(imageUriArrayList.get(position)) // Uri of the picture
                        .into(ivPhoto);

                Log.d("adapter", "bindView");

                ivPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhotoDescFragment photoDescFragment = new PhotoDescFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(CLICKED_IMAGE_URI, imageUriArrayList.get(position).toString());
                        photoDescFragment.setArguments(bundle);
                        mFragment.getFragmentManager().beginTransaction().replace(R.id.progressFragment, photoDescFragment)
                                .addToBackStack(null).commit();
                    }
                });
            }
        }

    }

}
