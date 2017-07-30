package com.example.toshiba.airbnb.Explore;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toshiba.airbnb.R;

import java.util.ArrayList;


/**
 * Created by Owner on 2017-06-25.
 */

public class ForYouSectionAdapter extends RecyclerView.Adapter<ForYouSectionAdapter.SectionViewHolder> {
    Context mContext;
    ArrayList<Integer> numbers = new ArrayList<>();

    public void addNumbers(Integer name) {
        numbers.add(name);

    }

    @Override
    public SectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("notifyDataSetChanged", "notified");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.for_you_section_adapter, parent, false);
        mContext = parent.getContext();
        SectionViewHolder SectionViewHolder = new SectionViewHolder(view);
        return SectionViewHolder;
    }

    @Override
    public void onBindViewHolder(SectionViewHolder holder, int position) {
        holder.bindView(position);
        ForYouSingleContentAdapter forYouSingleContentAdapter = new ForYouSingleContentAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        holder.singleContentRecyclerView.setLayoutManager(layoutManager);
        holder.singleContentRecyclerView.setAdapter(forYouSingleContentAdapter);
    }


    @Override
    public int getItemCount() {
        return numbers.size();
    }

    public class SectionViewHolder extends RecyclerView.ViewHolder {
        RecyclerView singleContentRecyclerView;
        TextView textView;

        public SectionViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tvSection);
            singleContentRecyclerView = (RecyclerView) itemView.findViewById(R.id.singleContentRecyclerView);
        }

        public void bindView(int position) {
            textView.setText("Section " + String.valueOf(numbers.get(position)));
        }
    }
}
