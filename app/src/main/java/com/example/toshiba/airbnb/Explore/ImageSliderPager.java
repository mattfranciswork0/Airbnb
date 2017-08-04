package com.example.toshiba.airbnb.Explore;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.toshiba.airbnb.R;

import java.util.ArrayList;
import java.util.Objects;


/**
 * Created by Owner on 2017-07-16.
 */

public class ImageSliderPager extends PagerAdapter {
    Context context;
    ArrayList<String> images;
    LayoutInflater layoutInflater;
    View view;
    Activity activity;


    public ImageSliderPager(Context context, ArrayList<String> images, Activity activity) {
        this.context = context;
        this.images = images;
        this.activity = activity;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    //identifies return value in instantiateItem
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.image_slider_item, container, false);
        view = itemView;

        ImageView ivHomePhoto = (ImageView) itemView.findViewById(R.id.ivHomePhoto);
        Glide.with(context)
                .load(Uri.parse(images.get(position)))
                .into(ivHomePhoto);
//        ivHomePhoto.setImageResource(images[position]);

        container.addView(itemView);

        //listening to image click
        ivHomePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "you clicked image " + (position), Toast.LENGTH_LONG).show();
            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
