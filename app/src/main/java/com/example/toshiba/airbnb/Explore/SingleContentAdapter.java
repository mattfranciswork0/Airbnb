package com.example.toshiba.airbnb.Explore;


        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import com.example.toshiba.airbnb.R;


/**
 * Created by Owner on 2017-06-26.
 */

public class SingleContentAdapter extends RecyclerView.Adapter<SingleContentAdapter.SingleContentViewHolder> {

    @Override
    public SingleContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.section_adapter_single_content_item, parent, false);
        SingleContentViewHolder singleContentViewHolder = new SingleContentViewHolder(view);
        return singleContentViewHolder;
    }

    @Override
    public void onBindViewHolder(SingleContentViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class SingleContentViewHolder extends RecyclerView.ViewHolder {
        public SingleContentViewHolder(View itemView) {
            super(itemView);
//            itemView.findViewById(R.id.textView3);
        }
        public void bindView(int position){

        }
    }
}
