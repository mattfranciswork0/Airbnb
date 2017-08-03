package com.example.toshiba.airbnb.Explore;

        import android.content.Context;
        import android.content.Intent;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import com.bumptech.glide.Glide;
        import com.example.toshiba.airbnb.R;


/**
 * Created by Owner on 2017-06-28.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder>{
    Context mContext;
    @Override
    public HomeAdapter.HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_adapter_item, parent, false);
        mContext = parent.getContext();
        HomeAdapter.HomeViewHolder homeViewHolder = new HomeAdapter.HomeViewHolder(view);
        return homeViewHolder;
    }

    @Override
    public void onBindViewHolder(HomeAdapter.HomeViewHolder holder, int position) {
        holder.bindView(position);
    }



    @Override
    public int getItemCount() {
        return 3;
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layoutHome;
        ImageView ivHome;
        public HomeViewHolder(View itemView) {
            super(itemView);
            layoutHome = (LinearLayout) itemView.findViewById(R.id.layoutHome);
            ivHome = (ImageView) itemView.findViewById(R.id.ivHome);
        }

        public void bindView(int position){

            Glide.with(mContext)
                    .load("")
                    .placeholder(R.drawable.home)
                    .centerCrop().into(ivHome);

            layoutHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, HomeDescActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
