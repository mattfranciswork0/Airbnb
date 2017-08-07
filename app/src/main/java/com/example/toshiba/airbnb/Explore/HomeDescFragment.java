package com.example.toshiba.airbnb.Explore;

/**
 * Created by TOSHIBA on 02/08/2017.
 */


import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.GalleryAdapter;
import com.example.toshiba.airbnb.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;


/**
 * Created by Owner on 2017-07-14.
 */

public class HomeDescFragment extends Fragment {
    private ArrayList<Uri> imageUriArrayList = new ArrayList<>();
    private ImageSliderPager imageSliderPager;

    public ImageSliderPager getImageSliderPager(){
        return imageSliderPager;
    }

    public void loadSDCard() {
        File[] listFile;
        //load images stored in SD CARD (external storage)

        //get file location
        File sdCardDirectory = Environment.getExternalStorageDirectory();
        File file = new File(sdCardDirectory.getAbsolutePath() + GalleryAdapter.airBnbDirectory);

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
        public void onCreate (@Nullable Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            loadSDCard();
        }

        @Nullable
        @Override
        public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup
        container, @Nullable Bundle savedInstanceState){
            View view = inflater.inflate(R.layout.fragment_home_desc, container, false);
            return view;
        }

        @Override
        public void onViewCreated (View view, @Nullable Bundle savedInstanceState){
            super.onViewCreated(view, savedInstanceState);

            ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
            final TextView tvSize = (TextView) view.findViewById(R.id.tvSize);
            imageSliderPager = new ImageSliderPager(getActivity(), imageUriArrayList, HomeDescFragment.this, viewPager);
            viewPager.setAdapter(imageSliderPager);

            ImageView ivHost = (ImageView) view.findViewById(R.id.ivHost);
            ivHost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction().replace(R.id.rootLayout, new HostProfileFragment()).commit();
                }
            });
            Glide.with(this).load("https://cdn.pixabay.com/photo/2014/03/04/12/55/people-279457_960_720.jpg").into(ivHost);

            ImageView ivGuest = (ImageView) view.findViewById(R.id.ivGuest);
            ImageView ivRoom = (ImageView) view.findViewById(R.id.ivRoom);
            ImageView ivBed = (ImageView) view.findViewById(R.id.ivBed);
            ImageView ivBathroom = (ImageView) view.findViewById(R.id.ivBathroom);

            Glide.with(getContext()).load("http://www.freeiconspng.com/uploads/person-icon-person-icon-clipart-image-from-our-icon-clipart-category--9.png").into(ivGuest);
            Glide.with(getContext()).load("http://www.freeiconspng.com/uploads/door-icon-19.png").into(ivRoom);
            Glide.with(getContext()).load("https://www.materialui.co/materialIcons/maps/hotel_grey_192x192.png").into(ivBed);
            Glide.with(getContext()).load("http://classicbathtubrefinishing.com/communities/7/000/001/766/187//images/9604963_229x158.png").into(ivBathroom);
        }

    }
