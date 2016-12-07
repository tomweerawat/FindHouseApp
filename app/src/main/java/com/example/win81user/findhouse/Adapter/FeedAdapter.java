package com.example.win81user.findhouse.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.win81user.findhouse.Utility.ClickListener;
import com.example.win81user.findhouse.Model.ItemModel;
import com.example.win81user.findhouse.Model.Property;
import com.example.win81user.findhouse.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    private ArrayList<Property> properties;
    private ItemModel itemModel;
    private static ClickListener clicklistener = null;

    public FeedAdapter(ArrayList<Property> properties) {
        this.properties = properties;
    }

    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_item,viewGroup, false);
        return new ViewHolder(view);
    }
    public void setClickListener(ClickListener listener) {
        this.clicklistener = listener;
    }

    @Override
    public void onBindViewHolder(FeedAdapter.ViewHolder viewHolder, int i) {
        viewHolder.txtName1.setText(properties.get(i).getDescription()+"\n"+properties.get(i).getPrice()+"\t"+"Baht");
        Picasso.with(viewHolder.image.getContext()).load(properties.get(i).getImage()).into(viewHolder.image);


    }

    @Override
    public int getItemCount() {
        return properties.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtName,txtName1;
        private ImageView image,shareImageView;
        private TextView txtStatus;
        private TextView url;
        private TextView timeStamp;

        public ViewHolder(View v) {
            super(v);
            txtName1 = (TextView)v.findViewById(R.id.titleTextView);
            image = (ImageView)v.findViewById(R.id.coverImageView);
//            shareImageView = (ImageView) v.findViewById(R.id.shareImageView);
            v.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (clicklistener != null) {
                clicklistener.itemClicked(v, getAdapterPosition());
            }

        }

    }

}