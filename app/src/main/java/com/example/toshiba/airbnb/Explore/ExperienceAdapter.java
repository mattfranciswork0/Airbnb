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
        public ExperienceViewHolder(View itemView) {
            super(itemView);

        }

        public void bindView(int position) {
//            if(position == 1)
//                textView.setText("Hey");
        }
    }
}
