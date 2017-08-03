package com.example.toshiba.airbnb.Profile.BecomeAHost.SetTheScene;

        import android.content.Context;
        import android.graphics.Bitmap;
        import android.os.Bundle;
        import android.os.Parcelable;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.example.toshiba.airbnb.R;

        import java.util.ArrayList;


/**
 * Created by Owner on 2017-07-13.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {
    public Context mActivity;
    ArrayList bitMapArray = new ArrayList();
    int size = 0;

    public void addImage(Bitmap bitMapImage){
        bitMapArray.add(bitMapImage);
        size++;
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
        return size;
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder{
        ImageView ivPhoto;
        public GalleryViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.ivPhoto);
        }

        public void bindView(int position){
            ivPhoto.setImageBitmap((Bitmap) bitMapArray.get(position));
            ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


//                    ((AppCompatActivity) mActivity).getSupportFragmentManager().beginTransaction().replace(R.id.progressFragment, locationFragment).commit();


                }
            });
        }

    }

}
