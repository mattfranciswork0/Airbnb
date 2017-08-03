package com.example.toshiba.airbnb.Explore;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toshiba.airbnb.R;


/**
 * Created by Owner on 2017-06-29.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


/**
 * Created by Owner on 2017-06-29.
 */

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.ExperienceViewHolder> {
    Context mContext;
    @Override
    public ExperienceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.experience_adapter_item, parent, false);

        mContext = parent.getContext();

        ExperienceAdapter.ExperienceViewHolder experienceViewHolder = new ExperienceAdapter.ExperienceViewHolder(view);
        return experienceViewHolder;
    }

    @Override
    public void onBindViewHolder(ExperienceViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ExperienceViewHolder extends RecyclerView.ViewHolder {
        ImageView ivExperience;
        public ExperienceViewHolder(View itemView) {
            super(itemView);
            ivExperience = (ImageView) itemView.findViewById(R.id.ivExperience);
        }

        public void bindView(int position) {
            Glide.with(mContext)
                    .load("")
                    .placeholder(R.drawable.hot_air_balloon)
                    .centerCrop().into(ivExperience);

            ivExperience.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, SingleContentDescActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
