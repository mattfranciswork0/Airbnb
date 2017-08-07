package com.example.toshiba.airbnb.Explore;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene.PhotoDescFragment;
import com.example.toshiba.airbnb.R;

import java.util.ArrayList;
import java.util.Objects;


/**
 * Created by Owner on 2017-07-16.
 */

public class ImageSliderPager extends PagerAdapter {
    public static boolean FULL_SCREEN_MODE = false;
    private Context context;
    private ArrayList<Uri> imageUriArrayList;
    private LayoutInflater layoutInflater;
    private View view;
    private HomeDescFragment homeDescFragment;
    SharedPreferences sharedPreferences;
    private String savedCaption;
    TextView tvSize;
    ViewPager viewPager;


    public void getOutOfFullScreen(){
        FULL_SCREEN_MODE = false;
        homeDescFragment.getView().findViewById(R.id.layoutDesc).setVisibility(View.VISIBLE);

        LinearLayout scrollViewChild = (LinearLayout) homeDescFragment.getView().findViewById(R.id.scrollViewChild);

        RelativeLayout relativeLayout = (RelativeLayout) homeDescFragment.getView().findViewById(R.id.relativeLayout);
        //remove bakcground color
        relativeLayout.setBackgroundColor(ContextCompat.getColor(homeDescFragment.getActivity(), android.R.color.transparent));

        tvSize.setVisibility(View.INVISIBLE);

        ScrollView.LayoutParams layoutParams = new ScrollView.LayoutParams(
                ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.MATCH_PARENT);

        layoutParams.gravity = Gravity.NO_GRAVITY;
        scrollViewChild.setLayoutParams(layoutParams);

        Toast.makeText(context, "Out of full screen" , Toast.LENGTH_LONG).show();

    }

    public ImageSliderPager(Context context, ArrayList<Uri> imageUriArrayList,
                            HomeDescFragment homeDescFragment, ViewPager viewPager) {
        this.context = context;
        this.imageUriArrayList = imageUriArrayList;
        this.homeDescFragment =homeDescFragment;
        this.viewPager = viewPager;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imageUriArrayList.size();
    }

    //identifies return value in instantiateItem
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.image_slider_item, container, false);
        container.addView(itemView);
        view = itemView;
        ImageView ivHomePhoto = (ImageView) itemView.findViewById(R.id.ivHomePhoto);

        Glide.with(context)
                .load(Uri.parse(imageUriArrayList.get(position).toString()))
                .into(ivHomePhoto);
        tvSize = (TextView) homeDescFragment.getView().findViewById(R.id.tvSize);

        //listening to image click
        ivHomePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FULL_SCREEN_MODE = true;
                homeDescFragment.getView().findViewById(R.id.layoutDesc).setVisibility(View.GONE);

                LinearLayout scrollViewChild = (LinearLayout) homeDescFragment.getView().findViewById(R.id.scrollViewChild);

                RelativeLayout relativeLayout = (RelativeLayout) homeDescFragment.getView().findViewById(R.id.relativeLayout);
                relativeLayout.setBackgroundColor(ContextCompat.getColor(homeDescFragment.getActivity(), R.color.black));

                tvSize.setVisibility(View.VISIBLE);

                ScrollView.LayoutParams layoutParams = new ScrollView.LayoutParams(
                        ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.MATCH_PARENT);

                layoutParams.gravity = Gravity.CENTER_VERTICAL;
                scrollViewChild.setLayoutParams(layoutParams);

                Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_LONG).show();

            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}
