package com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene;


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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
    private Context mActivity;
    public static final String CLICKED_IMAGE_URI = "CLICKED_IMAGE_URI";
    public static final String GALLERY_FRAGMENT = "GALLERY_FRAGMENT";
    public static boolean IMAGE_LOADING = false;
    ArrayList<Uri> imageUriArrayList = new ArrayList<>();
    ArrayList<Boolean> imageLoadedArrayList = new ArrayList<>();
    File[] listFile;
    ProgressBar progressBar;
    //if position is lower than filePathsArrayList.length

    private Fragment mFragment;
    private String airBnbDirectory = "/Airbnb";

    public GalleryAdapter(Fragment fragment) {
        mFragment = fragment;
        //When RecyclerView is first initalized, load the images in SD card
        loadSDCard();
    }

    public void addImage(final Uri imageUri) {
        imageUriArrayList.add(imageUri);
        //Save image in SD CARD(External Storage)
        Log.d("mattAdapter", "addImage");
       new AsyncTask<Void, Void, Void>() {
            boolean success = false;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                imageLoadedArrayList.add(true);
            }

            @Override
            protected Void doInBackground(Void... params) {
                success = saveInSDCard(imageUri);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if(success){
                    imageLoadedArrayList.set(imageLoadedArrayList.size() - 1, false);
                    progressBar.setVisibility(View.GONE);
                }

            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);


    }

    public boolean saveInSDCard(Uri imageUri) {
        IMAGE_LOADING = true;
        File sdCardDirectory = Environment.getExternalStorageDirectory();
        File file = new File(sdCardDirectory.getAbsolutePath() + airBnbDirectory);
        if (!file.exists()) {
            //Create directory
            file.mkdirs();
        }
        String fileNameAsTime = "" + System.currentTimeMillis() + ".png";
        File image = new File(sdCardDirectory.getAbsolutePath() + airBnbDirectory, fileNameAsTime);

        boolean success = false;
        FileOutputStream outStream;
        Bitmap bitmap;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(mFragment.getActivity().getContentResolver(), imageUri);
            outStream = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
        /* 100 to keep full quality of the image */

            outStream.flush();
            outStream.close();
            success = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
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
                    imageLoadedArrayList.add(false);
                }

                Log.d("mattList", "SD CARD LOADED with size of " + listFile.length);
            }
        }


    }

    @Override
    public GalleryAdapter.GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_adapter_item, parent, false);
        mActivity = parent.getContext();
        return new GalleryAdapter.GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryAdapter.GalleryViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return imageUriArrayList.size();
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.ivPhoto);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }

        public void bindView(final int position) {
            //load images called by addImage()

            Glide.with(mFragment)
                    .load(imageUriArrayList.get(position)) // Uri of the picture
                    .into(ivPhoto);

            ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PhotoDescFragment photoDescFragment = new PhotoDescFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(CLICKED_IMAGE_URI, imageUriArrayList.get(position).toString());
                    photoDescFragment.setArguments(bundle);
                    mFragment.getFragmentManager().beginTransaction().replace(R.id.progressFragment, photoDescFragment)
                            .addToBackStack(null).commit();
//                    ((AppCompatActivity) mActivity).getSupportFragmentManager().beginTransaction().replace(R.id.progressFragment, locationFragment).commit();

                }
            });
            //Loading bar
            if(imageLoadedArrayList.get(position)){
                progressBar.setVisibility(View.VISIBLE);
            } else{
                progressBar.setVisibility(View.GONE);
            }


        }

    }

}
