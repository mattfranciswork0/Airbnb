package com.example.toshiba.airbnb.Explore;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.toshiba.airbnb.R;


/**
 * Created by Owner on 2017-06-26.
 */

public class ForYouSingleContentAdapter extends RecyclerView.Adapter<ForYouSingleContentAdapter.SingleContentViewHolder> {
    Context mContext;
    @Override
    public SingleContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.for_you_single_content_adapter_item, parent, false);
        mContext = parent.getContext();
        SingleContentViewHolder singleContentViewHolder = new SingleContentViewHolder(view);
        return singleContentViewHolder;
    }

    @Override
    public void onBindViewHolder(SingleContentViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class SingleContentViewHolder extends RecyclerView.ViewHolder {
        ImageView ivSingleContent;
        public SingleContentViewHolder(View itemView) {
            super(itemView);
            ivSingleContent = (ImageView) itemView.findViewById(R.id.ivSingleContent);

        }
        public void bindView(int position){
            Glide.with(mContext)
                    .load("")
                    .placeholder(R.drawable.hot_air_balloon)
                    .centerCrop().into(ivSingleContent);

            ivSingleContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, SingleContentDescActivity.class);
                    mContext.startActivity(intent);
                }
            });

        }
    }
}